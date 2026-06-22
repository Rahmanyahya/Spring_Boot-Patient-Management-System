package com.pm.patientservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.exception.ErrorExceptions;
import com.pm.patientservice.helper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
            .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) throw new ErrorExceptions("Email already exists", HttpStatus.CONFLICT);

        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ErrorExceptions("Patient not found", HttpStatus.NOT_FOUND));

        if (!patientRequestDTO.getEmail().equals(patient.getEmail()) && patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id))
             throw new ErrorExceptions("Email is already exists", HttpStatus.CONFLICT);

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        
        Patient newPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(newPatient);
    }

    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ErrorExceptions("Patient not found", HttpStatus.NOT_FOUND));

        patientRepository.delete(patient);
    }

}
