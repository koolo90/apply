package com.brocode.apply.test.unit;

import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.buissness.service.CelestialBodyController;
import com.brocode.apply.repositories.CelestialBodyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CelestialBodyRepository.class, CelestialBodyController.class})
@ActiveProfiles("utest")
class CelestialBodyControllerTest {
    // Mocks
    @MockitoBean CelestialBodyRepository mockCelestialBodyRepository;
    @Autowired CelestialBodyController controller; //sut

    @Test
    void findAll() {
        CelestialBody cb1 = new CelestialBody("Sun", "2e30");

        List<CelestialBody> celestialBodies = Stream.of(cb1).toList();
        when(mockCelestialBodyRepository.findAll()).thenReturn(celestialBodies);

        Iterable<CelestialBody> all = controller.getAll();

        assertThat(all).isNotNull().isNotEmpty();
    }
}
