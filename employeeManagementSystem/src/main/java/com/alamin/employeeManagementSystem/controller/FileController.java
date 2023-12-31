package com.alamin.employeeManagementSystem.controller;

import com.alamin.employeeManagementSystem.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class FileController {
    private final FileService fileService;
    @PostMapping("/import")
    public ResponseEntity<?> importCustomerData(@RequestParam("file") MultipartFile importFile) {
        fileService.fileImport(importFile);
        return new ResponseEntity<>("baseResponse", HttpStatus.OK);
    }
}
