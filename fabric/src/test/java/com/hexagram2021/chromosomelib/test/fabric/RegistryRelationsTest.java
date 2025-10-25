package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.registry.RegistryRelations;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistryRelationsTest {
	@BeforeAll
	static void bootstrap() {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		SheepColorDesigner.init();
		RegistryRelations.freezeAndBuild();
	}

	@Test
	void testTopologicalSort() {
		Assertions.assertEquals(0, SheepColorDesigner.MELANIN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.MELANIN_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.URANIDIN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.URANIDIN_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.MELANIN_METABOLIC_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.MELANIN_METABOLIC_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.DILUTION_ID.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.DILUTION_R.value().topologicalOrder);
		Assertions.assertEquals(2, SheepColorDesigner.NONE_PINK_D.value().topologicalOrder);
		Assertions.assertEquals(3, SheepColorDesigner.PINK_R.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.RED_D.value().topologicalOrder);
		Assertions.assertEquals(0, SheepColorDesigner.GREEN_D.value().topologicalOrder);
		Assertions.assertEquals(1, SheepColorDesigner.NORMAL_COLOR_R.value().topologicalOrder);
	}
}
