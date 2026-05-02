package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.common.chromosome.BuiltInChromosomes;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

/**
 * Shared bootstrap singleton for all fabric integration tests.
 *
 * <p>Initializes Minecraft's SharedConstants and Bootstrap exactly once across all test classes,
 * then registers all chromosome relations needed by the test suite and freezes the registry.
 * Any test class that requires Minecraft registry state should call {@link #init()} from its
 * {@code @BeforeAll} method.</p>
 *
 * @author liudongyu
 */
public final class TestBootstrap {
	private static volatile boolean initialized = false;

	/**
	 * Initializes the Minecraft environment and all required RegistryRelations exactly once.
	 * Thread-safe; safe to call concurrently from multiple {@code @BeforeAll} methods.
	 */
	public static synchronized void init() {
		if(initialized) {
			return;
		}
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// Register gene/trait relations for sheep color genetics (required by SheepTest)
		SheepColorDesigner.init();
		// Register chromosome-to-entity-type relations and sex chromosomes for sheep and chicken
		BuiltInChromosomes.Sheep.registerRelations();
		BuiltInChromosomes.Chicken.registerRelations();
		RegistryRelations.freezeAndBuild();
		initialized = true;
	}

	private TestBootstrap() {
	}
}
