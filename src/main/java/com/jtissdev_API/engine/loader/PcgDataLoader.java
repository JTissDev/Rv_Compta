package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.core.io.ClassPathResource;
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
		try (InputStream is = new ClassPathResource("data/" + fileName).getInputStream();
		     JsonReader reader = Json.createReader(is)) {

			JsonObject jsonObject = reader.readObject();
			return mapToDto(jsonObject);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load PCG resource: " + fileName, e);
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
}