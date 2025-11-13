package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitHandler;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.common.util.Mappers;
import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

@SuppressWarnings({"java:S100", "java:S116", "NotNullFieldNotInitialized"})
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IChromosomeCarrier {
	@Shadow
	public abstract RandomSource getRandom();

	@Unique
	private List<ChromosomeInstance> chromosomelib$chromosomes;
	@Unique
	private Object2IntMap<Holder<Gene>> chromosomelib$activeGenes;
	@Unique
	private Map<Holder<TraitType>, Holder<Trait>> chromosomelib$activeTraits;

	@Unique
	private boolean chromosomelib$isTraitsSolved;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void chromosomelib$initChromosomes(CallbackInfo ci) {
		this.chromosomelib$chromosomes = Lists.newArrayList();
		this.chromosomelib$activeGenes = AbstractRegisterEntry.newHolderObject2IntTreeMap();
		this.chromosomelib$activeTraits = AbstractRegisterEntry.newHolderTreeMap();
		this.chromosomelib$setChromosomes(this.chromosomelib$buildDefaultChromosomes(
				(IChromosomeLibEntityType)((LivingEntity)(Object)this).getType(),
				IWeightedGeneList.Context.of(this.getRandom())
		));
		this.chromosomelib$isTraitsSolved = false;
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "HEAD"))
	private void chromosomelib$saveChromosomes(CompoundTag nbt, CallbackInfo ci) {
		nbt.putBoolean("ChromosomeLibIsTraitsSolved", this.chromosomelib$isTraitsSolved);
		RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, ((LivingEntity)(Object)this).level().registryAccess());
		nbt.put("ChromosomeLibChromosomes", ChromosomeInstance.LIST_CODEC.encodeStart(ops, this.chromosomelib$chromosomes).getOrThrow(false, CLLogger::error));
	}

	@Inject(method = "readAdditionalSaveData", at = @At(value = "HEAD"))
	private void chromosomelib$loadChromosomes(CompoundTag nbt, CallbackInfo ci) {
		this.chromosomelib$isTraitsSolved = nbt.getBoolean("ChromosomeLibIsTraitsSolved");
		if(nbt.contains("ChromosomeLibChromosomes", Tag.TAG_LIST)) {
			RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, ((LivingEntity)(Object)this).level().registryAccess());
			this.chromosomelib$setChromosomes(
					ChromosomeInstance.LIST_CODEC
							.parse(ops, nbt.getList("ChromosomeLibChromosomes", Tag.TAG_COMPOUND))
							.getOrThrow(false, CLLogger::error)
			);
		}
	}

	@Override
	public Collection<ChromosomeInstance> chromosomelib$getChromosomes() {
		return this.chromosomelib$chromosomes;
	}

	@Override
	public void chromosomelib$setChromosomes(Collection<ChromosomeInstance> chromosomes) {
		LivingEntity current = (LivingEntity)(Object)this;
		// setting the collection
		this.chromosomelib$chromosomes.clear();
		this.chromosomelib$chromosomes.addAll(chromosomes);

		//maintaining active genes and traits
		this.chromosomelib$activeGenes.clear();
		this.chromosomelib$activeGenes.putAll(Mappers.convertChromosomeInstancesToExpressingGenes(this.chromosomelib$chromosomes));
		Gene.doDisable(this.chromosomelib$activeGenes);

		this.chromosomelib$activeTraits.clear();
		((IChromosomeLibEntityType)(current).getType()).chromosomelib$getTraitTypes()
				.forEach(traitType -> this.chromosomelib$activeTraits.put(traitType, TraitHandler.getHandler(traitType).handle(this.chromosomelib$activeGenes)));
		if(!current.level().isClientSide && !this.chromosomelib$isTraitsSolved) {
			Services.PLATFORM.solveAfterAssigningTrait(current, this.chromosomelib$activeTraits, this.chromosomelib$activeTraits.values()::contains);
			this.chromosomelib$isTraitsSolved = true;
		}
	}

	@Override
	public ToIntFunction<Holder<Gene>> chromosomelib$getActiveGenes() {
		return this.chromosomelib$activeGenes;
	}

	@Override
	public Collection<Holder<Trait>> chromosomelib$getActiveTraits() {
		return this.chromosomelib$activeTraits.values();
	}

	@Override
	public void chromosomelib$resetTraits() {
		this.chromosomelib$isTraitsSolved = false;
	}
}
