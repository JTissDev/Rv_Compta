package com.jtissdev.features.pcp.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 * Represents a generic contact within the application.
 * <p>
 * A {@code Contacts} instance is intended to be a simple DTO used to
 * transfer contact-related information between application layers or
 * with external systems (for example: REST APIs).
 * <p>
 * This class exposes a JSON representation via {@link #toJson()} and
 * can be rebuilt from a JSON representation via {@link #fromJson(JsonObject)}.
 *
 * The technical identifier ({@code id}) is considered an internal field and
 * is not exposed in the JSON representation by default.
 *
 * Typical use cases include:
 * <ul>
 *     <li>storing customer contact information;</li>
 *     <li>referencing supplier or partner contacts;</li>
 *     <li>exchanging simple contact data structures across services.</li>
 * </ul>
 *
 * All fields are optional; callers can choose which attributes to set.
 *
 * @author jtiss
 * @since 0.3
 * @version 1.4.0
 */
public class Contacts {

    // =========================================================
    // == FIELDS                                              ==
    // =========================================================

    /**
     * Technical identifier used by the database.
     * <p>
     * This field is not serialized in the JSON representation.
     *
     * @since 0.3
     */
    private Integer id;

    /**
     * Contact's display name.
     * <p>
     * This can be a person name (e.g. "Jane Doe") or an entity name
     * (e.g. "ACME Corp. - Sales Department").
     *
     * @since 0.3
     */
    private String name;

    /**
     * Primary email address of the contact.
     *
     * @since 0.3
     */
    private String email;

    /**
     * Primary phone number of the contact.
     * <p>
     * It is recommended to store the number in international
     * format when possible (e.g. {@code +1 555 123 4567}).
     *
     * @since 0.3
     */
    private String phone;

    /**
     * Optional mobile (cell) phone number of the contact.
     *
     * @since 0.3
     */
    private String mobile;

    /**
     * Optional company or organization name the contact belongs to.
     *
     * @since 0.3
     */
    private String company;

    /**
     * Optional role or job title of the contact inside the company.
     * <p>
     * For example: {@code "Accountant"}, {@code "Sales Manager"}, etc.
     *
     * @since 0.3
     */
    private String role;

    /**
     * Optional street address line of the contact.
     *
     * @since 0.3
     */
    private String address;

    /**
     * Optional ZIP or postal code of the contact.
     *
     * @since 0.3
     */
    private String zipCode;

    /**
     * Optional city name for the contact's address.
     *
     * @since 0.3
     */
    private String city;

    /**
     * Optional country name or ISO country code for the contact.
     *
     * @since 0.3
     */
    private String country;

    /**
     * Optional free-text notes for the contact.
     * <p>
     * This field can be used to store any additional information that
     * does not fit other structured fields.
     *
     * @since 0.3
     */
    private String notes;

    // =========================================================
    // == CONSTRUCTORS                                        ==
    // =========================================================

    /**
     * Creates an empty {@code Contacts} instance.
     * <p>
     * All fields are initialized to {@code null}.
     *
     * @since 0.3
     */
    public Contacts() {
        // Default constructor
    }

    /**
     * Constructs a new {@code Contacts} instance using the provided {@code JsonObject}.
     * <p>
     * This constructor initializes an empty {@code Contacts} instance and populates
     * its fields from the given JSON object using the {@code fromJson} method.
     *
     * @param json the JSON object containing the contact data; must not be {@code null}
     *
     * @since 0.4
     */
    public Contacts( JsonObject json ) {
        this();
        if (json.containsKey("id") && !json.isNull("id")) {
            this.setId(json.getInt("id"));
        }
        if (json.containsKey("name")) {
            this.setName(json.getString("name", null));
        }
        if (json.containsKey("email")) {
            this.setEmail(json.getString("email", null));
        }
        if (json.containsKey("phone")) {
            this.setPhone(json.getString("phone", null));
        }
        if (json.containsKey("mobile")) {
            this.setMobile(json.getString("mobile", null));
        }
        if (json.containsKey("company")) {
            this.setCompany(json.getString("company", null));
        }
        if (json.containsKey("role")) {
            this.setRole(json.getString("role", null));
        }
        if (json.containsKey("address")) {
            this.setAddress(json.getString("address", null));
        }
        if (json.containsKey("zipCode")) {
            this.setZipCode(json.getString("zipCode", null));
        }
        if (json.containsKey("city")) {
            this.setCity(json.getString("city", null));
        }
        if (json.containsKey("country")) {
            this.setCountry(json.getString("country", null));
        }
        if (json.containsKey("notes")) {
            this.setNotes(json.getString("notes", null));
        }
    }

    // =========================================================
    // == ACCESSORS & MUTATORS (FLUENT API)                   ==
    // =========================================================

    /**
     * Returns the technical identifier.
     *
     * @return the technical identifier, or {@code null} if not set
     *
     * @since 0.3
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the technical identifier for the contact.
     *
     * @param id the new technical identifier to set; may be {@code null}
     * @return the updated {@code Contacts} instance
     * 
     * @since 0.3
     * @version 1.1
     */
    public Contacts setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Returns the contact's display name.
     *
     * @return the display name, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the contact's display name.
     * This method updates the display name of the contact. 
     * Use this to modify or set the name of a contact entity.
     *
     * @param name the new display name for the contact; must not be null or empty
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns the primary email address of the contact.
     *
     * @return the email address, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the primary email address of the contact.
     * This method allows you to assign or update the email address for a contact.
     * It ensures the email is stored correctly, enabling future retrieval or communication needs.
     *
     * @param email new email address
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Returns the primary phone number of the contact.
     *
     * @return the phone number, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets the primary phone number of the contact.
     * <p>
     * This method allows setting or updating the primary phone number
     * for the contact instance.
     *
     * @param phone new phone number
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Returns the mobile phone number of the contact.
     *
     * @return the mobile phone number, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * Sets the mobile phone number of the contact.
     * <p>
     * This method updates or sets the mobile phone number for a contact entity.
     * Version {@code 1.1} ensures consistent storage of the mobile phone number.
     *
     * @param mobile new mobile phone number
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    /**
     * Returns the company or organization name.
     *
     * @return the company name, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getCompany() {
        return this.company;
    }

    /**
     * Sets the company or organization name.
     *
     * This method updates or assigns the company/organization name associated with a contact.
     * Use this to modify or set the company details for the contact entity.
     *
     * @param company new company name
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setCompany(String company) {
        this.company = company;
        return this;
    }

    /**
     * Returns the role or job title of the contact.
     *
     * @return the role, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets the role or job title of the contact.
     * This method updates the role or job title of a contact and reflects version 1.1 of the function.
     *
     * @param role new role or job title
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setRole(String role) {
        this.role = role;
        return this;
    }

    /**
     * Returns the street address of the contact.
     *
     * @return the street address, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the street address of the contact.
     * This method updates the address of the contact and ensures
     * proper storage for the contact's street address.
     * It reflects version 1.1 of the function, providing better clarity.
     *
     * @param address new street address
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Returns the ZIP or postal code of the contact.
     *
     * @return the ZIP/postal code, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Sets the ZIP or postal code of the contact.
     *
     * This method updates the ZIP or postal code for the contact entity.
     * It ensures consistent storage and enables effective retrieval of the postal information.
     *
     * @param zipCode new ZIP/postal code
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    /**
     * Returns the city of the contact.
     *
     * @return the city name, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Sets the city of the contact.
     * This method updates the city name for the contact entity. 
     * It reflects version 1.1 of the function, ensuring proper handling and 
     * storage of city information, consistent with other contact details.
     *
     * @param city new city name
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Returns the country of the contact.
     *
     * @return the country name or code, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Sets the country of the contact.
     * This method updates the country field of the contact instance.
     * It allows for specifying the country name or ISO code associated with the contact.
     * Version 1.1 improves the documentation for better clarity and usability.
     *
     * @param country new country name or code
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * Returns the free-text notes associated with the contact.
     *
     * @return the notes, or {@code null} if not set
     *
     * @since 0.3
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Sets the free-text notes associated with the contact.
     * <p>
     * This method assigns or updates the notes for a contact entity.
     * Version 1.1 ensures better clarity and explanation for this functionality.
     *
     * @param notes new notes
     *
     * @since 0.3
     * @version 1.1
     */
    public Contacts setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    // =========================================================
    // == JSON SERIALIZATION                                  ==
    // =========================================================

    /**
     * Builds and returns a {@link JsonObject} representing this contact.
     * <p>
     * Only non-null scalar fields are included in the JSON object.
     * The technical identifier field ({@code id}) is intentionally omitted.
     *
     * @return a {@link JsonObject} representing this instance
     *
     * @since 0.3
     */
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // id is intentionally not serialized
        if (this.getName() != null) {
            builder.add("name", this.getName());
        }
        if (this.getEmail() != null) {
            builder.add("email", this.getEmail());
        }
        if (this.getPhone() != null) {
            builder.add("phone", this.getPhone());
        }
        if (this.getMobile() != null) {
            builder.add("mobile", this.getMobile());
        }
        if (this.getCompany() != null) {
            builder.add("company", this.getCompany());
        }
        if (this.getRole() != null) {
            builder.add("role", this.getRole());
        }
        if (this.getAddress() != null) {
            builder.add("address", this.getAddress());
        }
        if (this.getZipCode() != null) {
            builder.add("zipCode", this.getZipCode());
        }
        if (this.getCity() != null) {
            builder.add("city", this.getCity());
        }
        if (this.getCountry() != null) {
            builder.add("country", this.getCountry());
        }
        if (this.getNotes() != null) {
            builder.add("notes", this.getNotes());
        }

        return builder.build();
    }

    /**
     * Returns a string representation of this instance,
     * mainly intended for debugging and logging purposes.
     *
     * @return a string representing this {@code Contacts}
     *
     * @since 0.3
     * @version 1.4
     */
    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + this.getId() +
                ", name=" + this.getName() +
                ", email=" + this.getEmail() +
                ", phone=" + this.getPhone() +
                ", mobile=" + this.getMobile() +
                ", company=" + this.getCompany() +
                ", role=" + this.getRole() +
                ", address=" + this.getAddress() +
                ", zipCode=" + this.getZipCode() +
                ", city=" + this.getCity() +
                ", country=" + this.getCountry() +
                ", notes=" + this.getNotes() +
                '}';
    }


    /**
     * Creates a new {@code Contacts} instance from the given JSON object.
     * <p>
     * This method expects the same JSON structure as produced by {@link #toJson()}.
     * The {@code id} field is not read from JSON, as it is considered internal.
     *
     * @param json the JSON object to convert; must not be {@code null}
     * @return a new {@code Contacts} instance populated from the JSON object
     *
     * @since 0.3
     * @deprecated since 1.3
     */
    @Deprecated ( since = "1.4" , forRemoval = true)
    public static Contacts fromJson(JsonObject json) {
        Contacts contact = new Contacts();

        if (json.containsKey("name")) {
            contact.setName(json.getString("name", null));
        }
        if (json.containsKey("email")) {
            contact.setEmail(json.getString("email", null));
        }
        if (json.containsKey("phone")) {
            contact.setPhone(json.getString("phone", null));
        }
        if (json.containsKey("mobile")) {
            contact.setMobile(json.getString("mobile", null));
        }
        if (json.containsKey("company")) {
            contact.setCompany(json.getString("company", null));
        }
        if (json.containsKey("role")) {
            contact.setRole(json.getString("role", null));
        }
        if (json.containsKey("address")) {
            contact.setAddress(json.getString("address", null));
        }
        if (json.containsKey("zipCode")) {
            contact.setZipCode(json.getString("zipCode", null));
        }
        if (json.containsKey("city")) {
            contact.setCity(json.getString("city", null));
        }
        if (json.containsKey("country")) {
            contact.setCountry(json.getString("country", null));
        }
        if (json.containsKey("notes")) {
            contact.setNotes(json.getString("notes", null));
        }

        return contact;
    }

}