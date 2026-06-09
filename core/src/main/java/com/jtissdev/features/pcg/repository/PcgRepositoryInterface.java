package com.jtissdev.features.pcg.repository;

import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import java.util.Optional;

/**
 * A repository interface for managing the persistence layer of the pcg data.
 * Provides methods for saving and loading pcg structures via the PcgCoreDTO object.
 * * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public interface PcgRepositoryInterface {

	/**
	 * Saves the provided pcg data to the persistent storage.
	 *
	 * @param pcgData The pcg data to save. Must not be null.
	 * @throws RuntimeException If an unrecoverable I/O error occurs during serialization.
	 */
	void save(PcgCoreDTO pcgData);

	/**
	 * Loads the pcg data from the persistent storage.
	 *
	 * @return An Optional containing the hydrated PcgCoreDTO if found,
	 * or Optional.empty() if no persistence data is currently available.
	 */
	Optional<PcgCoreDTO> load();

	/**
	 * Returns the number of main entries (Accounting classes) currently stored.
	 * * @return The size of the data.
	 */
	int getDataSize();
}