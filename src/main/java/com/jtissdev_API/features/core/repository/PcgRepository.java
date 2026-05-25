package com.jtissdev_API.features.core.repository;

import com.jtissdev_API.features.core.dto.PcgCoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

	protected void checkAndCopySeed() throws IOException {
		if (this.getDataSize()!=0){return;}

		logger.info("[Persistence] Live PCG file not found. Activating seed copy from source: {}", pcgSeedPath);
		Resource seedResource = resourceLoader.getResource(pcgSeedPath);

		if (!seedResource.exists()) {
			throw new FileNotFoundException("PCG seed resource could not be found inside the application bundle: " + pcgSeedPath);
		}

		// ToDo : algo de lecture du fichier source
		// ToDo defini l'objet PcgCoreDTO
		PcgCoreDTO pcgData = new PcgCoreDTO();
		this.save(pcgData);
	}

}
