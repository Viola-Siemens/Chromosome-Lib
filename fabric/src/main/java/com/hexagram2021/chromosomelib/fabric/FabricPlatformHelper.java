package com.hexagram2021.chromosomelib.fabric;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.fabric.registry.FabricRegisterEntry;
import com.hexagram2021.chromosomelib.platform.services.IPlatformHelper;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

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
}
