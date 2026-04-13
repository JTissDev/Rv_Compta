package com.jtissdev_API.DTO.PCG;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents the complete General Accounting Plan (PCG).
 * <p>
 * This class serves as the root container for the accounting hierarchy.
 * It manages a list of {@link Type_Comptable} for JSON serialization
 * and maintains an internal index (Map) for fast lookups by code.
 *
 * @author jtiss
 * @since 1.2.0
 * @version 1.1.0
 */
public class Pcg {

	// =========================================================
	// == FIELDS                                              ==
	// =========================================================

	/**
	 * Root list of accounting types (Level 1).
	 *
	 * @since 1.2.0
	 */
	private List<Type_Comptable> typesComptables = new ArrayList<>();

	/**
	 * Flat index for fast search by full accounting code.
	 * This field is marked final and is not serialized in the JSON output.
	 */
	private final Map<String, Type_Comptable_Details> codeIndex = new HashMap<>();

	// =========================================================
	// == CONSTRUCTORS                                        ==
	// =========================================================

	/**
	 * Constructs a new empty PCG container.
	 */
	public Pcg() {
	}

	// =========================================================
	// == METHODS                                             ==
	// =========================================================

	/**
	 * Builds the internal index from the current hierarchy.
	 * This method must be called after loading data from the database.
	 *
	 * @since 1.1.0
	 */
	public void buildIndex() {
		codeIndex.clear();
		for (Type_Comptable tc : typesComptables) {
			if (tc == null) continue;
			for (Sub_Type_Comptable stc : tc.getSubTypes()) {
				if (stc == null) continue;
				for (Type_Comptable_Details det : stc.getDetailsList()) {
					if (det != null && det.getFullCode() != null) {
						codeIndex.put(det.getFullCode(), det);
					}
				}
			}
		}
	}

	/**
	 * Searches for an accounting detail by its full code (e.g., "613000").
	 * Uses the internal index for O(1) performance.
	 *
	 * @param fullCode the complete accounting code to search for
	 * @return the corresponding {@link Type_Comptable_Details}, or {@code null} if not found
	 */
	public Type_Comptable_Details getByCode(String fullCode) {
		return codeIndex.get(fullCode);
	}

	/**
	 * Searches for accounting details by a name fragment (case-insensitive).
	 *
	 * @param query the text to search for within the name
	 * @return a list of matching {@link Type_Comptable_Details} (possibly empty)
	 */
	public List<Type_Comptable_Details> searchByName(String query) {
		if (query == null || query.isBlank()) return new ArrayList<>();

		String lowerQuery = query.toLowerCase();
		return codeIndex.values().stream()
				       .filter(d -> d.getName() != null && d.getName().toLowerCase().contains(lowerQuery))
				       .collect(Collectors.toList());
	}

	// =========================================================
	// == GETTERS & SETTERS                                   ==
	// =========================================================

	public List<Type_Comptable> getTypesComptables() {
		return typesComptables;
	}

	public void setTypesComptables(List<Type_Comptable> typesComptables) {
		this.typesComptables = typesComptables;
	}

	// =========================================================
	// == JSON SERIALIZATION                                  ==
	// =========================================================

	/**
	 * Builds the JSON object representing the PCG.
	 * Only the hierarchy (List) is exposed; the internal index is ignored.
	 *
	 * @return a {@link JsonObject} representing the PCG
	 */
	public JsonObject toJson() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Type_Comptable tc : typesComptables) {
			if (tc != null) {
				arrayBuilder.add(tc.toJson());
			}
		}
		builder.add("typesComptables", arrayBuilder);

		return builder.build();
	}

	@Override
	public String toString() {
		return "Pcg{" +
				       "typesCount=" + (typesComptables != null ? typesComptables.size() : 0) +
				       ", indexedItems=" + codeIndex.size() +
				       '}';
	}
}