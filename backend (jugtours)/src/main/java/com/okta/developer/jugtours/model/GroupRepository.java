package com.okta.developer.jugtours.model;

// This import brings in the JpaRepository interface from Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;

// Use List to handle groups of Group objects
import java.util.List;

// This interface tells Spring how to talk to the database for Group objects.
// JpaRepository gives you basic CRUD (Create, Read, Update, Delete) methods automatically
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Custom finder method.
    // Spring automatically turns it into SQL: "SELECT * FROM group WHERE name = ?"
    Group findByName(String name);
}