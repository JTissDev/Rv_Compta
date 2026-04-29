package com.jtissdev_API.features.core.dto.referential;

import com.jtissdev_API.features.core.dto.ReferentialCoreDTOTest;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link OperationStatus} DTO.
 * Verifies fluent API chaining, JSON mapping and serialization.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("OperationStatus Test")
@TestGroup("Réferentiel")
public class OperationStatusTest {

	/**
	 * Logger instance for the {@code OperationStatusTest} class.
	 *
	 * This logger is used for recording events, errors, and debug information
	 * during the execution of test cases in the {@code OperationStatusTest} class.
	 * It helps capture runtime messages and facilitates debugging by outputting
	 * relevant log statements for analysis.
	 *
	 * The logging framework used is SLF4J with a specific configuration
	 * for this class, allowing targeted and context-specific logging.
	 *
	 * Scope: Static and final, ensuring a single shared instance for the entire class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(OperationStatusTest.class);

	/**
	 * Represents a predefined status code for the `OperationStatusTest` class.
	 * This constant is used to denote a real operation status during tests.
	 */
	private static final String CODE = "REAL";
	private static final String NAME = "réalisé";
	private static final String COLOR = "#00FF00";

	/**
	 * A static JSON object used to represent the status attributes and values
	 * required in the {@code OperationStatusTest} unit tests.
	 *
	 * This object is initialized in the setup phase of the test lifecycle
	 * to provide predefined JSON structures for validating the behavior
	 * of the {@code OperationStatus} class.
	 */
	private static JsonObject statusJson;

	/**
	 * Initializes a JSON object containing various configurations
	 * of the "OperationStatus" data for testing purposes.
	 *
	 * This method prepares multiple test cases by constructing a comprehensive
	 * JSON structure that simulates different combinations of "code", "name",
	 * and "color" fields. The resulting JSON object is used throughout
	 * the test scenarios to validate the behavior of the "OperationStatus" class
	 * under various configurations.
	 *
	 * The following configurations are included in the JSON object:
	 * - Only "code" field
	 * - Only "name" field
	 * - Only "color" field
	 * - Combination of "code" and "name"
	 * - Combination of "code" and "color"
	 * - Combination of "name" and "color"
	 * - All fields: "code", "name", and "color"
	 *
	 * This method is executed once before all test cases in the class,
	 * ensuring consistency and reusability of the test data.
	 */
	@BeforeAll
	@DisplayName("Setup JSON Objects for OperationStatusTest")
	static void setUpTest() {
		statusJson = Json.createObjectBuilder()
				             .add("codeOnly", Json.createObjectBuilder()
						                              .add("code", CODE).build())
				             .add("nameOnly", Json.createObjectBuilder()
						                              .add("name", NAME).build())
				             .add("colorOnly", Json.createObjectBuilder()
						                               .add("color", COLOR).build())
				             .add("codeAndName", Json.createObjectBuilder()
						                                 .add("code", CODE)
						                                 .add("name", NAME).build())
				             .add("codeAndColor", Json.createObjectBuilder()
						                                  .add("code", CODE)
						                                  .add("color", COLOR).build())
				             .add("nameAndColor", Json.createObjectBuilder()
						                                  .add("name", NAME)
						                                  .add("color", COLOR).build())
				             .add("allFields", Json.createObjectBuilder()
						                               .add("code", CODE)
						                               .add("name", NAME)
						                               .add("color", COLOR).build())
				             .build();
	}

	/**
	 * Validates the behavior of the no-argument constructor of the {@code OperationStatus} class.
	 *
	 * This test ensures that when an {@code OperationStatus} instance is created using the
	 * empty constructor, all fields ({@code code}, {@code name}, and {@code color}) are initialized
	 * to {@code null}, representing an uninitialized state.
	 *
	 * Test steps:
	 * - Create an instance of {@code OperationStatus} using the default constructor.
	 * - Assert that the {@code getCode}, {@code getName}, and {@code getColor} methods all return {@code null}.
	 *
	 * Assertions:
	 * - {@code getCode()} returns {@code null}.
	 * - {@code getName()} returns {@code null}.
	 * - {@code getColor()} returns {@code null}.
	 */
	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		// Given & When
		OperationStatus status = new OperationStatus();

		// Then
		assertAll("Empty constructor state validation",
				() -> assertNull(status.getCode(), "Code should be null"),
				() -> assertNull(status.getName(), "Name should be null"),
				() -> assertNull(status.getColor(), "Color should be null")
		);
	}

	/**
	 * Validates the mapping of JSON object fields to the corresponding fields
	 * of an {@code OperationStatus} instance during initialization via the JSON constructor.
	 *
	 * This test ensures that when an {@code OperationStatus} object is instantiated
	 * using a JSON object, the fields ("code", "name", "color") are correctly populated
	 * from the respective JSON properties.
	 *
	 * Test scenario:
	 * - Constructs an {@code OperationStatus} using a test JSON object containing
	 *   values for "code", "name", and "color".
	 * - Verifies that the object's fields match the expected values, ensuring proper
	 *   field mapping by the constructor.
	 *
	 * Assertions:
	 * - {@code getCode()} returns the expected value from the JSON object.
	 * - {@code getName()} returns the expected value from the JSON object.
	 * - {@code getColor()} returns the expected value from the JSON object.
	 */
	@Test
	@DisplayName("Test JSON Full Constructor")
	void testJsonConstructor() {
		// Given & When
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("allFields"));

		// Then
		assertAll("JSON constructor mapping validation",
				() -> assertEquals(CODE, status.getCode(), "Code mapping failed"),
				() -> assertEquals(NAME, status.getName(), "Name mapping failed"),
				() -> assertEquals(COLOR, status.getColor(), "Color mapping failed")
		);
	}

	/**
	 * Validates the behavior of the JSON constructor in the {@code OperationStatus} class
	 * when handling incomplete JSON data.
	 *
	 * This test ensures that the constructor can correctly map available fields
	 * ("code" and "name") from a given JSON object and handle the absence of
	 * the optional field ("color") gracefully.
	 *
	 * Test scenario:
	 * - Constructs an {@code OperationStatus} instance using a JSON object
	 *   containing only "code" and "name" fields, while omitting the "color" field.
	 * - Verifies that the fields "code" and "name" are correctly mapped from the JSON object.
	 * - Checks that the "color" field is set to {@code null} as it is absent in the JSON input.
	 *
	 * Assertions:
	 * - {@code getCode()} returns the expected value mapped from the "code" field in the JSON object.
	 * - {@code getName()} returns the expected value mapped from the "name" field in the JSON object.
	 * - {@code getColor()} is {@code null}, as the "color" field is absent in the JSON object.
	 */
	@Test
	@DisplayName("Test JSON constructor robustness with incomplete data")
	void testJsonPartialConstructor() {
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("codeAndName"));

		assertAll("Test JSON constructor: should handle JSON without color field",
				() -> assertEquals(CODE, status.getCode(),"Code mapping failed"),
				() -> assertEquals(NAME, status.getName(),"Name mapping failed"),
				() -> assertNull(status.getColor(), "Color should be null")
				);
	}

	/**
	 * Validates the behavior of the setters in the {@code OperationStatus} class
	 * when used in a fluent API style.
	 *
	 * This test ensures that:
	 * - Each setter method returns the same instance of {@code OperationStatus}.
	 * - The provided values are correctly set on the instance.
	 *
	 * Test scenario:
	 * - An {@code OperationStatus} object is created.
	 * - Its {@code setCode}, {@code setName}, and {@code setColor} methods are called in sequence.
	 * - The returned instance is compared with the original instance to ensure they are the same.
	 * - The values set through the setter methods are validated against the expected values.
	 *
	 * Assertions:
	 * - The returned instance from the setters is the same as the original instance.
	 * - {@code getCode()} returns the expected value after calling {@code setCode}.
	 * - {@code getName()} returns the expected value after calling {@code setName}.
	 * - {@code getColor()} returns the expected value after calling {@code setColor}.
	 */
	@Test
	@DisplayName("Fluent API: setters should return this instance")
	void testFluentSetters() {
		// Given
		OperationStatus status = new OperationStatus();

		OperationStatus result = status
				                         .setCode(CODE)
				                         .setName(NAME)
				                         .setColor(COLOR);
		// Then
		assertAll("Fluent API validation",
				() -> assertSame(status, result, "Setter must return the same instance"),
				() -> assertEquals(CODE, status.getCode(), "Code should be set"),
				() -> assertEquals(NAME, status.getName(), "Name should be set"),
				() -> assertEquals(COLOR, status.getColor(), "Color should be set")
		);
	}

	/**
	 * Validates the behavior of the {@code toJson} method in the {@code OperationStatus} class to ensure
	 * that only non-null fields are included in the serialized JSON object.
	 *
	 * Test scenario:
	 * - An {@code OperationStatus} object is instantiated using a test JSON object which contains
	 *   specific field values, with some fields intentionally set to null (e.g., "color").
	 * - The {@code toJson} method is invoked to serialize the object into a {@code JsonObject}.
	 * - The resulting JSON object is validated to ensure it adheres to the expected structure, where:
	 *   - Non-null fields are included in the output.
	 *   - Null fields are omitted entirely from the JSON serialization.
	 *
	 * Assertions:
	 * - The JSON object contains keys for non-null fields ("code" and "name").
	 * - The JSON object omits keys for null fields (e.g., "color").
	 * - The number of keys in the serialized JSON matches the count of non-null fields.
	 */
	@Test
	@DisplayName("toJson: should only include non-null fields")
	void testToJsonSerialization() {
		// Given
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("codeAndName"));

		// color is null

		// When
		JsonObject json = status.toJson();

		// Then
		assertAll("JSON serialization validation",
				() -> assertEquals(CODE, json.getString("code")),
				() -> assertEquals(NAME, json.getString("name")),
				() -> assertFalse(json.containsKey("color"), "Null color should be omitted"),
				() -> assertEquals(2, json.size(), "JSON should contain exactly 2 keys")
		);
	}

	/**
	 * Validates the behavior of the {@code toString} method in the {@code OperationStatus} class
	 * to ensure that it correctly includes the class name and values of the fields.
	 *
	 * Test scenario:
	 * - An {@code OperationStatus} object is instantiated using a JSON representation of its fields.
	 * - The {@code toString} method is called, and the resulting string is captured.
	 * - The output string is checked to ensure it contains:
	 *   - The class name {@code OperationStatus}.
	 *   - The value of the {@code code} field.
	 *   - The value of the {@code name} field.
	 *
	 * Assertions:
	 * - The string includes the class name {@code OperationStatus}.
	 * - The string contains the field representation of {@code code}.
	 * - The string contains the field representation of {@code name}.
	 */
	@Test
	@DisplayName("toString: should contain class name and field values")
	void testToString() {
		// Given
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("codeAndName"));

		// When
		String result = status.toString();
		System.out.println(result);

		// Then
		assertAll("toString content validation",
				() -> assertThat(result).contains("OperationStatus"),
				() -> assertThat(result).contains("code=" + CODE ),
				() -> assertThat(result).contains("name=" + NAME )
		);
	}
}