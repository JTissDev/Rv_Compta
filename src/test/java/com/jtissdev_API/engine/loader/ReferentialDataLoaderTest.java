package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.referential.dto.ReferentialCoreDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ReferentialDataLoader}.
 *
 * @author J.Tiss
 * @since 0.2.0
 */
class ReferentialDataLoaderTest {

	// =========================================================
	// == TEST CASES                                          ==
	// =========================================================

	@Test
	@DisplayName("Should load OperationStatus from valid JSON stream")
	void testLoadOperationStatuses() throws Exception {
		// Given
		String json = "[{\"code\":\"TEST\",\"nom\":\"Test Status\",\"color\":\"#FFFFFF\"}]";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		ReferentialDataLoader loader = new ReferentialDataLoader();

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
	@Test
	@DisplayName("Should load PaymentMethod from valid JSON stream")
	void testLoadPaymentMethods() throws Exception {
		// Given
		String json = "[{\"code\":\"CB\",\"nom\":\"Carte\",\"description\":\"Paiement CB\"}]";
		InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		ReferentialCoreDTO dto = new ReferentialCoreDTO();
		ReferentialDataLoader loader = new ReferentialDataLoader();

		// When
		loader.loadPaymentMethods(is, dto);

		// Then
		assertEquals(1, dto.getPaymentMethods().size());
		assertEquals("CB", dto.getPaymentMethods().get(0).getCode());
		assertEquals("Paiement CB", dto.getPaymentMethods().get(0).getDescription());
	}
}