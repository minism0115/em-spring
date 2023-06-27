package em.event.domain;

import em.common.AuditingFields;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends AuditingFields implements Persistable<String> {

    @Id
    @Column(name = "event_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    @Comment("이벤트명")
    private String eventName;

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
