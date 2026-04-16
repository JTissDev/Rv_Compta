package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.PCG.dto.Sub_Type_Comptable;
import com.jtissdev_API.features.PCG.dto.Type_Comptable;
import com.jtissdev_API.features.PCG.dto.Type_Comptable_Details;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Engine component responsible for loading and parsing Accounting Plan data
 * from external physical resources.
 * @author JtissDev
 * @version 1.0
 */
@Component
public class PcgDataLoader {

	/**
	 * Loads the General Chart of Accounts (PCG) from a JSON file located
	 * in the classpath resources.
	 * @param fileName The name of the target file (e.g., "pcg.json")
	 * @return A populated PcgCoreDTO containing the hierarchical tree
	 * @throws RuntimeException If the file is missing or the JSON structure is invalid
	 */
	public PcgCoreDTO loadFromJson(String fileName) {
		// On utilise FileSystemResource pour lire le fichier sur le disque
		// fileName doit être "data/PCG.json"
		Resource resource = new FileSystemResource(fileName);

		try (InputStream is = resource.getInputStream();
		     JsonReader reader = Json.createReader(is)) {

			JsonArray jsonArray = reader.readArray();
			return mapToDto(jsonArray);

		} catch (Exception e) {
			// On affiche le chemin absolu en cas d'erreur pour déboguer facilement
			String absolutePath = new java.io.File(fileName).getAbsolutePath();
			throw new RuntimeException("Failed to load PCG resource : " + absolutePath, e);
		}
	}

	/**
	 * Maps a Jakarta JsonObject to the internal DTO domain model.
	 * @param json The source JsonObject to parse
	 * @return A mapped PcgCoreDTO instance
	 */
	private PcgCoreDTO mapToDto(JsonObject json) {
		PcgCoreDTO core = new PcgCoreDTO();
		// TODO: Implementation depends on the JSON schema provided
		return core;
	}


	/**
	 * Maps a JSON array to a structured PcgCoreDTO object.
	 * <p>
	 * This method iterates through a three-level hierarchy (Type, SubType, and Details).
	 * It ensures the reconstruction of parent-child relationships via accounting codes
	 * and handles optional database IDs (as Long) if present in the source JSON.
	 * </p>
	 *
	 * @author J.Tiss - jtissdev@gmail.com
	 * @since 1.0.0
	 * @version 1.1.1
	 * @param json The root JsonArray containing accounting classes.
	 * @return A fully populated PcgCoreDTO object.
	 */
	private PcgCoreDTO mapToDto(JsonArray json) {
		PcgCoreDTO core = new PcgCoreDTO();

		for (JsonObject typeJson : json.getValuesAs(JsonObject.class)) {
			System.out.println("=============================================");

			Type_Comptable typeObj = new Type_Comptable();
			typeObj.setName(typeJson.getString("type", "Unnamed"));
			typeObj.setCodeComptable(typeJson.getInt("parent_code"));
			typeObj.setDescription(typeJson.getString("parent_desc", ""));

			// Handling Long ID from JSON
			if (typeJson.containsKey("id") && !typeJson.isNull("id")) {
				// Using longValue() or a cast depending on your JSON library version
				typeObj.setId((long) typeJson.getJsonNumber("id").longValue());
			}

			JsonArray subTypesJson = typeJson.getJsonArray("subTypes");
			for (JsonObject subJson : subTypesJson.getValuesAs(JsonObject.class)) {

				Sub_Type_Comptable subObj = new Sub_Type_Comptable();
				subObj.setName(subJson.getString("name", ""));
				subObj.setCodeComptable(subJson.getInt("subType_Num"));
				subObj.setDescription(subJson.getString("subType_desc", ""));

				subObj.setParentCodeComptable(typeObj.getFullCodeComptable());

				if (subJson.containsKey("id") && !subJson.isNull("id")) {
					subObj.setId(subJson.getJsonNumber("id").longValue());
				}

				JsonArray detailsJson = subJson.getJsonArray("details");
				for (JsonObject detJson : detailsJson.getValuesAs(JsonObject.class)) {

					Type_Comptable_Details detObj = new Type_Comptable_Details();
					detObj.setName(detJson.getString("name", ""));
					detObj.setDescription(detJson.getString("desc_detail", ""));
					detObj.setCodeComptable(detJson.getInt("no_detail"));

					detObj.setParentCodeComptable(subObj.getFullCode());

					if (detJson.containsKey("id") && !detJson.isNull("id")) {
						detObj.setId(detJson.getJsonNumber("id").intValue());
					}

					subObj.addDetails(detObj);
				}
				typeObj.addSubType(subObj);
			}

			System.out.println(typeObj);
			core.addAccountingClass(typeObj);
		}

		return core;
	}
}