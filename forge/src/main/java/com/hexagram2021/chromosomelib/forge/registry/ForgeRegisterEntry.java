package com.hexagram2021.chromosomelib.forge.registry;

import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class ForgeRegisterEntry<T> extends AbstractRegisterEntry<T> {
	private final RegistryObject<T> registryObject;

	public ForgeRegisterEntry(RegistryObject<T> registryObject, ResourceKey<T> key) {
		super(key);
		this.registryObject = registryObject;
	}

	@Override
	public boolean is(TagKey<T> tag) {
		return this.registryObject.getHolder().map(holder -> holder.is(tag)).orElse(false);
	}

	@Override
	public Stream<TagKey<T>> tags() {
		return this.registryObject.getHolder().map(Holder::tags).orElseGet(Stream::empty);
	}

	@Override
	public boolean isBound() {
		return this.registryObject.getHolder().map(Holder::isBound).orElse(false);
	}

	@Override
	public boolean canSerializeIn(HolderOwner<T> owner) {
		return this.registryObject.getHolder().map(holder -> holder.canSerializeIn(owner)).orElse(false);
	}

	@Override
	public T value() {
		return this.registryObject.get();
	}

	@Override @Nullable
	public Holder<T> asHolder() {
		return this.registryObject.getHolder().orElse(null);
	}
}
