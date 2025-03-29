package com.duskio.features.profile;

import java.time.LocalDate;

public record ProfileRequest(String userId,
                             String username,
                             String displayName,
                             String biography,
                             String location,
                             LocalDate dateOfBirth,
                             String website,
                             String profilePicture,
                             String backgroundPicture) {
}
