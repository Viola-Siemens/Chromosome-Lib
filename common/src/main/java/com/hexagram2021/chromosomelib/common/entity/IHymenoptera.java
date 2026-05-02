package com.hexagram2021.chromosomelib.common.entity;

import com.hexagram2021.chromosomelib.common.sex.BiologicalSex;

/**
 * Interface for Hymenoptera entities (bees, etc.) whose sex is determined by ploidy
 * (haplodiploidy) rather than sex chromosomes.
 *
 * <p>Males are haploid (ploidy = 1) and females are diploid (ploidy = 2).
 * Biological sex is stored directly in entity data rather than derived from chromosomes.</p>
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
public interface IHymenoptera {
	/**
	 * Returns the biological sex stored directly in this Hymenoptera entity's data.
	 *
	 * @return the biological sex of this entity
	 */
	BiologicalSex chromosomelib$getHymenopteraSex();
}
