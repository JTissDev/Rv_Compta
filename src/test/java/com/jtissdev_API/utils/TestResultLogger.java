package com.jtissdev_API.utils;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLogger implements BeforeAllCallback, AfterAllCallback, AfterTestExecutionCallback, ExtensionContext.Store.CloseableResource {
	private static final Logger logger = LoggerFactory.getLogger(TestResultLogger.class);

	// --- COMPTEURS GLOBAUX ---
	private static int globalTotal = 0;
	private static int globalSuccess = 0;
	private static int globalFailure = 0;

	// --- COMPTEURS DE BLOC (Statiques pour survivre entre les fichiers) ---
	private static int blockTotal = 0;
	private static int blockSuccess = 0;
	private static int blockFailure = 0;
	private static String currentGroupName = null;

	// --- COMPTEURS DE FICHIER (D'instance pour reset automatique) ---
	private int fileTotal = 0;
	private int fileSuccess = 0;
	private int fileFailure = 0;

	private static boolean globalHeaderPrinted = false;

	@Override
	public void beforeAll(ExtensionContext context) {
		// Enregistre l'instance pour le callback close() final
		context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("GLOBAL_LOGGER", this);

		// 1. MESSAGE DE DÉBUT GÉNÉRAL (S'affiche une seule fois au tout début)
		if (!globalHeaderPrinted) {
			// Chargement des infos dynamiques depuis le POM (via Maven Resource Filtering)
			java.util.Properties props = loadProjectProperties();
			String projectName = props.getProperty("project.name", "RV-Compta");
			String version = props.getProperty("project.version", "Unknown");
			String devs = props.getProperty("project.devs", "jtiss");

			logger.info("################################################################################");
			logger.info("## 🚀 DÉMARRAGE GLOBAL DE LA SÉQUENCE DE TESTS");
			logger.info("## Nom du projet : {}", projectName);
			logger.info("## Version       : {}", version);
			logger.info("## Date/Heure    : {}", java.time.LocalDateTime.now());
			logger.info("## Developed by  : {}", devs);
			logger.info("################################################################################");
			globalHeaderPrinted = true;
		}

		// 2. GESTION DU BLOC (TestGroup)
		String groupName = "INDÉPENDANT";
		if (context.getRequiredTestClass().isAnnotationPresent(TestGroup.class)) {
			groupName = context.getRequiredTestClass().getAnnotation(TestGroup.class).value();
		}

		// Si on change de groupe, on affiche les stats du groupe précédent
		if (!groupName.equals(currentGroupName)) {
			if (currentGroupName != null) {
				printBlockSummary(); // Affiche le bilan du bloc qui vient de finir
			}
			// Reset des compteurs de bloc pour le nouveau groupe
			blockTotal = 0;
			blockSuccess = 0;
			blockFailure = 0;
			currentGroupName = groupName;

			logger.info("");
			logger.info("   ╔════════════════════════════════════════════════════════════════════════════");
			logger.info("   ║ BLOC : {}", groupName);
			logger.info("   ╚════════════════════════════════════════════════════════════════════════════");
		}

		logger.info("      ▶ FICHIER : {}", context.getDisplayName());
	}

	@Override
	public void afterTestExecution(ExtensionContext context) {
		String testName = context.getDisplayName();
		boolean failed = context.getExecutionException().isPresent();

		// Incrémentation de tous les niveaux
		fileTotal++;
		blockTotal++;
		globalTotal++;

		if (failed) {
			fileFailure++;
			blockFailure++;
			globalFailure++;
			logger.error("         ❌ ÉCHEC : {} -> {}", testName, context.getExecutionException().get().getMessage());
		} else {
			fileSuccess++;
			blockSuccess++;
			globalSuccess++;
			logger.info("         ✅ SUCCÈS : {}", testName);
		}
	}

	@Override
	public void afterAll(ExtensionContext context) {
		// Stats du fichier (interne à la classe)
		logger.info("      ■ FIN FICHIER : {} (Stats: {}/{} success)",
				context.getDisplayName(), fileSuccess, fileTotal);
	}

	private void printBlockSummary() {
		logger.info("   [📊 BILAN BLOC : {} | Tests: {} | ✅ {} | ❌ {}]",
				currentGroupName, blockTotal, blockSuccess, blockFailure);
		logger.info("   -----------------------------------------------------------------------------");
	}

	@Override
	public void close() {
		// Affiche le bilan du dernier bloc actif
		if (currentGroupName != null) {
			printBlockSummary();
		}

		// 3. BILAN GÉNÉRAL FINAL
		logger.info("");
		logger.info("################################################################################");
		logger.info("## 🏁 FIN DE TOUS LES TESTS");
		logger.info("## TOTAL GÉNÉRAL : {} | SUCCÈS : {} | ÉCHECS : {}", globalTotal, globalSuccess, globalFailure);
		double successRate = globalTotal > 0 ? (double) globalSuccess / globalTotal * 100 : 0;
		logger.info("## TAUX DE RÉUSSITE : {}%", String.format("%.2f", successRate));
		logger.info("################################################################################");
	}

	private java.util.Properties loadProjectProperties() {
		java.util.Properties props = new java.util.Properties();
		try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("app-info.properties")) {
			if (is != null) {
				props.load(is);
			}
		} catch (java.io.IOException e) {
			logger.error("Impossible de charger app-info.properties");
		}
		return props;
	}
}