package com.hexagram2021.chromosomelib.forge;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Maps;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.forge.event.SolveAfterAssigningTraitEvent;
import com.hexagram2021.chromosomelib.forge.event.SolveUnpairedChromosomesEvent;
import com.hexagram2021.chromosomelib.forge.registry.ForgeRegisterEntry;
import com.hexagram2021.chromosomelib.platform.services.IPlatformHelper;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Forge implementation of {@link IPlatformHelper}.
 *
 * @author liudongyu
 */
@ApiStatus.Internal
public class ForgePlatformHelper implements IPlatformHelper {
	private static final Map<String, DeferredRegister<Chromosome>> CHROMOSOME_REGISTERS = Maps.newConcurrentMap();
	private static final Map<String, DeferredRegister<GeneLocus>> GENE_LOCI_REGISTERS = Maps.newConcurrentMap();
	private static final Map<String, DeferredRegister<Gene>> GENE_REGISTERS = Maps.newConcurrentMap();
	private static final Map<String, DeferredRegister<Trait>> TRAIT_REGISTERS = Maps.newConcurrentMap();
	private static final Map<String, DeferredRegister<TraitType>> TRAIT_TYPE_REGISTERS = Maps.newConcurrentMap();

	@Override
	public AbstractRegisterEntry<Chromosome> registerChromosome(ResourceLocation id, Supplier<Chromosome> chromosome) {
		return new ForgeRegisterEntry<>(
				CHROMOSOME_REGISTERS
						.computeIfAbsent(id.getNamespace(), namespace -> create(CLRegistries.CHROMOSOMES, namespace))
						.register(id.getPath(), chromosome),
				ResourceKey.create(CLRegistries.CHROMOSOMES, id)
		);
	}

	@Override
	public AbstractRegisterEntry<GeneLocus> registerGeneLocus(ResourceLocation id, Supplier<GeneLocus> geneLocus) {
		return new ForgeRegisterEntry<>(
				GENE_LOCI_REGISTERS
						.computeIfAbsent(id.getNamespace(), namespace -> create(CLRegistries.GENE_LOCI, namespace))
						.register(id.getPath(), geneLocus),
				ResourceKey.create(CLRegistries.GENE_LOCI, id)
		);
	}

	@Override
	public AbstractRegisterEntry<Gene> registerGene(ResourceLocation id, Supplier<Gene> gene) {
		return new ForgeRegisterEntry<>(
				GENE_REGISTERS
						.computeIfAbsent(id.getNamespace(), namespace -> create(CLRegistries.GENES, namespace))
						.register(id.getPath(), gene),
				ResourceKey.create(CLRegistries.GENES, id)
		);
	}

	@Override
	public AbstractRegisterEntry<Trait> registerTrait(ResourceLocation id, Supplier<Trait> trait) {
		return new ForgeRegisterEntry<>(
				TRAIT_REGISTERS
						.computeIfAbsent(id.getNamespace(), namespace -> create(CLRegistries.TRAITS, namespace))
						.register(id.getPath(), trait),
				ResourceKey.create(CLRegistries.TRAITS, id)
		);
	}

	@Override
	public AbstractRegisterEntry<TraitType> registerTraitType(ResourceLocation id, Supplier<TraitType> traitType) {
		return new ForgeRegisterEntry<>(
				TRAIT_TYPE_REGISTERS
						.computeIfAbsent(id.getNamespace(), namespace -> create(CLRegistries.TRAIT_TYPES, namespace))
						.register(id.getPath(), traitType),
				ResourceKey.create(CLRegistries.TRAIT_TYPES, id)
		);
	}

	@Override
	public boolean solveUnpairedChromosomesToBreed(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomeInstances, RandomSource random, ImmutableCollection.Builder<ChromosomeInstance> builder) {
		SolveUnpairedChromosomesEvent event = new SolveUnpairedChromosomesEvent(entityType, chromosomeInstances, random, builder);
		return MinecraftForge.EVENT_BUS.post(event);
	}

	@Override
	public void solveAfterAssigningTrait(LivingEntity livingEntity, Map<Holder<TraitType>, Holder<Trait>> map, Predicate<Holder<Trait>> hasTrait) {
		SolveAfterAssigningTraitEvent event = new SolveAfterAssigningTraitEvent(livingEntity, map, hasTrait);
		MinecraftForge.EVENT_BUS.post(event);
	}

	private static final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
	private static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> key, String modid) {
		DeferredRegister<T> ret = DeferredRegister.create(key, modid);
		ret.register(MOD_BUS);
		return ret;
	}
}
