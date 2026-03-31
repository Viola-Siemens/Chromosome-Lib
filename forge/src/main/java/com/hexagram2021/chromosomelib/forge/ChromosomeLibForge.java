package com.hexagram2021.chromosomelib.forge;

import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.common.CLCommonEvents;
import com.hexagram2021.chromosomelib.common.chromosome.BuiltInChromosomes;
import com.hexagram2021.chromosomelib.common.command.ChromosomeLibCommand;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Optional;

/**
 * Forge implementation of ChromosomeLib.
 *
 * @author liudongyu
 */
@Mod(ChromosomeLib.MODID)
public class ChromosomeLibForge {
	/**
	 * Constructs the Forge mod loader entry point. <br/>
	 * Initializes built-in chromosomes via deferred work queue and registers event listeners.
	 */
	public ChromosomeLibForge() {
		DeferredWorkQueue queue = DeferredWorkQueue.lookup(Optional.of(ModLoadingStage.CONSTRUCT)).orElseThrow();
		BuiltInChromosomes.init(runnable -> queue.enqueueWork(ModLoadingContext.get().getActiveContainer(), runnable));

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegistryCreate);

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * Creates custom registries for the mod's genetic system. <br/>
	 * Registers five core registries: chromosomes, gene loci, genes, traits, and trait types.
	 *
	 * @param event the new registry event
	 */
	public void onRegistryCreate(NewRegistryEvent event) {
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.CHROMOSOMES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.GENE_LOCI.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.GENES.location()).disableSync().hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.TRAITS.location()).hasTags());
		event.create(new RegistryBuilder<>().setMaxID(0x000FFFFF).setName(CLRegistries.TRAIT_TYPES.location()).hasTags());
	}

	/**
	 * Freezes and builds registry relationships after tags are loaded. <br/>
	 * Called when server/client tags are updated to ensure all genetic data is properly linked.
	 *
	 * @param event the tags updated event
	 */
	@SubscribeEvent
	public void onFMLCommonSetup(TagsUpdatedEvent event) {
		RegistryRelations.freezeAndBuild();
	}

	/**
	 * Handles entity breeding events to apply chromosome inheritance. <br/>
	 * Triggers genetic recombination from parents to offspring, canceling breeding if inheritance fails.
	 *
	 * @param event the baby entity spawn event
	 */
	@SubscribeEvent
	public void onEntityBreed(BabyEntitySpawnEvent event) {
		if(!CLCommonEvents.onEntityBreed(event.getParentA(), event.getParentB(), event.getChild())) {
			event.setChild(null);
		}
	}

	/**
	 * Registers the mod's commands to the command dispatcher. <br/>
	 * Adds the {@code /chromosomelib} command for debugging and managing chromosomes.
	 *
	 * @param event the register commands event
	 */
	@SubscribeEvent
	public void onCommandRegister(RegisterCommandsEvent event) {
		event.getDispatcher().register(ChromosomeLibCommand.register());
	}
}
