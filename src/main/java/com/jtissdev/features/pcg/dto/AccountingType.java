package com.jtissdev.features.pcg.dto;

import jakarta.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a root accounting type (top-level in the hierarchy).
 * <p>
 * A {@code AccountingType} has its own identifier, name,
 * local accounting code and description.
 * It also contains a list of {@link SubAccountingType} that belong
 * to this root accounting type.
 * <p>
 * Unlike other levels, this type cannot have a parent accounting code:
 * its full accounting code is therefore equal to its local accounting code.
 *
 * @author jtiss
 * @version 2.0.0
 * @since 0.1
 */
public class AccountingType extends AccountElement<AccountingType> {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================


	/**
	 * List of sub accounting types attached to this root type.
	 *
	 * @since 0.1
	 */
	private List<SubAccountingType> subTypes = new ArrayList<>();

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code AccountingType} instance.
	 * All fields are initialized to {@code null}, and the list
	 * of sub types is initialized to an empty {@link ArrayList}.
	 *
	 * @since 0.1
	 */
	public AccountingType() {
		this.subTypes = new ArrayList<>();
	}

	public AccountingType(JsonObject json) {
		super(json);
		if (json.containsKey("subTypes")) {
			for (JsonObject subType : json.getJsonArray("subTypes").getValuesAs(JsonObject.class)) {

				this.addSubType(new SubAccountingType(subType).setParentAccountingCode(this.getFullCode()));

			}
		}
	}

	/**
	 * Creates a {@code AccountingType} instance with main fields initialized.
	 * The list of sub types is initialized to an empty {@link ArrayList}.
	 *
	 * @param id
	 * 		technical identifier used by the database
	 * @param name
	 * 		human-readable name of the root accounting type
	 * @param accountCode
	 * 		local accounting code (numeric value)
	 * @param description
	 * 		human-readable description of the root accounting type
	 * @since 0.1
	 * @deprecated Manual field initialization is discouraged.
	 * 		Use {@link AccountingType(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(since = "0.4", forRemoval = true)
	public AccountingType(Integer id,
	                      String name,
	                      Integer accountCode,
	                      String description) {
		this.setId(id);
		this.setName(name);
		this.setAccountCode(accountCode);
		this.setDescription(description);
		this.subTypes = new ArrayList<>();
	}

	// =========================================================
	// == GETTERS / SETTERS                                   ==
	// =========================================================


	/**
	 * Returns the list of sub accounting types attached to this root type.
	 *
	 * @return non-null list of sub accounting types (may be empty)
	 *
	 * @since 0.1
	 */
	public List<SubAccountingType> getSubTypes() {
		return this.subTypes != null ? this.subTypes : new ArrayList<>();
	}

	/**
	 * Sets the list of sub accounting types attached to this root type.
	 * If {@code null} is provided, the internal list is replaced by
	 * an empty {@link ArrayList}.
	 *
	 * @param subTypes
	 * 		list of sub accounting types to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public AccountingType setSubTypes(List<SubAccountingType> subTypes) {
		this.subTypes = subTypes != null ? subTypes : new ArrayList<>();
		return this;
	}

	public AccountingType setSubTypes(JsonArray subTypes) {
		if (this.getSubTypes() != null) {
			this.setSubTypes(new ArrayList<>());
		}
		if (!subTypes.isEmpty()) {
			for (JsonObject subType : subTypes.getValuesAs(JsonObject.class)) {
				this.addSubType(new SubAccountingType(subType));
			}
		}
		return this;
	}

	// =========================================================
	// == UTILITY METHODS                                     ==
	// =========================================================

	/**
	 * Adds a sub accounting type to the internal list.
	 * If the given sub type is {@code null}, the call is ignored.
	 *
	 * @param subType
	 * 		sub accounting type to add
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public AccountingType addSubType(SubAccountingType subType) {
		if (subType != null) {
			subType.setParentAccountingCode(this.getFullCode());
			this.getSubTypes().add(subType);
		}
		return this;
	}

	/**
	 * Computes the full accounting code of this root type.
	 * <p>
	 * Because a root type cannot have a parent accounting code,
	 * this method simply returns the local accounting code
	 * converted to a {@link String}.
	 *
	 * @return full accounting code as string, or {@code null} if not defined
	 *
	 * @since 0.1
	 */
	public String getFullCode() {
		return this.getAccountCode() != null ? this.getAccountCode().toString() : null;

	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Converts this root accounting type into a {@link JsonObject}.
	 * <p>
	 * Only non-null scalar fields are included in the resulting JSON.
	 * The list of sub types is always present as a JSON array (possibly empty).
	 *
	 * @return JSON representation of this instance
	 *
	 * @version 1.1
	 * @see AccountElement#toJson()
	 * @see SubAccountingType#toJson()
	 * @since 0.1
	 */
	@Override
	public JsonObject toJson() {
		JsonObjectBuilder builder = super.getBaseJsonBuilder();

		if (this.getFullCode() != null) {
			builder.add("fullCode", this.getFullCode());
		}

		if (this.getSubTypes() != null) {
			JsonArrayBuilder subTypesArray = Json.createArrayBuilder();
			for (SubAccountingType subType : this.getSubTypes()) {
				if (subType != null) {
					JsonObject jsonSubType = subType.toJson();
					subTypesArray.add(jsonSubType);
				}
			}
			builder.add("subTypes", subTypesArray);
		}

		return builder.build();
	}

	/**
	 * Returns a string representation of this instance,
	 * mainly intended for logging and debugging purposes.
	 *
	 * @return string representation of this instance
	 *
	 * @since 0.1
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AccountingType {")
				.append("\n  id=").append(this.getId() != null ? this.getId() : "null")
				.append(",\n  name=").append(this.getName() != null ? this.getName() : "null")
				.append(",\n  accountCode=").append(this.getAccountCode() != null ? this.getAccountCode() : "null")
				.append(",\n  description=").append(this.getDescription() != null ? this.getDescription() : "null");

		if (this.getSubTypes() != null && !this.getSubTypes().isEmpty()) {
			sb.append(",\n  subTypes=[");
			for (SubAccountingType subType : this.getSubTypes()) {
				// On ajoute un retour à la ligne et des espaces pour décaler les subtypes
				sb.append("\n    ").append(subType.toString().replace("\n", "\n      "));
			}
			sb.append("\n  ]");
		} else {
			sb.append(",\n  subTypes=0");
		}

		sb.append("\n}");
		return sb.toString();
	}
}