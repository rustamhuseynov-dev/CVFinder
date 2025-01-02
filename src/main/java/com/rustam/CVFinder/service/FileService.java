package com.rustam.CVFinder.service;

import com.rustam.CVFinder.dto.FileUploadDto;
import com.rustam.CVFinder.dto.request.CvFilterRequest;
import com.rustam.CVFinder.util.FileProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileProcessor fileProcessor;

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

    public List<String> filterCvsBySkill(CvFilterRequest cvFilterRequest) {
        List<String> filteredCvs = new ArrayList<>();

        for (String filePath : cvFilterRequest.getFilePaths()) {
            String text = fileProcessor.extractTextFromPdf(filePath);

            if (!hasRequiredSkill(text, cvFilterRequest.getRequiredSkill())) {
                return Collections.emptyList();
            }

            if (!hasRequiredLanguage(text,cvFilterRequest.getRequiredLanguage())){
                return Collections.emptyList();
            }

            filteredCvs.add(filePath);
        }
        return filteredCvs;
    }

    private boolean hasRequiredSkill(String text, String requiredSkill) {
        return text.contains("Skills") && text.contains(requiredSkill);
    }

    private boolean hasRequiredLanguage(String text, String requiredLanguage) {
        return text.contains("Languages") && text.contains(requiredLanguage);
    }
}
