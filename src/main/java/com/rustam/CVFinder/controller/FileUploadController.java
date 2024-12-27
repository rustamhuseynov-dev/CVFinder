package com.rustam.CVFinder.controller;

import com.rustam.CVFinder.dto.FileUploadDto;
import com.rustam.CVFinder.dto.request.CvFilterRequest;
import com.rustam.CVFinder.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @PostMapping(path = "/upload")
    public ResponseEntity<String> fileUpload(@ModelAttribute FileUploadDto fileUploadDto){
        return new ResponseEntity<>(fileService.fileUpload(fileUploadDto), HttpStatus.OK);
    }

    @PostMapping(path = "/filter")
    public ResponseEntity<List<String>> fileUpload(@RequestBody CvFilterRequest cvFilterRequest){
        return new ResponseEntity<>(fileService.filterCvsBySkill(cvFilterRequest), HttpStatus.OK);
    }
}
