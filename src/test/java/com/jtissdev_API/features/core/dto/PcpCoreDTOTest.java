package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link PcpCoreDTO}.
 * Ensures the container correctly manages the collection of PCP elements.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 1.0.0
 */
class PcpCoreDTOTest {

	@Test
	@DisplayName("Constructor - should initialize with an empty list of third parties")
	void constructor_shouldInitializeEmptyList() {
		// When
		PcpCoreDTO pcpCore = new PcpCoreDTO();

		// Then
		assertThat(pcpCore.getThirdParties())
				.isNotNull()
				.isEmpty();
	}

	@Test
	@DisplayName("Setters and Getters - should correctly store and retrieve third parties")
	void settersAndGetters_shouldWorkCorrectly() {
		// Given
		PcpCoreDTO pcpCore = new PcpCoreDTO();
		List<Tiers> mockTiers = new ArrayList<>();
		mockTiers.add(new Tiers(1L, "Test Vendor", "VENDOR"));
		mockTiers.add(new Tiers(2L, "Test Friend", "PERSONAL"));

		// When
		pcpCore.setThirdParties(mockTiers);

		// Then
		assertThat(pcpCore.getThirdParties())
				.hasSize(2)
				.containsExactlyElementsOf(mockTiers);

		assertThat(pcpCore.getThirdParties().get(0).getName())
				.isEqualTo("Test Vendor");
	}

	@Test
	@DisplayName("List mutation - should allow adding elements to the retrieved list")
	void list_shouldBeMutable() {
		// Given
		PcpCoreDTO pcpCore = new PcpCoreDTO();
		Tiers newTiers = new Tiers(99L, "New Tiers", "MISC");

		// When
		pcpCore.getThirdParties().add(newTiers);

		// Then
		assertThat(pcpCore.getThirdParties())
				.contains(newTiers)
				.hasSize(1);
	}

	@Test
	@DisplayName("Details - Should correctly store and retrieve Level 4 details")
	void details_ShouldWorkCorrectly() {
		// Given
		PcpCoreDTO pcpCore = new PcpCoreDTO();
		AnalyticDetail car = new AnalyticDetail(".P106", "Vehicule", "Peugeot 106");

		// When
		pcpCore.getDetails().add(car);

		// Then
		assertThat(pcpCore.getDetails()).hasSize(1);
		assertThat(pcpCore.getDetails().get(0).getCode()).isEqualTo(".P106");
	}
}