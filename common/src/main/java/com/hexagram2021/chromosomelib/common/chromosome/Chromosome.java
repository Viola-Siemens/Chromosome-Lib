package com.hexagram2021.chromosomelib.common.chromosome;

import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class Chromosome {
	/**
	 * The gene loci of this chromosome.
	 */
	@ApiStatus.Internal
	@Nullable
	public Int2ObjectMap<Holder<GeneLocus>> geneLoci = null;

	private final int index;

	public Chromosome(int index) {
		this.index = index;
	}

	public int index() {
		return this.index;
	}
}
