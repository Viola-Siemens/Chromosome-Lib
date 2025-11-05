package com.hexagram2021.chromosomelib.registry;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "java:S3038"})
public abstract class AbstractRegisterEntry<T> implements Holder<T>, Supplier<T> {
	protected final ResourceKey<T> key;

	protected AbstractRegisterEntry(ResourceKey<T> key) {
		this.key = key;
	}

	/**
	 * @param id	id to check
	 * @return true if the entry matches the id.
	 */
	@Override
	public boolean is(ResourceLocation id) {
		return id.equals(this.key.location());
	}

	/**
	 * @param key	key to check
	 * @return true if the entry matches the key.
	 */
	@Override
	public boolean is(ResourceKey<T> key) {
		return this.key.equals(key);
	}

	/**
	 * @param filter	filter to check
	 * @return true if the entry matches the filter.
	 */
	@Override
	public boolean is(Predicate<ResourceKey<T>> filter) {
		return filter.test(this.key);
	}

	/**
	 * @param tag	tag to check
	 * @return true if the entry is in the tag.
	 */
	@Override
	public abstract boolean is(TagKey<T> tag);

	/**
	 * @return tags of the entry.
	 */
	@Override
	public abstract Stream<TagKey<T>> tags();

	/**
	 * @return true if the entry is bound.
	 */
	@Override
	public abstract boolean isBound();

	/**
	 * @see Holder.Reference#unwrap
	 * @return resource key of the entry.
	 */
	@Override
	public Either<ResourceKey<T>, T> unwrap() {
		return Either.left(this.key);
	}

	/**
	 * @see Holder.Reference#unwrapKey
	 * @return resource key of the entry.
	 */
	@Override
	public Optional<ResourceKey<T>> unwrapKey() {
		return Optional.of(this.key);
	}

	@Override
	public Kind kind() {
		return Kind.REFERENCE;
	}

	@Override
	public abstract boolean canSerializeIn(HolderOwner<T> owner);

	@Override
	public abstract T value();

	@Override
	public T get() {
		return this.value();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(obj instanceof Holder.Reference<?> reference) {
			return reference.key() == this.key;
		}
		return obj instanceof Holder<?> h && this.key.equals(h.unwrapKey().orElse(null));
	}

	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	@Override
	public String toString() {
		return "RegisterEntry{" + this.key + "}";
	}

	public abstract Holder<T> asHolder();

	public Optional<T> asOptional() {
		return this.isBound() ? Optional.of(this.value()) : Optional.empty();
	}

	public ResourceLocation id() {
		return this.key.location();
	}

	public ResourceKey<T> key() {
		return this.key;
	}

	private static Comparator<ResourceKey<?>> resourceKeyComparator = (a, b) -> {
		int regDiff = a.registry().compareTo(b.registry());
		if(regDiff == 0) {
			return a.location().compareTo(b.location());
		}
		return regDiff;
	};

	public static boolean equals(Holder<?> a, Holder<?> b) {
		if (a == b) {
			return true;
		}
		if(a instanceof AbstractRegisterEntry<?> registerEntryA) {
			if(b instanceof AbstractRegisterEntry<?> registerEntryB) {
				return registerEntryA.key() == registerEntryB.key();
			}
			return registerEntryA.asHolder() == b;
		}
		if(b instanceof AbstractRegisterEntry<?> registerEntryB) {
			return registerEntryB.asHolder() == a;
		}
		return false;
	}

	public static int hashCode(Holder<?> holder) {
		if(holder instanceof AbstractRegisterEntry<?> registerEntry) {
			return registerEntry.key().hashCode();
		}
		return holder.hashCode();
	}

	public static <T> int compare(Holder<T> a, Holder<T> b) {
		if(a instanceof AbstractRegisterEntry<T> registerEntryA) {
			if(b instanceof AbstractRegisterEntry<T> registerEntryB) {
				return resourceKeyComparator.compare(registerEntryA.key(), registerEntryB.key());
			}
			if(b instanceof Holder.Reference<T> referenceB) {
				return resourceKeyComparator.compare(registerEntryA.key(), referenceB.key());
			}
			return 1;
		}
		if(a instanceof Holder.Reference<T> referenceA) {
			if(b instanceof AbstractRegisterEntry<T> registerEntryB) {
				return resourceKeyComparator.compare(referenceA.key(), registerEntryB.key());
			}
			if(b instanceof Holder.Reference<T> referenceB) {
				return resourceKeyComparator.compare(referenceA.key(), referenceB.key());
			}
			return 1;
		}
		if(b instanceof AbstractRegisterEntry<T> || b instanceof Holder.Reference<T>) {
			return -1;
		}
		return Integer.compare(a.hashCode(), b.hashCode());
	}

	public static <T, V> Map<Holder<T>, V> newHolderTreeMap() {
		return new TreeMap<>(AbstractRegisterEntry::compare);
	}

	public static <T> Set<Holder<T>> newHolderTreeSet() {
		return new TreeSet<>(AbstractRegisterEntry::compare);
	}

	public static <T> Object2IntMap<Holder<T>> newHolderObject2IntTreeMap() {
		return new Object2IntRBTreeMap<>(AbstractRegisterEntry::compare);
	}
}
