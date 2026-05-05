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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link JournalDTO} class.
 * <p>
 * Ensures that all constructors, fluent setters, and operation list management
 * are functioning correctly according to the 0.3.0 specifications.
 * </p>
 *
 * @author J.Tiss
 * @version 1.1.0
 * @since 0.3.0
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("Journal Test Suite")
@TestGroup("Compta DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JournalDTOTest {

	private static final Logger logger = LoggerFactory.getLogger(JournalDTOTest.class);

	// Global test data holders
	public static JsonObject TEST_DATA_JSON;
	public static JsonObject INITIAL_JOURNAL_JSON;
	public static JsonArray OPERATIONS_TO_ADD;

	private static JournalDTO journal;


	@BeforeAll
	static void setUp() {
		// Loading the root JsonObject from resources
		TEST_DATA_JSON = TestDataLoader.loadFromResources("data/journal-test.json");

		// Initializing the main journal DTO with the 'initialJournal' part
		INITIAL_JOURNAL_JSON = TEST_DATA_JSON.getJsonObject("initialJournal");
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
		assertNotNull(OPERATIONS_TO_ADD, "The array of operations to add should be present");

	}

	@Test
	@Order(1)
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		// 1. Action : instanciation
		journal = new JournalDTO();

		// 2. Assertions : vérification de l'état initial
		assertAll("Verify default state of empty JournalDTO",
				() -> assertNotNull(journal, "Journal should not be null"),
				() -> assertNotNull(journal.getOperations(), "Operations list should be initialized (not null)"),
				() -> assertTrue(journal.getOperations().isEmpty(), "Operations list should be empty initially")
		);
	}

	@Test
	@Order(2)
	@DisplayName("Fluent Setters")
	void testFluentSetters() {
		// 1. Action : On part d'un objet vide
		journal = new JournalDTO();

		// Préparation : Conversion des chaînes JSON en LocalDate
		LocalDate expectedStartDate = LocalDate.parse(INITIAL_JOURNAL_JSON.getString("startDate"));
		LocalDate expectedEndDate = LocalDate.parse(INITIAL_JOURNAL_JSON.getString("endDate"));
		int initialSize = journal.getOperations().size();
		// 2. Utilisation de l'API Fluent avec enchaînement
		JournalDTO result = journal.setId(INITIAL_JOURNAL_JSON.getInt("id"))
				                    .setName(INITIAL_JOURNAL_JSON.getString("name"))
				                    .setStartDate(expectedStartDate)
				                    .setEndDate(expectedEndDate)
				                    .setJournalTypeCode(INITIAL_JOURNAL_JSON.getString("journalTypeCode"))
				                    .setOperations(INITIAL_JOURNAL_JSON.getJsonArray("operations"));

		int expectedSize = initialSize + INITIAL_JOURNAL_JSON.getJsonArray("operations").size();
		// 3. Assertions : On vérifie que tout a bien fonctionné
		assertAll("Verify fluent setters logic",
				// Vérifie que la méthode retourne bien la même instance (le principe du "fluent")
				() -> assertSame(journal, result, "The setter must return the same instance"),

				// Vérifie que les champs ont bien été modifiés
				() -> assertEquals(INITIAL_JOURNAL_JSON.getInt("id"), journal.getId(), "The ID should be set correctly"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getString("name"), journal.getName(), "The name should be set correctly"),
				() -> assertEquals(expectedStartDate, journal.getStartDate(), "The start date should be set correctly"),
				() -> assertEquals(expectedEndDate, journal.getEndDate(), "The end date should be set correctly"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getString("journalTypeCode"), journal.getJournalTypeCode(), "The journal type code should be set correctly"),
				() -> assertEquals(expectedSize, journal.getOperations().size(), "Total size should be " + expectedSize),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getJsonArray("operations").getJsonObject(0).getString("libelle"), journal.getOperations().get(initialSize).getLibelle(), "First added operation mismatch"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getJsonArray("operations").getJsonObject(1).getString("libelle"), journal.getOperations().get(initialSize + 1).getLibelle(), "Second added operation mismatch")
		);
	}


	@Test
	@Order(3)
	@DisplayName("Test json constructor")
	void testJsonConstructor() {
		journal = new JournalDTO(INITIAL_JOURNAL_JSON);

		int journalSize = journal.getOperations().size();
		// Préparation : Conversion des chaînes JSON en LocalDate
		LocalDate expectedStartDate = LocalDate.parse(INITIAL_JOURNAL_JSON.getString("startDate"));
		LocalDate expectedEndDate = LocalDate.parse(INITIAL_JOURNAL_JSON.getString("endDate"));

		assertAll("check correct journal mapping form json Object ",
				() -> assertEquals(INITIAL_JOURNAL_JSON.getInt("id"), journal.getId(), "The ID should be set correctly"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getString("name"), journal.getName(), "The name should be set correctly"),
				() -> assertEquals(expectedStartDate, journal.getStartDate(), "The start date should be set correctly"),
				() -> assertEquals(expectedEndDate, journal.getEndDate(), "The end date should be set correctly"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getString("journalTypeCode"), journal.getJournalTypeCode(), "The journal type code should be set correctly"),
				() -> assertEquals(journalSize, journal.getOperations().size(), "Total size should be " + journalSize),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getJsonArray("operations").getJsonObject(0).getString("libelle"), journal.getOperations().get(0).getLibelle(), "First added operation mismatch"),
				() -> assertEquals(INITIAL_JOURNAL_JSON.getJsonArray("operations").getJsonObject(1).getString("libelle"), journal.getOperations().get(1).getLibelle(),"Second added operation mismatch")
				);
	}

	@Test
	@Order(4)
	@DisplayName("Add Single Operation")
	void testAddSingleOperation() {
		// 1. Action : Initialisation avec les données de base (le journal contient déjà 2 opérations)
		journal = new JournalDTO(INITIAL_JOURNAL_JSON); //[cite: 6, 7]

		int initialSize = journal.getOperations().size();
		int expectedSize = initialSize + 1;

		// On crée une opération à partir du premier objet de notre tableau "operationsToAdd"
		JsonObject opJson = OPERATIONS_TO_ADD.getJsonObject(0); //
		OperationDTO operation = new OperationDTO(opJson);

		// 2. Action : Ajout de l'opération
		JournalDTO result = journal.addOperation(operation); //[cite: 8]

		// 3. Assertions
		assertAll("Verify single operation addition",
				// Vérifie que la méthode est fluide (retourne l'instance actuelle)
				() -> assertSame(journal, result, "The addOperation method should return the same instance"),

				// Vérifie l'incrémentation de la taille
				() -> assertEquals(expectedSize, journal.getOperations().size(), "Journal should contain " + expectedSize + " operations"),

				// Vérifie que les données du dernier élément correspondent à l'opération ajoutée
				() -> assertEquals(operation.getLibelle(), journal.getOperations().get(initialSize).getLibelle(), "Operation labels should match")
		);
	}
}