package com.medilabo.assessment.dto;

public class NoteDTO {
    private String id;
    private Integer patId;
    private String patient;
    private String note;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Integer getPatId() { return patId; }
    public void setPatId(Integer patId) { this.patId = patId; }
    
    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
