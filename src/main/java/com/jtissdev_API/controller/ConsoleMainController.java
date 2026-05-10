package com.jtissdev_API.controller;

import com.jtissdev_API.core.util.ExcelReader;
import com.jtissdev_API.engine.loader.JournalLoader;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.compta.view.JournalConsoleView;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.view.MainConsoleView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Represents a ConsoleMainController, which is the main controller for the console application.
 *
 * @since 0.6
 * @version 1.0.0
 * @author jtiss
 */
@Component
@Profile({"dev", "prod", "show"})
public class ConsoleMainController implements MainController{

	@Value("${app.data.path}")
	private String dataPath;

	private final MainConsoleView mainConsoleView;
	private final JournalConsoleView journalView;
	private final OperationConsolView operationView;

	//private final Properties appProps = new Properties();
	private final JournalLoader journalLoader; // TODO À remplacer par JournalService plus tard
	//private final ExcelReader excelReader;
	private final Scanner scanner = new Scanner(System.in);


	public ConsoleMainController(JournalLoader journalLoader/*, ExcelReader excelReader*/) {
		this.mainConsoleView = new MainConsoleView();
		this.journalView = new JournalConsoleView();
		this.operationView = new OperationConsolView();
		this.journalLoader = journalLoader;
		//this.excelReader = excelReader;

		/*try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("app-info.properties")) {
			if (is != null) {
				this.appProps.load(is);
				//System.out.println("--- DEBUG VERSION MAVEN : " + this.props.getProperty("app.version") + " ---");
			} else {
				//System.out.println("--- DEBUG : Fichier app-info.properties introuvable ! ---");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}
	@Override
	public void run() {
		mainConsoleView.displayHeader();
		JournalDTO journal = loadJournalInitial();
		journalView.displayJournalHeader(journal);

		boolean running = true;
		while (running) {
			mainConsoleView.displayMainMenu();
			String choice = scanner.nextLine();

			switch (choice) {
				case "1" -> mainConsoleView.displayMessage("> Importation Excel à implémenter...");
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

	private void processEdit(JournalDTO journal) {
		List<OperationDTO> ops = journal.getOperations();

		if (ops.isEmpty()) {
			mainConsoleView.displayMessage("> Le journal est vide, aucune opération à modifier.");
			return;
		}

		mainConsoleView.displayMessage("\n--- [ MODIFIER UNE OPÉRATION ] ---");

		// On réaffiche la liste brièvement pour que l'utilisateur voit les numéros (1 à N)
		for (int i = 0; i < ops.size(); i++) {
			System.out.println("[" + (i + 1) + "] " + ops.get(i).getDescriptif() + " (" + ops.get(i).getDateOperation() + ")");
		}

		mainConsoleView.displayMessage("\nEntrez le numéro de l'opération à modifier (ou 0 pour annuler) : ");

		try {
			int index = Integer.parseInt(scanner.nextLine()) - 1; // -1 car la liste commence à 0

			if (index == -1) {
				mainConsoleView.displayMessage("> Modification annulée.");
				return;
			}

			if (index >= 0 && index < ops.size()) {
				OperationDTO targetOp = ops.get(index);

				// On affiche le détail avant modif
				mainConsoleView.displayMessage("\n> Opération sélectionnée :");
				operationView.displayOperation(targetOp);

				// Saisie de la modification
				mainConsoleView.displayMessage("Nouveau libellé (laissez vide pour conserver l'actuel) : ");
				String newDesc = scanner.nextLine();

				if (!newDesc.trim().isEmpty()) {
					targetOp.setDescriptif(newDesc);
					mainConsoleView.displayMessage("✅ Opération mise à jour.");
				} else {
					mainConsoleView.displayMessage("> Aucune modification apportée.");
				}

			} else {
				mainConsoleView.displayError("Numéro invalide.");
			}
		} catch (NumberFormatException e) {
			mainConsoleView.displayError("Veuillez entrer un chiffre valide.");
		}
	}

	private JournalDTO loadJournalInitial() {
		// Utilisation du dataPath injecté par Spring
		File journalFile = new File(dataPath + "compta.json");
		if (journalFile.exists()) {
			try (FileInputStream fis = new FileInputStream(journalFile)) {
				return journalLoader.loadJournal(fis);
			} catch (Exception e) {
				mainConsoleView.displayError("Erreur de lecture : " + e.getMessage());
			}
		}
		return new JournalDTO();
	}
}
