package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Weighted gene list.
 *
 * @author liudongyu
 */
public interface IWeightedGeneList {
	/**
	 * Empty weighted gene list.
	 */
	@SuppressWarnings("java:S4738")
	SimpleWeightedGeneList EMPTY = new SimpleWeightedGeneList(ImmutableList.of());

	/**
	 * Get a random gene from this list.
	 * @param context	the context
	 * @return a random gene
	 */
	Holder<Gene> getRandomGene(Context context);

	/**
	 * Get the total weight of this list.
	 * @param context	the context
	 * @return the total weight
	 */
	@Contract(pure = true)
	int totalWeight(Context context);

	/**
	 * Get all possible genes in this list.
	 * @return all possible genes
	 */
	Stream<Holder<Gene>> allGenes();

	/**
	 * Binary Search to find the index of the first element in the array that is greater than the given value.
	 * @param a		The array to search
	 * @param value	The value to search for
	 * @return The index of the first element in the array that is greater than the given value.
	 */
	static int upperBound(IntList a, int value) {
		int l = 0;
		int r = a.size();
		while(l < r) {
			int mid = (l + r) >> 1;
			if(a.getInt(mid) <= value) {
				l = mid + 1;
			} else {
				r = mid;
			}
		}
		return l;
	}

	/**
	 * Context for weighted gene list operations. <br/>
	 * Contains level information, position, random source, and chromosome context.
	 *
	 * @author liudongyu
	 */
	class Context {
		/**
		 * The level reader for accessing world data.
		 */
		@Nullable
		private final LevelReader level;
		/**
		 * The block position for context.
		 */
		private final BlockPos blockPos;
		/**
		 * The random source for gene selection.
		 */
		private final RandomSource random;
		/**
		 * The current gene locus.
		 */
		@Nullable
		private final Holder<GeneLocus> locus;
		/**
		 * The chromosome type (LEFT or RIGHT).
		 */
		@Nullable
		private final ChromosomeType type;
		/**
		 * The last chromosome instance for stable gene selection.
		 */
		@Nullable
		private final ChromosomeInstance last;

		private Context(@Nullable LevelReader level, BlockPos blockPos, RandomSource random) {
			this.level = level;
			this.blockPos = blockPos;
			this.random = random;
			this.locus = null;
			this.type = null;
			this.last = null;
		}
		private Context(@Nullable LevelReader level, BlockPos blockPos, RandomSource random, @Nullable Holder<GeneLocus> locus, @Nullable ChromosomeType type, @Nullable ChromosomeInstance last) {
			this.level = level;
			this.blockPos = blockPos;
			this.random = random;
			this.locus = locus;
			this.type = type;
			this.last = last;
		}

		/**
		 * Creates a context with only random source.
		 *
		 * @param random The random source
		 * @return A new context
		 */
		public static Context of(RandomSource random) {
			return new Context(null, BlockPos.ZERO, random);
		}
		/**
		 * Creates a context with server level and position.
		 *
		 * @param serverLevel The server level
		 * @param blockPos The block position
		 * @return A new context
		 */
		public static Context of(ServerLevelAccessor serverLevel, BlockPos blockPos) {
			return new Context(serverLevel, blockPos, RandomSource.create());
		}
		/**
		 * Creates a context with server level, position, and random source.
		 *
		 * @param serverLevel The server level
		 * @param blockPos The block position
		 * @param random The random source
		 * @return A new context
		 */
		public static Context of(ServerLevelAccessor serverLevel, BlockPos blockPos, RandomSource random) {
			return new Context(serverLevel, blockPos, random);
		}

		/**
		 * Gets the random source.
		 *
		 * @return The random source
		 */
		public RandomSource random() {
			return this.random;
		}

		/**
		 * Gets the last gene from the previous chromosome instance.
		 *
		 * @return The last gene, or null if not available
		 */
		@Nullable
		public Holder<Gene> last() {
			if(this.locus == null || this.type == null || this.last == null) {
				return null;
			}
			GeneLocusInstance instance = this.last.geneLocusInstances().get(this.locus.value().index(this.type));
			if(instance == null) {
				return null;
			}
			return instance.gene();
		}

		/**
		 * Gets the biome at the current position.
		 *
		 * @return The biome holder, or null if not available
		 */
		@Nullable
		public Holder<Biome> biome() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBiome(this.blockPos);
		}

		/**
		 * Gets the block entity at the current position.
		 *
		 * @return The block entity, or null if not available
		 */
		@Nullable
		public BlockEntity blockEntity() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockEntity(this.blockPos);
		}

		/**
		 * Gets the block state at the current position.
		 *
		 * @return The block state, or null if not available
		 */
		@Nullable
		public BlockState blockState() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockState(this.blockPos);
		}

		/**
		 * Gets the block state below the current position.
		 *
		 * @return The block state below, or null if not available
		 */
		@Nullable
		public BlockState below() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockState(this.blockPos.below());
		}

		/**
		 * Gets the fluid state at the current position.
		 *
		 * @return The fluid state, or null if not available
		 */
		@Nullable
		public FluidState fluidState() {
			if(this.level == null) {
				return null;
			}
			return this.level.getFluidState(this.blockPos);
		}

		/**
		 * Creates a new context with the specified last chromosome instance.
		 *
		 * @param last The last chromosome instance
		 * @return A new context
		 */
		public Context withLast(@Nullable ChromosomeInstance last) {
			return new Context(this.level, this.blockPos, this.random, this.locus, this.type, last);
		}

		/**
		 * Creates a new context with the specified locus and type.
		 *
		 * @param locus The gene locus
		 * @param type The chromosome type
		 * @return A new context
		 */
		public Context withLocusAndType(@Nullable Holder<GeneLocus> locus, @Nullable ChromosomeType type) {
			return new Context(this.level, this.blockPos, this.random, locus, type, this.last);
		}
	}

	/**
	 * Entry in a weighted gene list, containing a gene and its weight.
	 *
	 * @param gene The gene
	 * @param weight The weight for random selection
	 * @author liudongyu
	 */
	record Entry(Holder<Gene> gene, int weight) {
		/**
		 * Creates a validated entry with the specified gene and weight.
		 *
		 * @param gene The gene
		 * @param weight The weight (must be greater than 0)
		 * @return A new entry
		 */
		@SuppressWarnings({"ConstantValue", "java:S2583"})
		public static Entry of(Holder<Gene> gene, int weight) {
			if(gene == null) {
				throw new IllegalArgumentException("Gene is null.");
			}
			if(weight <= 0) {
				throw new IllegalArgumentException("Weight must be greater than 0, found " + weight + ".");
			}
			return new Entry(gene, weight);
		}
	}

	/**
	 * Builder for weighted gene lists.
	 *
	 * @author liudongyu
	 */
	abstract class Builder {
		/**
		 * The underlying list builder.
		 */
		protected final ImmutableList.Builder<Entry> shadowed = ImmutableList.builder();

		/**
		 * Adds a gene with weight to the builder.
		 *
		 * @param gene The gene
		 * @param weight The weight
		 * @return This builder
		 */
		public Builder add(Holder<Gene> gene, int weight) {
			this.shadowed.add(Entry.of(gene, weight));
			return this;
		}
		/**
		 * Adds entries to the builder.
		 *
		 * @param elements The entries to add
		 * @return This builder
		 */
		public Builder add(Entry... elements) {
			this.shadowed.add(elements);
			return this;
		}

		/**
		 * Adds all entries from an iterable to the builder.
		 *
		 * @param elements The entries to add
		 * @return This builder
		 */
		public Builder addAll(Iterable<Entry> elements) {
			this.shadowed.addAll(elements);
			return this;
		}
		/**
		 * Adds all entries from an iterator to the builder.
		 *
		 * @param elements The entries to add
		 * @return This builder
		 */
		public Builder addAll(Iterator<Entry> elements) {
			this.shadowed.addAll(elements);
			return this;
		}

		/**
		 * Build the weighted gene list.
		 * @return the weighted gene list
		 */
		abstract IWeightedGeneList build();
	}

	/**
	 * Factory for weighted gene lists.
	 *
	 * @author liudongyu
	 */
	interface WeightedGeneListFactory {
		/**
		 * Creates a weighted gene list from the specified entries.
		 *
		 * @param list The entries
		 * @return The weighted gene list
		 */
		IWeightedGeneList create(ImmutableList<Entry> list);
	}
}
