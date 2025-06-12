package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.Candidate;
import com.brocode.apply.repositories.CandidateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CandidateController {
    @NonNull CandidateRepository candidateRepository;
    public ResponseEntity<Candidate> getByEmail(String emailAddress) {
        return ResponseEntity.of(candidateRepository.findByEmail(emailAddress));
    }
}
