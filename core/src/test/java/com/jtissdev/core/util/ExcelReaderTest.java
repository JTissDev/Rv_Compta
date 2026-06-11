package com.jtissdev.core.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelReaderTest {

	/* @Test
	public void testReadExcelValidFile() throws Exception {
		ExcelReader excelReader = new ExcelReader();
		File validFile = new File("validTestFile.xlsx");

		// Manually creating a valid Excel file for testing
		try (FileWriter fileWriter = new FileWriter(validFile)) {
			// Add mock data
		}

		List<List<String>> result = excelReader.readExcel(validFile);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		validFile.delete();
	} */

	@Test
	public void testReadExcelFileNotFound() {
		ExcelReader excelReader = new ExcelReader();
		File nonExistentFile = new File("nonexistent.xlsx");

		assertThrows(Exception.class, () -> excelReader.readExcel(nonExistentFile));
	}

	@Test
	public void testReadExcelEmptyFile() throws IOException {
		ExcelReader excelReader = new ExcelReader();
		File emptyFile = new File("empty.xlsx");

		// Create an empty file for testing
		emptyFile.createNewFile();

		try {
			List<List<String>> result = excelReader.readExcel(emptyFile);
			assertNotNull(result);
			assertTrue(result.isEmpty());
		} catch (Exception e) {
		} finally {
			emptyFile.delete();
		}
	}
}
