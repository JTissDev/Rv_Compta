package com.jtissdev.view;

/**
 * The ViewUtil class is a utility class that provides common methods and constants for view-related operations in the application.
 *
 * @author jtiss
 * @version 1.1.0
 * @since 0.6
 */
public class ViewUtil {

	private static final int TAILLE_LINE = 30;

	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
	public static final String CYAN = "\u001B[36m";
	public static final String YELLOW = "\u001B[33m";

	public static final String TABLE_FORMAT = "  | %-10s | %-15s | %-20s | %10s | %10s |%n";
	public static final String TABLE_LINE = "--+------------+-----------------+----------------------+------------+------------+";
	public static final String MAIN_SEPARATOR = "=".repeat(TAILLE_LINE);
	public static final String SEPARATOR = "-".repeat(TAILLE_LINE);
	public static final String SEPARATOR_LINE = "*".repeat(TAILLE_LINE);


	/**
	 * Displays a placeholder message indicating that the feature has not yet been implemented.
	 * This method is typically used as a placeholder in areas of the application
	 * where functionality is still under development.
	 *
	 * @since 0.6
	 * @author jtiss
	 */
	public static void displayNotImplemented(){
		System.out.println("⚠️  Fonctionnalité non encore implémentée.");
	}


	/**
	 * Displays a message to the console.
	 *
	 * @param msg the message to be displayed
	 *
	 * @since 0.6
	 * @author jtiss
	 */
	public static void displayMessage(String msg) {
		System.out.println(msg);
	}


	/**
	 * Displays an error message to the console.
	 * @param error the error message to be displayed
	 *
	 * @since 0.6
	 * @author jtiss
	 *
	 */
	public static void displayError(String error) {
		System.err.println("❌ " + error);
	}

	/**
	 * Waits for the user to press the Enter key.
	 * This method is typically used to pause the execution of the application until the user is ready to continue,
	 * allowing them to read messages or view output before proceeding.
	 * @since 0.6
	 * @author jtiss
	 */
	public static void waitForUser() {
		System.out.println("\n" + CYAN + "Press [ENTER] to continue..." + RESET);
		try {
			System.in.read(); // Attend une entrée clavier
		} catch (Exception e) {
			// Optionnel : logger l'erreur
		}
	}

}
