package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.repositories.CelestialBodyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <h1>Based on: <a href="https://www.baeldung.com/spring-injection-lombok">Constructor Injection in Spring with Lombok</a></a></h1>
 * <h3>Credits:</h3>
 *      <h4>Written by:baeldung</h4>
 *      <h4>Reviewed by:Grzegorz Piwowarek</h4>
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
@RequestMapping("/planet")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetController {
    private final CelestialBodyRepository celestialBodyRepository;

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> createNew(@RequestBody CelestialBody candidate) {
        return ResponseEntity.of(Optional.of(celestialBodyRepository.save(candidate)));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CelestialBody> getAll() {
        return celestialBodyRepository.findAll();
    }

    @GetMapping(value = "/details/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> getByName(@PathVariable String name) {
        return ResponseEntity.of(celestialBodyRepository.findByName(name));
    }

    @PatchMapping(value = "/edit/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> updateByName(@PathVariable String name) {
        return ResponseEntity.of(celestialBodyRepository.findByName(name));
    }

    @DeleteMapping(value = "/delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByName(@PathVariable String name) {
        celestialBodyRepository.deleteByName(name);
    }
}
