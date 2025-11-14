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

import java.util.Comparator;
import java.util.stream.Collectors;

public class Chromosome {
	/**
	 * The gene loci of the left side of this chromosome.
	 */
	private Int2ObjectMap<Holder<GeneLocus>> leftGeneLoci = Int2ObjectMaps.emptyMap();
	/**
	 * The gene loci of the right side of this chromosome.
	 */
	private Int2ObjectMap<Holder<GeneLocus>> rightGeneLoci = Int2ObjectMaps.emptyMap();

	private final int index;

	private int homoSegmentStartDiff = 0;
	private int homoCount = 0;

	public Chromosome(int index) {
		this.index = index;
	}

	public int index() {
		return this.index;
	}

	public static int index(Holder<Chromosome> chromosome, EntityType<?> entityType) {
		if(entityType instanceof IChromosomeLibEntityType chromosomeLibEntityType) {
			return chromosomeLibEntityType.chromosomelib$getChromosomeIndex(chromosome);
		}
		return chromosome.value().index();
	}

	public Int2ObjectMap<Holder<GeneLocus>> geneLoci(ChromosomeType type) {
		return switch (type) {
			case LEFT -> this.leftGeneLoci;
			case RIGHT -> this.rightGeneLoci;
		};
	}

	/**
	 * @see com.hexagram2021.chromosomelib.registry.RegistryRelations#buildChromosome2GeneLocusRelations()
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
	 * @return leftIndex - rightIndex
	 */
	@ApiStatus.Internal
	public int homoSegmentStartDiff() {
		return this.homoSegmentStartDiff;
	}
}
