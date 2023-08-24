package em.equipment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import em.common.AuditingFields;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EquipmentItem extends AuditingFields implements Persistable<String> {

    @Id
    @Column(name = "equipment_item_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    @Comment("일련번호")
    private String serialNo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "equipment_id", referencedColumnName = "equipment_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    private Equipment equipment;

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
