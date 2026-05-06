package com.jtissdev_API.features.compta.view;

import com.jtissdev_API.features.compta.dto.OperationDTO;

import java.util.List;

/**
 * The JournalConsoleView class is responsible for displaying journal-related operations
 * in a console-based user interface. It provides methods to display a list of operations
 * with customizable formatting.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
public class JournalConsoleView {

	/**
	 * Displays a list of operations in the journal using the console.
	 *
	 * @param operations
	 * 		the list of OperationDTO objects to be displayed.
	 * 		If the list is empty, a message indicating no operations
	 * 		are available will be displayed.
	 */
	public void displayOperations(List<OperationDTO> operations) {
		System.out.println("\n--- [JOURNAL] DERNIÈRES OPÉRATIONS ---");
		System.out.println("Affichage de " + operations.size() + " opérations :");

		if (operations.isEmpty()) {
			System.out.println("Aucune opération à afficher.");
			return;
		}

		// Ici tu peux faire un affichage plus élégant (en tableau par exemple)
		for (OperationDTO op : operations) {
			formatOperationLine(op);
		}
	}

	/**
	 * Formats and displays an individual operation in the console.
	 *
	 * @param op
	 * 		the OperationDTO object to be formatted and displayed.
	 */
	private void formatOperationLine(OperationDTO op) {
		// Ta logique de formatage spécifique
		System.out.println(op.toString());
	}
}
