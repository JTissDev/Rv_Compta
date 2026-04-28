package com.jtissdev_API.features.core.dto.referential;

import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PaymentMethod} DTO.
 * Verifies the fluent API, JSON serialization and data integrity.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("PaymentMethod Test Suite")
public class PaymentMethodTest {

	/**
	 * A logger instance used for logging messages and debugging information within the test class.
	 * It adheres to the SLF4J logging facade and is initialized to capture logs specific to the
	 * behavior and execution of tests in the OperationStatusTest class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodTest.class);

	/**
	 * A constant that represents a predefined code used in payment method tests.
	 * This code is utilized as a standard identifier across test scenarios
	 * to validate behavior and ensure consistency during JSON mapping, serialization,
	 * and other test-related operations in the `PaymentMethodTest` class.
	 */
	private static final String CODE = "CB";
	private static final String NAME = "Carte Bancaire";
	private static final String DESCRIPTION = "Payement par Carte Bancaire";

	/**
	 * A static JSON object used to represent predefined payment method data required in unit tests.
	 *
	 * This object is initialized during the test setup phase and provides a structured JSON representation
	 * of various payment method attributes. It is primarily utilized for validating the behavior of the
	 * PaymentMethod class in test cases involving JSON parsing, serialization, and field mapping.
	 */
	private static JsonObject paymentMethodJson;

	/**
	 * Sets up a standard JsonObject for PaymentMethod before each test.
	 */
	@BeforeAll
	@DisplayName("Setup JSON Object for PaymentMethod")
	static void setUpTest() {
		paymentMethodJson = Json.createObjectBuilder()
				                         
				                         .add("codeOnly", Json.createObjectBuilder()
						                                          .add("code", CODE).build())
				                         .add("nameOnly", Json.createObjectBuilder()
						                                          .add("name", NAME).build())
				                         .add("descriptionOnly", Json.createObjectBuilder()
						                                                 .add("description", DESCRIPTION).build())
										 .add("codeAndName", Json.createObjectBuilder()
												                     .add("code", CODE)
												                     .add("name", NAME).build())
										 .add("codeAndDescription", Json.createObjectBuilder()
												                            .add("code", CODE)
												                            .add("description", DESCRIPTION).build())
										 .add("nameAndDescription", Json.createObjectBuilder()
												                            .add("name", NAME)
												                            .add("description", DESCRIPTION).build())
				                         .add("allFields", Json.createObjectBuilder()
						                                           .add("code", CODE)
						                                           .add("name", NAME)
						                                           .add("description", DESCRIPTION).build())
				                         .build();
	}

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		// Given & When
		PaymentMethod method = new PaymentMethod();

		// Then
		assertAll("Empty constructor state validation",
				() -> assertNull(method.getCode(), "Code should be null"),
				() -> assertNull(method.getName(), "Name should be null"),
				() -> assertNull(method.getDescription(), "Description should be null")
		);
	}

	@Test
	@DisplayName("Test Full JSON Constructor")
	void testFullJsonConstructor() {
		// Given & When
		PaymentMethod method = new PaymentMethod(paymentMethodJson.getJsonObject("allFields"));

		// Then
		assertAll("JSON constructor mapping validation",
				() -> assertEquals(CODE, method.getCode(), "Code mapping failed"),
				() -> assertEquals(NAME, method.getName(), "Name mapping failed"),
				() -> assertEquals(DESCRIPTION, method.getDescription(), "Description mapping failed")
		);
	}

	@Test@DisplayName("Test Code Only Constructor")
	void testCodeOnlyConstructor() {
		// Given and When
		PaymentMethod method = new PaymentMethod(paymentMethodJson.getJsonObject("codeOnly"));

		// Then
		assertAll("Code only constructor mapping validation",
					() -> assertEquals(CODE, method.getCode(), "Code mapping failed"),
					() -> assertNull(method.getName(), "Name should be null"),
					() -> assertNull(method.getDescription(), "Description should be null")
			);
	}
	@Test
	@DisplayName("Fluent API: setters should update fields and return this instance")
	void testFluentSetters() {
		// Given
		PaymentMethod method = new PaymentMethod();

		PaymentMethod result = method
				                       .setCode(CODE)
				                       .setName(NAME)
				                       .setDescription(DESCRIPTION);

		// Then
		assertAll("Fluent API validation",
				() -> assertSame(method, result, "Setter must return the same instance"),
				() -> assertEquals(CODE, method.getCode(), "Code should be set"),
				() -> assertEquals(NAME, method.getName(), "Name should be set"),
				() -> assertEquals(DESCRIPTION, method.getDescription(), "Description should be set")
		);
	}

	@Test
	@DisplayName("toJson: should serialize all non-null fields")
	void testToJsonSerialization() {
		// Given
		PaymentMethod method = new PaymentMethod(paymentMethodJson.getJsonObject("codeAndName"));
		// description is left null

		// When
		JsonObject json = method.toJson();

		// Then
		assertAll("JSON serialization validation",
				() -> assertEquals(CODE, json.getString("code"), "Code should be serialized"),
				() -> assertEquals(NAME, json.getString("name"), "Name should be serialized"),
				() -> assertFalse(json.containsKey("description"), "Null description should not be in JSON"),
				() -> assertEquals(2, json.size(), "JSON should contain exactly 2 keys")
		);
	}

	@Test
	@DisplayName("toString: should contain class name and field values")
	void testToString() {
		// Given
		PaymentMethod method = new PaymentMethod(paymentMethodJson.getJsonObject("codeAndName"));

		// When
		String result = method.toString();

		// Then
		assertAll("toString content validation",
				() -> assertThat(result).contains("PaymentMethod"),
				() -> assertThat(result).contains("code=" + CODE),
				() -> assertThat(result).contains("name=" + NAME)
		);
	}
}