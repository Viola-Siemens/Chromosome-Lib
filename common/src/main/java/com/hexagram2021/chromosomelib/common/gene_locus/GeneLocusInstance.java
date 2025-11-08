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

public record GeneLocusInstance(Holder<Gene> gene) {
	public static final Codec<GeneLocusInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(CLRegistries.GENES).fieldOf("gene").forGetter(GeneLocusInstance::gene)
	).apply(instance, GeneLocusInstance::new));
	public static final Codec<List<GeneLocusInstance>> LIST_CODEC = CODEC.listOf();

	public Holder<GeneLocus> geneLocus() {
		return Objects.requireNonNull(this.gene.value().geneLocus);
	}

	public int index(ChromosomeType type) {
		return this.geneLocus().value().index(type);
	}

	public void express(Object2IntMap<Holder<Gene>> set) {
		Holder<Gene> gene = this.gene instanceof AbstractRegisterEntry<Gene> registerEntry ? registerEntry.asHolder() : this.gene;
		set.computeInt(gene, (ignored, value) -> value == null ? 1 : value + 1);
	}

	public GeneLocusInstance copy() {
		return new GeneLocusInstance(this.gene);
	}

	public GeneLocusInstance mutate(RandomSource random) {
		List<Holder<Gene>> toMutate = Objects.requireNonNull(this.geneLocus().value().genes).stream()
				.filter(gene -> gene != this.gene).toList();
		return new GeneLocusInstance(toMutate.get(random.nextInt(toMutate.size())));
	}
}
