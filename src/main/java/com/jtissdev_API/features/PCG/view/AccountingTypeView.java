package com.jtissdev_API.features.PCG.view;

import com.jtissdev_API.features.PCG.dto.AccountingType;

/**
 * The AccountingTypeView class is responsible for displaying the list of accounting types in a console-based user interface.
 * It provides a method to display the information of a list of AccountingTypeDTO objects, including their codes and descriptions.
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class AccountingTypeView {

	private final SubAccountingTypeView subAccountingTypeView = new SubAccountingTypeView();

	public void displayAccountingType(AccountingType accountingType) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Type Comptable : ").append(accountingType.getName()).append(" ===/n");
		sb.append("     Code : ").append(accountingType.getAccountCode()).append(" *** ");
		sb.append("     Description : ").append(accountingType.getDescription()).append("\n");

	}

	public void displaySubAccountingType(AccountingType accountingType) {
		this.displayAccountingTypeHeader(accountingType);
		accountingType.getSubTypes().forEach(subAccountingTypeView::displaySubAccountingType);

	}

	public void displayCascadeAccountingType(AccountingType accountingType) {
		this.displayAccountingTypeHeader(accountingType);
		String indent = "    ";
		accountingType.getSubTypes().forEach(sub ->
				                                     subAccountingTypeView.displayCascadeSubAccountingType(sub, indent)
		);
	}

	private void displayAccountingTypeHeader(AccountingType accountingType) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Type Comptable : ").append(accountingType.getName()).append(" ===\n");
		System.out.println(sb.toString());
	}
}
