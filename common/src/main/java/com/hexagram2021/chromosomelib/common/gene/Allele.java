package com.hexagram2021.chromosomelib.common.gene;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;

public record Allele(Holder<Gene> gene1, Holder<Gene> gene2) implements Pair<Holder<Gene>, Holder<Gene>> {
	public Allele(Holder<Gene> gene1, Holder<Gene> gene2) {
		// Sort the genes
		if(Gene.COMPARATOR.compare(gene1, gene2) > 0) {
			this.gene1 = gene2;
			this.gene2 = gene1;
		} else {
			this.gene1 = gene1;
			this.gene2 = gene2;
		}
	}

	@Override
	public Holder<Gene> left() {
		return this.gene1;
	}

	@Override
	public Holder<Gene> right() {
		return this.gene2;
	}

	public void express(ImmutableList.Builder<Gene> builder) {
	}
}
