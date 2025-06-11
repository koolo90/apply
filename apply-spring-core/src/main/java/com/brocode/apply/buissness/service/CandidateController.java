package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.Candidate;
import com.brocode.apply.repositories.CandidateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CandidateController {
    @NonNull CandidateRepository candidateRepository;
    public Candidate getByEmail(String emailAddress) {
        return candidateRepository.findByEmail(emailAddress).orElseThrow(() -> new ResourceNotFoundException(this.getClass(), Candidate.class, "Email: \"{0}\" not found!", emailAddress));
    }
}
