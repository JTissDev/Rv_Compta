package com.jtissdev_API.controller;

import com.jtissdev_API.core.util.ExcelReader;
import com.jtissdev_API.engine.loader.JournalLoader;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.compta.view.JournalConsoleView;
import com.jtissdev_API.features.compta.view.OperationConsolView;
import com.jtissdev_API.view.MainConsoleView;
import com.jtissdev_API.view.ViewUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a ConsoleMainController, which is the main controller for the console application.
 *
 * @since 0.6
 * @version 1.0.0
 * @author jtiss
 */
@Component
@Profile("console")
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
	private JournalDTO currentJournal;


	public ConsoleMainController(JournalLoader journalLoader/*, ExcelReader excelReader*/) {
		this.mainConsoleView = new MainConsoleView();
		this.journalView = new JournalConsoleView();
		this.operationView = new OperationConsolView();
		this.journalLoader = journalLoader;
		//this.excelReader = excelReader;



	}
	@Override
	public void run() {
		mainConsoleView.displayHeader();
		currentJournal = loadJournalInitial();
		journalView.displayJournal(currentJournal);

		boolean quitApp = false;
		while (!quitApp) {
			mainConsoleView.displayMainMenu();
			String choice = scanner.nextLine();

			switch (choice) {
				case "1" -> ViewUtil.displayNotImplemented(); //processImport(currentJournal);
				case "2" -> quitApp = handleJournalMenu();
				case "0" -> {
					quitApp = true;
					ViewUtil.displayMessage("> Fermeture du programme.");
				}
				default -> ViewUtil.displayError("Choix invalide.");
			}
		}
	}


	private boolean handleJournalMenu() {
		boolean back = false;
		boolean quitApp = false;
		while (!back && !quitApp) {
			List<OperationDTO> operations = currentJournal.getOperations();
			journalView.displayJournalForSelection(operations);

			String input = scanner.nextLine().trim();
			String[] parts = input.split("\\s+");
			String action = parts[0].toUpperCase();

			switch (action) {
				case "N":
					newOperation();
					break;
				case "UP":
					processUpdateCommand(parts, operations);
					break;
				case "INS":
					processInsertCommand(parts, operations);
					break;
				case "B": // Back
					back = true;
					break;
				case "Q": // Quit
					quitApp = true;
					break;
				default:
					ViewUtil.displayError("Action invalide dans le journal");
			}
		}
		return quitApp;
	}

	private void processUpdateCommand(String[] parts, List<OperationDTO> operations) {
		ViewUtil.displayNotImplemented();
		//TODO need to be implemented after implementing the view
	}

	private void processInsertCommand(String[] parts, List<OperationDTO> operations) {
		ViewUtil.displayNotImplemented();
		//TODO need to be implemented after implementing the view
	}

	private void newOperation() {
		ViewUtil.displayNotImplemented();
		//TODO need to be implemented after implementing the view
	}



	private void processImport(JournalDTO journal) {
		// Ici, tu remets ta logique de sélection de fichier Excel[cite: 49]
		ViewUtil.displayMessage("> Lancement de l'importation Excel...");
		// Appelle tes méthodes privées de lecture excel ici
	}

	private void processManual(JournalDTO journal) {
		ViewUtil.displayMessage("\n--- [SAISIE MANUELLE] ---");
		OperationDTO op = new OperationDTO();

		ViewUtil.displayMessage("Date (JJ/MM/AAAA) : ");
		String dateStr = scanner.nextLine();
		op.setDateOperation(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		ViewUtil.displayMessage("Libellé : ");
		op.setDescriptif(scanner.nextLine());

		journal.getOperations().add(op);
		ViewUtil.displayMessage("✅ Opération ajoutée au journal.");
	}

	private void processEdit(JournalDTO journal) {
		List<OperationDTO> ops = journal.getOperations();

		if (ops.isEmpty()) {
			ViewUtil.displayMessage("> Le journal est vide, aucune opération à modifier.");
			return;
		}

		ViewUtil.displayMessage("\n--- [ MODIFIER UNE OPÉRATION ] ---");

		// On réaffiche la liste brièvement pour que l'utilisateur voit les numéros (1 à N)
		for (int i = 0; i < ops.size(); i++) {
			System.out.println("[" + (i + 1) + "] " + ops.get(i).getDescriptif() + " (" + ops.get(i).getDateOperation() + ")");
		}

		ViewUtil.displayMessage("\nEntrez le numéro de l'opération à modifier (ou 0 pour annuler) : ");

		try {
			int index = Integer.parseInt(scanner.nextLine()) - 1; // -1 car la liste commence à 0

			if (index == -1) {
				ViewUtil.displayMessage("> Modification annulée.");
				return;
			}

			if (index >= 0 && index < ops.size()) {
				OperationDTO targetOp = ops.get(index);

				// On affiche le détail avant modif
				ViewUtil.displayMessage("\n> Opération sélectionnée :");
				operationView.displayOperation(targetOp);

				// Saisie de la modification
				ViewUtil.displayMessage("Nouveau libellé (laissez vide pour conserver l'actuel) : ");
				String newDesc = scanner.nextLine();

				if (!newDesc.trim().isEmpty()) {
					targetOp.setDescriptif(newDesc);
					ViewUtil.displayMessage("✅ Opération mise à jour.");
				} else {
					ViewUtil.displayMessage("> Aucune modification apportée.");
				}

			} else {
				ViewUtil.displayError("Numéro invalide.");
			}
		} catch (NumberFormatException e) {
			ViewUtil.displayError("Veuillez entrer un chiffre valide.");
		}
	}

	private JournalDTO loadJournalInitial() {
		// Utilisation du dataPath injecté par Spring
		File journalFile = new File(dataPath + "compta.json");
		if (journalFile.exists()) {
			try (FileInputStream fis = new FileInputStream(journalFile)) {
				return journalLoader.loadJournal(fis);
			} catch (Exception e) {
				ViewUtil.displayError("Erreur de lecture : " + e.getMessage());
			}
		}
		return new JournalDTO();
	}
}
