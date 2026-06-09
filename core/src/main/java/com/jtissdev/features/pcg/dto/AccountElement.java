package com.jtissdev.features.pcg.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents a base class for accounting elements within the system.
 * This class provides common attributes and behaviors shared by
 * all accounting elements and is designed to be extended by other
 * specific types of accounting elements.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public abstract class  AccountElement<T extends AccountElement<T>> {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Unique identifier for the accounting element.
	 * This value is used to distinguish this element from others
	 * within the system. It is typically assigned by the system and
	 * remains constant for the life of the element.
	 * @since 0.6
	 */
	private Integer id;

	/**
	 * Human-readable name of the accounting element.
	 *
	 * @since 0.6
	 */
	private String name;

	/**
	 * Local accounting code (numeric value) for this accounting element.
	 * Since this type cannot have a parent code, this value is
	 * also considered to be the full accounting code.
	 *
	 * @since 0.6
	 */
	private Integer accountCode;

	/**
	 * Human-readable description of the accounting element.
	 *
	 * @since 0.6
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Constructs a new instance of the AccountElement class.
	 * This constructor is protected to ensure that the class can only
	 * be instantiated by subclasses or within the same package.
	 * It provides a default mechanism to initialize a base accounting
	 * element without setting any specific properties.
	 * @since 0.6
	 */
	protected AccountElement() {}

	/**
	 * Constructs a new instance of the AccountElement class from a JSON object.
	 * This constructor is protected to ensure that the class can only
	 * be instantiated by subclasses or within the same package.
	 * It initializes the accounting element with data from the provided JSON object.
	 * @param json the JSON object containing the element's data
	 * @since 0.6
	 */
	protected AccountElement(JsonObject json) {
		this();
		if(json.containsKey("id")) {this.setId(json.getInt("id"));}
		if(json.containsKey("name")) {this.setName(json.getString("name"));}
		if(json.containsKey("accountCode")) {this.setAccountCode(json.getInt("accountCode"));}
		if(json.containsKey("description")) {this.setDescription(json.getString("description"));}
	}

	// =========================================================
	// == GETTERS & SETTERS                                   ==
	// =========================================================

	/**
	 * Retrieves the unique identifier of this AccountElement.
	 *
	 * @return the identifier of the account element as an Integer, or null if not set
	 * @since 0.6
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Retrieves the name of this AccountElement.
	 *
	 * @return the name of the account element as a String, or null if not set
	 * @since 0.6
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves the accounting code of this AccountElement.
	 *
	 * @return the accounting code of the account element as an Integer, or null if not set
	 * @since 0.6
	 */
	public Integer getAccountCode() {
		return this.accountCode;
	}

	/**
	 * Retrieves the description of this AccountElement.
	 *
	 * @return the description of the account element as a String, or null if not set
	 * @since 0.6
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Retrieves the full accounting code of this AccountElement.
	 *
	 * @return the full accounting code of the account element as a String
	 * @since 0.6
	 */
	public abstract String getFullCode() ;

	/**
	 * Sets the unique identifier for this AccountElement.
	 *
	 * @param id the identifier to be set for the account element
	 * @return the current AccountElement instance with the updated identifier
	 * @since 0.6
	 */
	@SuppressWarnings("unchecked")
	public T setId(Integer id) {
		this.id = id;
		return (T) this;
	}

	/**
	 * Sets the name for this AccountElement.
	 *
	 * @param name the name to be assigned to this account element
	 * @return the current AccountElement instance with the updated name
	 * @since 0.6
	 */
	@SuppressWarnings("unchecked")
	public T setName(String name) {
		this.name = name;
		return (T) this;
	}

	/**
	 * Sets the account code for this AccountElement.
	 *
	 * @param accountCode the account code to be assigned to this account element
	 * @return the current AccountElement instance with the updated account code
	 * @since 0.6
	 */
	@SuppressWarnings("unchecked")
	public T setAccountCode(Integer accountCode) {
		this.accountCode = accountCode;
		return (T) this;
	}

	/**
	 * Sets the description for this AccountElement.
	 *
	 * @param description the description to be assigned to this account element
	 * @return the current AccountElement instance with the updated description
	 * @since 0.6
	 */
	@SuppressWarnings("unchecked")
	public T setDescription(String description) {
		this.description = description;
		return (T) this;
	}

	// =========================================================
	// == SERIALIZATION                                       ==
	// =========================================================

	/**
	 * Constructs a JsonObjectBuilder and populates it with properties of the AccountElement
	 * instance that are not null. The builder includes the following properties if available:
	 * id, name, accountCode, and description.
	 *
	 * @return a JsonObjectBuilder containing properties of the AccountElement instance
	 * @since 0.6
	 */
	protected JsonObjectBuilder getBaseJsonBuilder() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		if (this.getId() != null) builder.add("id", this.getId());
		if (this.getName() != null) builder.add("name", this.getName());
		if (this.getAccountCode() != null) builder.add("accountCode", this.getAccountCode());
		if (this.getDescription() != null) builder.add("description", this.getDescription());

		return builder;
	}
	/**
	 * Converts the AccountElement to a JSON object representation.
	 *
	 * @return a JsonObject representing the AccountElement
	 * @since 0.6
	 */
	public abstract JsonObject toJson();

	/**
	 * Returns a string representation of the AccountElement.
	 *
	 * @return a string representation of the AccountElement
	 * @since 0.6
	 */
	@Override
	public abstract String toString();
}