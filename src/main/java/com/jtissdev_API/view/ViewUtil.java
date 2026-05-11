package com.jtissdev_API.view;

/**
 * The ViewUtil class is a utility class that provides common methods and constants for view-related operations in the application.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class ViewUtil {

	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
	public static final String TABLE_FORMAT = "  | %-10s | %-15s | %-20s | %10s | %10s |%n";
	public static final String TABLE_LINE = "--+------------+-----------------+----------------------+------------+------------+";
	public static final String MAIN_SEPARATOR = "==========================================";
	public static final String SEPARATOR = "----------------------------------------";
	public static final String SEPARATOR_LINE = "****************************************";

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

}
