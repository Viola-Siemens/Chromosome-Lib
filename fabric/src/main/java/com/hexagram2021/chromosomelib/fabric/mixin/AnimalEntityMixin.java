package com.hexagram2021.chromosomelib.fabric.mixin;

import com.hexagram2021.chromosomelib.common.CLCommonEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

/**
 * Mixin to {@link Animal} for assigning genes and traits to child entity.
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
@Mixin(Animal.class)
public class AnimalEntityMixin {
	@Nullable
	@WrapOperation(method = "spawnChildFromBreeding", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/AgeableMob;"))
	private AgeableMob chromosomelib$onBabyEntitySpawn(Animal current, ServerLevel serverLevel, AgeableMob mate, Operation<AgeableMob> original) {
		AgeableMob child = original.call(current, serverLevel, mate);
		if(!CLCommonEvents.onEntityBreed(current, mate, child)) {
			child = null;
		}
		return child;
	}
}
