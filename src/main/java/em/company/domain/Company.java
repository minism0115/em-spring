package em.company.domain;

import em.common.AuditingFields;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends AuditingFields implements Persistable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    private String companyName;

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
