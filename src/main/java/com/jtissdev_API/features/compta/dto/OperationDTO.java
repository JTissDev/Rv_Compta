package com.jtissdev_API.features.compta.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing the header of an accounting operation.
 * <p>
 * This class acts as the primary container for a transaction. According to the
 * version 0.3.0 database refactoring, this header focuses on descriptive
 * and temporal metadata.
 * </p>
 * <p>
 * Financial details such as Tiers, Payment Methods, and Amounts are now
 * delegated to the associated {@link MovementDTO} list to allow for
 * complex, multi-modal transactions.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.2.0
 */
public class OperationDTO {

	/**
	 * Unique technical identifier for the operation in the database.
	 * Corresponds to the 'N_id' column in the 'operations' table.
	 * @since 0.3.0
	 */
	private Long id;

	/**
	 * The real date when the physical transaction or event took place.
	 * Corresponds to the 'Date_Operation' column.
	 * @since 0.3.0
	 */
	private LocalDate dateOperation;

	/**
	 * The official date used for accounting records and fiscal periods.
	 * Corresponds to the 'Date_Comptable' column.
	 * @since 0.3.0
	 */
	private LocalDate dateComptable;

	/**
	 * Human-readable short summary or title describing the operation.
	 * Corresponds to the 'Libelle' column.
	 * @since 0.3.0
	 */
	private String libelle;

	/**
	 * External reference string to a supporting document (Invoice, Receipt).
	 * Corresponds to the 'Reference_Document' column.
	 * @since 0.3.0
	 */
	private String referenceDocument;

	/**
	 * Optional extended description or notes providing more context.
	 * Corresponds to the 'Descriptif' column.
	 * @since 0.3.0
	 */
	private String descriptif;

	/**
	 * Technical status code linking to the operation's current state.
	 * References 'ref_statut'.
	 * @since 0.3.0
	 */
	private String statutCode;

	/**
	 * Internal list of atomic financial movements (lines) composing this operation.
	 * @since 0.3.0
	 */
	private List<MovementDTO> movements;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * Initializes an empty list of movements.
	 * @since 0.3.0
	 */
	public OperationDTO() {
		this.movements = new ArrayList<>();
	}

	/**
	 * Constructor for new operations (without ID and without movements).
	 * Useful during the initial creation phase before lines are added.
	 *
	 * @param dateOperation    the date of the transaction
	 * @param dateComptable    the accounting record date
	 * @param libelle          the short label
	 * @param referenceDocument the document reference
	 * @param descriptif       the detailed description
	 * @param statutCode       the initial status code
	 * @since 0.3.0
	 */
	public OperationDTO(LocalDate dateOperation, LocalDate dateComptable, String libelle,
	                    String referenceDocument, String descriptif, String statutCode) {
		this();
		this.dateOperation = dateOperation;
		this.dateComptable = dateComptable;
		this.libelle = libelle;
		this.referenceDocument = referenceDocument;
		this.descriptif = descriptif;
		this.statutCode = statutCode;
	}

	/**
	 * Constructor for updates or persistence without movements.
	 * Used when updating header metadata in the database.
	 *
	 * @param id               the technical ID
	 * @param dateOperation    the date of the transaction
	 * @param dateComptable    the accounting record date
	 * @param libelle          the short label
	 * @param referenceDocument the document reference
	 * @param descriptif       the detailed description
	 * @param statutCode       the status code
	 * @since 0.3.0
	 */
	public OperationDTO(Long id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
	                    String referenceDocument, String descriptif, String statutCode) {
		this(dateOperation, dateComptable, libelle, referenceDocument, descriptif, statutCode);
		this.id = id;
	}

	/**
	 * Full constructor.
	 * Used when retrieving a complete operation with all its lines from the database.
	 *
	 * @param id               the technical ID
	 * @param dateOperation    the date of the transaction
	 * @param dateComptable    the accounting record date
	 * @param libelle          the short label
	 * @param referenceDocument the document reference
	 * @param descriptif       the detailed description
	 * @param statutCode       the status code
	 * @param movements        the list of movement lines
	 * @since 0.3.0
	 */
	public OperationDTO(Long id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
	                    String referenceDocument, String descriptif, String statutCode, List<MovementDTO> movements) {
		this(id, dateOperation, dateComptable, libelle, referenceDocument, descriptif, statutCode);
		this.setMovements(movements);
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
	 * @param id the technical ID to assign.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the date of the transaction.
	 * @since 0.3.0
	 */
	public LocalDate getDateOperation() {
		return dateOperation;
	}

	/**
	 * @param dateOperation the date to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setDateOperation(LocalDate dateOperation) {
		this.dateOperation = dateOperation;
		return this;
	}

	/**
	 * @return the accounting record date.
	 * @since 0.3.0
	 */
	public LocalDate getDateComptable() {
		return dateComptable;
	}

	/**
	 * @param dateComptable the date to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setDateComptable(LocalDate dateComptable) {
		this.dateComptable = dateComptable;
		return this;
	}

	/**
	 * @return the operation label.
	 * @since 0.3.0
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the label to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setLibelle(String libelle) {
		this.libelle = libelle;
		return this;
	}

	/**
	 * @return the document reference.
	 * @since 0.3.0
	 */
	public String getReferenceDocument() {
		return referenceDocument;
	}

	/**
	 * @param referenceDocument the reference to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
		return this;
	}

	/**
	 * @return the description.
	 * @since 0.3.0
	 */
	public String getDescriptif() {
		return descriptif;
	}

	/**
	 * @param descriptif the description to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setDescriptif(String descriptif) {
		this.descriptif = descriptif;
		return this;
	}

	/**
	 * @return the technical status code.
	 * @since 0.3.0
	 */
	public String getStatutCode() {
		return statutCode;
	}

	/**
	 * @param statutCode the code to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setStatutCode(String statutCode) {
		this.statutCode = statutCode;
		return this;
	}

	/**
	 * @return the list of associated movement lines.
	 * @since 0.3.0
	 */
	public List<MovementDTO> getMovements() {
		return movements;
	}

	/**
	 * @param movements the list of lines to set.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO setMovements(List<MovementDTO> movements) {
		this.movements = movements != null ? movements : new ArrayList<>();
		return this;
	}

	/**
	 * Adds a movement line.
	 * @param movement the line to add.
	 * @return this instance for chaining.
	 * @since 0.3.0
	 */
	public OperationDTO addMovement(MovementDTO movement) {
		if (movement != null) {
			this.movements.add(movement);
		}
		return this;
	}

	/**
	 * Returns a string representation of the OperationDTO object.
	 * The representation includes the operation's ID, dates, label, document reference, description, status code,
	 * and a detailed list of associated movements.
	 *
	 * @return a formatted string representing the current state of the OperationDTO instance.
	 *
	 * @since 0.4
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Operation [id=").append(id).
				append(", dateOperation = ").append(dateOperation)
				.append(", dateComptable = ").append(dateComptable)
				.append(", libelle = '").append(libelle).append('\'').
				append(", referenceDocument = '").append(referenceDocument).append('\'')
				.append(", descriptif = '").append(descriptif).append('\'').
				append(", statutCode = '").append(statutCode).append('\'')
				.append(", movements : ").append("\n");
		for (MovementDTO movement : movements) {
			sb.append(movement.toString()).append("\n");
		}
		return sb.toString();
	}
}