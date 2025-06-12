package com.brocode.apply.test.integration;

import com.brocode.apply.buissness.model.Candidate;
import com.brocode.apply.test.RestClientConfig;
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

    @Test
    void helloGlobalShouldRespond(){
        String formatted = (url + "/global").formatted(8080);

        ResponseEntity<Candidate> candidateResponseEntity = this.restTemplate.getForEntity(formatted, Candidate.class);
        Candidate responseCandidate = candidateResponseEntity.getBody();

        assertThat(responseCandidate).isNotNull();
        assertThat(responseCandidate.getEmail()).isNotBlank().isEqualTo("world@example.com");
        assertThat(responseCandidate.getUsername()).isNotBlank().isEqualTo("World");

        assertThat(candidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(candidateResponseEntity.getBody()).isInstanceOf(Candidate.class);

    }

    @Test
    void helloSpecificShouldRespond() {
        String formatted = (url + "/World").formatted(8080);

        ResponseEntity<Candidate> candidateResponseEntity = this.restTemplate.getForEntity(formatted, Candidate.class);
        Candidate responseCandidate = candidateResponseEntity.getBody();

        assertThat(responseCandidate).isNotNull();
        assertThat(responseCandidate.getEmail()).isNotBlank().isEqualTo("world@example.com");
        assertThat(responseCandidate.getUsername()).isNotBlank().isEqualTo("World");
    }

    @Test
    void helloSpecificShouldThrowError() {
        String formatted = (url + "/Mars").formatted(8080);

        assertThatThrownBy(() -> this.restTemplate.getForEntity(formatted, Candidate.class))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("404  on GET request")
                .hasMessageContaining("/hello/Mars")
                .hasMessageContaining("[no body]");
    }

    @Test
    void fixWorldShouldUpdateEntity() {
        //Verify entry state
        String formatted = (url + "/World").formatted(8080);
        ResponseEntity<Candidate> candidateResponseEntity = this.restTemplate.getForEntity(formatted, Candidate.class);
        Candidate responseCandidate = candidateResponseEntity.getBody();
        assertThat(responseCandidate).isNotNull();
        assertThat(responseCandidate.getEmail()).isNotBlank().isEqualTo("world@example.com");
        assertThat(responseCandidate.getUsername()).isNotBlank().isEqualTo("World");
        assertThat(candidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Verify the outcome of save
        formatted = (url + "/fix/World").formatted(8080);
        ResponseEntity<Candidate> mars = restTemplate.postForEntity(formatted, new Candidate("Mars", "mars@solar.system"), Candidate.class);
        assertThat(mars.getBody()).isNotNull();
        assertThat(mars.getBody().getEmail()).isNotBlank().isEqualTo("mars@solar.system");
        assertThat(mars.getBody().getUsername()).isNotBlank().isEqualTo("Mars");

        //Verify end state
        formatted = (url + "/World").formatted(8080);
        ResponseEntity<Candidate> finalCandidateResponseEntity = this.restTemplate.getForEntity(formatted, Candidate.class);
        Candidate finalCandidateResponseBody = candidateResponseEntity.getBody();
        assertThat(finalCandidateResponseBody).isNotNull();
        assertThat(finalCandidateResponseBody.getEmail()).isNotBlank().isEqualTo("world@example.com");
        assertThat(finalCandidateResponseBody.getUsername()).isNotBlank().isEqualTo("World");
        assertThat(finalCandidateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
