package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.CLCommonEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@SuppressWarnings({"java:S100", "OptionalUsedAsFieldOrParameterType"})
@Mixin(VillagerMakeLove.class)
public class VillagerMakeLoveMixin {
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
