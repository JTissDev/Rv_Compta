package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents a specific accounting detail (Level 4) in the PCP block.
 * <p>
 * This class identifies specific objects like bank accounts, vehicles,
 * or properties, linked to a Level 3 nature.
 * </p>
 *
 * @author J.Tiss
 * @version 1.1.0
 * @since 0.1
 */
public class AnalyticDetail {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Unique alphanumeric code for the detail (e.g., ".442").
	 *
	 * @since 0.1
	 */
	private String code;

	/**
	 * Category or functional group of the detail (e.g., "Banque", "Vehicule").
	 *
	 * @since 0.1
	 */
	private String type;

	/**
	 * Display name or label for the specific object.
	 *
	 * @since 0.1
	 */
	private String name;

	/**
	 * Additional notes or technical description of the object.
	 *
	 * @since 0.1
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 *
	 * @since 0.1
	 */
	public AnalyticDetail() {
	}

	/**
	 * Constructor that initializes an {@code AnalyticDetail} instance using data from a {@code JsonObject}.
	 * Populates the fields of the object based on the corresponding keys in the JSON object.
	 *
	 * @param json
	 *        the {@code JsonObject} containing the data to initialize the {@code AnalyticDetail}.
	 *        The keys expected include "Code", "Type", "Name", and "Description".
	 *
	 * @since 0.4
	 */
	public AnalyticDetail(JsonObject json) {
		this();
		if (json.containsKey("code")) {
			this.setCode(json.getString("code", null));
		}
		if (json.containsKey("type")) {
			this.setType(json.getString("type",null));
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name",null));
		}
		if (json.containsKey("description")) {
			this.setDescription(json.getString("description",null));
		}
	}

	/**
	 * Functional constructor for quick initialization of mandatory fields.
	 *
	 * @param code
	 * 		Unique code of the detail
	 * @param type
	 * 		Category of the detail
	 * @param name
	 * 		Display name of the detail
	 * @since 0.1
	 */
	public AnalyticDetail(String code, String type, String name) {
		this.setCode(code);
		this.setType(type);
		this.setName(name);
	}

	/**
	 * Complete constructor for full initialization of all fields.
	 *
	 * @param code
	 * 		Unique code of the detail
	 * @param type
	 * 		Category of the detail
	 * @param name
	 * 		Display name of the detail
	 * @param description
	 * 		Technical description of the object
	 * @since 0.2
	 */
	public AnalyticDetail(String code, String type, String name, String description) {
		this(code, type, name);
		this.setDescription(description);
	}

	// =========================================================
	// == GETTERS / SETTERS (FLUENT API)                      ==
	// =========================================================

	/**
	 * Gets the unique alphanumeric code.
	 *
	 * @return the detail code.
	 *
	 * @since 0.1
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the unique alphanumeric code.
	 *
	 * @param code
	 * 		the code to assign.
	 * @since 0.1
	 */
	public AnalyticDetail setCode(String code) {
		this.code = code;
		return this;
	}

	/**
	 * Gets the category/type of the detail.
	 *
	 * @return the type string.
	 *
	 * @since 0.1
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the category/type of the detail.
	 *
	 * @param type
	 * 		the type label to assign.
	 * @since 0.1
	 */
	public AnalyticDetail setType(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Gets the display name of the detail.
	 *
	 * @return the name/label.
	 *
	 * @since 0.1
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the display name of the detail.
	 *
	 * @param name
	 * 		the label to assign.
	 * @since 0.1
	 */
	public AnalyticDetail setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Gets the additional notes for this detail.
	 *
	 * @return the description text.
	 *
	 * @since 0.1
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the additional notes for this detail.
	 *
	 * @param description
	 * 		the information to store.
	 * @since 0.1
	 */
	public AnalyticDetail setDescription(String description) {
		this.description = description;
		return this;
	}

	// =========================================================
	// == SERIALIZATION LOGIC                                 ==
	// =========================================================

	/**
	 * Converts the object into a JSON format for persistence.
	 *
	 * @return A {@link JsonObject} representing the detail.
	 *
	 * @since 0.1
	 * @version 1.1
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		if (this.code != null) {
			builder.add("code", code);
		}
		if (this.type != null) {
			builder.add("type", type);
		}
		if (this.name != null) {
			builder.add("name", name);
		}
		if (this.description != null) {
			builder.add("description", description);
		}

		return builder.build();
	}

	/**
	 * Provides a summary of the detail for logging and debugging.
	 *
	 * @return a formatted string.
	 *
	 * @since 0.1
	 */
	@Override
	public String toString() {
		return "Analytic Detail [" +
				       "code=" + code +
				       ", type=" + type +
				       ", name=" + name +
				       ", description=" + description +
				       "]";
	}


	/**
	 * Converts a {@link JsonObject} into an instance of {@code AnalyticDetail}.
	 * Populates the fields of the object based on the corresponding keys in the JSON object.
	 *
	 * @param json
	 * 		the {@code JsonObject} containing the data to initialize the {@code AnalyticDetail}.
	 * 		The keys expected include "Code", "Type", "Name", and "Description".
	 * @return an instance of {@code AnalyticDetail} populated with the values from the JSON object.
	 *
	 * @since 0.4
	 * @deprecated since 0.4.0, use {@link #AnalyticDetail(JsonObject)} instead.
	 */
	@Deprecated (since = "1.1.0", forRemoval = true)
	public static AnalyticDetail fromJson(JsonObject json) {
		AnalyticDetail detail = new AnalyticDetail();
		if (json.containsKey("Code")) {
			detail.setCode(json.getString("code"));
		}
		if (json.containsKey("Type")) {
			detail.setType(json.getString("type"));
		}
		if (json.containsKey("Name")) {
			detail.setName(json.getString("name"));
		}
		if (json.containsKey("Description")) {
			detail.setDescription(json.getString("description"));
		}
		return detail;
	}

}