package com.hexagram2021.chromosomelib.common.chromosome;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import com.hexagram2021.chromosomelib.common.util.exception.InvalidGeneFromGeneLocusException;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.RandomSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Represents an instance of a chromosome in an entity. <br/>
 * Each chromosome instance contains specific gene alleles at each gene locus.
 *
 * @author liudongyu
 */
public class ChromosomeInstance {
	/**
	 * Codec for serializing and deserializing chromosome instances.
	 */
	public static final Codec<ChromosomeInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(CLRegistries.CHROMOSOMES).fieldOf("chromosome").forGetter(ChromosomeInstance::chromosome),
			ChromosomeType.CODEC.fieldOf("type").forGetter(ChromosomeInstance::type),
			GeneLocusInstance.LIST_CODEC.fieldOf("gene_locus_instances").forGetter(chromosomeInstance -> List.copyOf(chromosomeInstance.geneLocusInstances.values()))
	).apply(instance, ChromosomeInstance::of));
	/**
	 * Codec for serializing and deserializing lists of chromosome instances.
	 */
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

	/**
	 * Creates a chromosome instance with random genes from the specified chromosome and type.
	 *
	 * @param chromosome The chromosome definition
	 * @param type The chromosome type (LEFT or RIGHT)
	 * @return A new chromosome instance
	 */
	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type) {
		return of(chromosome, type, IWeightedGeneList.Context.of(RANDOM));
	}

	/**
	 * Creates a chromosome instance with the specified gene locus instances.
	 *
	 * @param chromosome The chromosome definition
	 * @param type The chromosome type (LEFT or RIGHT)
	 * @param geneLocusInstances The gene locus instances to include
	 * @return A new chromosome instance
	 */
	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, Collection<GeneLocusInstance> geneLocusInstances) {
		return of(chromosome, type, geneLocusInstances, IWeightedGeneList.Context.of(RANDOM));
	}

	/**
	 * Creates a chromosome instance with random genes using the specified context.
	 *
	 * @param chromosome The chromosome definition
	 * @param type The chromosome type (LEFT or RIGHT)
	 * @param context The weighted gene list context for random selection
	 * @return A new chromosome instance
	 */
	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, IWeightedGeneList.Context context) {
		return new ChromosomeInstance(chromosome, type, new Int2ObjectOpenHashMap<>(), context);
	}

	/**
	 * Creates a chromosome instance with the specified gene locus instances and context.
	 *
	 * @param chromosome The chromosome definition
	 * @param type The chromosome type (LEFT or RIGHT)
	 * @param geneLocusInstances The gene locus instances to include
	 * @param context The weighted gene list context for random selection
	 * @return A new chromosome instance
	 */
	public static ChromosomeInstance of(Holder<Chromosome> chromosome, ChromosomeType type, Collection<GeneLocusInstance> geneLocusInstances, IWeightedGeneList.Context context) {
		Int2ObjectMap<GeneLocusInstance> geneLocusInstancesMap = new Int2ObjectOpenHashMap<>();
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

	/**
	 * Gets the chromosome definition of this instance.
	 *
	 * @return The chromosome holder
	 */
	public Holder<Chromosome> chromosome() {
		return this.chromosome;
	}

	/**
	 * Gets the type of this chromosome instance (LEFT or RIGHT).
	 *
	 * @return The chromosome type
	 */
	public ChromosomeType type() {
		return this.type;
	}

	/**
	 * Gets the gene locus instances map of this chromosome instance.
	 *
	 * @return The gene locus instances indexed by position
	 */
	public Int2ObjectMap<GeneLocusInstance> geneLocusInstances() {
		return this.geneLocusInstances;
	}

	/**
	 * Creates a copy of this chromosome instance.
	 *
	 * @return A new chromosome instance with the same gene locus instances
	 */
	public ChromosomeInstance copy() {
		return of(this.chromosome, this.type, this.geneLocusInstances.values());
	}

	/**
	 * Expresses all genes in this chromosome instance, updating the gene count map.
	 *
	 * @param set The gene count map to update
	 */
	public void express(Object2IntMap<Holder<Gene>> set) {
		this.geneLocusInstances.values().forEach(geneLocusInstance -> geneLocusInstance.express(set));
	}
}
