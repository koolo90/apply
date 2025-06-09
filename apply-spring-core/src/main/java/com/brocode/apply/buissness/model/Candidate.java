package com.brocode.apply.buissness.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor @Getter @Setter //Project Lombok
@Entity @Table(schema = "apply") // Spring JPA
public class Candidate {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    @NonNull @Column(unique = true) String username; // https://www.baeldung.com/jpa-unique-constraints
    @NonNull @Column(unique = true) String email; // https://www.baeldung.com/jpa-unique-constraints
}
