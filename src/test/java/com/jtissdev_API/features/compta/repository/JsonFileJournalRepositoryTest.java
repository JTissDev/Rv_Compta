package com.jtissdev_API.features.compta.repository;

import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.utils.TestDataLoader;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
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
 * Integration and unit tests for the {@link JsonFileJournalRepository} class.
 * <p>
 * Uses the official test dataset to validate the full JSON serialization
 * and flat-file storage loop under version 0.6.0 specifications.
 * </p>
 *
 * @author J.Tiss
 * @email jtissdev@gmail.com
 * @version 1.3.0
 * @since 0.6.0
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(TestResultLogger.class)
@DisplayName("Json File Journal Repository Test Suite")
@TestGroup("Compta Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFileJournalRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFileJournalRepositoryTest.class);

	@Autowired
	private JsonFileJournalRepository repository;

	@Value("${app.persistence.storage-path}")
	private String configuredStorageDir;

	@Value("${app.persistence.filename}")
	private String configuredFilename;

	private Path fullTestFilePath;
	private JournalDTO sampleJournal;

	@BeforeEach
	void setUp() throws IOException {
		// Résolution dynamique du chemin complet basé sur tes fichiers .properties
		this.fullTestFilePath = Paths.get(configuredStorageDir, configuredFilename).toAbsolutePath().normalize();

		logger.info("[Test Setup] Cleaning potential residual file at: {}", fullTestFilePath);
		Files.deleteIfExists(fullTestFilePath);

		// Chargement sécurisé depuis le dossier resources via ton TestDataLoader
		JsonObject testDataJson = TestDataLoader.loadFromResources("data/journal-test.json");
		JsonObject initialJournalJson = testDataJson.getJsonObject("initialJournal");

		// Hydratation du DTO témoin avec le constructeur officiel
		this.sampleJournal = new JournalDTO(initialJournalJson);
	}

	/* @Test
	@Order(1)
	@DisplayName("Load when file does not exist")
	void testLoadWhenFileDoesNotExist() {
		logger.info("[Test] Executing LoadWhenFileDoesNotExist");
		Optional<JournalDTO> result = repository.load();

		assertAll("Verify empty storage behavior",
				() -> assertTrue(result.isEmpty(), "Repository should return an empty Optional when file is missing"),
				// On utilise getName() pour éviter les assertions strictes sur la racine absolue modifiée par l'IDE
				() -> assertEquals(configuredFilename, fullTestFilePath.getFileName().toString(), "The filename match expected configuration")
		);
	} */

	/* @Test
	@Order(2)
	@DisplayName("Save and Load nominal case")
	void testSaveAndLoadNominal() {
		logger.info("[Test] Executing SaveAndLoadNominal");

		// 1. Action : Sauvegarde du journal réel issu du JSON
		repository.save(sampleJournal);

		// 2. Assertion : Le fichier a bien été écrit sur le disque au chemin configuré par l'environnement
		assertTrue(Files.exists(fullTestFilePath), "The JSON file must be physically created at the target path");

		// 3. Action : Rechargement depuis le fichier à plat
		Optional<JournalDTO> loadedResult = repository.load();

		assertTrue(loadedResult.isPresent(), "The loaded Optional must contain a JournalDTO instance");
		JournalDTO loadedJournal = loadedResult.get();

		// 4. Assertions : Alignées de manière stricte sur ton JournalDTO v0.6.0
		assertAll("Verify data consistency after reload",
				() -> assertEquals(sampleJournal.getId(), loadedJournal.getId(), "Journal ID mismatch"),
				() -> assertEquals(sampleJournal.getName(), loadedJournal.getName(), "Journal name mismatch"),
				() -> assertEquals(sampleJournal.getJournalTypeCode(), loadedJournal.getJournalTypeCode(), "Journal type code mismatch"),
				() -> assertEquals(sampleJournal.getStartDate(), loadedJournal.getStartDate(), "Start date mismatch"),
				() -> assertEquals(sampleJournal.getEndDate(), loadedJournal.getEndDate(), "End date mismatch"),
				() -> assertEquals(sampleJournal.getOperations().size(), loadedJournal.getOperations().size(), "Operations count mismatch")
		);
	} */

	/* @Test
	@Order(3)
	@DisplayName("Save throws IllegalArgumentException on null input")
	void testSaveNullJournal() {
		logger.info("[Test] Executing SaveNullJournal");
		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Saving a null journal must throw an IllegalArgumentException");
	} */
}