package com.hexagram2021.chromosomelib.common.entity.type;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

/**
 * Interface for entity types that support the chromosome system. <br/>
 * Stores chromosome definitions and trait types for the entity type.
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
public interface IChromosomeLibEntityType {
	/**
	 * Gets the chromosomes associated with this entity type.
	 *
	 * @return Map of chromosome index to chromosome holder
	 */
	Int2ObjectMap<Holder<Chromosome>> chromosomelib$getChromosomes();
	/**
	 * Gets the index of a specific chromosome for this entity type.
	 *
	 * @param chromosome The chromosome holder
	 * @return The chromosome index
	 */
	int chromosomelib$getChromosomeIndex(Holder<Chromosome> chromosome);
	/**
	 * Sets the chromosomes associated with this entity type.
	 *
	 * @param chromosomes Map of chromosome index to chromosome holder
	 */
	void chromosomelib$setChromosomes(Int2ObjectMap<Holder<Chromosome>> chromosomes);
	/**
	 * Gets the trait types that can be expressed by this entity type.
	 *
	 * @return Set of trait types
	 */
	HolderSet<TraitType> chromosomelib$getTraitTypes();
	/**
	 * Sets the trait types that can be expressed by this entity type.
	 *
	 * @param traits Set of trait types
	 */
	void chromosomelib$setTraitTypes(HolderSet<TraitType> traits);
}
