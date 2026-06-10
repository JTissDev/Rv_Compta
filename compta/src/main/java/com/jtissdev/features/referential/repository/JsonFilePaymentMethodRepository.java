package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.PaymentMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;

/**
 * Implémentation par fichier JSON pour la persistance des modes de paiement.
 *
 * @author J.Tiss
 * @email jtissdev.gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
@Repository
@ConditionalOnProperty(name = "rvcompta.storage.type", havingValue = "json", matchIfMissing = true)
public class JsonFilePaymentMethodRepository extends AbstractReferentialJsonRepository<PaymentMethod, String> implements PaymentMethodRepository {

	public JsonFilePaymentMethodRepository(
			ResourceLoader resourceLoader,
			@Value("${app.persistence.storage-path}") String storagePath,
			@Value("${app.persistence.file-name.paymentMethod}") String paymentFileName,
			@Value("${app.persistence.seed-path}") String seedPath) {

		super(
				new File(storagePath, paymentFileName),
				resourceLoader,
				seedPath + paymentFileName,
				PaymentMethod::getCode,       // Extraction ID
				PaymentMethod::new,           // JsonObject -> Object
				PaymentMethod::toJson         // Object -> JsonObject
		);

		checkAndDeploySeed();
	}
}