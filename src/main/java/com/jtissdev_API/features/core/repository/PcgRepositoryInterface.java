package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.core.dto.PcgCoreDTO;

import java.util.Optional;

/**
 * A repository interface for managing the persistence layer of the PCG data.
 * Provides methods for saving and loading PCG structures via the PcgCoreDTO object.
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public interface PcgRepositoryInterface {

	/**
	 * Saves the provided PCG data to the persistent storage.
	 *
	 * @param pcgData The PCG data to save. Must not be null.
	 * @throws RuntimeException If an unrecoverable I/O error occurs during serialization.
	 * @since 0.6
	 */
	void save(PcgCoreDTO pcgData);

	/**
	 * Loads the PCG data from the persistent storage.
	 *
	 * @return An Optional containing the hydrated PcgCoreDTO if found,
	 * or Optional.empty() if no persistence data is currently available.
	 * @throws RuntimeException If the stored data is corrupted or fails to deserialize.
	 * @since 0.6
	 */
	Optional<PcgCoreDTO> load();

	int getDataSize();
}
