package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.PCP.dto.Tiers;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Technical loader dedicated to parsing Third Party data from JSON resources.
 * <p>
 * This class handles the mapping between the legacy JSON format (N_id, Nom_RaisonSociale)
 * and the domain-driven Tiers DTO.
 * </p>
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since v0.1
 */
@Component
public class TiersDataLoader {

	/**
	 * Reads a JSON file and converts it into a list of Tiers objects.
	 *
	 * @param fileName The name of the file located in src/main/resources/data/
	 * @return A list of initialized {@link Tiers}
	 * @throws RuntimeException if the file cannot be read or parsed
	 * @since 0.1
	 */
	public List<Tiers> loadTiersFromJson(String fileName) {
		List<Tiers> tiersList = new ArrayList<>();

		try (InputStream is = new FileSystemResource("data/" + fileName).getInputStream();
		     JsonReader reader = Json.createReader(is)) {

			JsonArray jsonArray = reader.readArray();

			for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
				// We use the complete constructor or setters via the default one
				Tiers tiers = new Tiers();

				// Mapping: N_id (int) -> id (Long)
				tiers.setId((Integer) obj.getInt("N_id"));

				// Mapping: Nom_RaisonSociale -> name
				tiers.setName(obj.getString("Nom_RaisonSociale", "Unknown"));

				// Logic: Est_Professionnel (int) -> thirdPartyType (String)
				int isPro = obj.getInt("Est_Professionnel", 0);
				tiers.setThirdPartyType(isPro == 1 ? "PROFESSIONAL" : "PERSONAL");

				// Description handling (Checking for nulls in JSON)
				if (obj.containsKey("Description") && !obj.isNull("Description")) {
					tiers.setDescription(obj.getString("Description"));
				} else {
					tiers.setDescription("");
				}

				tiersList.add(tiers);
			}

		} catch (Exception e) {
			// Error message aligned with the project's testing standards
			throw new RuntimeException("Failed to load Tiers resource: " + fileName, e);
		}

		return tiersList;
	}
}