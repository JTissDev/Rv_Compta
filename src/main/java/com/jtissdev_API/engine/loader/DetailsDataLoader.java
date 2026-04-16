package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.PCP.dto.Details_Comptable;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Technical loader dedicated to parsing Level 4 accounting details from JSON resources.
 * <p>
 * This class handles the mapping between the Details JSON format and the
 * {@link Details_Comptable} DTO. It looks for files in the root "data" folder.
 * </p>
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since v0.1
 */
@Component
public class DetailsDataLoader {

	/**
	 * Reads a JSON file from the filesystem and converts it into a list of Details_Comptable.
	 *
	 * @param fileName The name of the file located in the /data folder at project root.
	 * @return A list of initialized {@link Details_Comptable}.
	 * @throws RuntimeException if the file cannot be found or parsed.
	 * @since 0.1
	 */
	public List<Details_Comptable> loadDetailsFromJson(String fileName) {
		List<Details_Comptable> detailsList = new ArrayList<>();
		FileSystemResource resource = new FileSystemResource("data/" + fileName);

		if (!resource.exists()) {
			throw new RuntimeException("Failed to load Details resource: " + resource.getPath());
		}

		try (InputStream is = resource.getInputStream();
		     JsonReader reader = Json.createReader(is)) {

			JsonArray jsonArray = reader.readArray();

			for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
				// Using the complete constructor (1.0.2) defined in the DTO
				Details_Comptable detail = new Details_Comptable();

				// Mapping based on your Details.json structure
				detail.setCode(obj.getString("Code", ""));
				detail.setType(obj.getString("type", "MISC"));
				detail.setNom(obj.getString("Nom", "Unknown"));

				// Handling optional description and potential nulls
				if (obj.containsKey("description") && !obj.isNull("description")) {
					detail.setDescription(obj.getString("description"));
				} else {
					detail.setDescription("");
				}

				detailsList.add(detail);
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to parse Details resource: " + fileName, e);
		}

		return detailsList;
	}
}