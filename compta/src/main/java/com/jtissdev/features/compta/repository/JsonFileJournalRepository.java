package com.jtissdev.features.compta.repository;

import com.jtissdev.features.compta.dto.JournalDTO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Concrete implementation of {@link JournalRepositoryInterface} for flat-file JSON persistence.
 * <p>
 * This class handles saving and loading the accounting journal to and from a local JSON file.
 * The storage directory and filename are dynamically injected from the application properties,
 * ensuring seamless environment switching (dev, test, prod).
 * </p>
 *
 * @author J.Tiss
 * @version 1.0.0
 * @email jtissdev@gmail.com
 * @since 0.6.0
 */
@Repository
public class JsonFileJournalRepository extends JournalRepository implements JournalRepositoryInterface {



	private final Path storagePath;

	/**
	 * Constructs the repository by resolving the full target file path.
	 *
	 * @param storageDir
	 * 		The directory path configured via {@code app.persistence.path}.
	 * @param filename
	 * 		The target file name configured via {@code app.persistence.filename}.
	 * @since 0.6.0
	 */
	public JsonFileJournalRepository(
			@Value("${app.persistence.storage-path}") String storageDir,
			@Value("${app.persistence.filename}") String filename) {

		this.storagePath = Paths.get(storageDir, filename).toAbsolutePath().normalize();
		logger.info("[Persistence] JSON Repository target path initialized to: {}", this.storagePath);
	}

	/**
	 * Saves the given {@link JournalDTO} instance to the configured storage location.
	 * <p>
	 * Handles the persistence of the journal data to a JSON file.
	 * This method ensures that the parent directory structure is created if it doesn't exist.
	 * The journal data is serialized to JSON format and written to the target file. If the file already exists,
	 * it will be overwritten.
	 * </p>
	 *
	 * @param journal
	 * 		The {@link JournalDTO} instance containing the data to save. Must not be null.
	 * @throws IllegalArgumentException
	 * 		If the provided journal is null.
	 * @throws RuntimeException
	 * 		If an unrecoverable I/O error occurs during serialization.
	 * @since 0.6.0
	 */
	@Override
	public void save(JournalDTO journal) {
		if (journal == null) {
			throw new IllegalArgumentException("Cannot save a null journal.");
		}

		try {
			// Sécurité : S'assurer que le dossier parent existe (ex: créera le dossier /storage s'il est manquant)
			Path parentDir = storagePath.getParent();
			if (parentDir != null && !Files.exists(parentDir)) {
				Files.createDirectories(parentDir);
				logger.info("[Persistence] Created missing storage directory: {}", parentDir);
			}

			// Conversion du DTO en JsonObject via ta méthode existante
			JsonObject jsonJournal = journal.toJson();

			// Écriture du fichier JSON sur le disque
			try (OutputStream os = Files.newOutputStream(storagePath);
			     JsonWriter writer = Json.createWriter(os)) {
				writer.writeObject(jsonJournal);
			}

			logger.info("[Persistence] Journal '{}' successfully saved to {}", journal.getName(), storagePath.getFileName());

		} catch (Exception e) {
			logger.error("[Persistence] Critical error while saving journal to {}", storagePath, e);
			throw new RuntimeException("Failed to persist accounting journal to file system.", e);
		}
	}

	/**
	 *
	 * Loads the accounting journal from the configured storage location.
	 * <p>
	 * This method attempts to read the JSON file from the disk and deserialize it into a {@link JournalDTO} instance.
	 * If the file does not exist (e.g., first launch), it returns an empty {@link Optional}. If the file exists but is corrupted or fails to deserialize, it throws a {@link RuntimeException}.
	 * </p>
	 * @return An {@link Optional} containing the hydrated {@link JournalDTO} if found,
	 *
	 * @throws RuntimeException If the stored data is corrupted or fails to deserialize.
	 * @since 0.6.0
	 *
	 */
	@Override
	public Optional<JournalDTO> load() {
		// Cas nominal du premier démarrage : si le fichier n'existe pas encore, on gère proprement
		if (!Files.exists(storagePath)) {
			logger.warn("[Persistence] Storage file {} does not exist yet. Returning empty Optional.", storagePath.getFileName());
			return Optional.empty();
		}

		try (InputStream is = Files.newInputStream(storagePath);
		     JsonReader reader = Json.createReader(is)) {

			JsonObject jsonJournal = reader.readObject();

			// Hydratation du DTO en utilisant ton constructeur basé sur un JsonObject
			JournalDTO journal = new JournalDTO(jsonJournal);

			logger.info("[Persistence] Journal '{}' successfully loaded from {}", journal.getName(), storagePath.getFileName());
			return Optional.of(journal);

		} catch (Exception e) {
			logger.error("[Persistence] Critical error while loading journal from {}", storagePath, e);
			throw new RuntimeException("Failed to load or parse accounting journal from file system.", e);
		}
	}
}