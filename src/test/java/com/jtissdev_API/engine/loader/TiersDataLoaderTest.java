package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.PCP.dto.Tiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link TiersDataLoader}.
 * Verifies JSON parsing logic and mapping to Tiers DTO.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 1.0.0
 */
class TiersDataLoaderTest {

	private TiersDataLoader loader;

	/**
	 * Set up the testing environment.
	 * @since 1.0.0
	 */
	@BeforeEach
	void setUp() {
		loader = new TiersDataLoader();
	}

	/**
	 * Verifies that a RuntimeException is thrown with the correct message
	 * when the Tiers JSON file is not found.
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should throw RuntimeException when Tiers file is missing")
	void shouldThrowExceptionWhenFileNotFound() {
		// Given
		String unknownFile = "missing_tiers.json";

		// When & Then
		assertThatThrownBy(() -> loader.loadTiersFromJson(unknownFile))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Failed to load Tiers resource");
	}

	/**
	 * Verifies that the loader correctly parses a valid JSON and maps fields.
	 * Note: Requires 'Tiers.json' in src/test/resources/data/
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should return a populated list when Tiers.json exists")
	void shouldReturnPopulatedListWhenFileExists() {
		// Given
		String testFile = "Tiers.json";

		// When
		List<Tiers> result = loader.loadTiersFromJson(testFile);

		// Then
		assertThat(result).isNotEmpty();

		// Testing the first element (Marie Blachère based on your file)
		Tiers firstTiers = result.get(0);
		assertThat(firstTiers.getId()).isEqualTo(1L);
		assertThat(firstTiers.getName()).isEqualTo("Marie Blachère Vierzon");
		assertThat(firstTiers.getThirdPartyType()).isEqualTo("PROFESSIONAL");
	}

	/**
	 * Verifies that 'Est_Professionnel' : 0 is correctly mapped to PERSONAL.
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should map Est_Professionnel 0 to PERSONAL")
	void shouldMapPersonalTiersCorrectly() {
		// Given
		String testFile = "Tiers.json";

		// When
		List<Tiers> result = loader.loadTiersFromJson(testFile);

		// Then
		// Mémère is ID 28 in your file and is not pro (0)
		Tiers personalTiers = result.stream()
				                      .filter(t -> t.getId() == 28L)
				                      .findFirst()
				                      .orElseThrow();

		assertThat(personalTiers.getThirdPartyType()).isEqualTo("PERSONAL");
	}
}