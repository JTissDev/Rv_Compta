package com.jtissdev_API.features.PCG.view;

import com.jtissdev_API.features.PCG.dto.SubAccountingType;
import com.jtissdev_API.features.core.dto.SelectionContext;

/**
 * Represents a SubAccountingTypeView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public class SubAccountingTypeView {

	private final AccountingTypeDetailView accountingTypeDetailView = new AccountingTypeDetailView();
	private final int subIndent = 5;

	public void displaySubAccountingType(SubAccountingType subAccountingType) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Sous-Type Comptable : ").append(subAccountingType.getName()).append(" ===\n");
		sb.append("     Code : ").append(subAccountingType.getFullCode()).append(" *** ");
		sb.append("     Description : ").append(subAccountingType.getDescription()).append("\n");
		System.out.println(sb.toString());

	}

	public void displayAccountingDetailsType(SubAccountingType subAccountingType) {
		this.displaySubAccountingTypeHeader(subAccountingType);
		subAccountingType.getDetailsList().forEach(accountingTypeDetailView::displayAccountingDetail);

	}

	public void displayCascadeSubAccountingType(SubAccountingType subAccountingType, int indent) {
		this.displaySubAccountingTypeHeader(subAccountingType);
		System.out.println(this.getCascadeSubAccountingType(subAccountingType, indent));
	}

	public void displayCascadeSubAccountingType(SubAccountingType subAccountingType, SelectionContext context) {

		if (context.getDetail() != null) {
			this.displaySubAccountingTypeHeader(subAccountingType);
			accountingTypeDetailView.displayAccountingDetail(context.getDetail());
		} else {
			System.out.println(this.getCascadeSubAccountingType(subAccountingType, subIndent));
		}

	}

	public String getCascadeSubAccountingType(SubAccountingType sub, int indent) {
		StringBuilder sb = new StringBuilder();
		// 1. On ajoute le header du sous-type
		String formattedHeader = getSubAccountingTypeHeader(sub)
				                         .indent(indent);
		sb.append(formattedHeader);
		// 2. On récupère et on joint tous les détails (Niveau 4)

		String allDetails = sub.getDetailsList().stream()
				                    .map(detail -> accountingTypeDetailView.getFormattedAccountingDetail(detail, indent + subIndent))
				                    .collect(java.util.stream.Collectors.joining("")); // Déjà avec des \n dans la vue détail
		sb.append(allDetails);
		String result = sb.toString();

		return result;
	}


	private String getSubAccountingTypeHeader(SubAccountingType sub) {
		// Version simplifiée pour l'exemple
		StringBuilder sb = new StringBuilder();
		sb.append(">> ").append(sub.getName()).append("\n");
		sb.append(" *** Code : ").append(sub.getFullCode());
		if (sub.getDescription() != null) {
			sb.append("    Description : ").append(sub.getDescription());
		}
		sb.append("\n");

		return sb.toString();
	}

	private void displaySubAccountingTypeHeader(SubAccountingType subAccountingType) {
		this.displaySubAccountingTypeHeader(subAccountingType, "");
	}

	private void displaySubAccountingTypeHeader(SubAccountingType subAccountingType, String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append(indent).append("└── [").append("=== Sous-Type Comptable : ").append(subAccountingType.getName()).append(" ===\n");
		System.out.println(sb.toString());
	}


}
