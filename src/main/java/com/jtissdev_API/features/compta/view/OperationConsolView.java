package com.jtissdev_API.features.compta.view;

import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.view.ViewUtil;

/**
 * The OperationConsolView class is responsible for displaying the details of an operation in a console-based user interface.
 * It provides a method to display the information of an OperationDTO object, including its movements and balance status.
 *
 * @author jtiss
 * @version 1.1.0
 * @since 0.6
 */
public class OperationConsolView {

	private final MovmentConsolView movementView = new MovmentConsolView();

	/**
	 * Returns a formatted status string based on whether the provided balance status is true or false.
	 * The string is color-coded, using green for a balanced status and red for an unbalanced status.
	 *
	 * @param isBalanced
	 * 		a boolean indicating whether the balance is correct (true) or not (false).
	 * @return a color-coded string representing the balance status.
	 *
	 * @since 0.6
	 */
	private String getFormattedStatus(boolean isBalanced) {
		if (isBalanced) {
			return ViewUtil.GREEN + "Balance OK" + ViewUtil.RESET;
		}
		return ViewUtil.RED + "Balance KO (Déséquilibrée)" + ViewUtil.RESET;
	}

	/**
	 * Displays the details of an OperationDTO object, including its movements and balance status.
	 * The movements are displayed in a table format.
	 * The balance status is color-coded, using green for a balanced status and red for an unbalanced status.
	 *
	 * @param operation
	 * 		the OperationDTO object containing the details of the operation to be displayed.
	 * @author jtiss
	 * @since 0.6
	 * @see OperationDTO
	 */
	public void displayOperation(OperationDTO operation) {
		StringBuilder sb = new StringBuilder();
		sb.append(ViewUtil.SEPARATOR).append("\n");
		sb.append("Affichage de l'operation : ").append(operation.getLibelle()).append("\n");
		sb.append("Réalisé le : ").append(operation.getDateOperation()).append("\n");
		if (operation.getDateComptable() != null) {
			sb.append("Comptablilisé le : ").append(operation.getDateComptable()).append("\n");
		}
		sb.append(ViewUtil.SEPARATOR_LINE).append("\n");
		sb.append("Detail de l'operation : \n");
		sb.append("Operation [id=").append(operation.getId() != null ? operation.getId() : "").append("\n")
				.append(", referenceDocument = ").append(operation.getReferenceDocument() != null ? operation.getReferenceDocument() : "undefined").append("\n")
				.append(", descriptif = ").append(operation.getDescriptif() != null ? operation.getDescriptif() : "undefined").append("\n")
				.append(", statutCode = ").append(operation.getStatutCode() != null ? operation.getStatutCode() : "undefined").append("\n")
				.append(", movements : ").append("\n");

		System.out.println(sb.toString());

		if (operation.getMovements() != null) {
			displayMovments(operation);
		} else {System.out.println("No movements found");}

		System.out.println(getFormattedStatus(operation.isBalance()));

	}

	/**
	 * Displays a table header with the specified format.
	 * The table header is formatted with the specified format string.
	 * The table header is printed to the console.
	 * @author jtiss
	 * @since 0.6
	 */
	private void displayTableHeader(){
		System.out.println(ViewUtil.TABLE_LINE);
		System.out.format(ViewUtil.TABLE_FORMAT, "COMPTE", "TIERS", "DESCRIPTION", "DÉBIT", "CRÉDIT");
		System.out.println(ViewUtil.TABLE_LINE);

	}

	/**
	 * Displays the movements of an operation in a table format.
	 * The movements are displayed in a table format.
	 *
	 * @param operation
	 * 		the OperationDTO object containing the movements to be displayed.
	 * @author jtiss
	 * @since 0.6
	 */
	private void displayMovments(OperationDTO operation) {
		this.displayTableHeader();
		operation.getMovements().forEach(movementView::displayInlineMovment);
		System.out.println(ViewUtil.TABLE_LINE);
	}

	/**
	 * Displays the details of an OperationDTO object, including its movements and balance status.
	 * The movements are displayed in a table format.
	 * The balance status is color-coded, using green for a balanced status and red for an unbalanced status.
	 * 1. Displays the index of the operation.
	 * 2. Displays the ID of the operation.
	 * 3. Displays the date of the operation.
	 * 4. Displays the description of the operation.
	 * 5. Displays the balance status of the operation.
	 * 6. Displays the movements of the operation.
	 * 7. Prints a newline character to separate the operation from the next one.
	 * @param operation
	 * 		the OperationDTO object containing the details of the operation to be displayed.
	 * @param i	the index of the operation in the list of operations, used for display purposes.
	 * @author jtiss
	 * @since 0.6
	 * @see OperationDTO
	 *
	 */
	public void displayResumOperation(OperationDTO operation, int i) {
		String indexColor = operation.isBalance() ? "" : ViewUtil.RED;

		StringBuilder sb = new StringBuilder();

		sb.append(indexColor).append("[").append(i).append("]").append(ViewUtil.RESET).append(" ");
		sb.append("ID: ").append(operation.getId() != null ? operation.getId() : "NEW").append(" | ");
		sb.append(operation.getDateOperation()).append(" | ");
		sb.append(operation.getDescriptif() != null ? operation.getDescriptif() : "No desc").append(" | ");
		sb.append(getFormattedStatus(operation.isBalance())).append("\n");

		System.out.print(sb.toString());

		if (operation.getMovements() != null) {
			operation.getMovements().forEach(movementView::displayResumMovement);
		}
	}

	/**
	 * Displays the details of an OperationDTO object, including its movements and balance status.
	 * The movements are displayed in a table format.
	 * The balance status is color-coded, using green for a balanced status and red for an unbalanced status.
	 * 1. Displays the index of the operation.
	 * 2. Displays the date of the operation.
	 * 3. Displays the description of the operation.
	 * 4. Displays the balance status of the operation.
	 * 5. Prints a newline character to separate the operation from the next one.
	 *
	 * @param operationDTO
	 * 		the OperationDTO object containing the details of the operation to be displayed.
	 * @param i	the index of the operation in the list of operations, used for display purposes.
	 *
	 * @author  jtiss
	 * @since 0.6
	 */
	public void displaySelectableOperation(OperationDTO operationDTO, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(i).append("] ");
		sb.append(operationDTO.getDateOperation()).append(" | ");
		sb.append(operationDTO.getDescriptif() != null ? operationDTO.getDescriptif() : "No desc").append(" | ");
		sb.append(getFormattedStatus(operationDTO.isBalance())).append("\n");
		System.out.print(sb.toString());
	}
}
