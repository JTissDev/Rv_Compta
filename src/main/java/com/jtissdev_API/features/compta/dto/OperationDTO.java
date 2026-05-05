package com.jtissdev_API.features.compta.dto;

import jakarta.json.*;

import java.math.BigDecimal;
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
 * @version 1.4.0
 * @since 0.3.0
 */
public class OperationDTO {

	/**
	 * Unique technical identifier for the operation in the database.
	 * Corresponds to the 'N_id' column in the 'operations' table.
	 *
	 * @since 0.3.0
	 */
	private Integer id;

	/**
	 * The real date when the physical transaction or event took place.
	 * Corresponds to the 'Date_Operation' column.
	 *
	 * @since 0.3.0
	 */
	private LocalDate dateOperation;

	/**
	 * The official date used for accounting records and fiscal periods.
	 * Corresponds to the 'Date_Comptable' column.
	 *
	 * @since 0.3.0
	 */
	private LocalDate dateComptable;

	/**
	 * Human-readable short summary or title describing the operation.
	 * Corresponds to the 'Libelle' column.
	 *
	 * @since 0.3.0
	 */
	private String libelle;

	/**
	 * External reference string to a supporting document (Invoice, Receipt).
	 * Corresponds to the 'Reference_Document' column.
	 *
	 * @since 0.3.0
	 */
	private String referenceDocument;

	/**
	 * Optional extended description or notes providing more context.
	 * Corresponds to the 'Descriptif' column.
	 *
	 * @since 0.3.0
	 */
	private String descriptif;

	/**
	 * Technical status code linking to the operation's current state.
	 * References 'ref_statut'.
	 *
	 * @since 0.3.0
	 */
	private String statutCode;

	/**
	 * Internal list of atomic financial movements (lines) composing this operation.
	 *
	 * @since 0.3.0
	 */
	private List<MovementDTO> movements;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * Initializes an empty list of movements.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO() {
		this.movements = new ArrayList<>();
	}

	/**
	 * Constructs an {@code OperationDTO} object by parsing the provided {@code JsonObject}.
	 * Populates the fields of the object based on the keys and values present in the JSON data.
	 *
	 * @param jsonObject
	 * 		the JSON object containing the data to initialize the {@code OperationDTO}.
	 * 		The following keys may be present in the JSON:
	 * 		                 <ul>
	 * 		                 <li>"id" (optional): A JSON number representing the technical ID.</li>
	 * 		                 <li>"dateOperation" (optional): A string representing the operation date in ISO-8601 format.</li>
	 * 		                 <li>"dateComptable" (optional): A string representing the accounting record date in ISO-8601 format.</li>
	 * 		                 <li>"libelle" (optional): A string representing the label or short description.</li>
	 * 		                 <li>"referenceDocument" (optional): A string with the document reference.</li>
	 * 		                 <li>"descriptif" (optional): A detailed string description of the operation.</li>
	 * 		                 <li>"statutCode" (optional): A string representing the status code.</li>
	 * 		                 <li>"movements" (optional): An array of JSON objects representing movement data, which will be converted into {@code MovementDTO} objects and added to the list
	 * 		of movements.</li>
	 * 		                 </ul>
	 * @since 0.4
	 */
	public OperationDTO(JsonObject jsonObject) {
		this();
		if (jsonObject.containsKey("id")) {
			this.setId(jsonObject.getInt("id"));
		}
		if (jsonObject.containsKey("dateOperation")) {
			this.setDateOperation(LocalDate.parse(jsonObject.getString("dateOperation")));
		}
		if (jsonObject.containsKey("dateComptable")) {
			this.setDateComptable(LocalDate.parse(jsonObject.getString("dateComptable")));
		}
		if (jsonObject.containsKey("libelle")) {
			this.setLibelle(jsonObject.getString("libelle"));
		}
		if (jsonObject.containsKey("referenceDocument")) {
			this.setReferenceDocument(jsonObject.getString("referenceDocument"));
		}
		if (jsonObject.containsKey("descriptif")) {
			this.setDescriptif(jsonObject.getString("descriptif"));
		}
		if (jsonObject.containsKey("statutCode")) {
			this.setStatutCode(jsonObject.getString("statutCode"));
		}
		if (jsonObject.containsKey("movements") && !jsonObject.isNull("movements")) {
			for (JsonObject movement : jsonObject.getJsonArray("movements").getValuesAs(JsonObject.class)) {
				this.getMovements().add(new MovementDTO(movement));
			}
		}

	}

	/**
	 * Constructor for new operations (without ID and without movements).
	 * Useful during the initial creation phase before lines are added.
	 *
	 * @param dateOperation
	 * 		the date of the transaction
	 * @param dateComptable
	 * 		the accounting record date
	 * @param libelle
	 * 		the short label
	 * @param referenceDocument
	 * 		the document reference
	 * @param descriptif
	 * 		the detailed description
	 * @param statutCode
	 * 		the initial status code
	 * @since 0.3.0
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * 		Use {@link OperationDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
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
	 * @param id
	 * 		the technical ID
	 * @param dateOperation
	 * 		the date of the transaction
	 * @param dateComptable
	 * 		the accounting record date
	 * @param libelle
	 * 		the short label
	 * @param referenceDocument
	 * 		the document reference
	 * @param descriptif
	 * 		the detailed description
	 * @param statutCode
	 * 		the status code
	 * @since 0.3.0
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * 		Use {@link OperationDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public OperationDTO(Integer id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
	                    String referenceDocument, String descriptif, String statutCode) {
		this(dateOperation, dateComptable, libelle, referenceDocument, descriptif, statutCode);
		this.id = id;
	}

	/**
	 * Full constructor.
	 * Used when retrieving a complete operation with all its lines from the database.
	 *
	 * @param id
	 * 		the technical ID
	 * @param dateOperation
	 * 		the date of the transaction
	 * @param dateComptable
	 * 		the accounting record date
	 * @param libelle
	 * 		the short label
	 * @param referenceDocument
	 * 		the document reference
	 * @param descriptif
	 * 		the detailed description
	 * @param statutCode
	 * 		the status code
	 * @param movements
	 * 		the list of movement lines
	 * @since 0.3.0
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * 		Use {@link OperationDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public OperationDTO(Integer id, LocalDate dateOperation, LocalDate dateComptable, String libelle,
	                    String referenceDocument, String descriptif, String statutCode, List<MovementDTO> movements) {
		this(id, dateOperation, dateComptable, libelle, referenceDocument, descriptif, statutCode);
		this.setMovements(movements);
	}

	// =========================================================
	// == GETTERS / SETTERS (Fluent API)                      ==
	// =========================================================

	/**
	 * @return the unique technical identifier.
	 *
	 * @since 0.3.0
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id
	 * 		the technical ID to assign.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the date of the transaction.
	 *
	 * @since 0.3.0
	 */
	public LocalDate getDateOperation() {
		return this.dateOperation;
	}

	/**
	 * @param dateOperation
	 * 		the date to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setDateOperation(LocalDate dateOperation) {
		this.dateOperation = dateOperation;
		return this;
	}

	/**
	 * @return the accounting record date.
	 *
	 * @since 0.3.0
	 */
	public LocalDate getDateComptable() {
		return this.dateComptable;
	}

	/**
	 * @param dateComptable
	 * 		the date to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setDateComptable(LocalDate dateComptable) {
		this.dateComptable = dateComptable;
		return this;
	}

	/**
	 * @return the operation label.
	 *
	 * @since 0.3.0
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * @param libelle
	 * 		the label to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setLibelle(String libelle) {
		this.libelle = libelle;
		return this;
	}

	/**
	 * @return the document reference.
	 *
	 * @since 0.3.0
	 */
	public String getReferenceDocument() {
		return this.referenceDocument;
	}

	/**
	 * @param referenceDocument
	 * 		the reference to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
		return this;
	}

	/**
	 * @return the description.
	 *
	 * @since 0.3.0
	 */
	public String getDescriptif() {
		return this.descriptif;
	}

	/**
	 * @param descriptif
	 * 		the description to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setDescriptif(String descriptif) {
		this.descriptif = descriptif;
		return this;
	}

	/**
	 * @return the technical status code.
	 *
	 * @since 0.3.0
	 */
	public String getStatutCode() {
		return this.statutCode;
	}

	/**
	 * @param statutCode
	 * 		the code to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setStatutCode(String statutCode) {
		this.statutCode = statutCode;
		return this;
	}

	/**
	 * @return the list of associated movement lines.
	 *
	 * @since 0.3.0
	 */
	public List<MovementDTO> getMovements() {
		return this.movements;
	}

	/**
	 * @param movements
	 * 		the list of lines to set.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO setMovements(List<MovementDTO> movements) {
		this.movements = movements != null ? movements : new ArrayList<>();
		return this;
	}

	public OperationDTO setMovements(JsonArray movments) {
		if(!movments.isEmpty()) {
			for(JsonObject movement : movments.getValuesAs(JsonObject.class)) {
				this.movements.add(new MovementDTO(movement));
			}
		}
		return this;
	}

	/**
	 * Adds a movement line.
	 *
	 * @param movement
	 * 		the line to add.
	 * @return this instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public OperationDTO addMovement(MovementDTO movement) {
		if (movement != null) {
			this.movements.add(movement);
		}
		return this;
	}

	/**
	 * Verifies if the total debit amount is equal to the total credit amount across all movements.
	 * If the list of movements is null or empty, the method assumes no debits or credits exist, and the
	 * totals are equal.
	 *
	 * @return {@code true} if the total debit amount equals the total credit amount; {@code false} otherwise.
	 *
	 * @since 0.4
	 */
	public Boolean isBalance() {
		BigDecimal totalDebit = BigDecimal.ZERO;
		BigDecimal totalCredit = BigDecimal.ZERO;
		if (this.getMovements() != null) {
			for (MovementDTO move : this.getMovements()) {
				if (move.getDebitAmount() != null) {
					totalDebit = totalDebit.add(move.getDebitAmount());
				}
				if (move.getCreditAmount() != null) {
					totalCredit = totalCredit.add(move.getCreditAmount());
				}
			}
		}

		return totalDebit.compareTo(totalCredit) == 0;
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
		sb.append("Operation [id=").append(this.getId() != null ? this.getId() : "null")
				.append(", dateOperation = ").append(this.getDateOperation() != null ? this.getDateOperation().toString() : "null")
				.append(", dateComptable = ").append(this.getDateComptable() != null ? this.getDateComptable().toString() : "null")
				.append(", libelle = ").append(this.getLibelle() != null ? this.getLibelle() : "null")
				.append(", referenceDocument = ").append(this.getReferenceDocument() != null ? this.getReferenceDocument() : "null")
				.append(", descriptif = ").append(this.getDescriptif() != null ? this.getDescriptif() : "null")
				.append(", statutCode = ").append(this.getStatutCode() != null ? this.getStatutCode() : "null")
				.append(", movements : ").append("\n");
		if (this.getMovements() != null) {
			for (MovementDTO movement : this.getMovements()) {
				sb.append(movement.toString()).append("\n");
			}
			sb.append(this.isBalance() ? "Balance OK" : "Balance KO");
		} else sb.append("No movements found");
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Converts the current instance of {@code OperationDTO} to a JSON representation.
	 * Constructs a {@code JsonObject} where the fields of the object are serialized into key-value pairs.
	 * If a field is {@code null}, it is not included in the resulting JSON object.
	 *
	 * @return a {@code JsonObject} representation of the {@code OperationDTO} instance, containing serialized key-value pairs
	 * 		for non-null fields including the ID, dates, label, document reference, description, status code, and associated movements.
	 *
	 * @since 0.4
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		if (this.getId() != null) {
			builder.add("id", this.getId());
		}
		if (this.getDateOperation() != null) {
			builder.add("dateOperation", this.getDateOperation().toString());
		}
		if (this.getDateComptable() != null) {
			builder.add("dateComptable", this.getDateComptable().toString());
		}
		if (this.getLibelle() != null) {
			builder.add("libelle", this.getLibelle());
		}
		if (this.getReferenceDocument() != null) {
			builder.add("referenceDocument", this.getReferenceDocument());
		}
		if (this.getDescriptif() != null) {
			builder.add("descriptif", this.getDescriptif());
		}
		if (this.getStatutCode() != null) {
			builder.add("statutCode", this.getStatutCode());
		}
		if (this.getMovements() != null && !this.getMovements().isEmpty()) {
			JsonArrayBuilder movementsBuilder = Json.createArrayBuilder();
			for (MovementDTO movement : this.getMovements()) {
				movementsBuilder.add(movement.toJson());
			}
			builder.add("movements", movementsBuilder);
		}
		builder.add("balance", this.isBalance());
		return builder.build();
	}
}