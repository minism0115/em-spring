package em.board.dto;

import em.board.domain.Article;
import em.board.domain.ArticleComment;
import em.board.domain.UserAccount;
import lombok.Data;

/**
 * A DTO for the {@link em.board.domain.ArticleComment} entity
 */
@Data
public class ArticleCommentDto {
    private final Long id;
    private final Long articleId;
    private final UserAccountDto userAccountDto;
    private final String content;
    private final String createdAt;
    private final String createdBy;
    private final String updatedAt;
    private final String updatedBy;

    public static ArticleCommentDto of(Long articleId, UserAccountDto userAccountDto, String content) {
        return new ArticleCommentDto(null, articleId, userAccountDto, content, null, null, null, null);
    }

    public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, String createdAt, String createdBy, String modifiedAt, String modifiedBy) {
        return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(
                article,
                userAccount,
                content
        );
    }

}