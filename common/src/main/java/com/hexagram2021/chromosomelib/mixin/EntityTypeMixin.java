package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"java:S100", "java:S116", "NotNullFieldNotInitialized"})
@Mixin(EntityType.class)
public class EntityTypeMixin implements IChromosomeLibEntityType {
	@Unique
	private Int2ObjectMap<Holder<Chromosome>> chromosomelib$chromosomes = Int2ObjectMaps.emptyMap();
	@Unique
	private HolderSet<TraitType> chromosomelib$traitTypes;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void chromosomelib$initChromosomes(CallbackInfo ci) {
		this.chromosomelib$chromosomes = Int2ObjectMaps.emptyMap();
		this.chromosomelib$traitTypes = HolderSet.direct();
	}

	@Override
	public Int2ObjectMap<Holder<Chromosome>> chromosomelib$getChromosomes() {
		return this.chromosomelib$chromosomes;
	}

	@Override
	public void chromosomelib$setChromosomes(Int2ObjectMap<Holder<Chromosome>> chromosomes) {
		this.chromosomelib$chromosomes = chromosomes;
	}

	@Override
	public HolderSet<TraitType> chromosomelib$getTraitTypes() {
		return this.chromosomelib$traitTypes;
	}

	@Override
	public void chromosomelib$setTraitTypes(HolderSet<TraitType> traits) {
		this.chromosomelib$traitTypes = traits;
	}
}
