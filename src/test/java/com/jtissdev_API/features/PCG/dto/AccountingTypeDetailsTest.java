package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountingTypeDetailsTest {

	/**
	 * Test case to verify the toJson method correctly serializes all
	 * attributes when all fields are non-null.
	 */
	@Test
	void testToJsonWithAllFieldsNonNull() {
		AccountingTypeDetails details = new AccountingTypeDetails(
				1,
				"Revenue",
				4000,
				"Revenue Description",
				"40"
		);

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals(1, json.getInt("id"));
		assertEquals("Revenue", json.getString("name"));
		assertEquals(4000, json.getInt("accountingCode"));
		assertEquals("Revenue Description", json.getString("description"));
		assertEquals("40", json.getString("parentCodeComptable"));
		assertEquals("404000", json.getString("fullCode"));
	}

	/**
	 * Test case to verify the toJson method when optional fields are null.
	 */
	@Test
	void testToJsonWithSomeFieldsNull() {
		AccountingTypeDetails details = new AccountingTypeDetails(
				2,
				"Expense",
				5000,
				null,
				null
		);

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals(2, json.getInt("id"));
		assertEquals("Expense", json.getString("name"));
		assertEquals(5000, json.getInt("accountingCode"));
		assertFalse(json.containsKey("description"));
		assertFalse(json.containsKey("parentCodeComptable"));
		assertEquals("5000", json.getString("fullCode"));
	}

	/**
	 * Test case to verify the toJson method handles completely null fields properly.
	 */
	@Test
	void testToJsonWithAllFieldsNull() {
		AccountingTypeDetails details = new AccountingTypeDetails();

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertFalse(json.containsKey("id"));
		assertFalse(json.containsKey("name"));
		assertFalse(json.containsKey("accountingCode"));
		assertFalse(json.containsKey("description"));
		assertFalse(json.containsKey("parentCodeComptable"));
		assertFalse(json.containsKey("fullCode"));
	}

	/**
	 * Test case to verify the fullCode field when parentCodeComptable is provided.
	 */
	@Test
	void testToJsonFullCodeWithParentCode() {
		AccountingTypeDetails details = new AccountingTypeDetails(
				"Assets",
				1000,
				"Assets Description",
				"10"
		);

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals("101000", json.getString("fullCode"));
	}

	/**
	 * Test case to verify the fullCode field when parentCodeComptable is not provided.
	 */
	@Test
	void testToJsonFullCodeWithoutParentCode() {
		AccountingTypeDetails details = new AccountingTypeDetails(
				"Liabilities",
				2000,
				"Liabilities Description",
				null
		);

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals("2000", json.getString("fullCode"));
	}
}