package com.hexagram2021.chromosomelib.common.chromosome;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ChromosomeType implements StringRepresentable {
	// only in X or Z
	LEFT,
	// only in Y or W
	RIGHT;

	public static final Codec<ChromosomeType> CODEC = StringRepresentable.fromEnum(ChromosomeType::values);

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase(Locale.ROOT);
	}
}
