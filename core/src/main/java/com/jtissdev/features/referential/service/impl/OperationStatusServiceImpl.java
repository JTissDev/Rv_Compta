package com.jtissdev.features.referential.service.impl;

import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.repository.OperationStatusRepository;
import com.jtissdev.features.referential.service.OperationStatusService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationStatusServiceImpl implements OperationStatusService {

	private final OperationStatusRepository repository;

	// Injection par constructeur (recommandé pour la testabilité)
	public OperationStatusServiceImpl(OperationStatusRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<OperationStatus> getAllStatuses() {
		return repository.findAll();
	}

	@Override
	public Optional<OperationStatus> getStatusById(String code) {
		if (code == null || code.trim().isEmpty()) {
			return Optional.empty();
		}
		return repository.findById(code);
	}

	@Override
	public List<OperationStatus> searchByPartialName(String partialName) {
		if (partialName == null || partialName.trim().isEmpty()) {
			return Collections.emptyList();
		}
		return repository.findAll().stream()
				       .filter(s -> s.getName() != null &&
						                    s.getName().toLowerCase().contains(partialName.toLowerCase()))
				       .toList();
	}

	@Override
	public OperationStatus createStatus(OperationStatus status) {
		if (status == null || status.getCode() == null || status.getCode().trim().isEmpty()) {
			throw new IllegalArgumentException("Le statut et son code identifiant ne peuvent pas être nuls ou vides.");
		}

		// Sécurité : Vérifier le doublon d'ID avant d'écraser
		if (repository.findById(status.getCode()).isPresent()) {
			throw new IllegalStateException("Un statut avec le code '" + status.getCode() + "' existe déjà.");
		}

		return repository.save(status);
	}

	@Override
	public OperationStatus updateStatus(String code, OperationStatus status) {
		if (code == null || status == null) {
			throw new IllegalArgumentException("Le code cible et le corps du statut ne peuvent pas être nuls.");
		}

		return repository.findById(code)
				       .map(existing -> {
					       // Sécurité : On s'assure que l'entité sauvegardée porte le bon code cible
					       status.setCode(code);
					       return repository.save(status);
				       })
				       .orElseThrow(() -> new NoSuchElementException("Mise à jour impossible : Statut introuvable avec le code '" + code + "'"));
	}

	@Override
	public void deleteStatus(String code) {
		if (code == null || code.trim().isEmpty()) {
			return;
		}
		// TODO : Plus tard, ajouter ici une vérification pour interdire la suppression si le statut est lié à des opérations existantes.
		repository.deleteById(code);
	}
}