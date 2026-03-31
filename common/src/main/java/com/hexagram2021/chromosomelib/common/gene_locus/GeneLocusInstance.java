package com.hexagram2021.chromosomelib.common.gene_locus;

import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Objects;

/**
 * Represents an instance of a gene locus with a specific gene allele.
 *
 * @author liudongyu
 * @param gene The gene allele at this locus instance
 */
public record GeneLocusInstance(Holder<Gene> gene) {
	/**
	 * Codec for serializing and deserializing gene locus instances.
	 */
	public static final Codec<GeneLocusInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(CLRegistries.GENES).fieldOf("gene").forGetter(GeneLocusInstance::gene)
	).apply(instance, GeneLocusInstance::new));
	/**
	 * Codec for serializing and deserializing lists of gene locus instances.
	 */
	public static final Codec<List<GeneLocusInstance>> LIST_CODEC = CODEC.listOf();

	/**
	 * Gets the gene locus definition of this instance.
	 *
	 * @return The gene locus holder
	 */
	public Holder<GeneLocus> geneLocus() {
		return Objects.requireNonNull(this.gene.value().geneLocus);
	}

	/**
	 * Gets the index of this gene locus on the specified chromosome type.
	 *
	 * @param type The chromosome type
	 * @return The index of this locus on the specified chromosome type
	 */
	public int index(ChromosomeType type) {
		return this.geneLocus().value().index(type);
	}

	/**
	 * Expresses this gene, updating the gene count map.
	 *
	 * @param set The gene count map to update
	 */
	public void express(Object2IntMap<Holder<Gene>> set) {
		Holder<Gene> gene = this.gene instanceof AbstractRegisterEntry<Gene> registerEntry ? registerEntry.asHolder() : this.gene;
		set.computeInt(gene, (ignored, value) -> value == null ? 1 : value + 1);
	}

	/**
	 * Creates a copy of this gene locus instance.
	 *
	 * @return A new gene locus instance with the same gene
	 */
	public GeneLocusInstance copy() {
		return new GeneLocusInstance(this.gene);
	}

	/**
	 * Creates a mutated copy of this gene locus instance with a different gene allele.
	 *
	 * @param random Random source for selecting the mutation
	 * @return A new gene locus instance with a mutated gene
	 */
	public GeneLocusInstance mutate(RandomSource random) {
		List<Holder<Gene>> toMutate = Objects.requireNonNull(this.geneLocus().value().genes).stream()
				.filter(gene -> gene != this.gene).toList();
		return new GeneLocusInstance(toMutate.get(random.nextInt(toMutate.size())));
	}
}
