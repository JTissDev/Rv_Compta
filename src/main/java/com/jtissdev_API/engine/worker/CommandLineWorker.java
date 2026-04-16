package com.jtissdev_API.engine.worker;

import com.jtissdev_API.engine.loader.*;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import com.jtissdev_API.features.compta.dto.JournalDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
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
 * @version 1.0.0
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

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	public CommandLineWorker(PcgDataLoader pcgLoader,
	                         TiersDataLoader tiersLoader,
	                         DetailsDataLoader detailsLoader,
	                         JournalLoader journalLoader) {
		this.pcgLoader = pcgLoader;
		this.tiersLoader = tiersLoader;
		this.detailsLoader = detailsLoader;
		this.journalLoader = journalLoader;
	}

	// =========================================================
	// == METHODS                                             ==
	// =========================================================

	/**
	 * Starts the operational import workflow.
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
					processExcelImport(scanner);
					break;
				case "2":
					processManualEntry(scanner);
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
			System.out.println(String.format("[%d] %s - %s",
					i,
					op.getDateOperation(),
					op.getDescriptif()));

			// Affichage rapide des mouvements pour vérifier l'équilibre
			if (op.getMovements() != null) {
				op.getMovements().forEach(m ->
						                          System.out.println(m.toString())
				);
			}
		}
	}

	/**
	 * Handles the Excel file selection and processing.
	 * @param scanner to get user input
	 * @since 0.4.0
	 */
	private void processExcelImport(Scanner scanner) {
		File selectedFile = selectExcelFile(scanner);
		if (selectedFile != null) {
			System.out.println("> Lancement de l'import : " + selectedFile.getName());
			// TODO: Appeler le futur ExcelReader logic ici
		}
	}

	/**
	 * Handles manual entry of a new operation.
	 * @param scanner to get user input
	 * @since 0.4.0
	 */
	private void processManualEntry(Scanner scanner) {
		System.out.println("\n--- [SAISIE MANUELLE] ---");
		// TODO: Développer la logique de saisie assistée (Tiers -> Type -> Sous-Type)
		System.out.println("Fonctionnalité en cours de développement...");
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
}