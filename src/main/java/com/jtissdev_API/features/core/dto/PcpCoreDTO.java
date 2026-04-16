package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.Details_Comptable;
import com.jtissdev_API.features.PCP.dto.Tiers;
import java.util.ArrayList;
import java.util.List;

/**
 * Main container for the Personal Accounting Plan (PCP).
 * <p>
 * This DTO centralizes personalized data including Third Parties (Tiers)
 * and Level 4 accounting details (Details_Comptable).
 * </p>
 *
 * @author J.Tiss
 * @version 1.1.0
 * @since v1.0
 */
public class PcpCoreDTO {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * List of all registered third parties (Vendors, Friends, etc.).
	 * @since 1.0.0
	 */
	private List<Tiers> thirdParties;

	/**
	 * List of specific Level 4 accounting objects (Accounts, Vehicles, etc.).
	 * @since 1.1.0
	 */
	private List<Details_Comptable> details;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor initializing empty collections.
	 * @since 1.0.0
	 */
	public PcpCoreDTO() {
		this.thirdParties = new ArrayList<>();
		this.details = new ArrayList<>();
	}

	// =========================================================
	// == ACCESSORS (GETTERS)                                 ==
	// =========================================================

	/**
	 * Gets the list of third parties.
	 * @return the list of {@link Tiers}.
	 * @since 1.0.0
	 */
	public List<Tiers> getThirdParties() {
		return thirdParties;
	}

	/**
	 * Gets the list of Level 4 accounting details.
	 * @return the list of {@link Details_Comptable}.
	 * @since 1.1.0
	 */
	public List<Details_Comptable> getDetails() {
		return details;
	}

	// =========================================================
	// == ACCESSORS (SETTERS)                                 ==
	// =========================================================

	/**
	 * Sets the list of third parties.
	 * @param thirdParties the list to assign.
	 * @since 1.0.0
	 */
	public void setThirdParties(List<Tiers> thirdParties) {
		this.thirdParties = thirdParties;
	}

	/**
	 * Sets the list of Level 4 accounting details.
	 * @param details the list to assign.
	 * @since 1.1.0
	 */
	public void setDetails(List<Details_Comptable> details) {
		this.details = details;
	}
}