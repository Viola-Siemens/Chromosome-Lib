package com.hexagram2021.chromosomelib.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unused")
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
		return String.format(Locale.ENGLISH, "RegisterEntry{%s}", this.key);
	}

	@Nullable
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
}
