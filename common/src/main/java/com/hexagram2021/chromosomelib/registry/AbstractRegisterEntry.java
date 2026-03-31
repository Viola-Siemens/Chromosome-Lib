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

/**
 * Abstract register entry.
 * @param <T>	the type of the entry
 * @author liudongyu
 */
@SuppressWarnings({"unused", "java:S3038"})
public abstract class AbstractRegisterEntry<T> implements Holder<T>, Supplier<T> {
	/**
	 * resource key of the entry
	 */
	protected final ResourceKey<T> key;

	/**
	 * Constructor.
	 * @param key	resource key of the entry
	 */
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

	/**
	 * @see Holder.Reference#kind
	 * @return kind of the entry.
	 */
	@Override
	public Kind kind() {
		return Kind.REFERENCE;
	}

	/**
	 * @see Holder.Reference#canSerializeIn
	 * @param owner	owner of the entry
	 * @return true if the entry can be serialized in the owner.
	 */
	@Override
	public abstract boolean canSerializeIn(HolderOwner<T> owner);

	/**
	 * @see Holder#value
	 * @return value of the entry.
	 */
	@Override
	public abstract T value();

	/**
	 * @see AbstractRegisterEntry#value
	 * @return value of the entry.
	 */
	@Override
	public T get() {
		return this.value();
	}

	/**
	 * @param obj	another object
	 * @return true if the object is equal to this object.
	 */
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

	/**
	 * @return hash code of the entry.
	 */
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	/**
	 * @return string representation of the entry.
	 */
	@Override
	public String toString() {
		return "RegisterEntry{" + this.key + "}";
	}

	/**
	 * Get the vanilla holder of the entry.
	 * @return vanilla holder of the entry.
	 */
	public abstract Holder<T> asHolder();

	/**
	 * @return optional of the entry. null if the entry is not bound.
	 */
	public Optional<T> asOptional() {
		return this.isBound() ? Optional.of(this.value()) : Optional.empty();
	}

	/**
	 * Get the id of the entry.
	 * @return id of the entry.
	 */
	public ResourceLocation id() {
		return this.key.location();
	}

	/**
	 * Get the key of the entry.
	 * @return key of the entry.
	 */
	public ResourceKey<T> key() {
		return this.key;
	}

	private static final Comparator<ResourceKey<?>> RESOURCE_KEY_COMPARATOR = (a, b) -> {
		int regDiff = a.registry().compareTo(b.registry());
		if(regDiff == 0) {
			return a.location().compareTo(b.location());
		}
		return regDiff;
	};

	/**
	 * Compare two holders.
	 * @param a	first holder
	 * @param b	second holder
	 * @return true if the holders are equal. false otherwise.
	 */
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

	/**
	 * Get the hash code of the holder.
	 * @param holder	to get the hash code
	 * @return hash code of the holder.
	 */
	public static int hashCode(Holder<?> holder) {
		if(holder instanceof AbstractRegisterEntry<?> registerEntry) {
			return registerEntry.key().hashCode();
		}
		return holder.hashCode();
	}

	/**
	 * Compare two holders.
	 * @param a	first holder
	 * @param b	second holder
	 * @return the result of the comparison.
	 * @param <T>	the type of the entry
	 */
	public static <T> int compare(Holder<T> a, Holder<T> b) {
		if(a instanceof AbstractRegisterEntry<T> registerEntryA) {
			if(b instanceof AbstractRegisterEntry<T> registerEntryB) {
				return RESOURCE_KEY_COMPARATOR.compare(registerEntryA.key(), registerEntryB.key());
			}
			if(b instanceof Holder.Reference<T> referenceB) {
				return RESOURCE_KEY_COMPARATOR.compare(registerEntryA.key(), referenceB.key());
			}
			return 1;
		}
		if(a instanceof Holder.Reference<T> referenceA) {
			if(b instanceof AbstractRegisterEntry<T> registerEntryB) {
				return RESOURCE_KEY_COMPARATOR.compare(referenceA.key(), registerEntryB.key());
			}
			if(b instanceof Holder.Reference<T> referenceB) {
				return RESOURCE_KEY_COMPARATOR.compare(referenceA.key(), referenceB.key());
			}
			return 1;
		}
		if(b instanceof AbstractRegisterEntry<T> || b instanceof Holder.Reference<T>) {
			return -1;
		}
		return Integer.compare(a.hashCode(), b.hashCode());
	}

	/**
	 * Create a tree map with the comparator. Users should use this instead of {@link com.google.common.collect.Maps#newIdentityHashMap()}.
	 * @return a tree map with the comparator.
	 * @param <T>	the type of the entry
	 * @param <V>	the type of the value
	 */
	public static <T, V> Map<Holder<T>, V> newHolderTreeMap() {
		return new TreeMap<>(AbstractRegisterEntry::compare);
	}

	/**
	 * Create a tree set with the comparator. Users should use this instead of {@link com.google.common.collect.Sets#newIdentityHashSet()}.
	 * @return a tree set with the comparator.
	 * @param <T>	the type of the entry
	 */
	public static <T> Set<Holder<T>> newHolderTreeSet() {
		return new TreeSet<>(AbstractRegisterEntry::compare);
	}

	/**
	 * Create a tree map with the comparator. Users should use this instead of {@link com.google.common.collect.Maps#newIdentityHashMap()}.
	 * @return a tree map with the comparator.
	 * @param <T>	the type of the entry
	 */
	public static <T> Object2IntMap<Holder<T>> newHolderObject2IntTreeMap() {
		return new Object2IntRBTreeMap<>(AbstractRegisterEntry::compare);
	}
}
