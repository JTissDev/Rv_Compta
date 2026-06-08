package com.jtissdev.features.compta.dto;

import com.jtissdev.utils.TestDataLoader;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
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
 * @version 1.5.0
 * @since 0.3.0
 */
@ExtendWith(TestResultLogger.class)
@TestGroup("Compta DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Journal Test Suite")
public class JournalDTOTest {

	private static final Logger logger = LoggerFactory.getLogger(JournalDTOTest.class);

	// Global test data holders
	private static final Integer JOURNAL_ID = 1;
	private static final String JOURNAL_NAME = "Journal Général de Test";
	private static final String JOURNAL_TYPE_CODE = "BQE";

	private static JsonObject testDataset;
	private JournalDTO journal;


	@BeforeAll
	static void initAll() {
		logger.info("Initializing dataset from data/journal-test.json using TestDataLoader");
		testDataset = TestDataLoader.loadFromResources("data/journal-test.json");
		assertNotNull(testDataset, "The global test dataset must be loaded successfully.");
	}

	@BeforeEach
	void setUp() {
		journal = new JournalDTO();
	}

	@Test
	@Order(1)
	@DisplayName("Test Default Constructor and Default Values")
	void testDefaultConstructor() {
		assertAll("Default constructor must initialize empty structures and defensive defaults",
				() -> assertNull(journal.getId(), "ID should be null by default"),
				() -> assertNull(journal.getName(), "Name should be null by default"),
				() -> assertNull(journal.getStartDate(), "startDate should be null by default"),
				() -> assertNull(journal.getEndDate(), "endDate should be null by default"),
				// Si journalTypeCode s'initialise à null ou "", adapte la ligne ci-dessous :
				() -> assertNull(journal.getJournalTypeCode(), "journalTypeCode should be null by default"),
				() -> assertNotNull(journal.getOperations(), "Operations list must never be null"),
				() -> assertTrue(journal.getOperations().isEmpty(), "Operations list must be initialized empty")
		);
	}

	@Test
	@Order(2)
	@DisplayName("Test Fluent Getters and Setters")
	void testFluentGettersSetters() {
		LocalDate startDate = LocalDate.of(2026, 5, 1);
		LocalDate endDate = LocalDate.of(2026, 5, 31);
		List<OperationDTO> ops = new ArrayList<>();

		journal.setId(JOURNAL_ID)
				.setName(JOURNAL_NAME)
				.setStartDate(startDate)
				.setEndDate(endDate)
				.setJournalTypeCode(JOURNAL_TYPE_CODE)
				.setOperations(ops);

		assertAll("Fluent API state validation for JournalDTO",
				() -> assertEquals(JOURNAL_ID, journal.getId()),
				() -> assertEquals(JOURNAL_NAME, journal.getName()),
				() -> assertEquals(startDate, journal.getStartDate()),
				() -> assertEquals(endDate, journal.getEndDate()),
				() -> assertEquals(JOURNAL_TYPE_CODE, journal.getJournalTypeCode()),
				() -> assertEquals(ops, journal.getOperations(), "Operations list content must match perfectly")
		);

	}

	@Test
	@Order(3)
	@DisplayName("Test JSON Hydration - Valid Initial Journal mapping")
	void testJsonHydrationValid() {
		JsonObject initialJournalJson = testDataset.getJsonObject("initialJournal");

		journal = new JournalDTO(initialJournalJson);

		assertAll("Validation of rehydrated fields from valid Journal JSON structure",
				() -> assertEquals(1, journal.getId()),
				() -> assertEquals("Journal de Banque Mai 2026", journal.getName()),
				() -> assertEquals(LocalDate.parse("2026-05-01"), journal.getStartDate()),
				() -> assertEquals(LocalDate.parse("2026-05-31"), journal.getEndDate()),
				() -> assertEquals("BQE", journal.getJournalTypeCode()),
				() -> assertEquals(4, journal.getOperations().size(), "Should have successfully parsed 4 operations from dataset")
		);
	}

	@Test
	@Order(4)
	@DisplayName("Test Fluent Operation Addition")
	void testAddSingleOperation() {
		JsonObject initialJournalJson = testDataset.getJsonObject("initialJournal");
		JsonArray operationsToAdd = testDataset.getJsonArray("operationsToAdd");

		// Initialisation du journal (contient déjà 4 opérations)
		journal = new JournalDTO(initialJournalJson);
		int initialSize = journal.getOperations().size();

		// Extraction et instanciation de la première opération à ajouter (ID 201)
		JsonObject opJson = operationsToAdd.getJsonObject(0);
		OperationDTO newOperation = new OperationDTO(opJson);

		// Ajout fluide
		JournalDTO resultJournal = journal.addOperation(newOperation);

		assertAll("Verification of operational behaviors and side effects on structural list",
				() -> assertSame(journal, resultJournal, "The addOperation method must support chaining by returning this instance"),
				() -> assertEquals(initialSize + 1, journal.getOperations().size(), "Journal collection size should increment by 1"),
				() -> assertEquals(201, journal.getOperations().get(journal.getOperations().size() - 1).getId(), "The last operation added must match the target ID")
		);
	}

	@Test
	@Order(5)
	@DisplayName("Test Serialization toJson & State Conservation")
	void testToJsonSerialization() {
		JsonObject initialJournalJson = testDataset.getJsonObject("initialJournal");
		journal = new JournalDTO(initialJournalJson);

		// Sérialisation
		JsonObject serializedJson = journal.toJson();

		assertAll("JSON Output formatting constraints mapping for JournalDTO",
				() -> assertTrue(serializedJson.containsKey("id"), "Technical numeric key 'id' must be serialized"),
				() -> assertEquals("Journal de Banque Mai 2026", serializedJson.getString("name")),
				() -> assertTrue(serializedJson.containsKey("operations"), "Embedded structural array 'operations' must exist"),
				() -> assertEquals(4, serializedJson.getJsonArray("operations").size(), "Exported JSON operations array length mismatch")
		);
	}
}