package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.Candidate;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CandidateRepository extends CrudRepository<Candidate, String> {
    Optional<Candidate> findByUsername(String world);

    Optional<Candidate> findByEmail(String emailAddress);
}
