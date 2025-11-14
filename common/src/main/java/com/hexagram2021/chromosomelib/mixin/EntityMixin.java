package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("java:S100")
@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER))
	private void chromosomelib$loadChromosomes(CompoundTag nbt, CallbackInfo ci) {
		if(this instanceof IChromosomeCarrier carrier && nbt.contains(ChromosomeLib.CHROMOSOMES_TAG, Tag.TAG_LIST)) {
			RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, ((Entity)(Object)this).level().registryAccess());
			carrier.chromosomelib$setChromosomes(
					ChromosomeInstance.LIST_CODEC
							.parse(ops, nbt.getList(ChromosomeLib.CHROMOSOMES_TAG, Tag.TAG_COMPOUND))
							.getOrThrow(false, CLLogger::error)
			);
		}
	}

	@Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER))
	private void chromosomelib$saveChromosomes(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
		if(this instanceof IChromosomeCarrier carrier) {
			RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, ((Entity)(Object)this).level().registryAccess());
			nbt.put(ChromosomeLib.CHROMOSOMES_TAG, ChromosomeInstance.LIST_CODEC.encodeStart(ops, carrier.chromosomelib$getChromosomes()).getOrThrow(false, CLLogger::error));
		}
	}
}
