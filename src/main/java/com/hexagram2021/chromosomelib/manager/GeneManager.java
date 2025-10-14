package com.hexagram2021.chromosomelib.manager;

import com.hexagram2021.chromosomelib.manager.gene.Gene;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

public class GeneManager {
	public static final ResourceKey<Registry<Gene>> GENE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MODID, "gene"));
	public static final Registry<Gene> GENE_REGISTRY = new RegistryBuilder<>(GENE_REGISTRY_KEY)
			.sync(true)
			.maxId(65536)
			.create();
}
