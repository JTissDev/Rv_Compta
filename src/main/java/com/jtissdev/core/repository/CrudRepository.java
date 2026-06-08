package com.jtissdev.core.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface de contrat générique pour les opérations de CRUD élémentaires.
 * <p>
 * Standardise les accès aux données pour préparer la transition transparente vers JPA.
 * </p>
 *
 * @param <T>  Le type de l'objet de domaine / DTO géré.
 * @param <ID> Le type de l'identifiant unique de l'élément.
 *
 * @author J.Tiss
 * @email jtissdev@gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
public interface CrudRepository<T, ID> {

	/**
	 * Sauvegarde ou met à jour une entité.
	 *
	 * @param entity L'élément à persister.
	 * @return L'élément sauvegardé.
	 */
	T save(T entity);

	/**
	 * Recherche un élément par son identifiant unique.
	 *
	 * @param id L'identifiant cible.
	 * @return Un Optional contenant l'élément s'il existe.
	 */
	Optional<T> findById(ID id);

	/**
	 * Récupère l'ensemble des éléments stockés.
	 *
	 * @return La liste complète des éléments.
	 */
	List<T> findAll();

	/**
	 * Supprime un élément via son identifiant.
	 *
	 * @param id L'identifiant de l'élément à effacer.
	 */
	void deleteById(ID id);
}