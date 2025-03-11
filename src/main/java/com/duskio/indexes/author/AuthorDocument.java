package com.duskio.indexes.author;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.indexes.alternatename.AlternateNameDocument;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "author")
@Getter @Setter
@Builder @AllArgsConstructor @ToString
public class AuthorDocument extends AuditableWithID {

    @Field(type = FieldType.Text)
    private String authorName;

    private List<AlternateNameDocument> alternateNames;
}
