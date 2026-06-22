package com.pm.patientservice.helper;

import java.time.LocalDate;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();

        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toPatient(PatientRequestDTO patientRequest) {
        Patient patientEntity = new Patient();

        patientEntity.setName(patientRequest.getName());
        patientEntity.setEmail(patientRequest.getEmail());
        patientEntity.setAddress(patientRequest.getAddress());
        patientEntity.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
        patientEntity.setRegisteredDate(LocalDate.now());

        return patientEntity;
    }
}
