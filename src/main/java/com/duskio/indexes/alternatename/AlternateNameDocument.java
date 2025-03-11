package com.duskio.indexes.alternatename;

import com.duskio.common.entity.AuditableWithID;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "alternate_name")
@Getter @Setter
@Builder @AllArgsConstructor @ToString
public class AlternateNameDocument extends AuditableWithID {

    @Field(type = FieldType.Text)
    private String alternateName;
}
