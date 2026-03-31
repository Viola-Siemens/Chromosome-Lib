package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import net.minecraft.core.Holder;

import java.util.Collection;

/**
 * Exception thrown when gene frequency data is not present for a gene locus.
 *
 * @author liudongyu
 */
public class GeneFrequencyNotPresentException extends IllegalStateException {
	/**
	 * Constructs a new exception with the missing genes and gene locus.
	 *
	 * @param genes The genes that are missing frequency data
	 * @param geneLocus The gene locus that should contain the frequency data
	 */
	public GeneFrequencyNotPresentException(Collection<Holder<Gene>> genes, Holder<GeneLocus> geneLocus) {
		super("Gene(s) %s of GeneLocus %s may not be registered to gene frequency map.".formatted(genes, geneLocus));
	}
}
