package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CandidateRepository extends CrudRepository<Candidate, String>, PagingAndSortingRepository<Candidate, String> {
    Optional<Candidate> findByUsername(String username);

    Optional<Candidate> findByEmail(String emailAddress);
}
