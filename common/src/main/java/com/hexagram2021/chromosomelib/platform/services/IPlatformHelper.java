package com.hexagram2021.chromosomelib.platform.services;

import com.google.common.collect.ImmutableCollection;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.event.AfterAssigningTraitSolver;
import com.hexagram2021.chromosomelib.event.UnpairedChromosomesToBreedSolver;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * APIs of this mod. <br/>
 * REMEMBER: Only call these methods in mod initializer.
 *
 * @author liudongyu
 */
public interface IPlatformHelper extends UnpairedChromosomesToBreedSolver, AfterAssigningTraitSolver {
	/**
	 * Registers a chromosome to the mod's registry. <br/>
	 * Platform-specific implementation handles registry differences between Forge and Fabric.
	 *
	 * @param id the resource location identifier
	 * @param chromosome the chromosome supplier
	 * @return the abstract register entry for the chromosome
	 */
	AbstractRegisterEntry<Chromosome> registerChromosome(ResourceLocation id, Supplier<Chromosome> chromosome);

	/**
	 * Registers a gene locus to the mod's registry. <br/>
	 * Platform-specific implementation handles registry differences between Forge and Fabric.
	 *
	 * @param id the resource location identifier
	 * @param geneLocus the gene locus supplier
	 * @return the abstract register entry for the gene locus
	 */
	AbstractRegisterEntry<GeneLocus> registerGeneLocus(ResourceLocation id, Supplier<GeneLocus> geneLocus);

	/**
	 * Registers a gene to the mod's registry. <br/>
	 * Platform-specific implementation handles registry differences between Forge and Fabric.
	 *
	 * @param id the resource location identifier
	 * @param gene the gene supplier
	 * @return the abstract register entry for the gene
	 */
	AbstractRegisterEntry<Gene> registerGene(ResourceLocation id, Supplier<Gene> gene);

	/**
	 * Registers a trait to the mod's registry. <br/>
	 * Platform-specific implementation handles registry differences between Forge and Fabric.
	 *
	 * @param id the resource location identifier
	 * @param trait the trait supplier
	 * @return the abstract register entry for the trait
	 */
	AbstractRegisterEntry<Trait> registerTrait(ResourceLocation id, Supplier<Trait> trait);

	/**
	 * Registers a trait type to the mod's registry. <br/>
	 * Platform-specific implementation handles registry differences between Forge and Fabric.
	 *
	 * @param id the resource location identifier
	 * @param traitType the trait type supplier
	 * @return the abstract register entry for the trait type
	 */
	AbstractRegisterEntry<TraitType> registerTraitType(ResourceLocation id, Supplier<TraitType> traitType);

	/**
	 * Platform-specific solver for handling unpaired chromosomes during breeding. <br/>
	 * Fires platform events (Forge/Fabric) to allow downstream mods to customize inheritance of sex chromosomes.
	 *
	 * @param entityType the entity type being bred
	 * @param chromosomeInstances the collection of unpaired chromosome instances
	 * @param random the random source for genetic variation
	 * @param builder the builder to add resolved chromosomes into
	 * @return true if the event was handled by platform listeners, false otherwise
	 */
	@Override
	boolean solveUnpairedChromosomesToBreed(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomeInstances, RandomSource random, ImmutableCollection.Builder<ChromosomeInstance> builder);

	/**
	 * Platform-specific solver for post-trait-assignment modifications. <br/>
	 * Fires platform events (Forge/Fabric) to allow downstream mods to react to trait assignment.
	 *
	 * @param livingEntity the living entity whose traits were just assigned
	 * @param map the map of trait types to assigned traits
	 * @param hasTrait the predicate to check if a trait is present
	 */
	@Override
	void solveAfterAssigningTrait(LivingEntity livingEntity, Map<Holder<TraitType>, Holder<Trait>> map, Predicate<Holder<Trait>> hasTrait);
}
