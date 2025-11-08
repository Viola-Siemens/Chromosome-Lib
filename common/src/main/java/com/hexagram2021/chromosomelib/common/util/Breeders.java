package com.hexagram2021.chromosomelib.common.util;

import com.google.common.collect.*;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import com.hexagram2021.chromosomelib.common.util.exception.UnpairedChromosomeException;
import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;
import java.util.Map;

public final class Breeders {
	/**
	 * Cross over.
	 * @param chromosomes	chromosomes to cross over.
	 * @param random		random source
	 */
	public static void crossOver(Collection<ChromosomeInstance> chromosomes, RandomSource random) {
		Map<Holder<Chromosome>, ChromosomeInstance> temporary = Maps.newIdentityHashMap();
		chromosomes.forEach(chromosomeInstance -> {
			Holder<Chromosome> chromosome = chromosomeInstance.chromosome();
			ChromosomeInstance oldInstance = temporary.put(chromosome, chromosomeInstance);
			boolean swap = false;
			if(oldInstance != null) {
				// Start cross over for two chromosomes.
				int offset = chromosome.value().homoSegmentStartDiff() * (chromosomeInstance.type().ordinal() - oldInstance.type().ordinal());
				for(Int2ObjectMap.Entry<GeneLocusInstance> entry : chromosomeInstance.geneLocusInstances().int2ObjectEntrySet()) {
					GeneLocusInstance current = entry.getValue();
					int otherIndex = entry.getIntKey() + offset;
					GeneLocusInstance other = oldInstance.geneLocusInstances().get(otherIndex);
					Holder<GeneLocus> geneLocus = current.geneLocus();
					if(other != null && other.geneLocus() == geneLocus) {
						if(random.nextDouble() <= geneLocus.value().possibilityOfCrossingOver) {
							swap = !swap;
						}
						if(swap) {
							entry.setValue(other);
							oldInstance.geneLocusInstances().put(otherIndex, current);
						}
					} else {
						swap = false;
					}
				}
			}
		});
	}

	/**
	 * Mutate.
	 * @param chromosomes	Chromosomes to mutate.
	 * @param random		Random source.
	 */
	public static void mutate(Collection<ChromosomeInstance> chromosomes, RandomSource random) {
		chromosomes.forEach(chromosomeInstance -> {
			for(Int2ObjectMap.Entry<GeneLocusInstance> entry : chromosomeInstance.geneLocusInstances().int2ObjectEntrySet()) {
				GeneLocusInstance geneLocusInstance = entry.getValue();
				if(random.nextDouble() <= geneLocusInstance.geneLocus().value().possibilityOfMutation) {
					entry.setValue(geneLocusInstance.mutate(random));
				}
			}
		});
	}

	public static Collection<ChromosomeInstance> breed(EntityType<?> parentAType, EntityType<?> parentBType,
													   Collection<ChromosomeInstance> parentA, Collection<ChromosomeInstance> parentB,
													   RandomSource random) {
		ImmutableSet.Builder<ChromosomeInstance> builder = ImmutableSet.builder();
		Holder<Chromosome> failed = null;
		Multimap<Holder<Chromosome>, ChromosomeInstance> temporary = Multimaps.newListMultimap(AbstractRegisterEntry.newHolderTreeMap(), Lists::newArrayList);
		parentA.forEach(chromosomeInstance -> temporary.put(chromosomeInstance.chromosome(), chromosomeInstance));
		for(Holder<Chromosome> chromosome : temporary.keySet()) {
			Collection<ChromosomeInstance> chromosomeInstances = temporary.get(chromosome);
			if(chromosomeInstances.size() % 2 == 1) {
				failed = chromosome;
				break;
			}
			ObjectArrayList<ChromosomeInstance> shuffled = new ObjectArrayList<>(chromosomeInstances);
			Util.shuffle(shuffled, random);
			for(int i = 0; i < shuffled.size(); i += 2) {
				builder.add(shuffled.get(i).copy());
			}
		}
		if(failed != null) {
			if(!Services.PLATFORM.solveUnpairedChromosomesToBreed(parentAType, parentA, random, builder)) {
				throw new UnpairedChromosomeException(parentAType, "breed", failed, temporary.get(failed));
			}
			failed = null;
		}
		temporary.clear();
		parentB.forEach(chromosomeInstance -> temporary.put(chromosomeInstance.chromosome(), chromosomeInstance));
		for(Holder<Chromosome> chromosome : temporary.keySet()) {
			Collection<ChromosomeInstance> chromosomeInstances = temporary.get(chromosome);
			if(chromosomeInstances.size() % 2 == 1) {
				failed = chromosome;
				break;
			}
			ObjectArrayList<ChromosomeInstance> shuffled = new ObjectArrayList<>(chromosomeInstances);
			Util.shuffle(shuffled, random);
			for(int i = 0; i < shuffled.size(); i += 2) {
				builder.add(shuffled.get(i).copy());
			}
		}
		if(failed != null && !Services.PLATFORM.solveUnpairedChromosomesToBreed(parentBType, parentB, random, builder)) {
			throw new UnpairedChromosomeException(parentBType, "breed", failed, temporary.get(failed));
		}

		return builder.build();
	}

	private Breeders() {
	}
}
