package com.jtissdev_API.features.PCG.repository;

import com.jtissdev_API.features.PCG.dto.PcgCoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a PcgRepository DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.1
 */
public abstract class PcgRepository implements PcgRepositoryInterface{

	protected final  Logger logger = LoggerFactory.getLogger(this.getClass());

	protected final String pcgSeedPath;
	protected final ResourceLoader resourceLoader;

	public PcgRepository(ResourceLoader resourceLoader,
	                     @Value("${app.seed.folder-path}") String seedFolder,
	                     @Value("${app.seed.pcg-file-name}") String pcgSeedFileName) {

		this.pcgSeedPath = seedFolder + pcgSeedFileName;
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Retrieves an input stream for the PCG seed resource.
	 *
	 * @return an {@link InputStream} instance for reading the PCG seed resource.
	 * @throws IOException if the PCG seed resource cannot be found or accessed.
	 * @since 0.6
	 */
	protected InputStream getSeedInputStream() throws IOException {
		Resource seedResource = resourceLoader.getResource(pcgSeedPath);

		if (!seedResource.exists()) {
			throw new FileNotFoundException("PCG seed resource could not be found inside the application bundle: " + pcgSeedPath);
		}

		return seedResource.getInputStream();
	}

	protected void checkAndDeploySeed() throws Exception {
		// On utilise la méthode de l'interface pour savoir si la zone vivante est vide
		if (this.getDataSize() > 0) {
			return; // Il y a déjà des données, on ne fait rien
		}

		logger.info("[Persistence] Live data is empty. Loading seed from source: {}", pcgSeedPath);
		Resource seedResource = resourceLoader.getResource(pcgSeedPath);

		if (!seedResource.exists()) {
			throw new FileNotFoundException("PCG seed resource not found: " + pcgSeedPath);
		}

		// On ouvre la graine et on la passe à la classe fille
		try (InputStream seedStream = seedResource.getInputStream()) {
			writeSeedToLiveZone(seedStream);
			logger.info("[Persistence] Seed successfully deployed to live zone.");
		}
	}

	protected abstract void writeSeedToLiveZone(InputStream seedStream) throws Exception;

}
