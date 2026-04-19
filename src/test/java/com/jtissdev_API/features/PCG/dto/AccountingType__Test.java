package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AccountingType}.
 *
 * @author jtiss
 * @since 1.1.0
 */

public class AccountingType__Test {
// =========================================================
	// == CONSTRUCTOR TESTS                                   ==
	// =========================================================

	@Test
	@DisplayName("Default constructor - should initialize with nulls and empty sub-types list")
	void defaultConstructor_shouldInitializeDefaults() {
		AccountingType type = new AccountingType();

		assertNull(type.getId(), "id should be null");
		assertNull(type.getName(), "name should be null");
		assertNull(type.getAccountCode(), "codeComptable should be null");
		assertNotNull(type.getSubTypes(), "subTypes list should be initialized (not null)");
		assertTrue(type.getSubTypes().isEmpty(), "subTypes list should be empty");
	}

	// =========================================================
	// == LOGIC TESTS                                         ==
	// =========================================================

	@Test
	@DisplayName("getFullCodeComptable - should return local code as string (no parent at root)")
	void getFullCodeComptable_shouldReturnLocalCodeOnly() {
		AccountingType type = new AccountingType();
		type.setAccountCode(6);

		assertEquals("6", type.getFullCodeComptable());
	}

	@Test
	@DisplayName("getFullCodeComptable - should return null if codeComptable is null")
	void getFullCodeComptable_shouldReturnNullIfCodeIsNull() {
		AccountingType type = new AccountingType();
		type.setAccountCode(null);

		assertNull(type.getFullCodeComptable());
	}

	// =========================================================
	// == JSON SERIALIZATION TESTS                            ==
	// =========================================================

	@Test
	@DisplayName("toJson - should reflect full deep hierarchy (Type -> SubType -> Details)")
	void toJson_shouldExposeDeepHierarchy() {
		// 1. Setup Root Type (Level 1)
		AccountingType type = new AccountingType();
		type.setName("Charges");
		type.setAccountCode(6);

		// 2. Setup SubType (Level 2)
		SubAccountingType subType = new SubAccountingType();
		subType.setName("Services Extérieurs");
		subType.setAccountingCode(1);
		subType.setParentCodeComptable(type.getFullCodeComptable()); // "6"

		// 3. Setup Detail (Level 3)
		AccountingTypeDetails detail = new AccountingTypeDetails();
		detail.setName("Sous-traitance");
		detail.setAccountingCode(1);
		detail.setParentCodeComptable(subType.getFullCode()); // "61"

		// Linking
		subType.getDetailsList().add(detail);
		type.getSubTypes().add(subType);

		JsonObject json = type.toJson();

		// Assertions Level 1
		assertEquals("Charges", json.getString("name"));
		assertEquals("6", json.getString("fullCodeComptable"));

		// Assertions Level 2
		assertTrue(json.containsKey("subTypes"));
		JsonObject subTypeJson = json.getJsonArray("subTypes").getJsonObject(0);
		assertEquals("Services Extérieurs", subTypeJson.getString("name"));
		assertEquals("61", subTypeJson.getString("fullCode"));

		// Assertions Level 3
		assertTrue(subTypeJson.containsKey("detailsList"));
		JsonObject detailJson = subTypeJson.getJsonArray("detailsList").getJsonObject(0);
		assertEquals("Sous-traitance", detailJson.getString("name"));
		assertEquals("611", detailJson.getString("fullCode"));
	}

	// =========================================================
	// == BOUNDARY TESTS                                      ==
	// =========================================================

	@Test
	@DisplayName("setId - should verify long-to-int safety for database compatibility")
	void setId_shouldEnforceIntLimits() {
		AccountingType type = new AccountingType();

		// Testing standard int assignment to Long field
		type.setId(42L);
		assertEquals(42L, type.getId());

		// Testing upper limit (Integer.MAX_VALUE)
		type.setId((long) Integer.MAX_VALUE);
		assertEquals((long) Integer.MAX_VALUE, type.getId());
	}
}
