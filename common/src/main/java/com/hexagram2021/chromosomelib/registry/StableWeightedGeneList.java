package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import net.minecraft.core.Holder;

/**
 * This list simulates the fact that homozygotes are more common in nature for some species.
 *
 * @author liudongyu
 */
public class StableWeightedGeneList extends SimpleWeightedGeneList {
	/**
	 * The possibility that we don't generate a random gene but directly return the generated one.
	 * <p>Notice that this is NOT the possibility of homozygotes - it should be {@code possibilityOfStable + (1 - possibilityOfStable) * p}, where {@code p} is the original possibility of choosing two same gene.
	 */
	protected final double possibilityOfStable;

	protected StableWeightedGeneList(double possibilityOfStable, ImmutableList<Entry> entries) {
		super(entries);
		this.possibilityOfStable = possibilityOfStable;
	}

	@Override
	public Holder<Gene> getRandomGene(Context context) {
		Holder<Gene> gene = context.last();
		if(gene != null && context.random().nextDouble() < this.possibilityOfStable) {
			return gene;
		}
		return super.getRandomGene(context);
	}

	/**
	 * Create a builder for StableWeightedGeneList.
	 *
	 * @return a builder
	 */
	public static Builder stableBuilder() {
		return new Builder();
	}

	/**
	 * Builder for StableWeightedGeneList.
	 *
	 * @author liudongyu
	 */
	public static class Builder extends IWeightedGeneList.Builder {
		/**
		 * Possibility for stable gene selection (defaults to 0.25).
		 */
		private double possibilityOfStable = 0.25D;

		@Override
		StableWeightedGeneList build() {
			return new StableWeightedGeneList(this.possibilityOfStable, this.shadowed.build());
		}

		/**
		 * Sets the possibility of stable gene selection.
		 *
		 * @param possibilityOfStable The possibility value
		 * @return This builder
		 */
		public Builder possibilityOfStable(double possibilityOfStable) {
			this.possibilityOfStable = possibilityOfStable;
			return this;
		}
	}
}
