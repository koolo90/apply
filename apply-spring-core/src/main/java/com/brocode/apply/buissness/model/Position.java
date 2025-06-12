package com.brocode.apply.buissness.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(schema = "apply")
public class Position {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;

    @NonNull
    @Column(nullable = false) @Temporal(TemporalType.DATE) //https://www.baeldung.com/spring-data-jpa-query-by-date
    private LocalDate start;

    @NonNull
    @Temporal(TemporalType.DATE) //https://www.baeldung.com/spring-data-jpa-query-by-date
    private LocalDate finish;

    @NonNull
    @Column(length = 100, nullable = false)
    private String role;

    private String description;
}
