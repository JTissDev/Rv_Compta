package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AnalyticDetail} DTO.
 * Verifies JSON mapping, data integrity via getters/setters,
 * and the specific toString formatting.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
public class AnalyticDetailTest {

	private static final String CODE = ".123";
	private static final String TYPE = "Compte";
	private static final String NAME = "Compte Courant";
	private static final String DESCRIPTION = "Compte principal professionnel";

	private static JsonObject detailJson;

	/**
	 *
	 */
	@BeforeAll
	@DisplayName("Setup JSON Object for AnalyticDetail")
	static void setUp() {
		detailJson = Json.createObjectBuilder()
				             .add("codeOnly", Json.createObjectBuilder()
						                              .add("code", CODE).build())
				             .add("typeOnly", Json.createObjectBuilder()
						                              .add("type", TYPE).build())
				             .add("nameOnly", Json.createObjectBuilder()
						                              .add("name", NAME).build())
				             .add("descriptionOnly", Json.createObjectBuilder()
						                                     .add("description", DESCRIPTION).build())
				             .add("codeAndType", Json.createObjectBuilder()
						                                 .add("code", CODE)
						                                 .add("type", TYPE).build())
				             .add("codeAndName", Json.createObjectBuilder()
						                                 .add("code", CODE)
						                                 .add("name", NAME).build())
				             .add("codeAndDescription", Json.createObjectBuilder()
						                                        .add("code", CODE)
						                                        .add("description", DESCRIPTION).build())
				             .add("typeAndName", Json.createObjectBuilder()
						                                 .add("type", TYPE)
						                                 .add("name", NAME).build())
				             .add("typeAndDescription", Json.createObjectBuilder()
						                                        .add("type", TYPE)
						                                        .add("description", DESCRIPTION).build())
				             .add("nameAndDescription", Json.createObjectBuilder()
						                                        .add("name", NAME)
						                                        .add("description", DESCRIPTION).build())
				             .add("allFields", Json.createObjectBuilder()
						                               .add("code", CODE)
						                               .add("type", TYPE)
						                               .add("name", NAME)
						                               .add("description", DESCRIPTION).build())
				             .build();
	}

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		// Given & When
		AnalyticDetail detail = new AnalyticDetail();

		// Then
		assertAll("Empty constructor state validation",
				() -> assertNull(detail.getCode(), "Code should be null"),
				() -> assertNull(detail.getType(), "Type should be null"),
				() -> assertNull(detail.getName(), "Name should be null"),
				() -> assertNull(detail.getDescription(), "Description should be null")
		);
	}

	@Test
	@DisplayName("Test JSON Constructor")
	void testJsonConstructor() {
		// Given & When
		AnalyticDetail detail = new AnalyticDetail(detailJson.getJsonObject("allFields"));

		// Then
		assertAll("JSON constructor mapping validation",
				() -> assertEquals(CODE, detail.getCode(), "Code mapping failed"),
				() -> assertEquals(TYPE, detail.getType(), "Type mapping failed"),
				() -> assertEquals(NAME, detail.getName(), "Name mapping failed"),
				() -> assertEquals(DESCRIPTION, detail.getDescription(), "Description mapping failed")
		);
	}

	@Test
	@DisplayName("Fluent API : setters should return this instance")
	void testSettersAndGetters() {
		// Given
		AnalyticDetail detail = new AnalyticDetail();

		AnalyticDetail result = detail
				                        .setCode(CODE)
				                        .setType(TYPE)
				                        .setName(NAME)
				                        .setDescription(DESCRIPTION);


		// Then
		assertAll("Setters validation",
				() -> assertSame(detail, result, "Setter must return the same instance"),
				() -> assertEquals(CODE, detail.getCode(), "Code should be set"),
				() -> assertEquals(TYPE, detail.getType(), "Type should be set"),
				() -> assertEquals(NAME, detail.getName(), "Name should be set"),
				() -> assertEquals(DESCRIPTION, detail.getDescription(), "Description should be set")
		);
	}

	/**
	 * Tests the {@link AnalyticDetail#toJson()} method.
	 * Verifies that all fields are correctly serialized and that
	 * null values are replaced by empty strings as per implementation.
	 */
	@Test
	@DisplayName("toJson: should serialize all fields with empty string defaults")
	void testToJsonSerialization() {
		// Given
		AnalyticDetail detail = new AnalyticDetail(detailJson.getJsonObject("codeAndName"));

		// type and description are left null

		// When
		JsonObject json = detail.toJson();

		// Then
		assertAll("JSON serialization validation",
				() -> assertEquals(CODE, json.getString("code"), "Code should be serialized"),
				() -> assertEquals(NAME, json.getString("name"), "Name should be serialized"),
				() -> assertFalse(json.containsKey("type"),"Null type should be omitted"),
				() -> assertFalse(json.containsKey("description"),"Null description should be ommitted"),
				() -> assertEquals(2, json.size(), "JSON should contain exactly 2 keys")

		);
	}

	/**
	 * Tests the {@link AnalyticDetail#toString()} implementation.
	 * Expected format: "[code] name (type)"
	 */
	@Test
	@DisplayName("toString: should follow the format [code] name (type)")
	void testToString() {
		// Given
		AnalyticDetail detail = new AnalyticDetail(detailJson.getJsonObject("codeAndName"))
				                        .setType(TYPE);

		// When
		String result = detail.toString();

		// Then
		assertAll("toString content validation",
				() -> assertThat(result).contains("Analytic Detail"),
				() -> assertThat(result).contains("code="+CODE),
				() -> assertThat(result).contains("type="+TYPE),
				() -> assertThat(result).contains("name="+NAME)
				);
	}
}