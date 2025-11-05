package com.hexagram2021.chromosomelib.common.trait;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Contract;

import java.util.Map;
import java.util.function.ToIntFunction;

public interface TraitHandler {
	Map<Holder<TraitType>, TraitHandler> HANDLERS = AbstractRegisterEntry.newHolderTreeMap();

	static void registerHandler(Holder<TraitType> type, TraitHandler handler) {
		HANDLERS.put(type, handler);
	}

	static TraitHandler getHandler(Holder<TraitType> type) {
		return HANDLERS.get(type);
	}

	/**
	 *
	 * @param activeGeneWeight	returns a weight of the given gene.
	 *                          <p>For diploids, 0 is for inactive gene, 1 or 2 means the count of the given gene.
	 *                          <p>Notice that completely recessive genes will always return 0 or 2. For incompletely dominant genes, you can code different logics based on whether the return value is 1 or 2
	 * @return a trait of given trait type, determined by active genes
	 */
	@Contract(pure = true)
	Holder<Trait> handle(ToIntFunction<Holder<Gene>> activeGeneWeight);
}
