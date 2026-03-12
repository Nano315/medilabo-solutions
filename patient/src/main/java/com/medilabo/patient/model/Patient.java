package com.medilabo.patient.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = true)
    private String adressePostale;

    @Column(nullable = true)
    private String telephone;
}
