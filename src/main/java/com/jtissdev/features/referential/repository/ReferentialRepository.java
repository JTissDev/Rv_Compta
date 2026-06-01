package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import java.util.Optional;

/**
 * Defines the contract for a repository managing referential core data.
 * This interface allows for loading, saving, and checking the existence
 * of referential data. Implementations are responsible for defining
 * the persistence mechanism and ensuring that data operations are handled
 * appropriately.
 * @author J.Tiss
 * @version 1.0.0
 * @since 0.6.0
 */
public interface ReferentialRepository {
	/**
	 * Loads the referential core data.
	 * This method retrieves the current state of referential data managed by the repository.
	 * If no data is available, an empty {@code Optional} is returned.
	 *
	 * @return an {@code Optional} containing {@code ReferentialCoreDTO} if data is present;
	 *         otherwise, an empty {@code Optional}.
	 * @since 1.0.0
	 * @throws RuntimeException if an unrecoverable error occurs during data retrieval.
	 */
	Optional<ReferentialCoreDTO> load();

	/**
	 * Saves the provided referential core data.
	 * This method persists the given {@code ReferentialCoreDTO} instance,
	 * allowing the state of referential data to be stored for future use.
	 *
	 * @param referentialCoreDTO the data object containing referential core information
	 *                           to be persisted. Must not be {@code null}.
	 * @throws IllegalArgumentException if {@code referentialCoreDTO} is {@code null}.
	 * @throws RuntimeException if an error occurs during the save operation.
	 * @since 1.0.0
	 */
	void save(ReferentialCoreDTO referentialCoreDTO);

	/**
	 * Checks whether referential core data is available in the repository.
	 *
	 * @return {@code true} if referential data exists; {@code false} otherwise.
	 * @since 1.0.0
	 */
	boolean hasData();
}