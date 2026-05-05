package com.jtissdev_API.features.compta.dto;

import com.jtissdev_API.features.core.dto.PcpCoreDTOTest;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @version 1.2.0
 * @since 0.3.0
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("Movment Test Suite")
@TestGroup("Compta DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovementDTOTest {

	private static final Logger logger = LoggerFactory.getLogger(MovementDTOTest.class);

	private static final Integer MOVEMENT_ID = 123;
	private static final Integer TIERS_ID = 456;
	private static final String ACCOUNT_CODE = "123456";
	private static final String COMPLEMENT_CODE = "COMP-123";
	private static final String DETAIL_CODE = "DETAIL-123";
	private static final String PAYMENT_CODE = "ESPECES";
	private static final String DESCRIPTION = "Test Description";
	private static final String REFERENCE = "REF-123";
	private static final BigDecimal DEBIT_AMOUNT = new BigDecimal("100.50");
	private static final BigDecimal CREDIT_AMOUNT = new BigDecimal("20.75");
	private static final BigDecimal TOTAL_AMOUNT = DEBIT_AMOUNT.add(CREDIT_AMOUNT);

	private static MovementDTO movement;
	private static JsonObject movmentJson;

	@BeforeAll
	@DisplayName("Setup Test Data")
	static void setUp() {
		movmentJson = Json.createObjectBuilder()
				              .add("id_Only", Json.createObjectBuilder()
						                              .add("id", MOVEMENT_ID)
						                              .build())
				              .add("fullCreditMovement", Json.createObjectBuilder()
						                                         .add("id", MOVEMENT_ID)
						                                         .add("tiersId", TIERS_ID)
						                                         .add("accountingCode", ACCOUNT_CODE)
						                                         .add("complementaryAccountingCode", COMPLEMENT_CODE)
						                                         .add("accountDetailCode", DETAIL_CODE)
						                                         .add("paymentCode", PAYMENT_CODE)
						                                         .add("description", DESCRIPTION)
						                                         .add("reference", REFERENCE)
						                                         .add("creditAmount", CREDIT_AMOUNT)
						                                         .build())
				              .add("fullDebitMovement", Json.createObjectBuilder()
						                                        .add("id", MOVEMENT_ID)
						                                        .add("tiersId", TIERS_ID)
						                                        .add("accountingCode", ACCOUNT_CODE)
						                                        .add("complementaryAccountingCode", COMPLEMENT_CODE)
						                                        .add("accountDetailCode", DETAIL_CODE)
						                                        .add("paymentCode", PAYMENT_CODE)
						                                        .add("description", DESCRIPTION)
						                                        .add("reference", REFERENCE)
						                                        .add("debitAmount", DEBIT_AMOUNT)
						                                        .build())
				              .add("simpleDebitMovement", Json.createObjectBuilder()
						                                          .add("id", MOVEMENT_ID)
						                                          .add("tiersId", TIERS_ID)
						                                          .add("accountingCode", ACCOUNT_CODE)
						                                          .add("paymentCode", PAYMENT_CODE)
						                                          .add("description", DESCRIPTION)
						                                          .add("reference", REFERENCE)
						                                          .add("debitAmount", DEBIT_AMOUNT)
						                                          .build())
				              .add("simpleCreditMovement", Json.createObjectBuilder()
						                                           .add("id", MOVEMENT_ID)
						                                           .add("tiersId", TIERS_ID)
						                                           .add("accountingCode", ACCOUNT_CODE)
						                                           .add("paymentCode", PAYMENT_CODE)
						                                           .add("description", DESCRIPTION)
						                                           .add("reference", REFERENCE)
						                                           .add("creditAmount", CREDIT_AMOUNT)
						                                           .build())
				              .build();
	}

	/**
	 * Tests the default constructor.
	 * Verified: Default values for BigDecimal fields should be ZERO.
	 *
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Test Empty Constructor)")
	@Order(1)
	void testEmptyConstructor() {
		MovementDTO movement = new MovementDTO();

		assertNull(movement.getId(), "ID should be null by default");
		assertEquals(BigDecimal.ZERO, movement.getDebitAmount(), "Debit should be initialized to ZERO");
		assertEquals(BigDecimal.ZERO, movement.getCreditAmount(), "Credit should be initialized to ZERO");
	}


	/**
	 * Tests the Fluent API (chaining setters).
	 * Verified: Each setter returns 'this' and updates the value.
	 *
	 * @since 0.3.0
	 */
	@Test
	@DisplayName("Should support fluent method chaining")
	void shouldSupportFluentApi() {
		movement = new MovementDTO();
		MovementDTO movementResult = movement.setId(MOVEMENT_ID)
				                             .setTiersId(TIERS_ID)
				                             .setPaiementCode(PAYMENT_CODE)
				                             .setDescription(DESCRIPTION)
				                             .setAccountingCode(ACCOUNT_CODE)
				                             .setComplementaryAccountingCode(COMPLEMENT_CODE)
				                             .setAccountDetailCode(DETAIL_CODE)
				                             .setCreditAmount(CREDIT_AMOUNT)
				                             .setDebitAmount(DEBIT_AMOUNT);

		assertAll("Fluent setter verification",
				() -> assertSame(movementResult, movement, "Setter should return 'this'"),
				() -> assertEquals(MOVEMENT_ID, movement.getId()),
				() -> assertEquals(TIERS_ID, movement.getTiersId()),
				() -> assertEquals(PAYMENT_CODE, movement.getPaiementCode()),
				() -> assertEquals(DESCRIPTION, movement.getDescription()),
				() -> assertEquals(ACCOUNT_CODE, movement.getAccountingCode()),
				() -> assertEquals(COMPLEMENT_CODE, movement.getComplementaryAccountingCode()),
				() -> assertEquals(DETAIL_CODE, movement.getAccountDetailCode()),
				() -> assertEquals(CREDIT_AMOUNT, movement.getCreditAmount()),
				() -> assertEquals(DEBIT_AMOUNT, movement.getDebitAmount())
		);
	}

	@Test @Order(3) @DisplayName("Test Json Constructor credit movment")
	void testJsonCreditConstructor(){
		movement = new MovementDTO(movmentJson.getJsonObject("fullCreditMovement"));

		assertAll("Json constructor verification",
				() -> assertEquals(MOVEMENT_ID, movement.getId()),
				() -> assertEquals(TIERS_ID, movement.getTiersId()),
				() -> assertEquals(PAYMENT_CODE, movement.getPaiementCode()),
				() -> assertEquals(DESCRIPTION, movement.getDescription()),
				() -> assertEquals(ACCOUNT_CODE, movement.getAccountingCode()),
				() -> assertEquals(COMPLEMENT_CODE, movement.getComplementaryAccountingCode()),
				() -> assertEquals(DETAIL_CODE, movement.getAccountDetailCode()),
				() -> assertEquals(CREDIT_AMOUNT, movement.getCreditAmount())
		);
	}

	@Test @Order(3) @DisplayName("Test Json Constructor debit movment")
	void testJsonDebitConstructor(){
		movement = new MovementDTO(movmentJson.getJsonObject("fullDebitMovement"));
		assertAll("Json constructor verification",
					() -> assertEquals(MOVEMENT_ID, movement.getId()),
					() -> assertEquals(TIERS_ID, movement.getTiersId()),
					() -> assertEquals(PAYMENT_CODE, movement.getPaiementCode()),
					() -> assertEquals(DESCRIPTION, movement.getDescription()),
					() -> assertEquals(ACCOUNT_CODE, movement.getAccountingCode()),
					() -> assertEquals(COMPLEMENT_CODE, movement.getComplementaryAccountingCode()),
					() -> assertEquals(DETAIL_CODE, movement.getAccountDetailCode()),
					() -> assertEquals(DEBIT_AMOUNT, movement.getDebitAmount())
			);
	}

	@Test @Order(4) @DisplayName("Test Serialization toJson")
	void testToJson(){
		movement = new MovementDTO(movmentJson.getJsonObject("simpleDebitMovement"));
		JsonObject json = movement.toJson();

		assertAll("check keys are include when value are not null",
				() -> assertTrue(json.containsKey("id"),"id not null keys should be inserted"),
				() -> assertTrue(json.containsKey("tiersId"),"tiersId not null keys should be inserted"),
				() -> assertTrue(json.containsKey("accountingCode"),"accountingCode not null keys should be inserted"),
				() -> assertTrue(json.containsKey("paymentCode"),"paymentCode not null keys should be inserted"),
				() -> assertTrue(json.containsKey("description"),"description not null keys should be inserted")
				);

	}

	/**
	 * Tests BigDecimal precision for financial calculations.
	 * Verified: No loss of precision during assignment.
	 *
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