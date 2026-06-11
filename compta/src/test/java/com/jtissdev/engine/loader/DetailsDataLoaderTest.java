package com.jtissdev.engine.loader;

import com.jtissdev.features.pcp.dto.AnalyticDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link DetailsDataLoader}.
 * Verifies parsing logic from the filesystem and mapping to AnalyticDetail DTO.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 1.0.0
 */
class DetailsDataLoaderTest {

	private DetailsDataLoader loader;

	/**
	 * Set up the testing environment.
	 * @since 1.0.0
	 */
	@BeforeEach
	void setUp() {
		loader = new DetailsDataLoader();
	}

	/**
	 * Verifies that a RuntimeException is thrown when the Details JSON file is not found.
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should throw RuntimeException when Details file is missing")
	void shouldThrowExceptionWhenFileNotFound() {
		// Given
		String unknownFile = "missing_details.json";

		// When & Then
		assertThatThrownBy(() -> loader.loadDetailsFromJson(unknownFile))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Failed to load Details resource");
	}

	/**
	 * Verifies that the loader correctly parses valid JSON and maps fields.
	 * Note: Requires 'Details.json' in the root /data folder.
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should return a populated list when Details.json exists")
	void shouldReturnPopulatedListWhenFileExists() {
		// Given
		String testFile = "Details.json";

		// When
		List<AnalyticDetail> result = loader.loadDetailsFromJson(testFile);

		// Then
		assertThat(result).isNotEmpty();

		// Testing the first element (.229 based on your file)
		AnalyticDetail firstDetail = result.get(0);
		assertThat(firstDetail.getCode()).isEqualTo(".229");
		assertThat(firstDetail.getType()).isEqualTo("Banque");
		assertThat(firstDetail.getName()).isEqualTo("Compte courant .229");
		assertThat(firstDetail.getDescription()).isEqualTo("Compte joint leslie à supprimer");
	}

	/**
	 * Verifies that optional or empty descriptions are handled.
	 * @since 1.0.0
	 */
	@Test
	@DisplayName("Should handle empty description fields correctly")
	void shouldHandleEmptyDescription() {
		// Given
		String testFile = "Details.json";

		// When
		List<AnalyticDetail> result = loader.loadDetailsFromJson(testFile);

		// Then
		// .Chas has an empty description "" in your JSON
		AnalyticDetail emptyDescDetail = result.stream()
				                                    .filter(d -> d.getCode().equals(".Chas"))
				                                    .findFirst()
				                                    .orElseThrow();

		assertThat(emptyDescDetail.getDescription()).isEmpty();
	}
}