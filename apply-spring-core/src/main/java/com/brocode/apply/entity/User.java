package com.brocode.apply.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity @Table(name = "users", schema = "apply")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="celestial_body_seq")
    @SequenceGenerator(name="celestial_body_seq", sequenceName="celestial_body_id_seq", initialValue = 7537000, allocationSize=1)
    @Column Long id;
    @NonNull @Column(unique = true) private String username;
    @NonNull private String password;
    @NonNull private String roles;
}
