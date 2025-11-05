package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import net.minecraft.core.Holder;

public class InvalidGeneFromGeneLocusException extends IllegalStateException {
	public InvalidGeneFromGeneLocusException(Holder<Gene> gene, Holder<GeneLocus> geneLocus) {
		super("Gene %s should not be at GeneLocus %s.".formatted(gene, geneLocus));
	}
}
