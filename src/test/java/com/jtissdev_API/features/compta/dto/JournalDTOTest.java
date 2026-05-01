package com.jtissdev_API.features.compta.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link JournalDTO} class.
 * <p>
 * Ensures that all constructors, fluent setters, and operation list management
 * are functioning correctly according to the 0.3.0 specifications.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.0.0
 */
class JournalDTOTest {

	/**
	 * Tests the default constructor.
	 * Verified: The operations list must be initialized and empty.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with an empty operations list")
	void shouldInitializeWithEmptyList() {
		JournalDTO journal = new JournalDTO();

		assertNotNull(journal.getOperations(), "Operations list should never be null");
		assertTrue(journal.getOperations().isEmpty(), "Operations list should be empty on init");
	}

	/**
	 * Tests the null-safety of the setOperations method.
	 * Verified: Passing null to setOperations should result in an empty list.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should handle null operations list safely")
	void shouldHandleNullOperationsSafely() {
		JournalDTO journal = new JournalDTO();
		journal.setOperations(null);

		assertNotNull(journal.getOperations(), "SetOperations(null) should initialize an empty list");
	}

}