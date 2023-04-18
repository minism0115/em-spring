package em.board.dto;

import em.board.domain.Article;
import em.board.domain.UserAccount;
import lombok.Data;

/**
 * A DTO for the {@link em.board.domain.Article} entity
 */
@Data
public class ArticleDto {
    private final Long id;
    private final UserAccountDto userAccountDto;
    private final String title;
    private final String content;
    private final String hashtag;
    private final String createdAt;
    private final String createdBy;
    private final String updatedAt;
    private final String updatedBy;

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, String hashtag) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, String createdAt, String createdBy, String modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content,
                hashtag
        );
    }

}