package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import com.jtissdev_API.features.core.dto.referential.PaymentMethod;
import jakarta.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Central container for all cross-functional referential data.
 * <p>
 * This DTO aggregates shared lists such as {@link OperationStatus}
 * and {@link PaymentMethod} used by different features
 * of the application.
 *
 * @author J.Tiss
 * @since 0.2.0
 * @version 1.1.0
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

	/**
	 * List of all available payment methods.
	 *
	 * @since 0.2.0
	 */
	private List<PaymentMethod> paymentMethods;

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
		this.paymentMethods = new ArrayList<>();
	}

	/**
	 * Constructs a {@code ReferentialCoreDTO} instance with the specified lists of
	 * operation statuses and payment methods.
	 *
	 * @param operationStatuses the list of operation statuses to initialize the instance with
	 * @param paymentMethods the list of payment methods to initialize the instance with
	 *
	 * @since 0.4
	 */
	public ReferentialCoreDTO(List<OperationStatus> operationStatuses, List<PaymentMethod> paymentMethods) {
		this();
		this.setOperationStatuses(operationStatuses);
		this.setPaymentMethods(paymentMethods);

	}

	public ReferentialCoreDTO(JsonObject json) {
		this();
		if(json.containsKey("operationStatuses")) {
			JsonArray statusArray = json.getJsonArray("operationStatuses");
				for (JsonObject statusJson : statusArray.getValuesAs(JsonObject.class)) {
					OperationStatus status = new OperationStatus(statusJson);
						this.addOperationStatus(status);
				}
		}
		if(json.containsKey("paymentMethods")) {
			JsonArray paymentArray = json.getJsonArray("paymentMethods");
				for (JsonObject paymentJson : paymentArray.getValuesAs(JsonObject.class)) {
					PaymentMethod payment = new PaymentMethod(paymentJson);
						this.addPaymentMethod(payment);
				}
		}
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

	/**
	 * Returns the list of payment methods.
	 *
	 * @return non-null list of methods
	 *
	 * @since 0.2.0
	 */
	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	/**
	 * Sets the list of payment methods.
	 *
	 * @param paymentMethods list of methods to set
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public ReferentialCoreDTO setPaymentMethods(List<PaymentMethod> paymentMethods) {
		// Note: correction logique pour correspondre au type du champ
		this.paymentMethods = paymentMethods != null ? (List) paymentMethods : new ArrayList<>();
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

	/**
	 * Adds a payment method to the internal list.
	 *
	 * @param method method to add
	 * @return {@code this} instance for fluent chaining
	 *
	 * @since 0.2.0
	 */
	public ReferentialCoreDTO addPaymentMethod(PaymentMethod method) {
		if (method != null) {
			this.paymentMethods.add(method);
		}
		return this;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Converts this referential container into a {@link JsonObject}.
	 *
	 * @return JSON representation of all referential data
	 *
	 * @since 0.2.0
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		// Statuses
		JsonArrayBuilder statusArray = Json.createArrayBuilder();
		for (OperationStatus status : operationStatuses) {
			if (status != null) {
				statusArray.add(status.toJson());
			}
		}
		builder.add("operationStatuses", statusArray);

		// Payment Methods
		JsonArrayBuilder paymentArray = Json.createArrayBuilder();
		for (PaymentMethod method : paymentMethods) {
			if (method != null) {
				paymentArray.add(method.toJson());
			}
		}
		builder.add("paymentMethods", paymentArray);

		return builder.build();
	}
}