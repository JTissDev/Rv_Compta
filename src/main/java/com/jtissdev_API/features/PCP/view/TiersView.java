package com.jtissdev_API.features.PCP.view;

import com.jtissdev_API.features.PCP.dto.Tiers;

import java.util.List;

/**
 * Represents the view model for a "Tier" in the context of the PCP (Plan Comptable Personnel) feature.
 * <p>
 * A "Tier" typically refers to an entity such as a client, supplier, or any other party involved in accounting transactions. This view model is designed to encapsulate
 * the necessary information for displaying and managing tiers within the application, including details like name, contact information, and associated accounts.
 *
 * @since 0.6
 * @version 1.0.0
 * @author jtiss
 */
public class TiersView {

	public void displayTiers(List<Tiers> tiers) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Liste des Tiers ===\n");
		for (Tiers tier : tiers) {
			displayTier(tier);
			sb.append("-----------------------\n");
		}

		System.out.println(sb.toString());

	}

	public void displayTier(Tiers tier){
		StringBuilder sb = new StringBuilder();
		sb.append("=== Détails du Tier ").append(tier.getId()).append(" ===\n");
		sb.append("Nom: ").append(tier.getName()).append("\n");
		sb.append("Type: ").append(tier.getThirdPartyType()).append("\n");

		System.out.println(sb.toString());
	}

	public void displayShortTier(Tiers tier) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Tier ").append(tier.getId()).append(" ===\n");
		sb.append("Nom: ").append(tier.getName()).append("\n");

		System.out.println(sb.toString());
	}
}
