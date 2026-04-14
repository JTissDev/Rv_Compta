package com.jtissdev_API.features.PCG.dto;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sub accounting type (one level above detailed accounting types).
 * <p>
 * A {@code Sub_Type_Comptable} has its own identifier, name,
 * local accounting code, description and an optional parent accounting code.
 * It also contains a list of {@link Type_Comptable_Details} that belong
 * to this sub accounting type.
 *
 * The full accounting code is built by combining the optional
 * parent accounting code with the local accounting code.
 *
 * @author jtiss
 * @since 1.1.0
 * @version 1.0.0
 */
public class Sub_Type_Comptable {

    // =========================================================
    // == FIELDS                                              ==
    // =========================================================

    /**
     * Technical identifier used by the database.
     *
     * @since 1.1.0
     */
    private Long id;

    /**
     * Human-readable name of the sub accounting type.
     *
     * @since 1.1.0
     */
    private String name;

    /**
     * Local accounting code (numeric value) for this sub type.
     *
     * @since 1.1.0
     */
    private Integer codeComptable;

    /**
     * Human-readable description of the sub accounting type.
     *
     * @since 1.1.0
     */
    private String description;

    /**
     * Parent accounting code.
     * This is used as a prefix when computing the full accounting code.
     *
     * @since 1.1.0
     */
    private String parentCodeComptable;

    /**
     * List of detailed accounting types attached to this sub type.
     *
     * @since 1.1.0
     */
    private List<Type_Comptable_Details> detailsList;

    // =========================================================
    // == CONSTRUCTORS                                        ==
    // =========================================================

    /**
     * Creates an empty {@code Sub_Type_Comptable} instance.
     * All fields are initialized to {@code null}, and the list of details
     * is initialized as an empty {@link ArrayList}.
     *
     * @since 1.1.0
     */
    public Sub_Type_Comptable() {
        this.detailsList = new ArrayList<>();
    }

    /**
     * Creates a new {@code Sub_Type_Comptable} instance
     * without a parent accounting code.
     *
     * @param id            technical identifier used by the database
     * @param name          human-readable name
     * @param codeComptable local accounting numeric code
     * @param description   human-readable description
     *
     * @since 1.1.0
     */
    public Sub_Type_Comptable(Long id,
                              String name,
                              Integer codeComptable,
                              String description) {
        this.id = id;
        this.name = name;
        this.codeComptable = codeComptable;
        this.description = description;
        this.detailsList = new ArrayList<>();
    }

    /**
     * Creates a new {@code Sub_Type_Comptable} instance with
     * a parent accounting code and an optional predefined list of details.
     *
     * @param id                  technical identifier used by the database
     * @param name                human-readable name
     * @param codeComptable       local accounting numeric code
     * @param description         human-readable description
     * @param parentCodeComptable parent accounting code used as prefix
     * @param detailsList         list of details; if {@code null}, an empty list will be used
     *
     * @since 1.1.0
     */
    public Sub_Type_Comptable(Long id,
                              String name,
                              Integer codeComptable,
                              String description,
                              String parentCodeComptable,
                              List<Type_Comptable_Details> detailsList) {
        this.id = id;
        this.name = name;
        this.codeComptable = codeComptable;
        this.description = description;
        this.parentCodeComptable = parentCodeComptable;
        this.detailsList = (detailsList != null) ? detailsList : new ArrayList<>();
    }

    // =========================================================
    // == GETTERS                                             ==
    // =========================================================

    /**
     * Returns the technical identifier used by the database.
     *
     * @return the technical identifier
     *
     * @since 1.1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the human-readable name of the sub accounting type.
     *
     * @return the name
     *
     * @since 1.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the local accounting numeric code.
     *
     * @return the local accounting code
     *
     * @since 1.1.0
     */
    public Integer getCodeComptable() {
        return codeComptable;
    }

    /**
     * Returns the human-readable description of the sub accounting type.
     *
     * @return the description
     *
     * @since 1.1.0
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the parent accounting code.
     *
     * @return the parent accounting code, or {@code null} if not defined
     *
     * @since 1.1.0
     */
    public String getParentCodeComptable() {
        return parentCodeComptable;
    }

    /**
     * Returns the full accounting code of this sub type.
     * <p>
     * If a parent accounting code is defined, the full code is built
     * by concatenating the parent code, a separator (".") and the
     * local accounting code. If no parent is defined, only the local
     * accounting code is returned as string.
     *
     * @return the full accounting code, or {@code null} if
     *         the local accounting code is not defined
     *
     * @since 1.1.0
     */
    public String getFullCode() {
        if (codeComptable == null) {
            return null;
        }
        String local = codeComptable.toString();
        if (parentCodeComptable == null || parentCodeComptable.isBlank()) {
            return local;
        }
        return parentCodeComptable  + local;
    }

    /**
     * Returns the list of detailed accounting types
     * belonging to this sub type.
     *
     * @return a mutable list of {@link Type_Comptable_Details}
     *
     * @since 1.1.0
     */
    public List<Type_Comptable_Details> getDetailsList() {
        return detailsList;
    }

    // =========================================================
    // == SETTERS                                             ==
    // =========================================================

    /**
     * Sets the technical identifier used by the database.
     *
     * @param id the new identifier
     *
     * @since 1.1.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the human-readable name of the sub accounting type.
     *
     * @param name the new name
     *
     * @since 1.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the local accounting numeric code.
     *
     * @param codeComptable the new local accounting code
     *
     * @since 1.1.0
     */
    public void setCodeComptable(Integer codeComptable) {
        this.codeComptable = codeComptable;
    }

    /**
     * Sets the human-readable description of the sub accounting type.
     *
     * @param description the new description
     *
     * @since 1.1.0
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the parent accounting code.
     *
     * @param parentCodeComptable the new parent accounting code
     *
     * @since 1.1.0
     */
    public void setParentCodeComptable(String parentCodeComptable) {
        this.parentCodeComptable = parentCodeComptable;
    }

    /**
     * Replaces the current details list.
     *
     * @param detailsList new list of details; if {@code null},
     *                    an empty list will be used
     *
     * @since 1.1.0
     */
    public void setDetailsList(List<Type_Comptable_Details> detailsList) {
        this.detailsList = (detailsList != null) ? detailsList : new ArrayList<>();
    }

    // =========================================================
    // == COLLECTION HELPERS                                  ==
    // =========================================================

    /**
     * Adds a new {@link Type_Comptable_Details} to this sub type.
     * If the internal list is {@code null}, it will be initialized.
     *
     * @param details the details to add; ignored if {@code null}
     *
     * @since 1.1.0
     */
    public void addDetails(Type_Comptable_Details details) {
        if (details == null) {
            return;
        }
        if (this.detailsList == null) {
            this.detailsList = new ArrayList<>();
        }
        this.detailsList.add(details);
    }

    // =========================================================
    // == JSON & UTILITIES                                   ==
    // =========================================================

    /**
     * Construit et renvoie un {@link JsonObject} représentant ce sous-type.
     * <p>
     * Les valeurs {@code null} sont encodées comme {@code null} JSON.
     * La liste des détails est sérialisée en tableau JSON :
     * chaque élément utilise sa propre méthode {@code toJson()}.
     *
     * @return un {@link JsonObject} représentant cette instance
     *
     * @since 1.1.0
     */
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // id
        if (id == null) {
            builder.addNull("id");
        } else {
            builder.add("id", id);
        }

        // name
        if (name == null) {
            builder.addNull("name");
        } else {
            builder.add("name", name);
        }

        // codeComptable
        if (codeComptable == null) {
            builder.addNull("codeComptable");
        } else {
            builder.add("codeComptable", codeComptable);
        }

        // description
        if (description == null) {
            builder.addNull("description");
        } else {
            builder.add("description", description);
        }

        // parentCodeComptable
        if (parentCodeComptable == null) {
            builder.addNull("parentCodeComptable");
        } else {
            builder.add("parentCodeComptable", parentCodeComptable);
        }

        // fullCode (calculé)
        String fullCode = getFullCode();
        if (fullCode == null) {
            builder.addNull("fullCode");
        } else {
            builder.add("fullCode", fullCode);
        }

        // detailsList (array)
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (detailsList != null) {
            for (Type_Comptable_Details d : detailsList) {
                if (d == null) {
                    arrayBuilder.addNull();
                } else {
                    arrayBuilder.add(d.toJson());
                }
            }
        }
        builder.add("detailsList", arrayBuilder);

        return builder.build();
    }

    /**
     * Returns a string representation of this object,
     * mainly for debugging purposes.
     *
     * @return a human-readable string representation
     *
     * @since 1.1.0
     */
    @Override
    public String toString() {
        return "Sub_Type_Comptable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeComptable=" + codeComptable +
                ", description='" + description + '\'' +
                ", parentCodeComptable='" + parentCodeComptable + '\'' +
                ", detailsListSize=" + (detailsList != null ? detailsList.size() : 0) +
                '}';
    }
}