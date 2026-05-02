package com.hexagram2021.chromosomelib.common.chromosome;

import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.CLEntityTypeTags;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import com.hexagram2021.chromosomelib.common.sex.SexDetermination;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Locale;
import java.util.function.Consumer;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

/**
 * Built-in chromosomes for vanilla entities.
 *
 * @author liudongyu
 */
public final class BuiltInChromosomes {
	/**
	 * Built-in chromosomes for axolotls.
	 */
	public static final class Axolotl {
		public static final Holder<Chromosome> AXOLOTL_1 = register("axolotl_1", 1);
		public static final Holder<Chromosome> AXOLOTL_2 = register("axolotl_2", 2);
		public static final Holder<Chromosome> AXOLOTL_3 = register("axolotl_3", 3);
		public static final Holder<Chromosome> AXOLOTL_4 = register("axolotl_4", 4);
		public static final Holder<Chromosome> AXOLOTL_5 = register("axolotl_5", 5);
		public static final Holder<Chromosome> AXOLOTL_6 = register("axolotl_6", 6);
		public static final Holder<Chromosome> AXOLOTL_7 = register("axolotl_7", 7);
		public static final Holder<Chromosome> AXOLOTL_8 = register("axolotl_8", 8);
		public static final Holder<Chromosome> AXOLOTL_9 = register("axolotl_9", 9);
		public static final Holder<Chromosome> AXOLOTL_10 = register("axolotl_10", 10);
		public static final Holder<Chromosome> AXOLOTL_11 = register("axolotl_11", 11);
		public static final Holder<Chromosome> AXOLOTL_12 = register("axolotl_12", 12);
		public static final Holder<Chromosome> AXOLOTL_13 = register("axolotl_13", 13);
		/** Sex Chromosomes */
		public static final Holder<Chromosome> AXOLOTL_XY = register("axolotl_xy", 14);

		private Axolotl() {
		}

		/**
		 * Initializes the axolotl chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for axolotls.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.AXOLOTL, AXOLOTL_XY);

			RegistryRelations.registerSexChromosome(AXOLOTL_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.AXOLOTL, AXOLOTL_XY);
		}
	}

	/**
	 * Built-in chromosomes for bees.
	 */
	public static final class Bee {
		public static final Holder<Chromosome> BEE_1 = register("bee_1", 1);
		public static final Holder<Chromosome> BEE_2 = register("bee_2", 2);
		public static final Holder<Chromosome> BEE_3 = register("bee_3", 3);
		public static final Holder<Chromosome> BEE_4 = register("bee_4", 4);
		public static final Holder<Chromosome> BEE_5 = register("bee_5", 5);
		public static final Holder<Chromosome> BEE_6 = register("bee_6", 6);
		public static final Holder<Chromosome> BEE_7 = register("bee_7", 7);
		public static final Holder<Chromosome> BEE_8 = register("bee_8", 8);
		public static final Holder<Chromosome> BEE_9 = register("bee_9", 9);
		public static final Holder<Chromosome> BEE_10 = register("bee_10", 10);
		public static final Holder<Chromosome> BEE_11 = register("bee_11", 11);
		public static final Holder<Chromosome> BEE_12 = register("bee_12", 12);
		public static final Holder<Chromosome> BEE_13 = register("bee_13", 13);
		public static final Holder<Chromosome> BEE_14 = register("bee_14", 14);
		public static final Holder<Chromosome> BEE_15 = register("bee_15", 15);
		public static final Holder<Chromosome> BEE_16 = register("bee_16", 16);

		private Bee() {
		}

		/**
		 * Initializes the bee chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for bees.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.BEE, BEE_16);
		}
	}

	/**
	 * Built-in chromosomes for cats.
	 */
	public static final class Cat {
		public static final Holder<Chromosome> CAT_1 = register("cat_1", 1);
		public static final Holder<Chromosome> CAT_2 = register("cat_2", 2);
		public static final Holder<Chromosome> CAT_3 = register("cat_3", 3);
		public static final Holder<Chromosome> CAT_4 = register("cat_4", 4);
		public static final Holder<Chromosome> CAT_5 = register("cat_5", 5);
		public static final Holder<Chromosome> CAT_6 = register("cat_6", 6);
		public static final Holder<Chromosome> CAT_7 = register("cat_7", 7);
		public static final Holder<Chromosome> CAT_8 = register("cat_8", 8);
		public static final Holder<Chromosome> CAT_9 = register("cat_9", 9);
		public static final Holder<Chromosome> CAT_10 = register("cat_10", 10);
		public static final Holder<Chromosome> CAT_11 = register("cat_11", 11);
		public static final Holder<Chromosome> CAT_12 = register("cat_12", 12);
		public static final Holder<Chromosome> CAT_13 = register("cat_13", 13);
		public static final Holder<Chromosome> CAT_14 = register("cat_14", 14);
		public static final Holder<Chromosome> CAT_15 = register("cat_15", 15);
		public static final Holder<Chromosome> CAT_16 = register("cat_16", 16);
		public static final Holder<Chromosome> CAT_17 = register("cat_17", 17);
		public static final Holder<Chromosome> CAT_18 = register("cat_18", 18);
		public static final Holder<Chromosome> CAT_XY = register("cat_xy", 19);

		private Cat() {
		}

		/**
		 * Initializes the cat chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for cats.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CAT, CAT_XY);

			RegistryRelations.registerSexChromosome(CAT_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.CAT, CAT_XY);
		}
	}

	/**
	 * Built-in chromosomes for chickens.
	 */
	public static final class Chicken {
		public static final Holder<Chromosome> CHICKEN_1 = register("chicken_1", 1);
		public static final Holder<Chromosome> CHICKEN_2 = register("chicken_2", 2);
		public static final Holder<Chromosome> CHICKEN_3 = register("chicken_3", 3);
		public static final Holder<Chromosome> CHICKEN_4 = register("chicken_4", 4);
		public static final Holder<Chromosome> CHICKEN_5 = register("chicken_5", 5);
		public static final Holder<Chromosome> CHICKEN_6 = register("chicken_6", 6);
		public static final Holder<Chromosome> CHICKEN_7 = register("chicken_7", 7);
		public static final Holder<Chromosome> CHICKEN_8 = register("chicken_8", 8);
		public static final Holder<Chromosome> CHICKEN_9 = register("chicken_9", 9);
		public static final Holder<Chromosome> CHICKEN_10 = register("chicken_10", 10);
		public static final Holder<Chromosome> CHICKEN_11 = register("chicken_11", 11);
		public static final Holder<Chromosome> CHICKEN_12 = register("chicken_12", 12);
		public static final Holder<Chromosome> CHICKEN_13 = register("chicken_13", 13);
		public static final Holder<Chromosome> CHICKEN_14 = register("chicken_14", 14);
		public static final Holder<Chromosome> CHICKEN_15 = register("chicken_15", 15);
		public static final Holder<Chromosome> CHICKEN_16 = register("chicken_16", 16);
		public static final Holder<Chromosome> CHICKEN_17 = register("chicken_17", 17);
		public static final Holder<Chromosome> CHICKEN_18 = register("chicken_18", 18);
		public static final Holder<Chromosome> CHICKEN_19 = register("chicken_19", 19);
		public static final Holder<Chromosome> CHICKEN_20 = register("chicken_20", 20);
		public static final Holder<Chromosome> CHICKEN_21 = register("chicken_21", 21);
		public static final Holder<Chromosome> CHICKEN_22 = register("chicken_22", 22);
		public static final Holder<Chromosome> CHICKEN_23 = register("chicken_23", 23);
		public static final Holder<Chromosome> CHICKEN_24 = register("chicken_24", 24);
		public static final Holder<Chromosome> CHICKEN_25 = register("chicken_25", 25);
		public static final Holder<Chromosome> CHICKEN_26 = register("chicken_26", 26);
		public static final Holder<Chromosome> CHICKEN_27 = register("chicken_27", 27);
		public static final Holder<Chromosome> CHICKEN_28 = register("chicken_28", 28);
		public static final Holder<Chromosome> CHICKEN_29 = register("chicken_29", 29);
		public static final Holder<Chromosome> CHICKEN_30 = register("chicken_30", 30);
		public static final Holder<Chromosome> CHICKEN_31 = register("chicken_31", 31);
		public static final Holder<Chromosome> CHICKEN_32 = register("chicken_32", 32);
		public static final Holder<Chromosome> CHICKEN_33 = register("chicken_33", 33);
		public static final Holder<Chromosome> CHICKEN_34 = register("chicken_34", 34);
		public static final Holder<Chromosome> CHICKEN_35 = register("chicken_35", 35);
		public static final Holder<Chromosome> CHICKEN_36 = register("chicken_36", 36);
		public static final Holder<Chromosome> CHICKEN_37 = register("chicken_37", 37);
		public static final Holder<Chromosome> CHICKEN_38 = register("chicken_38", 38);
		public static final Holder<Chromosome> CHICKEN_ZW = register("chicken_zw", 39);

		private Chicken() {
		}

		/**
		 * Initializes the chicken chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for chickens.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_31);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_32);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_33);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_34);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_35);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_36);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_37);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_38);
			RegistryRelations.registerEntityType2Chromosome(EntityType.CHICKEN, Chicken.CHICKEN_ZW);

			RegistryRelations.registerSexChromosome(CHICKEN_ZW, SexDetermination.ZW);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.CHICKEN, CHICKEN_ZW);
		}
	}

	/**
	 * Built-in chromosomes for cows.
	 */
	public static final class Cow {
		public static final Holder<Chromosome> COW_1 = register("cow_1", 1);
		public static final Holder<Chromosome> COW_2 = register("cow_2", 2);
		public static final Holder<Chromosome> COW_3 = register("cow_3", 3);
		public static final Holder<Chromosome> COW_4 = register("cow_4", 4);
		public static final Holder<Chromosome> COW_5 = register("cow_5", 5);
		public static final Holder<Chromosome> COW_6 = register("cow_6", 6);
		public static final Holder<Chromosome> COW_7 = register("cow_7", 7);
		public static final Holder<Chromosome> COW_8 = register("cow_8", 8);
		public static final Holder<Chromosome> COW_9 = register("cow_9", 9);
		public static final Holder<Chromosome> COW_10 = register("cow_10", 10);
		public static final Holder<Chromosome> COW_11 = register("cow_11", 11);
		public static final Holder<Chromosome> COW_12 = register("cow_12", 12);
		public static final Holder<Chromosome> COW_13 = register("cow_13", 13);
		public static final Holder<Chromosome> COW_14 = register("cow_14", 14);
		public static final Holder<Chromosome> COW_15 = register("cow_15", 15);
		public static final Holder<Chromosome> COW_16 = register("cow_16", 16);
		public static final Holder<Chromosome> COW_17 = register("cow_17", 17);
		public static final Holder<Chromosome> COW_18 = register("cow_18", 18);
		public static final Holder<Chromosome> COW_19 = register("cow_19", 19);
		public static final Holder<Chromosome> COW_20 = register("cow_20", 20);
		public static final Holder<Chromosome> COW_21 = register("cow_21", 21);
		public static final Holder<Chromosome> COW_22 = register("cow_22", 22);
		public static final Holder<Chromosome> COW_23 = register("cow_23", 23);
		public static final Holder<Chromosome> COW_24 = register("cow_24", 24);
		public static final Holder<Chromosome> COW_25 = register("cow_25", 25);
		public static final Holder<Chromosome> COW_26 = register("cow_26", 26);
		public static final Holder<Chromosome> COW_27 = register("cow_27", 27);
		public static final Holder<Chromosome> COW_28 = register("cow_28", 28);
		public static final Holder<Chromosome> COW_29 = register("cow_29", 29);
		public static final Holder<Chromosome> COW_XY = register("cow_xy", 30);

		private Cow() {
		}

		/**
		 * Initializes the cow chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for cows.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.COW, COW_XY);

			RegistryRelations.registerSexChromosome(COW_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.COW, COW_XY);
		}
	}

	/**
	 * Built-in chromosomes for donkeys.
	 */
	public static final class Donkey {
		public static final Holder<Chromosome> DONKEY_1 = register("donkey_1", 1);
		public static final Holder<Chromosome> DONKEY_2 = register("donkey_2", 2);
		public static final Holder<Chromosome> DONKEY_3 = register("donkey_3", 3);
		public static final Holder<Chromosome> DONKEY_4 = register("donkey_4", 4);
		public static final Holder<Chromosome> DONKEY_5 = register("donkey_5", 5);
		public static final Holder<Chromosome> DONKEY_6 = register("donkey_6", 6);
		public static final Holder<Chromosome> DONKEY_7 = register("donkey_7", 7);
		public static final Holder<Chromosome> DONKEY_8 = register("donkey_8", 8);
		public static final Holder<Chromosome> DONKEY_9 = register("donkey_9", 9);
		public static final Holder<Chromosome> DONKEY_10 = register("donkey_10", 10);
		public static final Holder<Chromosome> DONKEY_11 = register("donkey_11", 11);
		public static final Holder<Chromosome> DONKEY_12 = register("donkey_12", 12);
		public static final Holder<Chromosome> DONKEY_13 = register("donkey_13", 13);
		public static final Holder<Chromosome> DONKEY_14 = register("donkey_14", 14);
		public static final Holder<Chromosome> DONKEY_15 = register("donkey_15", 15);
		public static final Holder<Chromosome> DONKEY_16 = register("donkey_16", 16);
		public static final Holder<Chromosome> DONKEY_17 = register("donkey_17", 17);
		public static final Holder<Chromosome> DONKEY_18 = register("donkey_18", 18);
		public static final Holder<Chromosome> DONKEY_19 = register("donkey_19", 19);
		public static final Holder<Chromosome> DONKEY_20 = register("donkey_20", 20);
		public static final Holder<Chromosome> DONKEY_21 = register("donkey_21", 21);
		public static final Holder<Chromosome> DONKEY_22 = register("donkey_22", 22);
		public static final Holder<Chromosome> DONKEY_23 = register("donkey_23", 23);
		public static final Holder<Chromosome> DONKEY_24 = register("donkey_24", 24);
		public static final Holder<Chromosome> DONKEY_25 = register("donkey_25", 25);
		public static final Holder<Chromosome> DONKEY_26 = register("donkey_26", 26);
		public static final Holder<Chromosome> DONKEY_27 = register("donkey_27", 27);
		public static final Holder<Chromosome> DONKEY_28 = register("donkey_28", 28);
		public static final Holder<Chromosome> DONKEY_29 = register("donkey_29", 29);
		public static final Holder<Chromosome> DONKEY_30 = register("donkey_30", 30);
		public static final Holder<Chromosome> DONKEY_XY = register("donkey_xy", 31);

		private Donkey() {
		}

		/**
		 * Initializes the donkey chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for donkeys.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.DONKEY, DONKEY_XY);

			RegistryRelations.registerSexChromosome(DONKEY_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.DONKEY, DONKEY_XY);
		}
	}

	/**
	 * Built-in chromosomes for foxes.
	 */
	public static final class Fox {
		public static final Holder<Chromosome> FOX_1 = register("fox_1", 1);
		public static final Holder<Chromosome> FOX_2 = register("fox_2", 2);
		public static final Holder<Chromosome> FOX_3 = register("fox_3", 3);
		public static final Holder<Chromosome> FOX_4 = register("fox_4", 4);
		public static final Holder<Chromosome> FOX_5 = register("fox_5", 5);
		public static final Holder<Chromosome> FOX_6 = register("fox_6", 6);
		public static final Holder<Chromosome> FOX_7 = register("fox_7", 7);
		public static final Holder<Chromosome> FOX_8 = register("fox_8", 8);
		public static final Holder<Chromosome> FOX_9 = register("fox_9", 9);
		public static final Holder<Chromosome> FOX_10 = register("fox_10", 10);
		public static final Holder<Chromosome> FOX_11 = register("fox_11", 11);
		public static final Holder<Chromosome> FOX_12 = register("fox_12", 12);
		public static final Holder<Chromosome> FOX_13 = register("fox_13", 13);
		public static final Holder<Chromosome> FOX_14 = register("fox_14", 14);
		public static final Holder<Chromosome> FOX_15 = register("fox_15", 15);
		public static final Holder<Chromosome> FOX_16 = register("fox_16", 16);
		public static final Holder<Chromosome> FOX_XY = register("fox_xy", 17);

		private Fox() {
		}

		/**
		 * Initializes the fox chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for foxes.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FOX, FOX_XY);

			RegistryRelations.registerSexChromosome(FOX_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.FOX, FOX_XY);
		}
	}

	/**
	 * Built-in chromosomes for frogs.
	 */
	public static final class Frog {
		public static final Holder<Chromosome> FROG_1 = register("frog_1", 1);
		public static final Holder<Chromosome> FROG_2 = register("frog_2", 2);
		public static final Holder<Chromosome> FROG_3 = register("frog_3", 3);
		public static final Holder<Chromosome> FROG_4 = register("frog_4", 4);
		public static final Holder<Chromosome> FROG_5 = register("frog_5", 5);
		public static final Holder<Chromosome> FROG_6 = register("frog_6", 6);
		public static final Holder<Chromosome> FROG_7 = register("frog_7", 7);
		public static final Holder<Chromosome> FROG_8 = register("frog_8", 8);
		public static final Holder<Chromosome> FROG_9 = register("frog_9", 9);
		public static final Holder<Chromosome> FROG_10 = register("frog_10", 10);
		public static final Holder<Chromosome> FROG_11 = register("frog_11", 11);
		public static final Holder<Chromosome> FROG_12 = register("frog_12", 12);
		public static final Holder<Chromosome> FROG_XY = register("frog_xy", 13);

		private Frog() {
		}

		/**
		 * Initializes the frog chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for frogs.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.FROG, FROG_XY);

			RegistryRelations.registerSexChromosome(FROG_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.FROG, FROG_XY);
		}
	}

	/**
	 * Built-in chromosomes for goats.
	 */
	public static final class Goat {
		public static final Holder<Chromosome> GOAT_1 = register("goat_1", 1);
		public static final Holder<Chromosome> GOAT_2 = register("goat_2", 2);
		public static final Holder<Chromosome> GOAT_3 = register("goat_3", 3);
		public static final Holder<Chromosome> GOAT_4 = register("goat_4", 4);
		public static final Holder<Chromosome> GOAT_5 = register("goat_5", 5);
		public static final Holder<Chromosome> GOAT_6 = register("goat_6", 6);
		public static final Holder<Chromosome> GOAT_7 = register("goat_7", 7);
		public static final Holder<Chromosome> GOAT_8 = register("goat_8", 8);
		public static final Holder<Chromosome> GOAT_9 = register("goat_9", 9);
		public static final Holder<Chromosome> GOAT_10 = register("goat_10", 10);
		public static final Holder<Chromosome> GOAT_11 = register("goat_11", 11);
		public static final Holder<Chromosome> GOAT_12 = register("goat_12", 12);
		public static final Holder<Chromosome> GOAT_13 = register("goat_13", 13);
		public static final Holder<Chromosome> GOAT_14 = register("goat_14", 14);
		public static final Holder<Chromosome> GOAT_15 = register("goat_15", 15);
		public static final Holder<Chromosome> GOAT_16 = register("goat_16", 16);
		public static final Holder<Chromosome> GOAT_17 = register("goat_17", 17);
		public static final Holder<Chromosome> GOAT_18 = register("goat_18", 18);
		public static final Holder<Chromosome> GOAT_19 = register("goat_19", 19);
		public static final Holder<Chromosome> GOAT_20 = register("goat_20", 20);
		public static final Holder<Chromosome> GOAT_21 = register("goat_21", 21);
		public static final Holder<Chromosome> GOAT_22 = register("goat_22", 22);
		public static final Holder<Chromosome> GOAT_23 = register("goat_23", 23);
		public static final Holder<Chromosome> GOAT_24 = register("goat_24", 24);
		public static final Holder<Chromosome> GOAT_25 = register("goat_25", 25);
		public static final Holder<Chromosome> GOAT_26 = register("goat_26", 26);
		public static final Holder<Chromosome> GOAT_27 = register("goat_27", 27);
		public static final Holder<Chromosome> GOAT_28 = register("goat_28", 28);
		public static final Holder<Chromosome> GOAT_29 = register("goat_29", 29);
		public static final Holder<Chromosome> GOAT_XY = register("goat_xy", 30);

		private Goat() {
		}

		/**
		 * Initializes the goat chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for goats.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.GOAT, GOAT_XY);

			RegistryRelations.registerSexChromosome(GOAT_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.GOAT, GOAT_XY);
		}
	}

	/**
	 * Built-in chromosomes for horses.
	 */
	public static final class Horse {
		public static final Holder<Chromosome> HORSE_1 = register("horse_1", 1);
		public static final Holder<Chromosome> HORSE_2 = register("horse_2", 2);
		public static final Holder<Chromosome> HORSE_3 = register("horse_3", 3);
		public static final Holder<Chromosome> HORSE_4 = register("horse_4", 4);
		public static final Holder<Chromosome> HORSE_5 = register("horse_5", 5);
		public static final Holder<Chromosome> HORSE_6 = register("horse_6", 6);
		public static final Holder<Chromosome> HORSE_7 = register("horse_7", 7);
		public static final Holder<Chromosome> HORSE_8 = register("horse_8", 8);
		public static final Holder<Chromosome> HORSE_9 = register("horse_9", 9);
		public static final Holder<Chromosome> HORSE_10 = register("horse_10", 10);
		public static final Holder<Chromosome> HORSE_11 = register("horse_11", 11);
		public static final Holder<Chromosome> HORSE_12 = register("horse_12", 12);
		public static final Holder<Chromosome> HORSE_13 = register("horse_13", 13);
		public static final Holder<Chromosome> HORSE_14 = register("horse_14", 14);
		public static final Holder<Chromosome> HORSE_15 = register("horse_15", 15);
		public static final Holder<Chromosome> HORSE_16 = register("horse_16", 16);
		public static final Holder<Chromosome> HORSE_17 = register("horse_17", 17);
		public static final Holder<Chromosome> HORSE_18 = register("horse_18", 18);
		public static final Holder<Chromosome> HORSE_19 = register("horse_19", 19);
		public static final Holder<Chromosome> HORSE_20 = register("horse_20", 20);
		public static final Holder<Chromosome> HORSE_21 = register("horse_21", 21);
		public static final Holder<Chromosome> HORSE_22 = register("horse_22", 22);
		public static final Holder<Chromosome> HORSE_23 = register("horse_23", 23);
		public static final Holder<Chromosome> HORSE_24 = register("horse_24", 24);
		public static final Holder<Chromosome> HORSE_25 = register("horse_25", 25);
		public static final Holder<Chromosome> HORSE_26 = register("horse_26", 26);
		public static final Holder<Chromosome> HORSE_27 = register("horse_27", 27);
		public static final Holder<Chromosome> HORSE_28 = register("horse_28", 28);
		public static final Holder<Chromosome> HORSE_29 = register("horse_29", 29);
		public static final Holder<Chromosome> HORSE_30 = register("horse_30", 30);
		public static final Holder<Chromosome> HORSE_31 = register("horse_31", 31);
		public static final Holder<Chromosome> HORSE_XY = register("horse_xy", 32);

		private Horse() {
		}

		/**
		 * Initializes the horse chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for horses.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_31);
			RegistryRelations.registerEntityType2Chromosome(EntityType.HORSE, HORSE_XY);

			RegistryRelations.registerSexChromosome(HORSE_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.HORSE, HORSE_XY);
		}
	}

	/**
	 * Built-in chromosomes for humans.
	 */
	public static final class Human {
		public static final Holder<Chromosome> HUMAN_1 = register("human_1", 1);
		public static final Holder<Chromosome> HUMAN_2 = register("human_2", 2);
		public static final Holder<Chromosome> HUMAN_3 = register("human_3", 3);
		public static final Holder<Chromosome> HUMAN_4 = register("human_4", 4);
		public static final Holder<Chromosome> HUMAN_5 = register("human_5", 5);
		public static final Holder<Chromosome> HUMAN_6 = register("human_6", 6);
		public static final Holder<Chromosome> HUMAN_7 = register("human_7", 7);
		public static final Holder<Chromosome> HUMAN_8 = register("human_8", 8);
		public static final Holder<Chromosome> HUMAN_9 = register("human_9", 9);
		public static final Holder<Chromosome> HUMAN_10 = register("human_10", 10);
		public static final Holder<Chromosome> HUMAN_11 = register("human_11", 11);
		public static final Holder<Chromosome> HUMAN_12 = register("human_12", 12);
		public static final Holder<Chromosome> HUMAN_13 = register("human_13", 13);
		public static final Holder<Chromosome> HUMAN_14 = register("human_14", 14);
		public static final Holder<Chromosome> HUMAN_15 = register("human_15", 15);
		public static final Holder<Chromosome> HUMAN_16 = register("human_16", 16);
		public static final Holder<Chromosome> HUMAN_17 = register("human_17", 17);
		public static final Holder<Chromosome> HUMAN_18 = register("human_18", 18);
		public static final Holder<Chromosome> HUMAN_19 = register("human_19", 19);
		public static final Holder<Chromosome> HUMAN_20 = register("human_20", 20);
		public static final Holder<Chromosome> HUMAN_21 = register("human_21", 21);
		public static final Holder<Chromosome> HUMAN_22 = register("human_22", 22);
		public static final Holder<Chromosome> HUMAN_XY = register("human_xy", 27);

		private Human() {
		}

		/**
		 * Initializes the human chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for humans.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_1);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_2);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_3);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_4);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_5);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_6);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_7);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_8);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_9);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_10);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_11);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_12);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_13);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_14);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_15);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_16);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_17);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_18);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_19);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_20);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_21);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_22);
			RegistryRelations.registerEntityTypeTag2Chromosome(CLEntityTypeTags.HUMANS, HUMAN_XY);

			RegistryRelations.registerSexChromosome(HUMAN_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeTagSexChromosome(CLEntityTypeTags.HUMANS, HUMAN_XY);
		}
	}

	/**
	 * Built-in chromosomes for mules. Notice that a mule is a hybrid of a horse and a donkey.
	 */
	public static final class Mule {
		private Mule() {
		}

		/**
		 * Initializes the mule chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for mules.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Donkey.DONKEY_XY);

			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_1, 32);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_2, 33);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_3, 34);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_4, 35);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_5, 36);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_6, 37);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_7, 38);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_8, 39);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_9, 40);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_10, 41);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_11, 42);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_12, 43);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_13, 44);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_14, 45);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_15, 46);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_16, 47);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_17, 48);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_18, 49);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_19, 50);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_20, 51);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_21, 52);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_22, 53);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_23, 54);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_24, 55);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_25, 56);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_26, 57);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_27, 58);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_28, 59);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_29, 60);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_30, 61);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_31, 62);
			RegistryRelations.registerEntityType2Chromosome(EntityType.MULE, Horse.HORSE_XY, 63);
		}
	}

	/**
	 * Built-in chromosomes for ocelots.
	 */
	public static final class Ocelot {
		public static final Holder<Chromosome> OCELOT_1 = register("ocelot_1", 1);
		public static final Holder<Chromosome> OCELOT_2 = register("ocelot_2", 2);
		public static final Holder<Chromosome> OCELOT_3 = register("ocelot_3", 3);
		public static final Holder<Chromosome> OCELOT_4 = register("ocelot_4", 4);
		public static final Holder<Chromosome> OCELOT_5 = register("ocelot_5", 5);
		public static final Holder<Chromosome> OCELOT_6 = register("ocelot_6", 6);
		public static final Holder<Chromosome> OCELOT_7 = register("ocelot_7", 7);
		public static final Holder<Chromosome> OCELOT_8 = register("ocelot_8", 8);
		public static final Holder<Chromosome> OCELOT_9 = register("ocelot_9", 9);
		public static final Holder<Chromosome> OCELOT_10 = register("ocelot_10", 10);
		public static final Holder<Chromosome> OCELOT_11 = register("ocelot_11", 11);
		public static final Holder<Chromosome> OCELOT_12 = register("ocelot_12", 12);
		public static final Holder<Chromosome> OCELOT_13 = register("ocelot_13", 13);
		public static final Holder<Chromosome> OCELOT_14 = register("ocelot_14", 14);
		public static final Holder<Chromosome> OCELOT_15 = register("ocelot_15", 15);
		public static final Holder<Chromosome> OCELOT_16 = register("ocelot_16", 16);
		public static final Holder<Chromosome> OCELOT_17 = register("ocelot_17", 17);
		public static final Holder<Chromosome> OCELOT_XY = register("ocelot_xy", 18);

		private Ocelot() {
		}

		/**
		 * Initializes the ocelot chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for ocelots.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.OCELOT, OCELOT_XY);

			RegistryRelations.registerSexChromosome(OCELOT_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.OCELOT, OCELOT_XY);
		}
	}

	/**
	 * Built-in chromosomes for pandas.
	 */
	public static final class Panda {
		public static final Holder<Chromosome> PANDA_1 = register("panda_1", 1);
		public static final Holder<Chromosome> PANDA_2 = register("panda_2", 2);
		public static final Holder<Chromosome> PANDA_3 = register("panda_3", 3);
		public static final Holder<Chromosome> PANDA_4 = register("panda_4", 4);
		public static final Holder<Chromosome> PANDA_5 = register("panda_5", 5);
		public static final Holder<Chromosome> PANDA_6 = register("panda_6", 6);
		public static final Holder<Chromosome> PANDA_7 = register("panda_7", 7);
		public static final Holder<Chromosome> PANDA_8 = register("panda_8", 8);
		public static final Holder<Chromosome> PANDA_9 = register("panda_9", 9);
		public static final Holder<Chromosome> PANDA_10 = register("panda_10", 10);
		public static final Holder<Chromosome> PANDA_11 = register("panda_11", 11);
		public static final Holder<Chromosome> PANDA_12 = register("panda_12", 12);
		public static final Holder<Chromosome> PANDA_13 = register("panda_13", 13);
		public static final Holder<Chromosome> PANDA_14 = register("panda_14", 14);
		public static final Holder<Chromosome> PANDA_15 = register("panda_15", 15);
		public static final Holder<Chromosome> PANDA_16 = register("panda_16", 16);
		public static final Holder<Chromosome> PANDA_17 = register("panda_17", 17);
		public static final Holder<Chromosome> PANDA_18 = register("panda_18", 18);
		public static final Holder<Chromosome> PANDA_19 = register("panda_19", 19);
		public static final Holder<Chromosome> PANDA_20 = register("panda_20", 20);
		public static final Holder<Chromosome> PANDA_XY = register("panda_xy", 21);

		private Panda() {
		}

		/**
		 * Initializes the panda chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for pandas.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PANDA, PANDA_XY);

			RegistryRelations.registerSexChromosome(PANDA_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.PANDA, PANDA_XY);
		}
	}

	/**
	 * Built-in chromosomes for parrots.
	 */
	public static final class Parrot {
		public static final Holder<Chromosome> PARROT_1 = register("parrot_1", 1);
		public static final Holder<Chromosome> PARROT_2 = register("parrot_2", 2);
		public static final Holder<Chromosome> PARROT_3 = register("parrot_3", 3);
		public static final Holder<Chromosome> PARROT_4 = register("parrot_4", 4);
		public static final Holder<Chromosome> PARROT_5 = register("parrot_5", 5);
		public static final Holder<Chromosome> PARROT_6 = register("parrot_6", 6);
		public static final Holder<Chromosome> PARROT_7 = register("parrot_7", 7);
		public static final Holder<Chromosome> PARROT_8 = register("parrot_8", 8);
		public static final Holder<Chromosome> PARROT_9 = register("parrot_9", 9);
		public static final Holder<Chromosome> PARROT_10 = register("parrot_10", 10);
		public static final Holder<Chromosome> PARROT_11 = register("parrot_11", 11);
		public static final Holder<Chromosome> PARROT_12 = register("parrot_12", 12);
		public static final Holder<Chromosome> PARROT_13 = register("parrot_13", 13);
		public static final Holder<Chromosome> PARROT_14 = register("parrot_14", 14);
		public static final Holder<Chromosome> PARROT_15 = register("parrot_15", 15);
		public static final Holder<Chromosome> PARROT_16 = register("parrot_16", 16);
		public static final Holder<Chromosome> PARROT_17 = register("parrot_17", 17);
		public static final Holder<Chromosome> PARROT_18 = register("parrot_18", 18);
		public static final Holder<Chromosome> PARROT_19 = register("parrot_19", 19);
		public static final Holder<Chromosome> PARROT_20 = register("parrot_20", 20);
		public static final Holder<Chromosome> PARROT_21 = register("parrot_21", 21);
		public static final Holder<Chromosome> PARROT_22 = register("parrot_22", 22);
		public static final Holder<Chromosome> PARROT_23 = register("parrot_23", 23);
		public static final Holder<Chromosome> PARROT_24 = register("parrot_24", 24);
		public static final Holder<Chromosome> PARROT_25 = register("parrot_25", 25);
		public static final Holder<Chromosome> PARROT_26 = register("parrot_26", 26);
		public static final Holder<Chromosome> PARROT_27 = register("parrot_27", 27);
		public static final Holder<Chromosome> PARROT_28 = register("parrot_28", 28);
		public static final Holder<Chromosome> PARROT_29 = register("parrot_29", 29);
		public static final Holder<Chromosome> PARROT_30 = register("parrot_30", 30);
		public static final Holder<Chromosome> PARROT_ZW = register("parrot_zw", 31);

		private Parrot() {
		}

		/**
		 * Initializes the parrot chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for parrots.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PARROT, PARROT_ZW);

			RegistryRelations.registerSexChromosome(PARROT_ZW, SexDetermination.ZW);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.PARROT, PARROT_ZW);
		}
	}

	/**
	 * Built-in chromosomes for pigs.
	 */
	public static final class Pig {
		public static final Holder<Chromosome> PIG_1 = register("pig_1", 1);
		public static final Holder<Chromosome> PIG_2 = register("pig_2", 2);
		public static final Holder<Chromosome> PIG_3 = register("pig_3", 3);
		public static final Holder<Chromosome> PIG_4 = register("pig_4", 4);
		public static final Holder<Chromosome> PIG_5 = register("pig_5", 5);
		public static final Holder<Chromosome> PIG_6 = register("pig_6", 6);
		public static final Holder<Chromosome> PIG_7 = register("pig_7", 7);
		public static final Holder<Chromosome> PIG_8 = register("pig_8", 8);
		public static final Holder<Chromosome> PIG_9 = register("pig_9", 9);
		public static final Holder<Chromosome> PIG_10 = register("pig_10", 10);
		public static final Holder<Chromosome> PIG_11 = register("pig_11", 11);
		public static final Holder<Chromosome> PIG_12 = register("pig_12", 12);
		public static final Holder<Chromosome> PIG_13 = register("pig_13", 13);
		public static final Holder<Chromosome> PIG_14 = register("pig_14", 14);
		public static final Holder<Chromosome> PIG_15 = register("pig_15", 15);
		public static final Holder<Chromosome> PIG_16 = register("pig_16", 16);
		public static final Holder<Chromosome> PIG_17 = register("pig_17", 17);
		public static final Holder<Chromosome> PIG_18 = register("pig_18", 18);
		public static final Holder<Chromosome> PIG_XY = register("pig_xy", 19);

		private Pig() {
		}

		/**
		 * Initializes the pig chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for pigs.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.PIG, PIG_XY);

			RegistryRelations.registerSexChromosome(PIG_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.PIG, PIG_XY);
		}
	}

	/**
	 * Built-in chromosomes for polar bears.
	 */
	public static final class PolarBear {
		public static final Holder<Chromosome> POLAR_BEAR_1 = register("polar_bear_1", 1);
		public static final Holder<Chromosome> POLAR_BEAR_2 = register("polar_bear_2", 2);
		public static final Holder<Chromosome> POLAR_BEAR_3 = register("polar_bear_3", 3);
		public static final Holder<Chromosome> POLAR_BEAR_4 = register("polar_bear_4", 4);
		public static final Holder<Chromosome> POLAR_BEAR_5 = register("polar_bear_5", 5);
		public static final Holder<Chromosome> POLAR_BEAR_6 = register("polar_bear_6", 6);
		public static final Holder<Chromosome> POLAR_BEAR_7 = register("polar_bear_7", 7);
		public static final Holder<Chromosome> POLAR_BEAR_8 = register("polar_bear_8", 8);
		public static final Holder<Chromosome> POLAR_BEAR_9 = register("polar_bear_9", 9);
		public static final Holder<Chromosome> POLAR_BEAR_10 = register("polar_bear_10", 10);
		public static final Holder<Chromosome> POLAR_BEAR_11 = register("polar_bear_11", 11);
		public static final Holder<Chromosome> POLAR_BEAR_12 = register("polar_bear_12", 12);
		public static final Holder<Chromosome> POLAR_BEAR_13 = register("polar_bear_13", 13);
		public static final Holder<Chromosome> POLAR_BEAR_14 = register("polar_bear_14", 14);
		public static final Holder<Chromosome> POLAR_BEAR_15 = register("polar_bear_15", 15);
		public static final Holder<Chromosome> POLAR_BEAR_16 = register("polar_bear_16", 16);
		public static final Holder<Chromosome> POLAR_BEAR_17 = register("polar_bear_17", 17);
		public static final Holder<Chromosome> POLAR_BEAR_18 = register("polar_bear_18", 18);
		public static final Holder<Chromosome> POLAR_BEAR_19 = register("polar_bear_19", 19);
		public static final Holder<Chromosome> POLAR_BEAR_20 = register("polar_bear_20", 20);
		public static final Holder<Chromosome> POLAR_BEAR_21 = register("polar_bear_21", 21);
		public static final Holder<Chromosome> POLAR_BEAR_22 = register("polar_bear_22", 22);
		public static final Holder<Chromosome> POLAR_BEAR_23 = register("polar_bear_23", 23);
		public static final Holder<Chromosome> POLAR_BEAR_24 = register("polar_bear_24", 24);
		public static final Holder<Chromosome> POLAR_BEAR_25 = register("polar_bear_25", 25);
		public static final Holder<Chromosome> POLAR_BEAR_26 = register("polar_bear_26", 26);
		public static final Holder<Chromosome> POLAR_BEAR_27 = register("polar_bear_27", 27);
		public static final Holder<Chromosome> POLAR_BEAR_28 = register("polar_bear_28", 28);
		public static final Holder<Chromosome> POLAR_BEAR_29 = register("polar_bear_29", 29);
		public static final Holder<Chromosome> POLAR_BEAR_30 = register("polar_bear_30", 30);
		public static final Holder<Chromosome> POLAR_BEAR_31 = register("polar_bear_31", 31);
		public static final Holder<Chromosome> POLAR_BEAR_32 = register("polar_bear_32", 32);
		public static final Holder<Chromosome> POLAR_BEAR_33 = register("polar_bear_33", 33);
		public static final Holder<Chromosome> POLAR_BEAR_34 = register("polar_bear_34", 34);
		public static final Holder<Chromosome> POLAR_BEAR_35 = register("polar_bear_35", 35);
		public static final Holder<Chromosome> POLAR_BEAR_36 = register("polar_bear_36", 36);
		public static final Holder<Chromosome> POLAR_BEAR_XY = register("polar_bear_xy", 37);

		private PolarBear() {
		}

		/**
		 * Initializes the polar bear chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for polar bears.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_28);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_29);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_30);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_31);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_32);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_33);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_34);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_35);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_36);
			RegistryRelations.registerEntityType2Chromosome(EntityType.POLAR_BEAR, POLAR_BEAR_XY);

			RegistryRelations.registerSexChromosome(POLAR_BEAR_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.POLAR_BEAR, POLAR_BEAR_XY);
		}
	}

	/**
	 * Built-in chromosomes for rabbits (hares).
	 */
	public static final class Rabbit {
		public static final Holder<Chromosome> RABBIT_1 = register("rabbit_1", 1);
		public static final Holder<Chromosome> RABBIT_2 = register("rabbit_2", 2);
		public static final Holder<Chromosome> RABBIT_3 = register("rabbit_3", 3);
		public static final Holder<Chromosome> RABBIT_4 = register("rabbit_4", 4);
		public static final Holder<Chromosome> RABBIT_5 = register("rabbit_5", 5);
		public static final Holder<Chromosome> RABBIT_6 = register("rabbit_6", 6);
		public static final Holder<Chromosome> RABBIT_7 = register("rabbit_7", 7);
		public static final Holder<Chromosome> RABBIT_8 = register("rabbit_8", 8);
		public static final Holder<Chromosome> RABBIT_9 = register("rabbit_9", 9);
		public static final Holder<Chromosome> RABBIT_10 = register("rabbit_10", 10);
		public static final Holder<Chromosome> RABBIT_11 = register("rabbit_11", 11);
		public static final Holder<Chromosome> RABBIT_12 = register("rabbit_12", 12);
		public static final Holder<Chromosome> RABBIT_13 = register("rabbit_13", 13);
		public static final Holder<Chromosome> RABBIT_14 = register("rabbit_14", 14);
		public static final Holder<Chromosome> RABBIT_15 = register("rabbit_15", 15);
		public static final Holder<Chromosome> RABBIT_16 = register("rabbit_16", 16);
		public static final Holder<Chromosome> RABBIT_17 = register("rabbit_17", 17);
		public static final Holder<Chromosome> RABBIT_18 = register("rabbit_18", 18);
		public static final Holder<Chromosome> RABBIT_19 = register("rabbit_19", 19);
		public static final Holder<Chromosome> RABBIT_20 = register("rabbit_20", 20);
		public static final Holder<Chromosome> RABBIT_21 = register("rabbit_21", 21);
		public static final Holder<Chromosome> RABBIT_22 = register("rabbit_22", 22);
		public static final Holder<Chromosome> RABBIT_23 = register("rabbit_23", 23);
		public static final Holder<Chromosome> RABBIT_XY = register("rabbit_xy", 24);

		private Rabbit() {
		}

		/**
		 * Initializes the rabbit chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for rabbits.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.RABBIT, RABBIT_XY);

			RegistryRelations.registerSexChromosome(RABBIT_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.RABBIT, RABBIT_XY);
		}
	}

	/**
	 * Built-in chromosomes for sheeps.
	 */
	public static final class Sheep {
		public static final Holder<Chromosome> SHEEP_1 = register("sheep_1", 1);
		public static final Holder<Chromosome> SHEEP_2 = register("sheep_2", 2);
		public static final Holder<Chromosome> SHEEP_3 = register("sheep_3", 3);
		public static final Holder<Chromosome> SHEEP_4 = register("sheep_4", 4);
		public static final Holder<Chromosome> SHEEP_5 = register("sheep_5", 5);
		public static final Holder<Chromosome> SHEEP_6 = register("sheep_6", 6);
		public static final Holder<Chromosome> SHEEP_7 = register("sheep_7", 7);
		public static final Holder<Chromosome> SHEEP_8 = register("sheep_8", 8);
		public static final Holder<Chromosome> SHEEP_9 = register("sheep_9", 9);
		public static final Holder<Chromosome> SHEEP_10 = register("sheep_10", 10);
		public static final Holder<Chromosome> SHEEP_11 = register("sheep_11", 11);
		public static final Holder<Chromosome> SHEEP_12 = register("sheep_12", 12);
		public static final Holder<Chromosome> SHEEP_13 = register("sheep_13", 13);
		public static final Holder<Chromosome> SHEEP_14 = register("sheep_14", 14);
		public static final Holder<Chromosome> SHEEP_15 = register("sheep_15", 15);
		public static final Holder<Chromosome> SHEEP_16 = register("sheep_16", 16);
		public static final Holder<Chromosome> SHEEP_17 = register("sheep_17", 17);
		public static final Holder<Chromosome> SHEEP_18 = register("sheep_18", 18);
		public static final Holder<Chromosome> SHEEP_19 = register("sheep_19", 19);
		public static final Holder<Chromosome> SHEEP_20 = register("sheep_20", 20);
		public static final Holder<Chromosome> SHEEP_21 = register("sheep_21", 21);
		public static final Holder<Chromosome> SHEEP_22 = register("sheep_22", 22);
		public static final Holder<Chromosome> SHEEP_23 = register("sheep_23", 23);
		public static final Holder<Chromosome> SHEEP_24 = register("sheep_24", 24);
		public static final Holder<Chromosome> SHEEP_25 = register("sheep_25", 25);
		public static final Holder<Chromosome> SHEEP_26 = register("sheep_26", 26);
		public static final Holder<Chromosome> SHEEP_XY = register("sheep_xy", 27);

		private Sheep() {
		}

		/**
		 * Initializes the sheep chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for sheep.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.SHEEP, SHEEP_XY);

			RegistryRelations.registerSexChromosome(SHEEP_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.SHEEP, SHEEP_XY);
		}
	}

	/**
	 * Built-in chromosomes for turtles.
	 */
	public static final class Turtle {
		public static final Holder<Chromosome> TURTLE_1 = register("turtle_1", 1);
		public static final Holder<Chromosome> TURTLE_2 = register("turtle_2", 2);
		public static final Holder<Chromosome> TURTLE_3 = register("turtle_3", 3);
		public static final Holder<Chromosome> TURTLE_4 = register("turtle_4", 4);
		public static final Holder<Chromosome> TURTLE_5 = register("turtle_5", 5);
		public static final Holder<Chromosome> TURTLE_6 = register("turtle_6", 6);
		public static final Holder<Chromosome> TURTLE_7 = register("turtle_7", 7);
		public static final Holder<Chromosome> TURTLE_8 = register("turtle_8", 8);
		public static final Holder<Chromosome> TURTLE_9 = register("turtle_9", 9);
		public static final Holder<Chromosome> TURTLE_10 = register("turtle_10", 10);
		public static final Holder<Chromosome> TURTLE_11 = register("turtle_11", 11);
		public static final Holder<Chromosome> TURTLE_12 = register("turtle_12", 12);
		public static final Holder<Chromosome> TURTLE_13 = register("turtle_13", 13);
		public static final Holder<Chromosome> TURTLE_14 = register("turtle_14", 14);
		public static final Holder<Chromosome> TURTLE_15 = register("turtle_15", 15);
		public static final Holder<Chromosome> TURTLE_16 = register("turtle_16", 16);
		public static final Holder<Chromosome> TURTLE_17 = register("turtle_17", 17);
		public static final Holder<Chromosome> TURTLE_18 = register("turtle_18", 18);
		public static final Holder<Chromosome> TURTLE_19 = register("turtle_19", 19);
		public static final Holder<Chromosome> TURTLE_20 = register("turtle_20", 20);
		public static final Holder<Chromosome> TURTLE_21 = register("turtle_21", 21);
		public static final Holder<Chromosome> TURTLE_22 = register("turtle_22", 22);
		public static final Holder<Chromosome> TURTLE_23 = register("turtle_23", 23);
		public static final Holder<Chromosome> TURTLE_24 = register("turtle_24", 24);
		public static final Holder<Chromosome> TURTLE_25 = register("turtle_25", 25);
		public static final Holder<Chromosome> TURTLE_26 = register("turtle_26", 26);
		public static final Holder<Chromosome> TURTLE_27 = register("turtle_27", 27);
		public static final Holder<Chromosome> TURTLE_XY = register("turtle_xy", 28);

		private Turtle() {
		}

		/**
		 * Initializes the turtle chromosomes (lazy initialization).
		 */
		public static void init() {
			// Lazy init
		}

		/**
		 * Registers the chromosome-to-entity-type relations for turtles.
		 */
		public static void registerRelations() {
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_1);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_2);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_3);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_4);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_5);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_6);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_7);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_8);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_9);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_10);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_11);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_12);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_13);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_14);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_15);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_16);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_17);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_18);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_19);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_20);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_21);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_22);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_23);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_24);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_25);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_26);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_27);
			RegistryRelations.registerEntityType2Chromosome(EntityType.TURTLE, TURTLE_XY);

			RegistryRelations.registerSexChromosome(TURTLE_XY, SexDetermination.XY);
			RegistryRelations.registerEntityTypeSexChromosome(EntityType.TURTLE, TURTLE_XY);
		}
	}

	private BuiltInChromosomes() {
	}

	/**
	 * Initializes all built-in chromosomes and enqueues relation registration tasks to the main thread. <br/>
	 * This method performs lazy initialization of chromosome definitions and schedules registry operations
	 * to avoid modifying thread-unsafe containers from mod loading threads.
	 *
	 * @param enqueue Task consumer that submits runnables to the main thread for execution,
	 *                preventing concurrent modification of thread-unsafe registries
	 */
	public static void init(Consumer<Runnable> enqueue) {
		Axolotl.init();
		Bee.init();
		Cat.init();
		Chicken.init();
		Cow.init();
		Donkey.init();
		Fox.init();
		Frog.init();
		Goat.init();
		Horse.init();
		Human.init();
		Mule.init();
		Ocelot.init();
		Panda.init();
		Parrot.init();
		Pig.init();
		PolarBear.init();
		Rabbit.init();
		Sheep.init();
		Turtle.init();

		enqueue.accept(Axolotl::registerRelations);
		enqueue.accept(Bee::registerRelations);
		enqueue.accept(Cat::registerRelations);
		enqueue.accept(Chicken::registerRelations);
		enqueue.accept(Cow::registerRelations);
		enqueue.accept(Donkey::registerRelations);
		enqueue.accept(Fox::registerRelations);
		enqueue.accept(Frog::registerRelations);
		enqueue.accept(Goat::registerRelations);
		enqueue.accept(Horse::registerRelations);
		enqueue.accept(Human::registerRelations);
		enqueue.accept(Mule::registerRelations);
		enqueue.accept(Ocelot::registerRelations);
		enqueue.accept(Panda::registerRelations);
		enqueue.accept(Parrot::registerRelations);
		enqueue.accept(Pig::registerRelations);
		enqueue.accept(PolarBear::registerRelations);
		enqueue.accept(Rabbit::registerRelations);
		enqueue.accept(Sheep::registerRelations);
		enqueue.accept(Turtle::registerRelations);
	}

	private static Holder<Chromosome> register(String name, int index) {
		return Services.PLATFORM.registerChromosome(new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT)), () -> new Chromosome(index));
	}
}
