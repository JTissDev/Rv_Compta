package com.jtissdev_API.features.PCP.dto;

import com.jtissdev_API.features.core.dto.referential.OperationStatusTest;
import com.jtissdev_API.utils.TestResultLogger;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ContactsTest class is designed for testing the functionality
 * of the Contacts-related operations or logic within the application.
 * It contains methods to verify the behavior, correctness, and
 * robustness of the Contacts implementation.
 * <p>
 * This class is typically part of a test suite and is used to ensure
 * that the Contacts feature behaves as expected under various conditions.
 *
 * @author jtiss
 * @version 1.1
 * @see Contacts
 * @since 0.4
 */
@DisplayName("Contacts Test")
@ExtendWith(TestResultLogger.class)
public class ContactsTest {

	private static final Logger logger = LoggerFactory.getLogger(ContactsTest.class);

	private static final Integer ID = 100;
	private static final String NAME = "John Doe";
	private static final String EMAIL = "john.doe@example.com";
	private static final String PHONE = "+33612345678";
	private static final String MOBILE = "+33712345678";
	private static final String COMPANY = "JtissDev Corp";
	private static final String ROLE = "Developer";
	private static final String ADDRESS = "123 Java Street";
	private static final String ZIPCODE = "75000";
	private static final String CITY = "Paris";
	private static final String COUNTRY = "France";
	private static final String NOTES = "Standard testing contact";

	private static JsonObject contactJson;

	/**
	 * Sets up the required JSON object for the contact used in testing.
	 * This method is annotated with @BeforeAll, indicating that it is
	 * executed before All test method in the test class. It initializes
	 * a JsonObject representing a contact with predefined attributes such as
	 * name, email, phone, mobile, company, role, address, zip code, city, country,
	 * and notes. The purpose of this setup is to provide a consistent and reusable
	 * JSON representation of a contact for the various test cases in the test class.
	 * <p>
	 * This method facilitates testing by ensuring that each test starts
	 * with the same base data for the contact object.
	 */
	@BeforeAll
	@DisplayName("Json Object for Contact Object")
	static void setUp() {
		contactJson = Json.createObjectBuilder()
				              .add("nameOnly", Json.createObjectBuilder()
						                               .add("name", NAME).build())
				              .add("emailOnly", Json.createObjectBuilder()
						                                .add("email", EMAIL).build())
				              .add("phoneOnly", Json.createObjectBuilder()
						                                .add("phone", PHONE).build())
				              .add("mobileOnly", Json.createObjectBuilder()
						                                 .add("mobile", MOBILE).build())
				              .add("companyOnly", Json.createObjectBuilder()
						                                  .add("company", COMPANY).build())
				              .add("roleOnly", Json.createObjectBuilder()
						                               .add("role", ROLE).build())
				              .add("addressOnly", Json.createObjectBuilder()
						                                  .add("address", ADDRESS).build())
				              .add("zipCodeOnly", Json.createObjectBuilder()
						                                  .add("zipCode", ZIPCODE).build())
				              .add("cityOnly", Json.createObjectBuilder()
						                               .add("city", CITY).build())
				              .add("countryOnly", Json.createObjectBuilder()
						                                  .add("country", COUNTRY).build())
				              .add("notesOnly", Json.createObjectBuilder()
						                                .add("notes", NOTES).build())
				              .add("idAndName", Json.createObjectBuilder()
						                                .add("id", ID)
						                                .add("name", NAME).build())
				              .add("nameAndEmail", Json.createObjectBuilder()
						                                   .add("name", NAME)
						                                   .add("email", EMAIL).build())
				              .add("idNameAndEmail", Json.createObjectBuilder()
						                                     .add("id", ID)
						                                     .add("name", NAME)
						                                     .add("email", EMAIL).build())
				              .add("allFields", Json.createObjectBuilder()
						                                .add("id", ID)
						                                .add("name", NAME)
						                                .add("email", EMAIL)
						                                .add("phone", PHONE)
						                                .add("mobile", MOBILE)
						                                .add("company", COMPANY)
						                                .add("role", ROLE)
						                                .add("address", ADDRESS)
						                                .add("zipCode", ZIPCODE)
						                                .add("city", CITY)
						                                .add("country", COUNTRY)
						                                .add("notes", NOTES)
						                                .build())
				              .build();

	}


	@Test
	@DisplayName("Test Empty Constructor")
	public void testEmptyConstructor() {
		// Given & When
		Contacts contact = new Contacts();

		// Then
		assertAll("Empty constructor state validation",
				() -> assertNull(contact.getId(), "Internal ID should be null"),
				() -> assertNull(contact.getName(), "Name should be null"),
				() -> assertNull(contact.getEmail(), "Email should be null"),
				() -> assertNull(contact.getPhone(), "Phone should be null"),
				() -> assertNull(contact.getMobile(), "Mobile should be null"),
				() -> assertNull(contact.getCompany(), "Company should be null"),
				() -> assertNull(contact.getRole(), "Role should be null"),
				() -> assertNull(contact.getAddress(), "Address should be null"),
				() -> assertNull(contact.getZipCode(), "Zip code should be null"),
				() -> assertNull(contact.getCity(), "City should be null"),
				() -> assertNull(contact.getCountry(), "Country should be null"),
				() -> assertNull(contact.getNotes(), "Notes should be null")
		);
	}

	@Test
	@DisplayName("Test JSON Constructor")
	public void testJSONConstructor() {
		Contacts contact = new Contacts(contactJson.getJsonObject("allFields"));

		assertAll("JSON constructor mapping validation",
				() -> assertEquals(ID, contact.getId(), "Internal ID mapping failed"),
				() -> assertEquals(NAME, contact.getName(), "Name mapping failed"),
				() -> assertEquals(EMAIL, contact.getEmail(), "Email mapping failed"),
				() -> assertEquals(PHONE, contact.getPhone(), "Phone mapping failed"),
				() -> assertEquals(MOBILE, contact.getMobile(), "Mobile mapping failed"),
				() -> assertEquals(COMPANY, contact.getCompany(), "Company mapping failed"),
				() -> assertEquals(ROLE, contact.getRole(), "Role mapping failed"),
				() -> assertEquals(ADDRESS, contact.getAddress(), "Address mapping failed"),
				() -> assertEquals(ZIPCODE, contact.getZipCode(), "ZipCode mapping failed"),
				() -> assertEquals(CITY, contact.getCity(), "City mapping failed"),
				() -> assertEquals(COUNTRY, contact.getCountry(), "Country mapping failed"),
				() -> assertEquals(NOTES, contact.getNotes(), "Notes mapping failed")
		);
	}


	/**
	 * Tests all mutators (setters) and accessors (getters) to ensure
	 * data integrity throughout the object's lifecycle.
	 */
	@Test
	@DisplayName("Fluent API : setters should return this instance")
	void testFluentSetters() {
		// Given
		Contacts contact = new Contacts();

		Contacts result = contact
				                  .setName(NAME)
				                  .setEmail(EMAIL)
				                  .setPhone(PHONE)
				                  .setMobile(MOBILE)
				                  .setCompany(COMPANY)
				                  .setRole(ROLE)
				                  .setAddress(ADDRESS)
				                  .setZipCode(ZIPCODE)
				                  .setCity(CITY)
				                  .setCountry(COUNTRY)
				                  .setNotes(NOTES);

		// Then
		assertAll("Fluent API validation",
				() -> assertSame(contact, result, "Setter must return the same instance"),
				() -> assertEquals(NAME, contact.getName()),
				() -> assertEquals(EMAIL, contact.getEmail()),
				() -> assertEquals(PHONE, contact.getPhone()),
				() -> assertEquals(MOBILE, contact.getMobile()),
				() -> assertEquals(COMPANY, contact.getCompany()),
				() -> assertEquals(ROLE, contact.getRole()),
				() -> assertEquals(ADDRESS, contact.getAddress()),
				() -> assertEquals(ZIPCODE, contact.getZipCode()),
				() -> assertEquals(CITY, contact.getCity()),
				() -> assertEquals(COUNTRY, contact.getCountry()),
				() -> assertEquals(NOTES, contact.getNotes())
		);
	}

	/**
	 * Tests the {@link Contacts#toJson()} method.
	 * <p>
	 * Verifies that all non-null fields are correctly serialized and that
	 * the technical ID is intentionally omitted from the final JSON.
	 * </p>
	 */
	@Test
	@DisplayName("toJson: should serialize all fields except the technical ID")
	void testToJsonSerialization() {
		// Given
		Contacts contact = new Contacts(contactJson.getJsonObject("nameAndEmail"))
				                   .setCity(CITY);

		// When
		JsonObject json = contact.toJson();

		// Then
		assertAll("JSON serialization validation",
				() -> assertFalse(json.containsKey("id"), "Technical ID must be excluded from JSON"),
				() -> assertEquals(NAME, json.getString("name"), "Name serialization failed"),
				() -> assertEquals(EMAIL, json.getString("email"), "Email serialization failed"),
				() -> assertEquals(CITY, json.getString("city"), "City serialization failed"),
				() -> assertEquals(3, json.size(), "JSON should contain exactly 3 keys (name, email, city)")
		);
	}

	/**
	 * Tests the {@link Contacts#toString()} implementation.
	 * <p>
	 * Ensures that the string representation contains the class name
	 * and the key field values for debugging purposes.
	 * </p>
	 */
	@Test
	@DisplayName("toString: should contain class name and field values")
	void testToString() {
		// Given
		Contacts contact = new Contacts(contactJson.getJsonObject("nameAndEmail"));

		// When
		String result = contact.toString();

		// Then
		assertAll("toString content validation",
				() -> assertThat(result).contains("Contacts"),
				() -> assertThat(result).contains("name="+NAME),
				() -> assertThat(result).contains("email="+EMAIL)
		);
	}
}
