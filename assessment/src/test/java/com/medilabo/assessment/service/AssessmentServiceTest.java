package com.medilabo.assessment.service;

import com.medilabo.assessment.client.NoteClient;
import com.medilabo.assessment.client.PatientClient;
import com.medilabo.assessment.dto.AssessmentResult;
import com.medilabo.assessment.dto.NoteDTO;
import com.medilabo.assessment.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private AssessmentService assessmentService;

    private PatientDTO patient;

    @BeforeEach
    void setUp() {
        patient = new PatientDTO();
    }

    @Test
    void evaluateRisk_testNone_returnsNone() {
        setupPatient(1L, "Test", "TestNone", LocalDate.of(1966, 12, 31), "F");
        List<NoteDTO> notes = List.of(
                buildNote("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé")
        );

        when(patientClient.getPatientById(1L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        AssessmentResult result = assessmentService.evaluateRisk(1L);

        assertThat(result.getRisque()).isEqualTo("None");
        assertThat(result.getPatient()).isEqualTo("Test TestNone");
    }

    @Test
    void evaluateRisk_testBorderline_returnsBorderline() {
        setupPatient(2L, "Test", "TestBorderline", LocalDate.of(1945, 6, 24), "M");
        List<NoteDTO> notes = Arrays.asList(
                buildNote("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"),
                buildNote("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois II remarque également que son audition continue d'être anormale")
        );

        when(patientClient.getPatientById(2L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(2)).thenReturn(notes);

        AssessmentResult result = assessmentService.evaluateRisk(2L);

        assertThat(result.getRisque()).isEqualTo("Borderline");
        assertThat(result.getPatient()).isEqualTo("Test TestBorderline");
    }

    @Test
    void evaluateRisk_testInDanger_returnsInDanger() {
        setupPatient(3L, "Test", "TestInDanger", LocalDate.of(2004, 6, 18), "M");
        List<NoteDTO> notes = Arrays.asList(
                buildNote("Le patient déclare qu'il fume depuis peu"),
                buildNote("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière II se plaint également de crises d'apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé")
        );

        when(patientClient.getPatientById(3L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(3)).thenReturn(notes);

        AssessmentResult result = assessmentService.evaluateRisk(3L);

        assertThat(result.getRisque()).isEqualTo("In Danger");
        assertThat(result.getPatient()).isEqualTo("Test TestInDanger");
    }

    @Test
    void evaluateRisk_testEarlyOnset_returnsEarlyOnset() {
        setupPatient(4L, "Test", "TestEarlyOnset", LocalDate.of(2002, 6, 28), "F");
        List<NoteDTO> notes = Arrays.asList(
                buildNote("Le patient déclare qu'il lui est devenu difficile de monter les escaliers II se plaint également d'être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"),
                buildNote("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
                buildNote("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"),
                buildNote("Taille, Poids, Cholestérol, Vertige et Réaction")
        );

        when(patientClient.getPatientById(4L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(4)).thenReturn(notes);

        AssessmentResult result = assessmentService.evaluateRisk(4L);

        assertThat(result.getRisque()).isEqualTo("Early onset");
        assertThat(result.getPatient()).isEqualTo("Test TestEarlyOnset");
    }

    @Test
    void evaluateRisk_patientWithoutNotes_returnsNone() {
        setupPatient(5L, "Test", "NoNotes", LocalDate.of(1990, 1, 1), "M");

        when(patientClient.getPatientById(5L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(5)).thenReturn(Collections.emptyList());

        AssessmentResult result = assessmentService.evaluateRisk(5L);

        assertThat(result.getRisque()).isEqualTo("None");
    }

    @Test
    void evaluateRisk_triggerDetection_isCaseInsensitive() {
        setupPatient(6L, "Test", "Casing", LocalDate.of(1980, 1, 1), "M");
        List<NoteDTO> notes = Arrays.asList(
                buildNote("HÉMOGLOBINE A1C élevée"),
                buildNote("CHOLESTÉROL LDL"),
                buildNote("ANORMAL")
        );

        when(patientClient.getPatientById(6L)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(6)).thenReturn(notes);

        AssessmentResult result = assessmentService.evaluateRisk(6L);

        assertThat(result.getRisque()).isEqualTo("Borderline");
    }

    private void setupPatient(Long id, String prenom, String nom, LocalDate dateNaissance, String genre) {
        patient.setId(id);
        patient.setPrenom(prenom);
        patient.setNom(nom);
        patient.setDateNaissance(dateNaissance);
        patient.setGenre(genre);
    }

    private NoteDTO buildNote(String content) {
        NoteDTO note = new NoteDTO();
        note.setNote(content);
        return note;
    }
}
