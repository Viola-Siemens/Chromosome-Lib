package com.hexagram2021.chromosomelib.common;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.util.Breeders;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Common event handlers for chromosome breeding and genetic operations.
 *
 * @author liudongyu
 */
public final class CLCommonEvents {
	/**
	 * Called when two entities are breeding. Handles genetic crossover, breeding, mutation, and trait assignment for the offspring.
	 *
	 * @param parentA The first parent entity
	 * @param parentB The second parent entity
	 * @param child The offspring entity (can be null)
	 * @return true if breeding was successful, false if child is null
	 */
	@SuppressWarnings("java:S1854")
	public static boolean onEntityBreed(LivingEntity parentA, LivingEntity parentB, @Nullable LivingEntity child) {
		if(child == null) {
			return false;
		}

		IChromosomeCarrier carrierA = (IChromosomeCarrier) parentA;
		IChromosomeCarrier carrierB = (IChromosomeCarrier) parentB;
		IChromosomeCarrier carrierChild = (IChromosomeCarrier) child;

		Breeders.crossOver(carrierA.chromosomelib$getChromosomes(), parentA.getRandom());
		Breeders.crossOver(carrierB.chromosomelib$getChromosomes(), parentB.getRandom());
		Collection<ChromosomeInstance> childChromosomes = Breeders.breed(
				parentA.getType(), parentB.getType(),
				carrierA.chromosomelib$getChromosomes(), carrierB.chromosomelib$getChromosomes(),
				child.getRandom()
		);
		Breeders.mutate(childChromosomes, child.getRandom());
		carrierChild.chromosomelib$resetTraits();
		carrierChild.chromosomelib$setChromosomes(childChromosomes);

		return true;
	}

	private CLCommonEvents() {
	}
}
