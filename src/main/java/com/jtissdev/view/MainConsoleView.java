package com.jtissdev.view;

import com.jtissdev.logging.banner.AppBanner;

import java.util.List;

/**
 * Represents a MainConsoleView DTO.
 *
 * @author jtiss
 * @version 1.1.0
 * @since 0.5
 */

public class MainConsoleView {

	/**
	 * Displays the application header with the application name and version.
	 * This method uses the AppBanner class to display a stylized banner in the console.
	 * It serves as the initial welcome message when the application starts.
	 * @see AppBanner
	 * @author jtiss
	 * @since 0.5
	 */
	public void displayHeader() {
		AppBanner.display();
		//System.out.println("\n=== MODE OPÉRATIONNEL : ENGINE " + version + " ===");
	}


	/**
	 * Displays the main menu options to the user.
	 * @author jtiss
	 * @since 0.5
	 */
	public void displayMainMenu() {
		System.out.println("\n--- [MENU PRINCIPAL] ---");
		System.out.println("[1] Charger un fichier Excel (Importation)");
		System.out.println("[2] Modifier le journal actuel (Ajouter ou modifier une opération)"); //n
		System.out.println("[0] Quitter");
		System.out.print("\nVotre choix : ");
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

}
