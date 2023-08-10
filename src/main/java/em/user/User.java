package em.user;

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
public class User extends AuditingFields implements Persistable<String> {

    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    @Comment("사용자명")
    private String userName;

    public void create(CreateUserRequest request){

    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
