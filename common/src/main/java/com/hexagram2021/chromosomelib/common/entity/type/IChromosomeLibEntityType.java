package com.hexagram2021.chromosomelib.common.entity.type;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.trait.Trait;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

@SuppressWarnings("java:S100")
public interface IChromosomeLibEntityType {
	void chromosomelib$setChromosomes(Int2ObjectMap<Holder<Chromosome>> chromosomes);
	void chromosomelib$setTraits(HolderSet<Trait> traits);
}
