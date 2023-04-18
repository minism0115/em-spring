package em.board.dto;

import em.board.domain.Article;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ArticleWithCommentsDto {
    private final Long id;
    private final UserAccountDto userAccountDto;
    private final Set<ArticleCommentDto> articleCommentDtos;
    private final String title;
    private final String content;
    private final String hashtag;
    private final String createdAt;
    private final String createdBy;
    private final String updatedAt;
    private final String updatedBy;

    public static ArticleWithCommentsDto of(Long id, UserAccountDto userAccountDto, Set<ArticleCommentDto> articleCommentDtos, String title, String content, String hashtag, String createdAt, String createdBy, String modifiedAt, String modifiedBy) {
        return new ArticleWithCommentsDto(id, userAccountDto, articleCommentDtos, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return new ArticleWithCommentsDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

}
