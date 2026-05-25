package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.PCP.dto.AnalyticDetail;
import com.jtissdev_API.features.PCP.dto.Tiers;
import com.jtissdev_API.features.core.dto.PcpCoreDTO;
import jakarta.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for persisting and retrieving Personal Accounting Plan (PCP) data
 * from JSON-formatted files. This class is responsible for managing JSON file
 * interactions such as reading, writing, and ensuring the existence of template
 * files when necessary.
 * <p>
 * The repository manages two main data structures:
 * - Third parties (`tiersLiveFile`)
 * - Analytical details (`detailsLiveFile`)
 * <p>
 * Configuration paths and filenames for the storage and seed templates are
 * provided through externalized properties.
 * <p>
 * This repository uses the following functionalities:
 * - Ensuring predefined directories and files exist before interaction.
 * - Loading data from JSON files into in-memory DTO structures.
 * - Persisting in-memory DTO updates back to their respective JSON files.
 * <p>
 * Important logs and operations are captured through SLF4J logging.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 0.6.0
 *
 */
@Repository
public class JsonFilePcpRepository {

	private static final Logger log = LoggerFactory.getLogger(JsonFilePcpRepository.class);

	/**
	 * Responsible for accessing and managing resource files within the application context.
	 * Utilized to load configuration files, seed data, or other resources needed for
	 * initializing and operating the repository. This component provides an abstraction
	 * over resource handling, enabling the repository to retrieve resources dynamically
	 * from the application environment.
	 *
	 * @since 0.6
	 */
	private final ResourceLoader resourceLoader;

	/**
	 * A factory for creating JSON writer instances used for serializing data into JSON format.
	 * The factory is tailored to generate writers compliant with the application's serialization
	 * requirements, including formatting and customization settings where applicable.
	 *
	 * This instance is finalized to ensure consistent configuration and to prevent modifications
	 * to the factory during runtime. It serves as an essential component for managing JSON output
	 * within the repository operations, which include storing "Tiers" and "Details" information
	 * into designated JSON files.
	 *
	 * It is primarily utilized in methods responsible for writing PCP (Personal Accounting Plan)
	 * data structures into JSON files associated with the repository.
	 * @since 0.6
	 */
	private final JsonWriterFactory writerFactory;

	/**
	 * Represents the JSON file used to store "Tiers" data, which contains
	 * information about third-party entities relevant to the PCP (Personal
	 * Accounting Plan) system. This file is expected to persist data related
	 * to third-party accounting parties and is referenced during data
	 * loading, saving, and initialization operations.
	 * <p>
	 * The file is typically managed within the application's storage directory,
	 * and its initial content may be seeded from a default "Tiers" seed file
	 * during the repository's initialization, if no live file exists.
	 * <p>
	 * This variable is immutable and is set during the instantiation of
	 * the {@code JsonFilePcpRepository} class.
	 *
	 * @since 0.6
	 */
	private final File tiersLiveFile;
	/**
	 * Represents the JSON file used to persist detailed accounting information in the repository.
	 * This file is managed by the {@code JsonFilePcpRepository} class. It is primarily involved in
	 * reading and writing "Details" data that forms part of the Personal Accounting Plan (PCP).
	 * <p>
	 * The file is initialized during repository setup, using either existing live data or
	 * default seed data if the live file is not present. This ensures that the repository has
	 * the necessary starting data for handling accounting details.
	 *
	 * @since 0.6
	 */
	private final File detailsLiveFile;
	/**
	 * The file path to the default seed JSON file storing "Tiers" data used for
	 * third-party information persistence. This variable is initialized during the setup
	 * of the {@code JsonFilePcpRepository} class and is used as a fallback source
	 * to populate initial data when no live "Tiers" data file is available.
	 *
	 * @since 0.6
	 */
	private final String tiersSeedPath;
	/**
	 * Represents the file path for the seed data JSON file containing detailed
	 * accounting information. This path is used to locate and initialize the
	 * default "Details" data when the live data file is missing or uninitialized.
	 * <p>
	 * This variable is initialized during the repository setup and provides a
	 * reference to the default seed data to ensure the repository can be properly
	 * initialized or re-initialized with default values when necessary.
	 * <p>
	 * It plays a critical role in the maintenance and recovery of the repository's
	 * "Details" data, ensuring consistency and reliability of accounting
	 * information persistence.
	 *
	 * @since 0.6
	 */
	private final String detailsSeedPath;

	/**
	 * Initializes a repository for managing PCP (Personal Accounting Plan) data by
	 * using JSON files. This constructor sets up the necessary file paths for live
	 * data and seed data initialization.
	 *
	 * @param resourceLoader
	 * 		the resource loader used for accessing and managing
	 * 		resource files within the application context.
	 * @param storagePath
	 * 		the base directory path where the live data JSON
	 * 		files are stored.
	 * @param seedPath
	 * 		the base directory path for locating the default seed
	 * 		JSON files used for initialization.
	 * @param filenameTiers
	 * 		the filename of the JSON file storing "Tiers" data,
	 * 		used for third-party information persistence.
	 * @param filenameDetails
	 * 		the filename of the JSON file storing "Details" data,
	 * 		used for storing detailed accounting information.
	 * @since 0.6
	 */
	public JsonFilePcpRepository(
			ResourceLoader resourceLoader,
			@Value("${app.persistence.path}") String storagePath,
			@Value("${app.persistence.seed-path}") String seedPath,
			@Value("${app.persistence.filename-tiers}") String filenameTiers,
			@Value("${app.persistence.filename-details}") String filenameDetails) {

		this.resourceLoader = resourceLoader;


		// Initialisation des fichiers physiques cibles (ex: ./data/dev/Tiers.json)
		this.tiersLiveFile = new File(storagePath, filenameTiers);
		this.detailsLiveFile = new File(storagePath, filenameDetails);

		// Chemins vers le classpath pour le seed d'initialisation
		this.tiersSeedPath = seedPath + filenameTiers;
		this.detailsSeedPath = seedPath + filenameDetails;

		// --- AJOUT DE L'INITIALISATION DE LA FACTORY ---
		// Configuration du Pretty Printing pour conserver des fichiers lisibles sur disque
		java.util.Map<String, Object> config = new java.util.HashMap<>();
		config.put(jakarta.json.stream.JsonGenerator.PRETTY_PRINTING, true);
		this.writerFactory = Json.createWriterFactory(config);

		log.info("[PCP-Persistence] Initialisé. Fichiers cibles : {} et {}",
				tiersLiveFile.getAbsolutePath(), detailsLiveFile.getAbsolutePath());
	}

	/**
	 * Loads and deserializes the PCP (Personal Accounting Plan) data from the associated JSON files.
	 * This includes reading third-party data from "Tiers.json" and accounting details from "details.json".
	 * If the required files are missing, they are initialized with seed data.
	 *
	 * @return an {@link Optional} containing a {@link PcpCoreDTO} instance if the data is successfully loaded,
	 * 		or an empty {@link Optional} if an error occurs during the loading process.
	 *
	 * @since 0.6
	 */
	public Optional<PcpCoreDTO> load() {
		try {
			// 1. Vérification et déploiement du template si absent
			checkAndCopySeeds();

			PcpCoreDTO pcpCore = new PcpCoreDTO();

			// 2. Lecture et désérialisation du fichier Tiers.json
			pcpCore.setThirdParties(loadTiers());

			// 3. Lecture et désérialisation du fichier details.json
			pcpCore.setDetails(loadDetails());

			return Optional.of(pcpCore);
		} catch (Exception e) {
			log.error("[PCP-Persistence] Erreur critique lors du chargement des structures PCP", e);
			return Optional.empty();
		}
	}

	// =========================================================
	// == ÉCRITURE / PERSISTANCE (Orchestration & Individuelle)==
	// =========================================================

	/**
	 * Saves the entire Personal Accounting Plan (PCP) core structure by delegating
	 * to specific sub-saving routines for third parties and analytical details.
	 *
	 * @param pcpCore the PCP core data transfer object to persist, must not be null.
	 * @throws IllegalArgumentException if the provided pcpCore instance is null.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.6.0
	 */
	public void save(PcpCoreDTO pcpCore) {
		if (pcpCore == null) {
			throw new IllegalArgumentException("Cannot save a null PCP Core reference.");
		}

		log.info("[PCP-Persistence] Triggering full serialization loop for PCP components.");

		if (pcpCore.getThirdParties() != null) {
			this.saveTiers(pcpCore.getThirdParties());
		}

		if (pcpCore.getDetails() != null) {
			this.saveDetails(pcpCore.getDetails());
		}
	}

	/**
	 * Serializes and writes the list of third parties into the designated JSON live file.
	 *
	 * @param thirdParties the list of third parties to persist, must not be null.
	 * @throws IllegalArgumentException if the third parties list is null.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.6.0
	 */
	public void saveTiers(List<Tiers> thirdParties) {
		if (thirdParties == null) {
			throw new IllegalArgumentException("Third parties list cannot be null.");
		}

		jakarta.json.JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Tiers tier : thirdParties) {
			if (tier != null) {
				arrayBuilder.add(tier.toJson());
			}
		}
		jakarta.json.JsonArray jsonArray = arrayBuilder.build();

		ensureParentDirectoryExists(tiersLiveFile);

		try (OutputStream os = new FileOutputStream(tiersLiveFile);
		     jakarta.json.JsonWriter writer = writerFactory.createWriter(new OutputStreamWriter(os, java.nio.charset.StandardCharsets.UTF_8))) {

			writer.write(jsonArray);
			log.info("[PCP-Persistence] Successfully synchronized {} third parties to disk ({})",
					thirdParties.size(), tiersLiveFile.getName());

		} catch (IOException e) {
			log.error("[PCP-Persistence] Failed to write Third Parties JSON structure to file: {}", tiersLiveFile.getName(), e);
		}
	}

	/**
	 * Serializes and writes the list of analytical details into the designated JSON live file.
	 *
	 * @param details the list of analytic details to persist, must not be null.
	 * @throws IllegalArgumentException if the details list is null.
	 * @author J.Tiss <jtissdev@gmail.com>
	 * @version 1.0.0
	 * @since 0.6.0
	 */
	public void saveDetails(List<AnalyticDetail> details) {
		if (details == null) {
			throw new IllegalArgumentException("Analytic details list cannot be null.");
		}

		jakarta.json.JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (AnalyticDetail detail : details) {
			if (detail != null) {
				arrayBuilder.add(detail.toJson());
			}
		}
		jakarta.json.JsonArray jsonArray = arrayBuilder.build();

		ensureParentDirectoryExists(detailsLiveFile);

		try (OutputStream os = new FileOutputStream(detailsLiveFile);
		     jakarta.json.JsonWriter writer = writerFactory.createWriter(new OutputStreamWriter(os, java.nio.charset.StandardCharsets.UTF_8))) {

			writer.write(jsonArray);
			log.info("[PCP-Persistence] Successfully synchronized {} analytic details to disk ({})",
					details.size(), detailsLiveFile.getName());

		} catch (IOException e) {
			log.error("[PCP-Persistence] Failed to write Analytic Details JSON structure to file: {}", detailsLiveFile.getName(), e);
		}
	}

	// =========================================================
	// == LOGIQUE INTERNE DE CHARGEMENT                      ==
	// =========================================================

	/**
	 * Loads the "Tiers" data from a JSON file and converts it into a list of {@link Tiers} objects.
	 * The method reads the JSON array from the file, processes each entry, and creates
	 * a corresponding {@link Tiers} object for each valid JSON object found.
	 *
	 * @return a {@link List} of {@link Tiers} objects representing the deserialized data
	 * 		from the JSON file.
	 *
	 * @throws IOException
	 * 		if an I/O error occurs while reading the JSON file.
	 * @since 0.6
	 */
	private List<Tiers> loadTiers() throws IOException {
		List<Tiers> list = new ArrayList<>();
		try (InputStream in = new FileInputStream(tiersLiveFile);
		     JsonReader reader = Json.createReader(in)) {
			JsonArray array = reader.readArray();
			for (JsonValue value : array) {
				if (value.getValueType() == JsonValue.ValueType.OBJECT) {
					list.add(new Tiers(value.asJsonObject()));
				}
			}
		}
		return list;
	}

	/**
	 * Reads and deserializes analytic detail data from a JSON file specified by the
	 * {@code detailsLiveFile} field. The method processes the JSON array in the file,
	 * extracts each object, and converts it to an {@link AnalyticDetail} instance.
	 *
	 * @return a {@link List} of {@link AnalyticDetail} objects representing the data
	 * 		retrieved and parsed from the JSON file.
	 *
	 * @throws IOException
	 * 		if an I/O error occurs while accessing or reading the JSON file.
	 * @since 0.6
	 */
	private List<AnalyticDetail> loadDetails() throws IOException {
		List<AnalyticDetail> list = new ArrayList<>();
		try (InputStream in = new FileInputStream(detailsLiveFile);
		     JsonReader reader = Json.createReader(in)) {
			JsonArray array = reader.readArray();
			for (JsonValue value : array) {
				if (value.getValueType() == JsonValue.ValueType.OBJECT) {
					list.add(new AnalyticDetail(value.asJsonObject()));
				}
			}
		}
		return list;
	}

	// =========================================================
	// == OUTILS DE SEED ET REPERTOIRE                       ==
	// =========================================================

	/**
	 * Ensures that the required live data files for "Tiers" and "Details" structures exist
	 * by copying seed files from the specified paths if needed. This method verifies the existence
	 * of the parent directories for the live files and initializes missing files using pre-defined
	 * seed templates.
	 * <p>
	 * The method performs the following steps:
	 * 1. Ensures the parent directory for the "tiersLiveFile" exists.
	 * 2. Checks if the "tiersLiveFile" is missing and deploys it from the "tiersSeedPath".
	 * 3. Checks if the "detailsLiveFile" is missing and deploys it from the "detailsSeedPath".
	 *
	 * @throws IOException
	 * 		if an I/O error occurs while creating directories,
	 * 		accessing seed files, or copying data to the live files.
	 * @since 0.6
	 */
	private void checkAndCopySeeds() throws IOException {
		ensureParentDirectoryExists(tiersLiveFile);

		if (!tiersLiveFile.exists()) {
			deploySeed(tiersSeedPath, tiersLiveFile);
		}
		if (!detailsLiveFile.exists()) {
			deploySeed(detailsSeedPath, detailsLiveFile);
		}
	}

	/**
	 * Deploys a seed file to the specified destination file location. This method is used to ensure that
	 * a default template file is copied to the target destination if needed for initialization purposes.
	 *
	 * @param seedPath
	 * 		the path to the seed file resource. This string specifies the location of the
	 * 		template file to be deployed, and it must be accessible through the application's
	 *        {@code resourceLoader}.
	 * @param destFile
	 * 		the target file where the seed file will be deployed. The method will copy the
	 * 		contents of the seed resource into this file.
	 * @throws IOException
	 * 		if an I/O error occurs during the deployment process, including but not limited to
	 * 		file not found, resource access issues, or failure to copy the file contents.
	 * @since 0.6
	 */
	private void deploySeed(String seedPath, File destFile) throws IOException {
		log.info("[PCP-Persistence] Fichier manquant. Déploiement du template depuis : {}", seedPath);
		Resource seedResource = resourceLoader.getResource(seedPath);

		if (!seedResource.exists()) {
			throw new FileNotFoundException("Template introuvable dans le bundle de l'application : " + seedPath);
		}

		try (InputStream in = seedResource.getInputStream()) {
			Files.copy(in, destFile.toPath());
			log.info("[PCP-Persistence] Template déployé avec succès : {}", destFile.getName());
		}
	}

	/**
	 * Ensures that the parent directory of the specified file exists.
	 * If the parent directory does not exist, it attempts to create the necessary
	 * directory structure.
	 *
	 * @param file
	 *        the file whose parent directory is to be checked and created if needed.
	 *        This must not be null and should represent a valid file path.
	 *        If the parent directory does not exist, it will be created.
	 *        If the parent directory already exists, this method does nothing.
	 *        If the parent directory cannot be created, an exception is thrown.
	 * @throws IOException
	 *        if an I/O error occurs while attempting to create the parent directory.
	 * @since 0.6
	 */
	private void ensureParentDirectoryExists(File file) {
		File parentDir = file.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			if (parentDir.mkdirs()) {
				log.info("[PCP-Persistence] Création de la couche de dossiers manquante : {}", parentDir.getAbsolutePath());
			}
		}
	}
}