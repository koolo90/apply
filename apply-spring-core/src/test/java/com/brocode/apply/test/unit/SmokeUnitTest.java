package com.brocode.apply.test.unit;

import com.brocode.apply.ApplyApplication;
import com.brocode.apply.buissness.service.CelestialBodyController;
import com.brocode.apply.repositories.CelestialBodyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {ApplyApplication.class})
@ActiveProfiles("test")
class SmokeUnitTest {
    @Autowired CelestialBodyRepository repository;
    @Autowired CelestialBodyController controller;

    @Test
    void contextLoads() {
        Assertions.assertThat(repository).isNotNull(); //basing on: https://spring.io/guides/gs/testing-web
        //Assertions.assertThat(controller).isNotNull(); //basing on: https://spring.io/guides/gs/testing-web
    }
}
