package com.jtissdev.features.referential.dto;

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
 * @version 1.2.0
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
	private String name;

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
	 * Constructs a {@code PaymentMethod} instance by initializing its fields using the provided JSON object.
	 *
	 * @param json the JSON object containing the payment method data. Keys "code", "name", and "description"
	 *             are expected to initialize the corresponding fields of the {@code PaymentMethod}.
	 * @since 0.4
	 */
	public PaymentMethod(JsonObject json) {
		this();
		 if(json.containsKey("code")) {
			 this.setCode(json.getString("code"));
		 }
		 if(json.containsKey("name")) {
			 this.setName(json.getString("name"));
		 }
		 if(json.containsKey("description")) {
			 this.setDescription(json.getString("description"));
		 }
	}

	/**
	 * Creates a {@code PaymentMethod} instance with all fields initialized.
	 *
	 * @param code        unique technical code
	 * @param name         human-readable name
	 * @param description usage description
	 *
	 * @since 0.2.0
	 *
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * Use {@link PaymentMethod (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated (since = "0.4",forRemoval = true)
	public PaymentMethod(String code, String name, String description) {
		this.setCode(code);
		this.setName(name);
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
		return this.code;
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
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the human-readable name of the payment method.
	 *
	 * @param name name to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public PaymentMethod setName(String name) {
		this.name = name;
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
		return this.description;
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

		if (this.getCode() != null) {
			builder.add("code", this.getCode());
		}
		if (this.getName() != null) {
			builder.add("name", this.getName());
		}
		if (this.getDescription() != null) {
			builder.add("description", this.getDescription());
		}

		return builder.build();
	}

	/**
	 * Returns a string representation of this instance.
	 *
	 * @return a formatted string containing the field values.
	 * @since 0.2.0
	 */
	@Override
	public String toString() {
		return "PaymentMethod{" +
				       "code=" + this.getCode() +
				       ", name=" + this.getName() +
				       ", description=" + this.getDescription() +
				       '}';
	}
}