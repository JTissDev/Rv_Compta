package com.jtissdev.features.referential.repository;

import com.jtissdev.core.repository.CrudRepository;
import com.jtissdev.features.referential.dto.PaymentMethod;

/**
 * Contrat d'accès pour la gestion unitaire des modes de paiement.
 *
 * @author J.Tiss
 * @email jtissdev.gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, String> {
}