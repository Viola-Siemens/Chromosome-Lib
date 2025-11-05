package com.hexagram2021.chromosomelib.common.util.exception;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import net.minecraft.core.Holder;

import java.util.Collection;

public class GeneFrequencyNotPresentException extends IllegalStateException {
	public GeneFrequencyNotPresentException(Collection<Holder<Gene>> genes, Holder<GeneLocus> geneLocus) {
		super("Gene(s) %s of GeneLocus %s may not be registered to gene frequency map.".formatted(genes, geneLocus));
	}
}
