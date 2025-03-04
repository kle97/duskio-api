package com.duskio.features.worksubject;

import com.duskio.common.entity.Auditable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
}
