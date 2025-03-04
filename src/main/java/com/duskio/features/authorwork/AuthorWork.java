package com.duskio.features.authorwork;

import com.duskio.common.entity.Auditable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class AuthorWork extends Auditable {
    
    @EmbeddedId
    private AuthorWorkId id;
}
