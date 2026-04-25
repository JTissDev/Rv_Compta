package com.jtissdev_API.features.core.dto.referential;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents the lifecycle status of an accounting operation.
 * <p>
 * An {@code OperationStatus} defines the state of a transaction (e.g., Planned, Real),
 * including a display name and a color code for UI representation.
 *
 * @author J.Tiss
 * @version 1.1.0
 * @since 0.2.0
 */
public class OperationStatus {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Unique technical code for the status.
	 *
	 * @since 0.2.0
	 */
	private String code;

	/**
	 * Human-readable name of the status.
	 *
	 * @since 0.2.0
	 */
	private String name;

	/**
	 * Hexadecimal color code used for UI display.
	 *
	 * @since 0.2.0
	 */
	private String color;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code OperationStatus} instance.
	 *
	 * @since 0.2.0
	 */
	public OperationStatus() {
	}

	/**
	 * Initializes an {@code OperationStatus} instance based on the provided JSON object.
	 * If the JSON contains any of the keys "code", "name", or "color", their respective values
	 * will be used to set the corresponding fields of this instance.
	 *
	 * @param json
	 * 		the JSON object containing the initial values for the instance fields
	 * @since 0.4
	 */
	public OperationStatus(JsonObject json) {
		this();
		if (json.containsKey("code")) {
			this.setCode(json.getString("code"));
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name"));
		}
		if (json.containsKey("color")) {
			this.setColor(json.getString("color"));
		}
	}

	/**
	 * Creates an {@code OperationStatus} instance with all fields initialized.
	 *
	 * @param code
	 * 		unique technical code
	 * @param name
	 * 		human-readable name
	 * @param color
	 * 		hexadecimal color code
	 * @since 0.2.0
	 */
	public OperationStatus(String code, String name, String color) {
		this.setCode(code);
		this.setName(name);
		this.setColor(color);
	}

	// =========================================================
	// == GETTERS / SETTERS                                   ==
	// =========================================================

	/**
	 * Returns the unique technical code.
	 *
	 * @return status code, or {@code null} if not set
	 *
	 * @since 0.2.0
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the unique technical code.
	 *
	 * @param code
	 * 		code to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public OperationStatus setCode(String code) {
		this.code = code;
		return this;
	}

	/**
	 * Returns the human-readable name of the status.
	 *
	 * @return status name, or {@code null} if not set
	 *
	 * @since 0.2.0
	 */
	public String getName() {return this.name;}

	/**
	 * Sets the human-readable name of the status.
	 *
	 * @param name
	 * 		name to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public OperationStatus setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the hexadecimal color code for UI.
	 *
	 * @return color code, or {@code null} if not set
	 *
	 * @since 0.2.0
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Sets the hexadecimal color code for UI.
	 *
	 * @param color
	 * 		color to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public OperationStatus setColor(String color) {
		this.color = color;
		return this;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Converts this operation status into a {@link JsonObject}.
	 *
	 * @return JSON representation of this instance
	 *
	 * @since 0.2.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		if (code != null) {
			builder.add("code", code);
		}
		if (name != null) {
			builder.add("name", name);
		}
		if (color != null) {
			builder.add("color", color);
		}

		return builder.build();
	}

	/**
	 * Returns a string representation of the {@code OperationStatus} object.
	 * The format includes the values of the {@code code}, {@code name},
	 * and {@code color} fields.
	 *
	 * @return a string representation of this {@code OperationStatus} instance
	 *
	 * @since 0.4
	 */
	public String toString() {
		return "OperationStatus {code=" + code + ", name=" + name + ", color=" + color + "}";
	}
}