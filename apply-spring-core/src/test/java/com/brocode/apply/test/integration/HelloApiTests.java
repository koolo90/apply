package com.brocode.apply.test.integration;

import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.RestClientConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {RestClientConfig.class})
class HelloApiTests {
    @Autowired RestTemplate restTemplate;

    String url = "http://localhost:%d/apply-spring/hello";

    @Test @@Disabled("Not tested yet")
    void helloGlobalShouldRespond(){
        String formatted = (url + "/global").formatted(8080);

        ResponseEntity<CelestialBody> candidateResponseEntity = this.restTemplate.getForEntity(formatted, CelestialBody.class);
        CelestialBody celestialBodyResponse = candidateResponseEntity.getBody();

        assertThat(celestialBodyResponse).isNotNull();
        assertThat(celestialBodyResponse.getName()).isNotBlank().isEqualTo("world@example.com");
        assertThat(celestialBodyResponse.getWeight()).isNotBlank().isEqualTo("World");

        assertThat(candidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(candidateResponseEntity.getBody()).isInstanceOf(CelestialBody.class);

    }

    @Test @@Disabled("Not tested yet")
    void helloSpecificShouldRespond() {
        String formatted = (url + "/World").formatted(8080);

        ResponseEntity<CelestialBody> celestialBodyResponseEntity = this.restTemplate.getForEntity(formatted, CelestialBody.class);
        CelestialBody responseCandidate = celestialBodyResponseEntity.getBody();

        assertThat(responseCandidate).isNotNull();
        assertThat(responseCandidate.getName()).isNotBlank().isEqualTo("world@example.com");
        assertThat(responseCandidate.getWeight()).isNotBlank().isEqualTo("World");
    }

    @Test @Disabled("Not tested yet")
    void helloSpecificShouldThrowError() {
        String formatted = (url + "/Mars").formatted(8080);

        assertThatThrownBy(() -> this.restTemplate.getForEntity(formatted, CelestialBody.class))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("404  on GET request")
                .hasMessageContaining("/hello/Mars")
                .hasMessageContaining("[no body]");
    }

    @Test @@Disabled("Not tested yet")
    void fixWorldShouldUpdateEntity() {
        //Verify entry state
        String formatted = (url + "/World").formatted(8080);
        ResponseEntity<CelestialBody> candidateResponseEntity = this.restTemplate.getForEntity(formatted, CelestialBody.class);
        CelestialBody responseCandidate = candidateResponseEntity.getBody();
        assertThat(responseCandidate).isNotNull();
        assertThat(responseCandidate.getName()).isNotBlank().isEqualTo("world@example.com");
        assertThat(responseCandidate.getWeight()).isNotBlank().isEqualTo("World");
        assertThat(candidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Verify the outcome of save
        formatted = (url + "/fix/World").formatted(8080);
        ResponseEntity<CelestialBody> mars = restTemplate.postForEntity(formatted, new CelestialBody("Mars", "mars@solar.system"), CelestialBody.class);
        assertThat(mars.getBody()).isNotNull();
        assertThat(mars.getBody().getName()).isNotBlank().isEqualTo("mars@solar.system");
        assertThat(mars.getBody().getWeight()).isNotBlank().isEqualTo("Mars");

        //Verify end state
        formatted = (url + "/World").formatted(8080);
        ResponseEntity<CelestialBody> finalCandidateResponseEntity = this.restTemplate.getForEntity(formatted, CelestialBody.class);
        CelestialBody finalCandidateResponseBody = candidateResponseEntity.getBody();
        assertThat(finalCandidateResponseBody).isNotNull();
        assertThat(finalCandidateResponseBody.getName()).isNotBlank().isEqualTo("world@example.com");
        assertThat(finalCandidateResponseBody.getWeight()).isNotBlank().isEqualTo("World");
        assertThat(finalCandidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
