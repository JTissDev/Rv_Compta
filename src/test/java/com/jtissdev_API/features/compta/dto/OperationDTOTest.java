package com.jtissdev_API.features.compta.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link OperationDTO} class.
 * <p>
 * Ensures that all constructors, fluent setters, and list management for
 * movements are functioning correctly according to the 0.3.0 specifications.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.0.0
 */
class OperationDTOTest {

	/**
	 * Tests the default constructor.
	 * Verified: The movements list must be initialized and empty, not null.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with an empty movements list")
	void shouldInitializeWithEmptyList() {
		OperationDTO operation = new OperationDTO();

		assertNotNull(operation.getMovements(), "Movements list should never be null");
		assertTrue(operation.getMovements().isEmpty(), "Movements list should be empty on init");
	}

	/**
	 * Tests the creation constructor (no ID, no movements).
	 * Verified: All descriptive fields are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with creation constructor")
	void shouldInitializeWithCreationConstructor() {
		LocalDate now = LocalDate.now();
		OperationDTO operation = new OperationDTO(now, now, "Courses", "FAC-001", "Courses hebdomadaires", "PROV");

		assertNull(operation.getId());
		assertEquals("Courses", operation.getLibelle());
		assertEquals("FAC-001", operation.getReferenceDocument());
		assertEquals("PROV", operation.getStatutCode());
	}

	/**
	 * Tests the persistence constructor (with ID, no movements).
	 * Verified: ID and metadata are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with persistence constructor")
	void shouldInitializeWithPersistenceConstructor() {
		OperationDTO operation = new OperationDTO(100L, LocalDate.now(), LocalDate.now(), "Loyer", null, null, "VAL");

		assertEquals(100L, operation.getId());
		assertEquals("VAL", operation.getStatutCode());
		assertTrue(operation.getMovements().isEmpty());
	}

	/**
	 * Tests the full constructor (with ID and movements).
	 * Verified: Deep mapping of the movements list.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with full constructor and movements")
	void shouldInitializeWithFullConstructor() {
		List<MovementDTO> movements = new ArrayList<>();
		movements.add(new MovementDTO());
		movements.add(new MovementDTO());

		OperationDTO operation = new OperationDTO(1L, LocalDate.now(), LocalDate.now(), "Op", "Ref", "Desc", "STAT", movements);

		assertEquals(1L, operation.getId());
		assertEquals(2, operation.getMovements().size(), "Should have 2 movements linked");
	}

	/**
	 * Tests the Fluent API and individual movement addition.
	 * Verified: Method chaining and addMovement logic.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should support fluent API and adding movements individually")
	void shouldSupportFluentApiAndAddMovement() {
		MovementDTO m1 = new MovementDTO().setId(10);

		OperationDTO operation = new OperationDTO()
				                         .setId(50L)
				                         .setLibelle("Fluent Test")
				                         .addMovement(m1)
				                         .addMovement(new MovementDTO().setId(11));

		assertAll("Fluent and Add verification",
				() -> assertEquals(50L, operation.getId()),
				() -> assertEquals("Fluent Test", operation.getLibelle()),
				() -> assertEquals(2, operation.getMovements().size()),
				() -> assertEquals(10, operation.getMovements().get(0).getId())
		);
	}

	/**
	 * Tests the null-safety of the setMovements method.
	 * Verified: Passing null to setMovements should result in an empty list instead of null.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should handle null movements list safely")
	void shouldHandleNullMovementsSafely() {
		OperationDTO operation = new OperationDTO();
		operation.setMovements(null);

		assertNotNull(operation.getMovements(), "SetMovements(null) should initialize an empty list");
	}
}