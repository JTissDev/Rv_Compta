package com.jtissdev_API.features.PCP.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link AnalyticDetail}.
 * Verifies constructors, accessors grouping, and JSON serialization.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 1.0.0
 */
class AnalyticDetailTest {

	@Test
	@DisplayName("Constructors - Should initialize fields correctly using different constructors")
	void constructors_ShouldInitializeFields() {
		// Test Default Constructor
		AnalyticDetail empty = new AnalyticDetail();
		assertThat(empty.getCode()).isNull();

		// Test Functional Constructor (1.0.1)
		AnalyticDetail functional = new AnalyticDetail(".442", "Banque", "Main Account");
		assertThat(functional.getCode()).isEqualTo(".442");
		assertThat(functional.getType()).isEqualTo("Banque");
		assertThat(functional.getName()).isEqualTo("Main Account");

		// Test Complete Constructor (1.0.2)
		AnalyticDetail complete = new AnalyticDetail(".Net", "Streaming", "Netflix", "Monthly sub");
		assertThat(complete.getCode()).isEqualTo(".Net");
		assertThat(complete.getDescription()).isEqualTo("Monthly sub");
	}

	@Test
	@DisplayName("Accessors - Should respect Getters/Setters grouping and data integrity")
	void accessors_ShouldHandleDataCorrectly() {
		// Given
		AnalyticDetail detail = new AnalyticDetail();
		String code = ".P106";
		String type = "Vehicule";
		String nom = "Peugeot 106";
		String desc = "Old car";

		// When (Testing Setters)
		detail.setCode(code);
		detail.setType(type);
		detail.setName(nom);
		detail.setDescription(desc);

		// Then (Testing Getters)
		assertThat(detail.getCode()).isEqualTo(code);
		assertThat(detail.getType()).isEqualTo(type);
		assertThat(detail.getName()).isEqualTo(nom);
		assertThat(detail.getDescription()).isEqualTo(desc);
	}

	@Test
	@DisplayName("Serialization - Should convert object to valid JsonObject")
	void serialization_ShouldProduceCorrectJson() {
		// Given
		AnalyticDetail detail = new AnalyticDetail(".229", "Banque", "Compte Leslie", "To delete");

		// When
		JsonObject json = detail.toJson();

		// Then
		assertThat(json.getString("code")).isEqualTo(".229");
		assertThat(json.getString("type")).isEqualTo("Banque");
		assertThat(json.getString("nom")).isEqualTo("Compte Leslie");
		assertThat(json.getString("description")).isEqualTo("To delete");
	}

	@Test
	@DisplayName("ToString - Should return formatted string for logging")
	void toString_ShouldReturnFormattedString() {
		// Given
		AnalyticDetail detail = new AnalyticDetail(".Rou", "Immo", "Bois Rouvres");

		// When & Then
		assertThat(detail.toString()).isEqualTo("[.Rou] Bois Rouvres (Immo)");
	}
}