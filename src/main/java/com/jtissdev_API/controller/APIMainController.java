package com.jtissdev_API.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the main controller for the API application, responsible for initializing and running the API services.
 *
 * @since 0.6
 * @version 1.0.0
 * @author jtiss
 */
@RestController
@Profile("api")
@RequestMapping("/api/compta") // Une bonne pratique pour préfixer tes routes
public class APIMainController implements MainController {

	@Override
	public void run() {
		// En mode API, le "run" peut simplement servir à logger
		// que l'interface Web est prête.
		System.out.println("Serveur API démarré et prêt à recevoir des requêtes.");
	}

	// Tes futurs endpoints ressembleront à ceci :
	@GetMapping("/status")
	public String getStatus() {
		return "L'API de comptabilité est opérationnelle.";
	}
}
