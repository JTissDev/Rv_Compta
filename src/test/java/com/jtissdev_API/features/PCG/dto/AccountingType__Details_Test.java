package com.jtissdev_API.features.PCG.dto;

import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link AccountingTypeDetails}.
 */
class AccountingType__Details_Test {

    // ================================================================================================================
    // Constructors Tests
    // ================================================================================================================
    @Test
    @DisplayName("Constructeur vide - tous les champs doivent être null")
    void defaultConstructor_shouldInitializeAllFieldsToNull() {
        AccountingTypeDetails details = new AccountingTypeDetails();

        assertEquals(0, details.getId(), "id doit être null");
        assertNull(details.getName(), "name doit être null");
        assertNull(details.getAccountingCode(), "codeComptable doit être null");
        assertNull(details.getDescription(), "description doit être null");
        assertNull(details.getParentCodeComptable(), "parentCodeComptable doit être null");
    }

    @Test
    @DisplayName("Constructeur sans parentCodeComptable - champs de base initialisés, parent null")
    void constructorWithoutParent_shouldInitializeBasicFieldsAndParentNull() {
        int id = 1;
        String name = "Actif";
        Integer code = 101;
        String description = "Compte d'actif";

        AccountingTypeDetails details =
                new AccountingTypeDetails(id, name, code, description);

        assertEquals(id, details.getId());
        assertEquals(name, details.getName());
        assertEquals(code, details.getAccountingCode());
        assertEquals(description, details.getDescription());
        assertNull(details.getParentCodeComptable(), "parentCodeComptable doit être null");
    }

    @Test
    @DisplayName("Constructeur complet - tous les champs doivent être initialisés")
    void fullConstructor_shouldInitializeAllFields() {
        int id = 2;
        String name = "Actif circulant";
        Integer code = 201;
        String description = "Compte d'actif circulant";
        String parentCode = "20";

        AccountingTypeDetails details =
                new AccountingTypeDetails(id, name, code, description, parentCode);

        assertEquals(id, details.getId());
        assertEquals(name, details.getName());
        assertEquals(code, details.getAccountingCode());
        assertEquals(description, details.getDescription());
        assertEquals(parentCode, details.getParentCodeComptable());
    }

    // ================================================================================================================
    // Getters and setters tests
    // ================================================================================================================
    @Test
    @DisplayName("Setters et getters - modification de tous les champs")
    void settersAndGetters_shouldUpdateAllFields() {

        int const_id = 5 ;
        String const_name = "Passif";
        Integer const_code = 1;
        String const_description = "Compte de passif";
        String const_parentCode = "30";

        AccountingTypeDetails details =
                new AccountingTypeDetails(const_id, const_name, const_code, const_description, const_parentCode);

        int id = 5 ;
        String name = "Passif";
        Integer code = 1;
        String description = "Compte de passif";
        String parentCode = "30";
        String FullCode = "301";

        details.setId(id);
        details.setName(name);
        details.setAccountingCode(code);
        details.setDescription(description);

        assertEquals(id, details.getId());
        assertEquals(name, details.getName());
        assertEquals(code, details.getAccountingCode());
        assertEquals(description, details.getDescription());
        assertEquals(parentCode, details.getParentCodeComptable());
        assertEquals(FullCode,details.getFullCode(),"Code complet est une concatenation de parent + code");
    }

    // ================================================================================================================
    // Others méthodes
    // ================================================================================================================
    /* @Test
    @DisplayName("getFullCodeComptable - sans parent")
    void getFullCodeComptable_withoutParent_shouldReturnLocalCodeOnly() {
        AccountingTypeDetails details =
                new AccountingTypeDetails(1L, "Actif", 101, "Compte d'actif");

        String fullCode = details.getFullCodeComptable();

        assertEquals("101", fullCode, "Adapter l'assert selon la logique réelle");
    } */

      @Test
    @DisplayName("toJson - doit exposer les champs fonctionnels")
    void toJson_shouldExposeExpectedFields() {
        AccountingTypeDetails details =
                new AccountingTypeDetails(11, "Actif", 1, "Compte d'actif", "10");

        JsonObject json = details.toJson();
System.out.println(json);
        assertEquals("Actif", json.getString("name"));
        assertEquals(1, json.getInt("codeComptable"));
        assertEquals("Compte d'actif", json.getString("description"));
        assertEquals("10", json.getString("parentCodeComptable"));

        // Si l'id ne doit pas être exposé :
        // assertFalse(json.containsKey("id"));
    }

    /* @Test
    @DisplayName("fromJson - doit reconstruire l'objet attendu")
    void fromJson_shouldRebuildObjectCorrectly() {
        // Construire un JSON conforme à ce que la méthode attend
        AccountingTypeDetails source =
                new AccountingTypeDetails(null, "Actif", 101, "Compte d'actif", "10");

        JsonObject json = source.toJson();

        AccountingTypeDetails rebuilt = AccountingTypeDetails.fromJson(json);

        assertNull(rebuilt.getId(), "id ne devrait pas être renseigné par fromJson (en général)");
        assertEquals("Actif", rebuilt.getName());
        assertEquals(101, rebuilt.getAccountCode());
        assertEquals("Compte d'actif", rebuilt.getDescription());
        assertEquals("10", rebuilt.getParentCodeComptable());
    } */
}