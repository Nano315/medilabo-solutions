package com.medilabo.assessment.dto;

public class AssessmentResult {
    private String patient;
    private int age;
    private String risque;

    public AssessmentResult() {}

    public AssessmentResult(String patient, int age, String risque) {
        this.patient = patient;
        this.age = age;
        this.risque = risque;
    }

    // Getters and Setters
    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getRisque() { return risque; }
    public void setRisque(String risque) { this.risque = risque; }
}
