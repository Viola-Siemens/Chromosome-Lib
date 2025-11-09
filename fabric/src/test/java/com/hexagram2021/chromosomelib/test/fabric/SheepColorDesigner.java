package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.common.chromosome.BuiltInChromosomes;
import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import com.hexagram2021.chromosomelib.common.trait.AbstractTraitType;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import com.hexagram2021.chromosomelib.common.trait.TraitHandler;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import com.hexagram2021.chromosomelib.registry.StableWeightedGeneList;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Locale;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

public class SheepColorDesigner {
	public static final class Genes {
		/**
		 * 黑色素酶生产基因，显性；位于 1 号染色体上
		 */
		public static final Holder<Gene> MELANIN_D = register("MELANIN_D");
		/**
		 * 黑色素酶隐性基因；位于 1 号染色体上
		 */
		public static final Holder<Gene> MELANIN_R = register("MELANIN_R");
		/**
		 * 黄色素酶生产基因，显性；位于 X 性染色体非同源区段上
		 */
		public static final Holder<Gene> URANIDIN_D = register("URANIDIN_D");
		/**
		 * 黄色素酶隐性基因；位于 X 性染色体非同源区段上
		 */
		public static final Holder<Gene> URANIDIN_R = register("URANIDIN_R");
		/**
		 * 黑色素代谢酶基因，使得黑色素被淡化时，品红色和绿色色素更容易附着，显性；位于 6 号染色体上
		 */
		public static final Holder<Gene> MELANIN_METABOLIC_D = register("MELANIN_METABOLIC_D");
		/**
		 * 黑色素代谢酶隐性基因；位于 6 号染色体上
		 */
		public static final Holder<Gene> MELANIN_METABOLIC_R = register("MELANIN_METABOLIC_R");
		/**
		 * 毛色淡化基因，黑->灰->淡灰，棕->红->黄，橙->蓝->青，不完全显性；位于 7 号染色体上
		 */
		public static final Holder<Gene> DILUTION_ID = register("DILUTION_ID");
		/**
		 * 毛色淡化基因，隐性；位于 7 号染色体上
		 */
		public static final Holder<Gene> DILUTION_R = register("DILUTION_R");
		/**
		 * 非粉红色基因，显性；位于 1 号染色体上
		 */
		public static final Holder<Gene> NONE_PINK_D = register("NONE_PINK_D");
		/**
		 * 粉红色突变基因，无其它色素时生效，隐性；位于 1 号染色体上
		 */
		public static final Holder<Gene> PINK_R = register("PINK_R");
		/**
		 * 品红色素基因，仅在黑色素淡化且可被代谢或无黑色素时生效，共显性；位于 20 号染色体上
		 * <p>品红+绿=黄绿</p>
		 * <p>品红+灰/淡灰/红=品红</p>
		 * <p>品红+蓝/黄=紫</p>
		 * <p>品红+青=淡蓝</p>
		 */
		public static final Holder<Gene> MAGENTA_D = register("MAGENTA_D");
		/**
		 * 绿色素基因，仅在黑色素淡化且可被代谢或无黑色素时生效，共显性；位于 20 号染色体上
		 * <p>绿+品红/黄=黄绿</p>
		 * <p>绿+红=黄</p>
		 * <p>绿+灰/淡灰=绿</p>
		 * <p>绿+蓝=蓝</p>
		 * <p>绿+青=黄绿</p>
		 */
		public static final Holder<Gene> GREEN_D = register("GREEN_D");
		/**
		 * 无特殊色素，隐性；位于 20 号染色体上
		 */
		public static final Holder<Gene> NORMAL_COLOR_R = register("NORMAL_COLOR_R");

		/**
		 * 雄性 SRY 基因；位于 Y 性染色体非同源区段上
		 */
		public static final Holder<Gene> SRY = register("SRY");

		private Genes() {
		}

		private static Holder<Gene> register(String code) {
			return Services.PLATFORM.registerGene(new ResourceLocation(MODID, code.toLowerCase(Locale.ROOT)), () -> new Gene(code));
		}
	}

	public static final class GeneLoci {
		public static final Holder<GeneLocus> MELANIN_ENZYME = registerHomologous("melanin_enzyme", 14, 0.000625D, 0.050000D);
		public static final Holder<GeneLocus> URANIDIN_ENZYME = registerLeft("uranidin_enzyme", 3, 0.001750D, 0.031250D);
		public static final Holder<GeneLocus> MELANIN_METABOLIC_ENZYME = registerHomologous("melanin_metabolic_enzyme", 5, 0.000250D, 0.003500D);
		public static final Holder<GeneLocus> DILUTION = registerHomologous("dilution", 19, 0.001500D, 0.003500D);
		public static final Holder<GeneLocus> PINK = registerHomologous("pink", 6, 0.000250D, 0.000250D);
		public static final Holder<GeneLocus> ABNORMAL = registerHomologous("abnormal", 7, 0.000300D, 0.000275D);
		public static final Holder<GeneLocus> SRY = registerRight("sry", 8, 0.000250D, 0.000250D);

		private GeneLoci() {
		}

		private static Holder<GeneLocus> registerHomologous(String name, int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			return Services.PLATFORM.registerGeneLocus(new ResourceLocation(MODID, name), () -> GeneLocus.homologous(index, possibilityOfMutation, possibilityOfCrossingOver));
		}

		@SuppressWarnings("SameParameterValue")
		private static Holder<GeneLocus> registerLeft(String name, int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			return Services.PLATFORM.registerGeneLocus(new ResourceLocation(MODID, name), () -> GeneLocus.left(index, possibilityOfMutation, possibilityOfCrossingOver));
		}

		@SuppressWarnings("SameParameterValue")
		private static Holder<GeneLocus> registerRight(String name, int index, double possibilityOfMutation, double possibilityOfCrossingOver) {
			return Services.PLATFORM.registerGeneLocus(new ResourceLocation(MODID, name), () -> GeneLocus.right(index, possibilityOfMutation, possibilityOfCrossingOver));
		}
	}

	public static final class TraitTypes {
		public static final Holder<TraitType> COLOR = register("color");

		private TraitTypes() {
		}

		@SuppressWarnings("SameParameterValue")
		private static Holder<TraitType> register(String code) {
			ResourceLocation name = new ResourceLocation(MODID, code);
			return Services.PLATFORM.registerTraitType(name, () -> new AbstractTraitType(name) {
				@Override
				public Holder<Trait> example() {
					return Traits.WHITE;
				}
			});
		}
	}

	public static final class Traits {
		public static final Holder<Trait> BLACK = registerColorTrait("black");
		public static final Holder<Trait> BLUE = registerColorTrait("blue");
		public static final Holder<Trait> BROWN = registerColorTrait("brown");
		public static final Holder<Trait> CYAN = registerColorTrait("cyan");
		public static final Holder<Trait> GRAY = registerColorTrait("gray");
		public static final Holder<Trait> GREEN = registerColorTrait("green");
		public static final Holder<Trait> LIGHT_BLUE = registerColorTrait("light_blue");
		public static final Holder<Trait> LIGHT_GRAY = registerColorTrait("light_gray");
		public static final Holder<Trait> LIME = registerColorTrait("lime");
		public static final Holder<Trait> MAGENTA = registerColorTrait("magenta");
		public static final Holder<Trait> ORANGE = registerColorTrait("orange");
		public static final Holder<Trait> PINK = registerColorTrait("pink");
		public static final Holder<Trait> PURPLE = registerColorTrait("purple");
		public static final Holder<Trait> RED = registerColorTrait("red");
		public static final Holder<Trait> WHITE = registerColorTrait("white");
		public static final Holder<Trait> YELLOW = registerColorTrait("yellow");

		private Traits() {
		}

		@SuppressWarnings("Convert2Lambda")
		private static Holder<Trait> registerColorTrait(String code) {
			// DO NOT "OPTIMIZE" IT TO "() -> () -> TraitTypes.COLOR" because now it will create a new object each time you call it, but after "optimizing" it will be a lazy-constant.
			return Services.PLATFORM.registerTrait(new ResourceLocation(MODID, code), () -> new Trait() {
				@Override
				public Holder<TraitType> getType() {
					return TraitTypes.COLOR;
				}
			});
		}
	}

	private SheepColorDesigner() {
	}

	public static void init() {
		// ---- Start: Register Chromosome to Gene Locus Relations ----
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_1, GeneLoci.MELANIN_ENZYME);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_XY, GeneLoci.URANIDIN_ENZYME);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_6, GeneLoci.MELANIN_METABOLIC_ENZYME);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_7, GeneLoci.DILUTION);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_1, GeneLoci.PINK);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_20, GeneLoci.ABNORMAL);
		RegistryRelations.registerChromosome2GeneLocus(BuiltInChromosomes.Sheep.SHEEP_XY, GeneLoci.SRY);
		// ---- End: Register Chromosome to Gene Locus Relations ----

		// ---- Start: Register Gene Locus to Gene Relations ----
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.MELANIN_ENZYME, Genes.MELANIN_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.MELANIN_ENZYME, Genes.MELANIN_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.URANIDIN_ENZYME, Genes.URANIDIN_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.URANIDIN_ENZYME, Genes.URANIDIN_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.MELANIN_METABOLIC_ENZYME, Genes.MELANIN_METABOLIC_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.MELANIN_METABOLIC_ENZYME, Genes.MELANIN_METABOLIC_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.DILUTION, Genes.DILUTION_ID);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.DILUTION, Genes.DILUTION_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.PINK, Genes.NONE_PINK_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.PINK, Genes.PINK_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.ABNORMAL, Genes.MAGENTA_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.ABNORMAL, Genes.GREEN_D);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.ABNORMAL, Genes.NORMAL_COLOR_R);
		RegistryRelations.registerGeneLocus2Gene(GeneLoci.SRY, Genes.SRY);
		// ---- End: Register Gene Locus to Gene Relations ----

		// ---- Start: Register Disable Relations ----
		RegistryRelations.registerDisableRelation(Genes.MELANIN_D, Genes.MELANIN_R);
		RegistryRelations.registerDisableRelation(Genes.URANIDIN_D, Genes.URANIDIN_R);
		RegistryRelations.registerDisableRelation(Genes.MELANIN_METABOLIC_D, Genes.MELANIN_METABOLIC_R);
		RegistryRelations.registerDisableRelation(Genes.NONE_PINK_D, Genes.PINK_R);
		RegistryRelations.registerDisableRelation(Genes.MAGENTA_D, Genes.NORMAL_COLOR_R);
		RegistryRelations.registerDisableRelation(Genes.GREEN_D, Genes.NORMAL_COLOR_R);

		// Not expected, but we can test our code.
		// RegistryRelations.registerDisableRelation(Genes.MELANIN_R, Genes.NONE_PINK_D);
		// RegistryRelations.registerDisableRelation(Genes.URANIDIN_R, Genes.NONE_PINK_D);
		// ---- End: Register Disable Relations ----

		// ---- Start: Register Entity Type to Trait Type Relations ----
		RegistryRelations.registerEntityType2TraitType(EntityType.SHEEP, TraitTypes.COLOR);
		// ---- End: Register Entity Type to Trait Type Relations ----

		// ---- Start: Register Gene Frequencies ----
		RegistryRelations.registerGeneFrequency(
				GeneLoci.MELANIN_ENZYME,
				StableWeightedGeneList.builder()
						.add(Genes.MELANIN_D, 1)
						.add(Genes.MELANIN_R, 9)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.URANIDIN_ENZYME,
				StableWeightedGeneList.builder()
						.add(Genes.URANIDIN_D, 1)
						.add(Genes.URANIDIN_R, 19)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.MELANIN_METABOLIC_ENZYME,
				StableWeightedGeneList.builder()
						.add(Genes.MELANIN_METABOLIC_D, 1)
						.add(Genes.MELANIN_METABOLIC_R, 15)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.DILUTION,
				StableWeightedGeneList.builder()
						.add(Genes.DILUTION_ID, 1)
						.add(Genes.DILUTION_R, 3)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.PINK,
				StableWeightedGeneList.builder()
						.add(Genes.NONE_PINK_D, 59)
						.add(Genes.PINK_R, 1)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.ABNORMAL,
				StableWeightedGeneList.builder()
						.add(Genes.MAGENTA_D, 3)
						.add(Genes.GREEN_D, 2)
						.add(Genes.NORMAL_COLOR_R, 95)
		);
		RegistryRelations.registerGeneFrequency(
				GeneLoci.SRY,
				StableWeightedGeneList.builder().add(Genes.SRY, 1)
		);
		// ---- End: Register Gene Frequencies ----

		// ---- Start: Register Trait Handlers ----
		TraitHandler.registerHandler(TraitTypes.COLOR, hasActiveGene -> {
			int melanin = hasActiveGene.applyAsInt(Genes.MELANIN_D);
			int uranidin = hasActiveGene.applyAsInt(Genes.URANIDIN_D);
			int dilution = hasActiveGene.applyAsInt(Genes.DILUTION_ID);
			int melaninMetabolic = hasActiveGene.applyAsInt(Genes.MELANIN_METABOLIC_D);
			int magenta = hasActiveGene.applyAsInt(Genes.MAGENTA_D);
			int green = hasActiveGene.applyAsInt(Genes.GREEN_D);
			int pink = hasActiveGene.applyAsInt(Genes.PINK_R);
			if(melanin > 0) {
				if(uranidin > 0) {
					// 黑 + 橙 = 棕色底色
					if(dilution == 0) {
						return Traits.BROWN;
					}
					if(melaninMetabolic == 0) {
						if(dilution == 1) {
							return Traits.RED;
						}
						return Traits.YELLOW;
					}
					if(magenta > 0) {
						if(green > 0) {
							return Traits.LIME;
						}
						if(dilution == 1) {
							return Traits.MAGENTA;
						}
						return Traits.PURPLE;
					}
					if(green > 0) {
						if(dilution == 1) {
							return Traits.YELLOW;
						}
						return Traits.GREEN;
					}
					if(dilution == 1) {
						return Traits.RED;
					}
					return Traits.YELLOW;
				}
				// 黑色底色
				if(dilution == 0) {
					return Traits.BLACK;
				}
				if(melaninMetabolic == 0) {
					if(dilution == 1) {
						return Traits.GRAY;
					}
					return Traits.LIGHT_GRAY;
				}
				if(magenta > 0) {
					if(green > 0) {
						return Traits.LIME;
					}
					return Traits.MAGENTA;
				}
				if(green > 0) {
					if(dilution == 1) {
						return Traits.YELLOW;
					}
					return Traits.GREEN;
				}
				if(dilution == 1) {
					return Traits.GRAY;
				}
				return Traits.LIGHT_GRAY;
			}
			if(uranidin > 0) {
				// 橙色底色
				if(dilution == 0) {
					return Traits.ORANGE;
				}
				if(magenta > 0) {
					if(green > 0) {
						return Traits.LIME;
					}
					if(dilution == 1) {
						return Traits.PURPLE;
					}
					return Traits.LIGHT_BLUE;
				}
				if(green > 0) {
					if(dilution == 1) {
						return Traits.BLUE;
					}
					return Traits.LIME;
				}
				if(dilution == 1) {
					return Traits.BLUE;
				}
				return Traits.CYAN;
			}
			//白色底色
			if(magenta > 0) {
				if(green > 0) {
					return Traits.LIME;
				}
				return Traits.MAGENTA;
			}
			if(green > 0) {
				return Traits.GREEN;
			}
			if(pink > 0) {
				return Traits.PINK;
			}
			return Traits.WHITE;
		});
		// ---- End: Register Trait Handlers ----
	}
}
