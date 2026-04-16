package com.jtissdev_API.engine.loader;

import com.jtissdev_API.features.core.dto.ReferentialCoreDTO;
import com.jtissdev_API.features.core.dto.referential.OperationStatus;
import com.jtissdev_API.features.core.dto.referential.PaymentMethod;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.InputStream;

/**
 * Loader engine for referential data.
 * <p>
 * This class provides methods to load shared referential data (like OperationStatus
 * and PaymentMethod) from JSON sources into a {@link ReferentialCoreDTO}.
 *
 * @author J.Tiss
 * @since 0.2.0
 * @version 1.1.0
 */
public class ReferentialDataLoader {

	// =========================================================
	// == PUBLIC METHODS                                      ==
	// =========================================================

	/**
	 * Loads operation statuses from a JSON input stream and adds them to the DTO.
	 *
	 * @param inputStream source of the JSON data
	 * @param targetDTO   the DTO container to fill
	 * @throws Exception if an error occurs during parsing or reading
	 *
	 * @since 0.2.0
	 */
	public void loadOperationStatuses(InputStream inputStream, ReferentialCoreDTO targetDTO) throws Exception {
		if (inputStream == null || targetDTO == null) {
			return;
		}

		try (JsonReader reader = Json.createReader(inputStream)) {
			JsonArray array = reader.readArray();

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);

				OperationStatus status = new OperationStatus()
						                         .setCode(obj.getString("code", null))
						                         .setNom(obj.getString("nom", null))
						                         .setColor(obj.getString("color", null));

				targetDTO.addOperationStatus(status);
			}
		}
	}

	/**
	 * Loads payment methods from a JSON input stream and adds them to the DTO.
	 *
	 * @param inputStream source of the JSON data
	 * @param targetDTO   the DTO container to fill
	 * @throws Exception if an error occurs during parsing or reading
	 *
	 * @since 0.2.0
	 */
	public void loadPaymentMethods(InputStream inputStream, ReferentialCoreDTO targetDTO) throws Exception {
		if (inputStream == null || targetDTO == null) {
			return;
		}

		try (JsonReader reader = Json.createReader(inputStream)) {
			JsonArray array = reader.readArray();

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);

				PaymentMethod method = new PaymentMethod()
						                       .setCode(obj.getString("code", null))
						                       .setNom(obj.getString("nom", null))
						                       .setDescription(obj.getString("description", null));

				targetDTO.addPaymentMethod(method);
			}
		}
	}
}