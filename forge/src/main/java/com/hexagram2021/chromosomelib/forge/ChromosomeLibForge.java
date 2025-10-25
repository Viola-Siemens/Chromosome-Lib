package com.hexagram2021.chromosomelib.forge;

import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(ChromosomeLib.MODID)
public class ChromosomeLibForge {

	@SubscribeEvent
	public void onRegistryCreate(NewRegistryEvent event) {
		event.create(new RegistryBuilder<>().setMaxID(0x00FFFFFF).setName(CLRegistries.CHROMOSOMES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x00FFFFFF).setName(CLRegistries.GENE_LOCI.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x00FFFFFF).setName(CLRegistries.GENES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x00FFFFFF).setName(CLRegistries.TRAITS.location()).hasTags());
	}
}
