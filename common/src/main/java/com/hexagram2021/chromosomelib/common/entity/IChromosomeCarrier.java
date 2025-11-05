package com.hexagram2021.chromosomelib.common.entity;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import net.minecraft.core.Holder;

import java.util.Collection;
import java.util.function.ToIntFunction;

@SuppressWarnings("java:S100")
public interface IChromosomeCarrier {
	/**
	 * Get all chromosomes of this entity.
	 * @return chromosomes
	 */
	Collection<ChromosomeInstance> chromosomelib$getChromosomes();
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
}
