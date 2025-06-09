package com.brocode.apply.buissness.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@Table(schema = "apply")
public class Applier {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String username;
}
