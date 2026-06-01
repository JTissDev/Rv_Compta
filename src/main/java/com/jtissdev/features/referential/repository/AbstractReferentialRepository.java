package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract base class for referential data repositories that provides common functionality
 * for managing seed data and ensures that child implementations adhere to the persistence operations
 * defined in the {@link ReferentialRepository} interface.
 *
 * The class handles the orchestration of deploying seed data for predefined targets (status and payment),
 * and delegates specific persistence responsibilities to subclasses through abstract methods.
 * @author J.Tiss
 * @since 0.6.0
 * @version 1.0.0
 */
public abstract class AbstractReferentialRepository implements ReferentialRepository {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final ResourceLoader resourceLoader;

	protected final String statusSeedPath;
	protected final String paymentSeedPath;

	/**
	 * Constructs an {@code AbstractReferentialRepository} with the specified resource loader and seed paths.
	 * This constructor primarily initializes the required dependencies and paths necessary
	 * for managing and deploying seed data.
	 *
	 * @param resourceLoader the {@code ResourceLoader} used to load resources such as seed files
	 * @param statusSeedPath the file path to the seed data for status targets
	 * @param paymentSeedPath the file path to the seed data for payment targets
	 * @since  1.0.0
	 */
	public AbstractReferentialRepository(ResourceLoader resourceLoader, String statusSeedPath, String paymentSeedPath) {
		this.resourceLoader = resourceLoader;
		this.statusSeedPath = statusSeedPath;
		this.paymentSeedPath = paymentSeedPath;
	}

	// =========================================================
	// == ORCHESTRATION DES GRAINES (SEEDS)                   ==
	// =========================================================

	/**
	 * Verifies the presence of required seed data in the persistence layer, and deploys the
	 * respective seeds if any data is missing. This method ensures that the seed files for
	 * "status" and "payment" targets are deployed when the corresponding data is not already present.
	 *
	 * The method performs the following checks and actions:
	 * - If status data is missing, logs a message indicating the activation of the corresponding seed
	 *   and invokes seed deployment for the "status" target using the predefined status seed path.
	 * - If payment data is missing, logs a message indicating the activation of the corresponding seed
	 *   and invokes seed deployment for the "payment" target using the predefined payment seed path.
	 *
	 * Any exceptions encountered during the orchestration of seed deployment are logged as errors.
	 * @since 1.0.0
	 */
	protected void checkAndDeploySeeds() {
		try {
			if (!isStatusDataPresent()) {
				logger.info("[Persistence] Status data missing. Activating seed...");
				deploySeed(statusSeedPath, TargetType.STATUS);
			}
			if (!isPaymentDataPresent()) {
				logger.info("[Persistence] Payment data missing. Activating seed...");
				deploySeed(paymentSeedPath, TargetType.PAYMENT);
			}
		} catch (Exception e) {
			logger.error("[Persistence] Error during seed deployment orchestration", e);
		}
	}

	/**
	 * Reads the seed data from the specified resource and writes it to the persistence layer.
	 * This method is responsible for reading the seed data from the specified path,
	 * ensuring that the resource exists, and then invoking the persistence layer's
	 * writeSeedToStorage method to write the seed data to the appropriate location.
	 *
	 * @param seedPath The path to the seed resource within the application's resource bundle.
	 * @param type The type of target for which the seed is being deployed.
	 * @throws IOException If there is an error reading from the seed resource or writing to the persistence layer.
	 * @since 1.0.0
	 */
	private void deploySeed(String seedPath, TargetType type) throws IOException {
		Resource seedResource = resourceLoader.getResource(seedPath);
		if (!seedResource.exists()) {
			throw new FileNotFoundException("Seed resource missing in bundle: " + seedPath);
		}

		try (InputStream seedStream = seedResource.getInputStream()) {
			writeSeedToStorage(seedStream, type);
			logger.info("[Persistence] Successfully deployed seed data for: {}", type);
		}
	}

	/**
	 * Represents the type of target for which seed data is managed within the context
	 * of the {@code AbstractReferentialRepository} class. This enumeration serves
	 * as a key discriminator for processing different categories of seed data.
	 *
	 * <ul>
	 * <li>{@code STATUS} - Identifies seed data associated with status targets.</li>
	 * <li>{@code PAYMENT} - Identifies seed data associated with payment targets.</li>
	 * </ul>
	 *
	 * The {@code TargetType} enumeration is primarily used to distinguish between these
	 * two categories when verifying the presence of seed data, deploying missing seeds,
	 * and writing corresponding data to the persistence layer.
	 * @since 1.0.0
	 */
	protected enum TargetType {
		STATUS, PAYMENT
	}

	// =========================================================
	// == MÉTHODES ABSTRAITES (DÉLÉGUÉES AUX ENFANTS)         ==
	// =========================================================

	/**
	 * Checks whether the status data is present in the persistence layer.
	 * This method is intended to be implemented by subclasses to define the specific
	 * logic for determining the presence of the "status" target seed data.
	 *
	 * @return {@code true} if the status data is present, {@code false} otherwise.
	 * @since 1.0.0
	 */
	protected abstract boolean isStatusDataPresent();

	/**
	 * Checks whether the payment data is present in the persistence layer.
	 * This method is intended to be implemented by subclasses to define the specific
	 * logic for determining the presence of the "payment" target seed data.
	 *
	 * @return {@code true} if the payment data is present, {@code false} otherwise.
	 * @since 1.0.0
	 */
	protected abstract boolean isPaymentDataPresent();

	/**
	 * Writes the seed data from the provided input stream to the persistence layer for the specified target type.
	 * This method is intended to be implemented by subclasses to define the specific logic for persisting
	 * seed data associated with the given target type.
	 *
	 * @param seedStream the input stream containing the seed data to be written.
	 * @param targetType the target type for which the seed data is being written, such as {@code STATUS} or {@code PAYMENT}.
	 * @throws IOException if an I/O error occurs while writing the seed data to the persistence layer.
	 * @since 1.0.0
	 */
	protected abstract void writeSeedToStorage(InputStream seedStream, TargetType targetType) throws IOException;



	// Les méthodes load() et save() restent abstraites via l'interface
}