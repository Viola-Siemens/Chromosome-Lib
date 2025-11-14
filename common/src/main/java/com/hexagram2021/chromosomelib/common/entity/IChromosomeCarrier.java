package com.hexagram2021.chromosomelib.common.entity;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.List;
import java.util.function.ToIntFunction;

@SuppressWarnings("java:S100")
public interface IChromosomeCarrier {
	/**
	 * Get all chromosomes of this entity.
	 * @return chromosomes
	 */
	List<ChromosomeInstance> chromosomelib$getChromosomes();
	/**
	 * Set chromosomes of this entity. At the same time, compute active genes and active traits.
	 * @param chromosomes	chromosomes
	 */
	void chromosomelib$setChromosomes(Collection<ChromosomeInstance> chromosomes);

	/**
	 * Get active genes of this entity.
	 * @return weight of active genes. 0 is for inactive gene, > 0 is for active genes.
	 */
	ToIntFunction<Holder<Gene>> chromosomelib$getActiveGenes();

	/**
	 * Get active traits of this entity.
	 * @return active traits
	 */
	Collection<Holder<Trait>> chromosomelib$getActiveTraits();

	/**
	 * Assign traits to this entity.
	 */
	void chromosomelib$assignTraits();

	/**
	 * Build default chromosomes of this entity.
	 * @param entityType	entity type
	 * @param context		build context
	 * @return default chromosomes
	 */
	@ApiStatus.Internal
	default List<ChromosomeInstance> chromosomelib$buildDefaultChromosomes(IChromosomeLibEntityType entityType, IWeightedGeneList.Context context) {
		return entityType.chromosomelib$getChromosomes().values().stream()
				.<ChromosomeInstance>mapMulti((chromosome, consumer) -> {
					int ploidy = this.chromosomelib$getPloidy();
					ChromosomeInstance instance = null;
					for(int i = 0; i < ploidy; ++i) {
						instance = ChromosomeInstance.of(
								chromosome,
								context.random().nextBoolean() ? ChromosomeType.LEFT : ChromosomeType.RIGHT,
								context.withLast(instance)
						);
						consumer.accept(instance);
					}
				}).toList();
	}

	/**
	 * Get ploidy of this entity. For example, a horse is a diploid entity, so we return 2; a mule is an allodiploid entity (but we consider it is a haploid), so we return 1.
	 * @return ploidy
	 */
	default int chromosomelib$getPloidy() {
		return 2;
	}

	/**
	 * Mark isTraitsSolved flag to false.
	 */
	void chromosomelib$resetTraits();
}
