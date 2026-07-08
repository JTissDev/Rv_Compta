package com.jtissdev.features.pcg.view;

import com.jtissdev.features.pcg.dto.AccountingTypeDetails;

/**
 * The AccountingTypeDetailView class is responsible for displaying the details of an accounting type in a console-based user interface.
 * It provides a method to display the information of an AccountingTypeDTO object, including its code, description, and associated accounts.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class AccountingTypeDetailView {


	public void displayAccountingDetail(AccountingTypeDetails accountingTypeDetails) {
		displayAccountingDetail(accountingTypeDetails, "");
	}

	public void displayAccountingDetail(AccountingTypeDetails accountingTypeDetails, String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Détails du Type Comptable : ").append(accountingTypeDetails.getName()).append(" === ***");
		sb.append(indent).append("• ").append("Code : ").append(accountingTypeDetails.getFullCode()).append("\n");
		sb.append("Description : ").append(accountingTypeDetails.getDescription()).append("\n");
		System.out.println(sb.toString());
	}

	public String getFormattedAccountingDetail(AccountingTypeDetails detail, int subIndent) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Détails du Type Comptable : ").append(detail.getName()).append(" === ***");
		sb.append("Code : ").append(detail.getFullCode()).append(" *** ");
		if (detail.getDescription() != null && !detail.getDescription().isEmpty()) {
			sb.append("\n").append(" *** ").append("Description : ").append(detail.getDescription()).append(" *** ");
		}


		return sb.toString().indent(subIndent);
	}

	/* public void displayAccountingDetail(AccountingTypeDetails accountingTypeDetails) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Détails du Type Comptable : ").append(accountingTypeDetails.getCode()).append(" ===\n");
		sb.append("Code : ").append(accountingTypeDetails.getCode()).append("\n");
		sb.append("Description : ").append(accountingTypeDetails.getDescription()).append("\n");
		sb.append("Comptes associés : \n");
		if (accountingTypeDetails.getAssociatedAccounts() != null && !accountingTypeDetails.getAssociatedAccounts().isEmpty()) {
			accountingTypeDetails.getAssociatedAccounts().forEach(account -> sb.append("- ").append(account).append("\n"));
		} else {
			sb.append("Aucun compte associé trouvé.\n");
		}

		System.out.println(sb.toString());
	} */
}
