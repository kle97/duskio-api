package com.duskio.features.review;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ReviewId implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "edition_id", nullable = false)
    private Long editionId;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReviewId that)) {
            return false;
        }
        return Objects.equals(editionId, that.editionId) && Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        if (editionId != null && profileId != null) {
            return Objects.hash(editionId, profileId);
        } else {
            return super.hashCode();
        }
    }
}
