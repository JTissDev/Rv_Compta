package com.jtissdev_API.features.compta.dto;

import jakarta.json.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
 * @version 1.5.0
 * @since 0.3.0
 */

public class JournalDTO {

	/**
	 * Unique technical identifier for the journal instance.
	 *
	 * @since 0.3.0
	 */
	private Integer id;

	/**
	 * Human-readable name or title of the journal (e.g., "General Journal 2024").
	 *
	 * @since 0.3.0
	 */
	private String name;

	/**
	 * The start date of the period covered by this journal.
	 *
	 * @since 0.3.0
	 */
	private LocalDate startDate;

	/**
	 * The end date of the period covered by this journal.
	 *
	 * @since 0.3.0
	 */
	private LocalDate endDate;

	/**
	 * Technical code representing the type of journal (e.g., 'ACHAT', 'VENTE', 'BANQUE').
	 *
	 * @since 0.3.0
	 */
	private String journalTypeCode;

	/**
	 * List of operations contained within this journal.
	 * Each operation represents a transaction imported or manually entered.
	 *
	 * @since 0.3.0
	 */
	private List<OperationDTO> operations;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Default constructor.
	 * Initializes the operations list to an empty {@link ArrayList}.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO() {
		this.operations = new ArrayList<>();
	}

	/**
	 * Constructs a new {@code JournalDTO} instance by populating its fields
	 * from the given {@link JsonObject}.
	 *
	 * @param json
	 *        the {@link JsonObject} containing data to initialize this {@code JournalDTO}.
	 * @since 0.4
	 */
	public JournalDTO(JsonObject json) {
		this();
		if (json.containsKey("id")) {
			this.setId(json.getInt("id"));
		}
		if (json.containsKey("name")) {
			this.setName(json.getString("name"));
		}
		if (json.containsKey("startDate") && !json.isNull("startDate")) {
			this.setStartDate(LocalDate.parse(json.getString("startDate")));
		}

		if (json.containsKey("endDate") && !json.isNull("endDate")) {
			this.setEndDate(LocalDate.parse(json.getString("endDate")));
		}
		if (json.containsKey("journalTypeCode")) {
			this.setJournalTypeCode(json.getString("journalTypeCode"));
		}
		if (json.containsKey("operations")) {
			this.setOperations(json.getJsonArray("operations"));
		}
	}

	/**
	 * Constructor for creating a new journal (without ID).
	 * Useful for the Excel import preparation phase.
	 *
	 * @param name
	 * 		the journal name
	 * @param startDate
	 * 		the period start date
	 * @param endDate
	 * 		the period end date
	 * @param journalTypeCode
	 * 		the technical type code
	 * @since 0.3.0
	 *
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * Use {@link JournalDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public JournalDTO(String name, LocalDate startDate, LocalDate endDate, String journalTypeCode) {
		this();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.journalTypeCode = journalTypeCode;
	}

	/**
	 * Constructor for existing journal updates or persistence.
	 *
	 * @param id
	 * 		the technical ID
	 * @param name
	 * 		the journal name
	 * @param startDate
	 * 		the period start date
	 * @param endDate
	 * 		the period end date
	 * @param journalTypeCode
	 * 		the technical type code
	 * @since 0.3.0
	 *
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * Use {@link JournalDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public JournalDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, String journalTypeCode) {
		this(name, startDate, endDate, journalTypeCode);
		this.id = id;
	}

	/**
	 * Full constructor for retrieving a journal with all its operations.
	 *
	 * @param id
	 * 		the technical ID
	 * @param name
	 * 		the journal name
	 * @param startDate
	 * 		the period start date
	 * @param endDate
	 * 		the period end date
	 * @param journalTypeCode
	 * 		the technical type code
	 * @param operations
	 * 		the list of associated operations
	 * @since 0.3.0
	 *
	 * @deprecated {@since 0.4} Manual field initialization is discouraged.
	 * Use {@link JournalDTO (JsonObject)} instead for mapping from jsonObject
	 */
	@Deprecated(forRemoval = true, since = "0.4")
	public JournalDTO(Integer id, String name, LocalDate startDate, LocalDate endDate,
	                  String journalTypeCode, List<OperationDTO> operations) {
		this(id, name, startDate, endDate, journalTypeCode);
		this.setOperations(operations);
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
	 * 		the technical ID to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name of the journal.
	 *
	 * @since 0.3.0
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 * 		the name to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the start date of the journal's period.
	 *
	 * @since 0.3.0
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 * 		the start date to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	/**
	 * @return the end date of the journal's period.
	 *
	 * @since 0.3.0
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 * 		the end date to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * @return the technical type code of the journal.
	 *
	 * @since 0.3.0
	 */
	public String getJournalTypeCode() {
		return this.journalTypeCode;
	}

	/**
	 * @param journalTypeCode
	 * 		the type code to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setJournalTypeCode(String journalTypeCode) {
		this.journalTypeCode = journalTypeCode;
		return this;
	}

	/**
	 * @return the list of operations within this journal.
	 *
	 * @since 0.3.0
	 */
	public List<OperationDTO> getOperations() {
		return this.operations;
	}



	/**
	 * Sets the list of operations. Initializes an empty list if null is provided.
	 *
	 * @param operations
	 * 		the list of {@link OperationDTO} to set.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO setOperations(List<OperationDTO> operations) {
		this.operations = operations != null ? sortedOperations(operations) : new ArrayList<>();
		return this;
	}

	/**
	 * Trie une liste d'opérations chronologiquement selon la date comptable,
	 * puis par leur position (index de tri technique).
	 *
	 * @param operations la liste brute à trier
	 * @return une nouvelle liste triée
	 * @since 0.6
	 */
	private List<OperationDTO> sortedOperations(List<OperationDTO> operations) {
		return operations.stream()
				       .sorted(Comparator
						               // 1er critère : La date comptable (on gère les nulls au cas où l'opération est en cours de saisie)
						               .comparing(OperationDTO::getDateComptable, Comparator.nullsLast(Comparator.naturalOrder()))
						               // 2ème critère : La position (INT) pour garder l'ordre des insertions du même jour
						               .thenComparingInt(OperationDTO::getPosition))
				       .collect(Collectors.toList());
	}

	/**
	 * Sets the operations for the journal based on the provided {@link JsonArray}.
	 * Each element in the given {@code JsonArray} is converted into an {@link OperationDTO}
	 * and added to the operations list of this journal.
	 *
	 * @param operations
	 *        the {@code JsonArray} containing operation data to be set.
	 *        If the array is empty, no operations will be added.
	 * @return this {@link JournalDTO} instance for method chaining.
	 * @since 0.5
	 */
	public JournalDTO setOperations(JsonArray operations) {
		if (!operations.isEmpty()) {
			List<OperationDTO> tempOperations = new ArrayList<>();
			for (JsonObject operation : operations.getValuesAs(JsonObject.class)) {
				tempOperations.add(new OperationDTO(operation));
			}
			// On passe par le setter officiel qui inclut le tri !
			this.setOperations(tempOperations);
		}
		return this;
	}

	/**
	 * Adds a single operation to the journal.
	 *
	 * @param operation
	 * 		the {@link OperationDTO} to add.
	 * @return this {@link JournalDTO} instance for chaining.
	 *
	 * @since 0.3.0
	 */
	public JournalDTO addOperation(OperationDTO operation) {
		if (operation != null) {
			this.operations.add(operation);
		}
		return this;
	}

	/**
	 * Returns a string representation of the current {@code JournalDTO} instance.
	 * The string includes detailed information about the journal such as its ID, name,
	 * period start and end dates, type code, and associated operations.
	 *
	 * @return a string representation of this {@code JournalDTO} instance.
	 *
	 * @since 0.4
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Journal DTO :\n");
		sb.append("ID : ").append(this.id).append("\n");
		sb.append("Nom : ").append(this.name).append("\n");
		sb.append("Date debut : ").append(this.startDate).append("\n");
		sb.append("Date fin : ").append(this.endDate).append("\n");
		sb.append("Type journal : ").append(this.journalTypeCode).append("\n");
		sb.append("Operations : ").append("\n");
		for (OperationDTO op : this.operations) {
			sb.append("Operation : ").append(op.toString()).append("\n");
		}

		return sb.toString();
	}

	/**
	 * Converts the current {@code JournalDTO} instance into a {@link JsonObject}.
	 * The resulting JSON object contains key-value pairs representing the fields
	 * of the journal, including its ID, name, start and end dates, type code,
	 * and associated operations (if any).
	 *
	 * @return a {@link JsonObject} representation of this {@code JournalDTO} instance.
	 * @since 0.4
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		if (this.id != null) {
			builder.add("id", this.id);
		}
		if (this.name != null) {
			builder.add("name", this.name);
		}
		if (this.startDate != null) {
			builder.add("startDate", this.startDate.toString());
		}
		if (this.endDate != null) {
				builder.add("endDate", this.endDate.toString());
		}
		if (this.journalTypeCode != null) {
			builder.add("journalTypeCode", this.journalTypeCode);
		}
		if (this.operations != null) {
			JsonArrayBuilder operationsBuilder = Json.createArrayBuilder();
			for (OperationDTO op : this.operations) {
				operationsBuilder.add(op.toJson());
			}
			builder.add("operations", operationsBuilder);
		}
		return builder.build();
	}

}