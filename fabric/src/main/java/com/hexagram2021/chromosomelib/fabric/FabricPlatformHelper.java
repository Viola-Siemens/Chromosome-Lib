package com.hexagram2021.chromosomelib.fabric;

import com.google.common.collect.ImmutableCollection;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.fabric.event.CLFabricEvents;
import com.hexagram2021.chromosomelib.fabric.registry.FabricRegisterEntry;
import com.hexagram2021.chromosomelib.platform.services.IPlatformHelper;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Fabric implementation of {@link IPlatformHelper}.
 *
 * @author liudongyu
 */
@ApiStatus.Internal
public class FabricPlatformHelper implements IPlatformHelper {
	@Override
	public AbstractRegisterEntry<Chromosome> registerChromosome(ResourceLocation id, Supplier<Chromosome> chromosome) {
		ResourceKey<Chromosome> key = ResourceKey.create(CLRegistries.CHROMOSOMES, id);
		return new FabricRegisterEntry<>(Registry.registerForHolder(ChromosomeLibFabric.CHROMOSOMES, key, chromosome.get()), key);
	}

	@Override
	public AbstractRegisterEntry<GeneLocus> registerGeneLocus(ResourceLocation id, Supplier<GeneLocus> geneLocus) {
		ResourceKey<GeneLocus> key = ResourceKey.create(CLRegistries.GENE_LOCI, id);
		return new FabricRegisterEntry<>(Registry.registerForHolder(ChromosomeLibFabric.GENE_LOCI, key, geneLocus.get()), key);
	}

	@Override
	public AbstractRegisterEntry<Gene> registerGene(ResourceLocation id, Supplier<Gene> gene) {
		ResourceKey<Gene> key = ResourceKey.create(CLRegistries.GENES, id);
		return new FabricRegisterEntry<>(Registry.registerForHolder(ChromosomeLibFabric.GENES, key, gene.get()), key);
	}

	@Override
	public AbstractRegisterEntry<Trait> registerTrait(ResourceLocation id, Supplier<Trait> trait) {
		ResourceKey<Trait> key = ResourceKey.create(CLRegistries.TRAITS, id);
		return new FabricRegisterEntry<>(Registry.registerForHolder(ChromosomeLibFabric.TRAITS, key, trait.get()), key);
	}

	@Override
	public AbstractRegisterEntry<TraitType> registerTraitType(ResourceLocation id, Supplier<TraitType> traitType) {
		ResourceKey<TraitType> key = ResourceKey.create(CLRegistries.TRAIT_TYPES, id);
		return new FabricRegisterEntry<>(Registry.registerForHolder(ChromosomeLibFabric.TRAIT_TYPES, key, traitType.get()), key);
	}

	@Override
	public boolean solveUnpairedChromosomesToBreed(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomeInstances, RandomSource random, ImmutableCollection.Builder<ChromosomeInstance> builder) {
		return CLFabricEvents.UNPAIRED_CHROMOSOMES_TO_BREED_SOLVER.invoker().solveUnpairedChromosomesToBreed(entityType, chromosomeInstances, random, builder);
	}

	@Override
	public void solveAfterAssigningTrait(LivingEntity livingEntity, Map<Holder<TraitType>, Holder<Trait>> map, Predicate<Holder<Trait>> hasTrait) {
		CLFabricEvents.AFTER_ASSIGNING_TRAIT_SOLVER.invoker().solveAfterAssigningTrait(livingEntity, map, hasTrait);
	}
}
