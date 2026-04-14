package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tiers (personne physique ou morale).
 * <p>
 * Un {@code Tiers} contient :
 * <ul>
 *     <li>un identifiant technique ;</li>
 *     <li>un nom ou une raison sociale ;</li>
 *     <li>un indicateur précisant s'il s'agit d'un professionnel ;</li>
 *     <li>un code comptable par défaut ;</li>
 *     <li>une description ;</li>
 *     <li>une liste de contacts associés.</li>
 * </ul>
 *
 * @author jtiss
 * @since 1.2.0
 * @version 1.1.0
 */
public class Tiers {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	private Long id;
	private String nomOuRaisonSociale;
	private boolean professionnel;
	private Integer codeComptableParDefaut;
	private String description;

	/**
	 * Liste des contacts associés au tiers.
	 *
	 * @since 1.2.0
	 */
	private List<Contacts> contacts = new ArrayList<>();

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	public Tiers() {
		// no-args constructor
	}

	public Tiers(String nomOuRaisonSociale,
	             boolean professionnel,
	             Integer codeComptableParDefaut,
	             String description) {
		this.nomOuRaisonSociale = nomOuRaisonSociale;
		this.professionnel = professionnel;
		this.codeComptableParDefaut = codeComptableParDefaut;
		this.description = description;
	}

	public Tiers(Long id,
	             String nomOuRaisonSociale,
	             boolean professionnel,
	             Integer codeComptableParDefaut,
	             String description,
	             List<Contacts> contacts) {
		this.id = id;
		this.nomOuRaisonSociale = nomOuRaisonSociale;
		this.professionnel = professionnel;
		this.codeComptableParDefaut = codeComptableParDefaut;
		this.description = description;
		if (contacts != null) {
			this.contacts = new ArrayList<>(contacts);
		}
	}

	// =========================================================
	// == GETTERS & SETTERS                                   ==
	// =========================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomOuRaisonSociale() {
		return nomOuRaisonSociale;
	}

	public void setNomOuRaisonSociale(String nomOuRaisonSociale) {
		this.nomOuRaisonSociale = nomOuRaisonSociale;
	}

	public boolean isProfessionnel() {
		return professionnel;
	}

	public void setProfessionnel(boolean professionnel) {
		this.professionnel = professionnel;
	}

	public Integer getCodeComptableParDefaut() {
		return codeComptableParDefaut;
	}

	public void setCodeComptableParDefaut(Integer codeComptableParDefaut) {
		this.codeComptableParDefaut = codeComptableParDefaut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retourne la liste des contacts associés au tiers.
	 *
	 * @return liste non nulle (éventuellement vide)
	 *
	 * @since 1.2.0
	 */
	public List<Contacts> getContacts() {
		return contacts;
	}

	/**
	 * Remplace la liste des contacts.
	 *
	 * @param contacts nouvelle liste de contacts (peut être {@code null})
	 *
	 * @since 1.2.0
	 */
	public void setContacts(List<Contacts> contacts) {
		if (contacts == null) {
			this.contacts = new ArrayList<>();
		} else {
			this.contacts = new ArrayList<>(contacts);
		}
	}

	/**
	 * Ajoute un contact à la liste.
	 *
	 * @param contact contact à ajouter
	 *
	 * @since 1.2.0
	 */
	public void addContact(Contacts contact) {
		if (contact != null) {
			this.contacts.add(contact);
		}
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		// Dé-commenter si l'id doit être exposé :
		// if (id != null) {
		//     builder.add("id", id);
		// }

		if (nomOuRaisonSociale != null) {
			builder.add("nom_ou_raison_sociale", nomOuRaisonSociale);
		}

		builder.add("professionnel", professionnel);

		if (codeComptableParDefaut != null) {
			builder.add("code_comptable_par_defaut", codeComptableParDefaut);
		}

		if (description != null) {
			builder.add("description", description);
		}

		// Sérialisation de la liste de contacts
		JsonArrayBuilder contactsArrayBuilder = Json.createArrayBuilder();
		for (Contacts contact : contacts) {
			if (contact != null) {
				contactsArrayBuilder.add(contact.toJson());
			}
		}
		builder.add("contacts", contactsArrayBuilder);

		return builder.build();
	}

	// =========================================================
	// == OBJECT OVERRIDES                                    ==
	// =========================================================

	@Override
	public String toString() {
		return "Tiers{" +
				"id=" + id +
				", nomOuRaisonSociale='" + nomOuRaisonSociale + '\'' +
				", professionnel=" + professionnel +
				", codeComptableParDefaut=" + codeComptableParDefaut +
				", description='" + description + '\'' +
				", contacts=" + contacts +
				'}';
	}
}
