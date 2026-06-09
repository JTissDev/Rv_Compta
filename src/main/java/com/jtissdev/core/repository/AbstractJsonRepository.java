package com.jtissdev.core.repository;

import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import jakarta.json.*;
import jakarta.json.stream.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * Implémentation de base pour la persistance axée sur un fichier JSON unique.
 * <p>
 * Centralise l'accès physique, la sérialisation, la désérialisation, et évite
 * la duplication de la tuyauterie I/O entre les différents dépôts.
 * </p>
 *
 * @param <T>  Le type de l'objet géré.
 * @param <ID> Le type de l'identifiant.
 *
 * @author J.Tiss
 * @email jtissdev@gmail.com
 * @version 1.0.0
 * @since 2.1.0
 */
public abstract class AbstractJsonRepository<T, ID> implements CrudRepository<T, ID> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected final File liveFile;
	protected final JsonWriterFactory writerFactory;

	// Fonctions de couplage lâche pour l'adaptation des DTOs
	protected final Function<T, ID> idExtractor;
	protected final Function<JsonObject, T> jsonMapper;
	protected final Function<T, JsonObject> jsonUnmapper;

	protected AbstractJsonRepository(File liveFile,
	                                 Function<T, ID> idExtractor,
	                                 Function<JsonObject, T> jsonMapper,
	                                 Function<T, JsonObject> jsonUnmapper) {
		this.liveFile = liveFile;
		this.idExtractor = idExtractor;
		this.jsonMapper = jsonMapper;
		this.jsonUnmapper = jsonUnmapper;
		this.writerFactory = Json.createWriterFactory(Map.of(JsonGenerator.PRETTY_PRINTING, true));
	}

	@Override
	public List<T> findAll() {
		if (!liveFile.exists() || liveFile.length() == 0) {
			return new ArrayList<>();
		}
		List<T> list = new ArrayList<>();
		try (InputStream is = new FileInputStream(liveFile);
		     JsonReader reader = Json.createReader(is)) {
			JsonArray array = reader.readArray();
			for (JsonObject obj : array.getValuesAs(JsonObject.class)) {
				list.add(jsonMapper.apply(obj));
			}
		} catch (Exception e) {
			log.error("[Persistence] Impossible de lire le fichier cible : {}", liveFile.getName(), e);
		}
		return list;
	}

	@Override
	public Optional<T> findById(ID id) {
		if (id == null) return Optional.empty();
		return findAll().stream()
				       .filter(entity -> id.equals(idExtractor.apply(entity)))
				       .findFirst();
	}

	@Override
	public T save(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Impossible de sauvegarder une entité nulle.");
		}
		List<T> allElements = findAll();
		ID targetId = idExtractor.apply(entity);

		// Remplacement si l'élément existe déjà, sinon ajout
		int index = -1;
		for (int i = 0; i < allElements.size(); i++) {
			if (targetId.equals(idExtractor.apply(allElements.get(i)))) {
				index = i;
				break;
			}
		}

		if (index != -1) {
			allElements.set(index, entity);
		} else {
			allElements.add(entity);
		}

		writeAllToStorage(allElements);
		return entity;
	}

	@Override
	public void deleteById(ID id) {
		if (id == null) return;
		List<T> allElements = findAll();
		boolean removed = allElements.removeIf(entity -> id.equals(idExtractor.apply(entity)));
		if (removed) {
			writeAllToStorage(allElements);
		}
	}

	// =========================================================
	// == OUTILS INTERNES                                     ==
	// =========================================================

	protected void writeAllToStorage(List<T> elements) {
		ensureParentDirectoryExists();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (T item : elements) {
			arrayBuilder.add(jsonUnmapper.apply(item));
		}

		try (FileOutputStream fos = new FileOutputStream(liveFile);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     JsonWriter writer = this.writerFactory.createWriter(osw)) {
			writer.writeArray(arrayBuilder.build());
		} catch (IOException e) {
			log.error("[Persistence] Échec d'écriture JSON dans le fichier : {}", liveFile.getName(), e);
		}
	}

	protected void ensureParentDirectoryExists() {
		File parentDir = liveFile.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			if (parentDir.mkdirs()) {
				log.info("[Persistence] Création de l'arborescence manquante : {}", parentDir.getAbsolutePath());
			}
		}
	}
}