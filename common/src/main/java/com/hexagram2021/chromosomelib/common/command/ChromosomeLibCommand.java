package com.hexagram2021.chromosomelib.common.command;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.hexagram2021.chromosomelib.ChromosomeLib;
import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import com.hexagram2021.chromosomelib.registry.AbstractRegisterEntry;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

/**
 * Commands for showing chromosome information of entities.
 *
 * @author liudongyu
 */
@SuppressWarnings("unchecked")
@ApiStatus.Internal
public final class ChromosomeLibCommand {
	private static final String ENTITY_ARGUMENT = "entity";
	private static final String CHROMOSOME_INDEX_ARGUMENT = "chromosome_index";
	private static final String GENE_LOCUS_INDEX_ARGUMENT = "gene_locus_index";
	private static final String GENE_1_ARGUMENT = "gene_1";
	private static final String GENE_2_ARGUMENT = "gene_2";

	/**
	 * Register commands.
	 *
	 * @return command builder
	 */
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("chromosomelib").requires(stack -> stack.hasPermission(2)).then(
				Commands.literal("show")
						.executes(ctx -> show(ctx.getSource().getPlayer(), ctx.getSource().getEntity())).then(
								Commands.argument(ENTITY_ARGUMENT, EntityArgument.entity())
										.executes(ctx -> show(ctx.getSource().getPlayer(), EntityArgument.getEntity(ctx, ENTITY_ARGUMENT)))
						)
		).then(
				Commands.literal("reassign")
						.executes(ctx -> reassign(ctx.getSource().getEntity())).then(
								Commands.argument(ENTITY_ARGUMENT, EntityArgument.entity())
										.executes(ctx -> reassign(EntityArgument.getEntity(ctx, ENTITY_ARGUMENT)))
						)
		).then(
				Commands.literal("set").then(
						Commands.argument(ENTITY_ARGUMENT, EntityArgument.entity()).then(
								Commands.argument(CHROMOSOME_INDEX_ARGUMENT, IntegerArgumentType.integer(0)).then(
										Commands.argument(GENE_LOCUS_INDEX_ARGUMENT, IntegerArgumentType.integer(0)).then(
												Commands.argument(GENE_1_ARGUMENT, ResourceLocationArgument.id()).suggests(GENE_SUGGESTIONS).executes(ctx -> set(
														EntityArgument.getEntity(ctx, ENTITY_ARGUMENT),
														IntegerArgumentType.getInteger(ctx, CHROMOSOME_INDEX_ARGUMENT),
														IntegerArgumentType.getInteger(ctx, GENE_LOCUS_INDEX_ARGUMENT),
														ResourceLocationArgument.getId(ctx, GENE_1_ARGUMENT),
														ResourceLocationArgument.getId(ctx, GENE_1_ARGUMENT)
												)).then(
														Commands.argument(GENE_2_ARGUMENT, ResourceLocationArgument.id()).suggests(GENE_SUGGESTIONS)
																.executes(ctx -> set(
																		EntityArgument.getEntity(ctx, ENTITY_ARGUMENT),
																		IntegerArgumentType.getInteger(ctx, CHROMOSOME_INDEX_ARGUMENT),
																		IntegerArgumentType.getInteger(ctx, GENE_LOCUS_INDEX_ARGUMENT),
																		ResourceLocationArgument.getId(ctx, GENE_1_ARGUMENT),
																		ResourceLocationArgument.getId(ctx, GENE_2_ARGUMENT)
																))
												)
										)

								)
						)
				)
		);
	}

	private static final Registry<Chromosome> chromosomeRegistry = (Registry<Chromosome>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.CHROMOSOMES.location()));

	private static final Registry<Gene> geneRegistry = (Registry<Gene>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENES.location()));

	private static final SuggestionProvider<CommandSourceStack> GENE_SUGGESTIONS = SuggestionProviders.register(
			new ResourceLocation(ChromosomeLib.MODID, "genes"),
			(context, builder) -> SharedSuggestionProvider.suggestResource(geneRegistry.holders().flatMap(holder -> holder.unwrapKey().stream().map(ResourceKey::location)), builder)
	);

	private static String chromosome2Loc(Holder<Chromosome> holder) {
		return holder.unwrap().map(key -> key.location().toString(), value -> {
			ResourceLocation key = chromosomeRegistry.getKey(value);
			if(key == null) {
				return "";
			}
			return key.toString();
		});
	}

	private static String gene2Loc(Holder<Gene> holder) {
		return holder.unwrap().map(key -> key.location().toString(), value -> {
			ResourceLocation key = geneRegistry.getKey(value);
			if(key == null) {
				return "";
			}
			return key.toString();
		});
	}

	private static int show(@Nullable ServerPlayer player, @Nullable Entity entity) {
		Map<Holder<Chromosome>, Object2IntMap<Holder<Gene>>> toShow = AbstractRegisterEntry.newHolderTreeMap();
		if(player != null && entity instanceof IChromosomeCarrier carrier) {
			ToIntFunction<Holder<Gene>> activeGenes = carrier.chromosomelib$getActiveGenes();
			carrier.chromosomelib$getChromosomes().forEach(chromosomeInstance -> {
				Object2IntMap<Holder<Gene>> chromosomeGenes = toShow.computeIfAbsent(chromosomeInstance.chromosome(), ignored -> AbstractRegisterEntry.newHolderObject2IntTreeMap());
				chromosomeInstance.geneLocusInstances().forEach((index, geneLocusInstance) -> chromosomeGenes.put(geneLocusInstance.gene(), activeGenes.applyAsInt(geneLocusInstance.gene())));
			});
			StringBuilder builder = new StringBuilder();
			toShow.keySet().stream().sorted(Comparator.comparing(ChromosomeLibCommand::chromosome2Loc)).forEach(chromosome -> {
				Object2IntMap<Holder<Gene>> chromosomeGenes = toShow.get(chromosome);
				if(!chromosomeGenes.isEmpty()) {
					builder.append(chromosome2Loc(chromosome)).append(":\n");
					chromosomeGenes.keySet().forEach(gene -> builder.append("  - ")
							.append(gene2Loc(gene))
							.append(": ")
							.append(activeGenes.applyAsInt(gene))
							.append('\n'));
				}
			});

			player.sendSystemMessage(Component.literal(builder.toString()));
		}
		return Command.SINGLE_SUCCESS;
	}

	private static int reassign(@Nullable Entity entity) {
		if(entity instanceof IChromosomeCarrier carrier) {
			carrier.chromosomelib$resetTraits();
			carrier.chromosomelib$assignTraits();
		}
		return Command.SINGLE_SUCCESS;
	}

	private static final Dynamic2CommandExceptionType GENE_LOCUS_INDEX_MISMATCHED = new Dynamic2CommandExceptionType(
			(expected, found) -> Component.literal("Gene Locus index mismatched. Expected %d, found %d.".formatted((int)expected, (int)found))
	);

	private static int set(@Nullable Entity entity, int chromosomeIndex, int geneLocusIndex, ResourceLocation geneId1, ResourceLocation geneId2) throws CommandSyntaxException {
		Holder<Gene> gene1 = geneRegistry.getHolderOrThrow(ResourceKey.create(CLRegistries.GENES, geneId1));
		Holder<Gene> gene2 = geneRegistry.getHolderOrThrow(ResourceKey.create(CLRegistries.GENES, geneId2));
		if(entity instanceof IChromosomeCarrier carrier) {
			AtomicInteger cnt = new AtomicInteger(0);
			ImmutableSet.Builder<ChromosomeInstance> builder = ImmutableSet.builder();
			for(ChromosomeInstance chromosomeInstance: carrier.chromosomelib$getChromosomes()) {
				int index = Chromosome.index(chromosomeInstance.chromosome(), entity.getType());
				if(index == chromosomeIndex) {
					Set<GeneLocusInstance> geneLocusInstances = Sets.newIdentityHashSet();
					Int2ObjectMap<GeneLocusInstance> original = chromosomeInstance.geneLocusInstances();
					for(int locusIndex: original.keySet()) {
						if(locusIndex == geneLocusIndex) {
							int currentCnt = cnt.get();
							if((currentCnt & 1) == 0) {
								checkGeneLocusIndex(gene1, chromosomeInstance.type(), geneLocusIndex);
								geneLocusInstances.add(new GeneLocusInstance(gene1));
							} else {
								checkGeneLocusIndex(gene2, chromosomeInstance.type(), geneLocusIndex);
								geneLocusInstances.add(new GeneLocusInstance(gene2));
							}
							cnt.set(currentCnt + 1);
						} else {
							geneLocusInstances.add(original.get(locusIndex));
						}
					}
					builder.add(ChromosomeInstance.of(chromosomeInstance.chromosome(), chromosomeInstance.type(), geneLocusInstances));
				} else {
					builder.add(chromosomeInstance);
				}
			}
			carrier.chromosomelib$setChromosomes(builder.build());
		}
		return Command.SINGLE_SUCCESS;
	}

	private static void checkGeneLocusIndex(Holder<Gene> gene, ChromosomeType type, int index) throws CommandSyntaxException {
		int found = Objects.requireNonNull(gene.value().geneLocus).value().index(type);
		if(found != index) {
			throw GENE_LOCUS_INDEX_MISMATCHED.create(index, found);
		}
	}

	private ChromosomeLibCommand() {
	}
}
