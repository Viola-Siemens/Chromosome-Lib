package com.hexagram2021.chromosomelib.common.chromosome;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import com.hexagram2021.chromosomelib.common.util.exception.InvalidGeneFromGeneLocusException;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.RandomSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ChromosomeInstance {
	public static final Codec<ChromosomeInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(CLRegistries.CHROMOSOMES).fieldOf("chromosome").forGetter(ChromosomeInstance::chromosome),
			ChromosomeType.CODEC.fieldOf("type").forGetter(ChromosomeInstance::type),
			GeneLocusInstance.LIST_CODEC.fieldOf("gene_locus_instances").forGetter(chromosomeInstance -> List.copyOf(chromosomeInstance.geneLocusInstances.values()))
	).apply(instance, ChromosomeInstance::of));
	public static final Codec<List<ChromosomeInstance>> LIST_CODEC = ChromosomeInstance.CODEC.listOf();
	private static final RandomSource RANDOM = RandomSource.create();

	private final Holder<Chromosome> chromosome;
	private final ChromosomeType type;
	private final Int2ObjectMap<GeneLocusInstance> geneLocusInstances;

	private ChromosomeInstance(Holder<Chromosome> chromosome, ChromosomeType type, Int2ObjectMap<GeneLocusInstance> geneLocusInstances, IWeightedGeneList.Context context) {
		this.chromosome = chromosome;
		this.type = type;
		this.geneLocusInstances = geneLocusInstances;

		chromosome.value().geneLoci(type).forEach((index, geneLocus) -> this.geneLocusInstances.compute(index, (ignored, existing) -> {
			if(existing == null) {
				return new GeneLocusInstance(GeneLocus.getRandomGene(geneLocus, context.withLocusAndType(geneLocus, this.type)));
			}
			if(existing.geneLocus().value() != geneLocus.value()) {
				throw new InvalidGeneFromGeneLocusException(existing.gene(), geneLocus);
			}
			return existing;
		}));
	}

	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type) {
		return of(chromosome, type, IWeightedGeneList.Context.of(RANDOM));
	}

	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, Collection<GeneLocusInstance> geneLocusInstances) {
		return of(chromosome, type, geneLocusInstances, IWeightedGeneList.Context.of(RANDOM));
	}

	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, IWeightedGeneList.Context context) {
		return new ChromosomeInstance(chromosome, type, new Int2ObjectArrayMap<>(), context);
	}

	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, Collection<GeneLocusInstance> geneLocusInstances, IWeightedGeneList.Context context) {
		Int2ObjectMap<GeneLocusInstance> geneLocusInstancesMap = new Int2ObjectArrayMap<>();
		geneLocusInstances.forEach(geneLocusInstance -> {
			int index = geneLocusInstance.index(type);
			if (index < 0) {
				throw new IllegalArgumentException("%s may be a heterologous gene locus and cannot be applied to %s side of chromosome %s".formatted(
						geneLocusInstance.gene(), type.name().toLowerCase(Locale.ROOT), chromosome
				));
			}
			geneLocusInstancesMap.put(index, geneLocusInstance.copy());
		});
		return new ChromosomeInstance(chromosome, type, geneLocusInstancesMap, context);
	}

	public Holder<Chromosome> chromosome() {
		return this.chromosome;
	}

	public ChromosomeType type() {
		return this.type;
	}

	public Int2ObjectMap<GeneLocusInstance> geneLocusInstances() {
		return this.geneLocusInstances;
	}

	public ChromosomeInstance copy() {
		return of(this.chromosome, this.type, this.geneLocusInstances.values());
	}

	public void express(Object2IntMap<Holder<Gene>> set) {
		this.geneLocusInstances.values().forEach(geneLocusInstance -> geneLocusInstance.express(set));
	}
}
