package com.jtissdev_API.controller;

import com.jtissdev_API.core.util.ExcelReader;
import com.jtissdev_API.engine.loader.JournalLoader;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.compta.view.JournalConsoleView;
import com.jtissdev_API.view.MainConsoleView;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;

/**
 * Represents a MainConsoleController DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
@Component
public class MainConsoleController {
	private final MainConsoleView mainConsoleView;
	private final JournalConsoleView journalView;

	private final Properties appProps = new Properties();
	private final JournalLoader journalLoader; // TODO À remplacer par JournalService plus tard
	private final ExcelReader excelReader;
	private final Scanner scanner = new Scanner(System.in);

	public MainConsoleController(JournalLoader journalLoader, ExcelReader excelReader) {
		this.mainConsoleView = new MainConsoleView();
		this.journalView = new JournalConsoleView();
		this.journalLoader = journalLoader;
		this.excelReader = excelReader;

		try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("app-info.properties")) {
			if (is != null) {
				this.appProps.load(is);
				//System.out.println("--- DEBUG VERSION MAVEN : " + this.props.getProperty("app.version") + " ---");
			} else {
				//System.out.println("--- DEBUG : Fichier app-info.properties introuvable ! ---");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		// Logique de chargement initial récupérée de App et CLW[cite: 54, 55]
		mainConsoleView.displayHeader();

		JournalDTO journal = loadJournalInitial();

		journalView.displayOperations(journal.getOperations());

		boolean running = true;
		while (running) {
			mainConsoleView.displayMainMenu();
			String choice = scanner.nextLine();

			switch (choice) {
				case "1" -> processImport(journal);
				case "2" -> processManual(journal);
				case "0" -> {
					running = false;
					mainConsoleView.displayMessage("> Fermeture du programme.");
				}
				default -> mainConsoleView.displayError("Choix invalide.");
			}
		}
	}

	private void processImport(JournalDTO journal) {
		// Ici, tu remets ta logique de sélection de fichier Excel[cite: 49]
		mainConsoleView.displayMessage("> Lancement de l'importation Excel...");
		// Appelle tes méthodes privées de lecture excel ici
	}

	private void processManual(JournalDTO journal) {
		mainConsoleView.displayMessage("\n--- [SAISIE MANUELLE] ---");
		OperationDTO op = new OperationDTO();

		mainConsoleView.displayMessage("Date (JJ/MM/AAAA) : ");
		String dateStr = scanner.nextLine();
		op.setDateOperation(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		mainConsoleView.displayMessage("Libellé : ");
		op.setDescriptif(scanner.nextLine());

		journal.getOperations().add(op);
		mainConsoleView.displayMessage("✅ Opération ajoutée au journal.");
	}

	private JournalDTO loadJournalInitial() {
		File journalFile = new File("data/compta.json");
		if (journalFile.exists()) {
			try (FileInputStream fis = new FileInputStream(journalFile)) {
				return journalLoader.loadJournal(fis);
			} catch (Exception e) {
				mainConsoleView.displayError("Erreur de lecture : " + e.getMessage());
			}
		}
		return new JournalDTO(); // Retourne un journal vide si erreur ou inexistant[cite: 49]
	}
}
