package em.file;

import em.common.AuditingFields;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends AuditingFields {

    @Id
    @Column(name = "fild_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    private Long parentId;

    private ParentType parentType;

    private String originName;

    private String fileName;

    private String fileType;

    private Long fileSize;
}
