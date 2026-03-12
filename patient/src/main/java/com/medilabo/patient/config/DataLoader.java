package com.medilabo.patient.config;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initDatabase(PatientRepository patientRepository) {
        return args -> {
            // Seulement si la base de données est vide
            if (patientRepository.count() == 0) {
                System.out.println("Lancement de l'initialisation des données de test...");

                // Patient 1
                Patient p1 = new Patient();
                p1.setPrenom("Test");
                p1.setNom("TestNone");
                p1.setDateNaissance(LocalDate.parse("1966-12-31"));
                p1.setGenre("F");
                p1.setAdressePostale("1 Brookside St");
                p1.setTelephone("100-222-3333");
                patientRepository.save(p1);

                // Patient 2
                Patient p2 = new Patient();
                p2.setPrenom("Test");
                p2.setNom("TestBorderline");
                p2.setDateNaissance(LocalDate.parse("1945-06-24"));
                p2.setGenre("M");
                p2.setAdressePostale("2 High St");
                p2.setTelephone("200-333-4444");
                patientRepository.save(p2);

                // Patient 3
                Patient p3 = new Patient();
                p3.setPrenom("Test");
                p3.setNom("TestInDanger");
                p3.setDateNaissance(LocalDate.parse("2004-06-18"));
                p3.setGenre("M");
                p3.setAdressePostale("3 Club Road");
                p3.setTelephone("300-444-5555");
                patientRepository.save(p3);

                // Patient 4
                Patient p4 = new Patient();
                p4.setPrenom("Test");
                p4.setNom("TestEarlyOnset");
                p4.setDateNaissance(LocalDate.parse("2002-06-28"));
                p4.setGenre("F");
                p4.setAdressePostale("4 Valley Dr");
                p4.setTelephone("400-555-6666");
                patientRepository.save(p4);

                System.out.println("Initialisation terminée : 4 patients ont été ajoutés.");
            } else {
                System.out.println("Des patients existent déjà en base de données, initialisation ignorée.");
            }
        };
    }
}
