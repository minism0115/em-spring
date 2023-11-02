package em.modelName.infrastructure;

import em.modelName.domain.ModelName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ModelNameRepository extends JpaRepository<ModelName, String> {

    @Query(value = "select mn from ModelName mn " +
            "where (:modelNameId is null or mn.id = :modelNameId) " +
            "and (:modelName is null or mn.modelName = :modelName)")
    ModelName findByModelNameIdAndModelName(@Param("modelNameId") String modelNameId,
                                            @Param("modelName") String modelName);

    Optional<ModelName> findByModelName(String name);
}
