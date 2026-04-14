package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for {@link Sub_Type_Comptable}.
 * * @author jtiss
 * @since 1.1.0
 */
public class Sub_Type_Comptable_Test {

	// =========================================================
	// == CONSTRUCTOR TESTS                                   ==
	// =========================================================

	@Test
	@DisplayName("Default constructor - should initialize with nulls and empty list")
	void defaultConstructor_shouldInitializeDefaults() {
		Sub_Type_Comptable subType = new Sub_Type_Comptable();

		assertNull(subType.getId(), "id should be null");
		assertNull(subType.getName(), "name should be null");
		assertNull(subType.getCodeComptable(), "codeComptable should be null");
		assertNotNull(subType.getDetailsList(), "detailsList should be initialized (not null)");
		assertTrue(subType.getDetailsList().isEmpty(), "detailsList should be empty");
	}

	// =========================================================
	// == LOGIC TESTS                                         ==
	// =========================================================

	@Test
	@DisplayName("getFullCode - should concatenate parent and local code")
	void getFullCode_shouldConcatenateCorrectly() {
		Sub_Type_Comptable subType = new Sub_Type_Comptable();
		subType.setParentCodeComptable("61");
		subType.setCodeComptable(3); // Result should be "613"

		assertEquals("613", subType.getFullCode());
	}

	@Test
	@DisplayName("getFullCode - should return local code if parent is null")
	void getFullCode_shouldHandleNullParent() {
		Sub_Type_Comptable subType = new Sub_Type_Comptable();
		subType.setParentCodeComptable(null);
		subType.setCodeComptable(70);

		assertEquals("70", subType.getFullCode());
	}

	// =========================================================
	// == JSON SERIALIZATION TESTS                            ==
	// =========================================================

	@Test
	@DisplayName("toJson - should contain all functional fields and nested details")
	void toJson_shouldExposeExpectedStructure() {
		// Setup SubType
		Sub_Type_Comptable subType = new Sub_Type_Comptable();
		subType.setName("Services");
		subType.setCodeComptable(61);
		subType.setParentCodeComptable(null);

		// Add a Detail
		Type_Comptable_Details detail = new Type_Comptable_Details();
		detail.setName("Rental");
		detail.setCodeComptable(3);

		List<Type_Comptable_Details> details = new ArrayList<>();
		details.add(detail);
		subType.setDetailsList(details);

		JsonObject json = subType.toJson();

		// Check scalar fields
		assertEquals("Services", json.getString("name"));
		assertEquals(61, json.getInt("codeComptable"));
		assertEquals("61", json.getString("fullCode"));

		// Check nested array
		assertTrue(json.containsKey("detailsList"), "JSON should contain detailsList array");
		assertEquals(1, json.getJsonArray("detailsList").size());
		assertEquals("Rental", json.getJsonArray("detailsList").getJsonObject(0).getString("name"));
	}

	// =========================================================
	// == BOUNDARY TESTS                                      ==
	// =========================================================

	@Test
	@DisplayName("setId - should allow null but enforce Integer limits via int parameter")
	void setId_shouldWorkWithIntParameter() {
		Sub_Type_Comptable subType = new Sub_Type_Comptable();

		// Initial state
		assertNull(subType.getId());

		// Standard assignment
		subType.setId(100L);
		assertEquals(100L, subType.getId());

		// Boundary check (Max Int)
		subType.setId((long) Integer.MAX_VALUE);
		assertEquals((long) Integer.MAX_VALUE, subType.getId());
	}
}

