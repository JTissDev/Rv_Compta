package com.jtissdev.engine.loader;

import com.jtissdev.features.pcp.dto.AnalyticDetail;
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
 * {@link AnalyticDetail} DTO. It looks for files in the root "data" folder.
 * </p>
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since v0.1
 */
@Component
public class DetailsDataLoader {

	/**
	 * Reads a JSON file from the filesystem and converts it into a list of AnalyticDetail.
	 *
	 * @param fileName The name of the file located in the /data folder at project root.
	 * @return A list of initialized {@link AnalyticDetail}.
	 * @throws RuntimeException if the file cannot be found or parsed.
	 * @since 0.1
	 */
	public List<AnalyticDetail> loadDetailsFromJson(String fileName) {
		List<AnalyticDetail> detailsList = new ArrayList<>();
		FileSystemResource resource = new FileSystemResource("data/" + fileName);

		if (!resource.exists()) {
			throw new RuntimeException("Failed to load Details resource: " + resource.getPath());
		}

		try (InputStream is = resource.getInputStream();
		     JsonReader reader = Json.createReader(is)) {

			JsonArray jsonArray = reader.readArray();

			for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
				// Using the complete constructor (1.0.2) defined in the DTO
				AnalyticDetail detail = new AnalyticDetail();

				// Mapping based on your Details.json structure
				detail.setCode(obj.getString("Code", ""));
				detail.setType(obj.getString("type", "MISC"));
				detail.setName(obj.getString("Nom", "Unknown"));

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