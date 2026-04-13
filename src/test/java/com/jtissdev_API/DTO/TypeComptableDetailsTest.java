package com.jtissdev_API.DTO.PCG;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link Type_Comptable_Details}.
 */
class Type_Comptable_DetailsTest {

    @Test
    @DisplayName("Constructeur vide - tous les champs doivent être null")
    void defaultConstructor_shouldInitializeAllFieldsToNull() {
        Type_Comptable_Details details = new Type_Comptable_Details();

        assertNull(details.getId(), "id doit être null");
        assertNull(details.getName(), "name doit être null");
        // en supposant l'existence d'un getter getCodeComptable()
        // et getDescription(), getParentCodeComptable()
        // Adapte les noms si nécessaire
        assertNull(details.getCodeComptable(), "codeComptable doit être null");
        assertNull(details.getDescription(), "description doit être null");
        assertNull(details.getParentCodeComptable(), "parentCodeComptable doit être null");
    }

    @Test
    @DisplayName("Constructeur sans parentCodeComptable - champs de base initialisés, parent null")
    void constructorWithoutParent_shouldInitializeBasicFieldsAndParentNull() {
        Long expectedId = 1L;
        String expectedName = "Actif";
        Integer expectedCodeComptable = 101;
        String expectedDescription = "Compte d'actif";

        Type_Comptable_Details details =
                new Type_Comptable_Details(expectedId, expectedName, expectedCodeComptable, expectedDescription);

        assertEquals(expectedId, details.getId());
        assertEquals(expectedName, details.getName());
        assertEquals(expectedCodeComptable, details.getCodeComptable());
        assertEquals(expectedDescription, details.getDescription());
        assertNull(details.getParentCodeComptable(), "parentCodeComptable doit être null quand non fourni");
    }

    @Test
    @DisplayName("Constructeur complet - tous les champs doivent être initialisés")
    void fullConstructor_shouldInitializeAllFields() {
        Long expectedId = 2L;
        String expectedName = "Passif";
        Integer expectedCodeComptable = 201;
        String expectedDescription = "Compte de passif";
        String expectedParentCode = "20";

        Type_Comptable_Details details =
                new Type_Comptable_Details(
                        expectedId,
                        expectedName,
                        expectedCodeComptable,
                        expectedDescription,
                        expectedParentCode
                );

        assertAll(
                () -> assertEquals(expectedId, details.getId()),
                () -> assertEquals(expectedName, details.getName()),
                () -> assertEquals(expectedCodeComptable, details.getCodeComptable()),
                () -> assertEquals(expectedDescription, details.getDescription()),
                () -> assertEquals(expectedParentCode, details.getParentCodeComptable())
        );
    }

    // Si la classe possède une méthode pour construire un JSON (par ex. toJson()),
    // on pourra ajouter un test spécifique, par exemple :
    //
    // @Test
    // void toJson_shouldExposeExpectedFields() {
    //     Type_Comptable_Details details =
    //             new Type_Comptable_Details(1L, "Actif", 101, "Compte d'actif", "10");
    //
    //     JsonObject json = details.toJson();
    //
    //     assertEquals("Actif", json.getString("name"));
    //     assertEquals(101, json.getInt("codeComptable"));
    //     assertEquals("Compte d'actif", json.getString("description"));
    //     assertEquals("10", json.getString("parentCodeComptable"));
    //     // si l'id ne doit pas être exposé, par exemple :
    //     // assertFalse(json.containsKey("id"));
    // }
}
