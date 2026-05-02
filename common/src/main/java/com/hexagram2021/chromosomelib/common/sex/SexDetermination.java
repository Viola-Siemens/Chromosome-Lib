package com.hexagram2021.chromosomelib.common.sex;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;

/**
 * The sex determination system for a species using sex chromosomes.
 * Determines how {@link ChromosomeType} (LEFT / RIGHT) maps to {@link BiologicalSex}.
 *
 * <p>Note: Hymenoptera (bees) use haplodiploidy and do NOT use this enum.
 * Their sex is stored directly via the {@code IHymenoptera} interface.</p>
 *
 * @author liudongyu
 */
public enum SexDetermination {
	/**
	 * Mammalian system: LEFT+LEFT = FEMALE (XX), LEFT+RIGHT = MALE (XY).
	 * Sex is determined by the father (Y chromosome provider).
	 */
	XY,
	/**
	 * Avian system: LEFT+LEFT = MALE (ZZ), LEFT+RIGHT = FEMALE (ZW).
	 * Sex is determined by the mother (W chromosome provider).
	 */
	ZW;

	/**
	 * Resolves the biological sex from counts of LEFT- and RIGHT-type sex chromosome instances.
	 *
	 * @param left  count of LEFT-type sex chromosome instances
	 * @param right count of RIGHT-type sex chromosome instances
	 * @return the resolved biological sex
	 */
	public BiologicalSex resolve(int left, int right) {
		return switch (this) {
			case XY -> (right > 0) ? BiologicalSex.MALE : BiologicalSex.FEMALE;
			case ZW -> (right > 0) ? BiologicalSex.FEMALE : BiologicalSex.MALE;
		};
	}
}