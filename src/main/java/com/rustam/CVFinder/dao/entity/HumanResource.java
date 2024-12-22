package com.rustam.CVFinder.dao.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("HUMAN_RESOURCE")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HumanResource extends BaseUser{

    private String username;
}
