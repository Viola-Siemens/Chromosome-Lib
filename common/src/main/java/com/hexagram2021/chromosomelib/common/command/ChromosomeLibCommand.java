package com.hexagram2021.chromosomelib.common.command;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.registry.CLRegistries;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * Commands for showing chromosome information of entities.
 */
@ApiStatus.Internal
public final class ChromosomeLibCommand {
	/**
	 * Register commands.
	 */
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("chromosomelib").requires(stack -> stack.hasPermission(2)).then(
				Commands.literal("show")
						.executes(ctx -> show(ctx.getSource().getPlayer(), ctx.getSource().getEntity()))
						.then(
								Commands.argument("entity", EntityArgument.entity())
										.executes(ctx -> show(ctx.getSource().getPlayer(), EntityArgument.getEntity(ctx, "entity")))
						)
		);
	}

	@Nullable
	private static Registry<Chromosome> chromosomeRegistry = null;

	@Nullable
	private static Registry<Gene> geneRegistry = null;

	private static String chromosome2Loc(Holder<Chromosome> holder) {
		assert chromosomeRegistry != null;
		return holder.unwrap().map(key -> key.location().toString(), value -> {
			ResourceLocation key = chromosomeRegistry.getKey(value);
			if(key == null) {
				return "";
			}
			return key.toString();
		});
	}

	private static String gene2Loc(Holder<Gene> holder) {
		assert geneRegistry != null;
		return holder.unwrap().map(key -> key.location().toString(), value -> {
			ResourceLocation key = geneRegistry.getKey(value);
			if(key == null) {
				return "";
			}
			return key.toString();
		});
	}

	private static final Comparator<ChromosomeInstance> chromosomeInstanceComparator = (o1, o2) -> {
		Holder<Chromosome> c1 = o1.chromosome();
		Holder<Chromosome> c2 = o2.chromosome();
		int i1 = c1.value().index();
		int i2 = c2.value().index();
		if(i1 == i2) {
			return chromosome2Loc(c1).compareTo(chromosome2Loc(c2));
		}
		return Integer.compare(i1, i2);
	};

	@SuppressWarnings("unchecked")
	private static int show(@Nullable ServerPlayer player, @Nullable Entity entity) {
		if(chromosomeRegistry == null) {
			chromosomeRegistry = (Registry<Chromosome>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.CHROMOSOMES.location()));
		}
		if(geneRegistry == null) {
			geneRegistry = (Registry<Gene>) Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(CLRegistries.GENES.location()));
		}
		if(player != null && entity instanceof IChromosomeCarrier carrier) {
			ToIntFunction<Holder<Gene>> activeGenes = carrier.chromosomelib$getActiveGenes();
			StringBuilder builder = new StringBuilder();
			carrier.chromosomelib$getChromosomes().stream().sorted(chromosomeInstanceComparator).forEach(chromosomeInstance -> {
				builder.append(chromosome2Loc(chromosomeInstance.chromosome())).append(":\n");
				chromosomeInstance.geneLocusInstances().values().stream()
						.sorted(Comparator.comparingInt(geneLocusInstance -> geneLocusInstance.index(chromosomeInstance.type())))
						.forEach(geneLocusInstance -> builder.append("  - ")
								.append(gene2Loc(geneLocusInstance.gene()))
								.append(": ")
								.append(activeGenes.applyAsInt(geneLocusInstance.gene()))
								.append('\n'));
			});
			player.sendSystemMessage(Component.literal(builder.toString()));
		}
		return Command.SINGLE_SUCCESS;
	}

	private ChromosomeLibCommand() {
	}
}
