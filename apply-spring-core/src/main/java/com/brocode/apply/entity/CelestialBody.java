package com.brocode.apply.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor //Project Lombok
@Entity @Table(schema = "apply") // Spring JPA
public class CelestialBody {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="celestial_body_seq")
    @SequenceGenerator(name="celestial_body_seq", sequenceName="celestial_body_id_seq", initialValue = 73570000, allocationSize=1)
    @Column Long id;
    @NonNull @Column(unique = true) String name; // https://www.baeldung.com/jpa-unique-constraints
    @NonNull String weight; // https://www.baeldung.com/jpa-unique-constraints
}
