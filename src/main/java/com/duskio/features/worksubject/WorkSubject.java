package com.duskio.features.worksubject;

import com.duskio.common.entity.Auditable;
import com.duskio.features.subject.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter @ToString(callSuper = true)
public class WorkSubject extends Auditable {
    
    @EmbeddedId
    private WorkSubjectId id;

    @ManyToOne(optional = false)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")        
    Subject subject;
}
