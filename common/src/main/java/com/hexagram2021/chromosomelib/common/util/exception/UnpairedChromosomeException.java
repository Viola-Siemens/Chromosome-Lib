package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;

/**
 * Exception thrown when an unpaired chromosome is found during breeding. <br/>
 * This indicates that the entity has an odd number of chromosome instances for a specific chromosome type.
 *
 * @author liudongyu
 */
public class UnpairedChromosomeException extends IllegalArgumentException {
	/**
	 * Constructs a new exception with information about the unpaired chromosome.
	 *
	 * @param entityType The entity type that has the unpaired chromosome
	 * @param stage The breeding stage where the error was detected
	 * @param chromosome The chromosome that is unpaired
	 * @param chromosomeInstances The chromosome instances that were found
	 */
	public UnpairedChromosomeException(EntityType<?> entityType, String stage, Holder<Chromosome> chromosome, Collection<ChromosomeInstance> chromosomeInstances) {
		super("Unpaired chromosome of entity %s was found while trying to %s. Chromosome: %s, instances: %s".formatted(
				BuiltInRegistries.ENTITY_TYPE.getKey(entityType),
				stage,
				chromosome,
				chromosomeInstances
		));
	}
}
