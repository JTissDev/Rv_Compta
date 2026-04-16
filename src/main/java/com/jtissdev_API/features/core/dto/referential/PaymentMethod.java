package com.jtissdev_API.features.core.dto.referential;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents a payment method used for accounting transactions.
 * <p>
 * A {@code PaymentMethod} defines how a payment is executed (e.g., Cash, Bank Transfer),
 * including a technical code, a display name and a detailed description.
 *
 * @author J.Tiss
 * @since 0.2.0
 * @version 1.0.0
 */
public class PaymentMethod {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Unique technical code for the payment method.
	 *
	 * @since 0.2.0
	 */
	private String code;

	/**
	 * Human-readable name of the payment method.
	 *
	 * @since 0.2.0
	 */
	private String nom;

	/**
	 * Detailed description of the payment method and its usage.
	 *
	 * @since 0.2.0
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code PaymentMethod} instance.
	 *
	 * @since 0.2.0
	 */
	public PaymentMethod() {
	}

	/**
	 * Creates a {@code PaymentMethod} instance with all fields initialized.
	 *
	 * @param code        unique technical code
	 * @param nom         human-readable name
	 * @param description usage description
	 *
	 * @since 0.2.0
	 */
	public PaymentMethod(String code, String nom, String description) {
		this.setCode(code);
		this.setNom(nom);
		this.setDescription(description);
	}

	// =========================================================
	// == GETTERS / SETTERS                                   ==
	// =========================================================

	/**
	 * Returns the unique technical code.
	 *
	 * @return method code, or {@code null} if not set
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
	public PaymentMethod setCode(String code) {
		this.code = code;
		return this;
	}

	/**
	 * Returns the human-readable name of the payment method.
	 *
	 * @return method name, or {@code null} if not set
	 *
	 * @since 0.2.0
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the human-readable name of the payment method.
	 *
	 * @param nom name to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public PaymentMethod setNom(String nom) {
		this.nom = nom;
		return this;
	}

	/**
	 * Returns the description of the payment method.
	 *
	 * @return description, or {@code null} if not set
	 *
	 * @since 0.2.0
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the payment method.
	 *
	 * @param description description to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public PaymentMethod setDescription(String description) {
		this.description = description;
		return this;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Converts this payment method into a {@link JsonObject}.
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
		if (description != null) {
			builder.add("description", description);
		}

		return builder.build();
	}
}