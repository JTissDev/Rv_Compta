package com.jtissdev.engine.loader;

import com.jtissdev.features.compta.dto.JournalDTO;
import com.jtissdev.features.compta.dto.OperationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link JournalLoader} service.
 * <p>
 * Verifies that the JSON parsing logic correctly rehydrates the Journal
 * hierarchy and handles various data scenarios (nulls, empty lists).
 * </p>
 *
 * @author J.Tiss
 * @since 0.4.0
 * @version 1.0.0
 */
class JournalLoaderTest {

	private JournalLoader journalLoader;

	@BeforeEach
	void setUp() {
		journalLoader = new JournalLoader();
	}

	/**
	 * Tests the loading of a complete Journal from a valid JSON string.
	 * Verified: Full hierarchy (Journal -> Operation -> Movement) is correctly mapped.
	 * @since 0.4.0
	 */
	@Test
	@DisplayName("Should load a complete journal with operations and movements")
	void shouldLoadCompleteJournal() {
		String json = "{"
				              + "  \"id\": 1,"
				              + "  \"nom\": \"General Journal 2024\","
				              + "  \"dateDebut\": \"2024-01-01\","
				              + "  \"typeJournalCode\": \"GEN\","
				              + "  \"operations\": ["
				              + "    {"
				              + "      \"id\": 10,"
				              + "      \"libelle\": \"Test Operation\","
				              + "      \"movements\": ["
				              + "        {"
				              + "          \"id\": 100,"
				              + "          \"montantDebit\": \"150.00\","
				              + "          \"montantCredit\": \"0.00\","
				              + "          \"paiementCode\": \"CB\""
				              + "        }"
				              + "      ]"
				              + "    }"
				              + "  ]"
				              + "}";

		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		JournalDTO journal = journalLoader.loadJournal(is);

		assertNotNull(journal);
		assertEquals(1, journal.getId());
		assertEquals("General Journal 2024", journal.getName());
		assertEquals(1, journal.getOperations().size());

		OperationDTO op = journal.getOperations().get(0);
		assertEquals("Test Operation", op.getLibelle());
		assertEquals(1, op.getMovements().size());
		assertEquals(new BigDecimal("150.00"), op.getMovements().get(0).getDebitAmount());
		assertEquals("CB", op.getMovements().get(0).getPaiementCode());
	}

	/**
	 * Tests behavior with an empty JSON object.
	 * Verified: Minimal Journal is created with default empty lists.
	 * @since 0.4.0
	 */
	@Test
	@DisplayName("Should handle minimal JSON object")
	void shouldHandleMinimalJson() {
		String json = "{}";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		JournalDTO journal = journalLoader.loadJournal(is);

		assertNotNull(journal);
		assertNull(journal.getName());
		assertTrue(journal.getOperations().isEmpty());
	}

	/**
	 * Tests error handling for invalid input streams.
	 * Verified: Returns null instead of throwing unhandled exceptions.
	 * @since 0.4.0
	 */
	@Test
	@DisplayName("Should return null on null input stream")
	void shouldReturnNullOnNullStream() {
		JournalDTO journal = journalLoader.loadJournal(null);
		assertNull(journal);
	}

	/**
	 * Tests date parsing logic.
	 * Verified: ISO strings are correctly converted to LocalDate.
	 * @since 0.4.0
	 */
	@Test
	@DisplayName("Should correctly parse dates")
	void shouldParseDates() {
		String json = "{\"dateDebut\": \"2024-12-31\"}";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		JournalDTO journal = journalLoader.loadJournal(is);

		assertNotNull(journal.getStartDate());
		assertEquals(2024, journal.getStartDate().getYear());
		assertEquals(12, journal.getStartDate().getMonthValue());
		assertEquals(31, journal.getStartDate().getDayOfMonth());
	}
}