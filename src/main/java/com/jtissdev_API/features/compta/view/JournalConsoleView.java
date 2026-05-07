package com.jtissdev_API.features.compta.view;

import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;

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
	public void displayJournalHeader(JournalDTO journal) {
		StringBuilder sb = new StringBuilder();
		String start = (journal.getStartDate() != null) ? journal.getStartDate().toString() : null;
		String end = (journal.getEndDate() != null) ? journal.getEndDate().toString() : java.time.LocalDate.now().toString();

		sb.append("\n ===  Affichage du Journal Comptable : ").append(journal.getName() != "null" ? journal.getName():" ").append(" ===\n");
		sb.append(journal.getJournalTypeCode() != null ? "Type de journal : " + journal.getJournalTypeCode() + "\n" : "");
		sb.append("======================================================\n");
		if (start == null) {
			sb.append(String.format("Analyse jusqu'au : %s\n", end));
		} else {
			sb.append(String.format("Analyse entre : %s et %s\n", start, end));
		}
		sb.append("======================================================\n");

		System.out.println(sb.toString());
	}

	public void displayJournalSize(int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("Nombre d'opérations trouvées : ").append(size).append("\n");
		System.out.println(sb.toString());
	}

	public void displayEmptyJournal(){
		StringBuilder sb = new StringBuilder();
		sb.append("Aucune operation trouvée dans le journal.\n");
		System.out.println(sb.toString());
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
