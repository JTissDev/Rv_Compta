package com.jtissdev.engine.loader;

import com.jtissdev.features.compta.dto.JournalDTO;
import com.jtissdev.features.compta.dto.OperationDTO;
import com.jtissdev.features.compta.dto.MovementDTO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
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
			return new JournalDTO(reader.readObject());
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
		JournalDTO journal = new JournalDTO(json);


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
		OperationDTO operation = new OperationDTO(json);


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