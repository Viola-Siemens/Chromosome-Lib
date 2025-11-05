package com.hexagram2021.chromosomelib.forge;

import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.common.CLCommonEvents;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(ChromosomeLib.MODID)
public class ChromosomeLibForge {
	public ChromosomeLibForge() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		modBus.addListener(this::onRegistryCreate);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void onRegistryCreate(NewRegistryEvent event) {
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.CHROMOSOMES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.GENE_LOCI.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.GENES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.TRAITS.location()).hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.TRAIT_TYPES.location()).hasTags());
	}

	@SubscribeEvent
	public void onEntityBreed(BabyEntitySpawnEvent event) {
		if(!CLCommonEvents.onEntityBreed(event.getParentA(), event.getParentB(), event.getChild())) {
			event.setChild(null);
		}
	}
}
