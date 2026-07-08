package com.jtissdev.features.pcg.service;


import com.jtissdev.features.pcg.dto.AccountingType;
import com.jtissdev.features.pcg.dto.AccountingTypeDetails;
import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import com.jtissdev.features.pcg.dto.SubAccountingType;

import java.util.List;
import java.util.Optional;

public interface PcgService {
	/* ====================================================================\
    ===                       VUE GLOBALE & NAVIGATION                   ===
    ==================================================================== */

	/**
	 * Récupère l'intégralité du plan comptable sous forme de DTO.
	 */
	PcgCoreDTO getFullAccountingPlan();

	/**
	 * Génère le chemin textuel complet (ex: "1 - Capitaux > 10 - Capital > 101 - Capital Social").
	 */
	String getAccountTreePath(int fullCode);

    /* ====================================================================\
    ===                   LECTURE PAR NIVEAU (GETTERS)                   ===
    ==================================================================== */

	/**
	 * Niveau 1 : Récupère toutes les classes racines (Comptes de capitaux, d'immobilisations...).
	 */
	List<AccountingType> getRootClasses();

	/**
	 * Niveau 2 : Récupère les sous-classes rattachées à une classe parente.
	 */
	List<SubAccountingType> getSubClasses(int parentClassCode);

	/**
	 * Niveau 3 : Récupère les détails rattachés à une sous-classe parente.
	 */
	List<AccountingTypeDetails> getAccountDetails(int parentSubClassCode);

    /* ====================================================================\
    ===                       RECHERCHES CIBLÉES                         ===
    ==================================================================== */

	/**
	 * Recherche un élément par son code comptable unique (qu'il soit de niveau 1, 2 ou 3).
	 */
	Optional<Object> findAccountByCode(int fullCode);

	/**
	 * Recherche des éléments par leur libellé (recherche partielle, insensible à la casse).
	 */
	List<Object> findAccountsByName(String name);

    /* ====================================================================\
    ===            MUTATIONS (PROTECTION PAR EXCEPTION)                  ===
    ==================================================================== */

	/**
	 * Ajoute un compte dans l'arbre.
	 * @throws UnsupportedOperationException si la méthode n'est pas encore implémentée.
	 */
	void addAccount(int parentCode, Object newAccount);

	/**
	 * Met à jour un compte existant via son code comptable.
	 * @throws UnsupportedOperationException si la méthode n'est pas encore implémentée.
	 */
	void updateAccount(int fullCode, Object updatedAccount);

	/**
	 * Supprime un compte de l'arbre via son code comptable.
	 * @throws UnsupportedOperationException si la méthode n'est pas encore implémentée.
	 */
	void removeAccount(int fullCode);
}