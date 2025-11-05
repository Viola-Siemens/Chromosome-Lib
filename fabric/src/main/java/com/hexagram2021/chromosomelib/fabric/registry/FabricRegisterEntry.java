package com.hexagram2021.chromosomelib.fabric.registry;

import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.stream.Stream;

@SuppressWarnings("java:S2160")
public class FabricRegisterEntry<T> extends AbstractRegisterEntry<T> {
	private final Holder<T> holder;

	public FabricRegisterEntry(Holder<T> holder, ResourceKey<T> key) {
		super(key);
		this.holder = holder;
	}

	@Override
	public boolean is(TagKey<T> tag) {
		return this.holder.is(tag);
	}

	@Override
	public Stream<TagKey<T>> tags() {
		return this.holder.tags();
	}

	@Override
	public boolean isBound() {
		return this.holder.isBound();
	}

	@Override
	public boolean canSerializeIn(HolderOwner<T> owner) {
		return this.holder.canSerializeIn(owner);
	}

	@Override
	public T value() {
		return this.holder.value();
	}

	@Override
	public Holder<T> asHolder() {
		return this.holder;
	}
}
