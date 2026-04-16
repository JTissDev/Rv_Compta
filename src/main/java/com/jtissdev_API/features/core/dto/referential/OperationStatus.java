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
 * @since 0.2.0
 * @version 1.0.0
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
	private String nom;

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
	 * Creates an {@code OperationStatus} instance with all fields initialized.
	 *
	 * @param code  unique technical code
	 * @param nom   human-readable name
	 * @param color hexadecimal color code
	 *
	 * @since 0.2.0
	 */
	public OperationStatus(String code, String nom, String color) {
		this.setCode(code);
		this.setNom(nom);
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
		return code;
	}

	/**
	 * Sets the unique technical code.
	 *
	 * @param code code to set
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
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the human-readable name of the status.
	 *
	 * @param nom name to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public OperationStatus setNom(String nom) {
		this.nom = nom;
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
		return color;
	}

	/**
	 * Sets the hexadecimal color code for UI.
	 *
	 * @param color color to set
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
		if (nom != null) {
			builder.add("nom", nom);
		}
		if (color != null) {
			builder.add("color", color);
		}

		return builder.build();
	}
}