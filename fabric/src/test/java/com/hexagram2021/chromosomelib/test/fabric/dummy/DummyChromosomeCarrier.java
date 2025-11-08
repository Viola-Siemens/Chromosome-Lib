package com.hexagram2021.chromosomelib.test.fabric.dummy;

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
import net.minecraft.world.entity.EntityType;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;

/**
 * Stub class for testing purposes.
 */
public class DummyChromosomeCarrier implements IChromosomeCarrier {
	private final EntityType<?> entityType;
	private final List<ChromosomeInstance> chromosomes = Lists.newArrayList();
	private final Object2IntMap<Holder<Gene>>  activeGenes = AbstractRegisterEntry.newHolderObject2IntTreeMap();
	private final Set<Holder<Trait>> activeTraits = AbstractRegisterEntry.newHolderTreeSet();

	public DummyChromosomeCarrier(EntityType<?> entityType) {
		this.entityType = entityType;
	}

	public EntityType<?> entityType() {
		return this.entityType;
	}

	@Override
	public Collection<ChromosomeInstance> chromosomelib$getChromosomes() {
		return this.chromosomes;
	}

	@Override
	public void chromosomelib$setChromosomes(Collection<ChromosomeInstance> chromosomes) {
		this.chromosomes.clear();
		this.chromosomes.addAll(chromosomes);

		this.activeGenes.clear();
		this.activeGenes.putAll(Mappers.convertChromosomeInstancesToExpressingGenes(chromosomes));
		Gene.doDisable(this.activeGenes);

		this.activeTraits.clear();
		((IChromosomeLibEntityType)this.entityType).chromosomelib$getTraitTypes()
				.forEach(traitType -> this.activeTraits.add(TraitHandler.getHandler(traitType).handle(this.activeGenes)));
	}

	@Override
	public ToIntFunction<Holder<Gene>> chromosomelib$getActiveGenes() {
		return this.activeGenes;
	}

	@Override
	public Collection<Holder<Trait>> chromosomelib$getActiveTraits() {
		return this.activeTraits;
	}
}
