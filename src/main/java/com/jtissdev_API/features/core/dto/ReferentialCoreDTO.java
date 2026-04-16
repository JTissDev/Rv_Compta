package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Central container for all cross-functional referential data.
 * <p>
 * This DTO aggregates shared lists such as {@link OperationStatus}
 * and {@link PaymentMethod} (to be created) used by different features
 * of the application.
 *
 * @author J.Tiss
 * @since 0.2.0
 * @version 1.0.0
 */
public class ReferentialCoreDTO {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * List of all available operation statuses.
	 *
	 * @since 0.2.0
	 */
	private List<OperationStatus> operationStatuses;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Creates an empty {@code ReferentialCoreDTO} instance.
	 * All lists are initialized to empty {@link ArrayList}.
	 *
	 * @since 0.2.0
	 */
	public ReferentialCoreDTO() {
		this.operationStatuses = new ArrayList<>();
	}

	// =========================================================
	// == GETTERS / SETTERS                                   ==
	// =========================================================

	/**
	 * Returns the list of operation statuses.
	 *
	 * @return non-null list of statuses
	 *
	 * @since 0.2.0
	 */
	public List<OperationStatus> getOperationStatuses() {
		return operationStatuses;
	}

	/**
	 * Sets the list of operation statuses.
	 * If {@code null} is provided, the internal list is replaced
	 * by an empty {@link ArrayList}.
	 *
	 * @param operationStatuses list of statuses to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public ReferentialCoreDTO setOperationStatuses(List<OperationStatus> operationStatuses) {
		this.operationStatuses = operationStatuses != null ? operationStatuses : new ArrayList<>();
		return this;
	}

	// =========================================================
	// == UTILITY METHODS                                     ==
	// =========================================================

	/**
	 * Adds an operation status to the internal list.
	 *
	 * @param status status to add
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public ReferentialCoreDTO addOperationStatus(OperationStatus status) {
		if (status != null) {
			this.operationStatuses.add(status);
		}
		return this;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Converts this referential container into a {@link JsonObject}.
	 *
	 * @return JSON representation of all referential
	 *
	 * @since 0.2.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		JsonArrayBuilder statusArray = Json.createArrayBuilder();
		for (OperationStatus status : operationStatuses) {
			if (status != null) {
				statusArray.add(status.toJson());
			}
		}
		builder.add("operationStatuses", statusArray);

		return builder.build();
	}
}