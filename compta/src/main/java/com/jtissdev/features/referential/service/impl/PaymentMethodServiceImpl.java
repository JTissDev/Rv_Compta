package com.jtissdev.features.referential.service.impl;

import com.jtissdev.features.referential.dto.PaymentMethod;
import com.jtissdev.features.referential.repository.PaymentMethodRepository;
import com.jtissdev.features.referential.service.PaymentMethodService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private final PaymentMethodRepository repository;

	public PaymentMethodServiceImpl(PaymentMethodRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<PaymentMethod> getAllMethods() {
		return repository.findAll();
	}

	@Override
	public Optional<PaymentMethod> getMethodById(String code) {
		if (code == null || code.trim().isEmpty()) {
			return Optional.empty();
		}
		return repository.findById(code);
	}

	@Override
	public List<PaymentMethod> searchByPartialName(String partialName) {
		if (partialName == null || partialName.trim().isEmpty()) {
			return Collections.emptyList();
		}
		return repository.findAll().stream()
				       .filter(m -> m.getName() != null &&
						                    m.getName().toLowerCase().contains(partialName.toLowerCase()))
				       .toList();
	}

	@Override
	public PaymentMethod createMethod(PaymentMethod method) {
		if (method == null || method.getCode() == null || method.getCode().trim().isEmpty()) {
			throw new IllegalArgumentException("Le mode de paiement et son code identifiant ne peuvent pas être nuls.");
		}

		if (repository.findById(method.getCode()).isPresent()) {
			throw new IllegalStateException("Un mode de paiement avec le code '" + method.getCode() + "' existe déjà.");
		}

		return repository.save(method);
	}

	@Override
	public PaymentMethod updateMethod(String code, PaymentMethod method) {
		if (code == null || method == null) {
			throw new IllegalArgumentException("Le code cible et les données de modification ne peuvent pas être nuls.");
		}

		return repository.findById(code)
				       .map(existing -> {
					       method.setCode(code); // On force la cohérence de la clé primaire
					       return repository.save(method);
				       })
				       .orElseThrow(() -> new NoSuchElementException("Mise à jour impossible : Mode de paiement introuvable avec le code '" + code + "'"));
	}

	@Override
	public void deleteMethod(String code) {
		if (code == null || code.trim().isEmpty()) {
			return;
		}
		// TODO : Plus tard, bloquer la suppression si le mode de paiement est utilisé par une écriture comptable.
		repository.deleteById(code);
	}
}