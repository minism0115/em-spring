package em;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
        String token = request.getHeader("token");
        // TODO: token deserialize > kdnId 식별
        String kdnId = "";
        // TODO: kdnId로 사용자 Id 조회
        String userId = "user2023";
        return Optional.of(userId);
    }
}
