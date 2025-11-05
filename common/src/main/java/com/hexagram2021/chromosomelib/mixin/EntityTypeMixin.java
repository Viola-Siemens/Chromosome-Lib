package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings({"java:S116", "NotNullFieldNotInitialized"})
@Mixin(EntityType.class)
public class EntityTypeMixin implements IChromosomeLibEntityType {
	@Unique
	private Int2ObjectMap<Holder<Chromosome>> chromosomelib$chromosomes;
	@Unique
	private HolderSet<TraitType> chromosomelib$traitTypes;

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
