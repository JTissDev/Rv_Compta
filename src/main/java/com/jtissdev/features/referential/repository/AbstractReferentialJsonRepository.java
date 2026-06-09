package com.jtissdev.features.referential.repository;

import com.jtissdev.core.repository.AbstractJsonRepository;
import jakarta.json.JsonObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.function.Function;

/**
 * Extension d'infrastructure dédiée aux données de référence.
 * <p>
 * Ajoute un mécanisme d'auto-déploiement basé sur des fichiers sources (Seeds)
 * présents dans les ressources de livraison du projet.
 * </p>
 *
 * @param <T>  Le type du DTO de référence.
 * @param <ID> Le type de l'identifiant.
 *
 * @author J.Tiss
 * @email jtissdev.gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
public abstract class AbstractReferentialJsonRepository<T, ID> extends AbstractJsonRepository<T, ID> {

	protected final ResourceLoader resourceLoader;
	protected final String seedResourcePath;

	protected AbstractReferentialJsonRepository(File liveFile,
	                                        ResourceLoader resourceLoader,
	                                        String seedResourcePath,
	                                        Function<T, ID> idExtractor,
	                                        Function<JsonObject, T> jsonMapper,
	                                        Function<T, JsonObject> jsonUnmapper) {
		super(liveFile, idExtractor, jsonMapper, jsonUnmapper);
		this.resourceLoader = resourceLoader;
		this.seedResourcePath = seedResourcePath;
	}

	/**
	 * Inspecte la présence du fichier de travail et déploie la graine par défaut s'il est absent.
	 */
	protected void checkAndDeploySeed() {
		if (!liveFile.exists()) {
			log.info("[Referential] Fichier de travail absent. Déploiement du seed depuis : {}", seedResourcePath);
			ensureParentDirectoryExists();
			Resource resource = resourceLoader.getResource( seedResourcePath);
			try (InputStream is = resource.getInputStream()) {
				Files.copy(is, liveFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				log.info("[Referential] Grappe de données initialisée avec succès pour : {}", liveFile.getName());
			} catch (IOException e) {
				log.error("[Referential] Impossible de copier le fichier seed de référence : {}", seedResourcePath, e);
			}
		}
	}
}