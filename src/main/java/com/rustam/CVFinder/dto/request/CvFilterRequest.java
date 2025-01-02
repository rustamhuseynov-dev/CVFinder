package com.rustam.CVFinder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CvFilterRequest {
    private List<String> filePaths;
    private String requiredSkill;
    private String requiredLanguage;

}
