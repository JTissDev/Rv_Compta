package com.jtissdev_API.features.compta.dto;

import com.jtissdev_API.utils.TestDataLoader;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @version 1.1.0
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("Journal Test Suite")
@TestGroup("Compta DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JournalDTOTest {

	private static final Logger logger = LoggerFactory.getLogger(JournalDTOTest.class);

	// Global test data holders
	public static JsonObject TEST_DATA_JSON;
	public static JsonArray OPERATIONS_TO_ADD;

	private static JournalDTO journal;


	@BeforeAll
	static void setUp() {
		// Loading the root JsonObject from resources
		TEST_DATA_JSON = TestDataLoader.loadFromResources("data/journal-test.json");

		// Initializing the main journal DTO with the 'initialJournal' part
		JsonObject initialJournalJson = TEST_DATA_JSON.getJsonObject("initialJournal");
		// Extracting operations intended for 'add' tests
		OPERATIONS_TO_ADD = TEST_DATA_JSON.getJsonArray("operationsToAdd");

		validateStructure();
		logger.info("Test environment initialized for JournalDTO");
	}

	/**
	 * Validates that the test data is correctly loaded and not null.
	 */
	private static void validateStructure() {
		assertNotNull(TEST_DATA_JSON, "The root test JSON should not be null");
		assertNotNull(journal, "The initial JournalDTO should be properly initialized");
		assertNotNull(OPERATIONS_TO_ADD, "The array of operations to add should be present");

		// Check if the journal actually contains the operations from the JSON
		assertFalse(journal.getOperations().isEmpty(), "The initial journal should contain operations");
	}
}