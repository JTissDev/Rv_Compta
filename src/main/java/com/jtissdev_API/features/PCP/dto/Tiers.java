package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents an external entity (Third Party) involved in accounting operations.
 * <p>
 * A third party can be a vendor, a client, an employer, or any entity
 * involved in financial flows. Within the PCP (Personal Accounting Plan) block,
 * it identifies the source or destination of a transaction.
 * </p>
 *
 * @author J.Tiss
 * @version 1.0.2
 * @since v1.0
 */
public class Tiers {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/** * Technical unique identifier.
	 * @since 1.0.0
	 */
	private Long id;

	/** * Legal name or display name of the third party.
	 * @since 1.0.0
	 */
	private String name;

	/** * Category of the third party (e.g., VENDOR, EMPLOYER, FRIEND).
	 * @since 1.0.0
	 */
	private String thirdPartyType;

	/** * Additional notes or information.
	 * @since 1.0.0
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * @since 1.0.0
	 */
	public Tiers() {
	}

	/**
	 * Functional constructor without ID.
	 * Primarily used for data loading from files or database cleanup/import
	 * where the ID is not yet assigned.
	 *
	 * @param name Name of the entity
	 * @param thirdPartyType Category of the entity
	 * @since 1.0.2
	 */
	public Tiers(String name, String thirdPartyType) {
		this.setName(name);
		this.setThirdPartyType(thirdPartyType);
	}

	/**
	 * Complete constructor for quick initialization.
	 * Uses setters to ensure data integrity and security logic.
	 *
	 * @param id Technical identifier
	 * @param name Name of the entity
	 * @param thirdPartyType Category of the entity
	 * @since 1.0.0
	 */
	public Tiers(Long id, String name, String thirdPartyType) {
		this.setId(id);
		this.setName(name);
		this.setThirdPartyType(thirdPartyType);
	}

	// =========================================================
	// == ACCESSORS (Getters & Setters)                      ==
	// =========================================================

	/** * Gets the technical unique identifier.
	 * @return the unique ID assigned by the database.
	 * @since 1.0.0
	 */
	public Long getId() {
		return id;
	}

	/** * Sets the technical unique identifier.
	 * @param id the unique ID to set.
	 * @since 1.0.0
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** * Gets the display name of the third party.
	 * @return the name used for reports and UI.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/** * Sets the display name of the third party.
	 * @param name the label to assign.
	 * @since 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** * Gets the category/type of the third party.
	 * @return a string defining the entity role (e.g. VENDOR).
	 * @since 1.0.0
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}

	/** * Sets the category/type of the third party.
	 * @param thirdPartyType the category string.
	 * @since 1.0.0
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	/** * Gets the additional notes for this third party.
	 * @return a free text description or null.
	 * @since 1.0.0
	 */
	public String getDescription() {
		return description;
	}

	/** * Sets the additional notes for this third party.
	 * @param description the information to store.
	 * @since 1.0.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	// =========================================================
	// == SERIALIZATION LOGIC                                 ==
	// =========================================================

	/**
	 * Converts the Tiers object into a JSON format for persistence.
	 *
	 * @return A {@link JsonObject} representing the third party.
	 * @since 1.0.0
	 * @version 1.0.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("id", id != null ? id : -1);
		builder.add("name", name != null ? name : "Unknown");
		builder.add("thirdPartyType", thirdPartyType != null ? thirdPartyType : "MISC");
		builder.add("description", description != null ? description : "");

		return builder.build();
	}

	/** * Provides a summary of the Third Party for logging and debugging.
	 * @return a formatted string.
	 * @since 1.0.0
	 */
	@Override
	public String toString() {
		return String.format("[%d] %s (%s)", id, name, thirdPartyType);
	}
}