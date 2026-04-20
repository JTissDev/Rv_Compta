package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link AnalyticDetail}.
 * Verifies constructors, accessors grouping, and JSON serialization.
 *
 * @author J.Tiss
 * @version 1.1.0
 * @since 0.4
 */
class AnalyticDetailTest {

	// =========================================================
	// == METHODS (TEST CASES)                                ==
	// =========================================================

	/**
	 * Vérifie que le constructeur vide et les setters fonctionnent correctement.
	 */
	@Test
	void testEmptyConstructorAndSetters() {
		AnalyticDetail detail = new AnalyticDetail();
		detail.setCode(".442");
		detail.setType("Banque");
		detail.setName("Compte Courant");
		detail.setDescription("Compte principal");

		assertEquals(".442", detail.getCode(), "Le code doit être '.442'");
		assertEquals("Banque", detail.getType(), "Le type doit être 'Banque'");
		assertEquals("Compte Courant", detail.getName(), "Le nom doit correspondre");
		assertEquals("Compte principal", detail.getDescription(), "La description doit correspondre");
	}

	/**
	 * Vérifie que le constructeur complet initialise bien tous les champs.
	 */
	@Test
	void testFullConstructor() {
		AnalyticDetail detail = new AnalyticDetail(".512", "Caisse", "Caisse Principale", "Petite monnaie");

		assertEquals(".512", detail.getCode());
		assertEquals("Caisse", detail.getType());
		assertEquals("Caisse Principale", detail.getName());
		assertEquals("Petite monnaie", detail.getDescription());
	}

	/**
	 *
	 */
	@Test
	void testFromJsonConstructor() {
		JsonObject json = Json.createObjectBuilder()
				.add("code", ".442")
				.add("type", "Banque")
				.add("name", "Compte Courant")
				.add("description", "Compte principal")
				.build();

		AnalyticDetail detail = new AnalyticDetail(json);

		assertEquals(".442", detail.getCode());
		assertEquals("Banque", detail.getType());
		assertEquals("Compte Courant", detail.getName());
		assertEquals("Compte principal", detail.getDescription());
	}
	/**
	 * Vérifie que l'export JSON génère bien toutes les clés avec les bonnes valeurs.
	 */
	@Test
	void testToJson() {
		AnalyticDetail detail = new AnalyticDetail(".442", "Banque", "Compte Courant", "Description test");
		JsonObject json = detail.toJson();

		assertEquals(".442", json.getString("code"));
		assertEquals("Banque", json.getString("type"));
		assertEquals("Compte Courant", json.getString("name"));
		assertEquals("Description test", json.getString("description"));
	}



	/**
	 * Vérifie que la surcharge de toString contient bien les informations essentielles.
	 */
	@Test
	void testToString() {
		AnalyticDetail detail = new AnalyticDetail(".442", "Banque", "Compte Courant");
		String result = detail.toString();

		assertTrue(result.contains(".442"), "Le toString doit contenir le code");
		assertTrue(result.contains("Compte Courant"), "Le toString doit contenir le nom");
		assertTrue(result.contains("Banque"), "Le toString doit contenir le type");
	}
}