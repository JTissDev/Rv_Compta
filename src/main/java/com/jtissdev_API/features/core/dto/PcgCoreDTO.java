package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Root data transfer object acting as the primary container for the
 * General Chart of Accounts (PCG).
 * * This class centralizes the accounting hierarchy to be shared across
 * different features of the application.
 * @see AccountingType
 * @author JtissDev
 * @version 1.1
 * @since 0.1
 */
public class PcgCoreDTO {

	/**
	 * List of main accounting classes (e.g., Class 1, Class 2, etc.)
	 * @since 0.1
	 */
	private List<AccountingType> accountingClasses;

	/**
	 * Default constructor initializing an empty collection of accounting classes.
	 * @since 0.1
	 */
	public PcgCoreDTO() {
		this.accountingClasses = new ArrayList<>();
	}

	/**
	 * Constructs a new PcgCoreDTO object with the specified list of accounting classes.
	 *
	 * @param accountingClasses The list of accounting classes to initialize the DTO with.
	 * @since 0.4
	 */
	public PcgCoreDTO(List<AccountingType> accountingClasses) {
		this();
		this.setAccountingClasses(accountingClasses);
	}

	/**
	 * Constructs a new PcgCoreDTO object by initializing it with the provided JSON array.
	 * Each element of the JSON array represents an accounting class and is converted into
	 * an {@link AccountingType} object and added to the PcgCoreDTO instance.
	 *
	 * @param jsonArray The JSON array containing accounting class data, where each element
	 *                  is expected to be a {@link JsonObject}.
	 * @since 0.4
	 */
	public PcgCoreDTO(JsonArray jsonArray) {
		this();
		for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
			this.addAccountingClass(new AccountingType(obj));
		}
	}

	/**
	 * Gets the list of all top-level accounting classes.
	 * * @return A list of {@link AccountingType} objects
	 * @since 0.1
	 */
	public List<AccountingType> getAccountingClasses() {
		return accountingClasses;
	}

	/**
	 * Sets the list of top-level accounting classes.
	 * * @param accountingClasses The list of classes to set
	 * @since 0.1
	 */
	public void setAccountingClasses(List<AccountingType> accountingClasses) {
		this.accountingClasses = accountingClasses;
	}

	/**
	 * Helper method to add a single accounting class to the container.
	 * * @param typeComptable The class to add
	 * @since 0.1
	 */
	public void addAccountingClass(AccountingType typeComptable) {
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
		for (AccountingType typeComptable : accountingClasses) {
			sb.append(typeComptable.toString()).append("\n");
		}
		sb.append("\n");

		return sb.toString();

	}
}