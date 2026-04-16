package com.jtissdev_API.features.PCG.dto;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a root accounting type (top-level in the hierarchy).
 * <p>
 * A {@code Type_Comptable} has its own identifier, name,
 * local accounting code and description.
 * It also contains a list of {@link Sub_Type_Comptable} that belong
 * to this root accounting type.
 * <p>
 * Unlike other levels, this type cannot have a parent accounting code:
 * its full accounting code is therefore equal to its local accounting code.
 *
 * @author jtiss
 * @since 0.1
 * @version 1.0.0
 */
public class Type_Comptable {

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
	 * Human-readable name of the root accounting type.
	 *
	 * @since 0.1
	 */
	private String name;

	/**
	 * Local accounting code (numeric value) for this root type.
	 * Since this type cannot have a parent code, this value is
	 * also considered to be the full accounting code.
	 *
	 * @since 0.1
	 */
	private Integer codeComptable;

	/**
	 * Human-readable description of the root accounting type.
	 *
	 * @since 0.1
	 */
	private String description;

	/**
	 * List of sub accounting types attached to this root type.
	 *
	 * @since 0.1
	 */
	private List<Sub_Type_Comptable> subTypes;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code Type_Comptable} instance.
	 * All fields are initialized to {@code null}, and the list
	 * of sub types is initialized to an empty {@link ArrayList}.
	 *
	 * @since 0.1
	 */
	public Type_Comptable() {
		this.subTypes = new ArrayList<>();
	}

	/**
	 * Creates a {@code Type_Comptable} instance with main fields initialized.
	 * The list of sub types is initialized to an empty {@link ArrayList}.
	 *
	 * @param id            technical identifier used by the database
	 * @param name          human-readable name of the root accounting type
	 * @param codeComptable local accounting code (numeric value)
	 * @param description   human-readable description of the root accounting type
	 *
	 * @since 0.1
	 */
	public Type_Comptable(Long id,
	                      String name,
	                      Integer codeComptable,
	                      String description) {
		this.setId( id ) ;
		this.setName( name ) ;
		this.setCodeComptable( codeComptable ) ;
		this.setDescription( description ) ;
		this.subTypes = new ArrayList<>();
	}

	// =========================================================
	// == GETTERS / SETTERS                                   ==
	// =========================================================

	/**
	 * Returns the technical identifier.
	 *
	 * @return technical identifier, or {@code null} if not set
	 *
	 * @since 0.1
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the technical identifier.
	 *
	 * @param id technical identifier to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Returns the human-readable name of the root accounting type.
	 *
	 * @return name, or {@code null} if not set
	 *
	 * @since 0.1
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the human-readable name of the root accounting type.
	 *
	 * @param name name to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the local accounting code (numeric value).
	 *
	 * @return local accounting code, or {@code null} if not set
	 *
	 * @since 0.1
	 */
	public Integer getCodeComptable() {
		return codeComptable;
	}

	/**
	 * Sets the local accounting code (numeric value).
	 *
	 * @param codeComptable code to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable setCodeComptable(Integer codeComptable) {
		this.codeComptable = codeComptable;
		return this;
	}

	/**
	 * Returns the human-readable description of the root accounting type.
	 *
	 * @return description, or {@code null} if not set
	 *
	 * @since 0.1
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the human-readable description of the root accounting type.
	 *
	 * @param description description to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Returns the list of sub accounting types attached to this root type.
	 *
	 * @return non-null list of sub accounting types (may be empty)
	 *
	 * @since 0.1
	 */
	public List<Sub_Type_Comptable> getSubTypes() {
		return subTypes;
	}

	/**
	 * Sets the list of sub accounting types attached to this root type.
	 * If {@code null} is provided, the internal list is replaced by
	 * an empty {@link ArrayList}.
	 *
	 * @param subTypes list of sub accounting types to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable setSubTypes(List<Sub_Type_Comptable> subTypes) {
		this.subTypes = subTypes != null ? subTypes : new ArrayList<>();
		return this;
	}

	// =========================================================
	// == UTILITY METHODS                                     ==
	// =========================================================

	/**
	 * Adds a sub accounting type to the internal list.
	 * If the given sub type is {@code null}, the call is ignored.
	 *
	 * @param subType sub accounting type to add
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.1
	 */
	public Type_Comptable addSubType(Sub_Type_Comptable subType) {
		if (subType != null) {
			if (this.subTypes == null) {
				this.subTypes = new ArrayList<>();
			}
			this.subTypes.add(subType);
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
	public String getFullCodeComptable() {
		return codeComptable != null ? codeComptable.toString() : null;
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
	 * @since 0.1
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		if (id != null) {
			builder.add("id", id);
		}
		if (name != null) {
			builder.add("name", name);
		}
		if (codeComptable != null) {
			builder.add("codeComptable", codeComptable);
		}
		if (description != null) {
			builder.add("description", description);
		}

		String fullCode = getFullCodeComptable();
		if (fullCode != null) {
			builder.add("fullCodeComptable", fullCode);
		}

		JsonArrayBuilder subTypesArray = Json.createArrayBuilder();
		if (subTypes != null) {
			for (Sub_Type_Comptable subType : subTypes) {
				if (subType != null) {
					JsonObject jsonSubType = subType.toJson();
					subTypesArray.add(jsonSubType);
				}
			}
		}
		builder.add("subTypes", subTypesArray);

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
		sb.append("Type_Comptable {")
				.append("\n  id=").append(id)
				.append(",\n  name='").append(name).append('\'')
				.append(",\n  codeComptable=").append(codeComptable)
				.append(",\n  description='").append(description).append('\'');

		if (subTypes != null && !subTypes.isEmpty()) {
			sb.append(",\n  subTypes=[");
			for (int i = 0; i < subTypes.size(); i++) {
				// On ajoute un retour à la ligne et des espaces pour décaler les subtypes
				sb.append("\n    ").append(subTypes.get(i).toString().replace("\n", "\n    "));

				if (i < subTypes.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("\n  ]");
		} else {
			sb.append(",\n  subTypes=0");
		}

		sb.append("\n}");
		return sb.toString();
	}
}