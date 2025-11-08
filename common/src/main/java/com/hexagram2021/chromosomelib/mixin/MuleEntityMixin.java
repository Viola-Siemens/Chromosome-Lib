package com.hexagram2021.chromosomelib.mixin;

import com.hexagram2021.chromosomelib.common.entity.IChromosomeCarrier;
import net.minecraft.world.entity.animal.horse.Mule;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Mule.class)
public abstract class MuleEntityMixin implements IChromosomeCarrier {
	@Override
	public int chromosomelib$getPloidy() {
		return 1;
	}
}
