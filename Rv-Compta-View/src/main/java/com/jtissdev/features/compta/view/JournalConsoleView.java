package com.jtissdev.features.compta.view;

import com.jtissdev.features.compta.dto.JournalDTO;
import com.jtissdev.features.compta.dto.OperationDTO;
import com.jtissdev.view.ViewUtil;

/**
 * The JournalConsoleView class is responsible for displaying journal-related operations
 * in a console-based user interface. It provides methods to display a list of operations
 * with customizable formatting.
 *
 * @author jtiss
 * @version 1.2.0
 * @since 0.5
 */
public class JournalConsoleView {

	private final OperationConsolView operationView = new OperationConsolView();

	/**
	 * Displays a journal with its operations in the console.
	 * 1. Displays the journal header.
	 * 2. Displays the journal size.
	 * 3. Displays each operation in the journal.
	 * 4. Displays a message if the journal is empty.
	 *
	 * @param journal
	 * 		the JournalDTO object containing the journal information and its operations.
	 * 		If the journal has no operations, a message indicating that the journal is empty will be displayed.
	 * @author jtiss
	 * @since 0.5
	 * @see JournalDTO
	 * @see OperationDTO
	 * @see OperationConsolView
	 * @see #displayJournalHeader(JournalDTO)
	 * @see #displayJournalSize(int)
	 * @see #displayEmptyJournal()
	 */
	public void displayJournal(JournalDTO journal)
	{
		displayJournalHeader(journal);
		if (journal.getOperations().isEmpty()) {
			this.displayEmptyJournal();
		}
		else {
			this.displayJournalSize(journal.getOperations().size());
			journal.getOperations().forEach(operationView::displayOperation);
		}
	}


	public void displayUpdatingJournal(JournalDTO journal) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n ===  Mise à jour du Journal Comptable : ").append(journal.getName() != "null" ? journal.getName():" ").append(" ===\n");
		sb.append(journal.getJournalTypeCode() != null ? "Type de journal : " + journal.getJournalTypeCode() + "\n" : "");
		sb.append(ViewUtil.MAIN_SEPARATOR).append("\n");
		System.out.println(sb.toString());
		if (journal.getOperations().isEmpty()) {
			this.displayEmptyJournal();
		}
		else {
			java.util.stream.IntStream.range(0, journal.getOperations().size())
					.forEach(i -> operationView.displaySelectableOperation(journal.getOperations().get(i),i+1));
		}



	}


	/**
	 * Displays a list of operations in the console, allowing the user to select one operation.
	 * 1. Displays the number of operations in the list.
	 * 2. Displays each operation in the list, allowing the user to select one operation.
	 * @param journal
	 * 		the JournalDTO object containing the journal information and its operations.
	 * 		The number of operations in the journal will be displayed before listing the operations.
	 * 		Each operation will be displayed with an index number for selection.
	 * 		If the journal has no operations, a message indicating that the journal is empty will be displayed.
	 * @author jtiss
	 * @since 0.5
	 * @see JournalDTO
	 * @see OperationDTO
	 * @see OperationConsolView
	 */
	public void displayJournalForSelection(JournalDTO journal) {
		this.displayJournalHeader(journal);

			java.util.stream.IntStream.range(0, journal.getOperations().size())
					.forEach(i -> operationView.displaySelectableOperation(journal.getOperations().get(i),i+1));

	}

	/**
	 * Displays a list of operations in the journal using the console.
	 *
	 * @param journal
	 * 		the JournalDTO object containing the journal information and its operations.
	 * 		The journal's name, type, and date range will be displayed as a header
	 * 		before listing the operations. If the journal has no operations, a message indicating that the journal is empty will be displayed.
	 * @author jtiss
	 * @since 0.5
	 * @see JournalDTO
	 * @see OperationDTO
	 * @see OperationConsolView
	 * @see #displayJournalHeader(JournalDTO)
	 * @see #displayJournalSize(int)
	 * @see #displayEmptyJournal()
	 */
	public void displayJournalHeader(JournalDTO journal) {
		StringBuilder sb = new StringBuilder();
		String start = (journal.getStartDate() != null) ? journal.getStartDate().toString() : null;
		String end = (journal.getEndDate() != null) ? journal.getEndDate().toString() : java.time.LocalDate.now().toString();

		sb.append("\n ===  Affichage du Journal Comptable : ").append(journal.getName() != "null" ? journal.getName():" ").append(" ===\n");
		sb.append(journal.getJournalTypeCode() != null ? "Type de journal : " + journal.getJournalTypeCode() + "\n" : "");
		sb.append(ViewUtil.MAIN_SEPARATOR).append("\n");
		if (start == null) {
			sb.append(String.format("Analyse jusqu'au : %s\n", end));
		} else {
			sb.append(String.format("Analyse entre : %s et %s\n", start, end));
		}
		sb.append(ViewUtil.MAIN_SEPARATOR).append("\n");

		System.out.println(sb.toString());
	}

	/**
	 * Displays the number of operations in the journal.
	 * 1. Constructs a string with the number of operations.
	 * 2. Prints the string to the console.
	 *
	 * @param size
	 * 		the number of operations in the journal to be displayed.
	 * @author jtiss
	 * @since 0.5
	 */
	public void displayJournalSize(int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("Nombre d'opérations trouvées : ").append(size).append("\n");
		System.out.println(sb.toString());
	}

	/**
	 * Displays a message indicating that the journal is empty.
	 * 1. Constructs a string with the message.
	 * 2. Prints the string to the console.
	 * @author jtiss
	 * @since 0.5
	 */
	public void displayEmptyJournal(){
		StringBuilder sb = new StringBuilder();
		sb.append("Aucune operation trouvée dans le journal.\n");
		System.out.println(sb.toString());
	}


}
