package com.hexagram2021.chromosomelib.manager;

import com.hexagram2021.chromosomelib.manager.chromosome.Chromosome;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

public class ChromosomeManager {
	public static final ResourceKey<Registry<Chromosome>> CHROMOSOME_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MODID, "chromosome"));
	public static final Registry<Chromosome> CHROMOSOME_REGISTRY = new RegistryBuilder<>(CHROMOSOME_REGISTRY_KEY)
			.sync(true)
			.maxId(65536)
			.create();

}
