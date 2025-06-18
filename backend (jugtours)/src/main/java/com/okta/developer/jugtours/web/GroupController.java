package com.okta.developer.jugtours.web;

// Import model and repository
import com.okta.developer.jugtours.model.Group;
import com.okta.developer.jugtours.model.GroupRepository;

// For logging requests and events
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// HTTP response classes from Spring
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// Annotations to define RESTful endpoints
import org.springframework.web.bind.annotation.*;

// For validating input
import jakarta.validation.Valid;

// Java utility classes
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

// @RestController: marks this class as a REST controller — handles HTTP requests and returns JSON
// @RequestMapping: all endpoints will start with /api
@RestController
@RequestMapping("/api")

class GroupController {

    // Logger to help print messages to the console (e.g., for debugging)
    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    // This is how we access the database for Group entities
    private GroupRepository groupRepository;

    // Constructor injection: Spring automatically provides the repository
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // GET /api/groups
    // Returns a list of all groups in the database
    @GetMapping("/groups")
    Collection<Group> groups() {
        return groupRepository.findAll();
    }

    // GET /api/group/{id}
    // Returns a single group by ID (if found), or 404 Not Found
    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST /api/group
    // Creates a new group and returns it with 201 Created and a Location header
    @PostMapping("/group")
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group" + result.getId())).body(result);
    }

    // PUT /api/group/{id}
    // Updates an existing group. Assumes the ID is already present in the group payload.
    @PutMapping("/group/{id}")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    // DELETE /api/group/{id}
    // Deletes a group by ID and returns 200 OK if successful
    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
