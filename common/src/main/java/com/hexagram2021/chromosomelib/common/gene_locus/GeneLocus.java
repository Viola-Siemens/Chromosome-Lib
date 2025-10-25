package com.hexagram2021.chromosomelib.common.gene_locus;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class GeneLocus {
	/**
	 * The chromosome that this locus belongs to.
	 */
	@ApiStatus.Internal
	@Nullable
	public Holder<Chromosome> chromosome = null;
	/**
	 * The allele located at this locus.
	 */
	@ApiStatus.Internal
	@Nullable
	public HolderSet<Gene> genes = null;
	/**
	 * The index of this locus in the chromosome.
	 * Please make sure that (chromosome, index) is unique.
	 */
	private final int index;
	/**
	 * The possibility of this locus to mutate.
	 */
	public final double possibilityOfMutation;
	/**
	 * The possibility of this locus to cross over.
	 */
	public final double possibilityOfCrossingOver;

	public GeneLocus(int index) {
		this(index, 0.0D, 0.0D);
	}

	public GeneLocus(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
		this.index = index;
		this.possibilityOfMutation = possibilityOfMutation;
		this.possibilityOfCrossingOver = possibilityOfCrossingOver;
	}

	public int index() {
		return this.index;
	}
}
