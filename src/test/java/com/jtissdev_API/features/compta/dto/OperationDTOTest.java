package com.jtissdev_API.features.compta.dto;

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
 * @version 1.1.0
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
	private static final String REFERENCE_DOCUMENT = "REF-123";
	private static final String DESCRIPTION = "This is a test operation";
	private static final String STATUT_CODE = "VAL";
	private static LocalDate OPERATION_DATE = LocalDate.of(2026, 5, 15);
	private static LocalDate CONTABLE_DATE = LocalDate.of(2026, 5, 16);
	private static JsonArray movementsJson;
	private static JsonObject operationJson;
	private static OperationDTO operation;

	@BeforeAll
	static void setUp() {
		movementsJson = Json.createArrayBuilder()
				                .add(Json.createObjectBuilder()
						                     .add("id", 1)
						                     .add("accountingCode", "12")
						                     .add("paymentCode", "ESPECES")
						                     .add("creditAmount", 12.5)
						                     .build())
				                .add(Json.createObjectBuilder()
						                     .add("id", 2)
						                     .add("accountingCode", "13")
						                     .add("debitAmount", 12.5)
						                     .build())
				                .build();
		operationJson = Json.createObjectBuilder()
				                .add("withoutmovment", Json.createObjectBuilder()
						                                       .add("id", OPERATION_ID)
						                                       .add("dateOperation", String.valueOf(OPERATION_DATE))
						                                       .add("dateComptable", String.valueOf(CONTABLE_DATE))
						                                       .add("libelle", OPERATION_LIBELLE)
						                                       .add("referenceDocument", REFERENCE_DOCUMENT)
						                                       .add("descriptif", DESCRIPTION)
						                                       .add("statutCode", STATUT_CODE)
						                                       .build())
				                .add("withmovment", Json.createObjectBuilder()
						                                    .add("id", OPERATION_ID)
						                                    .add("dateOperation", String.valueOf(OPERATION_DATE))
						                                    .add("dateComptable", String.valueOf(CONTABLE_DATE))
						                                    .add("libelle", OPERATION_LIBELLE)
						                                    .add("referenceDocument", REFERENCE_DOCUMENT)
						                                    .add("descriptif", DESCRIPTION)
						                                    .add("statutCode", STATUT_CODE)
						                                    .add("movements", movementsJson)
						                                    .build())
				                .build();
	}

	/**
	 * Tests the default constructor.
	 * Verified: The movements list must be initialized and empty, not null.
	 *
	 * @since 0.3.0
	 */
	@Test
	@Order(1)
	@DisplayName("Should initialize with an empty movements list")
	void shouldInitializeWithEmptyList() {
		OperationDTO operation = new OperationDTO();

		assertNotNull(operation.getMovements(), "Movements list should never be null");
		assertTrue(operation.getMovements().isEmpty(), "Movements list should be empty on init");
	}

	@Test
	@Order(2)
	@DisplayName("Test Fluent Setters")
	void testFluentSetters() {
		operation = new OperationDTO();
		OperationDTO result = operation.setId(OPERATION_ID)
				                      .setDateOperation(OPERATION_DATE)
				                      .setDescriptif(DESCRIPTION)
				                      .setDateComptable(CONTABLE_DATE)
				                      .setLibelle(OPERATION_LIBELLE)
				                      .setReferenceDocument(REFERENCE_DOCUMENT)
				                      .setStatutCode(STATUT_CODE)
				                      .setMovements(movementsJson);

		assertAll("Setters should return this instance",
				() -> assertSame(result, operation, "setters should return this instance"),
				() -> assertEquals(OPERATION_ID, operation.getId(), "id must be initialized")

		);
	}

	@Test
	@Order(3)
	@DisplayName("Test Json Constructor")
	void testJsonConstructor() {

		operation = new OperationDTO(operationJson.getJsonObject("withmovment"));
		assertAll("Test Json Constructor",
				() -> assertEquals(OPERATION_ID, operation.getId(), "id must be initialized"),
				() -> assertEquals(OPERATION_DATE, operation.getDateOperation(), "dateOperation must be initialized"),
				() -> assertEquals(CONTABLE_DATE, operation.getDateComptable(), "dateComptable must be initialized"),
				() -> assertEquals(DESCRIPTION, operation.getDescriptif(), "descriptif must be initialized"),
				() -> assertEquals(REFERENCE_DOCUMENT, operation.getReferenceDocument(), "referenceDocument must be initialized"),
				() -> assertEquals(STATUT_CODE, operation.getStatutCode(), "statutCode must be initialized"),
				() -> assertEquals(2, operation.getMovements().size(), "movements must be initialized")
		);

	}

	@Test
	@Order(3)
	@DisplayName("Test unfull Json Constructor")
	void testUnfullJsonConstructor() {
		operation = new OperationDTO(operationJson.getJsonObject("withoutmovment"));
		assertAll("Test Json Constructor",
				() -> assertEquals(OPERATION_ID, operation.getId(), "id must be initialized"),
				() -> assertEquals(OPERATION_DATE, operation.getDateOperation(), "dateOperation must be initialized"),
				() -> assertEquals(CONTABLE_DATE, operation.getDateComptable(), "dateComptable must be initialized"),
				() -> assertEquals(DESCRIPTION, operation.getDescriptif(), "descriptif must be initialized"),
				() -> assertEquals(REFERENCE_DOCUMENT, operation.getReferenceDocument(), "referenceDocument must be initialized"),
				() -> assertEquals(STATUT_CODE, operation.getStatutCode(), "statutCode must be initialized"),
				() -> assertTrue(operation.getMovements().isEmpty(), "movements must be initialized")
		);


	}


	@Test
	@Order(4)
	@DisplayName("Test Serialization toJson")
	void testToJson() {
		operation = new OperationDTO(operationJson.getJsonObject("withmovment"));
		operation.setStatutCode(null);
		JsonObject json = operation.toJson();

		assertAll("Json must serialize only non null values",
				() -> assertTrue(json.containsKey("id"),"id not null keys should be inserted"),
				() -> assertTrue(json.containsKey("dateOperation"),"dateOperation not null keys should be inserted"),
				() -> assertTrue(json.containsKey("dateComptable"),"dateComptable not null keys should be inserted"),
				() -> assertTrue(json.containsKey("libelle"),"libelle not null keys should be inserted"),
				() -> assertTrue(json.containsKey("referenceDocument"),"referenceDocument not null keys should be inserted"),
				() -> assertTrue(json.containsKey("descriptif"),"descriptif not null keys should be inserted"),
				() -> assertTrue(json.containsKey("movements"),"movements not null keys should be inserted"),
				() -> assertFalse(json.containsKey("statutCode"),"statutCode null keys should not be inserted")
		);


	}
}