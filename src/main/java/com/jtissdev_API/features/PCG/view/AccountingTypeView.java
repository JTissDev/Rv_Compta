package com.jtissdev_API.features.PCG.view;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import com.jtissdev_API.features.core.SelectionContext;

/**
 * The AccountingTypeView class is responsible for displaying the list of accounting types in a console-based user interface.
 * It provides a method to display the information of a list of AccountingTypeDTO objects, including their codes and descriptions.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class AccountingTypeView {

	private final SubAccountingTypeView subAccountingTypeView = new SubAccountingTypeView();
	private final int indent = 5;


	public void displayAccountingType(AccountingType accountingType) {

		System.out.println(this.getAccountingTypeHeader(accountingType));
	}

	public void displayCascadeAccountingType(AccountingType accountingType) {
		this.displayResumAccountingType(accountingType);
		System.out.println(this.getCascadeAccountingType(accountingType));
	}

	public void displayCascadeAccountingType(AccountingType accountingType, SelectionContext context) {
		this.displayResumAccountingType(accountingType);
		if (context.getSubType() != null) {
			subAccountingTypeView.displayCascadeSubAccountingType(context.getSubType(), context);
		} else {
			System.out.println(this.getCascadeAccountingType(accountingType));
		}

	}


	public String getCascadeAccountingType(AccountingType accountingType) {
		StringBuilder sb = new StringBuilder();

		// 2. On joint tous les sous-types en cascade
		String allSubTypes = accountingType.getSubTypes().stream()
				                     .map(sub -> subAccountingTypeView.getCascadeSubAccountingType(sub, indent))
				                     .collect(java.util.stream.Collectors.joining(""));

		sb.append(allSubTypes);
		return sb.toString();
	}

	public void displayResumAccountingType(AccountingType accountingType) {

		System.out.println(getAccountingTypeHeader(accountingType));
	}

	private String getAccountingTypeHeader(AccountingType accountingType) {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Type Comptable : ").append(accountingType.getName()).append(" ===\n");
		sb.append("     Code : ").append(accountingType.getAccountCode()).append(" *** ");
		if ((accountingType.getDescription() != null) && (accountingType.getDescription() != "")) {
			sb.append("     Description : ").append(accountingType.getDescription());
		}
		sb.append("\n");
		return sb.toString();
	}

}
