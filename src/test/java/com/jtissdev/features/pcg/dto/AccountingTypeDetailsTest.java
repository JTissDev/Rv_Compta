package com.jtissdev.features.pcg.dto;

import com.jtissdev.utils.TestDataLoader;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountingTypeDetails Test Suite")
@ExtendWith(TestResultLogger.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestGroup("pcg DTO")
public class AccountingTypeDetailsTest {

	private static final Logger logger = LoggerFactory.getLogger(AccountingTypeDetailsTest.class);

	private static final Integer ID = 1;
	private static final String NAME = "Revenue";
	private static final Integer ACCOUNTING_CODE = 4000;
	private static final String DESCRIPTION = "Revenue Description";
	private static final String PARENT_ACCOUNTING_CODE = "40";
	private static final String FULL_CODE = "404000";

	private static JsonObject testData;

	@BeforeAll
	static void setUpTest() {
		// Chargement propre depuis le fichier JSON externe
		testData = TestDataLoader.loadFromResources("data/pcg-dto-test-data.json")
				           .getJsonObject("AccountingTypeDetails");
	}

	@Test
	@Order(1)
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails();

		assertAll("Empty constructor test validation",
				() -> assertNull(accountingTypeDetails.getId(),"Id should be null"),
				() -> assertNull(accountingTypeDetails.getName(),"name should be null"),
				() -> assertNull(accountingTypeDetails.getAccountCode(),"Code should be null"),
				() -> assertNull(accountingTypeDetails.getDescription(),"description should be null"),
				() -> assertNull(accountingTypeDetails.getParentAccountingCode(),"parent code comptable should be null")
		);
	}

	@Test
	@Order(2)
	@DisplayName("Test Fluent Setters")
	void testFluentSetters() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails();

		AccountingTypeDetails result = accountingTypeDetails
				                               .setId(ID)
				                               .setName(NAME)
				                               .setAccountCode(ACCOUNTING_CODE)
				                               .setParentAccountingCode(PARENT_ACCOUNTING_CODE)
				                               .setDescription(DESCRIPTION);

		assertAll("Setters validation",
				() -> assertSame(accountingTypeDetails,result,"Setters must return the same instance"),
				() -> assertEquals(ID,accountingTypeDetails.getId(),"Id should be set"),
				() -> assertEquals(NAME,accountingTypeDetails.getName(),"name should be set"),
				() -> assertEquals(ACCOUNTING_CODE,accountingTypeDetails.getAccountCode(),"accounting code should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE, accountingTypeDetails.getParentAccountingCode(),"Parent accounting code should be set"),
				() -> assertEquals(DESCRIPTION, accountingTypeDetails.getDescription(),"description should be set"),
				() -> assertEquals(FULL_CODE, accountingTypeDetails.getFullCode(),"full code should be set")
		);
	}

	@Test
	@Order(3)
	@DisplayName("Test Full Json Constructor")
	void testJsonConstructor() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(testData.getJsonObject("allFields"));
		assertAll("Json constructor test validation",
				() -> assertEquals(ID, accountingTypeDetails.getId(),"Id should be set"),
				() -> assertEquals(NAME, accountingTypeDetails.getName(),"name should be set"),
				() -> assertEquals(ACCOUNTING_CODE, accountingTypeDetails.getAccountCode(),"Code should be set"),
				() -> assertEquals(DESCRIPTION, accountingTypeDetails.getDescription(),"description should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE, accountingTypeDetails.getParentAccountingCode(),"parent code comptable should be set"),
				() -> assertEquals(FULL_CODE, accountingTypeDetails.getFullCode(),"full code should be set")
		);
	}

	@Test
	@Order(3)
	@DisplayName("Test name Only Json Constructor")
	void testNameOnlyJsonConstructor() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(testData.getJsonObject("nameOnly"));

		assertAll("Json constructor mapping validation with non existing parameters",
				() -> assertEquals(NAME,accountingTypeDetails.getName(),"name must be mapped"),
				() -> assertNull(accountingTypeDetails.getId(),"non existing id should'nt be mapped"),
				() -> assertNull(accountingTypeDetails.getAccountCode(),"Non existing AccountingCode  should'nt be mapped."),
				() -> assertNull(accountingTypeDetails.getDescription(),"Non existing Description  should'nt be mapped."),
				() -> assertNull(accountingTypeDetails.getParentAccountingCode(),"Non existing ParentAccountingCode should'nt be mapped."),
				() -> assertNull(accountingTypeDetails.getFullCode(),"Non existing FullCode  should'nt be mapped.")
				);
	}



	@Test
	@Order(4)
	@DisplayName("toJson: should only include non-null fields")
	void testToJsonSerialization() {
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(testData.getJsonObject("nameAndAccountingCode"));
		JsonObject json = accountingTypeDetails.toJson();
		assertAll("toJson validation",
				() -> assertFalse(json.containsKey("id"),"Null id should be omitted"),
				() -> assertEquals(NAME,json.getString("name"),"name should be serialized"),
				() -> assertEquals(ACCOUNTING_CODE,json.getInt("accountCode"),"accounting code should be serialized"),
				() -> assertFalse(json.containsKey("description"),"Null description should be omitted"),
				() -> assertFalse(json.containsKey("parentAccountingCode"),"Null parent code comptable should be omitted"),
				() -> assertFalse(json.containsKey("fullCode"),"No Parent accounting code should be ommited"),
				() -> assertEquals(2,json.size(),"JSON should contain exactly 2 keys")
		);
	}

	@Test
	@Order(4)
	@DisplayName("toString : must have required format")
	void testToStringSerialization(){
		AccountingTypeDetails accountingTypeDetails = new AccountingTypeDetails(testData.getJsonObject("allFields"));


	}

}