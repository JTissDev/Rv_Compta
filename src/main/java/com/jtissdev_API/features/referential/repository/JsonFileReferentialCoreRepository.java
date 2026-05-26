package com.jtissdev_API.features.referential.repository;

import com.jtissdev_API.features.referential.dto.ReferentialCoreDTO;
import jakarta.json.*;
import jakarta.json.stream.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * File-based implementation of the Referential Core repository handling multiple JSON files.
 * <p>
 * This component manages the dual-file orchestration for operation statuses and payment methods.
 * It features an automatic "Seed" fallback that copies default reference data from the application resources
 * to the external environment-specific storage path upon first boot.
 * </p>
 *
 * @author J.Tiss
 * @email jtissdev.gmail.com
 * @version 2.0.0
 * @since 0.6.0
 */
@Repository
public class JsonFileReferentialCoreRepository {

	private static final Logger log = LoggerFactory.getLogger(JsonFileReferentialCoreRepository.class);

	private final ResourceLoader resourceLoader;
	private final JsonWriterFactory writerFactory;

	private final File statusLiveFile;
	private final File paymentLiveFile;
	private final String statusSeedPath;
	private final String paymentSeedPath;

	/**
	 * Constructs the repository with dynamic multi-environment property injection.
	 *
	 * @param resourceLoader Spring's resource loader to access classpath seeds.
	 * @param storageDir The destination directory for live data mutations.
	 * @param seedFolder The source folder prefix inside the application bundle.
	 * @param statusFilename Name of the operation statuses file.
	 * @param paymentFilename Name of the payment methods file.
	 * @since 2.0.0
	 */
	public JsonFileReferentialCoreRepository(
			ResourceLoader resourceLoader,
			@Value("${app.persistence.storage-path}") String storageDir,
			@Value("${app.persistence.seed-path}") String seedFolder,
			@Value("${app.persistence.file-name.status}") String statusFilename,
			@Value("${app.persistence.file-name.paymentMethod}") String paymentFilename) {

		this.resourceLoader = resourceLoader;

		// Definition of live mutable targets
		this.statusLiveFile = new File(storageDir, statusFilename);
		this.paymentLiveFile = new File(storageDir, paymentFilename);

		// Definition of immutable bundle seed targets
		this.statusSeedPath = seedFolder + statusFilename;
		this.paymentSeedPath = seedFolder + paymentFilename;

		// Pretty printing configuration
		Map<String, Object> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		this.writerFactory = Json.createWriterFactory(config);

		log.info("[Persistence] Referential Repository bound to files:\n -> Status: {}\n -> Payments: {}",
				statusLiveFile.getAbsolutePath(), paymentLiveFile.getAbsolutePath());
	}

	/**
	 * Loads the split JSON structures, applies seed copies if missing, and aggregates them
	 * into a single unified {@link ReferentialCoreDTO}.
	 *
	 * @return an {@link Optional} containing the hydrated referential object, or {@link Optional#empty()} if recovery fails.
	 * @since 2.0.0
	 */
	public Optional<ReferentialCoreDTO> load() {
		try {
			// Trigger seed fallback mechanisms if live files do not exist yet
			checkAndCopySeed(statusLiveFile, statusSeedPath, "Operation Statuses");
			checkAndCopySeed(paymentLiveFile, paymentSeedPath, "Payment Methods");

			if (!statusLiveFile.exists() || !paymentLiveFile.exists()) {
				log.error("[Persistence] Critical reference files are missing even after seed execution.");
				return Optional.empty();
			}

			// Read native JSON Arrays from disk
			JsonArray statusArray = readArrayFromFile(statusLiveFile);
			JsonArray paymentArray = readArrayFromFile(paymentLiveFile);

			// Reconstruct the virtual structural object expected by ReferentialCoreDTO(JsonObject)
			JsonObject consolidatedJson = Json.createObjectBuilder()
					                              .add("operationStatuses", statusArray)
					                              .add("paymentMethods", paymentArray)
					                              .build();

			return Optional.of(new ReferentialCoreDTO(consolidatedJson));

		} catch (Exception e) {
			log.error("[Persistence] Critical failure while orchestrating core referential load loop", e);
			return Optional.empty();
		}
	}

	/**
	 * Splits the unified {@link ReferentialCoreDTO} aggregate back into its distinctive native arrays
	 * and persists them into their respective external file targets.
	 *
	 * @param referential the aggregate entity containing all core definitions.
	 * @throws IllegalArgumentException if the provided aggregate is null.
	 * @since 2.0.0
	 */
	public void save(ReferentialCoreDTO referential) {
		if (referential == null) {
			throw new IllegalArgumentException("Cannot save a null referential core object.");
		}

		ensureParentDirectoryExists(statusLiveFile);
		ensureParentDirectoryExists(paymentLiveFile);

		// Extract state through the official aggregate exporter
		JsonObject fullJson = referential.toJson();

		// Safely extract the inner arrays, default to empty arrays if null to preserve structural intent
		JsonArray statusArray = fullJson.getJsonArray("operationStatuses");
		if (statusArray == null) statusArray = Json.createArrayBuilder().build();

		JsonArray paymentArray = fullJson.getJsonArray("paymentMethods");
		if (paymentArray == null) paymentArray = Json.createArrayBuilder().build();

		// Concurrent writing routine
		writeArrayToFile(statusLiveFile, statusArray);
		writeArrayToFile(paymentLiveFile, paymentArray);

		log.info("[Persistence] Referential data successfully dispatched and written to individual files.");
	}

	// =========================================================
	// == PRIVATE UTILITY CORE                                ==
	// =========================================================

	private void checkAndCopySeed(File liveFile, String seedPath, String contextLabel) throws IOException {
		if (liveFile.exists()) {
			return;
		}

		log.info("[Persistence] Live file for '{}' not found. Activating seed copy from path: {}", contextLabel, seedPath);
		Resource seedResource = resourceLoader.getResource(seedPath);

		if (!seedResource.exists()) {
			throw new FileNotFoundException("Seed resource could not be found in the application bundle: " + seedPath);
		}

		ensureParentDirectoryExists(liveFile);

		try (InputStream in = seedResource.getInputStream()) {
			Files.copy(in, liveFile.toPath());
			log.info("[Persistence] Successfully deployed seed data for '{}' to live destination: {}", contextLabel, liveFile.getName());
		}
	}

	private JsonArray readArrayFromFile(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file);
		     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		     JsonReader reader = Json.createReader(isr)) {
			return reader.readArray();
		}
	}

	private void writeArrayToFile(File file, JsonArray array) {
		try (FileOutputStream fos = new FileOutputStream(file);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     JsonWriter writer = this.writerFactory.createWriter(osw)) {
			writer.writeArray(array);
		} catch (IOException e) {
			log.error("[Persistence] Failed to write JSON Array target to file: {}", file.getName(), e);
		}
	}

	private void ensureParentDirectoryExists(File file) {
		File parentDir = file.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			if (parentDir.mkdirs()) {
				log.info("[Persistence] Created missing directory layer: {}", parentDir.getAbsolutePath());
			}
		}
	}

	// Getters handles useful for unit testing lifecycles
	public File getStatusLiveFile() { return statusLiveFile; }
	public File getPaymentLiveFile() { return paymentLiveFile; }
}