package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import com.jtissdev_API.features.core.dto.referential.OperationStatusTest;
import com.jtissdev_API.features.core.dto.referential.PaymentMethod;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestResultLogger.class)
@DisplayName("ReferentialCoreDTO Test Suite")
public class ReferentialCoreDTOTest {

	/**
	 * Logger instance used for logging messages in the context of `ReferentialCoreDTOTest` tests.
	 * The logger is statically initialized and associated with the `ReferentialCoreDTOTest` class.
	 * Typically used to log test execution details, debugging information, or errors during the test lifecycle.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ReferentialCoreDTOTest.class);

	/**
	 * A static constant representing the JSON object for the "Carte Bancaire" (CB) payment method.
	 *
	 * This JSON object is preconfigured with the following attributes:
	 * - "code": A string representing the code of the payment method, set to "CB".
	 * - "name": A string representing the name of the payment method, set to "Carte Bancaire".
	 * - "description": A string providing a description of the payment method, set to "Payement par Carte Bancaire".
	 *
	 * Typically used to validate or manage payment methods as part of testing the ReferentialCoreDTO functionality.
	 */
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

	/**
	 * A static, shared instance of {@link ReferentialCoreDTO}, used across tests in the context of
	 * {@code ReferentialCoreDTOTest}. This instance typically serves as a reusable container for testing
	 * functionalities associated with referential data management, such as handling operation statuses
	 * and payment methods.
	 *
	 * The instance may be initialized and configured during test setup to facilitate consistent
	 * and efficient execution of unit tests, avoiding redundant initialization.
	 */
	private static ReferentialCoreDTO REFERENTIAL_CORE_DTO;

	/**
	 * Represents the real state of an operation under test in the context of {@code ReferentialCoreDTOTest}.
	 * <p>
	 * This field is a static instance of {@link OperationStatus} and is intended to reflect
	 * the actual lifecycle status of a transaction during testing.
	 * It can be used to verify proper handling of "real" operation statuses within the tested class.
	 */
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
