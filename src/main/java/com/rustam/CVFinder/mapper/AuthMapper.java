package com.rustam.CVFinder.mapper;

import com.rustam.CVFinder.dao.entity.HumanResource;
import com.rustam.CVFinder.dao.entity.User;
import com.rustam.CVFinder.dto.request.UpdateRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.dto.response.UpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface AuthMapper {

    AuthResponse toDto(User user);

    AuthResponse toDto(HumanResource humanResource);

    UpdateResponse toUpdateResponse(User user);

    UpdateResponse toUpdateHumanResponse(HumanResource humanResource);
}
