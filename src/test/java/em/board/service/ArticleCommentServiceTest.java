package em.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

//    @InjectMocks private ArticleCommentService sut;
//
//    @Mock private ArticleRepository articleRepository;
//    @Mock private ArticleCommentRepository articleCommentRepository;
//    @Mock private UserAccountRepository userAccountRepository;
//
//    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
//    @Test
//    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
//        // Given
//        Long articleId = 1L;
//        ArticleComment expected = createArticleComment("content");
//        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));
//
//        // When
//        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);
//
//        // Then
//        assertThat(actual)
//                .hasSize(1)
//                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
//        then(articleCommentRepository).should().findByArticle_Id(articleId);
//    }
//
//    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
//    @Test
//    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
//        // Given
//        ArticleCommentDto dto = createArticleCommentDto("댓글");
//        given(articleRepository.getById(dto.getArticleId())).willReturn(createArticle());
//        given(userAccountRepository.getById(dto.getUserAccountDto().getUserId())).willReturn(createUserAccount());
//        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
//
//        // When
//        sut.saveArticleComment(dto);
//
//        // Then
//        then(articleRepository).should().getById(dto.getArticleId());
//        then(userAccountRepository).should().getById(dto.getUserAccountDto().getUserId());
//        then(articleCommentRepository).should().save(any(ArticleComment.class));
//    }
//
//    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
//    @Test
//    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
//        // Given
//        ArticleCommentDto dto = createArticleCommentDto("댓글");
//        given(articleRepository.getById(dto.getArticleId())).willThrow(EntityNotFoundException.class);
//
//        // When
//        sut.saveArticleComment(dto);
//
//        // Then
//        then(articleRepository).should().getById(dto.getArticleId());
//        then(userAccountRepository).shouldHaveNoInteractions();
//        then(articleCommentRepository).shouldHaveNoInteractions();
//    }
//
//    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
//    @Test
//    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
//        // Given
//        String oldContent = "content";
//        String updatedContent = "댓글";
//        ArticleComment articleComment = createArticleComment(oldContent);
//        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
//        given(articleCommentRepository.getById(dto.getId())).willReturn(articleComment);
//
//        // When
//        sut.updateArticleComment(dto);
//
//        // Then
//        assertThat(articleComment.getContent())
//                .isNotEqualTo(oldContent)
//                .isEqualTo(updatedContent);
//        then(articleCommentRepository).should().getById(dto.getId());
//    }
//
//    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
//    @Test
//    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {
//        // Given
//        ArticleCommentDto dto = createArticleCommentDto("댓글");
//        given(articleCommentRepository.getById(dto.getId())).willThrow(EntityNotFoundException.class);
//
//        // When
//        sut.updateArticleComment(dto);
//
//        // Then
//        then(articleCommentRepository).should().getById(dto.getId());
//    }
//
//    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
//    @Test
//    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
//        // Given
//        Long articleCommentId = 1L;
//        String userId = "uno";
//        willDoNothing().given(articleCommentRepository).deleteByIdAndUserAccount_UserId(articleCommentId, userId);
//
//        // When
//        sut.deleteArticleComment(articleCommentId, userId);
//
//        // Then
//        then(articleCommentRepository).should().deleteByIdAndUserAccount_UserId(articleCommentId, userId);
//    }
//
//
//    private ArticleCommentDto createArticleCommentDto(String content) {
//        return ArticleCommentDto.of(
//                1L,
//                1L,
//                createUserAccountDto(),
//                content,
//                LocalDateTime.now(),
//                "uno",
//                LocalDateTime.now(),
//                "uno"
//        );
//    }
//
//    private UserAccountDto createUserAccountDto() {
//        return UserAccountDto.of(
//                "uno",
//                "password",
//                "uno@mail.com",
//                "Uno",
//                "This is memo",
//                LocalDateTime.now(),
//                "uno",
//                LocalDateTime.now(),
//                "uno"
//        );
//    }
//
//    private ArticleComment createArticleComment(String content) {
//        return ArticleComment.of(
//                Article.of(createUserAccount(), "title", "content", "hashtag"),
//                createUserAccount(),
//                content
//        );
//    }
//
//    private UserAccount createUserAccount() {
//        return UserAccount.of(
//                "uno",
//                "password",
//                "uno@email.com",
//                "Uno",
//                null
//        );
//    }
//
//    private Article createArticle() {
//        return Article.of(
//                createUserAccount(),
//                "title",
//                "content",
//                "#java"
//        );
//    }

}
