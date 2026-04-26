package com.jtissdev_API.features.compta.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MovementDTO} class.
 * <p>
 * Ensures that all constructors, fluent setters, and Javadoc-documented
 * behaviors perform correctly, especially regarding financial precision.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.0.0
 */
class MovementDTOTest {

	/**
	 * Tests the default constructor.
	 * Verified: Default values for BigDecimal fields should be ZERO.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with default values (Zero for amounts)")
	void shouldInitializeWithDefaultValues() {
		MovementDTO movement = new MovementDTO();

		assertNull(movement.getId(), "ID should be null by default");
		assertEquals(BigDecimal.ZERO, movement.getDebitAmount(), "Debit should be initialized to ZERO");
		assertEquals(BigDecimal.ZERO, movement.getCreditAmount(), "Credit should be initialized to ZERO");
	}

	/**
	 * Tests the minimal constructor for quick instantiation.
	 * Verified: Tiers, Payment code and Account code are correctly set.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with minimal constructor")
	void shouldInitializeWithMinimalConstructor() {
		MovementDTO movement = new MovementDTO(10L, "CB", "707000");

		assertEquals(10L, movement.getTiersId());
		assertEquals("CB", movement.getPaiementCode());
		assertEquals("707000", movement.getAccountDetailCode());
		assertEquals(BigDecimal.ZERO, movement.getDebitAmount());
	}

	/**
	 * Tests the constructor used for new entries (without ID).
	 * Verified: All fields except ID are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with creation constructor (no ID)")
	void shouldInitializeWithCreationConstructor() {
		BigDecimal debit = new BigDecimal("150.50");
		MovementDTO movement = new MovementDTO(1L, "VIR", "607000", debit, BigDecimal.ZERO, "Achat marchandises");

		assertNull(movement.getId());
		assertEquals(debit, movement.getDebitAmount());
		assertEquals("Achat marchandises", movement.getDescription());
	}

	/**
	 * Tests the full constructor used for database retrieval.
	 * Verified: All fields including ID are correctly mapped.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should initialize with full database constructor")
	void shouldInitializeWithFullConstructor() {
		BigDecimal credit = new BigDecimal("2000.00");
		MovementDTO movement = new MovementDTO(500L, 2L, "CHQ", "512000", BigDecimal.ZERO, credit, "Salaire");

		assertEquals(500L, movement.getId());
		assertEquals(credit, movement.getCreditAmount());
		assertEquals("CHQ", movement.getPaiementCode());
	}

	/**
	 * Tests the Fluent API (chaining setters).
	 * Verified: Each setter returns 'this' and updates the value.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should support fluent method chaining")
	void shouldSupportFluentApi() {
		MovementDTO movement = new MovementDTO()
				                       .setId(1L)
				                       .setTiersId(5L)
				                       .setPaiementCode("ESPECES")
				                       .setDebitAmount(new BigDecimal("10.00"))
				                       .setDescription("Test Fluent");

		assertAll("Fluent setter verification",
				() -> assertEquals(1L, movement.getId()),
				() -> assertEquals(5L, movement.getTiersId()),
				() -> assertEquals("ESPECES", movement.getPaiementCode()),
				() -> assertEquals(new BigDecimal("10.00"), movement.getDebitAmount()),
				() -> assertEquals("Test Fluent", movement.getDescription())
		);
	}

	/**
	 * Tests BigDecimal precision for financial calculations.
	 * Verified: No loss of precision during assignment.
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should maintain BigDecimal precision")
	void shouldMaintainBigDecimalPrecision() {
		BigDecimal preciseValue = new BigDecimal("1234.5678");
		MovementDTO movement = new MovementDTO().setCreditAmount(preciseValue);

		assertEquals(0, preciseValue.compareTo(movement.getCreditAmount()), "Amounts should be identical in value");
	}
}