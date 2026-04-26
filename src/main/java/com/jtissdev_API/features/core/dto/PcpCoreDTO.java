package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import jakarta.json.JsonObject;

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
				this.thirdParties.add(new Tiers(obj));
			}
		}
		if(json.containsKey("details")) {
			for(JsonObject obj : json.getJsonArray("details").getValuesAs(JsonObject.class)) {
				this.details.add(new AnalyticDetail(obj));
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
	 */
	public List<Tiers> getThirdParties() {
		return thirdParties;
	}

	/**
	 * Gets the list of Level 4 accounting details.
	 * @return the list of {@link AnalyticDetail}.
	 * @since 0.1
	 */
	public List<AnalyticDetail> getDetails() {
		return details;
	}

	// =========================================================
	// == ACCESSORS (SETTERS)                                 ==
	// =========================================================

	/**
	 * Sets the list of third parties.
	 * @param thirdParties the list to assign.
	 * @return    {@code this} instance for fluent chaining
	 * @since 0.1
	 * @version 1.1
	 */
	public PcpCoreDTO setThirdParties(List<Tiers> thirdParties) {
		this.thirdParties = thirdParties;
		return this;
	}

	/**
	 * Sets the list of Level 4 accounting details.
	 * @param details the list to assign.
	 * @return {@code this} instance for fluent chaining
	 * @since 0.1
	 * @version 1.1
	 */
	public PcpCoreDTO setDetails(List<AnalyticDetail> details) {
		this.details = details;
		return this;
	}


}