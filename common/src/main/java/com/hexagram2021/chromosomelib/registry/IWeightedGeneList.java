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

public interface IWeightedGeneList {
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

	class Context {
		@Nullable
		private final LevelReader level;
		private final BlockPos blockPos;
		private final RandomSource random;
		@Nullable
		private final Holder<GeneLocus> locus;
		@Nullable
		private final ChromosomeType type;
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

		public static Context of(RandomSource random) {
			return new Context(null, BlockPos.ZERO, random);
		}
		public static Context of(ServerLevelAccessor serverLevel, BlockPos blockPos) {
			return new Context(serverLevel, blockPos, RandomSource.create());
		}
		public static Context of(ServerLevelAccessor serverLevel, BlockPos blockPos, RandomSource random) {
			return new Context(serverLevel, blockPos, random);
		}

		public RandomSource random() {
			return this.random;
		}

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

		@Nullable
		public Holder<Biome> biome() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBiome(this.blockPos);
		}

		@Nullable
		public BlockEntity blockEntity() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockEntity(this.blockPos);
		}

		@Nullable
		public BlockState blockState() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockState(this.blockPos);
		}

		@Nullable
		public BlockState below() {
			if(this.level == null) {
				return null;
			}
			return this.level.getBlockState(this.blockPos.below());
		}

		@Nullable
		public FluidState fluidState() {
			if(this.level == null) {
				return null;
			}
			return this.level.getFluidState(this.blockPos);
		}

		public Context withLast(@Nullable ChromosomeInstance last) {
			return new Context(this.level, this.blockPos, this.random, this.locus, this.type, last);
		}

		public Context withLocusAndType(@Nullable Holder<GeneLocus> locus, @Nullable ChromosomeType type) {
			return new Context(this.level, this.blockPos, this.random, locus, type, this.last);
		}
	}

	record Entry(Holder<Gene> gene, int weight) {
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

	abstract class Builder {
		protected final ImmutableList.Builder<Entry> shadowed = ImmutableList.builder();

		public Builder add(Holder<Gene> gene, int weight) {
			this.shadowed.add(Entry.of(gene, weight));
			return this;
		}
		public Builder add(Entry... elements) {
			this.shadowed.add(elements);
			return this;
		}

		public Builder addAll(Iterable<Entry> elements) {
			this.shadowed.addAll(elements);
			return this;
		}
		public Builder addAll(Iterator<Entry> elements) {
			this.shadowed.addAll(elements);
			return this;
		}

		abstract IWeightedGeneList build();
	}

	interface WeightedGeneListFactory {
		IWeightedGeneList create(ImmutableList<Entry> list);
	}
}
