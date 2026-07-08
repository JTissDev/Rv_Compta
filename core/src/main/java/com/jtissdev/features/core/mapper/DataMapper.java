package com.jtissdev.features.core.mapper;

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
