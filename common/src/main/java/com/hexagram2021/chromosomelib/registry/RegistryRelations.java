package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.util.RegistryConcurrentModificationException;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

/**
 * This class is used to register relations between registries.
 * Please ONLY call these APIs in main thread to avoid {@code ConcurrentModificationException}!
 */
@SuppressWarnings("UnstableApiUsage")
public final class RegistryRelations {
	private static boolean isFrozen = false;

	private static final Map<ResourceKey<EntityType<?>>, ImmutableList.Builder<Holder<Chromosome>>> entityType2ChromosomeMap = Maps.newIdentityHashMap();
	private static final Map<ResourceKey<Chromosome>, ImmutableList.Builder<Holder<GeneLocus>>> chromosome2GeneLocusMap = Maps.newIdentityHashMap();
	private static final Map<ResourceKey<GeneLocus>, ImmutableList.Builder<Holder<Gene>>> geneLocus2GeneMap = Maps.newIdentityHashMap();
	private static final Map<ResourceKey<EntityType<?>>, ImmutableList.Builder<Holder<Trait>>> entityType2TraitMap = Maps.newIdentityHashMap();
	private static final Map<TagKey<EntityType<?>>, ImmutableList.Builder<Holder<Chromosome>>> entityTypeTag2ChromosomeMap = Maps.newHashMap();
	private static final Map<TagKey<EntityType<?>>, ImmutableList.Builder<Holder<Trait>>> entityTypeTag2TraitMap = Maps.newHashMap();

	private static final ImmutableGraph.Builder<Holder<Gene>> disableRelations = GraphBuilder.directed().immutable();

	private static int countEntityType2Chromosome = 0;
	private static int countChromosome2GeneLocus = 0;
	private static int countGeneLocus2Gene = 0;
	private static int countEntityType2Trait = 0;

	private RegistryRelations() {
	}

	/**
	 * Register the chromosome of an entity type.
	 * @param entityType	the entity type
	 * @param chromosome	the chromosome
	 */
	public static void registerEntityType2Chromosome(ResourceKey<EntityType<?>> entityType, Holder<Chromosome> chromosome) {
		try {
			getBuilder(entityType2ChromosomeMap, entityType).add(chromosome);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "entityType2ChromosomeMap", "Map", e);
		}
		countEntityType2Chromosome += 1;
	}
	/**
	 * Register the chromosome of an entity type.
	 * @param chromosome	the chromosome
	 * @param geneLocus		the gene locus
	 */
	public static void registerChromosome2GeneLocus(ResourceKey<Chromosome> chromosome, Holder<GeneLocus> geneLocus) {
		try {
			getBuilder(chromosome2GeneLocusMap, chromosome).add(geneLocus);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "chromosome2GeneLocusMap", "Map", e);
		}
		countChromosome2GeneLocus += 1;
	}
	/**
	 * Register the gene locus of a chromosome.
	 * @param geneLocus	the gene locus
	 * @param gene		the gene
	 */
	public static void registerGeneLocus2Gene(ResourceKey<GeneLocus> geneLocus, Holder<Gene> gene) {
		try {
			getBuilder(geneLocus2GeneMap, geneLocus).add(gene);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "geneLocus2GeneMap", "Map", e);
		}
		countGeneLocus2Gene += 1;
	}
	/**
	 * Register the trait of an entity type.
	 * @param entityType	the entity type
	 * @param trait			the trait
	 */
	public static void registerEntityType2Trait(ResourceKey<EntityType<?>> entityType, Holder<Trait> trait) {
		try {
			getBuilder(entityType2TraitMap, entityType).add(trait);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "entityType2TraitMap", "Map", e);
		}
		countEntityType2Trait += 1;
	}

	/**
	 * Sometimes some different mobs are the same species (e.g. Villager, Wandering Trader, Pillager, etc.), so they share same chromosomes.
	 * @param entityTypeTag	the tag of entity types
	 * @param chromosome	the chromosome
	 */
	public static void registerEntityTypeTag2Chromosome(TagKey<EntityType<?>> entityTypeTag, Holder<Chromosome> chromosome) {
		try {
			getBuilder(entityTypeTag2ChromosomeMap, entityTypeTag).add(chromosome);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "entityTypeTag2ChromosomeMap", "Map", e);
		}
	}

	/**
	 * Sometimes some different mobs are the same species (e.g. Villager, Wandering Trader, Pillager, etc.), so they may share same traits.
	 * @param entityTypeTag	the tag of entity types
	 * @param trait			the trait
	 */
	public static void registerEntityTypeTag2Trait(TagKey<EntityType<?>> entityTypeTag, Holder<Trait> trait) {
		try {
			getBuilder(entityTypeTag2TraitMap, entityTypeTag).add(trait);
		} catch (ConcurrentModificationException | IndexOutOfBoundsException e) {
			throw new RegistryConcurrentModificationException(RegistryRelations.class.getName(), "entityTypeTag2TraitMap", "Map", e);
		}
	}

	private static <T, R> ImmutableList.Builder<Holder<R>> getBuilder(Map<T, ImmutableList.Builder<Holder<R>>> map, T key) {
		if(isFrozen) {
			throw new IllegalStateException("Relations registry is already frozen!");
		}
		return map.computeIfAbsent(key, k -> new ImmutableList.Builder<>());
	}

	/**
	 * Dominant genes can render recessive genes ineffective, while many genes in reality are not binary.
	 * Please use this function to register all relative explicit implicit relationships.
	 * For genes of incomplete dominance, co dominance, and mosaic dominance types, please specify rules in {@link TraitHandler} instead of this method.
	 * Two genes should be (multiple) alleles of the same gene locus.
	 *
	 * @param dominant	the dominant gene
	 * @param recessive	the recessive gene
	 */
	public static void registerDisableRelation(Holder<Gene> dominant, Holder<Gene> recessive) {
		if(isFrozen) {
			throw new IllegalStateException("Relations registry is already frozen!");
		}
		disableRelations.putEdge(dominant, recessive);
	}

	@ApiStatus.Internal
	@SuppressWarnings("unchecked")
	public static void freezeAndBuild() {
		Registry<EntityType<?>> entityTypeRegistry = BuiltInRegistries.ENTITY_TYPE;
		Registry<Chromosome> chromosomeRegistry = (Registry<Chromosome>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.CHROMOSOMES.location()));
		Registry<GeneLocus> geneLocusRegistry = (Registry<GeneLocus>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENE_LOCI.location()));
		Registry<Gene> geneRegistry = (Registry<Gene>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENES.location()));
		CLLogger.info("Extracting {} EntityType tags for EntityType to Chromosome relations...", entityTypeTag2ChromosomeMap.size());
		entityTypeTag2ChromosomeMap.forEach((tag, builder) -> {
			ImmutableList<Holder<Chromosome>> chromosomes = builder.build();
			BuiltInRegistries.ENTITY_TYPE.getTag(tag).ifPresent(entries -> entries.forEach(holder -> holder.unwrapKey().ifPresent(entityType -> {
				getBuilder(entityType2ChromosomeMap, entityType).addAll(chromosomes);
				countEntityType2Chromosome += chromosomes.size();
			})));
		});
		entityType2ChromosomeMap.forEach((entityTypeKey, builder) -> {
			ImmutableList<Holder<Chromosome>> chromosomes = builder.build();
			Int2ObjectMap<Holder<Chromosome>> chromosomeMap = new Int2ObjectArrayMap<>();
			for(Holder<Chromosome> chromosomeHolder : chromosomes) {
				Chromosome chromosome = chromosomeHolder.value();
				Holder<Chromosome> old = chromosomeMap.put(chromosome.index(), chromosomeHolder);
				if(old != null) {
					throw new IllegalStateException("Chromosome conflict at index " + chromosome.index() + "for entity type " + entityTypeKey +
							". Registered " + old.unwrapKey().orElse(null) + " and " + chromosomeHolder.unwrapKey().orElse(null) + ".");
				}
			}
			if(entityTypeRegistry.get(entityTypeKey) instanceof IChromosomeLibEntityType entityType) {
				entityType.chromosomelib$setChromosomes(chromosomeMap);
			}
		});
		CLLogger.info("Registered {} EntityType to Chromosome relations from {} EntityTypes.", countEntityType2Chromosome, entityType2ChromosomeMap.size());
		chromosome2GeneLocusMap.forEach((chromosomeKey, builder) -> {
			Chromosome chromosome = chromosomeRegistry.get(chromosomeKey);
			if(chromosome == null) {
				throw new NullPointerException("Chromosome " + chromosomeKey + " is null!");
			}
			ImmutableList<Holder<GeneLocus>> geneLoci = builder.build();
			Int2ObjectMap<Holder<GeneLocus>> geneLocusMap = new Int2ObjectArrayMap<>();
			for(Holder<GeneLocus> geneLocusHolder : geneLoci) {
				GeneLocus geneLocus = geneLocusHolder.value();
				Holder<GeneLocus> old = geneLocusMap.put(geneLocus.index(), geneLocusHolder);
				if(old != null) {
					throw new IllegalStateException("GeneLocus conflict at index " + geneLocus.index() + "for chromosome " + chromosomeKey +
							". Registered " + old.unwrapKey().orElse(null) + " and " + geneLocusHolder.unwrapKey().orElse(null) + ".");
				}
			}
			chromosome.geneLoci = Int2ObjectMaps.unmodifiable(geneLocusMap);
		});
		CLLogger.info("Registered {} Chromosome to GeneLocus relations from {} Chromosomes.", countChromosome2GeneLocus, chromosome2GeneLocusMap.size());
		geneLocus2GeneMap.forEach((geneLocusKey, builder) -> {
			GeneLocus geneLocus = geneLocusRegistry.get(geneLocusKey);
			if(geneLocus == null) {
				throw new NullPointerException("GeneLocus " + geneLocusKey + " is null!");
			}
			ImmutableList<Holder<Gene>> genes = builder.build();
			geneLocus.genes = HolderSet.direct(genes);
			genes.forEach(geneHolder -> geneHolder.value().geneLocus = Holder.direct(geneLocus));
		});
		CLLogger.info("Registered {} GeneLocus to Gene relations from {} GeneLoci.", countGeneLocus2Gene, geneLocus2GeneMap.size());
		CLLogger.info("Extracting {} EntityType tags for EntityType to Trait relations...", entityTypeTag2TraitMap.size());
		entityTypeTag2TraitMap.forEach((tag, builder) -> {
			ImmutableList<Holder<Trait>> traits = builder.build();
			BuiltInRegistries.ENTITY_TYPE.getTag(tag).ifPresent(entries -> entries.forEach(holder -> holder.unwrapKey().ifPresent(entityType -> {
				getBuilder(entityType2TraitMap, entityType).addAll(traits);
				countEntityType2Trait += traits.size();
			})));
		});
		entityType2TraitMap.forEach((entityTypeKey, builder) -> {
			ImmutableList<Holder<Trait>> traits = builder.build();
			if(entityTypeRegistry.get(entityTypeKey) instanceof IChromosomeLibEntityType entityType) {
				entityType.chromosomelib$setTraits(HolderSet.direct(traits));
			}
		});
		CLLogger.info("Registered {} EntityType to Trait relations from {} EntityTypes.", countEntityType2Trait, entityType2TraitMap.size());

		buildGeneTopologicalOrder(geneRegistry);

		isFrozen = true;
		CLLogger.info("Register successfully. Relations registry is frozen.");
	}

	private static void buildGeneTopologicalOrder(Registry<Gene> geneRegistry) {
		geneRegistry.asHolderIdMap().forEach(disableRelations::addNode);

		ImmutableGraph<Holder<Gene>> geneGraph = disableRelations.build();
		Object2IntMap<Holder<Gene>> degrees = new Object2IntRBTreeMap<>(Comparator.comparingInt(Holder::hashCode));
		Queue<Holder<Gene>> queue = Queues.newArrayDeque();
		for(Holder<Gene> gene : geneGraph.nodes()) {
			int degree = geneGraph.inDegree(gene);
			if(degree == 0) {
				queue.add(gene);
				gene.value().topologicalOrder = 0;
			}
			degrees.put(gene, degree);
		}
		while(!queue.isEmpty()) {
			Holder<Gene> gene = queue.poll();
			for(Holder<Gene> neighbor : geneGraph.successors(gene)) {
				int degree = degrees.getInt(neighbor) - 1;
				degrees.put(neighbor, degree);
				if(degree == 0) {
					queue.add(neighbor);
					neighbor.value().topologicalOrder = gene.value().topologicalOrder + 1;
				}
			}
		}
		List<Holder<Gene>> cyclicGenes = Lists.newArrayList();
		for(Holder<Gene> gene : geneGraph.nodes()) {
			if(degrees.getInt(gene) != 0) {
				cyclicGenes.add(gene);
			}
		}
		if(!cyclicGenes.isEmpty()) {
			throw new IllegalStateException("Cyclic gene relations detected: " + cyclicGenes);
		}
	}
}
