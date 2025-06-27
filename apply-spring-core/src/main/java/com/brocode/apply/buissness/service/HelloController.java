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
@RequestMapping("/hello")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {
    private final CelestialBodyRepository applierRepository;

    @GetMapping(value = "/global", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> hello() {
        return ResponseEntity.of(applierRepository.findByName("World"));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> targetedHello(@PathVariable("username") String username) {
        return ResponseEntity.of(applierRepository.findByName(username));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CelestialBody> helloAll() {
        return applierRepository.findAll();
    }

    @PostMapping(value = "/iam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> introduce(@RequestBody CelestialBody candidate) {
        return ResponseEntity.of(Optional.of(applierRepository.save(candidate)));
    }

    @PutMapping(value = "/fix/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CelestialBody> update(@PathVariable("username") String username, @RequestBody CelestialBody celestialBody) {
        return applierRepository.findByName(username).map(c -> {
            c.setName(celestialBody.getName());
            c.setWeight(celestialBody.getWeight());
            return ResponseEntity.of(Optional.of(applierRepository.save(c)));
        }).orElseGet(() ->  ResponseEntity.notFound().build());
    }
}
