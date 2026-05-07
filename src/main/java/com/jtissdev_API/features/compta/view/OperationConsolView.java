package com.jtissdev_API.features.compta.view;

import com.jtissdev_API.features.compta.dto.MovementDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;

/**
 * Represents a OperationConsolView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public class OperationConsolView {

	public void displayOperation(OperationDTO operation) {
		StringBuilder sb = new StringBuilder();
		sb.append("------------------------------------------\n");
		sb.append("Affichage de l'operation : ").append(operation.getLibelle()).append("\n");
		sb.append("Réalisé le : ").append(operation.getDateOperation()).append("\n");
		if (operation.getDateComptable() != null) {
			sb.append("Comptablilisé le : ").append(operation.getDateComptable()).append("\n");
		}
		sb.append("*******************************************\n");
		sb.append("Detail de l'operation : \n");
		sb.append("Operation [id=").append(operation.getId() != null ? operation.getId() : "").append("\n")
				.append(", referenceDocument = ").append(operation.getReferenceDocument() != null ? operation.getReferenceDocument() : "undefined").append("\n")
				.append(", descriptif = ").append(operation.getDescriptif() != null ? operation.getDescriptif() : "undefined").append("\n")
				.append(", statutCode = ").append(operation.getStatutCode() != null ? operation.getStatutCode() : "undefined").append("\n")
				.append(", movements : ").append("\n");
		if (operation.getMovements() != null) {
			for (MovementDTO movement : operation.getMovements()) {
				sb.append(movement.toString()).append("\n");
			}
			sb.append(operation.isBalance() ? "Balance OK" : "Balance KO");
		} else sb.append("No movements found");
		sb.append("]");
		System.out.println(sb.toString());

	}
}
