package com.apitest.demo.controller;
import com.apitest.demo.model.KidData;
import com.apitest.demo.repository.KidDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kids")
public class KidDataController {
    @Autowired
    private KidDataRepository kidDataRepository;
    @GetMapping
    public List<KidData> getAllKids()
    {
        return kidDataRepository.findAll();
    }

    //
  /*  @PostMapping
    public String addKidData(@RequestBody KidData kidData) {
        if (kidData.getKidProfilePath() == null || kidData.getKidName() == null ||
                kidData.getFileId() == 0 || kidData.getSchool() == null ||
                kidData.getLikes() == 0 || kidData.getFilename() == null) {
            return "Please fill all fields correctly!";
        }

        kidDataRepository.save(kidData);  // Save data to the database
        return "Kid data added successfully!";*/
    @PostMapping
    public ResponseEntity<String> addKidData(@RequestBody KidData kidData) {
        if (isInvalid(kidData.getKidProfilePath()) ||
                isInvalid(kidData.getKidName()) ||
                kidData.getFileId() <= 0 ||
                isInvalid(kidData.getSchool()) ||
                kidData.getLikes() < 0 ||
                isInvalid(kidData.getFilename())) {

            return ResponseEntity.badRequest().body("Please fill all fields correctly!");
        }
        kidDataRepository.save(kidData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Kid data added successfully!");
    }
    private boolean isInvalid(String field) {
        return field == null || field.trim().isEmpty();
    }

}