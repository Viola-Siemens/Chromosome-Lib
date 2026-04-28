package com.hexagram2021.chromosomelib.common.compat.jade;

import com.hexagram2021.chromosomelib.common.compat.jade.entity.BiologicalSexProvider;
import net.minecraft.world.entity.LivingEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/**
 * Jade (WAILA) plugin entry point for Chromosome Lib. <br/>
 * Registers server-side data providers and client-side component providers
 * that display entity biological sex information in the Jade overlay.
 *
 * @author liudongyu
 */
@WailaPlugin
public class CLWailaPlugin implements IWailaPlugin {
	/**
	 * Registers server-side data providers for Chromosome Lib. <br/>
	 * Adds {@link BiologicalSexProvider} as a data provider for all {@link LivingEntity} instances.
	 *
	 * @param registration The common registration object provided by Jade.
	 */
	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerEntityDataProvider(BiologicalSexProvider.INSTANCE, LivingEntity.class);
	}

	/**
	 * Registers client-side UI component providers for Chromosome Lib. <br/>
	 * Adds {@link BiologicalSexProvider} as a tooltip component provider for all {@link LivingEntity} instances.
	 *
	 * @param registration The client registration object provided by Jade.
	 */
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(BiologicalSexProvider.INSTANCE, LivingEntity.class);
	}
}
