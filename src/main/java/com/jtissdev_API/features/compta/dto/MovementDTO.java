package com.jtissdev_API.features.compta.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing an atomic accounting movement line.
 * <p>
 * This class captures the financial impact of an operation on a specific account.
 * Following the 0.3.0 refactoring, this entity now carries the Tiers and
 * Payment Method information to allow multi-party or multi-modal transactions
 * within a single operation.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.2.0
 */
public class MovementDTO {

	/**
	 * Unique technical identifier for the movement line in the database.
	 * Corresponds to the 'N_id' column in the 'tab_lignes' table.
	 * @since 0.3.0
	 */
	private Long id;

	/**
	 * Represents the general accounting code associated with a movement.
	 * Used to categorize the financial transaction for bookkeeping purposes.
	 * @since 0.4
	 */
	private String accountingCode;

	/**
	 * Represents an optional complementary accounting code associated with a movement.
	 * This field may be used to specify additional accounting details beyond the primary code.
	 * @since 0.4
	 */
	private String complementaryAccountingCode;

	/**
	 * Technical code linking to the specific accounting detail in the PCP.
	 * References 'tab_detail_comptable'.
	 * @since 0.3.0
	 */
	private String accountDetailCode;

	/**
	 * Identifier of the Tiers involved in this specific movement.
	 * References the 'N_id' in 'tab_tiers'.
	 * @since 0.3.0
	 */
	private Long tiersId;

	/**
	 * Technical code of the payment method used for this specific line.
	 * References 'ref_moyen_paiement'.
	 * @since 0.3.0
	 */
	private String paiementCode;

	/**
	 * The amount to be debited. Initialized to Zero.
	 * @since 0.3.0
	 */
	private BigDecimal debitAmount;

	/**
	 * The amount to be credited. Initialized to Zero.
	 * @since 0.3.0
	 */
	private BigDecimal creditAmount;

	/**
	 * Specific commentary or description for this individual movement line.
	 * Corresponds to the 'Description' column.
	 * @since 0.3.0
	 */
	private String description;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * Initializes debit and credit amounts to {@link BigDecimal#ZERO}.
	 * @since 0.3.0
	 */
	public MovementDTO() {
		this.debitAmount = BigDecimal.ZERO;
		this.creditAmount = BigDecimal.ZERO;
	}

	public MovementDTO(JsonObject json) {
		this();
		if (json.containsKey("id")) {
			this.setId(json.getJsonNumber("id").longValue());
		}
		if (json.containsKey("tiersId")) {
			this.setTiersId(json.getJsonNumber("tiersId").longValue());
		}
		if (json.containsKey("paiementCode")) {
			this.setPaiementCode(json.getString("paiementCode"));
		}

		if (json.containsKey("accountDetailCode")) {
			this.setAccountDetailCode(json.getString("accountDetailCode"));
		}
		if (json.containsKey("debitAmount")) {
			this.setDebitAmount(json.getJsonNumber("debitAmount").bigDecimalValue());
		}
	}

	/**
	 * Constructor for new movements (without ID).
	 * Used during the initial creation of an operation's lines.
	 *
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param accountDetailCode the PCP detail code
	 * @param debitAmount         the debit amount
	 * @param creditAmount        the credit amount
	 * @param description          the line description
	 * @since 0.3.0
	 */
	public MovementDTO(Long tiersId, String paiementCode, String accountDetailCode,
	                   BigDecimal debitAmount, BigDecimal creditAmount, String description) {
		this();
		this.setTiersId( tiersId );
		this.setPaiementCode(paiementCode) ;
		this.accountDetailCode = accountDetailCode;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.description = description;
	}

	/**
	 * Full constructor.
	 * Used when retrieving existing lines from the database.
	 *
	 * @param id                   the technical identifier
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param accountDetailCode the PCP detail code
	 * @param debitAmount         the debit amount
	 * @param creditAmount        the credit amount
	 * @param description          the line description
	 * @since 0.3.0
	 */
	public MovementDTO(Long id, Long tiersId, String paiementCode, String accountDetailCode,
	                   BigDecimal debitAmount, BigDecimal creditAmount, String description) {
		this(tiersId, paiementCode, accountDetailCode, debitAmount, creditAmount, description);
		this.id = id;
	}

	/**
	 * Minimal constructor for quick instantiation without amounts.
	 * Useful for temporary structures before financial calculation.
	 *
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param accountDetailCode the PCP detail code
	 * @since 0.3.0
	 */
	public MovementDTO(Long tiersId, String paiementCode, String accountDetailCode) {
		this();
		this.tiersId = tiersId;
		this.paiementCode = paiementCode;
		this.accountDetailCode = accountDetailCode;
	}

	// =========================================================
	// == GETTERS  (Fluent API)                      ==
	// =========================================================

	/**
	 * @return the unique technical identifier.
	 * @since 0.3.0
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Retrieves the accounting code associated with this instance.
	 *
	 * @return the accounting code as a string.
	 * @since 0.4
	 */
	public String getAccountingCode() {
		return this.accountingCode;
	}

	/**
	 * Retrieves the complementary accounting code associated with this instance.
	 *
	 * @return the complementary accounting code as a string.
	 * @since 0.4
	 */
	public String getComplementaryAccountingCode() {
		return this.complementaryAccountingCode;
	}

	/**
	 * @return the PCP detail code.
	 * @since 0.3.0
	 */
	public String getAccountDetailCode() {
		return accountDetailCode;
	}

	/**
	 * @return the associated Tiers ID.
	 * @since 0.3.0
	 */
	public Long getTiersId() {
		return tiersId;
	}

	/**
	 * @return the payment method code.
	 * @since 0.3.0
	 */
	public String getPaiementCode() {
		return paiementCode;
	}

	/**
	 * @return the debit amount as {@link BigDecimal}.
	 * @since 0.3.0
	 */
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @return the credit amount as {@link BigDecimal}.
	 * @since 0.3.0
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @return the line description.
	 * @since 0.3.0
	 */
	public String getDescription() {
		return description;
	}

	// =========================================================
	// == SETTERS  (Fluent API)                      ==
	// =========================================================

	/**
	 * @param id the technical ID to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Sets the accounting code associated with this instance.
	 *
	 * @param accountingCode the accounting code to be assigned
	 * @since 0.4
	 */
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	/**
	 * Assigns a complementary accounting code to this instance.
	 *
	 * @param complementaryAccountingCode the complementary accounting code to set
	 * @since 0.4
	 */
	public void setComplementaryAccountingCode(String complementaryAccountingCode) {
		this.complementaryAccountingCode = complementaryAccountingCode;
	}

	/**
	 * @param accountDetailCode the code from tab_detail_comptable.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setAccountDetailCode(String accountDetailCode) {
		this.accountDetailCode = accountDetailCode;
		return this;
	}

	/**
	 * @param tiersId the ID from tab_tiers to link.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setTiersId(Long tiersId) {
		this.tiersId = tiersId;
		return this;
	}

	/**
	 * @param paiementCode the code (e.g., 'CB') to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setPaiementCode(String paiementCode) {
		this.paiementCode = paiementCode;
		return this;
	}

	/**
	 * @param debitAmount the amount to debit.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
		return this;
	}

	/**
	 * @param creditAmount the amount to credit.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}

	/**
	 * @param description the text to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	// =========================================================
	// == OTHERS METHODS                                      ==
	// =========================================================

	/**
	 * Returns a string representation of the MovementDTO object.
	 * The string includes the values of its fields, such as id, tiersId, paiementCode,
	 * accountDetailCode, debitAmount, creditAmount, and description.
	 *
	 * @return a string representation of the MovementDTO object.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MovementDTO [id=").append(id)
				.append(", accountingCode=").append(accountingCode)
				.append(", complementaryAccountingCode=").append(complementaryAccountingCode)
				.append(", accountDetailCode=").append(accountDetailCode)
				.append(", tiersId=").append(tiersId)
				.append(", paiementCode=").append(paiementCode)
				.append(", debitAmount=").append(debitAmount)
				.append(", creditAmount=").append(creditAmount)
				.append(", description=").append(description).append("]");
		return sb.toString();
	}

	/**
	 * Converts the current instance of MovementDTO to a JSON representation.
	 * The JSON will include non-null fields such as id, accountingCode,
	 * complementaryAccountingCode, accountDetailCode, tiersId, paiementCode,
	 * debitAmount, creditAmount, and description.
	 *
	 * @return a JsonObject representing the current MovementDTO instance.
	 * @since 0.4
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		if (this.id != null) {
			builder.add("id", this.id);
		}
		if (this.accountingCode != null) {
			builder.add("accountingCode", this.accountingCode);
		}
		if (this.complementaryAccountingCode != null) {
				builder.add("complementaryAccountingCode", this.complementaryAccountingCode);
		}
		if (this.accountDetailCode != null) {
			builder.add("accountDetailCode", this.accountDetailCode);
		}
		if (this.tiersId != null) {
				builder.add("tiersId", this.tiersId);
		}
		if (this.paiementCode != null) {
				builder.add("paiementCode", this.paiementCode);
		}
		if (this.debitAmount != null) {
			builder.add("debitAmount", this.debitAmount);
		}
		if (this.creditAmount != null) {
			builder.add("creditAmount", this.creditAmount);
		}
		if (this.description != null) {
			builder.add("description", this.description);
		}
		return builder.build();
	}
}