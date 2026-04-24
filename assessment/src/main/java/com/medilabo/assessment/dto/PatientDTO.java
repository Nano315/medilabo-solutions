package com.medilabo.assessment.dto;

import java.time.LocalDate;

public class PatientDTO {
    private Long id;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}
