package em.equipment.domain;

import em.board.domain.AuditingFields;
import em.company.domain.Company;
import em.equipment.dto.UpdateEquipmentRequest;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"modeName", "version"})
        }
)
@IdClass(Equipment.EquipmentId.class)
public class Equipment extends AuditingFields {

    @Id
    @GeneratedValue
    @Column(name = "equipment_id")
    @Comment("ID")
    private Long id;

    @Id
    private int version;

    private String modeName;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public EquipmentId getCompositeId(){
        return EquipmentId.of(id, version);
    }

    public void update(UpdateEquipmentRequest request) {
        this.modeName = request.getModeName();
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
