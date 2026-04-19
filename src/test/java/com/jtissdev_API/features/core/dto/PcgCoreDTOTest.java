package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the PcgCoreDTO container to ensure data integrity
 * and proper collection management.
 * * @author JtissDev
 * @version 1.0
 */
class PcgCoreDTOTest {

	/**
	 * Verifies that the constructor correctly initializes an empty list
	 * to avoid NullPointerException.
	 */
	@Test
	@DisplayName("Should initialize with an empty list of classes")
	void shouldInitializeWithEmptyList() {
		// Given & When
		PcgCoreDTO core = new PcgCoreDTO();

		// Then
		assertThat(core.getAccountingClasses())
				.isNotNull()
				.isEmpty();
	}

	/**
	 * Tests the helper method for adding a single accounting class.
	 */
	@Test
	@DisplayName("Should add a single accounting class to the list")
	void shouldAddAccountingClass() {
		// Given
		PcgCoreDTO core = new PcgCoreDTO();
		AccountingType mockClass = new AccountingType(); // Assuming default constructor exists

		// When
		core.addAccountingClass(mockClass);

		// Then
		assertThat(core.getAccountingClasses())
				.hasSize(1)
				.contains(mockClass);
	}
}