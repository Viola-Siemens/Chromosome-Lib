package com.hexagram2021.chromosomelib.event;

import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Fired after assigning traits to a living entity.
 *
 * @author liudongyu
 */
public interface AfterAssigningTraitSolver {
	/**
	 * Called after assigning traits to a living entity.
	 *
	 * @param livingEntity	the living entity
	 * @param map			the trait map
	 * @param hasTrait		whether the living entity has the trait
	 */
	void solveAfterAssigningTrait(LivingEntity livingEntity, Map<Holder<TraitType>, Holder<Trait>> map, Predicate<Holder<Trait>> hasTrait);
}
