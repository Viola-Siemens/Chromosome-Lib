package com.hexagram2021.chromosomelib.common.sex;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;

/**
 * The biological sex of an entity.
 *
 * @author liudongyu
 */
public enum BiologicalSex implements StringRepresentable {
	ASEXUAL,
	MALE,
	FEMALE;

	/**
	 * Codec for serializing and deserializing biological sex.
	 */
	public static final Codec<BiologicalSex> CODEC = StringRepresentable.fromEnum(BiologicalSex::values);

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	/**
	 * Returns the {@link BiologicalSex} corresponding to the given byte ID, or {@code null} if the ID is out of range.
	 *
	 * @param id The byte ID matching the enum's ordinal.
	 * @return The corresponding {@link BiologicalSex}, or {@code null} if the ID is invalid.
	 */
	@Nullable
	public static BiologicalSex fromId(byte id) {
		BiologicalSex[] values = values();
		if(values.length <= id || id < 0) {
			return null;
		}
		return values[id];
	}

	/**
	 * Returns the byte ID of this biological sex, equal to its ordinal.
	 *
	 * @return The byte representation of this enum value.
	 */
	public byte getId() {
		return (byte) this.ordinal();
	}
}
