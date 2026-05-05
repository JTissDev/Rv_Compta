package com.jtissdev_API;

import com.jtissdev.logging.banner.AppBanner;
import com.jtissdev_API.engine.loader.DetailsDataLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.engine.loader.TiersDataLoader;
import com.jtissdev_API.engine.worker.CommandLineWorker;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
//import com.jtissdev_API.utils.AppBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Main Application entry point.
 * Performs the "Test de Vérité" by loading PCG and PCP data.
 *
 * @author J.Tiss
 * @version 1.3
 * @since 0.1
 */
@SpringBootApplication
public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		Throwable errorCaptured = null;
		// Affichage de la bannière de démarrage personnalisée


		try {
			SpringApplication app = new SpringApplication(App.class);

			// On enregistre un écouteur qui se déclenchera au vrai moment de l'arrêt du serveur
			app.addListeners((ApplicationListener<ContextClosedEvent>) event -> {
				AppBanner.mainShutdown(null); // Tu peux gérer les statuts ici
			});

			app.setBannerMode(Banner.Mode.OFF);
			app.run(args);

		} catch (Throwable t) {
			// Capture les erreurs critiques au démarrage
			AppBanner.mainShutdown(t);
			throw t;
		}
	}


	/**
	 * Executes the "Test de Vérité" process for loading and verifying PCG and PCP data.
	 * This method loads the General Chart of Accounts (PCG) and Third Parties (PCP) data from JSON files,
	 * displays the loaded data in the console, and performs a basic verification.
	 *
	 * @param pcgLoader
	 * 		The loader responsible for reading the General Chart of Accounts (PCG) from the JSON file.
	 * @param tiersLoader
	 * 		The loader responsible for reading Third Parties (Tiers) data from the JSON file.
	 * @param detailsLoader
	 * 		The loader responsible for reading Details data from the JSON file.
	 * @return A {@code CommandLineRunner} implementation that performs the test when the application runs.
	 *
	 * @since 0.1
	 */
	@Bean
	@Profile("test")
	public CommandLineRunner testDeVerite(
			PcgDataLoader pcgLoader,
			TiersDataLoader tiersLoader,
			DetailsDataLoader detailsLoader) {

		return args -> {
			System.out.println("=== DÉMARRAGE DU TEST DE VÉRITÉ ===\n");

			// --- CHARGEMENT DU PCG ---
			System.out.println("> Chargement du Bloc PCG...");
			PcgCoreDTO pcgCore = pcgLoader.loadFromJson("data/PCG.json");

			// --- CHARGEMENT DU PCP ---
			System.out.println("> Chargement du Bloc PCP...");
			PcpCoreDTO pcpCore = new PcpCoreDTO();
			pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
			pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));

			// --- AFFICHAGE DU PCG ---
			System.out.println("\n--- CONTENU DU BLOC PCG ---");
			System.out.println("PCG : " + pcgCore.toString());

			// --- AFFICHAGE DU PCP ---
			System.out.println("\n--- CONTENU DU BLOC PCP ---");

			System.out.println("[Tiers] Nombre d'entrées : " + pcpCore.getThirdParties().size());
			pcpCore.getThirdParties().stream()
					.limit(5) // On en affiche 5 pour vérifier
					.forEach(t -> System.out.println("  - " + t.toString()));

			System.out.println("\n[Détails Niveau 4] Nombre d'entrées : " + pcpCore.getDetails().size());
			pcpCore.getDetails().stream()
					.limit(5)
					.forEach(d -> System.out.println("  - " + d.toString()));

			System.out.println("\n=== FIN DU TEST DE VÉRITÉ : TOUT EST CHARGÉ EN MÉMOIRE ===");
		};
	}

	/**
	 * Creates and returns a CommandLineRunner instance that executes in an operational mode.
	 * This runner initializes the application by displaying an operational mode banner
	 * and starting the provided worker.
	 *
	 * @param worker
	 * 		The CommandLineWorker instance responsible for executing the operational tasks.
	 * @return A CommandLineRunner which sets the application in operational mode and starts the worker.
	 *
	 * @since 0.4
	 */
	@Bean
	public CommandLineRunner operationalRunner(CommandLineWorker worker) {
		return args -> {
			// Test direct pour voir si le fichier est chargé
			java.util.Properties props = new java.util.Properties();
			try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("app-info.properties")) {
				if (is != null) {
					props.load(is);
					System.out.println("--- DEBUG VERSION MAVEN : " + props.getProperty("app.version") + " ---");
				} else {
					System.out.println("--- DEBUG : Fichier app-info.properties introuvable ! ---");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			AppBanner.display();
			System.out.println("=== MODE OPÉRATIONNEL : ENGINE "+ props.getProperty("app.version") +" ===");
			worker.start();
		};
	}
}