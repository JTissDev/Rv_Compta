package com.jtissdev_API.features.PCG.repository;

import com.jtissdev_API.features.PCG.dto.PcgCoreDTO;
import jakarta.json.*;
import jakarta.json.stream.JsonGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * File-based implementation of the Plan Comptable Général (PCG) repository.
 *  * <p>
 *  * Manages the single JSON file persistence for the entire accounting structural hierarchy.
 *  * Relies on the parent class abstract logic to handle seed deployment orchestration.
 *  * </p>
 *
 * @author J.Tiss
 * @version 2.1.0
 * @since 0.6.0
 */
@Repository
public class JsonFilePcgRepository extends PcgRepository { ;

	private final JsonWriterFactory writerFactory;
	private final File pcgLiveFile;


	/**
	 * Constructs the PCG repository with environment-specific dynamic path injection.
	 *
	 * @param resourceLoader
	 * 		Spring's resource loader to access internal classpath assets.
	 * @param storageDir
	 * 		The mutable data storage root directory.
	 * @param seedFolder
	 * 		The base fallback folder prefix inside the application bundle.
	 * @since 2.0.0
	 */
	public JsonFilePcgRepository(
			ResourceLoader resourceLoader,
			@Value("${app.persistence.storage-path}") String storageDir,
			@Value("${app.seed.folder-path}") String seedFolder,
			@Value("${app.seed.pcg-file-name}") String pcgSeedFileName) {
		super(resourceLoader,seedFolder,pcgSeedFileName);

		this.pcgLiveFile = new File(storageDir, pcgSeedFileName);

		// Configuration du Pretty Printing pour conserver un fichier propre et lisible sur disque
		Map<String, Object> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		this.writerFactory = Json.createWriterFactory(config);

		logger.info("[Persistence] PCG Repository bound to live target: {}", pcgLiveFile.getAbsolutePath());
	}

	/**
	 * Loads the PCG structure from disk, triggers the seed fallback routine if missing,
	 * and returns an aggregated {@link PcgCoreDTO}.
	 *
	 * @return an {@link Optional} containing the hydrated PCG data object, or {@link Optional#empty()} if a fault occurs.
	 *
	 * @since 2.0.0
	 */
	@Override
	public Optional<PcgCoreDTO> load() {
		try {
			// Déclenchement automatique de la copie de la graine si le fichier vivant n'existe pas
			checkAndDeploySeed();

			if (!pcgLiveFile.exists()) {
				logger.error("[Persistence] Critical PCG reference file is missing even after seed copy execution.");
				return Optional.empty();
			}


			// Le fichier PCG.json stocke directement un tableau natif [ ... ]
			JsonArray pcgArray;
			try (FileInputStream fis = new FileInputStream(pcgLiveFile);
			     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			     JsonReader reader = Json.createReader(isr)) {
				pcgArray = reader.readArray();
			}

			// Reconstruction de l'enveloppe virtuelle attendue par le constructeur new PcgCoreDTO(JsonObject)
			JsonObject wrappedJson = Json.createObjectBuilder()
					                         .add("accountingClasses", pcgArray)
					                         .build();

			return Optional.of(new PcgCoreDTO(wrappedJson));

		} catch (Exception e) {
			logger.error("[Persistence] Critical failure while loading Plan Comptable Général hierarchy", e);
			return Optional.empty();
		}
	}

	/**
	 * Extracts the accounting hierarchy from the {@link PcgCoreDTO} wrapper
	 * and flattens it back into a native JSON array onto the disk.
	 *
	 * @param pcgCore
	 * 		the PCG DTO aggregate containing the classes tree.
	 * @throws IllegalArgumentException
	 * 		if the provided core instance is null.
	 * @since 2.0.0
	 */
	@Override
	public void save(PcgCoreDTO pcgCore) {
		if (pcgCore == null) {
			throw new IllegalArgumentException("Cannot save a null PCG core object.");
		}

		ensureParentDirectoryExists();

		// Exportation de la structure complète via le DTO
		JsonObject fullJson = pcgCore.toJson();

		// Extraction du tableau interne "accountingClasses"
		JsonArray pcgArray = fullJson.getJsonArray("accountingClasses");
		if (pcgArray == null) {
			pcgArray = Json.createArrayBuilder().build();
		}

		// Écriture du tableau aplati sur le disque (sans le wrapper externe)
		try (FileOutputStream fos = new FileOutputStream(pcgLiveFile);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     JsonWriter writer = this.writerFactory.createWriter(osw)) {
			writer.writeArray(pcgArray);
			logger.info("[Persistence] PCG structure successfully flattened and written to disk.");
		} catch (IOException e) {
			logger.error("[Persistence] Failed to write PCG JSON structure to file: {}", pcgLiveFile.getName(), e);
		}
	}

	@Override
	public int getDataSize() {
		if (!pcgLiveFile.exists()) {
			return 0; // Aucun fichier, donc taille = 0
		}

		// Lecture simple du fichier pour retourner la taille du tableau sans écraser de données
		try (FileInputStream fis = new FileInputStream(pcgLiveFile);
		     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		     JsonReader reader = Json.createReader(isr)) {

			JsonArray pcgArray = reader.readArray();
			return pcgArray.size();

		} catch (Exception e) {
			logger.error("[Persistence] Failed to compute data size from live file.", e);
			return 0;
		}
	}

	// =========================================================
	// == PRIVATE UTILITY CORE                                ==
	// =========================================================

	@Override
	protected void writeSeedToLiveZone(InputStream seedStream) throws IOException {
		ensureParentDirectoryExists();
		Files.copy(seedStream, pcgLiveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	private void ensureParentDirectoryExists() {
		File parentDir = pcgLiveFile.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			if (parentDir.mkdirs()) {
				logger.info("[Persistence] Created missing directory layer for PCG path: {}", parentDir.getAbsolutePath());
			}
		}
	}

	// Getter utile pour la gestion du cycle de vie des fichiers de test
	public File getPcgLiveFile() {
		return pcgLiveFile;
	}
}
