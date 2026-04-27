package com.jtissdev_API.features.PCG.dto;

import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountingTypeDetails Test Suite")
@ExtendWith(TestResultLogger.class)
class AccountingTypeDetailsTest {

	private static final Logger logger = LoggerFactory.getLogger(AccountingTypeDetailsTest.class);

	private static final Integer ID = 1;
	private static final String NAME = "Revenue";
	private static final String ACCOUNTING_CODE = "4000";
	private static final String DESCRIPTION = "Revenue Description";
	private static final String PARENT_ACCOUNTING_CODE = "40";
	private static final String FULL_CODE = "404000";

	private static JsonObject accountingTypeDetailsJson;

	@BeforeAll
	@DisplayName("Setup Object for Accounting ")
	static void setUpTest() {
		accountingTypeDetailsJson = Json.createObjectBuilder()
				                            .add("idOnly", Json.createObjectBuilder()
						                                           .add("id", ID).build())
				                            .add("nameOnly", Json.createObjectBuilder()
						                                             .add("name", NAME).build())
				                            .add("accountingCodeOnly", Json.createObjectBuilder()
						                                                       .add("accountingCode", ACCOUNTING_CODE).build())
				                            .add("descriptionOnly", Json.createObjectBuilder()
						                                                    .add("description", DESCRIPTION).build())
				                            .add("parentAccountingCodeOnly", Json.createObjectBuilder()
						                                                            .add("parentAccountingCode", PARENT_ACCOUNTING_CODE).build())
				                            .add("fullCodeOnly", Json.createObjectBuilder()
						                                                 .add("fullCode", FULL_CODE).build())
				                            .add("idAndName", Json.createObjectBuilder()
						                                              .add("id", ID)
						                                              .add("name", NAME).build())
				                            .add("nameAndAccountingCode", Json.createObjectBuilder()
						                                                          .add("name", NAME)
						                                                          .add("accountingCode", ACCOUNTING_CODE).build())
				                            .add("allFields", Json.createObjectBuilder()
						                                              .add("id", ID)
						                                              .add("name", NAME)
						                                              .add("accountingCode", ACCOUNTING_CODE)
						                                              .add("description", DESCRIPTION)
						                                              .add("parentAccountingCode", PARENT_ACCOUNTING_CODE)
						                                              .add("fullCode", FULL_CODE).build())
				                            .build();
	}

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails();

		assertAll("Empty constructor test validation",
				() -> assertNull(accountingTypeDetails.getId(),"Id should be null"),
				() -> assertNull(accountingTypeDetails.getName(),"name should be null"),
				() -> assertNull(accountingTypeDetails.getAccountingCode(),"Code should be null"),
				() -> assertNull(accountingTypeDetails.getDescription(),"description should be null"),
				() -> assertNull(accountingTypeDetails.getParentAccountingCode(),"parent code comptable should be null")
		);
	}

	@Test
	@DisplayName("Test Json Constructor")
	void testJsonConstructor() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(accountingTypeDetailsJson.getJsonObject("allFields"));
		assertAll("Json constructor test validation",
				() -> assertEquals(ID, accountingTypeDetails.getId(),"Id should be set"),
				() -> assertEquals(NAME, accountingTypeDetails.getName(),"name should be set"),
				() -> assertEquals(ACCOUNTING_CODE, accountingTypeDetails.getAccountingCode(),"Code should be set"),
				() -> assertEquals(DESCRIPTION, accountingTypeDetails.getDescription(),"description should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE, accountingTypeDetails.getParentAccountingCode(),"parent code comptable should be set"),
				() -> assertEquals(FULL_CODE, accountingTypeDetails.getFullCode(),"full code should be set")
		);
	}

	@Test
	@DisplayName("Test Fluent Setters")
	void testFluentSetters() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails();

		AccountingTypeDetails result = accountingTypeDetails
				                               .setId(ID)
				                               .setName(NAME)
				                               .setAccountingCode(ACCOUNTING_CODE)
				                               .setParentAccountingCode(PARENT_ACCOUNTING_CODE)
				                               .setDescription(DESCRIPTION);

		assertAll("Setters validation",
				() -> assertSame(accountingTypeDetails,result,"Setters must return the same instance"),
				() -> assertEquals(ID,accountingTypeDetails.getId(),"Id should be set"),
				() -> assertEquals(NAME,accountingTypeDetails.getName(),"name should be set"),
				() -> assertEquals(ACCOUNTING_CODE,accountingTypeDetails.getAccountingCode(),"accounting code should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE, accountingTypeDetails.getParentAccountingCode(),"Parent accounting code should be set"),
				() -> assertEquals(DESCRIPTION, accountingTypeDetails.getDescription(),"description should be set"),
				() -> assertEquals(FULL_CODE, accountingTypeDetails.getFullCode(),"full code should be set")
				);
	}

	@Test
	@DisplayName("toJson: should only include non-null fields")
	void testToJsonSerialization() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(accountingTypeDetailsJson.getJsonObject("nameAndAccountingCode"));
		JsonObject json = accountingTypeDetails.toJson();
		assertAll("toJson validation",
				() -> assertFalse(json.containsKey("id"),"Null id should be omitted"),
				() -> assertEquals(NAME,json.getString("name"),"name should be serialized"),
				() -> assertEquals(ACCOUNTING_CODE,json.getString("accountingCode"),"accounting code should be serialized"),
				() -> assertFalse(json.containsKey("description"),"Null description should be omitted"),
				() -> assertFalse(json.containsKey("parentAccountingCode"),"Null parent code comptable should be omitted"),
				() -> assertEquals(ACCOUNTING_CODE,json.getString("fullCode"),"No Parent accounting code so full code should be the same as accounting code"),
				() -> assertEquals(3,json.size(),"JSON should contain exactly 3 keys")
		);
	}


}