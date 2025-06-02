package com.brocode.apply.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_generator")
    @SequenceGenerator(name = "roles_seq_generator", sequenceName = "roles_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    // Constants for role names
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
}