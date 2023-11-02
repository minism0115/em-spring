package em.file.infrastructure;

import em.file.domain.File;
import em.file.ParentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByParentIdAndParentType(Long parentId, ParentType parentType);
}
