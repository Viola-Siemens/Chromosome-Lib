package com.hexagram2021.chromosomelib.common.trait;

import net.minecraft.core.Holder;

/**
 * Represents a trait (phenotype) that can be expressed by genes.
 *
 * @author liudongyu
 */
public interface Trait {
	/**
	 * Gets the type of this trait.
	 *
	 * @return The trait type holder
	 */
	Holder<TraitType> getType();
}
