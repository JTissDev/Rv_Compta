package com.jtissdev.features.core.mapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.io.InputStream;
public interface DataMapper<T> {

	/**
	 * Maps an input stream containing raw JSON into a domain DTO.
	 *
	 * @param is The input stream to parse.
	 * @return The fully populated domain DTO.
	 */
	T toDto(InputStream is);

}
