package com.jtissdev_API.features.PCP.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Contacts} DTO.
 * Verifies data integrity and JSON serialization/deserialization.
 *
 * @author J.Tiss
 * @since 0.4
 * @version 1.1.0
 */
class ContactsTest {

	// =========================================================
	// == TEST CONSTRUCTORS                                   ==
	// =========================================================
	@Test
	@DisplayName("Test empty Constructor")
	void testEmptyConstructor() {
		Contacts contact = new Contacts();
		 assertNull(contact.getId());
		 assertNull(contact.getName());
		 assertNull(contact.getEmail());
		 assertNull(contact.getPhone());
		 assertNull(contact.getMobile());
		 assertNull(contact.getCompany());
		 assertNull(contact.getRole());
		 assertNull(contact.getAddress());
		 assertNull(contact.getZipCode());
		 assertNull(contact.getCity());
		 assertNull(contact.getCountry());
		 assertNull(contact.getNotes());
	}
	
	@Test
	@DisplayName("Test Constructor form JsonObject")
	void testConstructorFromJson() {
		JsonObject json = Json.createObjectBuilder()
				                  .add("name", "Alice Cooper")
				                  .add("email", "alice.cooper@example.com")
				                  .add("phone", "555-1234")
				                  .add("mobile", "555-5678")
				                  .add("company", "Cooper LLC")
				                  .add("role", "Director")
				                  .add("address", "789 Main Street")
				                  .add("zipCode", "12345")
				                  .add("city", "Springfield")
				                  .add("country", "USA")
				                  .add("notes", "Potential business partner")
				                  .build();

		Contacts contact = new Contacts(json);

		assertAll("Contact object validation",
				() -> assertEquals("Alice Cooper", contact.getName()),
				() -> assertEquals("alice.cooper@example.com", contact.getEmail()),
				() -> assertEquals("555-1234", contact.getPhone()),
				() -> assertEquals("555-5678", contact.getMobile()),
				() -> assertEquals("Cooper LLC", contact.getCompany()),
				() -> assertEquals("Director", contact.getRole()),
				() -> assertEquals("789 Main Street", contact.getAddress()),
				() -> assertEquals("12345", contact.getZipCode()),
				() -> assertEquals("Springfield", contact.getCity()),
				() -> assertEquals("USA", contact.getCountry()),
				() -> assertEquals("Potential business partner", contact.getNotes())
		);
	}

	// =========================================================
	// == TEST METHODS                                        ==
	// =========================================================
	@Test
	void testToJson_withAllFieldsPopulated() {
		Contacts contact = new Contacts(
				1L,
				"John Doe",
				"john.doe@example.com",
				"123-456-7890",
				"987-654-3210",
				"Example Inc.",
				"Manager",
				"123 Street",
				"456789",
				"New York",
				"USA",
				"Important client"
		);

		JsonObject expectedJson = Json.createObjectBuilder()
				                          .add("name", "John Doe")
				                          .add("email", "john.doe@example.com")
				                          .add("phone", "123-456-7890")
				                          .add("mobile", "987-654-3210")
				                          .add("company", "Example Inc.")
				                          .add("role", "Manager")
				                          .add("address", "123 Street")
				                          .add("zipCode", "456789")
				                          .add("city", "New York")
				                          .add("country", "USA")
				                          .add("notes", "Important client")
				                          .build();

		assertEquals(expectedJson, contact.toJson());
	}

	@Test
	void testToJson_withSomeFieldsNull() {
		Contacts contact = new Contacts(
				1L,
				"Jane Smith",
				"jane.smith@example.com",
				"123-123-1234",
				null,
				"ABC Corp.",
				null,
				"456 Avenue",
				null,
				"Los Angeles",
				"USA",
				null
		);

		JsonObject expectedJson = Json.createObjectBuilder()
				                          .add("name", "Jane Smith")
				                          .add("email", "jane.smith@example.com")
				                          .add("phone", "123-123-1234")
				                          .add("company", "ABC Corp.")
				                          .add("address", "456 Avenue")
				                          .add("city", "Los Angeles")
				                          .add("country", "USA")
				                          .build();

		assertEquals(expectedJson, contact.toJson());
	}

	@Test
	void testToJson_withAllFieldsNull() {
		Contacts contact = new Contacts();

		JsonObject expectedJson = Json.createObjectBuilder().build();

		assertEquals(expectedJson, contact.toJson());
	}

	@Test
	void testToJson_withOnlyNameAndEmail() {
		Contacts contact = new Contacts(
				null,
				"Robert Brown",
				"robert.brown@example.com",
				null,
				null
		);

		JsonObject expectedJson = Json.createObjectBuilder()
				                          .add("name", "Robert Brown")
				                          .add("email", "robert.brown@example.com")
				                          .build();

		assertEquals(expectedJson, contact.toJson());
	}

	@Test
	@DisplayName("Should validate getter and setter logic for all fields")
	void testGettersAndSetters() {
		Contacts contact = new Contacts();

		contact.setName("John Doe");
		contact.setEmail("john.doe@example.com");
		contact.setCompany("Tech Corp");
		contact.setCity("New York");

		assertAll("Contact fields validation",
				() -> assertEquals("John Doe", contact.getName()),
				() -> assertEquals("john.doe@example.com", contact.getEmail()),
				() -> assertEquals("Tech Corp", contact.getCompany()),
				() -> assertEquals("New York", contact.getCity())
		);
	}

	@Test
	@DisplayName("Should convert contact to JsonObject excluding the technical ID")
	void testToJsonSerialization() {
		Contacts contact = new Contacts();
		contact.setId(100L); // ID should be ignored in JSON
		contact.setName("Jane Smith");
		contact.setRole("Manager");

		JsonObject json = contact.toJson();

		assertAll("JSON content validation",
				() -> assertFalse(json.containsKey("id"), "JSON should not contain the technical ID"),
				() -> assertEquals("Jane Smith", json.getString("name")),
				() -> assertEquals("Manager", json.getString("role"))
		);
	}
}