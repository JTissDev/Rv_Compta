package com.jtissdev.features.compta.repository;

import com.jtissdev.features.compta.dto.JournalDTO;

import java.util.Optional;

/**
 * Repository interface defining the persistence contract for a {@link JournalDTO}.
 * <p>
 * This abstraction decouples the business services from the underlying storage mechanism.
 * It allows the application to seamlessly switch from a flat-file JSON storage to a
 * relational database like MariaDB without altering the core business logic.
 * </p>
 *
 * @author J.Tiss
 * @email jtissdev@gmail.com
 * @version 1.0.0
 * @since 0.5.0
 */
public interface JournalRepositoryInterface {

	/**
	 * Persists the state of the provided accounting journal.
	 * <p>
	 * If a storage file or database record already exists, it will be overwritten
	 * with the new state of the journal, including all its operations and movements.
	 * </p>
	 *
	 * @param journal The {@link JournalDTO} instance containing the data to save. Must not be null.
	 * @throws RuntimeException If an unrecoverable I/O or database error occurs during serialization.
	 * @since 0.5.0
	 */
	void save(JournalDTO journal);

	/**
	 * Loads the accounting journal from the persistent storage.
	 * <p>
	 * This method utilizes an {@link Optional} container to gracefully handle cases
	 * where no persistence file or record has been initialized yet (e.g., first launch).
	 * </p>
	 *
	 * @return An {@link Optional} containing the hydrated {@link JournalDTO} if found,
	 * or {@link Optional#empty()} if no persistence data is currently available.
	 * @throws RuntimeException If the stored data is corrupted or fails to deserialize.
	 * @since 0.5.0
	 */
	Optional<JournalDTO> load();
}
