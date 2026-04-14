package com.jtissdev_API.DTO.PCG;

import com.jtissdev_API.features.core.Pcg;
import com.jtissdev_API.features.PCG.dto.Sub_Type_Comptable;
import com.jtissdev_API.features.PCG.dto.Type_Comptable;
import com.jtissdev_API.features.PCG.dto.Type_Comptable_Details;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Pcg}.
 * * @author jtiss
 * @since 1.2.0
 */
class PcgTest {

	// =========================================================
	// == CONSTRUCTOR TESTS                                   ==
	// =========================================================

	@Test
	@DisplayName("Default constructor - should initialize empty lists and maps")
	void defaultConstructor_shouldInitializeEmptyState() {
		Pcg pcg = new Pcg();

		assertNotNull(pcg.getTypesComptables(), "typesComptables list should not be null");
		assertTrue(pcg.getTypesComptables().isEmpty(), "typesComptables should be empty");
		assertNull(pcg.getByCode("ANY"), "Index should be empty and return null");
	}

	// =========================================================
	// == INDEXING & SEARCH TESTS                             ==
	// =========================================================

	@Test
	@DisplayName("buildIndex - should map deep details to their full codes")
	void buildIndex_shouldCreateCorrectMapping() {
		Pcg pcg = new Pcg();

		// Setup a deep structure: 6 (Type) -> 61 (SubType) -> 613 (Detail)
		Type_Comptable type = new Type_Comptable();
		type.setCodeComptable(6);

		Sub_Type_Comptable subType = new Sub_Type_Comptable();
		subType.setCodeComptable(1);
		subType.setParentCodeComptable("6");

		Type_Comptable_Details detail = new Type_Comptable_Details();
		detail.setName("Rent");
		detail.setCodeComptable(3);
		detail.setParentCodeComptable("61");

		subType.getDetailsList().add(detail);
		type.getSubTypes().add(subType);
		pcg.getTypesComptables().add(type);

		// Execute indexing
		pcg.buildIndex();

		// Verification
		Type_Comptable_Details found = pcg.getByCode("613");
		assertNotNull(found, "Detail should be found via fullCode '613'");
		assertEquals("Rent", found.getName());
	}

	@Test
	@DisplayName("searchByName - should find elements using case-insensitive fragments")
	void searchByName_shouldReturnMatchingElements() {
		Pcg pcg = new Pcg();

		// Manual insertion into a mock index for testing the search logic
		Type_Comptable_Details d1 = new Type_Comptable_Details();
		d1.setName("Banking Services");
		d1.setParentCodeComptable("62");
		d1.setCodeComptable(7); // 627

		// We simulate buildIndex behavior by adding it to a structure and indexing
		Sub_Type_Comptable st = new Sub_Type_Comptable();
		st.getDetailsList().add(d1);
		Type_Comptable t = new Type_Comptable();
		t.getSubTypes().add(st);
		pcg.getTypesComptables().add(t);

		pcg.buildIndex();

		// Test search
		List<Type_Comptable_Details> results = pcg.searchByName("BANK");

		assertFalse(results.isEmpty(), "Should find at least one result for 'BANK'");
		assertEquals("Banking Services", results.get(0).getName());
	}

	// =========================================================
	// == JSON SERIALIZATION TESTS                            ==
	// =========================================================

	@Test
	@DisplayName("toJson - should return a valid root JsonObject")
	void toJson_shouldReturnValidStructure() {
		Pcg pcg = new Pcg();
		Type_Comptable type = new Type_Comptable();
		type.setName("Assets");
		pcg.getTypesComptables().add(type);

		JsonObject json = pcg.toJson();

		assertNotNull(json);
		assertTrue(json.containsKey("typesComptables"), "JSON should have 'typesComptables' key");
		assertEquals(1, json.getJsonArray("typesComptables").size());
		assertEquals("Assets", json.getJsonArray("typesComptables").getJsonObject(0).getString("name"));
	}

	@Test
	@DisplayName("buildIndex - should handle null elements gracefully")
	void buildIndex_shouldHandleNulls() {
		Pcg pcg = new Pcg();
		pcg.getTypesComptables().add(null); // Add a null to test robustness

		assertDoesNotThrow(pcg::buildIndex, "buildIndex should not crash on null elements");
	}
}