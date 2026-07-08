package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@DisplayName("Test Suite : JsonFilePaymentMethodRepository")
@ActiveProfiles("test")
@TestGroup("Core Repository")
@ExtendWith(TestResultLogger.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFilePaymentMethodRepositoryTest {

	@Autowired
	private JsonFilePaymentMethodRepository repository;

	@Test
	@Order(1)
	@DisplayName("Chargement initial : Vérifier présence données")
	void testFindAll() {
		List<PaymentMethod> list = repository.findAll();
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}

	@Test
	@Order(2)
	@DisplayName("Sauvegarde et persistance")
	void testSave() {
		PaymentMethod method = new PaymentMethod()
				                       .setCode("BITCOIN")
				                       .setName("Crypto")
				                       .setDescription("Paiement via BTC");

		repository.save(method);

		assertTrue(repository.findById("BITCOIN").isPresent());
		assertEquals("Crypto", repository.findById("BITCOIN").get().getName());
	}
}