package com.brocode.apply.test.unit;

import com.brocode.apply.ApplyApplication;
import com.brocode.apply.buissness.model.CelestialBody;
import com.brocode.apply.repositories.CelestialBodyRepository;

import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ApplyApplication.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/initial_data.sql")
@Transactional
class CelestialBodyRepositoryTest {
    @Autowired
    CelestialBodyRepository repository;

    @Test // Create
    void createEntity() {
        repository.findAll();

        CelestialBody entity = new CelestialBody("Earth", "6e24");

        CelestialBody saved = repository.save(entity);

        assertThat(saved).extracting("name").isNotNull().isEqualTo("Earth");
        assertThat(saved).extracting("weight").isNotNull().isEqualTo("6e24");
    }

    @Test // Read -all
    void findAll() {
        Iterable<CelestialBody> celestialBodies = repository.findAll();
        assertThat(celestialBodies).isNotNull().isNotEmpty().size().isEqualTo(3);
    }

    @Test //Read by name
    void findOneByName() {
        CelestialBody celestialBody = repository.findByName("Mercury").get();
        assertThat(celestialBody).isNotNull();
        assertThat(celestialBody).extracting("id").isEqualTo(2L);
    }

    @Test //Read by id
    void findOneById() {
        CelestialBody celestialBody = repository.findById(1L).get();
        assertThat(celestialBody).isNotNull();
        assertThat(celestialBody).extracting("name").isEqualTo("Sun");
    }

    @Test //Update
    void updateEntity() {
        CelestialBody celestialBody = repository.findById(1L).get();
        celestialBody.setName("Star");
        CelestialBody updatedCelestialBody = repository.save(celestialBody);
        assertThat(updatedCelestialBody).extracting("name").isEqualTo("Star");
    }

    @Test //Update
    void deleteEntity() {
        int beforeCount = IterableUtil.sizeOf(repository.findAll());
        Optional<CelestialBody> presentMercury = repository.findByName("Mercury");
        assertThat(presentMercury).isNotEmpty();
        Long deletedMercuriesCount = repository.deleteByName("Mercury");
        assertThat(deletedMercuriesCount).isEqualTo(1);
        Optional<CelestialBody> absentMercury = repository.findByName("Mercury");
        assertThat(absentMercury).isEmpty();
        int afterCount = IterableUtil.sizeOf(repository.findAll());
        assertThat(afterCount).isEqualTo(beforeCount - 1);
    }
}