package com.jtissdev_API.features.core.dto.referential;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PaymentMethod} DTO.
 *
 * @author J.Tiss
 * @since 0.2.0
 */
class PaymentMethodTest {

	// =========================================================
	// == TEST CASES                                          ==
	// =========================================================

	@Test
	@DisplayName("Should correctly initialize fields via constructor")
	void testConstructorAndGetters() {
		// Given
		String code = "CHQ";
		String nom = "Chèque";
		String desc = "Paiement par chèque bancaire";

		// When
		PaymentMethod method = new PaymentMethod(code, nom, desc);

		// Then
		assertEquals(code, method.getCode());
		assertEquals(nom, method.getName());
		assertEquals(desc, method.getDescription());
	}

	@Test
	@DisplayName("Should support fluent chaining")
	void testFluentSetters() {
		// Given
		PaymentMethod method = new PaymentMethod();

		// When
		method.setCode("CB").setName("Carte").setDescription("Carte bleue");

		// Then
		assertEquals("CB", method.getCode());
		assertEquals("Carte", method.getName());
	}

	@Test
	@DisplayName("Should convert to valid JsonObject")
	void testToJson() {
		// Given
		PaymentMethod method = new PaymentMethod("VIR", "Virement", "Virement SEPA");

		// When
		JsonObject json = method.toJson();

		// Then
		assertNotNull(json);
		assertEquals("VIR", json.getString("code"));
		assertEquals("Virement", json.getString("nom"));
		assertEquals("Virement SEPA", json.getString("description"));
	}
}