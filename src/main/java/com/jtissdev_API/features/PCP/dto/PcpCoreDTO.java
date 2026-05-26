package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Main container for the Personal Accounting Plan (PCP).
 * <p>
 * This DTO centralizes personalized data including Third Parties (Tiers)
 * and Level 4 accounting details (AnalyticDetail).
 * </p>
 *
 * @author J.Tiss
 * @version 1.2.0
 * @since 0.1
 */
public class PcpCoreDTO {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * List of all registered third parties (Vendors, Friends, etc.).
	 * @since 0.1
	 */
	private List<Tiers> thirdParties;

	/**
	 * List of specific Level 4 accounting objects (Accounts, Vehicles, etc.).
	 * @since 0.1
	 */
	private List<AnalyticDetail> details;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor initializing empty collections.
	 * @since 0.1
	 */
	public PcpCoreDTO() {
		this.thirdParties = new ArrayList<>();
		this.details = new ArrayList<>();
	}

	public PcpCoreDTO(JsonObject json) {
		this();
		if(json.containsKey("thirdParties")) {
			for(JsonObject obj : json.getJsonArray("thirdParties").getValuesAs(JsonObject.class)) {
				this.getThirdParties().add(new Tiers(obj));
			}
		}
		if(json.containsKey("details")) {
			for(JsonObject obj : json.getJsonArray("details").getValuesAs(JsonObject.class)) {
				this.getDetails().add(new AnalyticDetail(obj));
			}
		}
	}

	// =========================================================
	// == ACCESSORS (GETTERS)                                 ==
	// =========================================================

	/**
	 * Gets the list of third parties.
	 * @return the list of {@link Tiers}.
	 * @since 0.1
	 * @version 1.1
	 */
	public List<Tiers> getThirdParties() {
		return this.thirdParties != null ? this.thirdParties : new ArrayList<>();
	}

	/**
	 * Gets the list of Level 4 accounting details.
	 * @return the list of {@link AnalyticDetail}.
	 * @since 0.1
	 * @version 1.1
	 */
	public List<AnalyticDetail> getDetails() {
		return this.details != null ? this.details : new ArrayList<>();
	}

	// =========================================================
	// == ACCESSORS (SETTERS)                                 ==
	// =========================================================

	/**
	 * Sets the list of third parties.
	 * @param thirdParties the list to assign.
	 * @return    {@code this} instance for fluent chaining
	 * @since 0.1
	 * @version 1.2
	 */
	public PcpCoreDTO setThirdParties(List<Tiers> thirdParties) {
		this.thirdParties = thirdParties != null ? thirdParties : new ArrayList<>();
		return this;
	}

	/**
	 * Sets the list of Level 4 accounting details.
	 * @param details the list to assign.
	 * @return {@code this} instance for fluent chaining
	 * @since 0.1
	 * @version 1.2
	 */
	public PcpCoreDTO setDetails(List<AnalyticDetail> details) {
		this.details = details != null ? details : new ArrayList<>();
		return this;
	}

	/**
	 * Adds a third party to the list of third parties associated with this instance.
	 * This method enables fluent chaining.
	 *
	 * @param thirdParty the {@link Tiers} instance to add to the list of third parties
	 * @return this {@link PcpCoreDTO} instance for fluent chaining
	 * @since 0.4
	 */
	public PcpCoreDTO addThirdParty(Tiers thirdParty) {
		if(thirdParty != null) {
			this.getThirdParties().add(thirdParty);
		}
		return this;
	}

	/**
	 * Adds an {@link AnalyticDetail} to the list of Level 4 accounting details associated with this instance.
	 * This method enables fluent chaining.
	 *
	 * @param detail the {@link AnalyticDetail} instance to add to the list of accounting details
	 * @return this {@link PcpCoreDTO} instance for fluent chaining
	 *
	 * @since 0.4
	 */
	public PcpCoreDTO addDetail(AnalyticDetail detail) {
		if(detail != null) {
			this.getDetails().add(detail);
		}
		return this;
	}

	/**
	 * Converts the current object state into a JSON representation.
	 * The resulting JSON object includes the lists of third parties and details, if they are present.
	 * Each third party and detail is also converted to JSON and included in the respective arrays.
	 *
	 * @return a {@link JsonObject} representing the current state of the object with
	 *         "thirdParties" and "details" as keys for their respective JSON arrays.
	 * @since 0.4
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		if(this.getThirdParties() != null) {
			JsonArrayBuilder thirdPartiesBuilder = Json.createArrayBuilder();
				for(Tiers thirdParty : this.getThirdParties()) {
					thirdPartiesBuilder.add(thirdParty.toJson());
				}
				builder.add("thirdParties", thirdPartiesBuilder);
		}
		if(this.getDetails() != null) {
			JsonArrayBuilder detailsBuilder = Json.createArrayBuilder();
				for(AnalyticDetail detail : this.getDetails()) {
					detailsBuilder.add(detail.toJson());
				}
				builder.add("details", detailsBuilder);
		}
		return builder.build();
	}

	/**
	 * Generates a string representation of the PcpCoreDTO object, including the lists
	 * of third parties and accounting details if they are present.
	 *
	 * @return a string that represents the current state of the PcpCoreDTO instance,
	 *         including details of third parties and accounting details.
	 * @since 0.4
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PcpCoreDTO{");
			if(this.getThirdParties() != null) {
				sb.append("thirdParties=[");
					for(Tiers thirdParty : this.getThirdParties()) {
						sb.append(thirdParty.toString());
					}
			}
			if(this.getDetails() != null) {
				sb.append("details=[");
						for(AnalyticDetail detail : this.getDetails()) {
							sb.append(detail.toString());
						}
			}
			sb.append("}");
		return sb.toString();
	}
}