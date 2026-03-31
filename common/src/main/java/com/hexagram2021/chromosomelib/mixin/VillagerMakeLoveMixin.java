package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.CLCommonEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * Mixin to {@link VillagerMakeLove} for integrating chromosome inheritance into villager breeding. <br/>
 * Modifies the breed behavior to trigger genetic recombination events.
 *
 * @author liudongyu
 */
@SuppressWarnings({"java:S100", "OptionalUsedAsFieldOrParameterType"})
@Mixin(VillagerMakeLove.class)
public class VillagerMakeLoveMixin {
	/**
	 * Modifies villager breeding to trigger chromosome inheritance event. <br/>
	 * Called after vanilla breeding logic to allow genetic system to process the offspring.
	 *
	 * @param original the optional offspring villager from vanilla breeding
	 * @param parentA the first parent villager
	 * @param parentB the second parent villager
	 * @return the offspring if breeding succeeds, empty optional otherwise
	 */
	@ModifyReturnValue(method = "breed", at = @At(value = "RETURN", ordinal = 1))
	private Optional<Villager> chromosomeLib$afterBreed(Optional<Villager> original,
														@Local(argsOnly = true, ordinal = 0) Villager parentA,
														@Local(argsOnly = true, ordinal = 1) Villager parentB) {
		if(CLCommonEvents.onEntityBreed(parentA, parentB, original.orElse(null))) {
			return original;
		}
		return Optional.empty();
	}
}
