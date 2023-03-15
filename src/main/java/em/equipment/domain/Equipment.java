package em.equipment.domain;

import lombok.*;

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
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    private int version;

    private String modeName;

    private String companyId;

    public EquipmentId getCompositeId(){
        return EquipmentId.of(id, version);
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
