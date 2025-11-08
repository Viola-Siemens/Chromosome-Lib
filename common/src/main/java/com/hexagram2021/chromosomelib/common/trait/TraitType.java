package com.hexagram2021.chromosomelib.common.trait;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;

import java.util.Comparator;

/**
 * Trait type.
 */
public interface TraitType {
	/**
	 * Comparator for trait types.
	 */
	Comparator<TraitType> COMPARATOR = Comparator.comparing(TraitType::toString);

	/**
	 * Default trait.
	 * @return a trait bound to this type
	 */
	Holder<Trait> example();

	/**
	 * All traits bound to this type.
	 * @return a collection of traits
	 */
	HolderSet<Trait> values();

	/**
	 * Set the traits bound to this type.
	 * @param values	a collection of traits
	 */
	@ApiStatus.Internal
	void setValues(HolderSet<Trait> values);

	/**
	 * The string representation of this trait type. It is recommended to use the registry name of this trait type.
	 * @return name of this trait type.
	 */
	@Override
	String toString();
}
