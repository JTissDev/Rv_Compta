package com.jtissdev_API;

import com.jtissdev.logging.banner.AppBanner;
import com.jtissdev_API.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Main Application entry point.
 * Performs the "Test de Vérité" by loading PCG and PCP data.
 *
 * @author J.Tiss
 * @version 1.4
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
	 * Creates and returns a CommandLineRunner instance that executes in an operational mode.
	 * This runner initializes the application by displaying an operational mode banner
	 * and starting the provided worker.
	 *
	 * @param mainController
	 * 		The MainConsoleController instance responsible for executing the operational tasks.
	 * @return A CommandLineRunner which sets the application in operational mode and starts the worker.
	 *
	 * @since 0.4
	 */
	@Bean
	public CommandLineRunner operationalRunner(MainController mainController) { // Utilise l'interface
		return args -> {
			mainController.run();
		};
	}
}