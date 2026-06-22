package com.pm.patientservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.dtos.ResponseDTO;
import com.pm.patientservice.helper.ResponseMapper;
import com.pm.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "API patient management")
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
         this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<ResponseDTO<List<PatientResponseDTO>>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(ResponseMapper.success("Success get data", patients));
    }

    @PostMapping
    @Operation(summary = "Create patient")
    public ResponseEntity<ResponseDTO<PatientResponseDTO>> createPatient(
        @Valid
        @RequestBody
        PatientRequestDTO patientRequest
    ) {
        PatientResponseDTO newPatient = patientService.createPatient(patientRequest);
        return ResponseEntity.ok().body(ResponseMapper.success("Success create patient", newPatient));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient")
    public ResponseEntity<ResponseDTO<PatientResponseDTO>> updatePatient(
        @PathVariable UUID id,
        @Valid @RequestBody PatientRequestDTO patientRequestDTO
    ) {
        PatientResponseDTO newPatient = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(ResponseMapper.success("Success update patient", newPatient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient")
    public ResponseEntity<ResponseDTO<Void>> deletePatient(
        @PathVariable UUID id
    ) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().body(ResponseMapper.success("Success delete patient", null));
    }
    
}
