package com.duskio.features.worksubject;

import com.duskio.common.entity.Auditable;
import com.duskio.common.entity.Default;
import com.duskio.features.subject.Subject;
import com.duskio.features.work.Work;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class WorkSubject extends Auditable {
    
    @EmbeddedId
    private WorkSubjectId id;

    @Default
    public WorkSubject(Work work, Subject subject) {
        this.id = new WorkSubjectId(work.getId(), subject.getId());
    }
}
