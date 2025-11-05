package com.hexagram2021.chromosomelib.mixin;

import com.google.common.collect.Lists;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitHandler;
import com.hexagram2021.chromosomelib.common.util.Mappers;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;

@SuppressWarnings("java:S116")
@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IChromosomeCarrier {
	@Unique
	private final List<ChromosomeInstance> chromosomelib$chromosomes = Lists.newArrayList();
	@Unique
	private final Object2IntMap<Holder<Gene>> chromosomelib$activeGenes = AbstractRegisterEntry.newHolderObject2IntTreeMap();
	@Unique
	private final Set<Holder<Trait>> chromosomelib$activeTraits = AbstractRegisterEntry.newHolderTreeSet();

	@Override
	public Collection<ChromosomeInstance> chromosomelib$getChromosomes() {
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

		this.chromosomelib$activeTraits.clear();
		((IChromosomeLibEntityType)((LivingEntity)(Object)this).getType()).chromosomelib$getTraitTypes()
				.forEach(traitType -> this.chromosomelib$activeTraits.add(TraitHandler.getHandler(traitType).handle(this.chromosomelib$activeGenes)));
	}

	@Override
	public ToIntFunction<Holder<Gene>> chromosomelib$getActiveGenes() {
		return this.chromosomelib$activeGenes;
	}

	@Override
	public Collection<Holder<Trait>> chromosomelib$getActiveTraits() {
		return this.chromosomelib$activeTraits;
	}
}
