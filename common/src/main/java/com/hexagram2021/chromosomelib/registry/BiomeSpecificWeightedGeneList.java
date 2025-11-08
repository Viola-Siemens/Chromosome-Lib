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

public class BiomeSpecificWeightedGeneList implements IWeightedGeneList {
	protected final IWeightedGeneList commonList;
	protected final Map<Holder<Biome>, IWeightedGeneList> biomeSpecificLists;

	protected BiomeSpecificWeightedGeneList(IWeightedGeneList commonList, Map<Holder<Biome>, IWeightedGeneList> biomeSpecificLists) {
		this.commonList = commonList;
		this.biomeSpecificLists = biomeSpecificLists;
	}

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

	@Override
	public int totalWeight(Context context) {
		return this.commonList.totalWeight(context) + this.get(context.biome()).totalWeight(context);
	}

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
	 */
	public static Builder stableBuilder(double possibilityOfStable) {
		return new Builder(entries -> new StableWeightedGeneList(possibilityOfStable, entries));
	}

	public static class Builder extends IWeightedGeneList.Builder {
		protected final Map<Holder<Biome>, ImmutableList.Builder<Entry>> biomeSpecificShadowed = new Object2ObjectOpenHashMap<>();
		private final WeightedGeneListFactory factory;

		public Builder(WeightedGeneListFactory factory) {
			super();
			this.factory = factory;
		}

		public Builder add(Holder<Biome> biome, Holder<Gene> gene, int weight) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(Entry.of(gene, weight));
			return this;
		}
		public Builder add(Holder<Biome> biome, Entry... elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(elements);
			return this;
		}
		public Builder addAll(Holder<Biome> biome, Iterable<Entry> elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements);
			return this;
		}
		public Builder addAll(Holder<Biome> biome, Iterator<Entry> elements) {
			this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements);
			return this;
		}

		public Builder add(HolderSet<Biome> biomes, Holder<Gene> gene, int weight) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(Entry.of(gene, weight)));
			return this;
		}
		public Builder add(HolderSet<Biome> biomes, Entry... elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).add(elements));
			return this;
		}
		public Builder addAll(HolderSet<Biome> biomes, Iterable<Entry> elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements));
			return this;
		}
		public Builder addAll(HolderSet<Biome> biomes, Iterator<Entry> elements) {
			biomes.forEach(biome -> this.biomeSpecificShadowed.computeIfAbsent(biome, ignored -> ImmutableList.builder()).addAll(elements));
			return this;
		}

		@Override
		BiomeSpecificWeightedGeneList build() {
			ImmutableMap.Builder<Holder<Biome>, IWeightedGeneList> biomeSpecificShadowedBuilder = ImmutableMap.builder();
			this.biomeSpecificShadowed.forEach((biome, builder) -> biomeSpecificShadowedBuilder.put(biome, this.factory.create(builder.build())));
			return new BiomeSpecificWeightedGeneList(this.factory.create(this.shadowed.build()), biomeSpecificShadowedBuilder.build());
		}
	}
}
