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

/**
 * Mixin to {@link Mob} for handling chromosome initialization during spawn finalization. <br/>
 * Ensures that mobs spawned in the world (not loaded from disk) receive proper chromosomes and traits
 * based on spawn location and context.
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
@Mixin(Mob.class)
public abstract class MobEntityMixin implements IChromosomeCarrier {
	/**
	 * Finalizes chromosome assignment when a mob spawns into the world. <br/>
	 * Builds location-based chromosomes for newly spawned mobs, or assigns traits to loaded mobs.
	 *
	 * @param level the server level accessor
	 * @param difficulty the difficulty instance at spawn location
	 * @param reason the spawn type (natural, spawner, etc.)
	 * @param spawnData the spawn group data
	 * @param dataTag additional data tag (may be null)
	 * @param cir callback info returnable (unused)
	 */
	@Inject(method = "finalizeSpawn", at = @At(value = "TAIL"))
	private void chromosomelib$finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData,
											 CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
		Mob current = (Mob)(Object)this;
		Collection<ChromosomeInstance> chromosomes = this.chromosomelib$getChromosomes();
		if(chromosomes.isEmpty()) {
			this.chromosomelib$resetTraits();
			this.chromosomelib$setChromosomes(this.chromosomelib$buildDefaultChromosomes(
					(IChromosomeLibEntityType) current.getType(),
					IWeightedGeneList.Context.of(level, current.blockPosition(), current.getRandom())
			));
		} else if(!this.chromosomelib$isTraitsSolved()) {
			this.chromosomelib$assignTraits();
		}
	}
}
