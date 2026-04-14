package com.jtissdev_API.features.PCP.dto;

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
 * @since 1.1.0
 * @version 1.0.0
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
     * @since 1.1.0
     */
    private Long id;

    /**
     * Contact's display name.
     * <p>
     * This can be a person name (e.g. "Jane Doe") or an entity name
     * (e.g. "ACME Corp. - Sales Department").
     *
     * @since 1.1.0
     */
    private String name;

    /**
     * Primary email address of the contact.
     *
     * @since 1.1.0
     */
    private String email;

    /**
     * Primary phone number of the contact.
     * <p>
     * It is recommended to store the number in international
     * format when possible (e.g. {@code +1 555 123 4567}).
     *
     * @since 1.1.0
     */
    private String phone;

    /**
     * Optional mobile (cell) phone number of the contact.
     *
     * @since 1.1.0
     */
    private String mobile;

    /**
     * Optional company or organization name the contact belongs to.
     *
     * @since 1.1.0
     */
    private String company;

    /**
     * Optional role or job title of the contact inside the company.
     * <p>
     * For example: {@code "Accountant"}, {@code "Sales Manager"}, etc.
     *
     * @since 1.1.0
     */
    private String role;

    /**
     * Optional street address line of the contact.
     *
     * @since 1.1.0
     */
    private String address;

    /**
     * Optional ZIP or postal code of the contact.
     *
     * @since 1.1.0
     */
    private String zipCode;

    /**
     * Optional city name for the contact's address.
     *
     * @since 1.1.0
     */
    private String city;

    /**
     * Optional country name or ISO country code for the contact.
     *
     * @since 1.1.0
     */
    private String country;

    /**
     * Optional free-text notes for the contact.
     * <p>
     * This field can be used to store any additional information that
     * does not fit other structured fields.
     *
     * @since 1.1.0
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
     * @since 1.1.0
     */
    public Contacts() {
        // Default constructor
    }

    /**
     * Creates a {@code Contacts} instance with the most common
     * contact information.
     *
     * @param id      technical identifier used by the database
     * @param name    contact's display name
     * @param email   primary email address
     * @param phone   primary phone number
     * @param company company or organization name
     *
     * @since 1.1.0
     */
    public Contacts(Long id,
                    String name,
                    String email,
                    String phone,
                    String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
    }

    /**
     * Creates a fully initialized {@code Contacts} instance.
     *
     * @param id       technical identifier used by the database
     * @param name     contact's display name
     * @param email    primary email address
     * @param phone    primary phone number
     * @param mobile   mobile phone number
     * @param company  company or organization name
     * @param role     role or job title
     * @param address  street address line
     * @param zipCode  ZIP or postal code
     * @param city     city name
     * @param country  country name or ISO code
     * @param notes    free-text notes
     *
     * @since 1.1.0
     */
    public Contacts(Long id,
                    String name,
                    String email,
                    String phone,
                    String mobile,
                    String company,
                    String role,
                    String address,
                    String zipCode,
                    String city,
                    String country,
                    String notes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.company = company;
        this.role = role;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.notes = notes;
    }

    // =========================================================
    // == ACCESSORS & MUTATORS                                ==
    // =========================================================

    /**
     * Returns the technical identifier.
     *
     * @return the technical identifier, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the technical identifier.
     *
     * @param id new technical identifier
     *
     * @since 1.1.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the contact's display name.
     *
     * @return the display name, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the contact's display name.
     *
     * @param name new display name
     *
     * @since 1.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the primary email address of the contact.
     *
     * @return the email address, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the primary email address of the contact.
     *
     * @param email new email address
     *
     * @since 1.1.0
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the primary phone number of the contact.
     *
     * @return the phone number, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the primary phone number of the contact.
     *
     * @param phone new phone number
     *
     * @since 1.1.0
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the mobile phone number of the contact.
     *
     * @return the mobile phone number, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the mobile phone number of the contact.
     *
     * @param mobile new mobile phone number
     *
     * @since 1.1.0
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Returns the company or organization name.
     *
     * @return the company name, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company or organization name.
     *
     * @param company new company name
     *
     * @since 1.1.0
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Returns the role or job title of the contact.
     *
     * @return the role, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role or job title of the contact.
     *
     * @param role new role or job title
     *
     * @since 1.1.0
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the street address of the contact.
     *
     * @return the street address, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the street address of the contact.
     *
     * @param address new street address
     *
     * @since 1.1.0
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the ZIP or postal code of the contact.
     *
     * @return the ZIP/postal code, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the ZIP or postal code of the contact.
     *
     * @param zipCode new ZIP/postal code
     *
     * @since 1.1.0
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns the city of the contact.
     *
     * @return the city name, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the contact.
     *
     * @param city new city name
     *
     * @since 1.1.0
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the country of the contact.
     *
     * @return the country name or code, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the contact.
     *
     * @param country new country name or code
     *
     * @since 1.1.0
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the free-text notes associated with the contact.
     *
     * @return the notes, or {@code null} if not set
     *
     * @since 1.1.0
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the free-text notes associated with the contact.
     *
     * @param notes new notes
     *
     * @since 1.1.0
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
     * @since 1.1.0
     */
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // id is intentionally not serialized
        if (name != null) {
            builder.add("name", name);
        }
        if (email != null) {
            builder.add("email", email);
        }
        if (phone != null) {
            builder.add("phone", phone);
        }
        if (mobile != null) {
            builder.add("mobile", mobile);
        }
        if (company != null) {
            builder.add("company", company);
        }
        if (role != null) {
            builder.add("role", role);
        }
        if (address != null) {
            builder.add("address", address);
        }
        if (zipCode != null) {
            builder.add("zipCode", zipCode);
        }
        if (city != null) {
            builder.add("city", city);
        }
        if (country != null) {
            builder.add("country", country);
        }
        if (notes != null) {
            builder.add("notes", notes);
        }

        return builder.build();
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
     * @since 1.1.0
     */
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

    // =========================================================
    // == UTILITY METHODS                                     ==
    // =========================================================

    /**
     * Returns a string representation of this instance,
     * mainly intended for debugging and logging purposes.
     *
     * @return a string representing this {@code Contacts}
     *
     * @since 1.1.0
     */
    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", company='" + company + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}