package com.medilabo.assessment.service;

import com.medilabo.assessment.client.NoteClient;
import com.medilabo.assessment.client.PatientClient;
import com.medilabo.assessment.dto.AssessmentResult;
import com.medilabo.assessment.dto.NoteDTO;
import com.medilabo.assessment.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Service
public class AssessmentService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final List<String> TRIGGERS = Arrays.asList(
            "hémoglobine a1c", "microalbumine", "taille", "poids", 
            "fumeur", "fumeuse", "anormal", "cholestérol", 
            "vertige", "rechute", "réaction", "anticorps"
    );

    public AssessmentService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    public AssessmentResult evaluateRisk(Long patId) {
        PatientDTO patient = patientClient.getPatientById(patId);
        List<NoteDTO> notes = noteClient.getNotesByPatientId(patId.intValue());

        int age = Period.between(patient.getDateNaissance(), LocalDate.now()).getYears();

        int triggerCount = countTriggers(notes);
        String risque = calculateRisk(age, patient.getGenre(), triggerCount);

        String patientFullName = patient.getPrenom() + " " + patient.getNom();
        return new AssessmentResult(patientFullName, age, risque);
    }

    private int countTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0;
        }

        String allNotes = notes.stream()
                .filter(note -> note.getNote() != null)
                .map(NoteDTO::getNote)
                .collect(java.util.stream.Collectors.joining(" "))
                .toLowerCase();

        return (int) TRIGGERS.stream()
                .filter(allNotes::contains)
                .count();
    }

    private String calculateRisk(int age, String genre, int triggerCount) {
        if (age > 30) {
            return calculateRiskOver30(triggerCount);
        }
        return calculateRiskUnder30(genre, triggerCount);
    }

    private String calculateRiskOver30(int triggerCount) {
        if (triggerCount >= 8) {
            return "Early onset";
        }
        if (triggerCount >= 6) {
            return "In Danger";
        }
        if (triggerCount >= 2) {
            return "Borderline";
        }
        return "None";
    }

    private String calculateRiskUnder30(String genre, int triggerCount) {
        if ("M".equalsIgnoreCase(genre)) {
            return calculateRiskYoungMale(triggerCount);
        }
        if ("F".equalsIgnoreCase(genre)) {
            return calculateRiskYoungFemale(triggerCount);
        }
        return "None";
    }

    private String calculateRiskYoungMale(int triggerCount) {
        if (triggerCount >= 5) {
            return "Early onset";
        }
        if (triggerCount >= 3) {
            return "In Danger";
        }
        return "None";
    }

    private String calculateRiskYoungFemale(int triggerCount) {
        if (triggerCount >= 7) {
            return "Early onset";
        }
        if (triggerCount >= 4) {
            return "In Danger";
        }
        return "None";
    }
}
