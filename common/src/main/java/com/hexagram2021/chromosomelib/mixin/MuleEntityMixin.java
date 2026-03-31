package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import net.minecraft.world.entity.animal.horse.Mule;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin to {@link Mule} defining special genetic properties. <br/>
 * Mules are sterile hybrids with haploid chromosome sets (ploidy = 1).
 *
 * @author liudongyu
 */
@Mixin(Mule.class)
public abstract class MuleEntityMixin implements IChromosomeCarrier {
	/**
	 * Returns the ploidy level for mules. <br/>
	 * Mules are sterile hybrids, so they have haploid (ploidy = 1) chromosome sets.
	 *
	 * @return 1 (haploid)
	 */
	@Override
	public int chromosomelib$getPloidy() {
		return 1;
	}
}
