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

/**
 * Mixin to {@link Entity} for handling chromosome data persistence. <br/>
 * Injects into entity loading and saving methods to serialize/deserialize chromosome data.
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
@Mixin(Entity.class)
public class EntityMixin {
	/**
	 * Loads chromosome data from NBT when entity is loaded from disk. <br/>
	 * Injected after {@link Entity#readAdditionalSaveData} to restore chromosome instances.
	 *
	 * @param nbt the compound tag containing entity data
	 * @param ci callback info (unused)
	 */
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

	/**
	 * Saves chromosome data to NBT when entity is saved to disk. <br/>
	 * Injected after {@link Entity#addAdditionalSaveData} to persist chromosome instances.
	 *
	 * @param nbt the compound tag to write data into
	 * @param cir callback info returnable (unused)
	 */
	@Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER))
	private void chromosomelib$saveChromosomes(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
		if(this instanceof IChromosomeCarrier carrier) {
			RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, ((Entity)(Object)this).level().registryAccess());
			nbt.put(ChromosomeLib.CHROMOSOMES_TAG, ChromosomeInstance.LIST_CODEC.encodeStart(ops, carrier.chromosomelib$getChromosomes()).getOrThrow(false, CLLogger::error));
		}
	}
}
