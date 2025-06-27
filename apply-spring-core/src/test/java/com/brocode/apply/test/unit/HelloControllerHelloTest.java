package com.brocode.apply.test.unit;

import com.brocode.apply.ApplyApplication;
import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.buissness.service.HelloController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ApplyApplication.class})
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application-test.properties")
class HelloControllerHelloTest {
    @Autowired HelloController helloController;

    @Test @Disabled("Not finished draft implementation")
    void greetTheWorld() {
        ResponseEntity<CelestialBody> global = helloController.hello();

        assertThat(global.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(global.getBody()).isInstanceOfSatisfying(CelestialBody.class, celestialBody -> {
            assertThat(celestialBody.getName()).isEqualTo("World");
            assertThat(celestialBody.getWeight()).isEqualTo("world@example.com");
        });
    }

    @Test @Disabled("Not finished draft implementation")
    void greetTheWorldByName() {
        ResponseEntity<CelestialBody> global = helloController.targetedHello("World");

        assertThat(global.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(global.getBody()).isInstanceOfSatisfying(CelestialBody.class, celestialBody -> {
            assertThat(celestialBody.getName()).isEqualTo("World");
            assertThat(celestialBody.getWeight()).isEqualTo("world@example.com");
        });
    }

    @Test @Disabled("Not finished draft implementation")
    void greetMars() {
        ResponseEntity<CelestialBody> halloMars = helloController.targetedHello("Mars");

        assertThat(halloMars.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND .value()));
        assertThat(halloMars.getBody()).isNull();
    }
}