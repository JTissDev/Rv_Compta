package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import com.jtissdev.features.referential.mapper.ReferentialDataMapper;
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
public class JsonFileReferentialCoreRepository extends AbstractReferentialRepository {

	private static final Logger log = LoggerFactory.getLogger(JsonFileReferentialCoreRepository.class);

	private final File statusLiveFile;
	private final File paymentLiveFile;
	private final ReferentialDataMapper mapper;
	private final JsonWriterFactory writerFactory;

	/**
	 * Constructs the repository with dynamic multi-environment property injection.
	 *
	 * @param resourceLoader Spring's resource loader to access classpath seeds.
	 * @param mapper The mapper for converting between DTO and entity representations.
	 * @param storagePath The destination directory for live data mutations.
	 * @param statusFileName Name of the operation statuses file.
	 * @param paymentFileName Name of the payment methods file.
	 * @param seedPath The source folder prefix inside the application bundle.
	 * @since 2.0.0
	 */
	public JsonFileReferentialCoreRepository(
			ResourceLoader resourceLoader,
			ReferentialDataMapper mapper,
			@Value("${app.persistence.storage-path}") String storagePath,
			@Value("${app.persistence.file-name.status}") String statusFileName,
			@Value("${app.persistence.file-name.paymentMethod}") String paymentFileName,
			@Value("${app.persistence.seed-path}") String seedPath) {

		super(resourceLoader, seedPath + statusFileName, seedPath + paymentFileName);
		this.mapper = mapper;
		this.statusLiveFile = new File(storagePath, statusFileName);
		this.paymentLiveFile = new File(storagePath, paymentFileName);
		this.writerFactory = Json.createWriterFactory(Map.of(JsonGenerator.PRETTY_PRINTING, true));

		// Initialisation : Vérifie et déploie les seeds si le dossier est vide
		checkAndDeploySeeds();
	}

	@Override
	public boolean hasData() {
		return statusLiveFile.exists() && paymentLiveFile.exists();
	}


	/**
	 * Loads the split JSON structures, applies seed copies if missing, and aggregates them
	 * into a single unified {@link ReferentialCoreDTO}.
	 *
	 * @return an {@link Optional} containing the hydrated referential object, or {@link Optional#empty()} if recovery fails.
	 * @since 2.0.0
	 */
	@Override
	public Optional<ReferentialCoreDTO> load() {
		try {
			ReferentialCoreDTO dto = new ReferentialCoreDTO();

			if (isStatusDataPresent()) {
				try (InputStream is = new FileInputStream(statusLiveFile)) {
					dto.setOperationStatuses(mapper.toOperationStatusList(is));
				}
			}

			if (isPaymentDataPresent()) {
				try (InputStream is = new FileInputStream(paymentLiveFile)) {
					dto.setPaymentMethods(mapper.toPaymentMethodList(is));
				}
			}

			return Optional.of(dto);
		} catch (Exception e) {
			logger.error("[Persistence] Error loading Referential Data", e);
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

	/**
	 * Checks whether the status data is present in the persistence layer.
	 * This method is intended to be implemented by subclasses to define the specific
	 * logic for determining the presence of the "status" target seed data.
	 *
	 * @return {@code true} if the status data is present, {@code false} otherwise.
	 *
	 * @since 1.0.0
	 */
	@Override
	protected boolean isStatusDataPresent() {
		return statusLiveFile.exists();
	}

	/**
	 * Checks whether the payment data is present in the persistence layer.
	 * This method is intended to be implemented by subclasses to define the specific
	 * logic for determining the presence of the "payment" target seed data.
	 *
	 * @return {@code true} if the payment data is present, {@code false} otherwise.
	 *
	 * @since 1.0.0
	 */
	@Override
	protected boolean isPaymentDataPresent() {
		return paymentLiveFile.exists();
	}

	/**
	 * Writes the seed data from the provided input stream to the persistence layer for the specified target type.
	 * This method is intended to be implemented by subclasses to define the specific logic for persisting
	 * seed data associated with the given target type.
	 *
	 * @param seedStream
	 * 		the input stream containing the seed data to be written.
	 * @param targetType
	 * 		the target type for which the seed data is being written, such as {@code STATUS} or {@code PAYMENT}.
	 * @throws IOException
	 * 		if an I/O error occurs while writing the seed data to the persistence layer.
	 * @since 1.0.0
	 */
	@Override
	protected void writeSeedToStorage(InputStream seedStream, TargetType targetType) throws IOException {
		File targetFile = (targetType == TargetType.STATUS) ? statusLiveFile : paymentLiveFile;
		ensureParentDirectoryExists(targetFile);
		Files.copy(seedStream, targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	}
}