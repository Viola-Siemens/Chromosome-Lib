package com.hexagram2021.chromosomelib.common.trait;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Contract;

import java.util.Map;
import java.util.function.ToIntFunction;

/**
 * Handles trait determination based on active gene weights. <br/>
 * Each trait type should have a handler that determines which specific trait to express.
 *
 * @author liudongyu
 */
public interface TraitHandler {
	/**
	 * Map of trait type to trait handler.
	 */
	Map<Holder<TraitType>, TraitHandler> HANDLERS = AbstractRegisterEntry.newHolderTreeMap();

	/**
	 * Registers a trait handler for the specified trait type.
	 *
	 * @param type The trait type
	 * @param handler The trait handler
	 */
	static void registerHandler(Holder<TraitType> type, TraitHandler handler) {
		HANDLERS.put(type, handler);
	}

	/**
	 * Gets the trait handler for the specified trait type.
	 *
	 * @param type The trait type
	 * @return The trait handler
	 */
	static TraitHandler getHandler(Holder<TraitType> type) {
		return HANDLERS.get(type);
	}

	/**
	 * Determines a trait of the given trait type, based on active genes.
	 *
	 * @param activeGeneWeight	returns a weight of the given gene.
	 *                          <p>For diploids, 0 is for inactive gene, 1 or 2 means the count of the given gene.
	 *                          <p>Notice that autosomal, completely recessive genes will always return 0 or 2. For incompletely dominant genes, you can code different logics based on whether the return value is 1 or 2
	 * @return a trait of given trait type, determined by active genes
	 */
	@Contract(pure = true)
	Holder<Trait> handle(ToIntFunction<Holder<Gene>> activeGeneWeight);
}
