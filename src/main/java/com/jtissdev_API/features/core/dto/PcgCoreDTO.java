package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.Type_Comptable;
import java.util.ArrayList;
import java.util.List;

/**
 * Root data transfer object acting as the primary container for the
 * General Chart of Accounts (PCG).
 * * This class centralizes the accounting hierarchy to be shared across
 * different features of the application.
 * * @author JtissDev
 * @version 1.1
 * @since 0.1
 */
public class PcgCoreDTO {

	/**
	 * List of main accounting classes (e.g., Class 1, Class 2, etc.)
	 * @since 0.1
	 */
	private List<Type_Comptable> accountingClasses;

	/**
	 * Default constructor initializing an empty collection of accounting classes.
	 * @since 0.1
	 */
	public PcgCoreDTO() {
		this.accountingClasses = new ArrayList<>();
	}

	/**
	 * Gets the list of all top-level accounting classes.
	 * * @return A list of {@link Type_Comptable} objects
	 * @since 0.1
	 */
	public List<Type_Comptable> getAccountingClasses() {
		return accountingClasses;
	}

	/**
	 * Sets the list of top-level accounting classes.
	 * * @param accountingClasses The list of classes to set
	 * @since 0.1
	 */
	public void setAccountingClasses(List<Type_Comptable> accountingClasses) {
		this.accountingClasses = accountingClasses;
	}

	/**
	 * Helper method to add a single accounting class to the container.
	 * * @param typeComptable The class to add
	 * @since 0.1
	 */
	public void addAccountingClass(Type_Comptable typeComptable) {
		if (this.accountingClasses == null) {
			this.accountingClasses = new ArrayList<>();
		}
		this.accountingClasses.add(typeComptable);
	}

	/**
	 * Returns a string representation of the object, summarizing its key details.
	 * The string includes an overview of the PCG Core DTO and a detailed listing
	 * of included accounting classes.
	 *
	 * @return A string representation of the PCG Core DTO including all accounting classes.
	 * @since 0.1
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PCG Core DTO:\n")
				.append("====================\n")
				.append("Accounting Classes:\n")
				.append("====================\n");
		for (Type_Comptable typeComptable : accountingClasses) {
			sb.append(typeComptable.toString()).append("\n");
		}
		sb.append("\n");

		return sb.toString();

	}
}