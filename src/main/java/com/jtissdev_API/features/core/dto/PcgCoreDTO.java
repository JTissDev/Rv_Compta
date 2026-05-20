package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import jakarta.json.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Root data transfer object acting as the primary container for the
 * General Chart of Accounts (PCG).
 * This class centralizes the accounting hierarchy to be shared across
 * different features of the application.
 *
 * @author J.Tiss <jtissdev@gmail.com>
 * @version 1.3
 * @see AccountingType
 * @since 0.1
 */
public class PcgCoreDTO {

	// =======================================================
	// FIELDS                                               ==
	// =======================================================
	/**
	 * List of main accounting classes (e.g., Class 1, Class 2, etc.)
	 *
	 * @since 0.1
	 */
	private List<AccountingType> accountingClasses;

	// =======================================================
	// CONSTRUCTORS                                         ==
	// =======================================================
	/**
	 * Default constructor initializing an empty collection of accounting classes.
	 *
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.1
	 */
	public PcgCoreDTO() {
		this.accountingClasses = new ArrayList<>();
	}

	/**
	 * Constructs a new PcgCoreDTO object with the specified list of accounting classes.
	 *
	 * @param accountingClasses The list of accounting classes to initialize the DTO with.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.4
	 * @deprecated Manual field initialization is discouraged.
	 */
	@Deprecated(forRemoval = true, since = "0.6")
	public PcgCoreDTO(List<AccountingType> accountingClasses) {
		this();
		this.setAccountingClasses(accountingClasses);
	}

	/**
	 * Constructs a new PcgCoreDTO object by initializing it with the provided JSON array.
	 *
	 * @param jsonArray The JSON array containing accounting class data.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.4
	 */
	public PcgCoreDTO(JsonArray jsonArray) {
		this();
		if (jsonArray != null) {
			for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
				this.addAccountingClass(new AccountingType(obj));
			}
		}
	}

	/**
	 * Constructs a new PcgCoreDTO object by initializing it with the provided JSON object.
	 * @param json The JSON object containing the PCG data.
	 *             Expected keys: "accountingClasses" (JsonArray)
	 *
	 *             The JSON structure should be as follows:
	 *             {
	 *               "accountingClasses": [
	 *                 { ... }
	 *               ]
	 *             }
	 * @author J.Tiss
	 * @since 0.6
	 */
	public PcgCoreDTO(JsonObject json) {
		this();
		if (json != null) {
			JsonArray jsonArray = json.getJsonArray("accountingClasses");
			if (jsonArray != null) {
				for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
					this.addAccountingClass(new AccountingType(obj));
				}
			}
		}
	}

	// =======================================================
	// FLUENT METHODS                                       ==
	// =======================================================
	/**
	 * Gets the list of all top-level accounting classes.
	 *
	 * @return A list of {@link AccountingType} objects.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.1
	 */
	public List<AccountingType> getAccountingClasses() {
		return this.accountingClasses != null ? this.accountingClasses : new ArrayList<>();
	}

	/**
	 * Sets the list of accounting classes.
	 *
	 * @param accountingClasses The list of accounting types to assign.
	 * @return This PcgCoreDTO instance for method chaining.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.1.0
	 * @since 0.1
	 */
	public PcgCoreDTO setAccountingClasses(List<AccountingType> accountingClasses) {
		this.accountingClasses = (accountingClasses != null) ? accountingClasses : new ArrayList<>();
		return this;
	}

	/**
	 * Defines accounting classes from a JSON array by replacing the current list.
	 *
	 * @param jsonArray The JSON array containing Level 1 objects.
	 * @return This PcgCoreDTO instance for method chaining.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.1.0
	 * @since 0.5
	 */
	public PcgCoreDTO setAccountingClasses(JsonArray jsonArray) {
		this.accountingClasses = new ArrayList<>();
		if (jsonArray != null && !jsonArray.isEmpty()) {
			for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
				this.addAccountingClass(new AccountingType(jsonObject));
			}
		}
		return this;
	}

	/**
	 * Adds a single accounting class to the container.
	 *
	 * @param accountingType The class to add.
	 * @return This PcgCoreDTO instance for method chaining.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.1.0
	 * @since 0.1
	 */
	public PcgCoreDTO addAccountingClass(AccountingType accountingType) {
		if (accountingType != null) {
			List<AccountingType> classes = this.getAccountingClasses();
			classes.add(accountingType);
			this.setAccountingClasses(classes);
		}
		return this;
	}

	/**
	 * Appends multiple accounting classes from a JSON array to the current list.
	 *
	 * @param jsonArray The JSON array containing accounting class data.
	 * @return This PcgCoreDTO instance for method chaining.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.5
	 */
	public PcgCoreDTO addAccountingClass(JsonArray jsonArray) {
		if (jsonArray != null && !jsonArray.isEmpty()) {
			for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
				this.addAccountingClass(new AccountingType(jsonObject));
			}
		}
		return this;
	}

	// =======================================================
	// SERIALIZATION                                        ==
	// =======================================================
	/**
	 * Serializes the PcgCoreDTO object into a JsonObject.
	 *
	 * @return A {@link JsonObject} representation of the DTO.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.1.0
	 * @since 0.5
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		List<AccountingType> classes = this.getAccountingClasses();

		builder.add("size", classes.size());

		if (!classes.isEmpty()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (AccountingType accountingType : classes) {
				arrayBuilder.add(accountingType.toJson());
			}
			builder.add("accountingClasses", arrayBuilder);
		}

		return builder.build();
	}

	/**
	 * Returns a string representation of the PCG Core DTO.
	 *
	 * @return A formatted string summarizing the DTO content.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.1
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PCG Core DTO:\n")
				.append("====================\n")
				.append("Accounting Classes:\n")
				.append("====================\n");

		for (AccountingType accountingType : this.getAccountingClasses()) {
			sb.append(accountingType.toString()).append("\n");
		}
		sb.append("\n");

		return sb.toString();
	}
}