package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
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
	 * Test case to verify the toJson method with null id and name but non-null accountingCode.
	 */
	@Test
	void testToJsonWithNullIdAndName() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		details.setAccountingCode(7000);

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertFalse(json.containsKey("id"));
		assertFalse(json.containsKey("name"));
		assertEquals(7000, json.getInt("accountingCode"));
		assertEquals("7000", json.getString("fullCode"));
	}

	/**
	 * Test case to verify the toJson method when only name is set.
	 */
	@Test
	void testToJsonWithOnlyNameSet() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		details.setName("Equity");

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertFalse(json.containsKey("id"));
		assertEquals("Equity", json.getString("name"));
		assertFalse(json.containsKey("accountingCode"));
		assertFalse(json.containsKey("fullCode"));
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

	/**
	 * Test case to verify the toJson method when accountingCode is null but other fields are set.
	 */
	@Test
	void testToJsonWithNullAccountingCode() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		details.setId(3);
		details.setName("No Code");
		details.setDescription("No accounting code available");

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals(3, json.getInt("id"));
		assertEquals("No Code", json.getString("name"));
		assertEquals("No accounting code available", json.getString("description"));
		assertFalse(json.containsKey("accountingCode"));
		assertFalse(json.containsKey("fullCode"));
	}

	/**
	 * Test case to verify the toJson method when description contains special characters.
	 */
	@Test
	void testToJsonWithSpecialCharactersInDescription() {
		AccountingTypeDetails details = new AccountingTypeDetails();
		details.setId(4);
		details.setName("Special Desc");
		details.setAccountingCode(12345);
		details.setDescription("Description with special characters: <>!@#$%^&*()");

		JsonObject json = details.toJson();

		assertNotNull(json);
		assertEquals(4, json.getInt("id"));
		assertEquals("Special Desc", json.getString("name"));
		assertEquals(12345, json.getInt("accountingCode"));
		assertEquals("Description with special characters: <>!@#$%^&*()", json.getString("description"));
		assertEquals("12345", json.getString("fullCode"));
	}
}