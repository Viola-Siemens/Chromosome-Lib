package com.hexagram2021.chromosomelib.common.gene_locus;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import net.minecraft.core.Holder;

public class GeneLocusInstance {
	private final Holder<Gene> gene;

	public GeneLocusInstance(Holder<Gene> gene) {
		this.gene = gene;
	}
}
