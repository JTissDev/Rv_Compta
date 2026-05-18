package com.jtissdev_API.utils;

import jakarta.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Data loader for testing purposes, allowing to read JSON files from
 * resources or an external data folder, and normalizing the output into a JsonObject.
 *
 * @author J.Tiss
 * @version 1.2.1
 * @email jtissdev@gmail.com
 * @since 1.0.0
 */
public class TestDataLoader {

	private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);
	private static final String EXTERNAL_DATA_FOLDER = "data";

	/**
	 * Loads a JSON file from the classpath resources.
	 *
	 * @param fileName
	 * 		The name or relative path of the file (e.g., "data/journal-test.json")
	 * @return A unified JsonObject (the raw object, or the array wrapped with its metadata)
	 */
	public static JsonObject loadFromResources(String fileName) {
		InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(fileName);
		if (is == null) {
			logger.debug(" Looking for file '{}'in: '{}'",
					fileName,
					TestDataLoader.class.getClassLoader().getResource(".").getPath());
			throw new RuntimeException("[Resources] File not found: " + fileName);
		}
		return processStream(is, fileName);
	}

	/**
	 * Loads a JSON file from the 'data' folder at the root of the file system.
	 *
	 * @param fileName
	 * 		The name of the file in the external folder (e.g., "work_file.json")
	 * @return A unified JsonObject (the raw object, or the array wrapped with its metadata)
	 */
	public static JsonObject loadFromExternalData(String fileName) {
		Path path = Paths.get(EXTERNAL_DATA_FOLDER, fileName);

		if (!Files.exists(path)) {
			throw new RuntimeException("[External] File not found: " + path.toAbsolutePath());
		}

		try {
			InputStream is = Files.newInputStream(path);
			return processStream(is, path.toString());
		} catch (Exception e) {
			throw new RuntimeException("Error reading FileSystem: " + path.toAbsolutePath(), e);
		}
	}

	/**
	 * Centralized business logic to process the input stream.
	 * Reads the input, determines the JSON structure type, and formats the output.
	 *
	 * @param is
	 * 		The input stream opened by the public methods
	 * @param sourceInfo
	 * 		Information about the source for error reporting
	 * @return The formatted JsonObject
	 */
	private static JsonObject processStream(InputStream is, String sourceInfo) {
		try (JsonReader reader = Json.createReader(is)) {
			JsonStructure structure = reader.read();

			if (structure.getValueType() == JsonValue.ValueType.OBJECT) {
				return structure.asJsonObject();

			} else if (structure.getValueType() == JsonValue.ValueType.ARRAY) {
				JsonArray array = structure.asJsonArray();
				return Json.createObjectBuilder()
						       .add("size", array.size())
						       .add("data", array)
						       .build();
			} else {
				throw new RuntimeException("The file contains neither a valid JSON object nor a root array.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error during JSON processing for: " + sourceInfo, e);
		}
	}
}