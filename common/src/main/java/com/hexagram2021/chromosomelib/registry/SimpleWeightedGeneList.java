package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.Holder;

import java.util.stream.Stream;

/**
 * Simple implementation of weighted gene list.
 */
public class SimpleWeightedGeneList implements IWeightedGeneList {
	protected final ImmutableList<Entry> entries;
	protected final IntList weightPrefixSums;
	protected final int totalWeight;

	protected SimpleWeightedGeneList(ImmutableList<Entry> entries) {
		this.entries = entries;
		ImmutableList.Builder<Integer> wpsBuilder = ImmutableList.builder();
		int temp = 0;
		for(int i = 0; i < entries.size(); ++i) {
			temp += entries.get(i).weight();
			wpsBuilder.add(temp);
		}
		this.weightPrefixSums = new IntImmutableList(wpsBuilder.build());
		this.totalWeight = temp;
	}

	@Override
	public Holder<Gene> getRandomGene(Context context) {
		return this.entries.get(IWeightedGeneList.upperBound(this.weightPrefixSums, context.random().nextInt(this.totalWeight))).gene();
	}

	@Override
	public int totalWeight(Context context) {
		return this.totalWeight;
	}

	@Override
	public Stream<Holder<Gene>> allGenes() {
		return this.entries.stream().map(Entry::gene).distinct();
	}

	/**
	 * Create a builder for SimpleWeightedGeneList.
	 * @return a builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends IWeightedGeneList.Builder {
		@Override
		SimpleWeightedGeneList build() {
			return new SimpleWeightedGeneList(this.shadowed.build());
		}
	}

	@SuppressWarnings({"unused", "java:S106", "java:S1192"})
	private static void unusedMain(String[] args) {
		int[] a = {1, 6, 8, 12, 15, 17, 20};
		int u = IWeightedGeneList.upperBound(IntList.of(a), 5);
		System.out.printf("a[%d] = %d%n", u, a[u]);
		u = IWeightedGeneList.upperBound(IntList.of(a), 16);
		System.out.printf("a[%d] = %d%n", u, a[u]);
		u = IWeightedGeneList.upperBound(IntList.of(a), 25);
		try {
			System.out.printf("a[%d] = %d%n", u, a[u]);
		} catch (ArrayIndexOutOfBoundsException ignored) {
			System.out.printf("Out of bounds %d%n", u);
		}
		u = IWeightedGeneList.upperBound(IntList.of(a), 12);
		System.out.printf("a[%d] = %d%n", u, a[u]);

		int[] b = {1, 3, 3, 3, 3, 3, 20};
		u = IWeightedGeneList.upperBound(IntList.of(b), 3);
		System.out.printf("b[%d] = %d%n", u, b[u]);
		u = IWeightedGeneList.upperBound(IntList.of(b), 2);
		System.out.printf("b[%d] = %d%n", u, b[u]);
	}
}
