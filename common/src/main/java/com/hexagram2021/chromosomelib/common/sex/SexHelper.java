package com.hexagram2021.chromosomelib.common.sex;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeInstance;
import com.hexagram2021.chromosomelib.common.chromosome.ChromosomeType;
import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import com.hexagram2021.chromosomelib.common.entity.IHymenoptera;
import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

/**
 * Utility class for querying the biological sex of entities.
 *
 * @author liudongyu
 */
public final class SexHelper {
	/**
	 * Returns the biological sex of the given living entity.
	 *
	 * <p>Resolution order:
	 * <ol>
	 *   <li>If the entity implements {@link IHymenoptera}, returns the stored sex directly.</li>
	 *   <li>Otherwise, derives sex from sex chromosome instances via {@link SexDetermination}.</li>
	 *   <li>If no sex chromosome is registered for the entity type, returns {@link BiologicalSex#ASEXUAL}.</li>
	 * </ol>
	 *
	 * @param entity the living entity to query
	 * @return the biological sex
	 */
	public static BiologicalSex getSex(LivingEntity entity) {
		// Hymenoptera (bees): sex is stored directly, not derived from chromosomes
		if(entity instanceof IHymenoptera hymenoptera) {
			return hymenoptera.chromosomelib$getHymenopteraSex();
		}
		return getSex(entity.getType(), ((IChromosomeCarrier) entity).chromosomelib$getChromosomes());
	}

	/**
	 * Returns the biological sex based on chromosome instances and entity type.
	 *
	 * <p>This overload is used for chromosome-based sex determination only (XY / ZW).
	 * For Hymenoptera entities, use {@link #getSex(LivingEntity)} instead.</p>
	 *
	 * @param entityType  the entity type
	 * @param chromosomes the chromosome instance list
	 * @return the biological sex
	 */
	public static BiologicalSex getSex(EntityType<?> entityType, Collection<ChromosomeInstance> chromosomes) {
		Holder<Chromosome> sexChromosome = RegistryRelations.getSexChromosome(entityType);
		if(sexChromosome == null) {
			return BiologicalSex.ASEXUAL;
		}
		SexDetermination system = RegistryRelations.getSexDetermination(sexChromosome);
		if(system == null) {
			return BiologicalSex.ASEXUAL;
		}

		// Count LEFT and RIGHT instances of the sex chromosome
		int left = 0;
		int right = 0;
		for(ChromosomeInstance inst : chromosomes) {
			if(inst.chromosome().equals(sexChromosome)) {
				if(inst.type() == ChromosomeType.LEFT) {
					left++;
				} else {
					right++;
				}
			}
		}
		return system.resolve(left, right);
	}

	/**
	 * Returns whether two entities can breed based on sex compatibility.
	 * Entities with {@link BiologicalSex#ASEXUAL} sex are always compatible.
	 *
	 * @param entityA first entity
	 * @param entityB second entity
	 * @return true if the pair is a valid breeding pair
	 */
	public static boolean isCompatibleBreedingPair(LivingEntity entityA, LivingEntity entityB) {
		BiologicalSex sexA = getSex(entityA);
		BiologicalSex sexB = getSex(entityB);
		if(sexA == BiologicalSex.ASEXUAL || sexB == BiologicalSex.ASEXUAL) {
			return true;
		}
		return sexA != sexB;
	}

	private SexHelper() {
	}
}
