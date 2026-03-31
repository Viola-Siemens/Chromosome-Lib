package com.hexagram2021.chromosomelib.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

/**
 * Entity type tags for Chromosome Lib. <br/>
 * These tags group entity types that share chromosomes or traits.
 *
 * @author liudongyu
 */
public final class CLEntityTypeTags {
	/**
	 * Tag for human-like entities (e.g., villagers, pillagers, wandering traders).
	 */
	public static final TagKey<EntityType<?>> HUMANS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MODID, "humans"));

	private CLEntityTypeTags() {
	}
}
