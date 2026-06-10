package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.OperationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;

/**
 * Implémentation par fichier JSON pour la persistance des statuts d'opérations.
 *
 * @author J.Tiss
 * @email jtissdev.gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
@Repository
@ConditionalOnProperty(name = "rvcompta.storage.type", havingValue = "json", matchIfMissing = true)
public class JsonFileOperationStatusRepository extends AbstractReferentialJsonRepository<OperationStatus, String> implements OperationStatusRepository {

	public JsonFileOperationStatusRepository(
			ResourceLoader resourceLoader,
			@Value("${app.persistence.storage-path}") String storagePath,
			@Value("${app.persistence.file-name.status}") String statusFileName,
			@Value("${app.persistence.seed-path}") String seedPath) {

		super(
				new File(storagePath, statusFileName),
				resourceLoader,
				seedPath + statusFileName,
				OperationStatus::getCode,       // Extraction ID
				json -> new OperationStatus(json),         // JsonObject -> Object
				OperationStatus::toJson         // Object -> JsonObject
		);

		checkAndDeploySeed();
	}

	@Override
	public OperationStatus save(OperationStatus entity) {
		return super.save(entity);
	}
}