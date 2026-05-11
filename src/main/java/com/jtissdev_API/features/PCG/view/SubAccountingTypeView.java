package com.jtissdev_API.features.PCG.view;

import com.jtissdev_API.features.PCG.dto.SubAccountingType;

/**
 * Represents a SubAccountingTypeView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public class SubAccountingTypeView {

	private final AccountingTypeDetailView accountingTypeDetailView = new AccountingTypeDetailView();

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

	public void displayCascadeSubAccountingType(SubAccountingType subAccountingType, String indent) {
		this.displaySubAccountingTypeHeader(subAccountingType);
		String nextIndent = indent + "    ";
		subAccountingType.getDetailsList().forEach(detail ->
				                                           accountingTypeDetailView.displayAccountingDetail(detail, nextIndent)
		);
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
