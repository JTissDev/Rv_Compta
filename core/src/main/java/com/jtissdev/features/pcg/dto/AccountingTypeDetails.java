package com.jtissdev.features.pcg.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents detailed information about an accounting type.
 * This class contains information such as an identifier,
 * the type's name, description, and associated accounting code.
 * <p>
 * The full accounting code is built by combining the optional
 * parent accounting code with the local accounting code.
 *
 * @author jtiss
 * @version 2.0
 * @since 0.1
 */
public class AccountingTypeDetails extends AccountElement<AccountingTypeDetails>{

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Parent accounting code.
	 * This is used as a prefix when computing the full accounting code.
	 * It is meant to represent a higher-level accounting structure.
	 *
	 * @since 0.1
	 */
	private String parentAccountingCode;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code AccountingTypeDetails} instance.
	 * All fields are initialized to {@code null}.
	 *
	 * @since 0.1
	 */
	public AccountingTypeDetails() {
		super ();
	}

	/**
	 * Creates a new {@code AccountingTypeDetails} instance and populates its fields
	 * based on the given JSON object. Fields are set only if the corresponding key
	 * exists in the JSON object.
	 *
	 * @param json
	 * 		the {@code JsonObject} containing the data to initialize the fields
	 * @since 0.4
	 */
	public AccountingTypeDetails(JsonObject json) {
		super(json);
		if (json.containsKey("parentAccountingCode")) {
			this.setParentAccountingCode(json.getString("parentAccountingCode"));
		}
	}

	/**
	 * Creates a new {@code AccountingTypeDetails} instance with basic fields.
	 * The parent accounting code is not defined in this constructor.
	 *
	 * @param id
	 * 		technical identifier used by the database
	 * @param name
	 * 		human-readable name
	 * @param accountingCode
	 * 		local accounting numeric code
	 * @param description
	 * 		human-readable description
	 * @since 0.1
	 * @deprecated Manual field initialization is discouraged.
	 * Use {@link AccountingTypeDetails(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated (since = "0.4", forRemoval = true)
	public AccountingTypeDetails(int id,
	                             String name,
	                             Integer accountingCode,
	                             String description) {
		this.setId(id);
		this.setName(name);
		this.setAccountCode(accountingCode);
		this.setDescription(description);
	}

	/**
	 * Creates a new {@code AccountingTypeDetails} instance with
	 * a parent accounting code and all other fields.
	 *
	 * @param id
	 * 		technical identifier used by the database
	 * @param name
	 * 		human-readable name
	 * @param accountingCode
	 * 		local accounting numeric code
	 * @param description
	 * 		human-readable description
	 * @param parentAccountingCode
	 * 		parent accounting code used as prefix
	 * @since 0.1
	 * @deprecated Manual field initialization is discouraged.
	 * Use {@link AccountingTypeDetails(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated (since = "0.4", forRemoval = true)
	public AccountingTypeDetails(int id,
	                             String name,
	                             Integer accountingCode,
	                             String description,
	                             String parentAccountingCode) {
		this.setId(id);
		this.setName(name);
		this.setAccountCode(accountingCode);
		this.setDescription(description);
		this.setParentAccountingCode(parentAccountingCode);
	}

	/**
	 * Constructs a new {@code AccountingTypeDetails} instance with the specified name,
	 * local accounting code, description, and parent accounting code.
	 *
	 * @param name
	 * 		the human-readable name of the accounting type
	 * @param accountingCode
	 * 		the local accounting numeric code
	 * @param description
	 * 		the human-readable description of the accounting type
	 * @param parentAccountingCode
	 * 		the parent accounting code that acts as a prefix
	 * @since 0.1
	 * @deprecated Manual field initialization is discouraged.
	 * Use {@link AccountingTypeDetails(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated (since = "0.4", forRemoval = true)
	public AccountingTypeDetails(String name,
	                             Integer accountingCode,
	                             String description,
	                             String parentAccountingCode) {
		this.setName(name);
		this.setAccountCode(accountingCode);
		this.setDescription(description);
		this.setParentAccountingCode(parentAccountingCode);
	}





	// =========================================================
	// == GETTERS                                             ==
	// =========================================================

	/**
	 * Returns the parent accounting code.
	 *
	 * @return the parent accounting code, or {@code null} if not defined
	 *
	 * @since 0.1
	 */
	public String getParentAccountingCode() {
		return this.parentAccountingCode;
	}

	// =========================================================
	// == SETTERS (NO PARENT CODE MODIFICATION)               ==
	// =========================================================

	public AccountingTypeDetails setParentAccountingCode(String fullCode) {
		this.parentAccountingCode = fullCode;
		return this;
	}

	// =========================================================
	// == JSON & UTILITIES                                   ==
	// =========================================================

	/**
	 * Returns the full accounting code.
	 * <p>
	 * If a parent accounting code is defined, the full code is built
	 * by concatenating the parent code, a separator (".") and the
	 * local accounting code. If no parent is defined, only the local
	 * accounting code is returned as string.
	 * <p>
	 * Examples (assuming accountingCode = 101):
	 * <ul>
	 *   <li>parentAccountingCode = "60"  → "60101"</li>
	 *   <li>parentAccountingCode = null → "101"</li>
	 * </ul>
	 *
	 * @return the full accounting code, or {@code null} if
	 * 		the local accounting code is not defined
	 *
	 * @since 0.1
	 */
	public String getFullCode() {
		if (this.getAccountCode() == null || this.getParentAccountingCode() == null) {
			return null; // Donnée invalide : orphelin
		}
		// Si code detail == parent (ex: Parent 40, Code 40), on ne double pas
		if (this.getAccountCode().equals(this.getParentAccountingCode())) {
			return this.getParentAccountingCode();
		}
		return this.getParentAccountingCode() + this.getAccountCode();
	}

	/**
	 * Construit et renvoie un {@link JsonObject} représentant cet objet.
	 * <p>
	 * Les valeurs {@code null} sont encodées comme {@code null} JSON.
	 *
	 * @return un JsonObject représentant cette instance
	 *
	 * @version 1.1
	 * @since 0.1
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = super.getBaseJsonBuilder();


		if (parentAccountingCode != null) builder.add("parentAccountingCode", parentAccountingCode);

		String fullCode = this.getFullCode();
		if (fullCode != null) builder.add("fullCode", fullCode);

		return builder.build();
	}

	/**
	 * Returns a string representation of this object,
	 * mainly for debugging purposes.
	 *
	 * @return a human-readable string representation
	 *
	 * @since 0.1
	 */
	@Override
	public String toString() {
		return "AccountingTypeDetails{" +
				       "id=" + this.getId() +
				       ", name='" + this.getName() + '\'' +
				       ", accountingCode=" + this.getAccountCode() +
				       ", description='" + this.getDescription() + '\'' +
				       ", parentAccountingCode='" + this.getParentAccountingCode() + '\'' +
				       '}';
	}
}