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
 * @version 1.2.0
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
	private Long id;

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
	private String accountingCode;

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
	private String parentCodeComptable;

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
		this.detailsList = new ArrayList<>();
	}

	/**
	 * Constructs a new {@code SubAccountingType} instance from a JSON object.
	 *
	 * @param json
	 *        the JSON object containing the data to populate this instance
	 *        - "id": a numeric identifier for the accounting type
	 *        - "name": a human-readable name
	 *        - "accountingCode": a numeric accounting code
	 *        - "description": a textual description
	 *        - "parentCod": a parent accounting code
	 *        - "detailsList": a list of details, where each detail is represented
	 *          as a JSON object
	 *
	 * @since 0.1
	 */
	public SubAccountingType(JsonObject json) {
		this();
		if (json.containsKey("id")) {
			this.setId(Long.valueOf(json.getString("Id")));
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name"));
		}
		if (json.containsKey("accountingCode")) {
			this.setAccountingCode(json.getString("accountingCode"));
		}
		if (json.containsKey("description")) {
			this.setDescription(json.getString("description"));
		}
		if (json.containsKey("parentCod")) {
			this.setParentCodeComptable(json.getString("parentCodeComptable"));
		}
		if (json.containsKey("detailsList")) {
			JsonArray array = json.getJsonArray("detailsList");
			for (JsonObject account : array.getValuesAs(JsonObject.class)) {
				this.addDetails(new AccountingTypeDetails(account));
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
	 * Use {@link SubAccountingType(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public SubAccountingType(Long id,
	                         String name,
	                         String accountingCode,
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
	 * @param parentCodeComptable
	 * 		parent accounting code used as prefix
	 * @param detailsList
	 * 		list of details; if {@code null}, an empty list will be used
	 * @since 0.1
	 *
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * Use {@link SubAccountingType(JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public SubAccountingType(Long id,
	                         String name,
	                         String accountingCode,
	                         String description,
	                         String parentCodeComptable,
	                         List<AccountingTypeDetails> detailsList) {
		this.id = id;
		this.name = name;
		this.accountingCode = accountingCode;
		this.description = description;
		this.parentCodeComptable = parentCodeComptable;
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
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the technical identifier used by the database.
	 *
	 * @param id
	 * 		the new identifier
	 * @since 0.1
	 * @return {this}
	 */
	public SubAccountingType setId(Long id) {
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
	public String getAccountingCode() {
		return this.accountingCode;
	}

	/**
	 * Sets the local accounting numeric code.
	 *
	 * @param accountingCode
	 * 		the new local accounting code
	 * @since 0.1
	 */
	public SubAccountingType setAccountingCode(String accountingCode) {
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
	public String getParentCodeComptable() {
		return this.parentCodeComptable;
	}

	/**
	 * Sets the parent accounting code.
	 *
	 * @param parentCodeComptable
	 * 		the new parent accounting code
	 * @since 0.1
	 */
	public SubAccountingType setParentCodeComptable(String parentCodeComptable) {
		this.parentCodeComptable = parentCodeComptable;
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
		if (accountingCode == null) {
			return null;
		}
		String local = accountingCode.toString();
		if (parentCodeComptable == null || parentCodeComptable.isBlank()) {
			return local;
		}
		return parentCodeComptable + local;
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
		return this.detailsList;
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
	public void addDetails(AccountingTypeDetails details) {
		if (details == null) {
			return;
		}
		if (this.detailsList == null) {
			this.detailsList = new ArrayList<>();
		}
		this.detailsList.add(details);
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

		// id
		if (id == null) {
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

		// accountingCode
		if (accountingCode == null) {
			builder.addNull("accountingCode");
		} else {
			builder.add("accountingCode", accountingCode);
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

		// detailsList (array)

		if (detailsList != null) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (AccountingTypeDetails d : detailsList) {
				if (d == null) {
					arrayBuilder.addNull();
				} else {
					arrayBuilder.add(d.toJson());
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
				.append("\n    id=").append(id)
				.append(",\n    name='").append(name).append('\'')
				.append(",\n    accountingCode=").append(accountingCode)
				.append(",\n    description='").append(description).append('\'')
				.append(",\n    parentCodeComptable='").append(parentCodeComptable).append('\'');

		if (detailsList != null && !detailsList.isEmpty()) {
			sb.append(",\n    detailsList=[");
			for (AccountingTypeDetails details : detailsList) {
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