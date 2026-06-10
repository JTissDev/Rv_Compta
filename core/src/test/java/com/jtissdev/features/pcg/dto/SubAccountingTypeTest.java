package com.jtissdev.features.pcg.dto;

import com.jtissdev.utils.TestDataLoader;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
@DisplayName("Sub Accounting Type Test Suite")
@ExtendWith(TestResultLogger.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestGroup("pcg DTO")
public class SubAccountingTypeTest {

	private static final Logger logger = LoggerFactory.getLogger(SubAccountingTypeTest.class);

	private static final int ID = 1;
	private static final String NAME = "Compte courant";
	private static final String DESCRIPTION = "Compte courant";
	private static final Integer ACCOUNTING_CODE = 3;
	private static final String PARENT_ACCOUNTING_CODE = "50";
	private static final String FULL_CODE = "503";
	private static JsonObject testData;
	private static JsonArray detailsArray;

	@BeforeAll
	static void setUpTest() {
		testData = TestDataLoader.loadFromResources("data/pcg-dto-test-data.json")
				           .getJsonObject("SubAccountingType");
		detailsArray = testData.getJsonObject("fullAccount").getJsonArray("detailsList");
	}

	@Test
	@Order(1)
	@DisplayName("Constructor: Empty constructor should initialize list")
	void testEmptyConstructor() {
		SubAccountingType type = new SubAccountingType();
		assertAll("Verify default state",
				() -> assertNull(type.getId()),
				() -> assertNotNull(type.getDetailsList(), "Details list should be initialized even if empty")
        );
	}

	@Test
	@Order(2)
	@DisplayName("Fluent API: Setters should return 'this' and hold values")
	void testFluentApi() {
		SubAccountingType type = new SubAccountingType();
		SubAccountingType result = type.setId(ID)
				                           .setName(NAME)
				                           .setDescription(DESCRIPTION)
				                           .setAccountCode(ACCOUNTING_CODE)
				                           .setParentAccountingCode(PARENT_ACCOUNTING_CODE)
				                           .setDetailsList(detailsArray);

		assertAll("Verify chaining and values",
				() -> assertEquals(type, result, "Should return the same instance"),
				() -> assertEquals(ID, type.getId(),"Id should be set"),
				() -> assertEquals(NAME, type.getName(),"Name should be set"),
				() -> assertEquals(DESCRIPTION,type.getDescription(),"Description should be set"),
				() -> assertEquals(ACCOUNTING_CODE,type.getAccountCode(),"Accounting code should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE,type.getParentAccountingCode(),"Parent Accounting Code should be set"),
				() -> assertEquals(FULL_CODE,type.getFullCode(),"Full Code should be set"),
				() -> assertEquals(detailsArray.size(),type.getDetailsList().size(),"Details list should be set")
		);
	}

	@Test
	@Order(3)
	@DisplayName("Test Full Json Constructor")
	void testFullJsonConstructor(){
		SubAccountingType type = new SubAccountingType(testData.getJsonObject("fullAccount"));
		assertAll("Json constructor test validation",
				() -> assertEquals(ID, type.getId(),"Id should be set"),
				() -> assertEquals(NAME, type.getName(),"name should be set"),
				() -> assertEquals(DESCRIPTION, type.getDescription(),"description should be set"),
				() -> assertEquals(ACCOUNTING_CODE, type.getAccountCode(),"Code should be set"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE, type.getParentAccountingCode(),"parent code comptable should be set"),
				() -> assertEquals(FULL_CODE, type.getFullCode(),"full code should be set"),
				() -> assertEquals(detailsArray.size(),type.getDetailsList().size(),"Details list should be set")
		);
	}


	@Test
	@Order(3)
	@DisplayName("Test Json Constructor with minimal object")
	void testMinimalJsonConstructor(){
		SubAccountingType type = new SubAccountingType(testData.getJsonObject("minimal"));
			assertAll("Json constructor test validation",
					() -> assertEquals(ID, type.getId(),"Id should be set"),
					() -> assertEquals(NAME, type.getName(),"name should be set"),
					() -> assertNull(type.getDescription(),"description should be null"),
					() -> assertNull(type.getAccountCode(),"Code should be null"),
					() -> assertNull(type.getParentAccountingCode(),"parent code comptable should be null"),
					() -> assertNull(type.getFullCode(),"full code should be null"),
					() -> assertTrue(type.getDetailsList().isEmpty(), "Details list should be initialized and empty")
			);
	}

	@Test
	@Order(4)
	@DisplayName("Test Serialization toJson")
	void testToJson(){
		SubAccountingType type = new SubAccountingType(testData.getJsonObject("fullAccount"));
		JsonObject json = type.toJson();
		assertAll("toJson validation",
				() -> assertEquals(ID,json.getInt("id"),"Id should be serialized"),
				() -> assertEquals(NAME,json.getString("name"),"name should be serialized"),
				() -> assertEquals(DESCRIPTION,json.getString("description"),"description should be serialized"),
				() -> assertEquals(ACCOUNTING_CODE,json.getInt("accountCode"),"accounting code should be serialized"),
				() -> assertEquals(PARENT_ACCOUNTING_CODE,json.getString("parentAccountingCode"),"parent code comptable should be serialized"),
				() -> assertEquals(FULL_CODE,json.getString("fullCode"),"full code should be serialized"),
				() -> assertEquals(detailsArray.size(),json.getJsonArray("detailsList").size(),"details list should be serialized")
		);
	}

	@Test
	@Order(4)
	@DisplayName("Test Serialization toString")
	void testToString(){
		SubAccountingType type = new SubAccountingType(testData.getJsonObject("fullAccount"));
		String sType = type.toString();
		assertAll("toString validation",
					() -> assertTrue(sType.contains("SubAccountingType"),"toString should contain 'SubAccountingType'"),
					() -> assertTrue(sType.contains(String.valueOf(ID)  ),"toString should contain the id"),
					() -> assertTrue(sType.contains(NAME ),"toString should contain the name"),
					() -> assertTrue(sType.contains(DESCRIPTION ),"toString should contain the description"),
					() -> assertTrue(sType.contains(ACCOUNTING_CODE.toString() ),"toString should contain the accounting code"),
					() -> assertTrue(sType.contains(PARENT_ACCOUNTING_CODE ),"toString should contain the parent accounting code"),
					() -> assertTrue(sType.contains(FULL_CODE ),"toString should contain the full code")
			);
	}

}
