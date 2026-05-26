package com.jtissdev_API.features.PCG.repository;

import com.jtissdev_API.features.PCG.dto.PcgCoreDTO;
import com.jtissdev_API.utils.TestGroup;
import com.jtissdev_API.utils.TestResultLogger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // INDISPENSABLE pour injecter le repository et lire les properties
@ExtendWith(TestResultLogger.class)
@DisplayName("Json File PCG Repository Test Suite")
@TestGroup("Core Repository")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonFilePcgRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonFilePcgRepositoryTest.class);

	@Autowired
	private JsonFilePcgRepository repository;

	private Path targetPcgFile;

	@BeforeEach
	void setUp() {
		//logger.info("before each test");
		// On utilise directement le fichier géré par le repository pour éviter toute erreur de nommage (ex: pcg-test.json)
		this.targetPcgFile = repository.getPcgLiveFile().toPath();
	}

	@Test
	@Order(1)
	@DisplayName("Chargement initial : copie de la seed si le fichier vivant n'existe pas")
	void testLoadFallbackSeed() throws IOException {
		//logger.info("[Test] Executing LoadFallbackSeed");

		// PRÉREQUIS CRUCIAL : On supprime le fichier pour FORCER le déclenchement de la copie de la graine
		Files.deleteIfExists(targetPcgFile);
		assertFalse(Files.exists(targetPcgFile), "Le fichier ne doit pas exister avant le test");

		// Action : Lecture initiale du dépôt
		Optional<PcgCoreDTO> pcgResult = repository.load();

		// Assertions : Le fichier doit avoir été déployé automatiquement à partir de la seed
		assertTrue(pcgResult.isPresent(), "L'Optional doit contenir une instance de PcgCoreDTO chargée depuis la seed");
		PcgCoreDTO pcg = pcgResult.get();

		assertNotNull(pcg.getAccountingClasses(), "La liste des classes comptables ne doit pas être nulle");
		assertTrue(Files.exists(targetPcgFile), "Le fichier physique doit désormais exister dans le dossier de test");

		logger.info("         ✅ SUCCÈS : Déploiement et lecture de la seed PCG validés");
	}

	@Test
	@Order(2)
	@DisplayName("Sauvegarde et rechargement complet : Cycle de persistance nominal")
	void testSaveAndLoadCycle() throws IOException {
		//logger.info("[Test] Executing SaveAndLoadCycle");

		// 1. Arrange : Assurons-nous que des données existent via un premier chargement
		Optional<PcgCoreDTO> currentPcgOpt = repository.load();
		assertTrue(currentPcgOpt.isPresent(), "Les données initiales doivent être présentes");
		PcgCoreDTO originalPcg = currentPcgOpt.get();

		// 2. Action : Sauvegarde sur le disque
		repository.save(originalPcg);

		// 3. Assertion physique : Vérification de la présence effective du fichier sur le disque
		assertTrue(Files.exists(targetPcgFile), "Le fichier cible doit persister sur le disque");

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
	@DisplayName("Vérification de la taille : Méthode getDataSize sans écrasement")
	void testGetDataSize() {
		//logger.info("[Test] Executing GetDataSize");

		// On s'assure que le fichier est chargé
		repository.load();

		// Action
		int size = repository.getDataSize();

		// Assertion : La graine contient forcément des données, la taille doit être strictement positive
		assertTrue(size > 0, "La taille des données PCG doit être supérieure à 0");

		logger.info("         ✅ SUCCÈS : La méthode getDataSize retourne correctement la taille ({} éléments)", size);
	}

	@Test
	@Order(4)
	@DisplayName("Sécurité : Levée d'une IllegalArgumentException en cas d'argument null lors de la sauvegarde")
	void testSaveNullPcg() {
		//logger.info("[Test] Executing SaveNullPcg");

		// Action & Assertion
		assertThrows(IllegalArgumentException.class, () -> repository.save(null),
				"Tenter de sauvegarder une structure PCG nulle doit lever une IllegalArgumentException");

		logger.info("         ✅ SUCCÈS : Le garde-fou contre les structures nulles est opérationnel");
	}

	@BeforeAll
	static void startTestSuite() {
		logger.info("      ■ DÉMARRAGE : Json File PCG Repository Test Suite");
	}


}