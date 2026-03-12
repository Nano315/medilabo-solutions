package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findById(id);
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> existingPatientOpt = patientService.findById(id);
        
        if (existingPatientOpt.isPresent()) {
            Patient existingPatient = existingPatientOpt.get();
            existingPatient.setPrenom(patientDetails.getPrenom());
            existingPatient.setNom(patientDetails.getNom());
            existingPatient.setDateNaissance(patientDetails.getDateNaissance());
            existingPatient.setGenre(patientDetails.getGenre());
            existingPatient.setAdressePostale(patientDetails.getAdressePostale());
            existingPatient.setTelephone(patientDetails.getTelephone());
            
            Patient updatedPatient = patientService.save(existingPatient);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
