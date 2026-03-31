package com.hexagram2021.chromosomelib.common.gene_locus;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Represents a gene locus (position) on a chromosome. <br/>
 * A gene locus can be left-only, right-only, or homologous (present on both sides).
 *
 * @author liudongyu
 */
public abstract class GeneLocus {
	/**
	 * The gene frequency of each locus.
	 */
	private static Map<Holder<GeneLocus>, IWeightedGeneList> geneFrequency = Map.of();

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
	 * Gets the index of this gene locus on the specified chromosome type.
	 *
	 * @param chromosomeType left or right.
	 * @return The index of this locus, or -1 if not present on this chromosome type
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

	/**
	 * Sets the gene frequency map for all gene loci.
	 *
	 * @param geneFrequency The gene frequency map
	 */
	@ApiStatus.Internal
	public static void setGeneFrequency(Map<Holder<GeneLocus>, IWeightedGeneList> geneFrequency) {
		GeneLocus.geneFrequency = geneFrequency;
	}

	/**
	 * Get a random gene from the gene locus.
	 * @param geneLocus	the gene locus.
	 * @param context The weighted gene list context for random selection
	 * @return a random gene from the gene locus.
	 */
	public static Holder<Gene> getRandomGene(Holder<GeneLocus> geneLocus, IWeightedGeneList.Context context) {
		return geneFrequency.get(geneLocus).getRandomGene(context);
	}

	/**
	 * Represents a gene locus only present on the left chromosome (e.g., X or Z sex chromosome).
	 *
	 * @author liudongyu
	 */
	public static class LeftGeneLocus extends GeneLocus {
		/**
		 * The index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "left", index) is unique.
		 */
		private final int index;

		/**
		 * Constructs a left-only gene locus.
		 *
		 * @param index The index of this locus in the left chromosome
		 * @param possibilityOfMutation The possibility of this locus to mutate
		 * @param possibilityOfCrossingOver The possibility of this locus to cross over
		 */
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

	/**
	 * Represents a gene locus only present on the right chromosome (e.g., Y or W sex chromosome).
	 *
	 * @author liudongyu
	 */
	public static class RightGeneLocus extends GeneLocus {
		/**
		 * The index of this locus in the chromosome.
		 * Please make sure that tuple (chromosome, "right", index) is unique.
		 */
		private final int index;

		/**
		 * Constructs a right-only gene locus.
		 *
		 * @param index The index of this locus in the right chromosome
		 * @param possibilityOfMutation The possibility of this locus to mutate
		 * @param possibilityOfCrossingOver The possibility of this locus to cross over
		 */
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

	/**
	 * Represents a gene locus present on both chromosomes (homologous). <br/>
	 * Can be at the same position (autosomes) or different positions (homologous segments of sex chromosomes).
	 *
	 * @author liudongyu
	 */
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

		/**
		 * Constructs a homologous gene locus at the same position on both chromosomes.
		 *
		 * @param index The index of this locus on both chromosomes
		 * @param possibilityOfMutation The possibility of this locus to mutate
		 * @param possibilityOfCrossingOver The possibility of this locus to cross over
		 */
		public HomologousGeneLocus(int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			this(index, index, possibilityOfMutation, possibilityOfCrossingOver);
		}

		/**
		 * Constructs a homologous gene locus at different positions on left and right chromosomes.
		 *
		 * @param leftIndex The index of this locus on the left chromosome
		 * @param rightIndex The index of this locus on the right chromosome
		 * @param possibilityOfMutation The possibility of this locus to mutate
		 * @param possibilityOfCrossingOver The possibility of this locus to cross over
		 */
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

		/**
		 * Gets the left index of this locus.
		 *
		 * @return The left chromosome index
		 */
		public int leftIndex() {
			return this.leftIndex;
		}

		/**
		 * Gets the right index of this locus.
		 *
		 * @return The right chromosome index
		 */
		public int rightIndex() {
			return this.rightIndex;
		}
	}
}
