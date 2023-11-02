package em.file;

import em.file.domain.File;
import em.file.infrastructure.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;


    public void uploadFiles(Long parent, ParentType parentType, List<MultipartFile> files) {
        if(!files.isEmpty()){
            for (MultipartFile file : files) {

            }
        }
    }

    public Map<ParentType, List<File>> findByParentIdAndParentType(Long parentId, ParentType parentType) {
        Map<ParentType, List<File>> result = new HashMap<>();
        List<File> files = fileRepository.findByParentIdAndParentType(parentId, parentType);
        files.forEach(
                file -> {
            if(!result.containsKey(file.getParentType())){
                result.put(file.getParentType(), new ArrayList<>());
            }
            result.get(file.getParentType()).add(file);
                }
        );
        return result;
    }
}
