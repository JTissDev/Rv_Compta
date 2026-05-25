package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.core.dto.ReferentialCoreDTO;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration and unit tests for the {@link JsonFileReferentialCoreRepository} class.
 * <p>
 * Validates the dual-file JSON serialization, the fallback seed copy mechanism,
 * and flat-file storage loop for the application's core referential data.
 * </p>
 *
 * @author J.Tiss
 * @version 2.0.0
 * @email jtissdev@gmail.com
 * @since 0.6.0
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(TestResultLogger.class)
@DisplayName("Json File Referential Repository Test Suite")
@TestGroup("Core Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFileReferentialCoreRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFileReferentialCoreRepositoryTest.class);

	@Autowired
	private JsonFileReferentialCoreRepository repository;

	@Value("${app.persistence.storage-path}")
	private String configuredStorageDir;

	@Value("${app.persistence.filename-status}")
	private String configuredStatusFilename;

	@Value("${app.persistence.filename-payment}")
	private String configuredPaymentFilename;

	private Path fullStatusPath;
	private Path fullPaymentPath;
	private ReferentialCoreDTO sampleReferential;

	@BeforeEach
	void setUp() throws IOException {
		// Résolution dynamique des deux chemins de destination
		this.fullStatusPath = Paths.get(configuredStorageDir, configuredStatusFilename).toAbsolutePath().normalize();
		this.fullPaymentPath = Paths.get(configuredStorageDir, configuredPaymentFilename).toAbsolutePath().normalize();

		logger.info("[Test Setup] Cleaning potential residual files at: {}", configuredStorageDir);
		Files.deleteIfExists(fullStatusPath);
		Files.deleteIfExists(fullPaymentPath);

		// Construction d'un jeu de données JSON "modifié" pour tester l'écrasement lors du save()
		JsonObject initialJson = Json.createObjectBuilder()
				                         .add("operationStatuses", Json.createArrayBuilder()
						                                                   .add(Json.createObjectBuilder()
								                                                        .add("code", "TST")
								                                                        .add("name", "Test Status")
								                                                        .add("color", "#000000")
								                                                        .build())
						                                                   .build())
				                         .add("paymentMethods", Json.createArrayBuilder()
						                                                .add(Json.createObjectBuilder()
								                                                     .add("code", "CRYPTO")
								                                                     .add("name", "Cryptomonnaie")
								                                                     .add("description", "Paiement en Bitcoin")
								                                                     .build())
						                                                .build())
				                         .build();

		this.sampleReferential = new ReferentialCoreDTO(initialJson);
	}

	@Test
	@Order(1)
	@DisplayName("Seed copy triggers when live files are missing")
	void testSeedTriggerWhenFilesDoNotExist() {
		logger.info("[Test] Executing testSeedTriggerWhenFilesDoNotExist");

		// 1. On vérifie que le dossier est bien vide avant l'action
		assertFalse(Files.exists(fullStatusPath), "Status file should not exist yet");
		assertFalse(Files.exists(fullPaymentPath), "Payment file should not exist yet");

		// 2. Action : On demande le chargement. Le repository doit détecter l'absence et copier les graines.
		Optional<ReferentialCoreDTO> result = repository.load();

		// 3. Assertions : On vérifie que la magie a opéré
		assertAll("Verify seed fallback behavior",
				() -> assertTrue(result.isPresent(), "Repository should have loaded the seed data successfully"),
				() -> assertTrue(Files.exists(fullStatusPath), "Status seed file should have been copied to target storage"),
				() -> assertTrue(Files.exists(fullPaymentPath), "Payment seed file should have been copied to target storage"),
				() -> assertFalse(result.get().getOperationStatuses().isEmpty(), "Loaded statuses should not be empty"),
				() -> assertFalse(result.get().getPaymentMethods().isEmpty(), "Loaded payment methods should not be empty")
		);
	}

	@Test
	@Order(2)
	@DisplayName("Save custom data and Load nominal case")
	void testSaveAndLoadNominal() {
		logger.info("[Test] Executing SaveAndLoadNominal for Referential");

		// 1. Action : Sauvegarde de notre référentiel personnalisé "TST" et "CRYPTO"
		repository.save(sampleReferential);

		// 2. Assertion physique
		assertTrue(Files.exists(fullStatusPath), "The Status JSON file must be written to disk");
		assertTrue(Files.exists(fullPaymentPath), "The Payment JSON file must be written to disk");

		// 3. Action : Rechargement depuis les fichiers
		Optional<ReferentialCoreDTO> loadedResult = repository.load();

		assertTrue(loadedResult.isPresent(), "The loaded Optional must contain a ReferentialCoreDTO instance");
		ReferentialCoreDTO loadedReferential = loadedResult.get();

		// 4. Assertions sur la fidélité des données (Vérifie la séparation en deux tableaux)
		assertAll("Verify data consistency across both split files",
				() -> assertEquals(1, loadedReferential.getOperationStatuses().size(), "Should have exactly 1 custom status"),
				() -> assertEquals("TST", loadedReferential.getOperationStatuses().get(0).getCode(), "Status code mismatch"),
				() -> assertEquals(1, loadedReferential.getPaymentMethods().size(), "Should have exactly 1 custom payment method"),
				() -> assertEquals("CRYPTO", loadedReferential.getPaymentMethods().get(0).getCode(), "Payment code mismatch")
		);
	}

	@Test
	@Order(3)
	@DisplayName("Save throws IllegalArgumentException on null input")
	void testSaveNullReferential() {
		logger.info("[Test] Executing SaveNullReferential");
		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Saving a null referential must throw an IllegalArgumentException");
	}
}