package com.jtissdev_API.features.PCG.dto;

import jakarta.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sub accounting type (one level above detailed accounting types).
 * <p>
 * A {@code SubAccountingType} has its own identifier, name,
 * local accounting code, description and an optional parent accounting code.
 * It also contains a list of {@link AccountingTypeDetails} that belong
 * to this sub accounting type.
 * <p>
 * The full accounting code is built by combining the optional
 * parent accounting code with the local accounting code.
 *
 * @author jtiss
 * @version 1.3.0
 * @since 0.1
 */
public class SubAccountingType {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Technical identifier used by the database.
	 *
	 * @since 0.1
	 */
	private Integer id;

	/**
	 * Human-readable name of the sub accounting type.
	 *
	 * @since 0.1
	 */
	private String name;

	/**
	 * Local accounting code (numeric value) for this sub type.
	 *
	 * @since 0.1
	 */
	private Integer accountingCode;

	/**
	 * Human-readable description of the sub accounting type.
	 *
	 * @since 0.1
	 */
	private String description;

	/**
	 * Parent accounting code.
	 * This is used as a prefix when computing the full accounting code.
	 *
	 * @since 0.1
	 */
	private String parentAccountingCode;

	/**
	 * List of detailed accounting types attached to this sub type.
	 *
	 * @since 0.1
	 */
	private List<AccountingTypeDetails> detailsList;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code SubAccountingType} instance.
	 * All fields are initialized to {@code null}, and the list of details
	 * is initialized as an empty {@link ArrayList}.
	 *
	 * @since 0.1
	 *
	 */
	public SubAccountingType() {
		if (this.detailsList == null) {
			this.detailsList = new ArrayList<>();
		}
	}

	/**
	 * Constructs a new {@code SubAccountingType} instance from a JSON object.
	 *
	 * @param json
	 * 		the JSON object containing the data to populate this instance
	 * 		- "id": a numeric identifier for the accounting type
	 * 		- "name": a human-readable name
	 * 		- "accountingCode": a numeric accounting code
	 * 		- "description": a textual description
	 * 		- "parentCod": a parent accounting code
	 * 		- "detailsList": a list of details, where each detail is represented
	 * 		as a JSON object
	 * @since 0.1
	 */
	public SubAccountingType(JsonObject json) {
		this();
		if (json.containsKey("id")) {
			this.setId(json.getInt("id"));
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name"));
		}
		if (json.containsKey("accountingCode")) {
			this.setAccountingCode(json.getInt("accountingCode"));
		}
		if (json.containsKey("description")) {
			this.setDescription(json.getString("description"));
		}
		if (json.containsKey("parentAccountingCode")) {
			this.setParentAccountingCode(json.getString("parentAccountingCode"));
		}
		if (json.containsKey("detailsList")) {
			JsonArray array = json.getJsonArray("detailsList");
			for (JsonObject account : array.getValuesAs(JsonObject.class)) {
				this.addDetails(new AccountingTypeDetails(account).setParentAccountingCode(this.getFullCode()));
			}
		}
	}



	/**
	 * Creates a new {@code SubAccountingType} instance
	 * without a parent accounting code.
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
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * 		Use {@link SubAccountingType(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public SubAccountingType(Integer id,
	                         String name,
	                         Integer accountingCode,
	                         String description) {
		this.id = id;
		this.name = name;
		this.accountingCode = accountingCode;
		this.description = description;
		this.detailsList = new ArrayList<>();
	}

	/**
	 * Creates a new {@code SubAccountingType} instance with
	 * a parent accounting code and an optional predefined list of details.
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
	 * @param detailsList
	 * 		list of details; if {@code null}, an empty list will be used
	 * @since 0.1
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * 		Use {@link SubAccountingType(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public SubAccountingType(Integer id,
	                         String name,
	                         Integer accountingCode,
	                         String description,
	                         String parentAccountingCode,
	                         List<AccountingTypeDetails> detailsList) {
		this.id = id;
		this.name = name;
		this.accountingCode = accountingCode;
		this.description = description;
		this.parentAccountingCode = parentAccountingCode;
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
	 * @since 0.1
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the technical identifier used by the database.
	 *
	 * @param id
	 * 		the new identifier
	 * @return {this}
	 *
	 * @since 0.1
	 */
	public SubAccountingType setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * Returns the human-readable name of the sub accounting type.
	 *
	 * @return the name
	 *
	 * @since 0.1
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the human-readable name of the sub accounting type.
	 *
	 * @param name
	 * 		the new name
	 * @since 0.1
	 */
	public SubAccountingType setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the local accounting numeric code.
	 *
	 * @return the local accounting code
	 *
	 * @since 0.1
	 */
	public Integer getAccountingCode() {
		return this.accountingCode;
	}

	/**
	 * Sets the local accounting numeric code.
	 *
	 * @param accountingCode
	 * 		the new local accounting code
	 * @since 0.1
	 */
	public SubAccountingType setAccountingCode(Integer accountingCode) {
		this.accountingCode = accountingCode;
		return this;
	}

	/**
	 * Returns the human-readable description of the sub accounting type.
	 *
	 * @return the description
	 *
	 * @since 0.1
	 */
	public String getDescription() {
		return this.description;
	}

	// =========================================================
	// == SETTERS                                             ==
	// =========================================================

	/**
	 * Sets the human-readable description of the sub accounting type.
	 *
	 * @param description
	 * 		the new description
	 * @since 0.1
	 */
	public SubAccountingType setDescription(String description) {
		this.description = description;
		return this;
	}

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

	/**
	 * Sets the parent accounting code.
	 *
	 * @param parentAccountingCode
	 * 		the new parent accounting code
	 * @since 0.1
	 */
	public SubAccountingType setParentAccountingCode(String parentAccountingCode) {
		this.parentAccountingCode = parentAccountingCode;
		for (AccountingTypeDetails detail : this.getDetailsList()) {
			detail.setParentAccountingCode(this.getFullCode());
		}
		return this;
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
	 * 		the local accounting code is not defined
	 *
	 * @since 0.1
	 */
	public String getFullCode() {
		if (this.getAccountingCode() == null || this.getParentAccountingCode() == null) {
			return null; // Donnée invalide : orphelin
		}
		// Si code detail == parent (ex: Parent 40, Code 40), on ne double pas
		if (this.getAccountingCode().equals(this.getParentAccountingCode())) {
			return this.getParentAccountingCode();
		}
		return this.getParentAccountingCode() + this.getAccountingCode();
	}

	/**
	 * Returns the list of detailed accounting types
	 * belonging to this sub type.
	 *
	 * @return a mutable list of {@link AccountingTypeDetails}
	 *
	 * @since 0.1
	 */
	public List<AccountingTypeDetails> getDetailsList() {
		return this.detailsList != null ? this.detailsList : new ArrayList<>();
	}

	/**
	 * Replaces the current details list.
	 *
	 * @param detailsList
	 * 		new list of details; if {@code null},
	 * 		an empty list will be used
	 * @since 0.1
	 */
	public SubAccountingType setDetailsList(List<AccountingTypeDetails> detailsList) {
		this.detailsList = (detailsList != null) ? detailsList : new ArrayList<>();
		return this;
	}

	/**
	 * Sets the details list for this sub-accounting type using a JSON array.
	 * If the existing details list is null, it initializes a new empty list.
	 * Iterates through the provided JSON array and converts each JSON object
	 * into an {@link AccountingTypeDetails} instance, which is then added
	 * to the internal details list.
	 *
	 * @param detailsList
	 * 		a JSON array containing the details to set; must not be null
	 * 		and should contain valid JSON objects representing details
	 * @return this instance of {@code SubAccountingType}, allowing for method chaining
	 *
	 * @since 0.5
	 */
	public SubAccountingType setDetailsList(JsonArray detailsList) {
		if (this.getDetailsList() == null) {
			this.setDetailsList(new ArrayList<>());
		}
		if (!detailsList.isEmpty()) {
			for (JsonValue detail : detailsList) {
				this.addDetails(new AccountingTypeDetails(detail.asJsonObject()));
			}
		}
		return this;
	}

	// =========================================================
	// == COLLECTION HELPERS                                  ==
	// =========================================================

	/**
	 * Adds a new {@link AccountingTypeDetails} to this sub type.
	 * If the internal list is {@code null}, it will be initialized.
	 *
	 * @param details
	 * 		the details to add; ignored if {@code null}
	 * @since 0.1
	 */
	public SubAccountingType addDetails(AccountingTypeDetails details) {
		if (details != null) {
			details.setParentAccountingCode(this.getFullCode());
			this.getDetailsList().add(details);
		}
		return this; // <--- C'est ça qui manquait pour le chaînage !
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
	 * @since 0.1
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		if (this.getId() != null) {
			builder.add("id", this.getId());
		}
		if (this.getName() != null) {
			builder.add("name", this.getName());
		}
		if (this.getAccountingCode() != null) {
			builder.add("accountingCode", this.getAccountingCode());
		}
		if (this.getDescription() != null) {
			builder.add("description", this.getDescription());
		}
		if (this.getParentAccountingCode() != null) {
			builder.add("parentAccountingCode", this.getParentAccountingCode());
		}
		if (this.getFullCode() != null) {
			builder.add("fullCode", this.getFullCode());
		}

		if (this.getDetailsList() != null && !this.getDetailsList().isEmpty()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (AccountingTypeDetails detail : this.getDetailsList()) {
				if (detail != null) {
					arrayBuilder.add(detail.toJson());
				}
			}
			builder.add("detailsList", arrayBuilder);
		}


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
		StringBuilder sb = new StringBuilder();
		sb.append("SubAccountingType {")
				.append("\n    id=").append(this.getId() != null ? this.getId() : "null")
				.append(",\n    name=").append(this.getName() != null ? this.getName() : "null")
				.append(",\n    accountingCode=").append(this.getAccountingCode() != null ? this.getAccountingCode() : "null")
				.append(",\n    description=").append(this.getDescription() != null ? this.getDescription() : "null")
				.append(",\n    parentAccountingCode=").append(this.getParentAccountingCode() != null ? this.getParentAccountingCode() : "null");

		if (this.getDetailsList() != null && !this.getDetailsList().isEmpty()) {
			sb.append(",\n    detailsList=[");
			for (AccountingTypeDetails details : this.getDetailsList()) {
				// On ajoute encore un cran d'indentation pour les détails
				sb.append("\n      ").append(details.toString().replace("\n", "\n      "));
			}
			sb.append("\n    ]");
		} else {
			sb.append(",\n    detailsList=0");
		}

		sb.append("\n  }");
		return sb.toString();
	}
}