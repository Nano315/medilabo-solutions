package com.medilabo.note.config;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public void run(String... args) throws Exception {
        if (noteRepository.count() == 0) {
            System.out.println("Lancement de l'initialisation des notes de test...");

            noteRepository.save(new Note(1, "TestNone", "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"));

            noteRepository.save(new Note(2, "TestBorderline", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"));
            noteRepository.save(new Note(2, "TestBorderline", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois II remarque également que son audition continue d'être anormale"));

            noteRepository.save(new Note(3, "TestInDanger", "Le patient déclare qu'il fume depuis peu"));
            noteRepository.save(new Note(3, "TestInDanger", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière II se plaint également de crises d'apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"));

            noteRepository.save(new Note(4, "TestEarlyOnset", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers II se plaint également d'être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"));
            noteRepository.save(new Note(4, "TestEarlyOnset", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));
            noteRepository.save(new Note(4, "TestEarlyOnset", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"));
            noteRepository.save(new Note(4, "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction"));

            System.out.println("Initialisation terminée : 9 notes ont été ajoutées.");
        } else {
            System.out.println("Des notes existent déjà en base de données, initialisation ignorée.");
        }
    }
}
