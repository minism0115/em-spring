package em.equipment.domain;

import em.common.AuditingFields;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EquipmentName extends AuditingFields implements Persistable<String> {

    @Id
    @Column(name = "equipment_name_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    @Comment("장비명")
    private byte[] equipmentName;

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
