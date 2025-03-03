package com.duskio.features.worksubject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"work_id", "subject_id"})})
public class WorkSubjectId implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Column(nullable = false)
    private Long workId;

    @Column(nullable = false)
    private Long subjectId;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof WorkSubjectId that)) {
            return false;
        }
        return Objects.equals(workId, that.workId) && Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        if (workId != null && subjectId != null) {
            return Objects.hash(workId, subjectId);
        } else {
            return super.hashCode();
        }
    }
}
