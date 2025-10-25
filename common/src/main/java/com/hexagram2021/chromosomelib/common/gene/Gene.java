package com.hexagram2021.chromosomelib.common.gene;

import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class Gene {
	public static final Comparator<Holder<Gene>> COMPARATOR = Comparator
			.<Holder<Gene>>comparingInt(holder -> holder.value().topologicalOrder)
			.thenComparingInt(holder -> holder.value().code().hashCode());

	/**
	 * The locus of this gene.
	 */
	@ApiStatus.Internal
	@Nullable
	public Holder<GeneLocus> geneLocus = null;

	/**
	 * @see com.hexagram2021.chromosomelib.registry.RegistryRelations#buildGeneTopologicalOrder
	 */
	@ApiStatus.Internal
	public int topologicalOrder = -1;

	private final String code;

	public Gene(String code) {
		this.code = code;
	}

	public String code() {
		return this.code;
	}
}
