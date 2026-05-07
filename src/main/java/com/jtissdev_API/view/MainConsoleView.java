package com.jtissdev_API.view;

import com.jtissdev.logging.banner.AppBanner;
import com.jtissdev_API.features.compta.dto.OperationDTO;

import java.util.List;

/**
 * Represents a MainConsoleView DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */

public class MainConsoleView {

	public void displayHeader() {
		AppBanner.display();
		//System.out.println("\n=== MODE OPÉRATIONNEL : ENGINE " + version + " ===");
	}

	public void displayMainMenu() {
		System.out.println("\n--- [MENU PRINCIPAL] ---");
		System.out.println("[1] Charger un fichier Excel (Importation)");
		System.out.println("[2] Ajouter une opération manuellement");
		System.out.println("[3] Modifier une opération existante"); // <-- Nouvelle option
		System.out.println("[0] Quitter");
		System.out.print("\nVotre choix : ");
	}

	public void displayJournal(List<OperationDTO> operations, int total) {
		System.out.println("\n--- [JOURNAL] DERNIÈRES OPÉRATIONS ---");
		if (operations.isEmpty()) {
			System.out.println(" > Le journal est actuellement vide.");
			return;
		}
		System.out.println("Affichage de " + operations.size() + " sur " + total + " opérations :");
		operations.forEach(op -> System.out.println(op.toString()));
	}

	public void displayExcelRow(List<String> row) {
		// Transfert de la logique de tableau complexe de CommandLineWorker
		String libelle = row.get(2).replace("\n", " ").trim();
		String displayLibelle = libelle.length() > 30 ? libelle.substring(0, 27) + "..." : libelle;

		String separator = "+--------------+--------------+--------------------------------+------------+------------+";
		System.out.println("\n" + separator);
		System.out.println(String.format("| %-12s | %-12s | %-30s | %-10s | %-10s |", "DATE", "DATE V.", "LIBELLE", "DEBIT", "CREDIT"));
		System.out.println(separator);
		System.out.println(String.format("| %-12s | %-12s | %-30s | %-10s | %-10s |", row.get(0), row.get(1), displayLibelle, row.get(3), row.get(4)));
		System.out.println(separator);
		System.out.print("👉 [Entrée] Continuer | [Q] Quitter : ");
	}

	public void displayMessage(String msg) {
		System.out.println(msg);
	}

	public void displayError(String error) {
		System.err.println("❌ " + error);
	}
}
