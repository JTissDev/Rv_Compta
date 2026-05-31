package com.jtissdev.features.pcg.mapper;

import com.jtissdev.core.exception.JsonMappingException;
import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestResultLogger.class)
@TestGroup("PCG - MAPPER")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("🧪 PcgDataMapper - Unit Tests Sequence")
public class PcgDataMapperTest {

	private PcgDataMapper pcgMapper;

	@BeforeEach
	void setUp() {
		this.pcgMapper = new PcgDataMapper();
	}

	@Nested
	@TestGroup("PCG - MAPPER")
	@DisplayName("➡️ Standard Streams Parsing Scenarios")
	class ParsingScenarios {

		@Test
		@DisplayName("✅ Should map successfully when JSON is a raw root Array")
		void shouldMapRawJsonArraySuccessfully() {
			String rawJson = """
					[
					    {
					        "name": "Capitaux",
					        "accountCode": 1,
					        "description": "Comptes de capitaux",
					        "subTypes": []
					    }
					]
					""";
			InputStream stream = new ByteArrayInputStream(rawJson.getBytes(StandardCharsets.UTF_8));

			PcgCoreDTO result = pcgMapper.toDto(stream);

			assertNotNull(result, "The parsed DTO should not be null");
			assertEquals(1, result.getAccountingClasses().size(), "Should have parsed 1 accounting class");
			assertEquals("Capitaux", result.getAccountingClasses().get(0).getName());
		}

		@Test
		@DisplayName("✅ Should map successfully when JSON array is wrapped in 'data' object")
		void shouldMapWrappedJsonObjectSuccessfully() {
			String wrappedJson = """
					{
					    "size": 1,
					    "data": [
					        {
					            "name": "Immobilisations",
					            "accountCode": 2,
					            "description": "Comptes d'immobilisations",
					            "subTypes": []
					        }
					    ]
					}
					""";
			InputStream stream = new ByteArrayInputStream(wrappedJson.getBytes(StandardCharsets.UTF_8));

			PcgCoreDTO result = pcgMapper.toDto(stream);

			assertNotNull(result);
			assertEquals(1, result.getAccountingClasses().size());
			assertEquals("Immobilisations", result.getAccountingClasses().get(0).getName());
		}
	}

	@Nested
	@TestGroup("PCG - MAPPER")
	@DisplayName("❌ Error & Defensive Programming Scenarios")
	class ErrorScenarios {

		@Test
		@DisplayName("❌ Should throw JsonMappingException when 'data' key is missing")
		void shouldThrowExceptionWhenDataKeyIsMissing() {
			String invalidJson = """
					{
					    "invalidKey": []
					}
					""";
			InputStream stream = new ByteArrayInputStream(invalidJson.getBytes(StandardCharsets.UTF_8));

			JsonMappingException exception = assertThrows(JsonMappingException.class, () -> pcgMapper.toDto(stream));
			assertTrue(exception.getCause().getMessage().contains("Invalid JSON structure"));
		}

		@Test
		@DisplayName("❌ Should throw JsonMappingException on corrupted JSON stream")
		void shouldThrowExceptionOnCorruptedJsonStream() {
			String corruptedJson = "[{ invalid json ...";
			InputStream stream = new ByteArrayInputStream(corruptedJson.getBytes(StandardCharsets.UTF_8));

			assertThrows(JsonMappingException.class, () -> pcgMapper.toDto(stream));
		}
	}
}