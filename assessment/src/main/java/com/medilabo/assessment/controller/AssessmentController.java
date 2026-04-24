package com.medilabo.assessment.controller;

import com.medilabo.assessment.dto.AssessmentResult;
import com.medilabo.assessment.service.AssessmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/{patId}")
    public AssessmentResult evaluateRisk(@PathVariable Long patId) {
        return assessmentService.evaluateRisk(patId);
    }
}
