package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.CelestialBody;

import java.util.Optional;

public interface CustomCandidateRepository {
    Optional<CelestialBody> deleteByUsername(String username);
}
