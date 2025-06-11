package com.brocode.apply.test.integration;

import com.brocode.apply.buissness.model.Candidate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {RestClientConfig.class})
class HelloApiTests {
    @Autowired
    RestTemplate restTemplate;

    @Test
    void helloApiTest(){
        String formatted = "http://localhost:%d/apply-spring/hello/global".formatted(8080);

        ResponseEntity<Candidate> candidateResponseEntity = this.restTemplate.getForEntity(formatted, Candidate.class);
        Candidate responseCandidate = candidateResponseEntity.getBody();

        Assertions.assertThat(responseCandidate.getEmail()).isNotBlank().isEqualTo("world@example.com");
        Assertions.assertThat(responseCandidate.getUsername()).isNotBlank().isEqualTo("World");
    }
}
