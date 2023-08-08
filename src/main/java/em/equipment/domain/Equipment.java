package em.equipment.domain;

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
                @UniqueConstraint(columnNames = {"equipmentName", "version"})
        }
)
@IdClass(Equipment.EquipmentId.class)
public class Equipment extends AuditingFields implements Persistable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "equipment_id")
    @Comment("ID")
    private Long id;

    @Id
    private int version;

    private String equipmentName;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public EquipmentId getCompositeId(){
        return EquipmentId.of(id, version);
    }

    public void update(UpdateEquipmentRequest request) {
        this.equipmentName = request.getEquipmentName();
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
        private Long id;
        @NonNull
        private int version;
    }
}
