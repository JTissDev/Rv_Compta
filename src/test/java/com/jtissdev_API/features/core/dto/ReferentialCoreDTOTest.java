package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import com.jtissdev_API.features.core.dto.referential.PaymentMethod;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReferentialCoreDTOTest {

	private static final JsonObject PAYMENT_METHOD_CB_JSON = Json.createObjectBuilder()
			                                                         .add("code", "CB")
			                                                         .add("name", "Carte Bancaire")
			                                                         .add("description", "Payement par Carte Bancaire")
			                                                         .build();
	private static final JsonObject PAYMENT_METHOD_CHQ_JSON = Json.createObjectBuilder()
			                                                          .add("code", "CHQ")
			                                                          .add("name", "Chèque")
			                                                          .add("description", "Paiement par chèque")
			                                                          .build();
	private static final JsonObject STATUS_VALID_JSON = Json.createObjectBuilder()
			                                                    .add("code", "VAL")
			                                                    .add("name", "Validé")
			                                                    .add("color", "#00FF00")
			                                                    .build();
	private static final JsonObject STATUS_REAL_JSON = Json.createObjectBuilder()
			                                                   .add("code", "REAL")
			                                                   .add("name", "Réalisé")
			                                                   .add("color", "#0000FF")
			                                                   .build();

	private static ReferentialCoreDTO REFERENTIAL_CORE_DTO;

	private static OperationStatus operationStatusReal;
	private static OperationStatus operationStatusValid;
	private static PaymentMethod paymentMethodCB;
	private static PaymentMethod paymentMethodChq;

	@BeforeAll
	@DisplayName("Setup Objects for ReferencialCoreDTO Tests")
	static void setUpTest() {
		operationStatusReal = new OperationStatus(STATUS_REAL_JSON);
		operationStatusValid = new OperationStatus(STATUS_VALID_JSON);
		paymentMethodCB = new PaymentMethod(PAYMENT_METHOD_CB_JSON);
		paymentMethodChq = new PaymentMethod(PAYMENT_METHOD_CHQ_JSON);

	}


	@Test
	@DisplayName("Empty Constructor Test")
	void testEmptyConstructor() {
		REFERENTIAL_CORE_DTO = new ReferentialCoreDTO();
		JsonObject json = REFERENTIAL_CORE_DTO.toJson();


		assertAll("Test Empty constructor state validation",
				() -> assertEquals(0, REFERENTIAL_CORE_DTO.getPaymentMethods().size(), "No Payment methods should be in the list"),
				() -> assertEquals(0, REFERENTIAL_CORE_DTO.getOperationStatuses().size(), "No Operation Status should be in the list")
		);
	}

	@Test
	@DisplayName("Test full Json Constructor")
	void testFullJsonConstructor() {
		REFERENTIAL_CORE_DTO = new ReferentialCoreDTO(Json.createObjectBuilder()
				                                              .add("paymentMethods", Json.createArrayBuilder()
						                                                                     .add(PAYMENT_METHOD_CB_JSON)
						                                                                     .add(PAYMENT_METHOD_CHQ_JSON))
				                                              .add("operationStatuses", Json.createArrayBuilder()
						                                                                        .add(STATUS_VALID_JSON)
						                                                                        .add(STATUS_REAL_JSON))
				                                              .build());

		JsonObject json = REFERENTIAL_CORE_DTO.toJson();
		assertAll("Test Full Json constructor state validation",
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getPaymentMethods().size(), "Two Payment methods should be in the list"),
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getOperationStatuses().size(), "Two Operation Status should be in the list")
		);
	}

	@Test
	@DisplayName("Test Json Constructor with empty OperationStatuses")
	void testJsonConstructorWithEmptyOperationStatuses() {
		REFERENTIAL_CORE_DTO = new ReferentialCoreDTO(Json.createObjectBuilder()
				                                              .add("paymentMethods", Json.createArrayBuilder()
						                                                                     .add(PAYMENT_METHOD_CB_JSON)
						                                                                     .add(PAYMENT_METHOD_CHQ_JSON))
				                                              .build());

		JsonObject json = REFERENTIAL_CORE_DTO.toJson();
		assertAll("Test Json constructor with empty OperationStatuses state validation",
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getPaymentMethods().size(), "Two Payment methods should be in the list"),
				() -> assertEquals(0, REFERENTIAL_CORE_DTO.getOperationStatuses().size(), "No Operation Status should be in the list")
		);
	}

	@Test
	@DisplayName("Fluent API: setters should return this instance")
	void testFluentSetters() {
		REFERENTIAL_CORE_DTO = new ReferentialCoreDTO();
		ArrayList<OperationStatus> operationStatuses = new ArrayList<OperationStatus>();
		operationStatuses.add(operationStatusReal);
		operationStatuses.add(operationStatusValid);

		ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
		paymentMethods.add(paymentMethodCB);
		paymentMethods.add(paymentMethodChq);

		ReferentialCoreDTO result = REFERENTIAL_CORE_DTO
				                            .setOperationStatuses(operationStatuses)
				                            .setPaymentMethods(paymentMethods);

		JsonObject json = REFERENTIAL_CORE_DTO.toJson();
		assertAll("Test Fluent API state validation",
				() -> assertSame(REFERENTIAL_CORE_DTO, result, "Setter must return the same instance"),
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getOperationStatuses().size(), "Two Operation Status should be in the list"),
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getPaymentMethods().size(), "Two Payment methods should be in the list")
		);
	}

	@Test
	@DisplayName("Test Adder for Operation Statuses an Payment Methods")
	void testAdderForOperationStatusesAndPaymentMethods() {
		REFERENTIAL_CORE_DTO = new ReferentialCoreDTO();

		ReferentialCoreDTO result = REFERENTIAL_CORE_DTO
				                            .addOperationStatus(operationStatusReal)
				                            .addOperationStatus(operationStatusValid)
				                            .addPaymentMethod(paymentMethodCB)
				                            .addPaymentMethod(paymentMethodChq);

		assertAll("Test Fluent API state validation",
				() -> assertSame(REFERENTIAL_CORE_DTO, result, "Setter must return the same instance"),
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getOperationStatuses().size(), "Two Operation Status should be in the list"),
				() -> assertEquals(2, REFERENTIAL_CORE_DTO.getPaymentMethods().size(), "Two Payment methods should be in the list")
		);
	}
}
