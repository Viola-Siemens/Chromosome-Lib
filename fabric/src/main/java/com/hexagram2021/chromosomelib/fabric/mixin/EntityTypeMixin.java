package com.hexagram2021.chromosomelib.fabric.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to {@link EntityType} for holding chromosome and trait types. <br/>
 *
 * @author liudongyu
 */
@SuppressWarnings({"java:S100", "java:S116", "NotNullFieldNotInitialized"})
@Mixin(EntityType.class)
public class EntityTypeMixin implements IChromosomeLibEntityType {
	@Unique
	private Int2ObjectMap<Holder<Chromosome>> chromosomelib$chromosomes;
	@Unique
	private Object2IntMap<Holder<Chromosome>> chromosomelib$invertChromosomes;
	@Unique
	private HolderSet<TraitType> chromosomelib$traitTypes;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void chromosomelib$initChromosomes(CallbackInfo ci) {
		this.chromosomelib$chromosomes = Int2ObjectMaps.emptyMap();
		this.chromosomelib$invertChromosomes = Object2IntMaps.emptyMap();
		this.chromosomelib$traitTypes = HolderSet.direct();
	}

	@Override
	public Int2ObjectMap<Holder<Chromosome>> chromosomelib$getChromosomes() {
		return this.chromosomelib$chromosomes;
	}

	@Override
	public int chromosomelib$getChromosomeIndex(Holder<Chromosome> chromosome) {
		return this.chromosomelib$invertChromosomes.getInt(chromosome);
	}

	@Override
	public void chromosomelib$setChromosomes(Int2ObjectMap<Holder<Chromosome>> chromosomes) {
		this.chromosomelib$chromosomes = chromosomes;
		this.chromosomelib$invertChromosomes = AbstractRegisterEntry.newHolderObject2IntTreeMap();
		chromosomes.forEach((index, chromosome) -> this.chromosomelib$invertChromosomes.put(chromosome, index.intValue()));
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
