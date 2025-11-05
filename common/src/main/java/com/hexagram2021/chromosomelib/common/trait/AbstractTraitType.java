package com.hexagram2021.chromosomelib.common.trait;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractTraitType implements TraitType {
	private final ResourceLocation name;
	private HolderSet<Trait> values = HolderSet.direct();

	protected AbstractTraitType(ResourceLocation name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name.toString();
	}

	@Override
	public HolderSet<Trait> values() {
		return this.values;
	}

	@Override
	public void setValues(HolderSet<Trait> values) {
		this.values = values;
	}
}
