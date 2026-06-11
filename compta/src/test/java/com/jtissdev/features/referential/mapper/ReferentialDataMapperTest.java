package com.jtissdev.features.referential.mapper;

import com.jtissdev.core.exception.JsonMappingException;
import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ReferentialDataMapper}.
 *
 * @author J.Tiss
 * @since 0.2.0
 */
@ExtendWith(TestResultLogger.class)
@TestGroup("Referential - MAPPER")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("🧪 ReferentialDataMapper - Unit Tests Sequence")
class ReferentialDataMapperTest {

	private ReferentialDataMapper mapper;

	@BeforeEach
	void setUp() {
		this.mapper = new ReferentialDataMapper();
	}
	// =========================================================
	// == TEST CASES                                          ==
	// =========================================================

	@Test
	@Order(1)
	@DisplayName("✅ toOperationStatusList : Doit mapper correctement un JsonArray valide")
	void testToOperationStatusList() {
		String json = """
                [
                    { "code": "PLAN", "name": "Planifié", "color": "#FFFFFF" },
                    { "code": "REAL", "name": "Réalisé", "color": "#00FF00" }
                ]
                """;
		InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		List<OperationStatus> result = mapper.toOperationStatusList(stream);

		assertNotNull(result, "La liste ne doit pas être nulle");
		assertEquals(2, result.size(), "Doit contenir 2 éléments");

		OperationStatus first = result.get(0);
		assertEquals("PLAN", first.getCode());
		assertEquals("Planifié", first.getName());
		assertEquals("#FFFFFF", first.getColor());
	}

	@Test
	@Order(2)
	@DisplayName("✅ toPaymentMethodList : Doit mapper correctement un JsonArray valide")
	void testToPaymentMethodList() {
		String json = """
                [
                    { "code": "CB", "name": "Carte Bancaire", "description": "Paiement par carte" }
                ]
                """;
		InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

		List<PaymentMethod> result = mapper.toPaymentMethodList(stream);

		assertNotNull(result, "La liste ne doit pas être nulle");
		assertEquals(1, result.size(), "Doit contenir 1 élément");

		PaymentMethod first = result.get(0);
		assertEquals("CB", first.getCode());
		assertEquals("Carte Bancaire", first.getName());
		assertEquals("Paiement par carte", first.getDescription());
	}

	@Test
	@Order(3)
	@DisplayName("❌ Résilience : Doit lever JsonMappingException sur un JSON malformé")
	void testMalformedJsonThrowsException() {
		String corruptedJson = "[ { bad json format ]";

		assertAll("Vérification des exceptions sur flux corrompus",
				() -> {
					InputStream stream1 = new ByteArrayInputStream(corruptedJson.getBytes(StandardCharsets.UTF_8));
					assertThrows(JsonMappingException.class, () -> mapper.toOperationStatusList(stream1));
				},
				() -> {
					InputStream stream2 = new ByteArrayInputStream(corruptedJson.getBytes(StandardCharsets.UTF_8));
					assertThrows(JsonMappingException.class, () -> mapper.toPaymentMethodList(stream2));
				}
		);
	}

	@Disabled("Deprecated method - to be removed in 0.6.0, replaced by direct list mapping")
	@Test
	@DisplayName("Should load OperationStatus from valid JSON stream")
	void testLoadOperationStatuses() throws Exception {
		// Given
		String json = "[{\"code\":\"TEST\",\"nom\":\"Test Status\",\"color\":\"#FFFFFF\"}]";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		ReferentialDataMapper loader = new ReferentialDataMapper();

		// When
		loader.loadOperationStatuses(is, dto);

		// Then
		assertEquals(1, dto.getOperationStatuses().size());
		assertEquals("TEST", dto.getOperationStatuses().get(0).getCode());
		assertEquals("Test Status", dto.getOperationStatuses().get(0).getName());
	}

	/**
	 * Tests the loading of PaymentMethod from JSON.
	 * * @since 0.2.0
	 */
	@Disabled("Deprecated method - to be removed in 0.6.0, replaced by direct list mapping")
	@Test
	@DisplayName("Should load PaymentMethod from valid JSON stream")
	void testLoadPaymentMethods() throws Exception {
		// Given
		String json = "[{\"code\":\"CB\",\"nom\":\"Carte\",\"description\":\"Paiement CB\"}]";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		ReferentialDataMapper loader = new ReferentialDataMapper();

		// When
		loader.loadPaymentMethods(is, dto);

		// Then
		assertEquals(1, dto.getPaymentMethods().size());
		assertEquals("CB", dto.getPaymentMethods().get(0).getCode());
		assertEquals("Paiement CB", dto.getPaymentMethods().get(0).getDescription());
	}
}