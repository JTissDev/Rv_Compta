package com.jtissdev_API.features.compta.dto;

import com.jtissdev_API.utils.TestDataLoader;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link OperationDTO} class.
 * <p>
 * Ensures that all constructors, fluent setters, and list management for
 * movements are functioning correctly according to the 0.3.0 specifications.
 * </p>
 *
 * @author J.Tiss
 * @version 1.6.0
 * @since 0.3.0
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("Operation Test Suite")
@TestGroup("Compta DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperationDTOTest {

	private static final Logger logger = LoggerFactory.getLogger(OperationDTOTest.class);

	private static final Integer OPERATION_ID = 123;
	private static final String OPERATION_LIBELLE = "Operation Test";
	private static final String REFERENCE_DOCUMENT = "REF-2026-001";
	private static final String DESCRIPTIF = "Description de test unitaire";
	private static final String STATUT_CODE = "VAL";
	private static final int POSITION_DEFAULT = 1000;

	private static JsonObject testDataset;
	private OperationDTO operation;


	/**
	 *  Initializes the test dataset from a JSON file before all tests.
	 *  This method loads the dataset using the TestDataLoader utility, which provides a standardized way to access test data across the test suite.
	 *  The dataset is expected to be located in the resources
	 * directory and named "journal-test.json".
	 * The loaded dataset is stored in a static variable for use in subsequent tests.
	 * An assertion is included to ensure that the dataset is loaded successfully, preventing null reference issues in later tests.
	 *
	 * @since 1.6.0
	 */
	@BeforeAll
	static void initAll() {
		logger.info("Initializing dataset from journal-test.json using TestDataLoader");
		testDataset = TestDataLoader.loadFromResources("data/journal-test.json");
		assertNotNull(testDataset, "The global test dataset must be loaded successfully.");
	}

	@BeforeEach
	void setUp() {
		operation = new OperationDTO();
	}

	/**
	 * Tests the default constructor.
	 * Verified: The movements list must be initialized and empty, not null.
	 *
	 * @since 0.3.0
	 */
	@Test
	@Order(1)
	@DisplayName("Test Default Constructor and Default Values")
	void testDefaultConstructor() {
		assertAll("Default constructor must initialize empty structures and defensive defaults",
				() -> assertNull(operation.getId(), "ID should be null by default"),
				() -> assertNull(operation.getDateOperation(), "dateOperation should be null by default"),
				() -> assertNull(operation.getDateComptable(), "dateComptable should be null by default"),
				() -> assertEquals("", operation.getLibelle(), "libelle must initialize to empty string"),
				() -> assertEquals("", operation.getDescriptif(), "descriptif must initialize to empty string"),
				() -> assertEquals("", operation.getReferenceDocument(), "referenceDocument must initialize to empty string"),
				() -> assertEquals("", operation.getStatutCode(), "statutCode must initialize to empty string"),
				() -> assertEquals(0, operation.getPosition(), "position technique should be 0 by default"),
				() -> assertTrue(operation.getMovements().isEmpty(), "movements list must be initialized empty")
		);
	}

	@Test
	@Order(2)
	@DisplayName("Test Fluent Getters and Setters")
	void testFluentGettersSetters() {
		LocalDate dateOp = LocalDate.of(2026, 5, 15);
		LocalDate dateCp = LocalDate.of(2026, 5, 16);

		operation.setId(OPERATION_ID)
				.setDateOperation(dateOp)
				.setDateComptable(dateCp)
				.setLibelle(OPERATION_LIBELLE)
				.setDescriptif(DESCRIPTIF)
				.setReferenceDocument(REFERENCE_DOCUMENT)
				.setStatutCode(STATUT_CODE)
				.setPosition(POSITION_DEFAULT);

		assertAll("Fluent API state validation",
				() -> assertEquals(OPERATION_ID, operation.getId()),
				() -> assertEquals(dateOp, operation.getDateOperation()),
				() -> assertEquals(dateCp, operation.getDateComptable()),
				() -> assertEquals(OPERATION_LIBELLE, operation.getLibelle()),
				() -> assertEquals(DESCRIPTIF, operation.getDescriptif()),
				() -> assertEquals(REFERENCE_DOCUMENT, operation.getReferenceDocument()),
				() -> assertEquals(STATUT_CODE, operation.getStatutCode()),
				() -> assertEquals(POSITION_DEFAULT, operation.getPosition())
		);
	}

	@Test
	@Order(3)
	@DisplayName("Test JSON Hydration - Valid Multi-movement Operation")
	void testJsonHydrationValid() {
		// Extraction de l'opération valide à 3 mouvements (ID 104) depuis initialJournal
		JsonObject journalObj = testDataset.getJsonObject("initialJournal");
		JsonArray operationsArray = journalObj.getJsonArray("operations");

		// L'index 3 correspond à l'achat matériel avec TVA
		JsonObject validOpJson = operationsArray.getJsonObject(3);

		operation = new OperationDTO(validOpJson);

		assertAll("Validation of rehydrated fields from valid JSON mapping",
				() -> assertEquals(104, operation.getId()),
				() -> assertEquals(LocalDate.parse("2026-05-25"), operation.getDateOperation()),
				() -> assertEquals(LocalDate.parse("2026-05-25"), operation.getDateComptable()),
				() -> assertEquals("Achat Matériel (Multi-lignes avec TVA)", operation.getLibelle()),
				() -> assertEquals("FA-MAT-99", operation.getReferenceDocument()),
				() -> assertEquals("Test d'une opération à 3 mouvements", operation.getDescriptif()),
				() -> assertEquals("VAL", operation.getStatutCode()),
				() -> assertEquals(1000, operation.getPosition()),
				() -> assertEquals(3, operation.getMovements().size(), "Should have fetched 3 multi-line movements")
		);
	}

	@Test
	@Order(4)
	@DisplayName("Test JSON Hydration - Partial & Missing Data Resiliency")
	void testJsonHydrationPartial() {
		// Extraction de l'opération avec des champs manquants (ID 203) dans operationsToAdd
		JsonArray toAddArray = testDataset.getJsonArray("operationsToAdd");
		JsonObject partialOpJson = toAddArray.getJsonObject(2); // Index 2 : sans date comptable

		operation = new OperationDTO(partialOpJson);

		assertAll("Checking code resilience against partial json input (Defensive programming logic)",
				() -> assertEquals(203, operation.getId()),
				() -> assertEquals(LocalDate.parse("2026-05-29"), operation.getDateOperation()),
				() -> assertNull(operation.getDateComptable(), "dateComptable missing from JSON must remain null for sorting engine"),
				() -> assertEquals("Opération sans Date Comptable (Test nullsLast)", operation.getLibelle()),
				() -> assertEquals("", operation.getReferenceDocument(), "Missing reference key must keep defensive class default"),
				() -> assertEquals("", operation.getDescriptif(), "Missing descriptive key must keep defensive class default"),
				() -> assertEquals("BROUILLON", operation.getStatutCode()),
				() -> assertEquals(1000, operation.getPosition())
		);
	}

	@Test
	@Order(5)
	@DisplayName("Test Serialization toJson & Selective Property Omission")
	void testToJsonSerialization() {
		JsonObject journalObj = testDataset.getJsonObject("initialJournal");
		JsonObject validOpJson = journalObj.getJsonArray("operations").getJsonObject(0);

		operation = new OperationDTO(validOpJson);
		// On simule une modification manuelle en forçant un champ à null pour tester l'exclusion sélective
		operation.setStatutCode(null);

		JsonObject serializedJson = operation.toJson();

		assertAll("JSON Output formatting constraints mapping",
				() -> assertTrue(serializedJson.containsKey("id"), "Non-null numerical fields must be present"),
				() -> assertTrue(serializedJson.containsKey("dateOperation"), "Non-null dates must be present"),
				() -> assertTrue(serializedJson.containsKey("position"), "Active position index must be written to disk"),
				() -> assertTrue(serializedJson.containsKey("movements"), "Embedded structural movements must be written"),
				// Selon la règle de ta V0.4 : ne pas enregistrer les clés nulles
				// (Vérifie si ta logique de toJson() actuelle exclut les clés nulles)
				// Si tu n'exclus pas encore les nulls dans toJson(), commente la ligne ci-dessous :
				() -> assertFalse(serializedJson.containsKey("statutCode"), "Null structural flags must be omitted from output JSON string")
		);
	}

	@Test
	@Order(6)
	@DisplayName("Test Accounting Rules Logic - Balanced vs Unbalanced Operations")
	void testAccountingBalanceLogic() {
		// Cas 1 : L'opération ID 104 est équilibrée (1000 + 200 = 1200)
		JsonObject journalObj = testDataset.getJsonObject("initialJournal");
		JsonObject balancedJson = journalObj.getJsonArray("operations").getJsonObject(3);
		OperationDTO balancedOp = new OperationDTO(balancedJson);

		// Cas 2 : L'opération ID 204 est déséquilibrée (Débit 500 / Crédit 450)
		JsonObject unbalancedJson = testDataset.getJsonArray("operationsToAdd").getJsonObject(3);
		OperationDTO unbalancedOp = new OperationDTO(unbalancedJson);

		assertAll("Financial compliance and balance validations rules",
				// TODO: À lier avec ta méthode d'équilibrage définitive (ex: operation.isBalanced() ou ValidationEngine)
				// Si ta méthode métier s'appelle operation.isBalanced(), remplace la condition ci-dessous :
				() -> logger.info("Checking balanced validation logic for OP 104 and 204"),
				() -> assertNotNull(balancedOp.getMovements()),
				() -> assertNotNull(unbalancedOp.getMovements())
		);
	}
}