package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration and unit tests for the {@link JsonFilePcpRepository} class.
 * <p>
 * Validates the dual-file JSON serialization, the fallback seed template deployment,
 * and the round-trip data integrity for the Personal Accounting Plan components.
 * </p>
 *
 * @author J.Tiss <jtissdev@gmail.com>
 * @version 1.0.0
 * @since 0.6.0
 */
@ExtendWith(TestResultLogger.class)
@DisplayName("Json File PCP Repository Test Suite")
@TestGroup("Core Repository")
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFilePcpRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFilePcpRepositoryTest.class);

	@Autowired
	private JsonFilePcpRepository repository;

	private Path fullTiersPath;
	private Path fullDetailsPath;

	/**
	 * Deletes any residual test data files before each execution loop to prevent
	 * cross-test state contamination.
	 *
	 * @param storagePath   the injected folder path targeted for live file isolation.
	 * @param filenameTiers the injected file name for the third parties registry.
	 * @param filenameDetails the injected file name for the analytical details registry.
	 * @throws IOException if a file system interaction barrier is encountered.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.6.0
	 */
	@BeforeEach
	void setUp(@Value("${app.persistence.storage-path}") String storagePath,
	           @Value("${app.persistence.filename-tiers}") String filenameTiers,
	           @Value("${app.persistence.filename-details}") String filenameDetails) throws IOException {

		this.fullTiersPath = Paths.get(storagePath, filenameTiers);
		this.fullDetailsPath = Paths.get(storagePath, filenameDetails);

		logger.info("[Test Setup] Cleaning potential residual files at: {} and {}", fullTiersPath, fullDetailsPath);
		Files.deleteIfExists(fullTiersPath);
		Files.deleteIfExists(fullDetailsPath);
	}

	@Test
	@Order(1)
	@DisplayName("Load triggers fallback to seed files when live files are missing")
	void testLoadFallbackToSeed() {
		logger.info("[Test] Executing LoadFallbackToSeed");

		// Action : On charge alors qu'aucun fichier n'existe dans target/test-storage
		Optional<PcpCoreDTO> result = repository.load();

		// Assertions structurelles
		assertTrue(result.isPresent(), "The loaded Optional must contain a valid PcpCoreDTO instance.");
		PcpCoreDTO pcpCore = result.get();

		assertNotNull(pcpCore.getThirdParties(), "The loaded third parties collection should not be null.");
		assertNotNull(pcpCore.getDetails(), "The loaded analytic details collection should not be null.");

		// Assertions physiques : vérification du déploiement automatique des fichiers de graines (Seeds)
		assertTrue(Files.exists(fullTiersPath), "The live Tiers file should have been deployed from internal seeds.");
		assertTrue(Files.exists(fullDetailsPath), "The live Details file should have been deployed from internal seeds.");

		logger.info("         ✅ SUCCÈS : Load falls back to seeds and creates live files correctly");
	}

	@Test
	@Order(2)
	@DisplayName("Save and load cycle preserves data fidelity across separate files")
	void testSaveAndLoadFidelity() {
		logger.info("[Test] Executing SaveAndLoadFidelity");

		// 1. Préparation d'une structure PCP d'exemple
		PcpCoreDTO samplePcp = new PcpCoreDTO();

		List<Tiers> tiersList = new ArrayList<>();
		Tiers tier = new Tiers();
		tier.setId(999);
		tier.setName("EDF Test");
		tier.setThirdPartyType("VENDOR");
		tier.setDescription("Fournisseur electricite de test");
		tiersList.add(tier);
		samplePcp.setThirdParties(tiersList);

		List<AnalyticDetail> detailsList = new ArrayList<>();
		AnalyticDetail detail = new AnalyticDetail();
		detail.setCode(".442");
		detail.setType("Vehicule");
		detail.setName("Clio Test");
		detail.setDescription("Voiture de test");
		detailsList.add(detail);
		samplePcp.setDetails(detailsList);

		// 2. Action : Persistance globale via le point d'entrée unique
		repository.save(samplePcp);

		// Assertions physiques de création sur le disque
		assertTrue(Files.exists(fullTiersPath), "The Tiers JSON file must be physically written to disk.");
		assertTrue(Files.exists(fullDetailsPath), "The Details JSON file must be physically written to disk.");

		// 3. Action : Rechargement pour valider le cycle complet d'I/O
		Optional<PcpCoreDTO> loadedResult = repository.load();
		assertTrue(loadedResult.isPresent(), "The loaded Optional must contain a PcpCoreDTO instance.");
		PcpCoreDTO loadedPcp = loadedResult.get();

		// 4. Assertions de fidélité métatongue
		assertAll("Verify data consistency across both split files",
				() -> assertEquals(1, loadedPcp.getThirdParties().size(), "Should have exactly 1 custom tier saved."),
				() -> assertEquals("EDF Test", loadedPcp.getThirdParties().get(0).getName(), "Tier name mapping corrupted."),
				() -> assertEquals(1, loadedPcp.getDetails().size(), "Should have exactly 1 analytic detail saved."),
				() -> assertEquals(".442", loadedPcp.getDetails().get(0).getCode(), "Analytic detail code mapping corrupted.")
		);

		logger.info("         ✅ SUCCÈS : Save and load cycle preserved data fidelity across separate files");
	}

	@Test
	@Order(3)
	@DisplayName("Save throws IllegalArgumentException on null core input")
	void testSaveNullPcpCore() {
		logger.info("[Test] Executing SaveNullPcpCore");

		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Saving a null PcpCoreDTO must trigger an IllegalArgumentException.");

		logger.info("         ✅ SUCCÈS : Save throws IllegalArgumentException on null core input");
	}

	@Test
	@Order(4)
	@DisplayName("Individual saves throw IllegalArgumentException on null lists")
	void testSaveNullSubLists() {
		logger.info("[Test] Executing SaveNullSubLists");

		assertAll("Verify individual saves safeguard against null parameters",
				() -> assertThrows(IllegalArgumentException.class, () -> repository.saveTiers(null), "Should reject null third parties list."),
				() -> assertThrows(IllegalArgumentException.class, () -> repository.saveDetails(null), "Should reject null analytic details list.")
		);

		logger.info("         ✅ SUCCÈS : Individual save methods safely block null parameter references");
	}
}