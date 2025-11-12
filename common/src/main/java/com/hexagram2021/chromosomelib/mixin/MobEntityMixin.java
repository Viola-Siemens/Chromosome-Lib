package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@SuppressWarnings("java:S100")
@Mixin(Mob.class)
public abstract class MobEntityMixin implements IChromosomeCarrier {
	@Inject(method = "finalizeSpawn", at = @At(value = "TAIL"))
	private void chromosomelib$finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData,
											 CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
		Mob current = (Mob)(Object)this;
		Collection<ChromosomeInstance> chromosomes = this.chromosomelib$getChromosomes();
		if(chromosomes.isEmpty()) {
			this.chromosomelib$setChromosomes(this.chromosomelib$buildDefaultChromosomes(
					(IChromosomeLibEntityType) current.getType(),
					IWeightedGeneList.Context.of(level, current.blockPosition(), current.getRandom())
			));
		}
	}
}
