package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import com.jtissdev.utils.TestGroup;
import com.jtissdev.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
@TestGroup("Referential Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFileReferentialCoreRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFileReferentialCoreRepositoryTest.class);

	@Autowired
	private JsonFileReferentialCoreRepository repository;

	//@Value("${app.persistence.storage-path}")
	private String configuredStorageDir;

	//@Value("${app.persistence.filename-status}")
	private String configuredStatusFilename;

	//@Value("${app.persistence.filename-payment}")
	private String configuredPaymentFilename;

	private Path fullStatusPath;
	private Path fullPaymentPath;
	private ReferentialCoreDTO sampleReferential;

	@Test
	@Order(1)
	@DisplayName("✅ Chargement initial et déploiement automatique des graines (Seeds)")
	void testLoadInitialAndSeedDeployment() {
		logger.info("[Test] Exécution de testLoadInitialAndSeedDeployment");

		// Action : Charger le référentiel central (ceci doit déclencher le mécanisme de fallback Seed si les fichiers live n'existent pas)
		Optional<ReferentialCoreDTO> optionalRef = repository.load();

		// Assertions sur l'hydratation du DTO
		assertTrue(optionalRef.isPresent(), "Le référentiel doit être chargé avec succès (données initiales ou fichiers live)");
		ReferentialCoreDTO dto = optionalRef.get();

		assertNotNull(dto.getOperationStatuses(), "La liste des statuts d'opération ne doit pas être nulle");
		assertNotNull(dto.getPaymentMethods(), "La liste des méthodes de paiement ne doit pas être nulle");

		// Vérification physique de l'existence des fichiers live déployés automatiquement dans la zone d'environnement
		File statusFile = repository.getStatusLiveFile();
		File paymentFile = repository.getPaymentLiveFile();

		assertNotNull(statusFile, "Le pointeur vers le fichier live des statuts ne doit pas être nul");
		assertNotNull(paymentFile, "Le pointeur vers le fichier live des paiements ne doit pas être nul");

		assertTrue(statusFile.exists(), "Le fichier live des statuts aurait dû être créé/copié depuis les ressources de graine (Seed)");
		assertTrue(paymentFile.exists(), "Le fichier live des paiements aurait dû être créé/copié depuis les ressources de graine (Seed)");

		logger.info("         ✅ SUCCÈS : Déploiement automatique des graines et chargement initial validés");
	}

	@Test
	@Order(2)
	@DisplayName("✅ Cycle complet d'écriture et de relecture (Save & Load)")
	void testSaveAndLoadCycle() {
		logger.info("[Test] Exécution de testSaveAndLoadCycle");

		// 1. Préparation d'un jeu de données de test en utilisant la Fluent API des DTOs
		OperationStatus customStatus = new OperationStatus()
				                               .setCode("TST_PLAN")
				                               .setName("Planifié pour Test")
				                               .setColor("#FF5733");

		PaymentMethod customPayment = new PaymentMethod()
				                              .setCode("TST_CB")
				                              .setName("Carte Bancaire Virtuelle Test")
				                              .setDescription("Moyen de paiement de test pour l'automatisation");

		ReferentialCoreDTO newDto = new ReferentialCoreDTO();
		newDto.addOperationStatus(customStatus);
		newDto.addPaymentMethod(customPayment);

		// 2. Action : Sauvegarde dans les fichiers physiques correspondants
		assertDoesNotThrow(() -> repository.save(newDto), "La sauvegarde d'un DTO de référentiel valide ne doit lever aucune exception");

		// 3. Action : Relecture depuis la persistance pour valider la bonne écriture/lecture
		Optional<ReferentialCoreDTO> reloadedOptional = repository.load();
		assertTrue(reloadedOptional.isPresent(), "Le référentiel doit pouvoir être rechargé depuis les fichiers modifiés");
		ReferentialCoreDTO reloadedDto = reloadedOptional.get();

		// 4. Assertions : Vérifier que nos données personnalisées sont bien présentes dans le flux JSON persistant
		boolean hasCustomStatus = reloadedDto.getOperationStatuses().stream()
				                          .anyMatch(s -> "TST_PLAN".equals(s.getCode()) && "Planifié pour Test".equals(s.getName()) && "#FF5733".equals(s.getColor()));

		boolean hasCustomPayment = reloadedDto.getPaymentMethods().stream()
				                           .anyMatch(p -> "TST_CB".equals(p.getCode()) && "Carte Bancaire Virtuelle Test".equals(p.getName()));

		assertTrue(hasCustomStatus, "Le statut d'opération personnalisé sauvegardé doit être retrouvé après relecture");
		assertTrue(hasCustomPayment, "La méthode de paiement personnalisée sauvegardée doit être retrouvée après relecture");

		logger.info("         ✅ SUCCÈS : Le cycle complet d'écriture et de relecture est valide");
	}

	@Test
	@Order(3)
	@DisplayName("Save throws IllegalArgumentException on null input")
	void testSaveNullReferential() {
		logger.info("[Test] Executing SaveNullReferential");
		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Saving a null referential must throw an IllegalArgumentException");
	}

	@Disabled("Seed fallback mechanism validation test - enable to validate seed copy when files are missing")
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

	@Disabled("Custom data save and nominal load test - enable to validate custom data persistence and nominal load behavior")
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
}