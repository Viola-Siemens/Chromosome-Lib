package com.hexagram2021.chromosomelib.test.fabric;

import com.hexagram2021.chromosomelib.common.sex.BiologicalSex;
import com.hexagram2021.chromosomelib.common.sex.SexDetermination;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.StringRepresentable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BiologicalSex} enum (TC-001 ~ TC-003)
 * and {@link SexDetermination} enum (TC-010 ~ TC-014).
 *
 * <p>These are pure unit tests with no dependency on Minecraft world state or registry.
 * No {@code @BeforeAll} bootstrap is required.</p>
 *
 * @author liudongyu
 */
class SexEnumTest {
	// ========================
	// 4.1 BiologicalSex
	// ========================

	/**
	 * TC-001: Verify serialized names of all {@link BiologicalSex} enum values.
	 */
	@Test
	void testBiologicalSexSerializedNames() {
		Assertions.assertEquals("male", BiologicalSex.MALE.getSerializedName());
		Assertions.assertEquals("female", BiologicalSex.FEMALE.getSerializedName());
		Assertions.assertEquals("asexual", BiologicalSex.ASEXUAL.getSerializedName());
	}

	/**
	 * TC-002: Verify that {@link BiologicalSex#CODEC} correctly round-trips all enum values.
	 */
	@Test
	void testBiologicalSexCodecRoundTrip() {
		for(BiologicalSex sex : BiologicalSex.values()) {
			// Encode to JsonElement
			var encoded = BiologicalSex.CODEC.encodeStart(JsonOps.INSTANCE, sex);
			Assertions.assertTrue(encoded.result().isPresent(),
					"Encoding %s should succeed".formatted(sex));
			// Decode back
			var decoded = BiologicalSex.CODEC.parse(JsonOps.INSTANCE, encoded.result().get());
			Assertions.assertTrue(decoded.result().isPresent(),
					"Decoding %s result should succeed".formatted(sex));
			Assertions.assertEquals(sex, decoded.result().get(),
					"Round-trip should restore original value for %s".formatted(sex));
		}
	}

	/**
	 * TC-003: Verify that {@link BiologicalSex#values()} returns exactly 3 entries and
	 * that {@link StringRepresentable#fromEnum} accepts the enum without throwing.
	 */
	@Test
	void testBiologicalSexEnumCompleteness() {
		BiologicalSex[] values = BiologicalSex.values();
		Assertions.assertEquals(3, values.length,
				"BiologicalSex should have exactly 3 values (MALE, FEMALE, ASEXUAL)");
		// Verify all expected members are present
		Assertions.assertNotNull(BiologicalSex.MALE);
		Assertions.assertNotNull(BiologicalSex.FEMALE);
		Assertions.assertNotNull(BiologicalSex.ASEXUAL);
		// Verify StringRepresentable.fromEnum does not throw
		Assertions.assertDoesNotThrow(() -> StringRepresentable.fromEnum(BiologicalSex::values));
	}

	// ========================
	// 4.2 SexDetermination
	// ========================

	/**
	 * TC-010: XY system – LEFT+LEFT (XX) resolves to FEMALE.
	 */
	@Test
	void testXyLeftLeftIsFemale() {
		Assertions.assertEquals(BiologicalSex.FEMALE, SexDetermination.XY.resolve(2, 0));
	}

	/**
	 * TC-011: XY system – LEFT+RIGHT (XY) resolves to MALE.
	 */
	@Test
	void testXyLeftRightIsMale() {
		Assertions.assertEquals(BiologicalSex.MALE, SexDetermination.XY.resolve(1, 1));
	}

	/**
	 * TC-012: ZW system – LEFT+LEFT (ZZ) resolves to MALE.
	 */
	@Test
	void testZwLeftLeftIsMale() {
		Assertions.assertEquals(BiologicalSex.MALE, SexDetermination.ZW.resolve(2, 0));
	}

	/**
	 * TC-013: ZW system – LEFT+RIGHT (ZW) resolves to FEMALE.
	 */
	@Test
	void testZwLeftRightIsFemale() {
		Assertions.assertEquals(BiologicalSex.FEMALE, SexDetermination.ZW.resolve(1, 1));
	}

	/**
	 * TC-014: {@link SexDetermination#resolve} handles the right=0 edge case
	 * (X0 treated same as XX; Z0 treated same as ZZ).
	 */
	@Test
	void testResolveRightZeroBoundary() {
		// X0: no RIGHT chromosome, treated as XX → FEMALE
		Assertions.assertEquals(BiologicalSex.FEMALE, SexDetermination.XY.resolve(1, 0));
		// Z0: no RIGHT chromosome, treated as ZZ → MALE
		Assertions.assertEquals(BiologicalSex.MALE, SexDetermination.ZW.resolve(1, 0));
	}
}
