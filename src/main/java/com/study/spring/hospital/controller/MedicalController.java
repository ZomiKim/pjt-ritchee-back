package com.study.spring.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.spring.hospital.dto.MedicalListDto;
import com.study.spring.hospital.repository.MedicalReopository;

@RestController
public class MedicalController {

    @Autowired
    MedicalReopository mReop;

    @GetMapping("/api/appmlist")
    public List<MedicalListDto> getMedicalList() {
        return mReop.findAllMedicalByIdDesc();
    }
}
