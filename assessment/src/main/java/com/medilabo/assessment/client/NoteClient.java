package com.medilabo.assessment.client;

import com.medilabo.assessment.config.FeignConfig;
import com.medilabo.assessment.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface NoteClient {

    @GetMapping("/api/notes/patient/{patId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable("patId") Integer patId);
}
