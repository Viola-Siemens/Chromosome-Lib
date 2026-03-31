package com.hexagram2021.chromosomelib.event;

import com.google.common.collect.ImmutableCollection;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;

/**
 * Fired when an entity with unpaired chromosomes is about to breed.
 *
 * @author liudongyu
 */
public interface UnpairedChromosomesToBreedSolver {
	/**
	 * Called when an entity with unpaired chromosomes is about to breed.
	 *
	 * @param entityType			the entity type with unpaired chromosomes
	 * @param chromosomeInstances	the chromosome instances of this entity
	 * @param random				random source
	 * @param builder				chromosomes builder, do NOT modify it until you decide to return {@code true}
	 * @return {@code true} if we just solved this situation. {@code false} otherwise. If false, the builder should NOT be modified.
	 */
	boolean solveUnpairedChromosomesToBreed(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomeInstances, RandomSource random, ImmutableCollection.Builder<ChromosomeInstance> builder);
}
