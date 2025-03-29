package com.duskio.features.profile;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder(toBuilder = true)
public class ProfileEntityResponse extends ProfileResponse {

    private String biography;

    private String location;

    private LocalDate dateOfBirth;

    private String website;

    private String backgroundPicture;
}
