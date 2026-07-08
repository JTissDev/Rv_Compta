package com.jtissdev.core.exception;


/**
 * Exception thrown to indicate an issue encountered during the mapping of JSON data.
 * Typically used to signal an error while converting JSON content to an object
 * or vice versa in a JSON serialization or deserialization process.
 * @author J.Tiss
 * @version 1.0.0
 * @since 0.6.0
 */
public class JsonMappingException extends RuntimeException {

	public JsonMappingException(String message) {
		super(message);
	}

	public JsonMappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
