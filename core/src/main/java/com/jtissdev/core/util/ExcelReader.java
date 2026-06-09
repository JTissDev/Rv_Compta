package com.jtissdev.core.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for reading Excel files (.xlsx).
 * <p>
 * Provides methods to extract data from sheets into generic structures
 * suitable for accounting processing.
 * </p>
 *
 * @author jtiss
 * @since 0.4
 * @version 1.0.0
 */
@Component
public class ExcelReader {

	// =========================================================
	// == METHODS                                             ==
	// =========================================================

	/**
	 * Reads the first sheet of an Excel file and returns rows as a list of string lists.
	 *
	 * @param file the Excel file to read
	 * @return a list containing each row as a list of cell values
	 * @throws Exception if file is missing or format is invalid
	 * @since 0.4
	 */
	public List<List<String>> readExcel(File file) throws Exception {
		List<List<String>> data = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(file);
		     Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			// On force la Locale FR pour les dates et les nombres
			DataFormatter formatter = new DataFormatter(Locale.FRANCE);

			for (Row row : sheet) {
				List<String> rowData = new ArrayList<>();
				// On boucle de 0 à 5 (tes colonnes) pour être SUR de ne pas sauter de cellule vide
				for (int i = 0; i < 5; i++) {
					Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					rowData.add(formatter.formatCellValue(cell));
				}
				data.add(rowData);
			}
		}
		return data;
	}
}