package com.rustam.CVFinder.controller;

import com.rustam.CVFinder.dto.FileUploadDto;
import com.rustam.CVFinder.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

//    @PostMapping(path = "/filter")
//    public ResponseEntity<List<String>> fileUpload( FileUploadDto fileUploadDto){
//        return new ResponseEntity<>(fileService.fileUpload(fileUploadDto), HttpStatus.OK);
//    }
}
