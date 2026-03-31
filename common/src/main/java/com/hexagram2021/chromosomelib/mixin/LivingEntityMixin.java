package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitHandler;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.common.util.Mappers;
import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
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

/**
 * Mixin to {@link LivingEntity} implementing {@link IChromosomeCarrier}. <br/>
 * Adds chromosome storage, gene expression tracking, and trait assignment functionality to living entities.
 * Manages the lifecycle of genetic data including initialization, persistence, and trait calculation.
 *
 * @author liudongyu
 */
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

	/**
	 * Initializes chromosome data structures when a living entity is constructed. <br/>
	 * Creates empty collections for chromosomes, active genes, and traits, then builds default chromosomes.
	 *
	 * @param ci callback info (unused)
	 */
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void chromosomelib$initChromosomes(CallbackInfo ci) {
		this.chromosomelib$chromosomes = Lists.newArrayList();
		this.chromosomelib$activeGenes = AbstractRegisterEntry.newHolderObject2IntTreeMap();
		this.chromosomelib$activeTraits = AbstractRegisterEntry.newHolderTreeMap();
		this.chromosomelib$isTraitsSolved = false;
		this.chromosomelib$setChromosomes(this.chromosomelib$buildDefaultChromosomes(
				(IChromosomeLibEntityType)((LivingEntity)(Object)this).getType(),
				IWeightedGeneList.Context.of(this.getRandom())
		));
	}

	/**
	 * Saves the trait-solved state when entity writes additional save data. <br/>
	 * Stores whether traits have been assigned to prevent redundant trait calculations on reload.
	 *
	 * @param nbt the compound tag to write data into
	 * @param ci callback info (unused)
	 */
	@Inject(method = "addAdditionalSaveData", at = @At(value = "HEAD"))
	private void chromosomelib$saveChromosomes(CompoundTag nbt, CallbackInfo ci) {
		nbt.putBoolean("ChromosomeLibIsTraitsSolved", this.chromosomelib$isTraitsSolved);
	}

	/**
	 * Loads the trait-solved state when entity reads additional save data. <br/>
	 * Restores whether traits have been assigned from disk.
	 *
	 * @param nbt the compound tag containing entity data
	 * @param ci callback info (unused)
	 */
	@Inject(method = "readAdditionalSaveData", at = @At(value = "HEAD"))
	private void chromosomelib$loadChromosomes(CompoundTag nbt, CallbackInfo ci) {
		this.chromosomelib$isTraitsSolved = nbt.getBoolean("ChromosomeLibIsTraitsSolved");
	}

	@Override
	public List<ChromosomeInstance> chromosomelib$getChromosomes() {
		return this.chromosomelib$chromosomes;
	}

	@Override
	public void chromosomelib$setChromosomes(Collection<ChromosomeInstance> chromosomes) {
		// setting the collection
		this.chromosomelib$chromosomes.clear();
		this.chromosomelib$chromosomes.addAll(chromosomes);

		//maintaining active genes and traits
		this.chromosomelib$activeGenes.clear();
		this.chromosomelib$activeGenes.putAll(Mappers.convertChromosomeInstancesToExpressingGenes(this.chromosomelib$chromosomes));
		Gene.doDisable(this.chromosomelib$activeGenes);

		this.chromosomelib$assignTraits();
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
	public void chromosomelib$assignTraits() {
		LivingEntity current = (LivingEntity)(Object)this;
		this.chromosomelib$activeTraits.clear();
		((IChromosomeLibEntityType)(current).getType()).chromosomelib$getTraitTypes()
				.forEach(traitType -> this.chromosomelib$activeTraits.put(traitType, TraitHandler.getHandler(traitType).handle(this.chromosomelib$activeGenes)));
		if(!current.level().isClientSide && !this.chromosomelib$isTraitsSolved) {
			Services.PLATFORM.solveAfterAssigningTrait(current, this.chromosomelib$activeTraits, this.chromosomelib$activeTraits.values()::contains);
			this.chromosomelib$isTraitsSolved = true;
		}
	}

	@Override
	public void chromosomelib$resetTraits() {
		this.chromosomelib$isTraitsSolved = false;
	}

	@Override
	public boolean chromosomelib$isTraitsSolved() {
		return this.chromosomelib$isTraitsSolved;
	}
}
