package com.duskio.features.author;

import com.duskio.features.common.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Author extends Auditable {

        @Id
        private int authorId;

        private String authorName;

        private String birthDate;

        private String deathDate;

        private String authorDate;

        private String biography;

        private String photo;

        private String olKey;
}
