package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import net.minecraft.core.Holder;

/**
 * Exception thrown when a gene is placed at an invalid gene locus.
 *
 * @author liudongyu
 */
public class InvalidGeneFromGeneLocusException extends IllegalStateException {
	/**
	 * Constructs a new exception with the invalid gene and locus.
	 *
	 * @param gene The gene that is invalid for the locus
	 * @param geneLocus The gene locus that the gene should not be at
	 */
	public InvalidGeneFromGeneLocusException(Holder<Gene> gene, Holder<GeneLocus> geneLocus) {
		super("Gene %s should not be at GeneLocus %s.".formatted(gene, geneLocus));
	}
}
