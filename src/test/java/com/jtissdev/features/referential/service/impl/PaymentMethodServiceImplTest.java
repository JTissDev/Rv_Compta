package com.jtissdev.features.referential.service.impl;

import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.features.referential.repository.PaymentMethodRepository;
import com.jtissdev.features.referential.service.PaymentMethodService;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(TestResultLogger.class)
@DisplayName("PaymentMethod Service Test Suite")
@TestGroup("Referential Service")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentMethodServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImplTest.class);

	@Autowired
	private PaymentMethodService service;

	@MockBean
	private PaymentMethodRepository repository;

	private PaymentMethod testMethod;

	@BeforeEach
	void setUp() {
		testMethod = new PaymentMethod()
				             .setCode("CB")
				             .setName("Carte Bancaire")
				             .setDescription("Paiement par carte bancaire");
	}

	@Test
	@Order(1)
	@DisplayName("Global Read: Retrieve full payment method list")
	void testGetAllMethods() {
		when(repository.findAll()).thenReturn(List.of(testMethod));

		List<PaymentMethod> result = service.getAllMethods();

		assertNotNull(result, "The returned payment method list must not be null");
		assertEquals(1, result.size(), "The list must contain exactly one payment method");
		assertEquals("CB", result.get(0).getCode(), "The extracted code must match 'CB'");

		logger.info("         ✅ SUCCESS: Global payment methods list retrieval verified");
	}

	@Test
	@Order(2)
	@DisplayName("Defensive Check: Reject creation if inputs are null or corrupted")
	void testCreateMethod_NullSafety() {
		assertThrows(IllegalArgumentException.class, () -> service.createMethod(null),
				"Passing a null payload must throw an IllegalArgumentException");
		assertThrows(IllegalArgumentException.class, () -> service.createMethod(new PaymentMethod()),
				"Passing a payment method without an identifier must throw an IllegalArgumentException");

		logger.info("         ✅ SUCCESS: Rejection of corrupted payment payloads verified");
	}

	@Test
	@Order(3)
	@DisplayName("Security Check: Prevent overwriting existing code (Duplicate key)")
	void testCreateMethod_DuplicateProtection() {
		when(repository.findById("CB")).thenReturn(Optional.of(testMethod));

		assertThrows(IllegalStateException.class, () -> service.createMethod(testMethod),
				"Inserting an already existing payment method code must throw an IllegalStateException");

		logger.info("         ✅ SUCCESS: Duplicate key protection for payment methods verified");
	}

	@Test
	@Order(4)
	@DisplayName("Persistence: Update payment method data attributes")
	void testUpdateMethod_Success() {
		PaymentMethod updatePayload = new PaymentMethod().setName("Carte VISA");

		when(repository.findById("CB")).thenReturn(Optional.of(testMethod));
		when(repository.save(any(PaymentMethod.class))).thenAnswer(inv -> inv.getArgument(0));

		PaymentMethod result = service.updateMethod("CB", updatePayload);

		assertEquals("CB", result.getCode(), "The original identifier must be preserved and forced onto the payload");
		assertEquals("Carte VISA", result.getName(), "The payment method name must be successfully updated to 'Carte VISA'");

		logger.info("         ✅ SUCCESS: Unitary mutation of payment method attributes verified");
	}

	@Test
	@Order(5)
	@DisplayName("Destruction: Trigger deletion if target key is valid")
	void testDeleteMethod_Execution() {
		service.deleteMethod("CB");

		verify(repository, times(1)).deleteById("CB");
		logger.info("         ✅ SUCCESS: Deletion command propagation to repository verified");
	}

	@Test
	@Order(6)
	@DisplayName("Defensive Check: Silently ignore deletion if key is null or blank")
	void testDeleteMethod_NullOrBlank() {
		service.deleteMethod(null);
		service.deleteMethod("   ");

		verify(repository, never()).deleteById(any());
		logger.info("         ✅ SUCCESS: Filtering of empty or invalid deletion requests verified");
	}
}