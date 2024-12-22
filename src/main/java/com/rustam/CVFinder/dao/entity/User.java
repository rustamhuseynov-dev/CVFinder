package com.rustam.CVFinder.dao.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.File;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("USER")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUser {

    private String username;

    private File enterYourCV;
}
