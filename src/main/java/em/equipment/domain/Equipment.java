package em.equipment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import em.common.AuditingFields;
import em.company.domain.Company;
import em.equipment.dto.UpdateEquipmentRequest;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"modelNameId", "version"})
        }
)
@IdClass(Equipment.EquipmentId.class)
public class Equipment extends AuditingFields implements Persistable<String> {

    @Id
    @Column(name = "equipment_id", columnDefinition = "VARCHAR2(36)")
    @Comment("ID")
    private String id;

    @Id
    private int version;

    private String modelNameId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public EquipmentId getCompositeId(){
        return EquipmentId.of(id, version);
    }

    public void update(UpdateEquipmentRequest request) {
        this.modelNameId = request.getEquipmentName();
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class EquipmentId implements Serializable {
        @NonNull
        private String id;
        @NonNull
        private int version;
    }
}
