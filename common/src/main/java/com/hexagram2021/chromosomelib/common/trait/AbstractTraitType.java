package com.hexagram2021.chromosomelib.common.trait;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

/**
 * Abstract trait type.
 *
 * @author liudongyu
 */
public abstract class AbstractTraitType implements TraitType {
	/**
	 * The name of this trait type.
	 */
	private final ResourceLocation name;
	/**
	 * All traits bound to this trait type.
	 */
	private HolderSet<Trait> values = HolderSet.direct();

	/**
	 * Constructor.
	 * @param name	the name of this trait type
	 */
	protected AbstractTraitType(ResourceLocation name) {
		this.name = name;
	}

	/**
	 * @return the name of this trait type
	 */
	@Override
	public String toString() {
		return this.name.toString();
	}

	/**
	 * All traits bound to this trait type.
	 * @return a collection of traits
	 */
	@Override
	public HolderSet<Trait> values() {
		return this.values;
	}

	/**
	 * Set all traits bound to this trait type.
	 * @param values	a collection of traits
	 */
	@ApiStatus.Internal
	@Override
	public void setValues(HolderSet<Trait> values) {
		this.values = values;
	}
}
