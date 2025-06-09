package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

public interface ExperienceRepository extends CrudRepository<Position, Long>, PagingAndSortingRepository<Position, Long> {
    @Query("select pos from Position pos where pos.start < :date and pos.finish > :date")
    Position findAtDate(LocalDate date); //https://www.baeldung.com/spring-data-jpa-query-by-date
}
