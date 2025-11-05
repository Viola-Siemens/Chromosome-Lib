package com.hexagram2021.chromosomelib.registry;

import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class CLRegistries {
	public static final ResourceKey<Registry<Chromosome>> CHROMOSOMES = ResourceKey.createRegistryKey(new ResourceLocation(ChromosomeLib.MODID, "chromosomes"));
	public static final ResourceKey<Registry<GeneLocus>> GENE_LOCI = ResourceKey.createRegistryKey(new ResourceLocation(ChromosomeLib.MODID, "gene_loci"));
	public static final ResourceKey<Registry<Gene>> GENES = ResourceKey.createRegistryKey(new ResourceLocation(ChromosomeLib.MODID, "genes"));
	public static final ResourceKey<Registry<Trait>> TRAITS = ResourceKey.createRegistryKey(new ResourceLocation(ChromosomeLib.MODID, "traits"));
	public static final ResourceKey<Registry<TraitType>> TRAIT_TYPES = ResourceKey.createRegistryKey(new ResourceLocation(ChromosomeLib.MODID, "trait_types"));

	private CLRegistries() {
	}
}
