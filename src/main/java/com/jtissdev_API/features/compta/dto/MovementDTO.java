package com.jtissdev_API.features.compta.dto;

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
 * @version 1.1.0
 */
public class MovementDTO {

	/**
	 * Unique technical identifier for the movement line in the database.
	 * Corresponds to the 'N_id' column in the 'tab_lignes' table.
	 * @since 0.3.0
	 */
	private Long id;

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
	 * Technical code linking to the specific accounting detail in the PCP.
	 * References 'tab_detail_comptable'.
	 * @since 0.3.0
	 */
	private String codeDetailsComptable;

	/**
	 * The amount to be debited. Initialized to Zero.
	 * @since 0.3.0
	 */
	private BigDecimal montantDebit;

	/**
	 * The amount to be credited. Initialized to Zero.
	 * @since 0.3.0
	 */
	private BigDecimal montantCredit;

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
		this.montantDebit = BigDecimal.ZERO;
		this.montantCredit = BigDecimal.ZERO;
	}

	/**
	 * Constructor for new movements (without ID).
	 * Used during the initial creation of an operation's lines.
	 *
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param codeDetailsComptable the PCP detail code
	 * @param montantDebit         the debit amount
	 * @param montantCredit        the credit amount
	 * @param description          the line description
	 * @since 0.3.0
	 */
	public MovementDTO(Long tiersId, String paiementCode, String codeDetailsComptable,
	                   BigDecimal montantDebit, BigDecimal montantCredit, String description) {
		this();
		this.tiersId = tiersId;
		this.paiementCode = paiementCode;
		this.codeDetailsComptable = codeDetailsComptable;
		this.montantDebit = montantDebit;
		this.montantCredit = montantCredit;
		this.description = description;
	}

	/**
	 * Full constructor.
	 * Used when retrieving existing lines from the database.
	 *
	 * @param id                   the technical identifier
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param codeDetailsComptable the PCP detail code
	 * @param montantDebit         the debit amount
	 * @param montantCredit        the credit amount
	 * @param description          the line description
	 * @since 0.3.0
	 */
	public MovementDTO(Long id, Long tiersId, String paiementCode, String codeDetailsComptable,
	                   BigDecimal montantDebit, BigDecimal montantCredit, String description) {
		this(tiersId, paiementCode, codeDetailsComptable, montantDebit, montantCredit, description);
		this.id = id;
	}

	/**
	 * Minimal constructor for quick instantiation without amounts.
	 * Useful for temporary structures before financial calculation.
	 *
	 * @param tiersId              the ID of the associated tiers
	 * @param paiementCode         the payment method code
	 * @param codeDetailsComptable the PCP detail code
	 * @since 0.3.0
	 */
	public MovementDTO(Long tiersId, String paiementCode, String codeDetailsComptable) {
		this();
		this.tiersId = tiersId;
		this.paiementCode = paiementCode;
		this.codeDetailsComptable = codeDetailsComptable;
	}

	// =========================================================
	// == GETTERS / SETTERS (Fluent API)                      ==
	// =========================================================

	/**
	 * @return the unique technical identifier.
	 * @since 0.3.0
	 */
	public Long getId() {
		return id;
	}

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
	 * @return the associated Tiers ID.
	 * @since 0.3.0
	 */
	public Long getTiersId() {
		return tiersId;
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
	 * @return the payment method code.
	 * @since 0.3.0
	 */
	public String getPaiementCode() {
		return paiementCode;
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
	 * @return the PCP detail code.
	 * @since 0.3.0
	 */
	public String getCodeDetailsComptable() {
		return codeDetailsComptable;
	}

	/**
	 * @param codeDetailsComptable the code from tab_detail_comptable.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setCodeDetailsComptable(String codeDetailsComptable) {
		this.codeDetailsComptable = codeDetailsComptable;
		return this;
	}

	/**
	 * @return the debit amount as {@link BigDecimal}.
	 * @since 0.3.0
	 */
	public BigDecimal getMontantDebit() {
		return montantDebit;
	}

	/**
	 * @param montantDebit the amount to debit.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setMontantDebit(BigDecimal montantDebit) {
		this.montantDebit = montantDebit;
		return this;
	}

	/**
	 * @return the credit amount as {@link BigDecimal}.
	 * @since 0.3.0
	 */
	public BigDecimal getMontantCredit() {
		return montantCredit;
	}

	/**
	 * @param montantCredit the amount to credit.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public MovementDTO setMontantCredit(BigDecimal montantCredit) {
		this.montantCredit = montantCredit;
		return this;
	}

	/**
	 * @return the line description.
	 * @since 0.3.0
	 */
	public String getDescription() {
		return description;
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
}