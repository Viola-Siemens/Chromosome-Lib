package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.*;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.common.util.exception.GeneFrequencyNotPresentException;
import com.hexagram2021.chromosomelib.common.util.exception.InvalidGeneFromGeneLocusException;
import com.hexagram2021.chromosomelib.common.util.exception.RegistryConcurrentModificationException;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to register relations between registries.
 * Please ONLY call these APIs in main thread to avoid {@code ConcurrentModificationException}!
 */
@SuppressWarnings("UnstableApiUsage")
public final class RegistryRelations {
	private static boolean isFrozen = false;

	private static final Map<EntityType<?>, ImmutableList.Builder<Holder<Chromosome>>> entityType2ChromosomeMap = Maps.newIdentityHashMap();
	private static final Map<Holder<Chromosome>, ImmutableList.Builder<Holder<GeneLocus>>> chromosome2GeneLocusMap = Maps.newIdentityHashMap();
	private static final Map<Holder<GeneLocus>, ImmutableList.Builder<Holder<Gene>>> geneLocus2GeneMap = Maps.newIdentityHashMap();
	private static final Map<EntityType<?>, ImmutableList.Builder<Holder<TraitType>>> entityType2TraitTypeMap = Maps.newIdentityHashMap();
	private static final Map<TagKey<EntityType<?>>, ImmutableList.Builder<Holder<Chromosome>>> entityTypeTag2ChromosomeMap = Maps.newHashMap();
	private static final Map<TagKey<EntityType<?>>, ImmutableList.Builder<Holder<TraitType>>> entityTypeTag2TraitTypeMap = Maps.newHashMap();

	private static final ImmutableGraph.Builder<Holder<Gene>> disableRelations = GraphBuilder.directed().immutable();

	private static final ImmutableMap.Builder<Holder<GeneLocus>, IWeightedGeneList> geneFrequency = ImmutableMap.builder();

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
	public static void registerEntityType2Chromosome(EntityType<?> entityType, Holder<Chromosome> chromosome) {
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
	public static void registerChromosome2GeneLocus(Holder<Chromosome> chromosome, Holder<GeneLocus> geneLocus) {
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
	public static void registerGeneLocus2Gene(Holder<GeneLocus> geneLocus, Holder<Gene> gene) {
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
	 * @param traitType		the trait type
	 */
	public static void registerEntityType2TraitType(EntityType<?> entityType, Holder<TraitType> traitType) {
		try {
			getBuilder(entityType2TraitTypeMap, entityType).add(traitType);
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
	 * @param traitType		the trait type
	 */
	public static void registerEntityTypeTag2TraitType(TagKey<EntityType<?>> entityTypeTag, Holder<TraitType> traitType) {
		try {
			getBuilder(entityTypeTag2TraitTypeMap, entityTypeTag).add(traitType);
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
	 * For genes of incomplete dominance, co dominance, and mosaic dominance types, please specify rules in {@link com.hexagram2021.chromosomelib.common.trait.TraitHandler#HANDLERS} instead of this method.
	 * Two genes should be (multiple) geneLocusInstances of the same gene locus.
	 *
	 * @param dominant	the dominant gene
	 * @param recessive	the recessive gene
	 */
	public static void registerDisableRelation(Holder<Gene> dominant, Holder<Gene> recessive) {
		if(isFrozen) {
			throw new IllegalStateException("Relations registry is already frozen!");
		}
		if(dominant instanceof AbstractRegisterEntry<Gene> registerEntry) {
			dominant = registerEntry.asHolder();
		}
		if(recessive instanceof AbstractRegisterEntry<Gene> registerEntry) {
			recessive = registerEntry.asHolder();
		}
		disableRelations.putEdge(dominant, recessive);
	}

	/**
	 * Register the gene frequency of a gene locus.
	 * <p>Notice that holders are NOT typed {@link Holder.Direct}. Either {@link AbstractRegisterEntry} or {@link Holder.Reference} is acceptable.
	 * @param geneLocus	the gene locus
	 * @param entries	genes and their weights
	 */
	public static void registerGeneFrequency(Holder<GeneLocus> geneLocus, IWeightedGeneList.Builder entries) {
		if(isFrozen) {
			throw new IllegalStateException("Relations registry is already frozen!");
		}
		geneFrequency.put(geneLocus, entries.build());
	}

	@ApiStatus.Internal
	@SuppressWarnings("unchecked")
	public static void freezeAndBuild() {
		Registry<EntityType<?>> entityTypeRegistry = BuiltInRegistries.ENTITY_TYPE;
		Registry<Gene> geneRegistry = (Registry<Gene>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENES.location()));
		Registry<GeneLocus> geneLocusRegistry = (Registry<GeneLocus>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENE_LOCI.location()));
		Registry<Trait> traitRegistry = (Registry<Trait>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.TRAITS.location()));

		buildEntityType2ChromosomeRelations(entityTypeRegistry);
		buildChromosome2GeneLocusRelations();
		buildGeneLocus2GeneRelations();
		buildEntityType2TraitRelations(entityTypeRegistry);

		checkGeneFrequencyLists(geneLocusRegistry);

		buildTraitType2TraitsReversedRelations(traitRegistry);

		buildGeneTopologicalOrder(geneRegistry);

		isFrozen = true;
		CLLogger.info("Register successfully. Relations registry is frozen.");
	}

	private static void checkGeneFrequencyLists(Registry<GeneLocus> geneLocusRegistry) {
		Set<Holder<GeneLocus>> baseline = geneLocusRegistry.holders().collect(Collectors.toCollection(AbstractRegisterEntry::newHolderTreeSet));
		Map<Holder<GeneLocus>, IWeightedGeneList> geneFrequencyLists = geneFrequency.build();
		geneFrequencyLists.forEach((geneLocus, list) -> {
			Set<Holder<Gene>> set = Objects.requireNonNull(geneLocus.value().genes).stream().collect(Collectors.toCollection(AbstractRegisterEntry::newHolderTreeSet));
			list.allGenes().forEach(gene -> {
				if(!set.remove(gene)) {
					throw new InvalidGeneFromGeneLocusException(gene, geneLocus);
				}
			});
			if(!set.isEmpty()) {
				throw new GeneFrequencyNotPresentException(set, geneLocus);
			}
			baseline.remove(geneLocus);
		});
		baseline.forEach(geneLocus -> {
			GeneLocus value = geneLocus.value();
			if(value.genes != null && value.genes.size() != 0) {
				throw new GeneFrequencyNotPresentException(value.genes.stream().collect(Collectors.toCollection(Sets::newIdentityHashSet)), geneLocus);
			}
		});
		GeneLocus.setGeneFrequency(geneFrequencyLists);
	}

	private static void buildEntityType2TraitRelations(Registry<EntityType<?>> entityTypeRegistry) {
		CLLogger.info("Extracting {} EntityType tags for EntityType to Trait Type relations...", entityTypeTag2TraitTypeMap.size());
		entityTypeTag2TraitTypeMap.forEach((tag, builder) -> {
			ImmutableList<Holder<TraitType>> traitTypes = builder.build();
			BuiltInRegistries.ENTITY_TYPE.getTag(tag).ifPresent(entries -> entries.forEach(holder -> holder.unwrapKey().ifPresent(entityTypeKey -> {
				EntityType<?> entityType = entityTypeRegistry.get(entityTypeKey);
				if(entityType != null) {
					getBuilder(entityType2TraitTypeMap, entityType).addAll(traitTypes);
					countEntityType2Trait += traitTypes.size();
				}
			})));
		});
		entityType2TraitTypeMap.forEach((entityType, builder) -> {
			ImmutableList<Holder<TraitType>> traitTypes = builder.build();
			if(entityType instanceof IChromosomeLibEntityType clEntityType) {
				clEntityType.chromosomelib$setTraitTypes(HolderSet.direct(traitTypes));
			}
		});
		CLLogger.info("Registered {} EntityType to TraitType relations from {} EntityTypes.", countEntityType2Trait, entityType2TraitTypeMap.size());
	}

	private static void buildGeneLocus2GeneRelations() {
		geneLocus2GeneMap.forEach((geneLocusHolder, builder) -> {
			GeneLocus geneLocus = geneLocusHolder.value();
			ImmutableList<Holder<Gene>> genes = builder.build();
			geneLocus.genes = HolderSet.direct(genes);
			genes.forEach(geneHolder -> geneHolder.value().geneLocus = Holder.direct(geneLocus));
		});
		CLLogger.info("Registered {} GeneLocus to Gene relations from {} GeneLoci.", countGeneLocus2Gene, geneLocus2GeneMap.size());
	}

	private static void buildChromosome2GeneLocusRelations() {
		chromosome2GeneLocusMap.forEach((chromosomeHolder, builder) -> {
			Chromosome chromosome = chromosomeHolder.value();
			ImmutableList<Holder<GeneLocus>> geneLoci = builder.build();
			Int2ObjectMap<Holder<GeneLocus>> leftGeneLocusMap = new Int2ObjectArrayMap<>();
			Int2ObjectMap<Holder<GeneLocus>> rightGeneLocusMap = new Int2ObjectArrayMap<>();
			for(Holder<GeneLocus> geneLocusHolder : geneLoci) {
				GeneLocus geneLocus = geneLocusHolder.value();
				int leftIndex = geneLocus.index(ChromosomeType.LEFT);
				int rightIndex = geneLocus.index(ChromosomeType.RIGHT);
				if(leftIndex >= 0) {
					Holder<GeneLocus> old = leftGeneLocusMap.put(leftIndex, geneLocusHolder);
					if(old != null) {
						throw new IllegalStateException("GeneLocus conflict at left side, index " + leftIndex + "for chromosome " + chromosomeHolder +
								". Registered " + old.unwrapKey().orElse(null) + " and " + geneLocusHolder.unwrapKey().orElse(null) + ".");
					}
				}
				if(rightIndex >= 0) {
					Holder<GeneLocus> old = rightGeneLocusMap.put(rightIndex, geneLocusHolder);
					if(old != null) {
						throw new IllegalStateException("GeneLocus conflict at right side, index " + rightIndex + "for chromosome " + chromosomeHolder +
								". Registered " + old.unwrapKey().orElse(null) + " and " + geneLocusHolder.unwrapKey().orElse(null) + ".");
					}
				}
			}
			chromosome.maintain(Int2ObjectMaps.unmodifiable(leftGeneLocusMap), Int2ObjectMaps.unmodifiable(rightGeneLocusMap));
		});
		CLLogger.info("Registered {} Chromosome to GeneLocus relations from {} Chromosomes.", countChromosome2GeneLocus, chromosome2GeneLocusMap.size());
	}

	private static void buildEntityType2ChromosomeRelations(Registry<EntityType<?>> entityTypeRegistry) {
		CLLogger.info("Extracting {} EntityType tags for EntityType to Chromosome relations...", entityTypeTag2ChromosomeMap.size());
		entityTypeTag2ChromosomeMap.forEach((tag, builder) -> {
			ImmutableList<Holder<Chromosome>> chromosomes = builder.build();
			BuiltInRegistries.ENTITY_TYPE.getTag(tag).ifPresent(entries -> entries.forEach(holder -> holder.unwrapKey().ifPresent(entityTypeKey -> {
				EntityType<?> entityType = entityTypeRegistry.get(entityTypeKey);
				if(entityType != null) {
					getBuilder(entityType2ChromosomeMap, entityType).addAll(chromosomes);
					countEntityType2Chromosome += chromosomes.size();
				}
			})));
		});
		entityType2ChromosomeMap.forEach((entityType, builder) -> {
			ImmutableList<Holder<Chromosome>> chromosomes = builder.build();
			Int2ObjectMap<Holder<Chromosome>> chromosomeMap = new Int2ObjectArrayMap<>();
			for(Holder<Chromosome> chromosomeHolder : chromosomes) {
				Chromosome chromosome = chromosomeHolder.value();
				Holder<Chromosome> old = chromosomeMap.put(chromosome.index(), chromosomeHolder);
				if(old != null) {
					throw new IllegalStateException("Chromosome conflict at index " + chromosome.index() + "for entity type " + entityType +
							". Registered " + old.unwrapKey().orElse(null) + " and " + chromosomeHolder.unwrapKey().orElse(null) + ".");
				}
			}
			if(entityType instanceof IChromosomeLibEntityType clEntityType) {
				clEntityType.chromosomelib$setChromosomes(chromosomeMap);
			}
		});
		CLLogger.info("Registered {} EntityType to Chromosome relations from {} EntityTypes.", countEntityType2Chromosome, entityType2ChromosomeMap.size());
	}

	private static void buildTraitType2TraitsReversedRelations(Registry<Trait> traitRegistry) {
		ListMultimap<Holder<TraitType>, Holder<Trait>> traitType2TraitsMap = Multimaps.newListMultimap(Maps.newIdentityHashMap(), Lists::newArrayList);
		traitRegistry.holders().forEach(traitHolder -> traitType2TraitsMap.put(traitHolder.value().getType(), traitHolder));
		for(Holder<TraitType> traitTypeHolder: traitType2TraitsMap.keySet()) {
			traitTypeHolder.value().setValues(HolderSet.direct(traitType2TraitsMap.get(traitTypeHolder)));
		}
	}

	private static void buildGeneTopologicalOrder(Registry<Gene> geneRegistry) {
		geneRegistry.asHolderIdMap().forEach(disableRelations::addNode);

		ImmutableGraph<Holder<Gene>> geneGraph = disableRelations.build();
		Object2IntMap<Holder<Gene>> degrees = new Object2IntRBTreeMap<>(Comparator.comparingInt(Holder::hashCode));
		Queue<Holder<Gene>> queue = Queues.newArrayDeque();
		for(Holder<Gene> gene : geneGraph.nodes()) {
			if(gene instanceof AbstractRegisterEntry<Gene> registerEntry) {
				gene = registerEntry.asHolder();
			}
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
		Gene.setDisableRelationship(geneGraph);
	}
}
