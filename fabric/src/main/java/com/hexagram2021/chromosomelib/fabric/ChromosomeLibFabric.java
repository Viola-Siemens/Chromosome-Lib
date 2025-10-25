package com.hexagram2021.chromosomelib.fabric;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;

public class ChromosomeLibFabric implements ModInitializer {
	static final MappedRegistry<Chromosome> CHROMOSOMES = FabricRegistryBuilder.createSimple(CLRegistries.CHROMOSOMES).buildAndRegister();
	static final MappedRegistry<GeneLocus> GENE_LOCI = FabricRegistryBuilder.createSimple(CLRegistries.GENE_LOCI).buildAndRegister();
	static final MappedRegistry<Gene> GENES = FabricRegistryBuilder.createSimple(CLRegistries.GENES).buildAndRegister();
	static final MappedRegistry<Trait> TRAITS = FabricRegistryBuilder.createSimple(CLRegistries.TRAITS).attribute(RegistryAttribute.SYNCED).buildAndRegister();

	@Override
	public void onInitialize() {

	}
}
