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
 * @version 1.1.0
 * @since v0.1
 */
public class Tiers {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Technical unique identifier.
	 *
	 * @since 0.1
	 */
	private Long id;

	/**
	 * Legal name or display name of the third party.
	 *
	 * @since 0.1
	 */
	private String name;

	/**
	 * Category of the third party (e.g., VENDOR, EMPLOYER, FRIEND).
	 *
	 * @since 0.1
	 */
	private String thirdPartyType;

	/**
	 * Additional notes or information.
	 *
	 * @since 0.1
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 *
	 * @since 0.1
	 */
	public Tiers() {
	}

	/**
	 * Constructs a new {@code Tiers} instance from a JSON object, mapping its properties
	 * to the corresponding fields of the class.
	 *
	 * @param json
	 *        the JSON object containing the data to populate the {@code Tiers} object
	 *
	 * @since 0.4
	 */
	public Tiers(JsonObject json) {
		this();
		if (json.containsKey("id")) {
			this.setId(json.getJsonNumber("id").longValue());
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name"));
		}
		if (json.containsKey("thirdPartyType")) {
			this.setThirdPartyType(json.getString("thirdPartyType"));
		}
		if (json.containsKey("description")) {
			this.setDescription(json.getString("description"));
		}
	}

	/**
	 * Constructs a new {@code Tiers} instance with the specified name.
	 *
	 * @param name
	 * 		the display name of the third party
	 * @since 0.4
	 */
	public Tiers(String name) {
		this.setName(name);
	}

	/**
	 * Functional constructor without ID.
	 * Primarily used for data loading from files or database cleanup/import
	 * where the ID is not yet assigned.
	 *
	 * @param name
	 * 		Name of the entity
	 * @param thirdPartyType
	 * 		Category of the entity
	 * @since 1.0.2
	 */
	public Tiers(String name, String thirdPartyType) {
		this(name);
		this.setThirdPartyType(thirdPartyType);
	}

	/**
	 * Constructs a new {@code Tiers} instance with the specified name, third party type, and description.
	 * This constructor builds upon another functional constructor and uses setters to ensure data integrity.
	 *
	 * @param name
	 * 		the name of the entity
	 * @param thirdPartyType
	 * 		the category or type of the third party
	 * @param description
	 * 		additional descriptive information about the third party
	 * @since 0.4
	 */
	public Tiers(String name, String thirdPartyType, String description) {
		this(name, thirdPartyType);
		this.setDescription(description);
	}

	/**
	 * Constructs a new {@code Tiers} instance with the specified ID and name.
	 * Uses setters to assign the ID and name properties.
	 *
	 * @param Id
	 * 		the unique technical identifier of the entity
	 * @param name
	 * 		the display name of the third party
	 * @since 0.4
	 */
	public Tiers(Long Id, String name) {
		this.setName(name);
		this.setId(Id);
	}

	/**
	 * Constructs a new {@code Tiers} instance with the specified ID, name, and third party type.
	 * This constructor builds upon another functional constructor and uses a setter to assign the ID.
	 *
	 * @param id
	 * 		the unique technical identifier of the entity
	 * @param name
	 * 		the name of the entity
	 * @param thirdPartyType
	 * 		the category or type of the third party
	 * @version 1.1
	 * @since 0.1
	 */
	public Tiers(Long id, String name, String thirdPartyType) {
		this(name, thirdPartyType);
		this.setId(id);
	}

	/**
	 * Constructs a new {@code Tiers} instance with the specified ID, name, third party type, and description.
	 * This constructor builds upon another functional constructor and uses setters to assign properties, ensuring data integrity.
	 *
	 * @param id
	 * 		the unique technical identifier of the entity
	 * @param name
	 * 		the name of the entity
	 * @param thirdPartyType
	 * 		the category or type of the third party
	 * @param description
	 * 		additional descriptive information about the third party
	 * @since 0.4
	 */
	public Tiers(Long id, String name, String thirdPartyType, String description) {
		this(id, name, thirdPartyType);
		this.setDescription(description);
	}

	// =========================================================
	// == ACCESSORS (Getters & Setters)                      ==
	// =========================================================

	/**
	 * Creates a {@code Tiers} object from a JSON object by mapping its properties.
	 *
	 * @param json
	 * 		the JSON object containing the data to populate the {@code Tiers} object
	 * @return an instance of {@code Tiers} populated with data from the provided JSON object
	 *
	 * @since 0.4
	 * @deprecated since 0.4 use {@link #Tiers(JsonObject)} instead.
	 */
	@Deprecated (since = "1.1.0", forRemoval = true)
	public static Tiers fromJson(JsonObject json) {
		Tiers tier = new Tiers();

		if (json.containsKey("id")) {
			tier.setId(json.getJsonNumber("id").longValue());
		}
		if (json.containsKey("name")) {
			tier.setName(json.getString("name"));
		}
		if (json.containsKey("thirdPartyType")) {
			tier.setThirdPartyType(json.getString("thirdPartyType"));
		}
		if (json.containsKey("description")) {
			tier.setDescription(json.getString("description"));
		}

		return tier;
	}

	/**
	 * Gets the technical unique identifier.
	 *
	 * @return the unique ID assigned by the database.
	 *
	 * @since 1.0.0
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the technical unique identifier.
	 *
	 * @param id
	 * 		the unique ID to set.
	 * @since 1.0.0
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the display name of the third party.
	 *
	 * @return the name used for reports and UI.
	 *
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name of the third party.
	 *
	 * @param name
	 * 		the label to assign.
	 * @since 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the category/type of the third party.
	 *
	 * @return a string defining the entity role (e.g. VENDOR).
	 *
	 * @since 1.0.0
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}

	/**
	 * Sets the category/type of the third party.
	 *
	 * @param thirdPartyType
	 * 		the category string.
	 * @since 1.0.0
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	/**
	 * Gets the additional notes for this third party.
	 *
	 * @return a free text description or null.
	 *
	 * @since 1.0.0
	 */
	public String getDescription() {
		return description;
	}

	// =========================================================
	// == SERIALIZATION LOGIC                                 ==
	// =========================================================

	/**
	 * Sets the additional notes for this third party.
	 *
	 * @param description
	 * 		the information to store.
	 * @since 1.0.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Converts the Tiers object into a JSON format for persistence.
	 *
	 * @return A {@link JsonObject} representing the third party.
	 *
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("id", id != null ? id : -1);
		builder.add("name", name != null ? name : "Unknown");
		builder.add("thirdPartyType", thirdPartyType != null ? thirdPartyType : "MISC");
		builder.add("description", description != null ? description : "");

		return builder.build();
	}

	/**
	 * Provides a summary of the Third Party for logging and debugging.
	 *
	 * @return a formatted string.
	 *
	 * @version 1.1
	 * @since 0.4
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tiers [id=").append(id)
				.append(", name=").append(name)
				.append(", thirdPartyType=").append(thirdPartyType)
				.append(", description=").append(description).append("]");
		return builder.toString();
	}
}