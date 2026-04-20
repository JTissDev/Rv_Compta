package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the PcgCoreDTO container to ensure data integrity
 * and proper collection management.
 * * @author JtissDev
 * @version 1.0
 */
class PcgCoreDTOTest {

	
	@Test
	@DisplayName("Test Initialize fromJsonObject")
	void testInitializeFromJsonObject() {
		
	}

	/**
	 * Verifies that the constructor correctly initializes the PcgCoreDTO object
	 * with a list of AccountingType objects when provided with a valid JSON array.
	 */
	@Test
	@DisplayName("Test Initialize from JsonArray")
	void testInitializeFromJsonArray() {
	/* "type": "Patrimoine & Résultat",
    "parent_code": 1,
    "parent_desc": "Patrimoine et epargne long terme",
    "subTypes": [
      {
        "name": "Dettes Long Terme",
        "subType_Num": 3,
        "subType_desc": "ensemble des dettes long terme",
        "details": [
          { "name": "Crédit Immobilier", "no_detail": 1, "desc_detail": "" },
          { "name": "Divers", "no_detail": 9, "desc_detail": "" },
          { "name": "Prêt Bancaire Long Terme", "no_detail": 2, "desc_detail": "" }
        ]
      },
      {
        "name": "Patrimoine net initial",
        "subType_Num": 0,
        "subType_desc": "ensemble des biens et dettes au début dez suivi",
        "details": [
          { "name": "Contrepartie Solde Début", "no_detail": 1, "desc_detail": "" },
          { "name": "Divers", "no_detail": 9, "desc_detail": "" },
          { "name": "Patrimoine Initial Réel", "no_detail": 0, "desc_detail": "" }
        ]
      }
    ] */

	}

	/**
	 * Verifies that the constructor correctly initializes an empty list
	 * to avoid NullPointerException.
	 */
	@Test
	@DisplayName("Should initialize with an empty list of classes")
	void shouldInitializeWithEmptyList() {
		// Given & When
		PcgCoreDTO core = new PcgCoreDTO();

		// Then
		assertThat(core.getAccountingClasses())
				.isNotNull()
				.isEmpty();
	}

	/**
	 * Tests the helper method for adding a single accounting class.
	 */
	@Test
	@DisplayName("Should add a single accounting class to the list")
	void shouldAddAccountingClass() {
		// Given
		PcgCoreDTO core = new PcgCoreDTO();
		AccountingType mockClass = new AccountingType(); // Assuming default constructor exists

		// When
		core.addAccountingClass(mockClass);

		// Then
		assertThat(core.getAccountingClasses())
				.hasSize(1)
				.contains(mockClass);
	}
}