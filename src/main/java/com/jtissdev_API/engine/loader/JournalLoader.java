package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.compta.dto.JournalDTO;
import com.jtissdev_API.features.compta.dto.OperationDTO;
import com.jtissdev_API.features.compta.dto.MovementDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Service responsible for loading accounting journal data from JSON sources.
 * <p>
 * This loader ensures that existing accounting records (Journal, Operations, Movements)
 * can be rehydrated into the system at startup. It uses Jakarta JSON for parsing.
 * </p>
 *
 * @author J.Tiss
 * @since 0.4.0
 * @version 1.0.0
 */
@Component
public class JournalLoader {

	/**
	 * Loads a {@link JournalDTO} from an input stream containing JSON data.
	 *
	 * @param is the {@link InputStream} of the JSON file
	 * @return a fully populated {@link JournalDTO}, or {@code null} if the stream is invalid
	 * @since 0.4.0
	 */
	public JournalDTO loadJournal(InputStream is) {
		if (is == null) {
			return null;
		}

		try (JsonReader reader = Json.createReader(is)) {
			JsonObject jsonJournal = reader.readObject();
			return mapToJournalDTO(jsonJournal);
		} catch (Exception e) {
			// Log error in a real scenario
			return null;
		}
	}

	/**
	 * Maps a {@link JsonObject} to a {@link JournalDTO}.
	 *
	 * @param json the JSON object representing the journal
	 * @return a populated {@link JournalDTO}
	 * @since 0.4.0
	 */
	private JournalDTO mapToJournalDTO(JsonObject json) {
		JournalDTO journal = new JournalDTO()
				                     .setId(json.containsKey("id") ? (long) json.getInt("id") : null)
				                     .setName(json.getString("nom", null))
				                     .setStartDate(parseDate(json.getString("dateDebut", null)))
				                     .setEndDate(parseDate(json.getString("dateFin", null)))
				                     .setJournalTypeCode(json.getString("typeJournalCode", null));

		if (json.containsKey("operations")) {
			JsonArray opsArray = json.getJsonArray("operations");
			opsArray.forEach(val -> {
				if (val instanceof JsonObject) {
					journal.addOperation(mapToOperationDTO((JsonObject) val));
				}
			});
		}

		return journal;
	}

	/**
	 * Maps a {@link JsonObject} to an {@link OperationDTO}.
	 *
	 * @param json the JSON object representing an operation
	 * @return a populated {@link OperationDTO}
	 * @since 0.4.0
	 */
	private OperationDTO mapToOperationDTO(JsonObject json) {
		OperationDTO operation = new OperationDTO()
				                         .setId(json.containsKey("id") ?  json.getInt("id") : null)
				                         .setDateOperation(parseDate(json.getString("dateOperation", null)))
				                         .setDateComptable(parseDate(json.getString("dateComptable", null)))
				                         .setLibelle(json.getString("libelle", null))
				                         .setReferenceDocument(json.getString("referenceDocument", null))
				                         .setDescriptif(json.getString("descriptif", null))
				                         .setStatutCode(json.getString("statutCode", null));

		if (json.containsKey("movements")) {
			JsonArray movArray = json.getJsonArray("movements");
			movArray.forEach(val -> {
				if (val instanceof JsonObject) {
					operation.addMovement(mapToMovementDTO((JsonObject) val));
				}
			});
		}

		return operation;
	}

	/**
	 * Maps a {@link JsonObject} to a {@link MovementDTO}.
	 *
	 * @param json the JSON object representing a movement line
	 * @return a populated {@link MovementDTO}
	 * @since 0.4.0
	 */
	private MovementDTO mapToMovementDTO(JsonObject json) {
		return new MovementDTO(json);

	}

	/**
	 * Helper to parse dates from JSON strings.
	 *
	 * @param dateStr the date string (ISO format)
	 * @return a {@link LocalDate} or {@code null}
	 * @since 0.4.0
	 */
	private LocalDate parseDate(String dateStr) {
		return (dateStr != null && !dateStr.isEmpty()) ? LocalDate.parse(dateStr) : null;
	}
}