package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents a simple accounting detail.
 * <p>
 * A {@code Details_Comptable} contains:
 * <ul>
 *     <li>a technical identifier (not exposed in JSON);</li>
 *     <li>an association type (string);</li>
 *     <li>a human-readable name;</li>
 *     <li>a description;</li>
 *     <li>a letter-based accounting code.</li>
 * </ul>
 *
 * This class is designed to be a lightweight DTO for transferring
 * accounting detail information through the application layers.
 *
 * The {@link #toJson()} method provides a JSON representation that
 * excludes the technical identifier field.
 *
 * @author jtiss
 * @since 1.1.0
 * @version 1.0.0
 */
public class Details_Comptable {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Technical identifier used by the database.
	 * <p>
	 * This field is not serialized in the JSON representation.
	 *
	 * @since 1.1.0
	 */
	private Long id;

	/**
	 * Association type (functional category).
	 * <p>
	 * For example: {@code "CLIENT"}, {@code "SUPPLIER"}, {@code "INTERNAL"}, etc.
	 *
	 * @since 1.1.0
	 */
	private String type;

	/**
	 * Human-readable name of the accounting detail.
	 *
	 * @since 1.1.0
	 */
	private String name;

	/**
	 * Human-readable description of the accounting detail.
	 *
	 * @since 1.1.0
	 */
	private String description;

	/**
	 * Letter-based accounting code associated with this accounting detail.
	 * <p>
	 * This field is intended to contain an alphanumeric code
	 * primarily made of letters.
	 *
	 * @since 1.1.0
	 */
	private String code;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code Details_Comptable} instance.
	 * All fields are initialized to {@code null}.
	 *
	 * @since 1.1.0
	 */
	public Details_Comptable() {
		// Default constructor
	}

	/**
	 * Creates a fully initialized {@code Details_Comptable} instance.
	 *
	 * @param id          technical identifier used by the database
	 * @param type        association type
	 * @param name        human-readable name
	 * @param description human-readable description
	 * @param code        letter-based accounting code
	 *
	 * @since 1.1.0
	 */
	public Details_Comptable(Long id,
	                         String type,
	                         String name,
	                         String description,
	                         String code) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
		this.code = code;
	}

	// =========================================================
	// == ACCESSORS & MUTATORS                                ==
	// =========================================================

	/**
	 * Returns the technical identifier.
	 *
	 * @return the technical identifier, or {@code null} if not set
	 *
	 * @since 1.1.0
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the technical identifier.
	 *
	 * @param id new technical identifier
	 *
	 * @since 1.1.0
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the association type.
	 *
	 * @return the association type, or {@code null} if not set
	 *
	 * @since 1.1.0
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the association type.
	 *
	 * @param type new association type
	 *
	 * @since 1.1.0
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the human-readable name of the accounting detail.
	 *
	 * @return the name, or {@code null} if not set
	 *
	 * @since 1.1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the human-readable name of the accounting detail.
	 *
	 * @param name new name
	 *
	 * @since 1.1.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the description of the accounting detail.
	 *
	 * @return the description, or {@code null} if not set
	 *
	 * @since 1.1.0
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the accounting detail.
	 *
	 * @param description new description
	 *
	 * @since 1.1.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the letter-based accounting code.
	 *
	 * @return the letter-based code, or {@code null} if not set
	 *
	 * @since 1.1.0
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the letter-based accounting code.
	 *
	 * @param code new letter-based code
	 *
	 * @since 1.1.0
	 */
	public void setCode(String code) {
		this.code = code;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Builds and returns a {@link JsonObject} representing this accounting detail.
	 * <p>
	 * Only non-null scalar fields are included in the JSON object.
	 * The technical identifier field ({@code id}) is intentionally omitted.
	 *
	 * @return a {@link JsonObject} representing this instance
	 *
	 * @since 1.1.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		// The id field is intentionally not serialized

		if (type != null) {
			builder.add("type", type);
		}
		if (name != null) {
			builder.add("name", name);
		}
		if (description != null) {
			builder.add("description", description);
		}
		if (code != null) {
			builder.add("code", code);
		}

		return builder.build();
	}

	// =========================================================
	// == UTILITY METHODS                                     ==
	// =========================================================

	/**
	 * Returns a string representation of this instance,
	 * mainly intended for debugging and logging purposes.
	 *
	 * @return a string representing this {@code Details_Comptable}
	 *
	 * @since 1.1.0
	 */
	@Override
	public String toString() {
		return "Details_Comptable{" +
				       "id=" + id +
				       ", type='" + type + '\'' +
				       ", name='" + name + '\'' +
				       ", description='" + description + '\'' +
				       ", code='" + code + '\'' +
				       '}';
	}
}