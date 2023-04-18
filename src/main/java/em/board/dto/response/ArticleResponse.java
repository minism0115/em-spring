package em.board.dto.response;

import em.board.dto.ArticleDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleResponse implements Serializable {
    private final Long id;
    private final String title;
    private final String content;
    private final String hashtag;
    private final String createdAt;
    private final String email;
    private final String nickname;

    public static ArticleResponse of(Long id, String title, String content, String hashtag, String createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname
        );
    }

}
