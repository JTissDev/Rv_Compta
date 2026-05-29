package com.jtissdev.features.pcg.mapper;

import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🧪 PcgDataMapper - Unit Tests Sequence")
class PcgDataMapperTest {

	private PcgDataMapper pcgMapper;

	@BeforeEach
	void setUp() {
		this.pcgMapper = new PcgDataMapper();
	}

	@Nested
	@DisplayName("➡️ Standard Streams Parsing Scenarios")
	class ParsingScenarios {

		@Test
		@DisplayName("✅ Should map successfully when JSON is a raw root Array")
		void shouldMapRawJsonArraySuccessfully() {
			// Given: Un JSON minimaliste sous forme de tableau (Classe 1)
			String rawJson = """
                [
                    {
                        "type": "Capitaux",
                        "accountCode": 1,
                        "description": "Comptes de capitaux",
                        "subTypes": []
                    }
                ]
                """;
			InputStream stream = new ByteArrayInputStream(rawJson.getBytes(StandardCharsets.UTF_8));

			// When
			PcgCoreDTO result = pcgMapper.toDto(stream);

			// Then
			assertNotNull(result, "The parsed DTO should not be null");
			assertEquals(1, result.getAccountingClasses().size(), "Should have parsed 1 accounting class");
			assertEquals("Capitaux", result.getAccountingClasses().get(0).getName());
			assertEquals(1, result.getAccountingClasses().get(0).getAccountCode());
		}

		@Test
		@DisplayName("✅ Should map successfully when JSON array is wrapped in an object under 'data' key")
		void shouldMapWrappedJsonObjectSuccessfully() {
			// Given: Un JSON enveloppé dans un objet avec l'attribut 'data'
			String wrappedJson = """
                {
                    "size": 1,
                    "data": [
                        {
                            "type": "Immobilisations",
                            "accountCode": 2,
                            "description": "Comptes d'immobilisations",
                            "subTypes": []
                        }
                    ]
                }
                """;
			InputStream stream = new ByteArrayInputStream(wrappedJson.getBytes(StandardCharsets.UTF_8));

			// When
			PcgCoreDTO result = pcgMapper.toDto(stream);

			// Then
			assertNotNull(result);
			assertEquals(1, result.getAccountingClasses().size());
			assertEquals("Immobilisations", result.getAccountingClasses().get(0).getName());
			assertEquals(2, result.getAccountingClasses().get(0).getAccountCode());
		}
	}

	@Nested
	@DisplayName("❌ Error & Defensive Programming Scenarios")
	class ErrorScenarios {

		@Test
		@DisplayName("❌ Should throw RuntimeException when JSON structure is an object without 'data' key")
		void shouldThrowExceptionWhenDataKeyIsMissing() {
			// Given: Un objet JSON sans la clé "data" requise
			String invalidJson = """
                {
                    "invalidKey": []
                }
                """;
			InputStream stream = new ByteArrayInputStream(invalidJson.getBytes(StandardCharsets.UTF_8));

			// When & Then
			RuntimeException exception = assertThrows(RuntimeException.class, () -> pcgMapper.toDto(stream));
			assertTrue(exception.getMessage().contains("Invalid JSON structure"),
					"Exception message should guide developer on structural error");
		}

		@Test
		@DisplayName("❌ Should throw RuntimeException when stream contains corrupted or invalid JSON content")
		void shouldThrowExceptionOnCorruptedJsonStream() {
			// Given: Une chaîne corrompue qui brise le parser
			String corruptedJson = "[{ invalid json ...";
			InputStream stream = new ByteArrayInputStream(corruptedJson.getBytes(StandardCharsets.UTF_8));

			// When & Then
			assertThrows(RuntimeException.class, () -> pcgMapper.toDto(stream));
		}
	}
}