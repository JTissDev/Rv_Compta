package com.jtissdev_API.engine.worker;

import com.jtissdev_API.core.util.ExcelReader;
import com.jtissdev_API.engine.loader.*;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Main worker for command line interaction and import orchestration.
 * <p>
 * This component acts as the bridge between Spring Boot's context
 * and the interactive import logic defined in the v0.4 algorithm.
 * </p>
 *
 * @author jtiss
 * @since 0.4.0
 * @version 1.5.3
 */
@Component
public class CommandLineWorker {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	private final PcgDataLoader pcgLoader;
	private final TiersDataLoader tiersLoader;
	private final DetailsDataLoader detailsLoader;
	private final JournalLoader journalLoader;
	private final ExcelReader excelReader;

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Constructs an instance of CommandLineWorker with the necessary data loaders and Excel reader.
	 *
	 * @param pcgLoader      the data loader responsible for loading and parsing accounting plan data
	 * @param tiersLoader    the data loader responsible for loading and parsing tiers data
	 * @param detailsLoader  the data loader responsible for loading and parsing details data
	 * @param journalLoader  the loader responsible for handling journal data
	 * @param excelReader    the component responsible for reading and processing Excel files
	 *
	 * @since 0.4
	 */
	public CommandLineWorker(PcgDataLoader pcgLoader,
	                         TiersDataLoader tiersLoader,
	                         DetailsDataLoader detailsLoader,
	                         JournalLoader journalLoader, ExcelReader excelReader) {
		this.pcgLoader = pcgLoader;
		this.tiersLoader = tiersLoader;
		this.detailsLoader = detailsLoader;
		this.journalLoader = journalLoader;
		this.excelReader = excelReader;
	}

	// =========================================================
	// == METHODS                                             ==
	// =========================================================

	/**
	 * Starts the operational import workflow.
	 * Initialize referentials and start interactiv menu.1
	 *
	 * @throws Exception if data loading or processing fails
	 * @since 0.4.0
	 */
	public void start() throws Exception {
		// 1. Initialisation (Algorithme Etape 1)
		System.out.println("\n--- [INIT] CHARGEMENT DES RÉFÉRENTIELS ---");

		PcgCoreDTO pcgCore = pcgLoader.loadFromJson("data/PCG.json");
		PcpCoreDTO pcpCore = new PcpCoreDTO();
		pcpCore.setThirdParties(tiersLoader.loadTiersFromJson("Tiers.json"));
		pcpCore.setDetails(detailsLoader.loadDetailsFromJson("Details.json"));


		System.out.println("> Référentiels chargés. Prêt pour l'importation interactive.");

		// 2. Chargement du journal existant
		File journalFile = new File("data/compta.json");
		JournalDTO currentJournal = null;

		if (journalFile.exists()) {
			try (FileInputStream fis = new FileInputStream(journalFile)) {
				currentJournal = journalLoader.loadJournal(fis);
			} catch (Exception e) {
				System.err.println("> Erreur lors de la lecture du journal : " + e.getMessage());
			}
		}

// Sécurité : Si le fichier n'existe pas OU si le loader a renvoyé null
		if (currentJournal == null) {
			currentJournal = new JournalDTO();
			// On s'assure que la liste interne est initialisée si ton DTO ne le fait pas déjà
			if (currentJournal.getOperations() == null) {
				currentJournal.setOperations(new java.util.ArrayList<>());
			}
			System.out.println("> Nouveau journal initialisé (vierge).");
		} else {
			System.out.println("> Journal existant chargé (" + journalFile.getPath() + ")");
		}

// 3. Affichage des dernières opérations
		displayLastOperations(currentJournal, 5);

		System.out.println("\n------------------------------------------");

		// 4. Choix du type d'ajout des operations
		boolean running = true;
		Scanner scanner = new Scanner(System.in);

		while (running) {
			System.out.println("\n--- [MENU PRINCIPAL] ---");
			System.out.println("[1] Charger un fichier Excel (Importation)");
			System.out.println("[2] Ajouter une opération manuellement");
			System.out.println("[0] Quitter");
			System.out.print("\nVotre choix : ");

			String input = scanner.nextLine();

			switch (input) {
				case "1":
					processExcelImport(scanner, currentJournal);
					break;
				case "2":
					processManualEntry(scanner, currentJournal);
					break;
				case "0":
					running = false;
					System.out.println("> Fermeture du programme.");
					break;
				default:
					System.out.println("⚠️ Choix invalide.");
			}
		}
	}

	/**
	 * Displays the last N operations from the journal.
	 *
	 * @param journal the journal to scan
	 * @param count number of operations to show
	 * @since 0.4.0
	 */
	private void displayLastOperations(JournalDTO journal, int count) {
		System.out.println("\n--- [JOURNAL] DERNIÈRES OPÉRATIONS ---");

		if (journal.getOperations() == null || journal.getOperations().isEmpty()) {
			System.out.println(" > Le journal est actuellement vide.");
			return;
		}

		List<OperationDTO> ops = journal.getOperations();
		int total = ops.size();
		int start = Math.max(0, total - count);

		System.out.println("Affichage de " + (total - start) + " sur " + total + " opérations :");

		for (int i = start; i < total; i++) {
			OperationDTO op = ops.get(i);
			// On utilise le toString() de ton OperationDTO
			System.out.println(op.toString());

		}
	}

	/**
	 * Handles the Excel file selection and processing.
	 * @param scanner to get user input
	 * @since 0.4.0
	 */
	private void processExcelImport(Scanner scanner, JournalDTO journal) {
		File selectedFile = selectExcelFile(scanner);
		if (selectedFile != null) {
			try {
				List<List<String>> rows = excelReader.readExcel(selectedFile);
				System.out.println("> Lecture terminée : " + (rows.size() - 1) + " lignes trouvées (hors entête).");

				// On commence à la ligne 1 pour sauter l'entête
				for (int i = 1; i < rows.size(); i++) {
					List<String> row = rows.get(i);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

					// row.get(0) = Date, row.get(1) = Libellé, row.get(2) = Montant, etc.
					processSingleExcelRow(row, scanner);
					OperationDTO op = new OperationDTO();
					op.setDateOperation(LocalDate.parse(row.get(0), formatter));
					op.setDateComptable(LocalDate.parse(row.get(1), formatter));
					op.setDescriptif(row.get(2).replace("\n", " ").trim());

					double debit = parseAmount(row.get(3));
					double credit = parseAmount(row.get(4));

					//ventilateOperation(op, credit, debit, scanner);
					journal.getOperations().add(op);

				}

			} catch (Exception e) {
				System.err.println("❌ Erreur lors de la lecture Excel : " + e.getMessage());
			}
		}
	}




	/**
	 * Processes a manual entry operation by prompting the user for inputs via the provided scanner.
	 * The operation details, such as the date and description, are collected interactively
	 * and then added to the specified journal.
	 *
	 * @param scanner the Scanner object to collect user input from the console
	 * @param journal the JournalDTO object to which the new operation is added
	 *
	 * @since 0.4
	 */
	private void processManualEntry(Scanner scanner, JournalDTO journal) {
		System.out.println("\n--- [SAISIE MANUELLE] ---");
		OperationDTO op = new OperationDTO();

		System.out.print("Date (JJ/MM/AAAA) : ");
		String dateStr = scanner.nextLine();
		op.setDateOperation(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		System.out.print("Libellé : ");
		op.setDescriptif(scanner.nextLine());

		//ventilateOperation(op, scanner);
		journal.getOperations().add(op);
	}


	/**
	 * Scans the data directory for Excel files and lets the user choose one.
	 * @param scanner current scanner
	 * @return the selected File or null
	 * @since 0.4.0
	 */
	private File selectExcelFile(Scanner scanner) {
		File dataDir = new File("data");
		File[] files = dataDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx") && !name.startsWith("~$"));

		if (files == null || files.length == 0) {
			System.err.println("❌ Aucun fichier Excel trouvé dans le dossier /data");
			return null;
		}

		System.out.println("\n--- SÉLECTION DU FICHIER ---");
		for (int i = 0; i < files.length; i++) {
			System.out.println(String.format("[%d] %s", i + 1, files[i].getName()));
		}
		System.out.println("[0] Retour");

		System.out.print("\nVotre choix : ");
		try {
			int choice = Integer.parseInt(scanner.nextLine());
			if (choice > 0 && choice <= files.length) {
				return files[choice - 1];
			}
		} catch (NumberFormatException e) {
			System.out.println("⚠️ Entrée non valide.");
		}
		return null;
	}

	/**
	 * Processes a single row from the Excel file.
	 * <p>
	 * For now, it simply displays the raw content of the row to validate
	 * the mapping between Excel columns and accounting fields.
	 * </p>
	 *
	 * @param row     the list of strings representing the cells of the current row
	 * @param scanner the active scanner for user interaction
	 * @since 0.4.0
	 */
	private void processSingleExcelRow(List<String> row, Scanner scanner) {
		// 1. Extraction et Nettoyage RADICAL des données
		// On enlève les retours à la ligne (\n, \r) pour ne pas casser le tableau console
		String dateVal    = row.get(0).trim();
		String dateBanque = row.get(1).trim();

		// Nettoyage du libellé : on remplace les sauts de ligne par des espaces
		String libelle    = row.get(2).replace("\n", " ").replace("\r", " ").trim();

		// Nettoyage des montants
		String debit      = row.get(3).replace("\u00a0", "").trim();
		String credit     = row.get(4).replace("\u00a0", "").trim();

		// 2. Formatage du tableau
		String headerFormat = "| %-12s | %-12s | %-30s | %-10s | %-10s |";
		String lineFormat   = "| %-12s | %-12s | %-30s | %-10s | %-10s |";
		String separator    = "+--------------+--------------+--------------------------------+------------+------------+";

		// Tronquer le libellé s'il est trop long (30 char max)
		String displayLibelle = libelle.length() > 30 ? libelle.substring(0, 27) + "..." : libelle;

		// 3. Affichage
		System.out.println("\n" + separator);
		System.out.println(String.format(headerFormat, "DATE", "DATE V.", "LIBELLE", "DEBIT", "CREDIT"));
		System.out.println(separator);
		System.out.println(String.format(lineFormat, dateVal, dateBanque, displayLibelle, debit, credit));
		System.out.println(separator);

		System.out.print("👉 [Entrée] Continuer | [Q] Quitter : ");
		String input = scanner.nextLine();
		if ("q".equalsIgnoreCase(input)) {
			throw new RuntimeException("Importation interrompue.");
		}
	}

	/**
	 * Parses a string representing a numeric value and converts it into a double.
	 * It replaces non-breaking spaces and commas, trims the input, and handles
	 * invalid or null input gracefully by returning 0.0.
	 *
	 * @param value the string to be parsed as a numeric value
	 * @return the parsed double value; returns 0.0 if the input is null, empty, or invalid
	 *
	 * @since 0.4
	 */
	private double parseAmount(String value) {
		if (value == null || value.trim().isEmpty()) return 0.0;
		try {
			return Double.parseDouble(value.replace("\u00a0", "").replace(",", ".").trim());
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
}