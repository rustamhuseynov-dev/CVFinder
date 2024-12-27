package com.rustam.CVFinder.service;

import com.rustam.CVFinder.dto.FileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {


    public String fileUpload(FileUploadDto fileUploadDto) {
        MultipartFile file = fileUploadDto.getFile();
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("D:/uploads/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return "Fayl ugurla yuklendi: " + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
