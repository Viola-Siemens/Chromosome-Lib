package com.hexagram2021.chromosomelib.common.chromosome;

import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a chromosome definition in the genetic system. <br/>
 * Each chromosome contains multiple gene loci and determines specific traits of entities.
 *
 * @author liudongyu
 */
public class Chromosome {
	/**
	 * The necessary chromosome type of each chromosome.
	 * @see com.hexagram2021.chromosomelib.registry.RegistryRelations#registerNecessaryChromosomeTypes
	 */
	private static Map<Holder<Chromosome>, ChromosomeType> necessaryChromosomeTypes = Map.of();

	/**
	 * The gene loci of the left side of this chromosome.
	 */
	private Int2ObjectMap<Holder<GeneLocus>> leftGeneLoci = Int2ObjectMaps.emptyMap();
	/**
	 * The gene loci of the right side of this chromosome.
	 */
	private Int2ObjectMap<Holder<GeneLocus>> rightGeneLoci = Int2ObjectMaps.emptyMap();

	/**
	 * The index of this chromosome within entity's chromosome set.
	 */
	private final int index;

	/**
	 * The starting position difference of the homologous segment (leftIndex - rightIndex).
	 */
	private int homoSegmentStartDiff = 0;
	/**
	 * The count of homologous gene loci in the homologous segment.
	 */
	private int homoCount = 0;

	/**
	 * Constructs a chromosome with the specified index.
	 *
	 * @param index The index of this chromosome
	 */
	public Chromosome(int index) {
		this.index = index;
	}

	/**
	 * Gets the index of this chromosome.
	 *
	 * @return The chromosome index
	 */
	public int index() {
		return this.index;
	}

	/**
	 * Gets the index of a chromosome holder for a specific entity type.
	 *
	 * @param chromosome The chromosome holder
	 * @param entityType The entity type
	 * @return The chromosome index
	 */
	public static int index(Holder<Chromosome> chromosome, EntityType<?> entityType) {
		if(entityType instanceof IChromosomeLibEntityType chromosomeLibEntityType) {
			return chromosomeLibEntityType.chromosomelib$getChromosomeIndex(chromosome);
		}
		return chromosome.value().index();
	}

	/**
	 * Gets the gene loci map for the specified chromosome type (left or right).
	 *
	 * @param type The chromosome type
	 * @return The gene loci map indexed by position
	 */
	public Int2ObjectMap<Holder<GeneLocus>> geneLoci(ChromosomeType type) {
		return switch (type) {
			case LEFT -> this.leftGeneLoci;
			case RIGHT -> this.rightGeneLoci;
		};
	}

	/**
	 * Maintain the homologous gene count and offset.
	 *
	 * @see com.hexagram2021.chromosomelib.registry.RegistryRelations#buildChromosome2GeneLocusRelations
	 * @param leftGeneLoci	left gene loci
	 * @param rightGeneLoci	right gene loci
	 */
	@ApiStatus.Internal
	public void maintain(Int2ObjectMap<Holder<GeneLocus>> leftGeneLoci, Int2ObjectMap<Holder<GeneLocus>> rightGeneLoci) {
		this.leftGeneLoci = leftGeneLoci;
		this.rightGeneLoci = rightGeneLoci;

		// O(n^2), maybe we can "optimize"(?) it to O(nlogn) with FFT or NTT algorithm.
		ObjectSet<Holder<GeneLocus>> intersection = leftGeneLoci.values().stream()
				.filter(locus -> locus.value() instanceof GeneLocus.HomologousGeneLocus)
				.collect(Collectors.toCollection(ObjectOpenHashSet::new));
		intersection.retainAll(rightGeneLoci.values());

		Int2IntMap kMap = new Int2IntOpenHashMap();
		for(Holder<GeneLocus> locus : intersection) {
			if(locus.value() instanceof GeneLocus.HomologousGeneLocus geneLocus) {
				kMap.compute(geneLocus.leftIndex() - geneLocus.rightIndex(), (k, v) -> v == null ? 1 : v + 1);
			}
		}
		kMap.int2IntEntrySet().stream().max(Comparator.comparingInt(Int2IntMap.Entry::getIntValue)).ifPresent(entry -> {
			this.homoSegmentStartDiff = entry.getIntKey();
			this.homoCount = entry.getIntValue();
		});
	}

	/**
	 * Gets the offset of the homologous segment.
	 *
	 * @return leftIndex - rightIndex
	 */
	@ApiStatus.Internal
	public int homoSegmentStartDiff() {
		return this.homoSegmentStartDiff;
	}

	/**
	 * Set necessary chromosome types.
	 * @param necessaryChromosomeTypes	necessary chromosome types
	 */
	@ApiStatus.Internal
	public static void setNecessaryChromosomeTypes(Map<Holder<Chromosome>, ChromosomeType> necessaryChromosomeTypes) {
		Chromosome.necessaryChromosomeTypes = necessaryChromosomeTypes;
	}

	/**
	 * Get necessary chromosome type.
	 * @param chromosome	chromosome
	 * @return necessary chromosome type of this chromosome. {@code null} if this chromosome has no necessary chromosome type.
	 */
	@Nullable
	public static ChromosomeType getNecessaryChromosomeType(Holder<Chromosome> chromosome) {
		return necessaryChromosomeTypes.get(chromosome);
	}
}
