package com.brocode.apply.repository;

import com.brocode.apply.entity.CelestialBody;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CelestialBodyRepository extends CrudRepository<CelestialBody, Long>, PagingAndSortingRepository<CelestialBody, Long> {
    Optional<CelestialBody> findByName(String name);

    @Transactional
    Long deleteByName(String name);
}
