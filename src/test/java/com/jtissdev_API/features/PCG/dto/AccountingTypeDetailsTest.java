package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for validating the behavior and output of the `toJson` method in
 * the `AccountingTypeDetails` class.
 *
 * This class contains multiple test cases to ensure that the `toJson` method correctly
 * converts the state of an `AccountingTypeDetails` object into a JSON representation.
 * Each test case targets specific scenarios such as all fields being provided, optional
 * fields being missing, or specific fields being null or blank.
 */
class AccountingTypeDetailsTest {

	// =========================================================
	// == CONSTRUCTORS (TEST CASES)                           ==
	// =========================================================
	/**
	 * Test class to validate the `toJson` method of the `AccountingTypeDetails` class.
	 * The `toJson` method is expected to convert the current object state into a JsonObject.
	 */

	@Test
	@DisplayName("Test Empty Constructor")
	void testEmptyConstructor() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		 JsonObject result = details.toJson();
		 assertNotNull(result);
		 assertTrue(result.isEmpty());
	}
	
	@Test
	@DisplayName("Test Constructor from JsonObject")
	void testConstructorFromJsonObject() {
		// Arrange
		JsonObject json = jakarta.json.Json.createObjectBuilder()
				                  .add("id", 10)
				                  .add("name", "Revenue")
				                  .add("accountingCode", 500)
				                  .add("description", "Monthly income")
				                  .add("parentCodeComptable", "PCG500")
				                  .build();

		// Act
		AccountingTypeDetails details = new AccountingTypeDetails(json);

		// Assert
		assertNotNull(details);
		assertEquals(10, details.getId());
		assertEquals("Revenue", details.getName());
		assertEquals(500, details.getAccountingCode());
		assertEquals("Monthly income", details.getDescription());
		assertEquals("PCG500", details.getParentCodeComptable());
	}

	@Test
	@DisplayName("Test Constructor with All Fields")
	void testToJson_AllFieldsPresent() {
		// Arrange
		AccountingTypeDetails details = new AccountingTypeDetails(
				1,
				"Revenue",
				1000,
				"Monthly revenue",
				"PCG"
		);

		// Act
		JsonObject result = details.toJson();

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getInt("id"));
		assertEquals("Revenue", result.getString("name"));
		assertEquals(1000, result.getInt("accountingCode"));
		assertEquals("Monthly revenue", result.getString("description"));
		assertEquals("PCG", result.getString("parentCodeComptable"));
		assertEquals("PCG1000", result.getString("fullCode"));
	}

	/* @Test
	void testToJson_OptionalFieldsMissing() {
		// Arrange
		AccountingTypeDetails details = new AccountingTypeDetails(
				null,
				"Expenses",
				2000,
				null,
				null
		);

		// Act
		JsonObject result = details.toJson();

		// Assert
		assertNotNull(result);
		assertFalse(result.containsKey("id"));
		assertEquals("Expenses", result.getString("name"));
		assertEquals(2000, result.getInt("accountingCode"));
		assertFalse(result.containsKey("description"));
		assertFalse(result.containsKey("parentCodeComptable"));
		assertEquals("2000", result.getString("fullCode"));
	} */

	@Test
	@DisplayName("Test toJson with null fields")
	void testToJson_NoFieldsPresent() {
		// Arrange
		AccountingTypeDetails details = new AccountingTypeDetails();

		// Act
		JsonObject result = details.toJson();

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	/* @Test
	void testToJson_ParentCodeBlank() {
		// Arrange
		AccountingTypeDetails details = new AccountingTypeDetails(
				3,
				"Assets",
				3000,
				"Company assets",
				""
		);

		// Act
		JsonObject result = details.toJson();

		// Assert
		assertNotNull(result);
		assertEquals(3, result.getInt("id"));
		assertEquals("Assets", result.getString("name"));
		assertEquals(3000, result.getInt("accountingCode"));
		assertEquals("Company assets", result.getString("description"));
		assertFalse(result.containsKey("parentCodeComptable"));
		assertEquals("3000", result.getString("fullCode"));
	} */

	// =========================================================
	// == CONSTRUCTORS (TEST CASES)                           ==
	// =========================================================

	@Test
	@DisplayName("Test all Setters")
	void testSetters() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		 details.setId(10);
		 details.setName("Revenue");
		 details.setAccountingCode(500);
		 details.setDescription("Monthly income");
		 details.setParentCodeComptable("PCG500");

		 JsonObject result = details.toJson();
		 assertNotNull(result);
		 assertEquals(10, result.getInt("id"));
	}

	@Test
	@DisplayName("Test toJson with null accountingCode")
	void testToJson_NullAccountingCode() {
		// Arrange
		AccountingTypeDetails details = new AccountingTypeDetails(
				4,
				"Liabilities",
				null,
				"Corporate liabilities",
				"PCG"
		);

		// Act
		JsonObject result = details.toJson();

		// Assert
		assertNotNull(result);
		assertEquals(4, result.getInt("id"));
		assertEquals("Liabilities", result.getString("name"));
		assertFalse(result.containsKey("accountingCode"));
		assertEquals("Corporate liabilities", result.getString("description"));
		assertEquals("PCG", result.getString("parentCodeComptable"));
		assertFalse(result.containsKey("fullCode"));
	}


}