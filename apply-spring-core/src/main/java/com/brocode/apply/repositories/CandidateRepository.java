package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.Candidate;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, String> {
    Candidate findByUsername(String world);

    Candidate findByEmail(String emailAddress);
}
