package com.jtissdev_API.features.compta.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing an Accounting Journal.
 * <p>
 * The Journal acts as a chronological container for all {@link OperationDTO}.
 * In the context of version 0.3.0, it serves as the primary structure for
 * importing Excel data, where each row of the file will be transformed into
 * an operation integrated into this journal.
 * </p>
 *
 * @author J.Tiss
 * @since 0.3.0
 * @version 1.0.0
 */
public class JournalDTO {

	/**
	 * Unique technical identifier for the journal instance.
	 * @since 0.3.0
	 */
	private Long id;

	/**
	 * Human-readable name or title of the journal (e.g., "General Journal 2024").
	 * @since 0.3.0
	 */
	private String nom;

	/**
	 * The start date of the period covered by this journal.
	 * @since 0.3.0
	 */
	private LocalDate dateDebut;

	/**
	 * The end date of the period covered by this journal.
	 * @since 0.3.0
	 */
	private LocalDate dateFin;

	/**
	 * Technical code representing the type of journal (e.g., 'ACHAT', 'VENTE', 'BANQUE').
	 * @since 0.3.0
	 */
	private String typeJournalCode;

	/**
	 * List of operations contained within this journal.
	 * Each operation represents a transaction imported or manually entered.
	 * @since 0.3.0
	 */
	private List<OperationDTO> operations;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * Initializes the operations list to an empty {@link ArrayList}.
	 * @since 0.3.0
	 */
	public JournalDTO() {
		this.operations = new ArrayList<>();
	}

	/**
	 * Constructor for creating a new journal (without ID).
	 * Useful for the Excel import preparation phase.
	 *
	 * @param nom             the journal name
	 * @param dateDebut       the period start date
	 * @param dateFin         the period end date
	 * @param typeJournalCode the technical type code
	 * @since 0.3.0
	 */
	public JournalDTO(String nom, LocalDate dateDebut, LocalDate dateFin, String typeJournalCode) {
		this();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeJournalCode = typeJournalCode;
	}

	/**
	 * Constructor for existing journal updates or persistence.
	 *
	 * @param id              the technical ID
	 * @param nom             the journal name
	 * @param dateDebut       the period start date
	 * @param dateFin         the period end date
	 * @param typeJournalCode the technical type code
	 * @since 0.3.0
	 */
	public JournalDTO(Long id, String nom, LocalDate dateDebut, LocalDate dateFin, String typeJournalCode) {
		this(nom, dateDebut, dateFin, typeJournalCode);
		this.id = id;
	}

	/**
	 * Full constructor for retrieving a journal with all its operations.
	 *
	 * @param id              the technical ID
	 * @param nom             the journal name
	 * @param dateDebut       the period start date
	 * @param dateFin         the period end date
	 * @param typeJournalCode the technical type code
	 * @param operations      the list of associated operations
	 * @since 0.3.0
	 */
	public JournalDTO(Long id, String nom, LocalDate dateDebut, LocalDate dateFin,
	                  String typeJournalCode, List<OperationDTO> operations) {
		this(id, nom, dateDebut, dateFin, typeJournalCode);
		this.setOperations(operations);
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
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name of the journal.
	 * @since 0.3.0
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the name to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setNom(String nom) {
		this.nom = nom;
		return this;
	}

	/**
	 * @return the start date of the journal's period.
	 * @since 0.3.0
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the start date to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
		return this;
	}

	/**
	 * @return the end date of the journal's period.
	 * @since 0.3.0
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the end date to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
		return this;
	}

	/**
	 * @return the technical type code of the journal.
	 * @since 0.3.0
	 */
	public String getTypeJournalCode() {
		return typeJournalCode;
	}

	/**
	 * @param typeJournalCode the type code to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setTypeJournalCode(String typeJournalCode) {
		this.typeJournalCode = typeJournalCode;
		return this;
	}

	/**
	 * @return the list of operations within this journal.
	 * @since 0.3.0
	 */
	public List<OperationDTO> getOperations() {
		return operations;
	}

	/**
	 * Sets the list of operations. Initializes an empty list if null is provided.
	 *
	 * @param operations the list of {@link OperationDTO} to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO setOperations(List<OperationDTO> operations) {
		this.operations = operations != null ? operations : new ArrayList<>();
		return this;
	}

	/**
	 * Adds a single operation to the journal.
	 *
	 * @param operation the {@link OperationDTO} to add.
	 * @return this {@link JournalDTO} instance for chaining.
	 * @since 0.3.0
	 */
	public JournalDTO addOperation(OperationDTO operation) {
		if (operation != null) {
			this.operations.add(operation);
		}
		return this;
	}
}