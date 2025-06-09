package com.brocode.apply.test.unit;

import com.brocode.apply.ApplyApplication;
import com.brocode.apply.buissness.model.Position;
import com.brocode.apply.buissness.service.ExperienceController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ApplyApplication.class})
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application-test.properties")
class AddRoleExperienceTest {
    @Autowired ExperienceController experienceController;

    @Test
    void addRoleExperience() {
        Position position = experienceController.getPositionAtDate(LocalDate.of(2024, Month.JUNE, 1));

        Position updatedPosition = experienceController.setRoleDescription(position.getId(), "Lorem ipsum dolor sit amet");
        assertThat(updatedPosition.getDescription()).contains("Lorem ipsum");

        Position refetchedPosition = experienceController.getPositionAtDate(LocalDate.of(2024, Month.JUNE, 1));
        assertThat(refetchedPosition.getDescription()).contains("Lorem ipsum");
    }
}
