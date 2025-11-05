package com.hexagram2021.chromosomelib.common.gene_locus;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.WeightedGeneList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class GeneLocus {
	/**
	 * The gene frequency of each locus.
	 */
	private static Map<Holder<GeneLocus>, WeightedGeneList> geneFrequency = Map.of();

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
	 * The possibility of this locus to mutate.
	 */
	public final double possibilityOfMutation;
	/**
	 * The possibility of this locus to cross over.
	 */
	public final double possibilityOfCrossingOver;

	protected GeneLocus(double possibilityOfMutation, double possibilityOfCrossingOver) {
		this.possibilityOfMutation = possibilityOfMutation;
		this.possibilityOfCrossingOver = possibilityOfCrossingOver;
	}

	/**
	 * @param chromosomeType	left or right.
	 */
	public abstract int index(ChromosomeType chromosomeType);

	/**
	 * Create a gene locus only in the left chromosome (e.g. X sex chromosome, Z sex chromosome).
	 * @param index						the index of this locus in the chromosome.
	 * @param possibilityOfMutation		the possibility of this locus to mutate.
	 * @param possibilityOfCrossingOver	the possibility of this locus to cross over.
	 * @return the gene locus.
	 */
	public static LeftGeneLocus left(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
		return new LeftGeneLocus(index, possibilityOfMutation, possibilityOfCrossingOver);
	}

	/**
	 * Create a gene locus only in the right chromosome (e.g. Y sex chromosome, W sex chromosome).
	 * @param index						the index of this locus in the chromosome.
	 * @param possibilityOfMutation		the possibility of this locus to mutate.
	 * @param possibilityOfCrossingOver	the possibility of this locus to cross over.
	 * @return the gene locus.
	 */
	public static RightGeneLocus right(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
		return new RightGeneLocus(index, possibilityOfMutation, possibilityOfCrossingOver);
	}

	/**
	 * Create a gene locus that is homologous to both chromosomes (e.g. most autosomes).
	 * @param index						the index of this locus in the chromosome.
	 * @param possibilityOfMutation		the possibility of this locus to mutate.
	 * @param possibilityOfCrossingOver	the possibility of this locus to cross over.
	 * @return the gene locus.
	 */
	public static HomologousGeneLocus homologous(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
		return new HomologousGeneLocus(index, possibilityOfMutation, possibilityOfCrossingOver);
	}

	/**
	 * Create a gene locus that is homologous to both chromosomes, but is at different position of them (e.g. homologous segments of sex chromosomes).
	 * @param leftIndex					the left index of this locus in the chromosome.
	 * @param rightIndex				the right index of this locus in the chromosome.
	 * @param possibilityOfMutation		the possibility of this locus to mutate.
	 * @param possibilityOfCrossingOver	the possibility of this locus to cross over.
	 * @return the gene locus.
	 */
	public static HomologousGeneLocus homologous(int leftIndex, int rightIndex, double possibilityOfMutation, double possibilityOfCrossingOver) {
		return new HomologousGeneLocus(leftIndex, rightIndex, possibilityOfMutation, possibilityOfCrossingOver);
	}

	@ApiStatus.Internal
	public static void setGeneFrequency(Map<Holder<GeneLocus>, WeightedGeneList> geneFrequency) {
		GeneLocus.geneFrequency = geneFrequency;
	}

	/**
	 * Get a random gene from the gene locus.
	 * @param geneLocus	the gene locus.
	 * @return a random gene from the gene locus.
	 */
	public static Holder<Gene> getRandomGene(Holder<GeneLocus> geneLocus, RandomSource random) {
		return geneFrequency.get(geneLocus).getRandomGene(random);
	}

	public static class LeftGeneLocus extends GeneLocus {
		/**
		 * The index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "left", index) is unique.
		 */
		private final int index;

		public LeftGeneLocus(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			super(possibilityOfMutation, possibilityOfCrossingOver);
			if(index <= 0 || index > 0xffffff) {
				throw new IllegalArgumentException("Illegal index: " + index);
			}
			this.index = index;
		}

		@Override
		public int index(ChromosomeType chromosomeType) {
			return chromosomeType == ChromosomeType.LEFT ? this.index : -1;
		}
	}

	public static class RightGeneLocus extends GeneLocus {
		/**
		 * The index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "right", index) is unique.
		 */
		private final int index;

		public RightGeneLocus(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			super(possibilityOfMutation, possibilityOfCrossingOver);
			if(index <= 0 || index > 0xffffff) {
				throw new IllegalArgumentException("Illegal index: " + index);
			}
			this.index = index;
		}

		@Override
		public int index(ChromosomeType chromosomeType) {
			return chromosomeType == ChromosomeType.RIGHT ? this.index : -1;
		}
	}

	public static class HomologousGeneLocus extends GeneLocus {
		/**
		 * The left index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "left", leftIndex) is unique.
		 */
		private final int leftIndex;

		/**
		 * The right index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "right", rightIndex) is unique.
		 */
		private final int rightIndex;

		public HomologousGeneLocus(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			this(index, index, possibilityOfMutation, possibilityOfCrossingOver);
		}

		public HomologousGeneLocus(int leftIndex, int rightIndex, double possibilityOfMutation, double possibilityOfCrossingOver) {
			super(possibilityOfMutation, possibilityOfCrossingOver);
			if(leftIndex <= 0 || leftIndex > 0xffffff) {
				throw new IllegalArgumentException("Illegal left index: " + leftIndex);
			}
			if(rightIndex <= 0 || rightIndex > 0xffffff) {
				throw new IllegalArgumentException("Illegal right index: " + rightIndex);
			}
			this.leftIndex = leftIndex;
			this.rightIndex = rightIndex;
		}

		@Override
		public int index(ChromosomeType chromosomeType) {
			return switch (chromosomeType) {
				case LEFT -> this.leftIndex;
				case RIGHT -> this.rightIndex;
			};
		}

		public int leftIndex() {
			return this.leftIndex;
		}

		public int rightIndex() {
			return this.rightIndex;
		}
	}
}
