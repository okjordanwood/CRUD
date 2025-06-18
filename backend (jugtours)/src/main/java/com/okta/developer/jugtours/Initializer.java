package com.okta.developer.jugtours;

// Imports data model classes
import com.okta.developer.jugtours.model.Event;
import com.okta.developer.jugtours.model.Group;
import com.okta.developer.jugtours.model.GroupRepository;

// Spring Boot utility: runs code when the app starts
import org.springframework.boot.CommandLineRunner;

// Marks this class as a Spring Bean so Spring can use it
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;  // Utility for making an unmodifiable set
import java.util.stream.Stream;  // Used to easily loop over data

// @Component: tells Spring to automatically detect and create this as a bean
@Component

class Initializer implements CommandLineRunner {

    // This is how we talk to the database for Group entities
    private final GroupRepository repository;

    // Constructor injection: Spring will pass in the GroupRepository here
    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    // This code runs automatically when the app starts
    @Override
    public void run(String... strings) {

        // Create 4 new Group objects with just their names, and save them to the database
        Stream.of("Seattle JUG", "Denver JUG", "Dublin JUG", "London JUG").forEach(name -> repository.save(new Group(name)));

        // Find the "Seattle JUG" group from the database
        Group djug = repository.findByName("Seattle JUG");

        // Create a new Event object for this group
        Event e = Event.builder().title("Micro Frontends for Java Developers").description("JHipster now has microfrontend support!").date(Instant.parse("2022-09-13T17:00:00.00Z")).build();

        // Attach the event to the group
        djug.setEvents(Collections.singleton(e));
        repository.save(djug);  // Save the updated group with the new event

        // Print all groups and their details to the console
        repository.findAll().forEach(System.out::println);
    }
}
