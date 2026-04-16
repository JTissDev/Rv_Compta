package com.jtissdev_API.features.PCP.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Tiers} DTO.
 * Verifies constructor integrity, setter logic, and JSON serialization.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 1.0.0
 */
class TiersTest {

	// =========================================================
	// == CONSTRUCTOR TESTS                                   ==
	// =========================================================

	@Test
	@DisplayName("Default constructor - should initialize with nulls")
	void defaultConstructor_shouldInitializeWithNulls() {
		// When
		Tiers tiers = new Tiers();

		// Then
		assertThat(tiers.getId()).isNull();
		assertThat(tiers.getName()).isNull();
		assertThat(tiers.getThirdPartyType()).isNull();
		assertThat(tiers.getDescription()).isNull();
	}

	@Test
	@DisplayName("Functional constructor (No ID) - should initialize fields via setters")
	void functionalConstructor_shouldInitializeFields() {
		// Given
		String name = "EDF";
		String type = "VENDOR";

		// When
		Tiers tiers = new Tiers(name, type);

		// Then
		assertThat(tiers.getName()).isEqualTo(name);
		assertThat(tiers.getThirdPartyType()).isEqualTo(type);
		assertThat(tiers.getId()).isNull();
	}

	@Test
	@DisplayName("Complete constructor - should initialize all fields including ID")
	void completeConstructor_shouldInitializeAllFields() {
		// Given
		Long id = 42L;
		String name = "Employer Corp";
		String type = "EMPLOYER";

		// When
		Tiers tiers = new Tiers(id, name, type);

		// Then
		assertThat(tiers.getId()).isEqualTo(id);
		assertThat(tiers.getName()).isEqualTo(name);
		assertThat(tiers.getThirdPartyType()).isEqualTo(type);
	}

	// =========================================================
	// == LOGIC & SERIALIZATION TESTS                         ==
	// =========================================================

	@Test
	@DisplayName("toJson - should produce valid JsonObject with expected values")
	void toJson_shouldProduceCorrectJson() {
		// Given
		Tiers tiers = new Tiers(10L, "Water Co", "VENDOR");
		tiers.setDescription("Monthly subscription");

		// When
		JsonObject json = tiers.toJson();

		// Then
		assertThat(json.getInt("id")).isEqualTo(10);
		assertThat(json.getString("name")).isEqualTo("Water Co");
		assertThat(json.getString("thirdPartyType")).isEqualTo("VENDOR");
		assertThat(json.getString("description")).isEqualTo("Monthly subscription");
	}

	@Test
	@DisplayName("toJson - should handle null fields with default fallback values")
	void toJson_shouldHandleNulls() {
		// Given
		Tiers tiers = new Tiers(); // All null

		// When
		JsonObject json = tiers.toJson();

		// Then
		assertThat(json.getInt("id")).isEqualTo(-1);
		assertThat(json.getString("name")).isEqualTo("Unknown");
		assertThat(json.getString("thirdPartyType")).isEqualTo("MISC");
		assertThat(json.getString("description")).isEmpty();
	}

	@Test
	@DisplayName("toString - should return formatted string for logs")
	void toString_shouldReturnFormattedString() {
		// Given
		Tiers tiers = new Tiers(1L, "TestName", "TEST_TYPE");

		// When
		String result = tiers.toString();

		// Then
		assertThat(result).contains("[1]", "TestName", "TEST_TYPE");
	}
}