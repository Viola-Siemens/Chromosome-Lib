package com.hexagram2021.chromosomelib.forge.event;

import com.google.common.collect.ImmutableCollection;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.event.UnpairedChromosomesToBreedSolver;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.Collection;

@Cancelable
public class SolveUnpairedChromosomesEvent extends Event {
	private final EntityType<?> entityType;
	private final Collection<ChromosomeInstance> chromosomeInstances;
	private final RandomSource random;
	private final ImmutableCollection.Builder<ChromosomeInstance> builder;

	public SolveUnpairedChromosomesEvent(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomeInstances,
										 RandomSource random, ImmutableCollection.Builder<ChromosomeInstance> builder) {
		this.entityType = entityType;
		this.chromosomeInstances = chromosomeInstances;
		this.random = random;
		this.builder = builder;
	}

	public EntityType<?> getEntityType() {
		return this.entityType;
	}

	/**
	 * Tips: Check {@link SolveUnpairedChromosomesEvent#getEntityType()} before calling this method. Do NOT modify the builder if you return {@code false}.
	 * @param solver	The solver.
	 */
	public void accept(UnpairedChromosomesToBreedSolver solver) {
		if(solver.solveUnpairedChromosomesToBreed(this.entityType, this.chromosomeInstances, this.random, this.builder)) {
			this.setCanceled(true);
		}
	}
}
