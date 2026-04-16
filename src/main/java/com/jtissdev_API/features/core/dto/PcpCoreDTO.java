package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCP.dto.Tiers;
import java.util.ArrayList;
import java.util.List;

/**
 * Root data transfer object acting as the primary container for the
 * Personal Accounting Plan (PCP) - Third Parties block.
 * * This class centralizes the list of third parties to be shared across
 * different features of the application.
 * * @author JtissDev
 * @version 1.0
 * @since v1.1 (Roadmap Profiling)
 */
public class PcpCoreDTO {

	/** List of all registered third parties (Vendors, Clients, etc.) */
	private List<Tiers> thirdParties;

	/**
	 * Default constructor initializing an empty collection of third parties.
	 */
	public PcpCoreDTO() {
		this.thirdParties = new ArrayList<>();
	}

	/**
	 * Gets the list of all registered third parties.
	 * @return A list of {@link Tiers} objects
	 */
	public List<Tiers> getThirdParties() {
		return thirdParties;
	}

	/**
	 * Sets the list of third parties.
	 * @param thirdParties The list of tiers to set
	 */
	public void setThirdParties(List<Tiers> thirdParties) {
		this.thirdParties = thirdParties;
	}

	/**
	 * Helper method to add a single third party to the container.
	 * @param tiers The third party entity to add
	 */
	public void addThirdParty(Tiers tiers) {
		if (this.thirdParties == null) {
			this.thirdParties = new ArrayList<>();
		}
		this.thirdParties.add(tiers);
	}
}