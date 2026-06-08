package com.jtissdev.features.referential.mapper;

import com.jtissdev.features.core.mapper.AbstractJsonMapper;
import com.jtissdev.features.referential.dto.ReferentialCoreDTO;
import com.jtissdev.features.referential.dto.OperationStatus;
import com.jtissdev.features.referential.dto.PaymentMethod;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper engine for referential data.
 * <p>
 * This class provides methods to map shared referential data (like OperationStatus
 * and PaymentMethod) from JSON sources into a {@link ReferentialCoreDTO}.
 *
 * @author J.Tiss
 * @since 0.2.0
 * @version 2.0
 */
@Component
public class ReferentialDataMapper extends AbstractJsonMapper {

	// =========================================================
	// == PUBLIC METHODS                                      ==
	// =========================================================

	/**
	 * Converts a JSON input stream into a list of OperationStatus objects.
	 *
	 * @param inputStream source of the JSON data
	 * @return a list of {@link OperationStatus}
	 * @since 0.6.0
	 */
	public List<OperationStatus> toOperationStatusList(InputStream inputStream) {
		List<OperationStatus> statuses = new ArrayList<>();
		JsonArray array = extractJsonArray(inputStream);

		for (JsonObject obj : array.getValuesAs(JsonObject.class)) {
			statuses.add(new OperationStatus(obj)); // Utilise le constructeur propre
		}
		logger.info("Operation Statuses mapped successfully. Items count: {}", statuses.size());
		return statuses;
	}

	/**
	 * Converts a JSON input stream into a list of PaymentMethod objects.
	 *
	 * @param inputStream source of the JSON data
	 * @return a list of {@link PaymentMethod} objects
	 * @since 0.6.0
	 */
	public List<PaymentMethod> toPaymentMethodList(InputStream inputStream) {
		List<PaymentMethod> methods = new ArrayList<>();
		JsonArray array = extractJsonArray(inputStream);

		for (JsonObject obj : array.getValuesAs(JsonObject.class)) {
			methods.add(new PaymentMethod(obj)); // Utilise le constructeur propre
		}
		logger.info("Payment Methods mapped successfully. Items count: {}", methods.size());
		return methods;
	}


	/**
	 * Loads operation statuses from a JSON input stream and adds them to the DTO.
	 *
	 * @param inputStream source of the JSON data
	 * @param targetDTO   the DTO container to fill
	 * @throws Exception if an error occurs during parsing or reading
	 *
	 * @since 0.2.0
	 * @deprecated Use {@link #toOperationStatusList(InputStream)} and set the list in the DTO directly
	 */
	@Deprecated( forRemoval = true, since = "0.6.0")
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
						                         .setName(obj.getString("nom", null))
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
	 * @deprecated Use {@link #toPaymentMethodList(InputStream)} and set the list in the DTO directly
	 */
	@Deprecated( forRemoval = true, since = "0.6.0")
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
						                       .setName(obj.getString("nom", null))
						                       .setDescription(obj.getString("description", null));

				targetDTO.addPaymentMethod(method);
			}
		}
	}
}