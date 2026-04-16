package com.jtissdev_API.features.PCG.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents detailed information about an accounting type.
 * This class contains information such as an identifier,
 * the type's name, description, and associated accounting code.
 *
 * The full accounting code is built by combining the optional
 * parent accounting code with the local accounting code.
 *
 * @author jtiss
 * @since 1.0.0
 * @version 1.0.1
 */
public class Type_Comptable_Details {

    // =========================================================
    // == FIELDS                                              ==
    // =========================================================

    /**
     * Technical identifier used by the database.
     *
     * @since 1.0.0
     */
    private long id;

    /**
     * Human-readable name of the accounting type.
     *
     * @since 1.0.0
     */
    private String name;

    /**
     * Local accounting code (numeric value).
     * This code is combined with the parent accounting code
     * to build the full accounting code.
     *
     * @since 1.0.0
     */
    private Integer codeComptable;

    /**
     * Human-readable description of the accounting type.
     *
     * @since 1.0.0
     */
    private String description;

    /**
     * Parent accounting code.
     * This is used as a prefix when computing the full accounting code.
     * It is meant to represent a higher-level accounting structure.
     *
     * @since 1.0.0
     */
    private String parentCodeComptable;

    // =========================================================
    // == CONSTRUCTORS                                        ==
    // =========================================================

    /**
     * Creates an empty {@code Type_Comptable_Details} instance.
     * All fields are initialized to {@code null}.
     *
     * @since 1.0.0
     */
    public Type_Comptable_Details() {
        // Default constructor
    }

    /**
     * Creates a new {@code Type_Comptable_Details} instance with basic fields.
     * The parent accounting code is not defined in this constructor.
     *
     * @param id            technical identifier used by the database
     * @param name          human-readable name
     * @param codeComptable local accounting numeric code
     * @param description   human-readable description
     *
     * @since 1.1.0
     */
    public Type_Comptable_Details(int id,
                                  String name,
                                  Integer codeComptable,
                                  String description) {
        this.id = id;
        this.name = name;
        this.codeComptable = codeComptable;
        this.description = description;
    }

    /**
     * Creates a new {@code Type_Comptable_Details} instance with
     * a parent accounting code and all other fields.
     *
     * @param id                  technical identifier used by the database
     * @param name                human-readable name
     * @param codeComptable       local accounting numeric code
     * @param description         human-readable description
     * @param parentCodeComptable parent accounting code used as prefix
     *
     * @since 1.1.0
     */
    public Type_Comptable_Details(int id,
                                  String name,
                                  Integer codeComptable,
                                  String description,
                                  String parentCodeComptable) {
        this.id = id;
        this.name = name;
        this.codeComptable = codeComptable;
        this.description = description;
        this.parentCodeComptable = parentCodeComptable;
    }

    /**
     * Constructs a new {@code Type_Comptable_Details} instance with the specified name,
     * local accounting code, description, and parent accounting code.
     *
     * @param name                 the human-readable name of the accounting type
     * @param CodeComptable        the local accounting numeric code
     * @param description          the human-readable description of the accounting type
     * @param parentCodeComptable  the parent accounting code that acts as a prefix
     *
     * @since 1.0
     */
    public Type_Comptable_Details(String name,
                                  Integer CodeComptable,
                                  String description,
                                  String parentCodeComptable) {
        this.name = name;
        this.codeComptable = CodeComptable;
        this.description = description;
        this.parentCodeComptable = parentCodeComptable;
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
    public long getId() {
        return id;
    }

    /**
     * Returns the human-readable name of the accounting type.
     *
     * @return the accounting type name
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
     * Returns the human-readable description of the accounting type.
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
     * Returns the full accounting code.
     * <p>
     * If a parent accounting code is defined, the full code is built
     * by concatenating the parent code, a separator (".") and the
     * local accounting code. If no parent is defined, only the local
     * accounting code is returned as string.
     * <p>
     * Examples (assuming codeComptable = 101):
     * <ul>
     *   <li>parentCodeComptable = "60"  → "60101"</li>
     *   <li>parentCodeComptable = null → "101"</li>
     * </ul>
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

    // =========================================================
    // == SETTERS (NO PARENT CODE MODIFICATION)               ==
    // =========================================================

    /**
     * Sets the technical identifier used by the database.
     *
     * @param id the new identifier
     *
     * @since 1.1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the human-readable name of the accounting type.
     *
     * @param name the new accounting type name
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
     * Sets the human-readable description of the accounting type.
     *
     * @param description the new description
     *
     * @since 1.1.0
     */
    public void setDescription(String description) {
        this.description = description;
    }

    // =========================================================
    // == JSON & UTILITIES                                   ==
    // =========================================================

    /**
     * Construit et renvoie un {@link JsonObject} représentant cet objet.
     * <p>
     * Les valeurs {@code null} sont encodées comme {@code null} JSON.
     *
     * @return un JsonObject représentant cette instance
     *
     * @since 1.1.0
     */
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // id
        if (false) {
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
        return "Type_Comptable_Details{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeComptable=" + codeComptable +
                ", description='" + description + '\'' +
                ", parentCodeComptable='" + parentCodeComptable + '\'' +
                '}';
    }

    public void setParentCodeComptable(String fullCode) {
        this.parentCodeComptable = fullCode;
    }
}