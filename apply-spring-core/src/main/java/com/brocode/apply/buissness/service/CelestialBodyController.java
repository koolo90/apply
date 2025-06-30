package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.repositories.CelestialBodyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

/**
 * <h1>Based on: <a href="https://www.baeldung.com/spring-injection-lombok">Constructor Injection in Spring with Lombok</a></a></h1>
 * <h3>Credits:</h3>
 * <h4>Written by:baeldung</h4>
 * <h4>Reviewed by:Grzegorz Piwowarek</h4>
 * <p>The onConstructor parameter accepts an array of annotations (or a single annotation like in this specific example)
 * that are to be put on a generated constructor. The double underscore idiom has been introduced because of the
 * backward compatibility issues.</p>
 * <h3>According to the documentation:</h3>
 * <em>The reason of the weird syntax is to make this feature work in javac 7 compilers; the @__ type is an annotation
 * reference to the annotation type __ (double underscore) which doesn’t actually exist; this makes javac 7 delay
 * aborting the compilation process due to an error because it is possible an annotation processor will later create
 * the __ type.</em>
 * <p>And others aren't documented.</p>
 */
@RestController
@RequestMapping("/celestialBody")
@CrossOrigin(origins = { "http://localhost:4200", "http://127.0.0.1:4200" })
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CelestialBodyController {
    String createLogMessage = "Creating CelestialBody {}";
    String fetchLogMessage = "Getting CelestialBody by name {}";
    String listLogMessage = "Listing CelestialBodies";
    String editLogMessage = "Editing CelestialBody by name {}";
    String updateLogMessage = "Updating CelestialBody {}";
    String deleteLogMessage = "Deleting CelestialBody by name {}";

    private final CelestialBodyRepository celestialBodyRepository;

    // Create
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> createNew(@RequestBody CelestialBody celestialBody) {
        log.info(createLogMessage, celestialBody);

        try {
            celestialBodyRepository.save(celestialBody);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(celestialBody);
        }

        CelestialBody savedCelestialBody = celestialBodyRepository.save(celestialBody);
        Optional<CelestialBody> optionalSavedCelestialBody = Optional.of(savedCelestialBody);
        return ResponseEntity.of(optionalSavedCelestialBody);
    }

    // Read - All
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CelestialBody> getAll() {
        log.info(listLogMessage);
        return celestialBodyRepository.findAll();
    }

    // Read - Singular
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> getByName(@PathVariable String name) {
        log.info(fetchLogMessage, name);
        return ResponseEntity.of(celestialBodyRepository.findByName(name));
    }

    //Update
    @PatchMapping(value = "/edit/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pair<CelestialBody, CelestialBody>> edit(@PathVariable String name, @RequestBody CelestialBody celestialBody) {
        log.info(editLogMessage, name);
        log.info(fetchLogMessage, name);
        Optional<CelestialBody> formerStateOfCelestialBody = celestialBodyRepository.findByName(name);

        CelestialBody first;
        if (formerStateOfCelestialBody.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            first = formerStateOfCelestialBody.get();
        }

        log.info(updateLogMessage, celestialBody);
        CelestialBody savedCelestialBody = celestialBodyRepository.save(celestialBody);

        Pair<CelestialBody, CelestialBody> updateResponseHolder = Pair.of(first, savedCelestialBody);

        return ResponseEntity.ok(updateResponseHolder);
    }

    //Delete
    @DeleteMapping(value = "/delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pair<Long, CelestialBody>> deleteByName(@PathVariable String name) {
        log.info(deleteLogMessage, name);
        Optional<CelestialBody> formerStateOfCelestialBody = celestialBodyRepository.findByName(name);
        if (formerStateOfCelestialBody.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedCount = celestialBodyRepository.deleteByName(name);
        Pair<Long, CelestialBody> deleteResponseHolder = Pair.of(deletedCount, formerStateOfCelestialBody.get());
        return ResponseEntity.ok(deleteResponseHolder);
    }

    @DeleteMapping(value = "/deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pair<Long, Iterable<CelestialBody>>> deleteAll() {
        log.info(deleteLogMessage);
        long countOfCelestialBodies = celestialBodyRepository.count();
        if (countOfCelestialBodies <= 0) {
            return ResponseEntity.notFound().build();
        }
        Iterable<CelestialBody> formerStateOfCelestialBody = celestialBodyRepository.findAll();
        long size = IterableUtils.size(formerStateOfCelestialBody);
        celestialBodyRepository.deleteAll();
        Pair<Long, Iterable<CelestialBody>> deleteResponseHolder = Pair.of(size, formerStateOfCelestialBody);
        return ResponseEntity.ok(deleteResponseHolder);
    }
}
