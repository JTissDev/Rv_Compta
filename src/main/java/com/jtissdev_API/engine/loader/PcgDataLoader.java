package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import com.jtissdev_API.features.PCG.dto.AccountingTypeDetails;
import com.jtissdev_API.features.PCG.dto.SubAccountingType;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Engine component responsible for loading and parsing Accounting Plan data
 * from external physical resources.
 * @author JtissDev
 * @version 1.0.0
 * @since 0.1
 */
@Component
public class PcgDataLoader {

	/**
	 * Loads the General Chart of Accounts (PCG) from a JSON file located
	 * in the classpath resources.
	 * @param fileName The name of the target file (e.g., "pcg.json")
	 * @return A populated PcgCoreDTO containing the hierarchical tree
	 * @throws RuntimeException If the file is missing or the JSON structure is invalid
	 *
	 * @since 0.1
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
	 * @since 0.1
	 * @version 1.0.0
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
	 * @since 0.1
	 * @version 1.1.1
	 * @param json The root JsonArray containing accounting classes.
	 * @return A fully populated PcgCoreDTO object.
	 */
	private PcgCoreDTO mapToDto(JsonArray json) {
		PcgCoreDTO core = new PcgCoreDTO();

		for (JsonObject typeJson : json.getValuesAs(JsonObject.class)) {
			System.out.println("=============================================");

			AccountingType typeObj = new AccountingType();
			typeObj.setName(typeJson.getString("type", "Unnamed"));
			typeObj.setAccountCode(typeJson.getInt("parent_code"));
			typeObj.setDescription(typeJson.getString("parent_desc", ""));

			// Handling Long ID from JSON
			if (typeJson.containsKey("id") && !typeJson.isNull("id")) {
				// Using longValue() or a cast depending on your JSON library version
				typeObj.setId((Integer) typeJson.getJsonNumber("id").intValue());
			}

			JsonArray subTypesJson = typeJson.getJsonArray("subTypes");
			for (JsonObject subJson : subTypesJson.getValuesAs(JsonObject.class)) {

				SubAccountingType subObj = new SubAccountingType();
				subObj.setName(subJson.getString("name", ""));
				subObj.setAccountingCode(subJson.getInt("subType_Num"));
				subObj.setDescription(subJson.getString("subType_desc", ""));

				subObj.setParentCodeComptable(typeObj.getFullCodeComptable());

				if (subJson.containsKey("id") && !subJson.isNull("id")) {
					subObj.setId(subJson.getJsonNumber("id").longValue());
				}

				JsonArray detailsJson = subJson.getJsonArray("details");
				for (JsonObject detJson : detailsJson.getValuesAs(JsonObject.class)) {

					AccountingTypeDetails detObj = new AccountingTypeDetails();
					detObj.setName(detJson.getString("name", ""));
					detObj.setDescription(detJson.getString("desc_detail", ""));
					detObj.setAccountingCode(detJson.getString("no_detail"));

					detObj.setParentAccountingCode(subObj.getFullCode());

					if (detJson.containsKey("id") && !detJson.isNull("id")) {
						detObj.setId(detJson.getJsonNumber("id").intValue());
					}

					subObj.addDetails(detObj);
				}
				typeObj.addSubType(subObj);
			}

			//System.out.println(typeObj);
			core.addAccountingClass(typeObj);
		}

		return core;
	}
}