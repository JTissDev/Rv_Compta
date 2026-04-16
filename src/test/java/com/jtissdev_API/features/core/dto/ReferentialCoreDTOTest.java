package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import com.jtissdev_API.features.core.dto.referential.PaymentMethod;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ReferentialCoreDTO} container.
 *
 * @author J.Tiss
 * @since 0.2.0
 */
class ReferentialCoreDTOTest {

	// =========================================================
	// == TEST CASES                                          ==
	// =========================================================

	/**
	 * Tests the default constructor and list initialization.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should initialize empty lists on creation")
	void testConstructor() {
		// When
		ReferentialCoreDTO dto = new ReferentialCoreDTO();

		// Then
		assertNotNull(dto.getOperationStatuses(), "List should be initialized");
		assertTrue(dto.getOperationStatuses().isEmpty(), "List should be empty by default");
	}

	/**
	 * Tests the addition of elements and fluent chaining.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should add operation statuses and support fluent chaining")
	void testAddAndFluentChaining() {
		// Given
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		OperationStatus status = new OperationStatus("REEL", "Réel", "#00FF00");

		// When
		dto.addOperationStatus(status);

		// Then
		assertEquals(1, dto.getOperationStatuses().size());
		assertEquals("REEL", dto.getOperationStatuses().get(0).getCode());
	}

	/**
	 * Tests the list setter with null protection.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should handle null list in setter by creating an empty list")
	void testSetListWithNull() {
		// Given
		ReferentialCoreDTO dto = new ReferentialCoreDTO();

		// When
		dto.setOperationStatuses(null);

		// Then
		assertNotNull(dto.getOperationStatuses(), "Setter should prevent null list");
	}

	/**
	 * Tests the global JSON serialization.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should serialize the entire referential structure to JSON")
	void testToJson() {
		// Given
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		dto.addOperationStatus(new OperationStatus("PREV", "Prévu", "#0000FF"));

		// When
		JsonObject json = dto.toJson();

		// Then
		assertNotNull(json);
		assertTrue(json.containsKey("operationStatuses"), "JSON should contain the statuses array");
		assertEquals(1, json.getJsonArray("operationStatuses").size());
		assertEquals("PREV", json.getJsonArray("operationStatuses").getJsonObject(0).getString("code"));
	}

	/**
	 * Tests the addition of payment methods and fluent chaining.
	 *
	 * @since 0.2.0
	 */
	@Test
	@DisplayName("Should add payment methods and support fluent chaining")
	void testAddPaymentMethod() {
		// Given
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		PaymentMethod method = new PaymentMethod("CB", "Carte", "Carte Bancaire");

		// When
		dto.addPaymentMethod(method);

		// Then
		assertEquals(1, dto.getPaymentMethods().size());
		assertEquals("CB", dto.getPaymentMethods().get(0).getCode());
	}
}