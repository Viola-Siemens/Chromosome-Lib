package com.hexagram2021.chromosomelib.fabric.event;

import com.hexagram2021.chromosomelib.event.UnpairedChromosomesToBreedSolver;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class CLFabricEvents {
	/**
	 * Called when two entities is breeding, finding out that one of them has unpaired chromosomes. Do NOT modify the builder if you return {@code false}.
	 * @see com.hexagram2021.chromosomelib.common.util.Breeders#breed
	 * @see UnpairedChromosomesToBreedSolver
	 */
	public static final Event<UnpairedChromosomesToBreedSolver> UNPAIRED_CHROMOSOMES_TO_BREED_SOLVER = EventFactory.createArrayBacked(
			UnpairedChromosomesToBreedSolver.class,
			callbacks -> (entityType, chromosomeInstances, random, builder) -> {
				for(UnpairedChromosomesToBreedSolver callback : callbacks) {
					if(callback.solveUnpairedChromosomesToBreed(entityType, chromosomeInstances, random, builder)) {
						return true;
					}
				}
				return false;
			}
	);

	private CLFabricEvents() {
	}
}
