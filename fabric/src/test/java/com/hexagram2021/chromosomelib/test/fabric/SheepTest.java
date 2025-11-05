package com.hexagram2021.chromosomelib.test.fabric;

import com.google.common.collect.ImmutableSet;
import com.hexagram2021.chromosomelib.common.chromosome.BuiltInChromosomes;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocusInstance;
import com.hexagram2021.chromosomelib.common.util.Breeders;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import com.hexagram2021.chromosomelib.test.fabric.dummy.DummyChromosomeCarrier;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

class SheepTest {
	private final DummyChromosomeCarrier sheep1 = new DummyChromosomeCarrier(EntityType.SHEEP);
	private final DummyChromosomeCarrier sheep2 = new DummyChromosomeCarrier(EntityType.SHEEP);

	@BeforeAll
	static void bootstrap() {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		SheepColorDesigner.init();
		RegistryRelations.freezeAndBuild();
	}

	@Test
	void testSheepTopologicalSort() {
		Assertions.assertEquals(0, SheepColorDesigner.Genes.MELANIN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.Genes.MELANIN_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.URANIDIN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.Genes.URANIDIN_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.MELANIN_METABOLIC_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.Genes.MELANIN_METABOLIC_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.DILUTION_ID.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.DILUTION_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.NONE_PINK_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.Genes.PINK_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.MAGENTA_D.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.Genes.GREEN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.Genes.NORMAL_COLOR_R.value().topologicalOrder);
	}

	static final double Z_95 = 1.644854D;
	static final double Z_975 = 1.959964D;
	static final double Z_9875 = 2.241403D;
	static final int N = 10000;
	static final double P_3_1 = 0.75D;
	static final double BOUND_3_1 = Z_9875 * Math.sqrt(P_3_1 * (1.0D - P_3_1) / N);

	/**
	 * Test segregation ratio 3:1 in monogenic inheritance circumstance.
	 * <p>Cheer up! If everything goes right, there is still a 1.25% chance of failing :)
	 */
	@Test
	void testSheepBreedSegregationRatio1() {
		// Yes, this is bimaternal reproduction!
		this.sheep1.chromosomelib$setChromosomes(buildPinkHomozygoteSheepChromosomes(ChromosomeType.LEFT));
		this.sheep2.chromosomelib$setChromosomes(buildWhiteHomozygoteSheepChromosomes(ChromosomeType.LEFT));

		RandomSource random = RandomSource.create();
		int sum = 0;
		DummyChromosomeCarrier child1 = new DummyChromosomeCarrier(EntityType.SHEEP);
		DummyChromosomeCarrier child2 = new DummyChromosomeCarrier(EntityType.SHEEP);
		DummyChromosomeCarrier grandChild = new DummyChromosomeCarrier(EntityType.SHEEP);
		int[] childCount = new int[3];
		int[] grandChildCount = new int[3];
		for(int i = 0; i < N; i++) {
			child1.chromosomelib$setChromosomes(Breeders.breed(
					this.sheep1.entityType(), this.sheep2.entityType(),
					this.sheep1.chromosomelib$getChromosomes(), this.sheep2.chromosomelib$getChromosomes(),
					random
			));
			child2.chromosomelib$setChromosomes(Breeders.breed(
					this.sheep1.entityType(), this.sheep2.entityType(),
					this.sheep1.chromosomelib$getChromosomes(), this.sheep2.chromosomelib$getChromosomes(),
					random
			));
			childCount[child1.chromosomelib$getActiveGenes().applyAsInt(SheepColorDesigner.Genes.NONE_PINK_D)] += 1;
			childCount[child2.chromosomelib$getActiveGenes().applyAsInt(SheepColorDesigner.Genes.NONE_PINK_D)] += 1;

			grandChild.chromosomelib$setChromosomes(Breeders.breed(
					child1.entityType(), child2.entityType(),
					child1.chromosomelib$getChromosomes(), child2.chromosomelib$getChromosomes(),
					random
			));
			grandChildCount[grandChild.chromosomelib$getActiveGenes().applyAsInt(SheepColorDesigner.Genes.NONE_PINK_D)] += 1;

			if(grandChild.chromosomelib$getActiveTraits().contains(SheepColorDesigner.Traits.WHITE)) {
				sum += 1;
			}
		}
		CLLogger.info("Children:");
		CLLogger.info("  - PP: %d".formatted(childCount[2]));
		CLLogger.info("  - Pp: %d".formatted(childCount[1]));
		CLLogger.info("  - pp: %d".formatted(childCount[0]));
		CLLogger.info("Grandchildren:");
		CLLogger.info("  - PP: %d".formatted(grandChildCount[2]));
		CLLogger.info("  - Pp: %d".formatted(grandChildCount[1]));
		CLLogger.info("  - pp: %d".formatted(grandChildCount[0]));

		double mean = (double)sum / N;
		if(mean < P_3_1 - BOUND_3_1 || mean > P_3_1 + BOUND_3_1) {
			Assertions.fail("Frequency %.6f should be in confidence interval [%.6f, %.6f]. Don't worry it is still a 1.25%% chance of misjudgement.".formatted(
					mean, P_3_1 - BOUND_3_1, P_3_1 + BOUND_3_1
			));
		}
		CLLogger.info("Mean = %.6f".formatted(mean));
	}

	private static Collection<ChromosomeInstance> buildPinkHomozygoteSheepChromosomes(ChromosomeType gender) {
		ImmutableSet.Builder<ChromosomeInstance> builder = ImmutableSet.builder();

		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_R),
				new GeneLocusInstance(SheepColorDesigner.Genes.PINK_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_R),
				new GeneLocusInstance(SheepColorDesigner.Genes.PINK_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.NORMAL_COLOR_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.NORMAL_COLOR_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, gender, gender == ChromosomeType.LEFT ? List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		) : List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.SRY)
		)));

		return builder.build();
	}

	private static Collection<ChromosomeInstance> buildWhiteHomozygoteSheepChromosomes(ChromosomeType gender) {
		ImmutableSet.Builder<ChromosomeInstance> builder = ImmutableSet.builder();

		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_R),
				new GeneLocusInstance(SheepColorDesigner.Genes.NONE_PINK_D)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_R),
				new GeneLocusInstance(SheepColorDesigner.Genes.NONE_PINK_D)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, gender, gender == ChromosomeType.LEFT ? List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		) : List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.SRY)
		)));

		return builder.build();
	}

	private static Collection<ChromosomeInstance> buildBlackHomozygoteSheepChromosomes(ChromosomeType gender) {
		ImmutableSet.Builder<ChromosomeInstance> builder = ImmutableSet.builder();

		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_D),
				new GeneLocusInstance(SheepColorDesigner.Genes.NONE_PINK_D)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_1, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_D),
				new GeneLocusInstance(SheepColorDesigner.Genes.NONE_PINK_D)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_2, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_3, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_4, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_5, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_6, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.MELANIN_METABOLIC_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_7, ChromosomeType.RIGHT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.DILUTION_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_8, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_9, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_10, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_11, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_12, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_13, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_14, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_15, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_16, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_17, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_18, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_19, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_20, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_21, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_22, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_23, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_24, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_25, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.LEFT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_26, ChromosomeType.RIGHT));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT, List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		)));
		builder.add(ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, gender, gender == ChromosomeType.LEFT ? List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.URANIDIN_R)
		) : List.of(
				new GeneLocusInstance(SheepColorDesigner.Genes.SRY)
		)));

		return builder.build();
	}
}
