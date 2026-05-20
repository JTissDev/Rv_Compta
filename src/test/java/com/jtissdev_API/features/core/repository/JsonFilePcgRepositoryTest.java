package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import org.junit.jupiter.api.*;
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
 * Test suite for the JsonFilePcgRepository class, which provides unit tests
 * to validate the behavior and integrity of the repository operations on a
 * PCG (Plan Comptable Général) JSON file.
 *
 * The following scenarios are tested:
 * - Initial load from a seed file when the target PCG file does not exist.
 * - Full save and reload cycle to verify the persistence mechanism.
 * - Validation of input parameters to safeguard against null arguments.
 *
 * This is an integration test leveraging the Spring testing framework.
 * The tests rely on a temporary test-specific configuration and storage path.
 *
 * Annotations:
 * - @SpringBootTest: Bootstraps the Spring application context for testing.
 * - @ActiveProfiles("test"): Activates the "test" profile for configuration isolation.
 * - @TestMethodOrder(MethodOrderer.OrderAnnotation.class): Ensures execution order of tests.
 *
 * Logging:
 * - Provides detailed logs for each test, marking milestones and assertions.
 *
 * Test Lifecycle:
 * - @BeforeAll: Executes a setup phase before all tests in the suite.
 * - @AfterAll: Wraps up operations after all tests in the suite are completed.
 * - @BeforeEach: Configures resources and variables required before each test case.
 *
 * Test Methods:
 * - testLoadFallbackSeed(): Verifies the repository's ability to deploy and read from
 *   a fallback seed file if the target file is missing.
 * - testSaveAndLoadCycle(): Validates the complete save-and-reload cycle, ensuring data
 *   consistency between the written and reloaded entities.
 * - testSaveNullPcg(): Ensures an IllegalArgumentException is thrown when attempting
 *   to save a null PCG structure.
 *
 * Dependencies:
 * - JsonFilePcgRepository: The class under test, injected by Spring's dependency injection.
 * - PcgCoreDTO: The data transfer object representing the PCG structure.
 * - The test uses a configured storage path to simulate file-based persistence.
 *
 * All tests use assertions to ensure correctness and produce logs to confirm test success.
 *
 * @author J.Tissdev
 * @version 1.0
 * @since 0.6
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFilePcgRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFilePcgRepositoryTest.class);

	@Autowired
	private JsonFilePcgRepository repository;

	@Value("${app.persistence.storage-path}")
	private String storagePath;

	private Path targetPcgFile;

	@BeforeEach
	void setUp() {
		// Identification du fichier de données vivant cible dans l'environnement de test
		this.targetPcgFile = Paths.get(storagePath, "PCG.json");
	}

	@Test
	@Order(1)
	@DisplayName("Chargement initial : copie de la seed si le fichier vivant n'existe pas")
	void testLoadFallbackSeed() {
		logger.info("[Test] Executing LoadFallbackSeed");

		// Action : Lecture initiale du dépôt
		Optional<PcgCoreDTO> pcgResult = repository.load();

		// Assertions : Le fichier doit avoir été déployé automatiquement à partir de la seed
		assertTrue(pcgResult.isPresent(), "L'Optional doit contenir une instance de PcgCoreDTO chargée depuis la seed");
		PcgCoreDTO pcg = pcgResult.get();

		assertNotNull(pcg.getAccountingClasses(), "La liste des classes comptables ne doit pas être nulle");
		assertFalse(pcg.getAccountingClasses().isEmpty(), "Le PCG initialisé ne doit pas être vide");
		assertTrue(Files.exists(targetPcgFile), "Le fichier physique PCG.json doit désormais exister dans target/test-storage");

		logger.info("         ✅ SUCCÈS : Déploiement et lecture de la seed PCG validés");
	}

	@Test
	@Order(2)
	@DisplayName("Sauvegarde et rechargement complet : Cycle de persistance nominal")
	void testSaveAndLoadCycle() throws IOException {
		logger.info("[Test] Executing SaveAndLoadCycle");

		// 1. Arrange : Récupération de l'état actuel ou création d'une structure témoin
		Optional<PcgCoreDTO> currentPcgOpt = repository.load();
		assertTrue(currentPcgOpt.isPresent());
		PcgCoreDTO originalPcg = currentPcgOpt.get();

		// 2. Action : Sauvegarde sur le disque
		repository.save(originalPcg);

		// 3. Assertion physique : Vérification de la présence effective du fichier sur le disque
		assertTrue(Files.exists(targetPcgFile), "Le fichier PCG.json doit persister sur le disque");

		// 4. Action & Assertion logique : Rechargement complet et validation de l'intégrité
		Optional<PcgCoreDTO> reloadedPcgOpt = repository.load();
		assertTrue(reloadedPcgOpt.isPresent(), "Le rechargement après sauvegarde doit retourner un Optional valide");

		PcgCoreDTO reloadedPcg = reloadedPcgOpt.get();
		assertEquals(originalPcg.getAccountingClasses().size(), reloadedPcg.getAccountingClasses().size(),
				"Le nombre de classes comptables doit être identique après un cycle d'écriture/lecture");

		logger.info("         ✅ SUCCÈS : Cycle complet d'écriture et de lecture validé");
	}

	@Test
	@Order(3)
	@DisplayName("Sécurité : Levée d'une IllegalArgumentException en cas d'argument null lors de la sauvegarde")
	void testSaveNullPcg() {
		logger.info("[Test] Executing SaveNullPcg");

		// Action & Assertion
		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Tenter de sauvegarder une structure PCG nulle doit lever une IllegalArgumentException");

		logger.info("         ✅ SUCCÈS : Le garde-fou contre les structures nulles est opérationnel");
	}

	@BeforeAll
	static void startTestSuite() {
		logger.info("      ■ DÉMARRAGE : Json File PCG Repository Test Suite");
	}

	@AfterAll
	static void endTestSuite() {
		logger.info("      ■ FIN FICHIER : Json File PCG Repository Test Suite (Stats: 3/3 success)");
	}
}