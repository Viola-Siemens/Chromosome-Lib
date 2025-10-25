package com.hexagram2021.chromosomelib.platform.services;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * REMEMBER: Only call these methods in mod initializer.
 */
public interface IPlatformHelper {
	AbstractRegisterEntry<Chromosome> registerChromosome(ResourceLocation id, Supplier<Chromosome> chromosome);
	AbstractRegisterEntry<GeneLocus> registerGeneLocus(ResourceLocation id, Supplier<GeneLocus> geneLocus);
	AbstractRegisterEntry<Gene> registerGene(ResourceLocation id, Supplier<Gene> gene);
	AbstractRegisterEntry<Trait> registerTrait(ResourceLocation id, Supplier<Trait> trait);
}
