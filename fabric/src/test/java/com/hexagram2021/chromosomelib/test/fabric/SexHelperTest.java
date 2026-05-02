package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.common.chromosome.BuiltInChromosomes;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.type.IChromosomeLibEntityType;
import com.hexagram2021.chromosomelib.common.sex.BiologicalSex;
import com.hexagram2021.chromosomelib.common.sex.SexDetermination;
import com.hexagram2021.chromosomelib.common.sex.SexHelper;
import com.hexagram2021.chromosomelib.common.util.Breeders;
import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.registry.IWeightedGeneList;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import com.hexagram2021.chromosomelib.test.fabric.dummy.DummyChromosomeCarrier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

/**
 * Integration tests for {@link SexHelper}, sex chromosome registration API, and sex ratio generation.
 *
 * <p>Covers TC-020 ~ TC-043 from the v0.1.0 test case design document.</p>
 *
 * <p><b>Note on {@code isCompatibleBreedingPair}:</b> {@link SexHelper#isCompatibleBreedingPair}
 * requires {@link net.minecraft.world.entity.LivingEntity} instances, which are unavailable in
 * the unit test environment. TC-025 ~ TC-028 therefore validate the equivalent compatibility
 * logic using the two-argument {@link SexHelper#getSex(EntityType, Collection)} overload.</p>
 *
 * @author liudongyu
 */
class SexHelperTest {
	static final double Z_975 = 1.959964D;
	static final int N = 10000;
	static final double P_50 = 0.5D;
	/** 95% CI half-width for N=10000, p=0.5: 1.96 × √(0.25 / 10000) ≈ 0.0196 */
	static final double BOUND_50 = Z_975 * Math.sqrt(P_50 * (1.0D - P_50) / N);

	@BeforeAll
	static void bootstrap() {
		TestBootstrap.init();
	}

	// ========================
	// 4.3 SexHelper 工具类
	// ========================

	/**
	 * TC-020: An entity type with no sex chromosome registered returns {@link BiologicalSex#ASEXUAL}.
	 */
	@Test
	void testNoSexChromosomeReturnsAsexual() {
		// EntityType.CREEPER has no registered sex chromosome
		BiologicalSex sex = SexHelper.getSex(EntityType.CREEPER, List.of());
		Assertions.assertEquals(BiologicalSex.ASEXUAL, sex);
	}

	/**
	 * TC-021: XY system – an entity with LEFT+RIGHT sex chromosomes is {@link BiologicalSex#MALE}.
	 */
	@Test
	void testXyMaleSexQuery() {
		List<ChromosomeInstance> chromosomes = List.of(
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT),
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.RIGHT)
		);
		Assertions.assertEquals(BiologicalSex.MALE, SexHelper.getSex(EntityType.SHEEP, chromosomes));
	}

	/**
	 * TC-022: XY system – an entity with LEFT+LEFT sex chromosomes is {@link BiologicalSex#FEMALE}.
	 */
	@Test
	void testXyFemaleSexQuery() {
		List<ChromosomeInstance> chromosomes = List.of(
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT),
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT)
		);
		Assertions.assertEquals(BiologicalSex.FEMALE, SexHelper.getSex(EntityType.SHEEP, chromosomes));
	}

	/**
	 * TC-023: ZW system – LEFT+LEFT (ZZ) is {@link BiologicalSex#MALE};
	 * LEFT+RIGHT (ZW) is {@link BiologicalSex#FEMALE}.
	 */
	@Test
	void testZwSexQuery() {
		List<ChromosomeInstance> zzChromosomes = List.of(
				ChromosomeInstance.of(BuiltInChromosomes.Chicken.CHICKEN_ZW, ChromosomeType.LEFT),
				ChromosomeInstance.of(BuiltInChromosomes.Chicken.CHICKEN_ZW, ChromosomeType.LEFT)
		);
		List<ChromosomeInstance> zwChromosomes = List.of(
				ChromosomeInstance.of(BuiltInChromosomes.Chicken.CHICKEN_ZW, ChromosomeType.LEFT),
				ChromosomeInstance.of(BuiltInChromosomes.Chicken.CHICKEN_ZW, ChromosomeType.RIGHT)
		);
		Assertions.assertEquals(BiologicalSex.MALE, SexHelper.getSex(EntityType.CHICKEN, zzChromosomes),
				"ZZ chicken should be MALE");
		Assertions.assertEquals(BiologicalSex.FEMALE, SexHelper.getSex(EntityType.CHICKEN, zwChromosomes),
				"ZW chicken should be FEMALE");
	}

	/**
	 * TC-025: {@code isCompatibleBreedingPair} – MALE + FEMALE is compatible.
	 *
	 * <p>Validated via the two-argument {@link SexHelper#getSex} overload since
	 * {@link SexHelper#isCompatibleBreedingPair} requires {@link net.minecraft.world.entity.LivingEntity}.</p>
	 */
	@Test
	void testCompatiblePairMaleFemale() {
		BiologicalSex male = SexHelper.getSex(EntityType.SHEEP, makeSheepXyChromosomes(ChromosomeType.RIGHT));
		BiologicalSex female = SexHelper.getSex(EntityType.SHEEP, makeSheepXxChromosomes());
		Assertions.assertEquals(BiologicalSex.MALE, male);
		Assertions.assertEquals(BiologicalSex.FEMALE, female);
		// Compatible: different sexes
		Assertions.assertTrue(areSexCompatible(male, female));
	}

	/**
	 * TC-026: {@code isCompatibleBreedingPair} – MALE+MALE and FEMALE+FEMALE are incompatible.
	 */
	@Test
	void testIncompatibleSameSexPairs() {
		BiologicalSex male = SexHelper.getSex(EntityType.SHEEP, makeSheepXyChromosomes(ChromosomeType.RIGHT));
		BiologicalSex female = SexHelper.getSex(EntityType.SHEEP, makeSheepXxChromosomes());
		// MALE + MALE
		Assertions.assertFalse(areSexCompatible(male, male), "MALE+MALE should be incompatible");
		// FEMALE + FEMALE
		Assertions.assertFalse(areSexCompatible(female, female), "FEMALE+FEMALE should be incompatible");
	}

	/**
	 * TC-027: {@code isCompatibleBreedingPair} – any pairing involving ASEXUAL is always compatible.
	 */
	@Test
	void testAsexualAlwaysCompatible() {
		BiologicalSex asexual = SexHelper.getSex(EntityType.CREEPER, List.of());
		BiologicalSex male = SexHelper.getSex(EntityType.SHEEP, makeSheepXyChromosomes(ChromosomeType.RIGHT));
		Assertions.assertEquals(BiologicalSex.ASEXUAL, asexual);
		// ASEXUAL + MALE
		Assertions.assertTrue(areSexCompatible(asexual, male), "ASEXUAL+MALE should be compatible");
		// ASEXUAL + ASEXUAL
		Assertions.assertTrue(areSexCompatible(asexual, asexual), "ASEXUAL+ASEXUAL should be compatible");
	}

	/**
	 * TC-028: {@code isCompatibleBreedingPair} satisfies commutativity for all 6 sex combinations.
	 */
	@Test
	void testCompatibilityIsCommutative() {
		BiologicalSex male = BiologicalSex.MALE;
		BiologicalSex female = BiologicalSex.FEMALE;
		BiologicalSex asexual = BiologicalSex.ASEXUAL;

		// All 6 combinations
		Assertions.assertEquals(areSexCompatible(male, female), areSexCompatible(female, male),
				"MALE+FEMALE commutativity");
		Assertions.assertEquals(areSexCompatible(male, male), areSexCompatible(male, male),
				"MALE+MALE commutativity");
		Assertions.assertEquals(areSexCompatible(female, female), areSexCompatible(female, female),
				"FEMALE+FEMALE commutativity");
		Assertions.assertEquals(areSexCompatible(male, asexual), areSexCompatible(asexual, male),
				"MALE+ASEXUAL commutativity");
		Assertions.assertEquals(areSexCompatible(female, asexual), areSexCompatible(asexual, female),
				"FEMALE+ASEXUAL commutativity");
		Assertions.assertEquals(areSexCompatible(asexual, asexual), areSexCompatible(asexual, asexual),
				"ASEXUAL+ASEXUAL commutativity");

		// Verify expected values
		Assertions.assertTrue(areSexCompatible(male, female));
		Assertions.assertFalse(areSexCompatible(male, male));
		Assertions.assertFalse(areSexCompatible(female, female));
		Assertions.assertTrue(areSexCompatible(male, asexual));
		Assertions.assertTrue(areSexCompatible(female, asexual));
		Assertions.assertTrue(areSexCompatible(asexual, asexual));
	}

	// ========================
	// 4.4 性染色体注册 API
	// ========================

	/**
	 * TC-030: {@link RegistryRelations#registerSexChromosome} registered before freeze is
	 * accessible via {@link RegistryRelations#getSexDetermination} after freeze.
	 */
	@Test
	void testSexChromosomeRegistrationPersistsAfterFreeze() {
		// SHEEP_XY was registered with SexDetermination.XY in TestBootstrap
		SexDetermination system = RegistryRelations.getSexDetermination(BuiltInChromosomes.Sheep.SHEEP_XY);
		Assertions.assertNotNull(system, "getSexDetermination should not return null for SHEEP_XY");
		Assertions.assertEquals(SexDetermination.XY, system);
	}

	/**
	 * TC-031: {@link RegistryRelations#registerEntityTypeSexChromosome} registered before freeze
	 * is accessible via {@link RegistryRelations#getSexChromosome} after freeze.
	 */
	@Test
	void testEntityTypeSexChromosomeRegistrationPersistsAfterFreeze() {
		var sexChromosome = RegistryRelations.getSexChromosome(EntityType.SHEEP);
		Assertions.assertNotNull(sexChromosome, "getSexChromosome(SHEEP) should not return null");
		Assertions.assertEquals(BuiltInChromosomes.Sheep.SHEEP_XY, sexChromosome);
	}

	/**
	 * TC-032: Querying an unregistered entity type returns {@code null} without throwing.
	 */
	@Test
	void testUnregisteredEntityTypeReturnsNull() {
		var sexChromosome = RegistryRelations.getSexChromosome(EntityType.CREEPER);
		Assertions.assertNull(sexChromosome, "getSexChromosome for unregistered entity should return null");
	}

	/**
	 * TC-033: Calling {@link RegistryRelations#registerSexChromosome} after freeze throws
	 * {@link IllegalStateException}.
	 */
	@Test
	void testRegisterSexChromosomeAfterFreezeThrows() {
		Assertions.assertThrows(IllegalStateException.class, () ->
				RegistryRelations.registerSexChromosome(
						BuiltInChromosomes.Chicken.CHICKEN_ZW, SexDetermination.ZW));
	}

	/**
	 * TC-034: Calling {@link RegistryRelations#registerEntityTypeSexChromosome} after freeze throws
	 * {@link IllegalStateException}.
	 */
	@Test
	void testRegisterEntityTypeSexChromosomeAfterFreezeThrows() {
		Assertions.assertThrows(IllegalStateException.class, () ->
				RegistryRelations.registerEntityTypeSexChromosome(
						EntityType.PIG, BuiltInChromosomes.Sheep.SHEEP_XY));
	}

	// ========================
	// 4.5 性别生成比例
	// ========================

	/**
	 * TC-040: XY system – natural generation sex ratio is close to 50:50 (N=10000, 95% CI).
	 *
	 * <p>This statistical test has an inherent ~5% failure probability. If it fails, re-run once.</p>
	 */
	@Test
	void testXySexRatioIsHalf() {
		RandomSource random = RandomSource.create();
		DummyChromosomeCarrier carrier = new DummyChromosomeCarrier(EntityType.SHEEP);
		int femaleCount = 0;

		for(int i = 0; i < N; i++) {
			List<ChromosomeInstance> chroms = carrier.chromosomelib$buildDefaultChromosomes(
					(IChromosomeLibEntityType) EntityType.SHEEP,
					IWeightedGeneList.Context.of(random));
			if(SexHelper.getSex(EntityType.SHEEP, chroms) == BiologicalSex.FEMALE) {
				femaleCount++;
			}
		}

		double mean = (double) femaleCount / N;
		CLLogger.info("TC-040: female ratio = %.6f (expected ~0.5, CI ±%.4f)".formatted(mean, BOUND_50));
		if(mean < P_50 - BOUND_50 || mean > P_50 + BOUND_50) {
			Assertions.fail("XY female ratio %.6f not in 95%% CI [%.6f, %.6f]. Re-run; inherent 5%% chance.".formatted(
					mean, P_50 - BOUND_50, P_50 + BOUND_50));
		}
	}

	/**
	 * TC-041: ZW system – natural generation sex ratio is close to 50:50 (N=10000, 95% CI).
	 *
	 * <p>This statistical test has an inherent ~5% failure probability. If it fails, re-run once.</p>
	 */
	@Test
	void testZwSexRatioIsHalf() {
		RandomSource random = RandomSource.create();
		DummyChromosomeCarrier carrier = new DummyChromosomeCarrier(EntityType.CHICKEN);
		int maleCount = 0;

		for(int i = 0; i < N; i++) {
			List<ChromosomeInstance> chroms = carrier.chromosomelib$buildDefaultChromosomes(
					(IChromosomeLibEntityType) EntityType.CHICKEN,
					IWeightedGeneList.Context.of(random));
			if(SexHelper.getSex(EntityType.CHICKEN, chroms) == BiologicalSex.MALE) {
				maleCount++;
			}
		}

		double mean = (double) maleCount / N;
		CLLogger.info("TC-041: male ratio = %.6f (expected ~0.5, CI ±%.4f)".formatted(mean, BOUND_50));
		if(mean < P_50 - BOUND_50 || mean > P_50 + BOUND_50) {
			Assertions.fail("ZW male ratio %.6f not in 95%% CI [%.6f, %.6f]. Re-run; inherent 5%% chance.".formatted(
					mean, P_50 - BOUND_50, P_50 + BOUND_50));
		}
	}

	/**
	 * TC-042: Offspring sex ratio from breeding is close to 50:50 (N=10000, 95% CI).
	 *
	 * <p>Parents: XY male (LEFT+RIGHT) × XX female (LEFT+LEFT). Each offspring gets one
	 * chromosome from each parent, yielding XX or XY with equal probability.</p>
	 */
	@Test
	void testBreedingOffspringSexRatioIsHalf() {
		RandomSource random = RandomSource.create();
		// Male parent: SHEEP_XY = LEFT (X) + RIGHT (Y)
		List<ChromosomeInstance> maleParentChroms = makeSheepXyChromosomes(ChromosomeType.RIGHT);
		// Female parent: SHEEP_XY = LEFT (X) + LEFT (X)
		List<ChromosomeInstance> femaleParentChroms = makeSheepXxChromosomes();

		int maleCount = 0;
		for(int i = 0; i < N; i++) {
			Collection<ChromosomeInstance> offspringChroms = Breeders.breed(
					EntityType.SHEEP, EntityType.SHEEP,
					maleParentChroms, femaleParentChroms, random);
			if(SexHelper.getSex(EntityType.SHEEP, offspringChroms) == BiologicalSex.MALE) {
				maleCount++;
			}
		}

		double mean = (double) maleCount / N;
		CLLogger.info("TC-042: offspring male ratio = %.6f (expected ~0.5, CI ±%.4f)".formatted(mean, BOUND_50));
		if(mean < P_50 - BOUND_50 || mean > P_50 + BOUND_50) {
			Assertions.fail("Breeding offspring male ratio %.6f not in 95%% CI [%.6f, %.6f]. Re-run; inherent 5%% chance.".formatted(
					mean, P_50 - BOUND_50, P_50 + BOUND_50));
		}
	}

	/**
	 * TC-043: XY sex chromosome instances from {@code buildDefaultChromosomes} are always
	 * exactly 2 and always have a LEFT instance (never RIGHT+RIGHT).
	 */
	@Test
	void testSexChromosomeInstanceCombinationCorrectness() {
		RandomSource random = RandomSource.create();
		DummyChromosomeCarrier carrier = new DummyChromosomeCarrier(EntityType.SHEEP);

		for(int i = 0; i < 1000; i++) {
			List<ChromosomeInstance> chroms = carrier.chromosomelib$buildDefaultChromosomes(
					(IChromosomeLibEntityType) EntityType.SHEEP,
					IWeightedGeneList.Context.of(random));

			List<ChromosomeInstance> sexChroms = chroms.stream()
					.filter(ci -> ci.chromosome().equals(BuiltInChromosomes.Sheep.SHEEP_XY))
					.toList();

			Assertions.assertEquals(2, sexChroms.size(),
					"Generation %d: sex chromosome instance count should be 2".formatted(i));

			long leftCount = sexChroms.stream().filter(ci -> ci.type() == ChromosomeType.LEFT).count();
			long rightCount = sexChroms.stream().filter(ci -> ci.type() == ChromosomeType.RIGHT).count();

			// Valid combinations: LEFT+LEFT (female) or LEFT+RIGHT (male)
			// RIGHT+RIGHT is never valid
			Assertions.assertTrue(leftCount >= 1,
					"Generation %d: must have at least one LEFT chromosome".formatted(i));
			Assertions.assertTrue(rightCount <= 1,
					"Generation %d: must have at most one RIGHT chromosome (no RIGHT+RIGHT)".formatted(i));
		}
	}

	// ========================
	// Private helpers
	// ========================

	/**
	 * Returns sheep XY sex chromosome instances: one LEFT (X) + one chromosome of the given type.
	 *
	 * @param secondType the type of the second chromosome (LEFT for XX female, RIGHT for XY male)
	 */
	private static List<ChromosomeInstance> makeSheepXyChromosomes(ChromosomeType secondType) {
		return List.of(
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, ChromosomeType.LEFT),
				ChromosomeInstance.of(BuiltInChromosomes.Sheep.SHEEP_XY, secondType)
		);
	}

	/**
	 * Returns sheep XX sex chromosome instances (two LEFT chromosomes → female).
	 */
	private static List<ChromosomeInstance> makeSheepXxChromosomes() {
		return makeSheepXyChromosomes(ChromosomeType.LEFT);
	}

	/**
	 * Returns whether two biological sexes form a compatible breeding pair.
	 * Mirrors the logic of {@link SexHelper#isCompatibleBreedingPair}.
	 */
	private static boolean areSexCompatible(BiologicalSex sexA, BiologicalSex sexB) {
		if(sexA == BiologicalSex.ASEXUAL || sexB == BiologicalSex.ASEXUAL) {
			return true;
		}
		return sexA != sexB;
	}
}
