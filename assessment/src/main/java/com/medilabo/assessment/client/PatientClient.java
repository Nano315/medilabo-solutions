package com.medilabo.assessment.client;

import com.medilabo.assessment.config.FeignConfig;
import com.medilabo.assessment.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface PatientClient {

    @GetMapping("/api/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);
}
