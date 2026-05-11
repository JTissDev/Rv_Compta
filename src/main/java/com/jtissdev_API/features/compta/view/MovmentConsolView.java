package com.jtissdev_API.features.compta.view;

import com.jtissdev_API.features.compta.dto.MovementDTO;
import com.jtissdev_API.view.ViewUtil;

import java.math.BigDecimal;

/**
 * Represents a MovmentConsolView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public class MovmentConsolView {


	public String getInlineMovement(MovementDTO movement) {
		String tiersName = (movement.getTiers() != null) ? movement.getTiers().getName() : "-";

		// Gestion des montants (on n'affiche rien si c'est égal à zéro pour la lisibilité)
		String debit = (movement.getDebitAmount() != null && movement.getDebitAmount().compareTo(BigDecimal.ZERO) > 0)
				               ? String.format("%.2f €", movement.getDebitAmount()) : "";
		String credit = (movement.getCreditAmount() != null && movement.getCreditAmount().compareTo(BigDecimal.ZERO) > 0)
				                ? String.format("%.2f €", movement.getCreditAmount()) : "";

		return String.format(ViewUtil.TABLE_FORMAT,
				movement.getAccountingCode() != null ? movement.getAccountingCode() : "N/A",
				truncate(tiersName, 15),
				truncate(movement.getDescription(), 20),
				debit,
				credit
		);
	}

	private String truncate(String text, int length) {
		if (text == null) return "";
		return text.length() <= length ? text : text.substring(0, length - 3) + "...";
	}



	public void displayMovment(MovementDTO movement) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Détails du Mouvement ").append(movement.getId()).append(" ===\n");
		sb.append("code : ").append(movement.getAccountingCode()).append("\n");


		System.out.println(sb.toString());
	}

	public void displayResumMovement(MovementDTO movementDTO) {

	}

	public void displayInlineMovment(MovementDTO movementDTO) {
		System.out.println(getInlineMovement(movementDTO));
	}
}
