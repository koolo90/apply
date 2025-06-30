package com.brocode.apply.test.integration;

import com.brocode.apply.RestClientConfig;
import com.brocode.apply.buissness.model.CelestialBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RestClientConfig.class})
class CelestialBodyAPITest {
    @Autowired RestTemplate celestialBodyApi;
    @Autowired RestTemplateBuilder templateBuilder;

    static String host = "localhost";
    static String url = "http://%s:%d/apply-spring/celestialBody/%s";
    private static int port = 8080;

    @Test
    void createNewCelestialBodyShouldSuccess() {
        // Given
        String celestialBodyName = "Sun";
        CelestialBody celestialBody = new CelestialBody(celestialBodyName, "1,998 × 10^30 kg");
        String endpointUrl = url.formatted(host, port, "new");
        //When
        ResponseEntity<CelestialBody> celestialBodyResponseEntity = this.celestialBodyApi.postForEntity(endpointUrl, celestialBody, CelestialBody.class);
        //Then
        CelestialBody body = celestialBodyResponseEntity.getBody();
        assertThat(celestialBodyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(body);
        assertThat(body.getId()).isNotNull();
    }

    @Test
    void createError() {
        // Given
        String celestialBodyName = "Sun";
        CelestialBody celestialBody = new CelestialBody(celestialBodyName, "1,998 × 10^30 kg");
        String endpointUrl = url.formatted(host, port, "new");
        this.celestialBodyApi.postForEntity(endpointUrl, celestialBody, CelestialBody.class);
        //When
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            this.celestialBodyApi.postForEntity(endpointUrl, celestialBody, CelestialBody.class);
        });
    }

    @Test
    void readAll() {
        // Given
        String celestialBodyName = "Sun";
        CelestialBody celestialBody = new CelestialBody(celestialBodyName, "1,998 × 10^30 kg");
        String creationEndpointUrl = url.formatted(host, port, "new");
        this.celestialBodyApi.postForEntity(creationEndpointUrl, celestialBody, CelestialBody.class);
        //When
        String listAllUrl = url.formatted(host, port, "all");
        var celestialBodiesResponse = this.celestialBodyApi.getForEntity(listAllUrl, Collection.class);
        var celestialBodies = celestialBodiesResponse.getBody();
        Assertions.assertNotNull(celestialBodies);
        Object fetchedCelestialBodyMap = celestialBodies.iterator().next();
        ObjectMapper objectMapper = new ObjectMapper();
        CelestialBody fetchedCelestialBody = objectMapper.convertValue(fetchedCelestialBodyMap, CelestialBody.class);
        assertThat(fetchedCelestialBody.getId()).isNotNull();
        assertThat(fetchedCelestialBody.getName()).isEqualTo("Sun");
    }

    @AfterEach
    void cleanupEntities() {
        Map<String, String> uriParams = Map.of("name", "Sun");
        this.celestialBodyApi.delete(url.formatted(host, port, "delete/{name}"), uriParams);
    }
}
