package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Biome-specific weighted gene list.
 *
 * @author liudongyu
 */
public class BiomeSpecificWeightedGeneList implements IWeightedGeneList {
	/**
	 * Common gene list - all biomes share the same gene list.
	 */
	protected final IWeightedGeneList commonList;
	/**
	 * Biome-specific gene lists - each biome has its own gene list.
	 */
	protected final Map<Holder<Biome>, IWeightedGeneList> biomeSpecificLists;

	/**
	 * Create a BiomeSpecificWeightedGeneList.
	 * @param commonList			the common gene list
	 * @param biomeSpecificLists	the biome-specific gene lists
	 */
	protected BiomeSpecificWeightedGeneList(IWeightedGeneList commonList, Map<Holder<Biome>, IWeightedGeneList> biomeSpecificLists) {
		this.commonList = commonList;
		this.biomeSpecificLists = biomeSpecificLists;
	}

	/**
	 * Get a random gene from this BiomeSpecificWeightedGeneList.
	 * @param context	the context, where biome is passed.
	 * @return a random gene
	 */
	@Override
	public Holder<Gene> getRandomGene(Context context) {
		IWeightedGeneList biomeSpecificList = this.get(context.biome());
		int commonTotalWeight = this.commonList.totalWeight(context);
		int index = context.random().nextInt(commonTotalWeight + biomeSpecificList.totalWeight(context));
		if(index < commonTotalWeight) {
			return this.commonList.getRandomGene(context);
		}
		return biomeSpecificList.getRandomGene(context);
	}

	/**
	 * Get the total weight of this list.
	 * @param context	the context
	 * @return the total weight
	 */
	@Override
	public int totalWeight(Context context) {
		return this.commonList.totalWeight(context) + this.get(context.biome()).totalWeight(context);
	}

	/**
	 * Get all possible genes in this list.
	 * @return all possible genes
	 */
	@Override
	public Stream<Holder<Gene>> allGenes() {
		return Stream.concat(this.commonList.allGenes(), this.biomeSpecificLists.values().stream().flatMap(IWeightedGeneList::allGenes)).distinct();
	}

	private IWeightedGeneList get(@Nullable Holder<Biome> biome) {
		return biome == null ? IWeightedGeneList.EMPTY : this.biomeSpecificLists.getOrDefault(biome, IWeightedGeneList.EMPTY);
	}

	/**
	 * Create a builder for BiomeSpecificWeightedGeneList. SimpleWeightedGeneList is applied to all biomes.
	 * @return a builder
	 */
	public static Builder simpleBuilder() {
		return new Builder(SimpleWeightedGeneList::new);
	}

	/**
	 * Create a builder for BiomeSpecificWeightedGeneList. StableWeightedGeneList is applied to all biomes.
	 * @param possibilityOfStable	Possibility for stable weighted gene list
	 * @see StableWeightedGeneList#getRandomGene
	 * @return a builder
	 */
	public static Builder stableBuilder(double possibilityOfStable) {
		return new Builder(entries -> new StableWeightedGeneList(possibilityOfStable, entries));
	}

	/**
	 * Builder for BiomeSpecificWeightedGeneList.
	 */
	public static class Builder extends IWeightedGeneList.Builder {
		/**
		 * Biome-specific gene lists.
		 */
		protected final Map<Holder<Biome>, ImmutableList.Builder<Entry>> biomeSpecificShadowed = new Object2ObjectOpenHashMap<>();
		/**
		 * Factory for creating a weighted gene list.
		 */
		private final WeightedGeneListFactory factory;

		/**
		 * Create a builder.
		 * @param factory	factory for creating a weighted gene list
		 */
		public Builder(WeightedGeneListFactory factory) {
			super();
			this.factory = factory;
		}

		/**
		 * Add a gene to a biome to the builder.
		 * @param biome		the biome
		 * @param gene		the gene
		 * @param weight	the weight
		 * @return this builder
		 */
		public Builder add(Holder<Biome> biome, Holder<Gene> gene, int weight) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(Entry.of(gene, weight));
			return this;
		}
		/**
		 * Add some genes to a biome to the builder.
		 * @param biome		the biome
		 * @param elements	elements
		 * @return this builder
		 */
		public Builder add(Holder<Biome> biome, Entry... elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(elements);
			return this;
		}
		/**
		 * Add all genes from a collection to a biome to the builder.
		 * @param biome		the biome
		 * @param elements	a collection
		 * @return this builder
		 */
		public Builder addAll(Holder<Biome> biome, Iterable<Entry> elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements);
			return this;
		}
		/**
		 * Add all genes from a collection to a biome to the builder.
		 * @param biome		the biome
		 * @param elements	a collection
		 * @return this builder
		 */
		public Builder addAll(Holder<Biome> biome, Iterator<Entry> elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements);
			return this;
		}

		/**
		 * Add a gene to some biomes to the builder.
		 * @param biomes	a set of biomes
		 * @param gene		the gene
		 * @param weight	the weight
		 * @return this builder
		 */
		public Builder add(HolderSet<Biome> biomes, Holder<Gene> gene, int weight) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(Entry.of(gene, weight)));
			return this;
		}
		/**
		 * Add some genes to some biomes to the builder.
		 * @param biomes	a set of biomes
		 * @param elements	elements
		 * @return this builder
		 */
		public Builder add(HolderSet<Biome> biomes, Entry... elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(elements));
			return this;
		}
		/**
		 * Add all genes from a collection to some biomes to the builder.
		 * @param biomes	a set of biomes
		 * @param elements	a collection
		 * @return this builder
		 */
		public Builder addAll(HolderSet<Biome> biomes, Iterable<Entry> elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements));
			return this;
		}
		/**
		 * Add all genes from a collection to some biomes to the builder.
		 * @param biomes	a set of biomes
		 * @param elements	a collection
		 * @return this builder
		 */
		public Builder addAll(HolderSet<Biome> biomes, Iterator<Entry> elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements));
			return this;
		}

		/**
		 * Build the BiomeSpecificWeightedGeneList.
		 * @return the BiomeSpecificWeightedGeneList
		 */
		@Override
		BiomeSpecificWeightedGeneList build() {
			ImmutableMap.Builder<Holder<Biome>, IWeightedGeneList> biomeSpecificShadowedBuilder = ImmutableMap.builder();
			this.biomeSpecificShadowed.forEach((biome, builder) -> biomeSpecificShadowedBuilder.put(biome, this.factory.create(builder.build())));
			return new BiomeSpecificWeightedGeneList(this.factory.create(this.shadowed.build()), biomeSpecificShadowedBuilder.build());
		}
	}
}
