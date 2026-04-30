package com.jtissdev_API.utils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Chargeur de données pour les tests permettant de lire depuis les ressources
 * ou un dossier externe 'data'.
 *
 * @author J.Tiss
 * @email jtissdev@gmail.com
 * @since 1.0.0
 * @version 1.1.0
 */
public class TestDataLoader {

	private static final String EXTERNAL_DATA_FOLDER = "data";

	/**
	 * Charge un fichier JSON depuis le dossier 'src/main/resources'.
	 * Utile pour les fichiers de structure par défaut.
	 *
	 * @param fileName Nom du fichier (ex: "pcg_base.json")
	 * @return JsonArray contenant les données
	 */
	public static JsonArray loadFromResources(String fileName) {
		try (InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(fileName)) {
			if (is == null) {
				throw new RuntimeException("[Resources] Fichier introuvable : " + fileName);
			}
			try (JsonReader reader = Json.createReader(is)) {
				return reader.readArray();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erreur de lecture Resource : " + fileName, e);
		}
	}

	/**
	 * Charge un fichier JSON depuis le dossier 'data' à la racine du projet.
	 * Utile pour tester des fichiers de travail spécifiques ou d'installation.
	 *
	 * @param fileName Nom du fichier (ex: "mon_travail.json")
	 * @return JsonArray contenant les données
	 */
	public static JsonArray loadFromExternalData(String fileName) {
		Path path = Paths.get(EXTERNAL_DATA_FOLDER, fileName);

		if (!Files.exists(path)) {
			throw new RuntimeException("[External] Fichier introuvable dans /data/ : " + fileName);
		}

		try (InputStream is = Files.newInputStream(path);
		     JsonReader reader = Json.createReader(is)) {
			return reader.readArray();
		} catch (Exception e) {
			throw new RuntimeException("Erreur de lecture FileSystem : " + path.toAbsolutePath(), e);
		}
	}
}