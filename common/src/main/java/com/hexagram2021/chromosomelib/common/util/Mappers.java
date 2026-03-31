package com.hexagram2021.chromosomelib.common.util;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;

import java.util.Collection;

/**
 * Utility class for mapping chromosome instances to gene expressions.
 *
 * @author liudongyu
 */
public final class Mappers {
	/**
	 * Convert chromosome instances to gene expressions.
	 *
	 * @param chromosomeInstances	chromosomes to be converted
	 * @return a map of genes to weights from the given chromosomes
	 */
	public static Object2IntMap<Holder<Gene>> convertChromosomeInstancesToExpressingGenes(Collection<ChromosomeInstance> chromosomeInstances) {
		Object2IntMap<Holder<Gene>> ret = AbstractRegisterEntry.newHolderObject2IntTreeMap();
		chromosomeInstances.forEach(chromosomeInstance -> chromosomeInstance.express(ret));
		return ret;
	}

	private Mappers() {
	}
}
