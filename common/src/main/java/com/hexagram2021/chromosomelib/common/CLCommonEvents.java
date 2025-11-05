package com.hexagram2021.chromosomelib.common;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.util.Breeders;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Collection;

public final class CLCommonEvents {
	/**
	 * Called when two entities is breeding.
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
		carrierChild.chromosomelib$setChromosomes(childChromosomes);

		return true;
	}

	private CLCommonEvents() {
	}
}
