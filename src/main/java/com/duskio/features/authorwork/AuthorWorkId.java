package com.duskio.features.authorwork;

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
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class AuthorWorkId implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    @Column(name = "work_id", nullable = false)
    private Long workId;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof AuthorWorkId that)) {
            return false;
        }
        return Objects.equals(workId, that.workId) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        if (workId != null && authorId != null) {
            return Objects.hash(workId, authorId);
        } else {
            return super.hashCode();
        }
    }
}
