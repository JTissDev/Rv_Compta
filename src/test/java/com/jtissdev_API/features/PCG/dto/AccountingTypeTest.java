package com.jtissdev_API.features.PCG.dto;

import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Suite for AccountingType.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
@DisplayName("Accounting Type Test Suite")
@ExtendWith(TestResultLogger.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestGroup("PCG DTO")
public class AccountingTypeTest {

	private static final Logger logger = LoggerFactory.getLogger(AccountingTypeTest.class);

	// --- DONNÉES DE TEST (Niveau 1) ---
	private static final Integer ID = 1;
	private static final String NAME = "Patrimoine & Résultat";
	private static final String ACCOUNT_CODE = "1";
	private static final String DESCRIPTION = "Patrimoine et epargne long terme";

	// --- DONNÉES DE TEST (Niveau 2 - Array) ---
	private static final JsonArray SUB_TYPES_ARRAY = Json.createArrayBuilder()
			                                                 .add(Json.createObjectBuilder()
					                                                      .add("name", "Dettes Long Terme")
					                                                      .add("subType_Num", 3)
					                                                      .add("subType_desc", "ensemble des dettes long terme"))
			                                                 .add(Json.createObjectBuilder()
					                                                      .add("name", "Patrimoine net initial")
					                                                      .add("subType_Num", 0)
					                                                      .add("subType_desc", "ensemble des biens et dettes"))
			                                                 .build();

	private static JsonObject testDataJson;

	@BeforeAll
	@DisplayName("Setup JSON Objects for Account/Tiers Test")
	static void setUp() {
		testDataJson = Json.createObjectBuilder()
				               .add("fullLevel1", Json.createObjectBuilder()
						                                  .add("id", ID)
						                                  .add("name", NAME) // Correspond à "type" dans PCG.json
						                                  .add("accountCode", ACCOUNT_CODE)
						                                  .add("description", DESCRIPTION)
						                                  .add("subTypes", SUB_TYPES_ARRAY) // L'ARRAY EST ICI
						                                  .build())
				               .add("minimal", Json.createObjectBuilder()
						                               .add("name", NAME) // Correspond à "type" dans PCG.json
						                               .add("accountCode", ACCOUNT_CODE)
						                               .build())
				               .build();
	}

	@Test
	@Order(1)
	@DisplayName("test Empty Constructor")
	void testEmptyConstructor() {
		AccountingType type = new AccountingType();


		assertAll("Verify default state",
				() -> assertNull(type.getId(), "Id should be null"),
				() -> assertNotNull(type.getSubTypes(), "Details list should be initialized even if empty")
		);

	}

	@Test
	@Order(2)
	@DisplayName("Fluent API Test Setters")
	void testFluentApi() {
		AccountingType type = new AccountingType();
		AccountingType result = type.setId(ID)
				                           .setName(NAME)
				                           .setAccountCode(ACCOUNT_CODE)
				                           .setDescription(DESCRIPTION)
				                           .setSubTypes(SUB_TYPES_ARRAY);

		assertAll("Setters must return this check chaining value",
				() -> assertSame(result,type,"setters must return this"),
				() -> assertEquals(ID,type.getId(),"id have to been mapped"),
				() -> assertEquals(NAME,type.getName(),"name must have been mapped"),
				() -> assertEquals(ACCOUNT_CODE,type.getAccountCode(),"Account code must have been mapped"),
				() -> assertEquals(DESCRIPTION,type.getDescription(),"Description must have been mapped"),
				() -> assertEquals(SUB_TYPES_ARRAY.size(),type.getSubTypes().size(),"SubTypes must have been mapped")
				);
	}

	@Test
	@Order(3)
	@DisplayName("Test Full Json Constructor")
	void tsvFullJsonConstructor(){
		AccountingType type = new AccountingType(testDataJson.getJsonObject("fullLevel1"));
			assertAll("Json constructor test validation",
					() -> assertEquals(ID, type.getId(),"Id should be set"),
					() -> assertEquals(NAME, type.getName(),"name should be set"),
					() -> assertEquals(ACCOUNT_CODE, type.getAccountCode(),"Account code should be set"),
					() -> assertEquals(DESCRIPTION, type.getDescription(),"Description should be set"),
					() -> assertEquals(SUB_TYPES_ARRAY.size(),type.getSubTypes().size(),"SubTypes should be set")
			);
	}

	@Test
	@Order(3)
	@DisplayName("Test Constructor from json object with missing elements")
	void tsvMinimalJsonConstructor(){
		AccountingType type = new AccountingType(testDataJson.getJsonObject("minimal"));
			assertAll("Json constructor test validation",
					() -> assertEquals(NAME, type.getName(),"name should be set"),
					() -> assertEquals(ACCOUNT_CODE, type.getAccountCode(),"Account code should be set"),
					() -> assertNull(type.getId(),"Id should be null"),
					() -> assertNull(type.getDescription(),"Description should be null"),
					() -> assertTrue(type.getSubTypes().isEmpty(), "SubTypes should be initialized and empty")
			);
	}

	@Test @Order(4) @DisplayName("Serialization Test : toJson")
	void testToJson(){
		AccountingType type = new AccountingType(testDataJson.getJsonObject("fullLevel1"));
		JsonObject json = type.toJson();
		assertAll("toJson validation",
				() -> assertEquals(ID,json.getInt("id"),"Id should be serialized"),
				() -> assertEquals(NAME,json.getString("name"),"name should be serialized"),
				() -> assertEquals(ACCOUNT_CODE,json.getString("accountCode"),"Account code should be serialized"),
				() -> assertEquals(DESCRIPTION,json.getString("description"),"Description should be serialized"),
				() -> assertEquals(SUB_TYPES_ARRAY.size(),json.getJsonArray("subTypes").size(),"SubTypes should be serialized")
		);
	}

	@Test @Order(4) @DisplayName("Serialization Test : toString")
	void testToString(){
		AccountingType type = new AccountingType(testDataJson.getJsonObject("fullLevel1"));
		SubAccountingType subType = new SubAccountingType(SUB_TYPES_ARRAY.getJsonObject(0)).setParentAccountingCode(type.getAccountCode());
		String subSType = subType.toString();
		String sType = type.toString();
		
		assertAll("toString validation",
				() -> assertTrue(sType.contains("AccountingType"),"toString should contains 'AccountingType'"),
				() -> assertTrue(sType.contains(String.valueOf(ID)),"toString should contains the Id"),
				() -> assertTrue(sType.contains(NAME),"toString should contain the name"),
				() -> assertTrue(sType.contains(DESCRIPTION),"toString should contain the description"),
				() -> assertTrue(sType.contains(ACCOUNT_CODE),"toString should contain the accountCode"),
				() -> {
					String cleanSType = sType.replaceAll("\\s+", "");
					String cleanSubSType = subSType.replaceAll("\\s+", "");
					assertTrue(cleanSType.contains(cleanSubSType) , "toString should contain the subType");
				}
		);
	}
}
