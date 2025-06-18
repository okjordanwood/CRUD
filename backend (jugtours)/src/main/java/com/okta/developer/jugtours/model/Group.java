package com.okta.developer.jugtours.model;

// These annotations come from Lombok and help generate common code like getters and setters automatically
import lombok.Data;  // Generates getters, setters, toString, equals, and hashCode
import lombok.NoArgsConstructor;  // Makes a no-argument constructor
import lombok.NonNull;  // Marks a field as required (not null)
import lombok.RequiredArgsConstructor;  // Makes a constructor that includes only the @NonNull fields

// Java Persistence API (JPA) annotations for mapping to a database
import jakarta.persistence.*;
import java.util.Set;  // A Set is a collection that doesn't allow duplicates (used for events)

// Lombok will automatically generate constructors, builder, and data methods
// @Data: Lombok annotation to generate boilerplate code like getters/setters
// @NoArgsConstructor: generates a no-argument constructor
// @RequiredArgsConstructor: generates a constructor requiring all fields marked with @NonNull
@Data
@NoArgsConstructor
@RequiredArgsConstructor

// @Entity: tells JPA this class is a database entity
// @Table(name = "user_group"): maps this class to a table called "user_group"
@Entity
@Table(name = "user_group")

public class Group {

    // Marks this field as the primary key
    @Id
    @GeneratedValue  // Tells JPA to automatically generate the ID value
    private Long id;

    @NonNull  // Lombok will include this in the required constructor
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;

    // Many groups can belong to one user; persist the user when saving group
    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;  // The user who created or owns this group

    // A group has many events; load all events when group is loaded
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Event> events;  // The list of events organized by this group (no duplicates)
}