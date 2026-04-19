package com.jtissdev_API.features.core.dto.referential;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link OperationStatus} DTO.
 *
 * @author J.Tiss
 * @since 0.2.0
 */
class OperationStatusTest {

	// =========================================================
	// == TEST CASES                                          ==
	// =========================================================

	/**
	 * Tests the full constructor and getters.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should correctly initialize fields via constructor")
	void testFullConstructorAndGetters() {
		// Given
		String code = "PREV";
		String nom = "Prévisionnel";
		String color = "#3498db";

		// When
		OperationStatus status = new OperationStatus(code, nom, color);

		// Then
		assertEquals(code, status.getCode(), "Code should match constructor input");
		assertEquals(nom, status.getName(), "Name should match constructor input");
		assertEquals(color, status.getColor(), "Color should match constructor input");
	}

	/**
	 * Tests the fluent setters and basic getters.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should support fluent chaining and update fields")
	void testSettersAndFluentChaining() {
		// Given
		OperationStatus status = new OperationStatus();

		// When
		status.setCode("REEL")
				.setName("Réel")
				.setColor("#2ecc71");

		// Then
		assertEquals("REEL", status.getCode());
		assertEquals("Réel", status.getName());
		assertEquals("#2ecc71", status.getColor());
	}

	/**
	 * Tests the JSON serialization.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should convert instance to a valid JsonObject")
	void testToJson() {
		// Given
		OperationStatus status = new OperationStatus("WAIT", "En attente", "#f1c40f");

		// When
		JsonObject json = status.toJson();

		// Then
		assertNotNull(json, "JSON output should not be null");
		assertEquals("WAIT", json.getString("code"));
		assertEquals("En attente", json.getString("nom"));
		assertEquals("#f1c40f", json.getString("color"));
	}

	/**
	 * Tests JSON serialization with null fields to ensure no crash.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should handle null fields during JSON serialization")
	void testToJsonWithNullFields() {
		// Given
		OperationStatus status = new OperationStatus();

		// When
		JsonObject json = status.toJson();

		// Then
		assertNotNull(json);
		assertFalse(json.containsKey("code"), "Null code should not be in JSON");
		assertFalse(json.containsKey("nom"), "Null name should not be in JSON");
		assertFalse(json.containsKey("color"), "Null color should not be in JSON");
	}
}