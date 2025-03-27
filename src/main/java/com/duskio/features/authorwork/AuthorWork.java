package com.duskio.features.authorwork;

import com.duskio.common.entity.Auditable;
import com.duskio.features.author.Author;
import com.duskio.features.work.Work;
import jakarta.persistence.*;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workId")
    @JoinColumn(name = "work_id")
    Work work;
}
