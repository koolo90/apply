package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.CelestialBody;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CelestialBodyRepository extends CrudRepository<CelestialBody, Long>, PagingAndSortingRepository<CelestialBody, Long> {
    Optional<CelestialBody> findByName(String name);

    List<CelestialBody> getByName(String name);

    @Transactional
    Long deleteByName(String name);
}
