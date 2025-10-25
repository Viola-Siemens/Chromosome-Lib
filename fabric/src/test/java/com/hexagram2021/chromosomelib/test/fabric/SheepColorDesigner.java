package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.common.gene.Gene;
import com.hexagram2021.chromosomelib.platform.Services;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

public class SheepColorDesigner {
	/**
	 * 黑色素酶生产基因，显性
	 */
	public static final Holder<Gene> MELANIN_D = registerGene("MELANIN_D");
	/**
	 * 黑色素酶隐性基因
	 */
	public static final Holder<Gene> MELANIN_R = registerGene("MELANIN_R");
	/**
	 * 黄色素酶生产基因，显性
	 */
	public static final Holder<Gene> URANIDIN_D = registerGene("URANIDIN_D");
	/**
	 * 黄色素酶隐性基因
	 */
	public static final Holder<Gene> URANIDIN_R = registerGene("URANIDIN_R");
	/**
	 * 黑色素代谢酶基因，使得毛色被淡化时，品红色和绿色色素更容易附着，显性
	 */
	public static final Holder<Gene> MELANIN_METABOLIC_D = registerGene("MELANIN_METABOLIC_D");
	/**
	 * 黑色素代谢酶隐性基因
	 */
	public static final Holder<Gene> MELANIN_METABOLIC_R = registerGene("MELANIN_METABOLIC_R");
	/**
	 * 毛色淡化基因，黑->灰->淡灰，棕->红->黄，橙->蓝->青，不完全显性
	 */
	public static final Holder<Gene> DILUTION_ID = registerGene("DILUTION_ID");
	/**
	 * 毛色淡化基因，隐性
	 */
	public static final Holder<Gene> DILUTION_R = registerGene("DILUTION_R");

	/**
	 * 非粉红色基因，显性
	 */
	public static final Holder<Gene> NONE_PINK_D = registerGene("NONE_PINK_D");
	/**
	 * 粉红色突变基因，无其它色素时生效，隐性
	 */
	public static final Holder<Gene> PINK_R = registerGene("PINK_R");
	/**
	 * 品红色素基因，共显性。
	 * <p>品红+绿=黄绿</p>
	 * <p>品红+灰/淡灰=品红</p>
	 * <p>品红+蓝=紫</p>
	 * <p>品红+青=淡蓝</p>
	 */
	public static final Holder<Gene> RED_D = registerGene("RED_D");
	/**
	 * 绿色素基因，共显性。
	 * <p>绿+品红=黄绿</p>
	 * <p>绿+灰/淡灰=绿</p>
	 * <p>绿+蓝=蓝</p>
	 * <p>绿+青=淡绿</p>
	 */
	public static final Holder<Gene> GREEN_D = registerGene("GREEN_D");
	/**
	 * 无特殊色素，隐性
	 */
	public static final Holder<Gene> NORMAL_COLOR_R = registerGene("NORMAL_COLOR_R");

	private static Holder<Gene> registerGene(String code) {
		return Services.PLATFORM.registerGene(new ResourceLocation(MODID, code.toLowerCase(Locale.ROOT)), () -> new Gene(code));
	}

	private SheepColorDesigner() {
	}

	public static void init() {
		RegistryRelations.registerDisableRelation(MELANIN_D, MELANIN_R);
		RegistryRelations.registerDisableRelation(URANIDIN_D, URANIDIN_R);
		RegistryRelations.registerDisableRelation(MELANIN_METABOLIC_D, MELANIN_METABOLIC_R);
		RegistryRelations.registerDisableRelation(NONE_PINK_D, PINK_R);
		RegistryRelations.registerDisableRelation(RED_D, NORMAL_COLOR_R);
		RegistryRelations.registerDisableRelation(GREEN_D, NORMAL_COLOR_R);

		// Not expected, but we can test our code.
		RegistryRelations.registerDisableRelation(MELANIN_R, NONE_PINK_D);
		RegistryRelations.registerDisableRelation(URANIDIN_R, NONE_PINK_D);
	}
}
