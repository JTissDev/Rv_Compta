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
	 * Tests the creation constructor (no ID).
	 * Verified: Metadata for Excel import phase are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with creation constructor")
	void shouldInitializeWithCreationConstructor() {
		LocalDate start = LocalDate.of(2024, 1, 1);
		LocalDate end = LocalDate.of(2024, 12, 31);
		JournalDTO journal = new JournalDTO("Journal de test", start, end, "GEN");

		assertNull(journal.getId());
		assertEquals("Journal de test", journal.getName());
		assertEquals(start, journal.getStartDate());
		assertEquals(end, journal.getEndDate());
		assertEquals("GEN", journal.getJournalTypeCode());
	}

	/**
	 * Tests the persistence constructor (with ID).
	 * Verified: ID and period metadata are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with persistence constructor")
	void shouldInitializeWithPersistenceConstructor() {
		JournalDTO journal = new JournalDTO(500L, "Journal 2023", LocalDate.MIN, LocalDate.MAX, "OLD");

		assertEquals(500L, journal.getId());
		assertEquals("OLD", journal.getJournalTypeCode());
		assertTrue(journal.getOperations().isEmpty());
	}

	/**
	 * Tests the full constructor (with ID and operations).
	 * Verified: Mapping of the operational data.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with full constructor and operations")
	void shouldInitializeWithFullConstructor() {
		List<OperationDTO> ops = new ArrayList<>();
		ops.add(new OperationDTO());

		JournalDTO journal = new JournalDTO(1L, "Journal Complet", null, null, "FULL", ops);

		assertEquals(1L, journal.getId());
		assertEquals(1, journal.getOperations().size(), "Should have 1 operation linked");
	}

	/**
	 * Tests the Fluent API and operation addition.
	 * Verified: Method chaining and individual addition logic.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should support fluent API and adding operations individually")
	void shouldSupportFluentApiAndAddOperation() {
		OperationDTO op1 = new OperationDTO().setId(100L);

		JournalDTO journal = new JournalDTO()
				                     .setId(10L)
				                     .setName("Fluent Journal")
				                     .addOperation(op1)
				                     .addOperation(new OperationDTO().setId(101L));

		assertAll("Fluent verification",
				() -> assertEquals(10L, journal.getId()),
				() -> assertEquals("Fluent Journal", journal.getName()),
				() -> assertEquals(2, journal.getOperations().size()),
				() -> assertEquals(100L, journal.getOperations().get(0).getId())
		);
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