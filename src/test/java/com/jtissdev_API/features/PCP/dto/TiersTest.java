package com.jtissdev_API.features.PCP.dto;

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
 * Unit tests for the {@link Tiers} DTO.
 * Verifies constructor mapping, data integrity via getters/setters,
 * and JSON serialization logic.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
@DisplayName("Tiers Test Suite")
@ExtendWith(TestResultLogger.class)
public class TiersTest {

	private static final Logger logger = LoggerFactory.getLogger(TiersTest.class);

	private static final Integer ID = 1;
	private static final String NAME = "Tier 1";
	private static final String THIRD_PARTY_CODE = "TP1";
	private static final String DESCRIPTION = "Tier 1 description";

	private static JsonObject tierJson;

	@BeforeAll
	@DisplayName("Setup JSON Objects for Tiers")
	static void setUpTest() {

		tierJson = Json.createObjectBuilder()
				           .add("idOnly", Json.createObjectBuilder()
						                          .add("id", ID).build())
				           .add("nameOnly", Json.createObjectBuilder()
						                            .add("name", NAME).build())
				           .add("thirdPartyOnly", Json.createObjectBuilder()
						                                  .add("thirdPartyType", THIRD_PARTY_CODE).build())
				           .add("descriptionOnly", Json.createObjectBuilder()
						                                   .add("description", DESCRIPTION).build())
				           .add("idAndName", Json.createObjectBuilder()
						                             .add("id", ID)
						                             .add("name", NAME).build())
				           .add("nameAndThirdParty", Json.createObjectBuilder()
						                                     .add("name", NAME)
						                                     .add("thirdPartyType", THIRD_PARTY_CODE).build())
				           .add("allFields", Json.createObjectBuilder()
						                             .add("id", ID)
						                             .add("name", NAME)
						                             .add("thirdPartyType", THIRD_PARTY_CODE)
						                             .add("description", DESCRIPTION)
						                             .build())
				           .build();
	}

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		Tiers tier = new Tiers();

		assertAll("Empty constructor state validation",
				() -> assertNull(tier.getId(), "Id should be null"),
				() -> assertNull(tier.getName(), "Name should be null"),
				() -> assertNull(tier.getThirdPartyType(), "ThirdPartyType should be null"),
				() -> assertNull(tier.getDescription(), "Description should be null")
		);
	}

	@Test
	@DisplayName("Test Full Json Constructor")
	void testFullJsonConstructor() {
		Tiers tier = new Tiers(tierJson.getJsonObject("allFields"));

		assertAll("JSON constructor mapping validation",
				() -> assertEquals(ID, tier.getId(), "Id mapping failed"),
				() -> assertEquals(NAME, tier.getName(), "Name mapping failed"),
				() -> assertEquals(THIRD_PARTY_CODE, tier.getThirdPartyType(), "Third Party Code mapping failed"),
				() -> assertEquals(DESCRIPTION, tier.getDescription(), "Description mapping failed")
		);
	}

	@Test
	@DisplayName("Test name only Json Constructor")
	void testNameOnlyJsonConstructor() {
		Tiers tier = new Tiers(tierJson.getJsonObject("nameOnly"));

		assertAll("JSON constructor mapping validation",
				() -> assertNull(tier.getId(), "Id should be null"),
				() -> assertEquals(NAME, tier.getName(), "Name mapping failed"),
				() -> assertNull(tier.getThirdPartyType(), "Third Party Code should be null"),
				() -> assertNull(tier.getDescription(), "Description should be null")
		);
	}

	@Test
	@DisplayName("Test Fluent Setters")
	void testFluentSetters() {
		Tiers tier = new Tiers();

		Tiers result = tier.setId(ID)
				               .setName(NAME)
				               .setThirdPartyType(THIRD_PARTY_CODE)
				               .setDescription(DESCRIPTION);

		assertAll("Fluent API validation",
				() -> assertSame(tier, result, "Setter must return the same instance"),
				() -> assertEquals(ID, tier.getId(), "Id should be set"),
				() -> assertEquals(NAME, tier.getName(), "Name should be set"),
				() -> assertEquals(THIRD_PARTY_CODE, tier.getThirdPartyType(), "Third Party Code should be set"),
				() -> assertEquals(DESCRIPTION, tier.getDescription(), "Description should be set")
		);
	}

	@Test
	@DisplayName("toJson: should only include non-null fields")
	void testToJsonSerialization() {
		Tiers tiers = new Tiers(tierJson.getJsonObject("nameAndThirdParty"));

		JsonObject json = tiers.toJson();

		assertAll("JSON serialization validation",
				() -> assertFalse(json.containsKey("id"), "Null id should be omitted"),
				() -> assertEquals(NAME, json.getString("name"), "Name should be serialized"),
				() -> assertEquals(THIRD_PARTY_CODE, json.getString("thirdPartyType"), "Third Party Code should be serialized"),
				() -> assertFalse(json.containsKey("description"), "Null description should be omitted"),
				() -> assertEquals(2, json.size(), "JSON should contain exactly 2 keys")
		);
	}

	@Test
	@DisplayName("toString: should contain class name and field values")
	void testToString() {
		Tiers tiers = new Tiers(tierJson.getJsonObject("nameAndThirdParty"));

		String result = tiers.toString();

		assertAll("toString content validation",
				() -> assertThat(result).contains("Tiers"),
				() -> assertThat(result).contains("name=" + NAME),
				() -> assertThat(result).contains("thirdPartyType=" + THIRD_PARTY_CODE)
		);
	}
}
