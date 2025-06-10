package com.brocode.apply.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {RestClientConfig.class})
class HelloApiTests {
    @Autowired
    RestTemplate restTemplate;

    @Test
    void helloApiTest(){
        String formatted = "http://localhost:%d/hello".formatted(8080);
        String forObject = this.restTemplate.getForObject(formatted, String.class);
        Assertions.assertThat(forObject).isEqualTo("Hello World");
    }
}
