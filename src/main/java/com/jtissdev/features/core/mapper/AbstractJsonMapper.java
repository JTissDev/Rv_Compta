package com.jtissdev.features.core.mapper;


import com.jtissdev.core.exception.JsonMappingException;
import jakarta.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Represents a AbstractJsonMapper DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public abstract class AbstractJsonMapper {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Extracts a JSON array from the given input stream. If the JSON structure is
	 * an object, it attempts to extract the array from a specific key defined
	 * within the object.
	 *
	 * @param is
	 * 		The input stream containing the JSON data.
	 * @return A {@code JsonArray} representing the parsed JSON array.
	 *
	 * @throws RuntimeException
	 * 		If the JSON structure is neither an array nor an
	 * 		object, or if parsing fails.
	 * @since 0.6
	 */
	protected JsonArray extractJsonArray(InputStream is) {
		try (JsonReader reader = Json.createReader(is)) {
			JsonStructure struct = reader.read();

			if (struct.getValueType() == JsonValue.ValueType.ARRAY) {
				return struct.asJsonArray();
			}

			if (struct.getValueType() == JsonValue.ValueType.OBJECT) {
				return unwrapArrayFromObject(struct.asJsonObject());
			}
			throw new JsonMappingException("Unsupported JSON structure: expected Array or Object.");
		} catch (JsonException e) { // Capture spécifique à Jakarta
			logger.error("JSON parsing error: {}", e.getMessage());
			throw new JsonMappingException("Failed to parse JSON stream", e);
		} catch (Exception e) {
			throw new JsonMappingException("Unexpected error during JSON mapping", e);
		}
	}

	/**
	 * Extracts a {@link JsonArray} from the given {@link JsonObject} under the 'data' key.
	 * If the provided object does not contain a 'data' key or the value of 'data' is not a JSON array,
	 * an exception is thrown. Logs warnings and errors during the operation.
	 *
	 * @param jsonObject
	 * 		The {@link JsonObject} expected to contain a 'data' key with an array value.
	 * @return A {@link JsonArray} extracted from the 'data' key of the provided object.
	 *
	 * @throws RuntimeException
	 * 		If the 'data' key is missing or does not contain a JSON array.
	 * @since 0.6
	 */
	private JsonArray unwrapArrayFromObject(JsonObject jsonObject) {
		logger.warn("Expected a JSON array but found an object. Attempting to extract array from 'data' key.");

		if (jsonObject.containsKey("data") && jsonObject.get("data").getValueType() == JsonValue.ValueType.ARRAY) {
			logger.info("Extracted JSON array from object under 'data' key.");
			return jsonObject.getJsonArray("data");
		}

		logger.error("The JSON object does not contain a 'data' key with an array value.");
		throw new RuntimeException("Invalid JSON structure: expected an object containing a 'data' array.");
	}
}
