package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.Position;
import com.brocode.apply.repositories.ExperienceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/expirience")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExperienceController {
    @NonNull ExperienceRepository experienceRepository;
    public Position getPositionAtDate(LocalDate date) {
        return experienceRepository.findAtDate(date);
    }

    public Position getPositionById(Long id) {
        Position unemployed = new Position(-1L, LocalDate.EPOCH, LocalDate.EPOCH, "Unemployed");
        return experienceRepository.findById(id).orElse(unemployed);
    }

    public Position setRoleDescription(@NonNull Long id, String description) {
        Optional<Position> byId = experienceRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Position position = byId.get();
        position.setDescription(description);
        return experienceRepository.save(position);
    }
}
