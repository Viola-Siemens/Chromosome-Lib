package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;

public class UnpairedChromosomeException extends IllegalArgumentException {
	public UnpairedChromosomeException(EntityType<?> entityType, String stage, Holder<Chromosome> chromosome, Collection<ChromosomeInstance> chromosomeInstances) {
		super("Unpaired chromosome of entity %s was found while trying to %s. Chromosome: %s, instances: %s".formatted(
				BuiltInRegistries.ENTITY_TYPE.getKey(entityType),
				stage,
				chromosome,
				chromosomeInstances
		));
	}
}
