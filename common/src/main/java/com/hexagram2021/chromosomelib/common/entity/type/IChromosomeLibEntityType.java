package com.hexagram2021.chromosomelib.common.entity.type;

import com.hexagram2021.chromosomelib.common.chromosome.Chromosome;
import com.hexagram2021.chromosomelib.common.trait.TraitType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

@SuppressWarnings("java:S100")
public interface IChromosomeLibEntityType {
	Int2ObjectMap<Holder<Chromosome>> chromosomelib$getChromosomes();
	void chromosomelib$setChromosomes(Int2ObjectMap<Holder<Chromosome>> chromosomes);
	HolderSet<TraitType> chromosomelib$getTraitTypes();
	void chromosomelib$setTraitTypes(HolderSet<TraitType> traits);
}
