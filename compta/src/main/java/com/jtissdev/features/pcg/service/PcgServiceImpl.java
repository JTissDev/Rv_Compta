package com.jtissdev.features.pcg.service;


import com.jtissdev.features.pcg.dto.AccountingType;
import com.jtissdev.features.pcg.dto.AccountingTypeDetails;
import com.jtissdev.features.pcg.dto.PcgCoreDTO;
import com.jtissdev.features.pcg.dto.SubAccountingType;
import com.jtissdev.features.pcg.repository.PcgRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PcgService interface that provides methods to manage and query
 * an accounting plan (Plan Comptable Général). This service enables retrieval of accounting
 * structures, targeted searches, and account management operations.
 * <p>
 * Key features include:
 * - Navigation within the accounting hierarchy (classes, subclasses, and details).
 * - Searching accounts by code or partial name match.
 * - Methods for adding, updating, and removing accounts, currently not implemented.
 *
 * @author J.Tiss
 * @version 1.0.0
 * @since 0.6
 */
public class PcgServiceImpl implements PcgService {
	private final PcgRepository pcgRepository;

	/**
	 * Constructs an instance of PcgServiceImpl with the provided PcgRepository.
	 *
	 * @param pcgRepository
	 * 		the repository instance used for accessing and managing PCG data
	 * @since 0.6
	 */
	public PcgServiceImpl(PcgRepository pcgRepository) {
		this.pcgRepository = pcgRepository;
	}

	/* ====================================================================\
    ===                       VUE GLOBALE & NAVIGATION                   ===
    ==================================================================== */

	/**
	 * Retrieves the full accounting plan as a PcgCoreDTO object.
	 * If no accounting plan is available in the repository, a new instance of PcgCoreDTO is returned.
	 *
	 * @return the full accounting plan as a PcgCoreDTO object;
	 * 		if no data is present, a new PcgCoreDTO instance is returned.
	 *
	 * @see PcgCoreDTO
	 * @see PcgRepository
	 * @see Optional
	 * @see #getAccountTreePath(int)
	 * @see #getRootClasses()
	 * @see #getSubClasses(int)
	 * @see #getAccountDetails(int)
	 * @see #findAccountByCode(int)
	 * @see #findAccountsByName(String)
	 * @see #addAccount(int, Object)
	 * @see #updateAccount(int, Object)
	 * @see #removeAccount(int)
	 * @since 0.6
	 */
	@Override
	public PcgCoreDTO getFullAccountingPlan() {
		return pcgRepository.load().orElse(new PcgCoreDTO());
	}

	/**
	 * Retrieves the hierarchical path of an account within the accounting tree, formatted as
	 * a string representation with nested levels separated by " > ".
	 * If the account is not found, a message indicating the account is not reachable is returned.
	 *
	 * @param fullCode
	 * 		the full numeric code of the accounting entry whose tree path is to be retrieved
	 * @return a string representing the hierarchical path of the specified account within the
	 * 		accounting tree; if the account cannot be found, a message indicating this is returned
	 * 		instead.
	 *
	 * @since 0.6
	 */
	@Override
	public String getAccountTreePath(int fullCode) {
		String targetCode = String.valueOf(fullCode);

		for (AccountingType root : getRootClasses()) {
			if (root.getFullCode() != null && targetCode.startsWith(root.getFullCode())) {
				String path = root.toString();
				if (root.getFullCode().equals(targetCode)) return path;

				for (SubAccountingType sub : root.getSubTypes()) {
					if (sub.getFullCode() != null && targetCode.startsWith(sub.getFullCode())) {
						path += " > " + sub.toString();
						if (sub.getFullCode().equals(targetCode)) return path;

						for (AccountingTypeDetails detail : sub.getDetailsList()) {
							if (detail.getFullCode() != null && detail.getFullCode().equals(targetCode)) {
								return path + " > " + detail.toString();
							}
						}
					}
				}
			}
		}
		return "Chemin introuvable pour le compte : " + fullCode;
	}

	/* ====================================================================\
    ===                   LECTURE PAR NIVEAU (GETTERS)                   ===
    ==================================================================== */

	/**
	 * Retrieves a list of root-level accounting classes from the full accounting plan.
	 * If the full accounting plan is unavailable, an empty list is returned.
	 *
	 * @return a list of {@code AccountingType} objects representing the root-level accounting classes
	 * 		from the full accounting plan, or an empty list if no data is available.
	 *
	 * @since 0.6
	 */
	@Override
	public List<AccountingType> getRootClasses() {
		PcgCoreDTO pcg = getFullAccountingPlan();
		return pcg != null ? pcg.getAccountingClasses() : new ArrayList<>();
	}

	/**
	 * Retrieves a list of subclasses associated with a specified parent accounting class code.
	 * If the parent class code cannot be matched to any root class, an empty list is returned.
	 *
	 * @param parentClassCode the numeric code of the parent accounting class whose subclasses are to be retrieved
	 * @return a list of {@code SubAccountingType} objects representing the subclasses of the specified parent class;
	 *         if the specified parent class code does not correspond to any root class, an empty list is returned
	 *
	 *         @since 0.6
	 */
	@Override
	public List<SubAccountingType> getSubClasses(int parentClassCode) {
		String parentCodeStr = String.valueOf(parentClassCode);
		return getRootClasses().stream()
				       .filter(root -> parentCodeStr.equals(root.getFullCode()))
				       .findFirst()
				       .map(AccountingType::getSubTypes)
				       .orElse(new ArrayList<>());
	}

	/**
	 * Retrieves a list of account details associated with a specified parent subclass code.
	 * If the parent subclass code cannot be matched to any subclass, an empty list is returned.
	 *
	 * @param parentSubClassCode the numeric code of the parent subclass whose account details are to be retrieved
	 * @return a list of {@code AccountingTypeDetails} objects representing the account details of the specified parent subclass;
	 *         if the specified parent subclass code does not correspond to any subclass, an empty list is returned
	 *
	 * @since 0.6
	 */
	@Override
	public List<AccountingTypeDetails> getAccountDetails(int parentSubClassCode) {
		String targetParentStr = String.valueOf(parentSubClassCode);
		for (AccountingType root : getRootClasses()) {
			for (SubAccountingType sub : root.getSubTypes()) {
				if (targetParentStr.equals(sub.getFullCode())) {
					return sub.getDetailsList();
				}
			}
		}
		return new ArrayList<>();
	}

	/* ====================================================================\
    ===                       RECHERCHES CIBLÉES                         ===
    ==================================================================== */
	/**
	 * Searches for an account by its full code.
	 * If the account is found, it is returned wrapped in an Optional.
	 * If the account is not found, an empty Optional is returned.
	 *
	 * @param fullCode the numeric code of the account to be found
	 * @return an Optional containing the found account, or an empty Optional if not found
	 *
	 * @since 0.6
	 */
	@Override
	public Optional<Object> findAccountByCode(int fullCode) {
		String codeStr = String.valueOf(fullCode);

		for (AccountingType root : getRootClasses()) {
			if (codeStr.equals(root.getFullCode())) return Optional.of(root);

			for (SubAccountingType sub : root.getSubTypes()) {
				if (codeStr.equals(sub.getFullCode())) return Optional.of(sub);

				for (AccountingTypeDetails detail : sub.getDetailsList()) {
					if (codeStr.equals(detail.getFullCode())) return Optional.of(detail);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Searches for accounts by a partial name match, ignoring case.
	 * The search is performed across all levels of the accounting hierarchy (root classes, subclasses, and details).
	 * If the provided name is null or blank, an empty list is returned.
	 *
	 * @param name the partial name to search for in account names
	 * @return a list of accounts (of any type) whose names contain the specified partial name, ignoring case;
	 *         if the provided name is null or blank, an empty list is returned
	 *
	 * @since 0.6
	 */
	@Override
	public List<Object> findAccountsByName(String name) {
		List<Object> results = new ArrayList<>();
		if (name == null || name.isBlank()) return results;

		String searchLower = name.toLowerCase();

		for (AccountingType root : getRootClasses()) {
			if (root.getName() != null && root.getName().toLowerCase().contains(searchLower)) {
				results.add(root);
			}
			for (SubAccountingType sub : root.getSubTypes()) {
				if (sub.getName() != null && sub.getName().toLowerCase().contains(searchLower)) {
					results.add(sub);
				}
				for (AccountingTypeDetails detail : sub.getDetailsList()) {
					if (detail.getName() != null && detail.getName().toLowerCase().contains(searchLower)) {
						results.add(detail);
					}
				}
			}
		}
		return results;
	}

	/* ====================================================================\
    ===            MUTATIONS (PROTECTION PAR EXCEPTION)                  ===
    ==================================================================== */

	/**
	 * Adds a new account to the accounting hierarchy under the specified parent code.
	 * Throws an UnsupportedOperationException if the operation is not yet implemented.
	 *
	 * @param parentCode the numeric code of the parent account under which the new account is to be added
	 * @param newAccount the new account to be added
	 *
	 * @throws UnsupportedOperationException if the operation is not yet implemented
	 *
	 * @since 0.6
	 */
	@Override
	public void addAccount(int parentCode, Object newAccount) {
		throw new UnsupportedOperationException("L'ajout d'un compte n'est pas encore implémenté.");
	}

	/**
	 * Updates an existing account in the accounting hierarchy with the provided updated account data.
	 * This operation is not yet implemented and will throw an UnsupportedOperationException.
	 *
	 * @param fullCode       the numeric code of the account to be updated
	 * @param updatedAccount the updated account object containing the new data
	 * @throws UnsupportedOperationException if the operation is not yet implemented
	 * @since 0.6
	 */
	@Override
	public void updateAccount(int fullCode, Object updatedAccount) {
		throw new UnsupportedOperationException("La mise à jour d'un compte n'est pas encore implémentée.");
	}

	/**
	 * Removes an existing account from the accounting hierarchy based on its full code.
	 * This operation is not yet implemented and will throw an UnsupportedOperationException.
	 *
	 * @param fullCode the numeric code of the account to be removed
	 * @throws UnsupportedOperationException if the operation is not yet implemented
	 * @since 0.6
	 */
	@Override
	public void removeAccount(int fullCode) {
		throw new UnsupportedOperationException("La suppression d'un compte n'est pas encore implémentée.");
	}
}
