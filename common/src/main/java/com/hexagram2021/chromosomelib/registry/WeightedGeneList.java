package com.hexagram2021.chromosomelib.registry;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("java:S1123")
public class WeightedGeneList implements List<WeightedGeneList.Entry> {
	private final ImmutableList<Entry> entries;
	private final IntList weightPrefixSums;
	private final int totalWeight;

	private WeightedGeneList(ImmutableList<Entry> entries) {
		this.entries = entries;
		ImmutableList.Builder<Integer> wpsBuilder = ImmutableList.builder();
		int temp = 0;
		for(int i = 0; i < entries.size(); ++i) {
			temp += entries.get(i).weight();
			wpsBuilder.add(temp);
		}
		this.weightPrefixSums = new IntImmutableList(wpsBuilder.build());
		this.totalWeight = entries.stream().mapToInt(Entry::weight).sum();
	}

	@Override
	public int size() {
		return this.entries.size();
	}

	@Override
	public boolean isEmpty() {
		return this.entries.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.entries.contains(o);
	}

	@Override
	public Iterator<Entry> iterator() {
		return this.entries.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.entries.toArray();
	}

	@Override
	public @NotNull <T> T[] toArray(@NotNull T[] a) {
		return this.entries.toArray(a);
	}

	@Override @Deprecated
	public boolean add(Entry entry) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.entries.containsAll(c);
	}

	@Override @Deprecated
	public boolean addAll(@NotNull Collection<? extends Entry> c) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public boolean addAll(int index, @NotNull Collection<? extends Entry> c) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public boolean removeAll(@NotNull Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public boolean retainAll(@NotNull Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Entry get(int index) {
		return this.entries.get(index);
	}

	@Override @Deprecated
	public Entry set(int index, Entry element) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public void add(int index, Entry element) {
		throw new UnsupportedOperationException();
	}

	@Override @Deprecated
	public Entry remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		return this.entries.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.entries.lastIndexOf(o);
	}

	@Override
	public ListIterator<Entry> listIterator() {
		return this.entries.listIterator();
	}

	@Override
	public ListIterator<Entry> listIterator(int index) {
		return this.entries.listIterator(index);
	}

	@Override
	public List<Entry> subList(int fromIndex, int toIndex) {
		return this.entries.subList(fromIndex, toIndex);
	}

	public Holder<Gene> getRandomGene(RandomSource random) {
		return this.entries.get(upperBound(this.weightPrefixSums.toIntArray(), random.nextInt(this.totalWeight))).gene();
	}

	/**
	 * Binary Search to find the index of the first element in the array that is greater than the given value.
	 * @param a		The array to search
	 * @param value	The value to search for
	 * @return The index of the first element in the array that is greater than the given value.
	 */
	private static int upperBound(int[] a, int value) {
		int l = 0;
		int r = a.length;
		while(l < r) {
			int mid = (l + r) >> 1;
			if(a[mid] <= value) {
				l = mid + 1;
			} else {
				r = mid;
			}
		}
		return l;
	}

	public record Entry(Holder<Gene> gene, int weight) {
		public static Entry of(Holder<Gene> gene, int weight) {
			return new Entry(gene, weight);
		}
	}

	public static WeightedGeneList of(ImmutableList.Builder<Entry> builder) {
		return new WeightedGeneList(builder.build());
	}

	@SuppressWarnings({"unused", "java:S106", "java:S1192"})
	public static void unused(String[] args) {
		int[] a = {1, 6, 8, 12, 15, 17, 20};
		int u = upperBound(a, 5);
		System.out.printf("a[%d] = %d%n", u, a[u]);
		u = upperBound(a, 16);
		System.out.printf("a[%d] = %d%n", u, a[u]);
		u = upperBound(a, 25);
		try {
			System.out.printf("a[%d] = %d%n", u, a[u]);
		} catch (ArrayIndexOutOfBoundsException ignored) {
			System.out.printf("Out of bounds %d%n", u);
		}
		u = upperBound(a, 12);
		System.out.printf("a[%d] = %d%n", u, a[u]);

		int[] b = {1, 3, 3, 3, 3, 3, 20};
		u = upperBound(b, 3);
		System.out.printf("b[%d] = %d%n", u, b[u]);
		u = upperBound(b, 2);
		System.out.printf("b[%d] = %d%n", u, b[u]);
	}
}
