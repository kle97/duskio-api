package com.duskio.features.worksubject;

import com.duskio.common.entity.Auditable;
import com.duskio.features.subject.Subject;
import com.duskio.features.work.Work;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class WorkSubject extends Auditable {
    
    @EmbeddedId
    private WorkSubjectId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workId")
    @JoinColumn(name = "work_id")
    Work work;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    Subject subject;
}
