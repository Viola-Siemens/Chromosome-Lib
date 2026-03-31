package com.hexagram2021.chromosomelib.common.chromosome;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

/**
 * Represents the type of chromosome (left or right side). <br/>
 * LEFT corresponds to X or Z chromosomes, RIGHT corresponds to Y or W chromosomes.
 *
 * @author liudongyu
 */
public enum ChromosomeType implements StringRepresentable {
	/**
	 * Only in X or Z chromosome.
	 */
	LEFT,
	/**
	 * Only in Y or W chromosome.
	 */
	RIGHT;

	/**
	 * Codec for serializing and deserializing chromosome types.
	 */
	public static final Codec<ChromosomeType> CODEC = StringRepresentable.fromEnum(ChromosomeType::values);

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase(Locale.ROOT);
	}
}
