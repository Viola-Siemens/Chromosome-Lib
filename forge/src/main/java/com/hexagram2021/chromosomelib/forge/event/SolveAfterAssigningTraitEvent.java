package com.hexagram2021.chromosomelib.forge.event;

import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.event.AfterAssigningTraitSolver;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Fired after assigning traits to a living entity.
 *
 * @author liudongyu
 */
public class SolveAfterAssigningTraitEvent extends LivingEvent {
	private final Map<Holder<TraitType>, Holder<Trait>> map;
	private final Predicate<Holder<Trait>> hasTrait;

	/**
	 * Constructor.
	 * @param livingEntity	The living entity.
	 * @param map			Traits to assign.
	 * @param hasTrait		A predicate to check whether the trait is assigned to the living entity.
	 */
	public SolveAfterAssigningTraitEvent(LivingEntity livingEntity, Map<Holder<TraitType>, Holder<Trait>> map, Predicate<Holder<Trait>> hasTrait) {
		super(livingEntity);
		this.map = map;
		this.hasTrait = hasTrait;
	}

	/**
	 * Modify the living entity's data on your own.
	 * @param solver	The solver.
	 */
	public void accept(AfterAssigningTraitSolver solver) {
		solver.solveAfterAssigningTrait(this.getEntity(), this.map, this.hasTrait);
	}
}
