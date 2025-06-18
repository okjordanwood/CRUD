package com.okta.developer.jugtours.model;

import lombok.AllArgsConstructor;  // Generates a constructor with all fields
import lombok.Builder;  // Allows building Event objects using the builder pattern
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;  // Marks this class as a database entity
import jakarta.persistence.GeneratedValue;   // Lets the database auto-generate the ID
import jakarta.persistence.Id;  // Marks the primary key
import jakarta.persistence.ManyToMany;  // Sets up a many-to-many relationship

import java.time.Instant;  // A point in time (used for the event date/time)
import java.util.Set;

@Data
@NoArgsConstructor

// @AllArgsConstructor: constructor with all fields (useful for testing or full object creation)
// @Builder: lets you use the builder pattern to create Event objects easily
@AllArgsConstructor
@Builder
@Entity

public class Event {

    @Id
    @GeneratedValue
    private Long id;
    private Instant date;  // When the event happens (exact date & time)
    private String title;
    private String description;

    // Many users can attend many events
    @ManyToMany
    private Set<User> attendees;  // People who will attend this event (no duplicates)
}
