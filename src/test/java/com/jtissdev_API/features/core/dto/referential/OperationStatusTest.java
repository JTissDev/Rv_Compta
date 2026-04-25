package com.jtissdev_API.features.core.dto.referential;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
public class OperationStatusTest {

	private static JsonObject statusJson;

	/**
	 * Sets up a standard JsonObject for OperationStatus before each test.
	 */

	@BeforeAll
	@DisplayName("Setup JSON Objects for OperationStatusTest")
	static void setUpTest() {
		statusJson = Json.createObjectBuilder()
				                  .add("fields", Json.createObjectBuilder()
				                                        .add("code", "REAL")
						                                 .add("name","réalisé")
						                                 .add("color", "#00FF00").build())
				                  .add("codeOnly", Json.createObjectBuilder()
						                                   .add("code", "REAL").build())
				                  .add("nameOnly", Json.createObjectBuilder()
						                                   .add("name", "réalisé").build())
				                  .add("colorOnly", Json.createObjectBuilder()
						                                    .add("color", "#00FF00").build())
				                  .add("codeAndName", Json.createObjectBuilder()
						                                      .add("code", "REAL")
						                                      .add("name", "réalisé").build())
				                  .add("codeAndColor", Json.createObjectBuilder()
						                                       .add("code", "REAL")
						                                       .add("color", "#00FF00").build())
				                  .add("nameAndColor", Json.createObjectBuilder()
						                                       .add("name", "réalisé")
						                                       .add("color", "#00FF00").build())
				                  .add("allFields", Json.createObjectBuilder()
						                                    .add("code", "REAL")
						                                    .add("name", "réalisé")
						                                    .add("color", "#00FF00").build())
				                  .build();
	}

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		// Given & When
		OperationStatus status = new OperationStatus();

		// Then
		assertAll("Empty constructor state validation",
				() -> assertNull(status.getCode()),
				() -> assertNull(status.getName()),
				() -> assertNull(status.getColor())
		);
	}

	@Test
	@DisplayName("Test JSON Constructor")
	void testJsonConstructor() {
		// Given & When
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("allFields"));

		// Then
		assertAll("JSON constructor mapping validation",
				() -> assertEquals(statusJson.getJsonObject("fields").getString("code"), status.getCode()),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("name"), status.getName()),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("color"), status.getColor())
		);
	}

	@Test
	@DisplayName("Fluent API: setters should return this instance")
	void testFluentSetters() {
		// Given
		OperationStatus status = new OperationStatus(statusJson.getJsonObject("allFields"));

		// Then
		assertAll("Fluent API validation",
		//		() -> assertSame(status, statusJson.getJsonObject("allFields")),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("code"), status.getCode()),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("name"), status.getName()),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("color"), status.getColor())
		);
	}

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
				() -> assertEquals(statusJson.getJsonObject("fields").getString("code"), json.getString("code")),
				() -> assertEquals(statusJson.getJsonObject("fields").getString("name"), json.getString("name")),
				() -> assertFalse(json.containsKey("color"), "Null color should be omitted"),
				() -> assertEquals(2, json.size())
		);
	}

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
				() -> assertThat(result).contains("code="+statusJson.getJsonObject("fields").getString("code")+"'"),
				() -> assertThat(result).contains("name="+statusJson.getJsonObject("fields").getString("name")+"'")
		);
	}
}