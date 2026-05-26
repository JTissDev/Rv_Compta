package com.jtissdev_API.features.PCG.repository;

import com.jtissdev_API.features.PCG.dto.PcgCoreDTO;
import java.util.Optional;

/**
 * A repository interface for managing the persistence layer of the PCG data.
 * Provides methods for saving and loading PCG structures via the PcgCoreDTO object.
 * * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public interface PcgRepositoryInterface {

	/**
	 * Saves the provided PCG data to the persistent storage.
	 *
	 * @param pcgData The PCG data to save. Must not be null.
	 * @throws RuntimeException If an unrecoverable I/O error occurs during serialization.
	 */
	void save(PcgCoreDTO pcgData);

	/**
	 * Loads the PCG data from the persistent storage.
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