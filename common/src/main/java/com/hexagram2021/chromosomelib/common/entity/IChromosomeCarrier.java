package com.hexagram2021.chromosomelib.common.entity;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.sex.SexDetermination;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/**
 * Interface for entities that can carry chromosomes and express traits. <br/>
 * Typically implemented via Mixin on LivingEntity or specific entity types.
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S100")
public interface IChromosomeCarrier {
	/**
	 * Get all chromosomes of this entity.
	 * @return chromosomes
	 */
	List<ChromosomeInstance> chromosomelib$getChromosomes();
	/**
	 * Set chromosomes of this entity. At the same time, compute active genes and active traits.
	 * @param chromosomes	chromosomes
	 */
	void chromosomelib$setChromosomes(Collection<ChromosomeInstance> chromosomes);

	/**
	 * Get active genes of this entity.
	 * @return weight of active genes. 0 is for inactive gene, > 0 is for active genes.
	 */
	ToIntFunction<Holder<Gene>> chromosomelib$getActiveGenes();

	/**
	 * Get active traits of this entity.
	 * @return active traits
	 */
	Collection<Holder<Trait>> chromosomelib$getActiveTraits();

	/**
	 * Assign traits to this entity.
	 */
	void chromosomelib$assignTraits();

	/**
	 * Build default chromosomes of this entity.
	 * @param entityType	entity type
	 * @param context		build context
	 * @return default chromosomes
	 */
	@ApiStatus.Internal
	default List<ChromosomeInstance> chromosomelib$buildDefaultChromosomes(IChromosomeLibEntityType entityType, IWeightedGeneList.Context context) {
		return entityType.chromosomelib$getChromosomes().values().stream()
				.<ChromosomeInstance>mapMulti((chromosome, consumer) -> {
					int ploidy = this.chromosomelib$getPloidy();
					SexDetermination sexSystem = RegistryRelations.getSexDetermination(chromosome);
					if(sexSystem != null) {
						// Sex chromosome: randomly assign sex at 50:50 ratio
						generateSexChromosomeInstances(chromosome, ploidy, context, consumer);
					} else {
						// Normal chromosome: generate by ploidy, type fixed or random
						ChromosomeInstance last = null;
						for(int i = 0; i < ploidy; ++i) {
							ChromosomeType type = Chromosome.getNecessaryChromosomeType(chromosome);
							if(type == null) {
								type = context.random().nextBoolean() ? ChromosomeType.LEFT : ChromosomeType.RIGHT;
							}
							last = ChromosomeInstance.of(chromosome, type, context.withLast(last));
							consumer.accept(last);
						}
					}
				}).toList();
	}

	/**
	 * Generates sex chromosome instances for the XY / ZW determination system.
	 * For ploidy = 2: randomly produces LEFT+LEFT (50%) or LEFT+RIGHT (50%).
	 * For ploidy = 1: produces a single LEFT instance (rare edge case).
	 *
	 * @param chromosome	sex chromosome
	 * @param ploidy		ploidy
	 * @param context		context
	 * @param consumer		add chromosome instances by  {@link Consumer#accept}
	 */
	private static void generateSexChromosomeInstances(Holder<Chromosome> chromosome,
													   int ploidy, IWeightedGeneList.Context context,
													   Consumer<ChromosomeInstance> consumer) {
		// ploidy = 1: only one LEFT chromosome (edge case, normal species won't reach here)
		if(ploidy == 1) {
			consumer.accept(ChromosomeInstance.of(chromosome, ChromosomeType.LEFT, context));
			return;
		}
		// Diploid: 50% LEFT+LEFT, 50% LEFT+RIGHT
		boolean rightType = context.random().nextBoolean();
		ChromosomeInstance left = ChromosomeInstance.of(chromosome, ChromosomeType.LEFT, context);
		ChromosomeInstance right = ChromosomeInstance.of(
				chromosome,
				rightType ? ChromosomeType.RIGHT : ChromosomeType.LEFT,
				context.withLast(left));
		consumer.accept(left);
		consumer.accept(right);
	}

	/**
	 * Get ploidy of this entity. For example, a horse is a diploid entity, so we return 2; a mule is an allodiploid entity (but we consider it is a haploid), so we return 1.
	 * @return ploidy
	 */
	default int chromosomelib$getPloidy() {
		return 2;
	}

	/**
	 * Mark isTraitsSolved flag to false.
	 */
	void chromosomelib$resetTraits();

	/**
	 * Check if isTraitsSolved flag is true.
	 *
	 * @return true if traits have been solved, false otherwise
	 */
	boolean chromosomelib$isTraitsSolved();
}
