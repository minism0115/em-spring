package em.capi.unit;

import em.capi.domain.Module;
import em.capi.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
public class ModuleTest {

    private Module module = new Module("http://localhost", 1L, "serialNumberTest1234");

    @Test
    @DisplayName("토큰 정보를 요청하는 api를 호출 시 정상적으로 tokenInfo 를 얻습니다")
    public void testGetTokenInfo_whenNormalRequest_thenSuccess()
            throws URISyntaxException, InterruptedException, ExecutionException {

        // given
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);

        given(mockHttpResponse.body())
                .willReturn("{\"flags\":[\"Rng\",\"WriteProtected\",\"LoginRequired\"],\"message\":\"testMessage\"}");

        CompletableFuture<HttpResponse<String>> mockResponseFuture = new CompletableFuture<>();

        mockResponseFuture.completeAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : completed work!");
            return mockHttpResponse;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);
        module.setHttpClient(mockHttpClient);

        given(mockHttpClient.sendAsync(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).willReturn(mockResponseFuture);

        // when
        CompletableFuture<TokenInfo> tokenInfoFuture = module.getTokenInfo();

        System.out.println(Thread.currentThread().getName() + " : called tokenInfoFuture.get()");
        TokenInfo tokenInfo = tokenInfoFuture.get();

        List<TokenFlag> expectedFlags = new ArrayList<>();
        expectedFlags.add(TokenFlag.Rng);
        expectedFlags.add(TokenFlag.WriteProtected);
        expectedFlags.add(TokenFlag.LoginRequired);

        // then
        assertThat(tokenInfo).isNotNull();
        assertThat(tokenInfo.getFlags()).isNotNull().isEqualTo(expectedFlags);
        assertThat(tokenInfo.getMessage()).isNotNull().isNotEmpty();

    }

    @Test
    @DisplayName("사용자들의 id들을 요청하는 api 를 호출 시 정상적으로 값을 얻어옵니다")
    public void testGetUserIds_whenNormalRequest_thenSuccess()
            throws InterruptedException, ExecutionException, URISyntaxException {

        // given
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);

        given(mockHttpResponse.body())
                .willReturn("[\"test_user_id_1\",\"test_user_id_2\",\"test_user_id_3\"]");

        CompletableFuture<HttpResponse<String>> mockResponseFuture = new CompletableFuture<>();

        mockResponseFuture.completeAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : completed work!");
            return mockHttpResponse;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);
        module.setHttpClient(mockHttpClient);

        given(mockHttpClient.sendAsync(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).willReturn(mockResponseFuture);

        // when
        CompletableFuture<List<String>> userIdsFuture = module.getUserIds();

        System.out.println(Thread.currentThread().getName() + " : called userIdsFuture.get()");
        List<String> userIds = userIdsFuture.get();

        List<String> expectedUserIds = new ArrayList<>();
        expectedUserIds.add("test_user_id_1");
        expectedUserIds.add("test_user_id_2");
        expectedUserIds.add("test_user_id_3");

        // then
        assertThat(userIds).isNotNull().isEqualTo(expectedUserIds);
    }

    @Test
    @DisplayName("vids 를 요청하는 api 를 호출 시 정상적으로 값을 얻어옵니다.")
    public void testGetVids_whenNormalRequest_thenSuccess()
            throws InterruptedException, ExecutionException, URISyntaxException {

        // given
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);

        given(mockHttpResponse.body())
                .willReturn("[\"test_vid_1\",\"test_vid_2\",\"test_vid_3\"]");

        CompletableFuture<HttpResponse<String>> mockResponseFuture = new CompletableFuture<>();

        mockResponseFuture.completeAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : completed work!");
            return mockHttpResponse;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);
        module.setHttpClient(mockHttpClient);

        given(mockHttpClient.sendAsync(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).willReturn(mockResponseFuture);

        // when
        CompletableFuture<List<String>> vidsFuture = module.getVids();

        System.out.println(Thread.currentThread().getName() + " : called vidsFuture.get()");
        List<String> vids = vidsFuture.get();

        List<String> expectedVids = new ArrayList<>();
        expectedVids.add("test_vid_1");
        expectedVids.add("test_vid_2");
        expectedVids.add("test_vid_3");

        // then
        assertThat(vids).isNotNull().isEqualTo(expectedVids);
    }

    @Test
    @DisplayName("인증을 요청하는 api 를 호출 시 정상적인 Token 값을 얻습니다.")
    public void testAuth_whenUseAllParm_thenSuccess() throws URISyntaxException, IOException, InterruptedException, ExecutionException {

        // given
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);

        given(mockHttpResponse.body())
                .willReturn("{\"accessToken\":\"accessTokenTest1\",\"refreshToken\":\"refreshToeknTest2\"}");

        CompletableFuture<HttpResponse<String>> mockResponseFuture = new CompletableFuture<>();

        mockResponseFuture.completeAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : completed work!");
            return mockHttpResponse;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);
        module.setHttpClient(mockHttpClient);

        given(mockHttpClient.sendAsync(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).willReturn(mockResponseFuture);

        // when
        CompletableFuture<Session> sessionFuture = module.auth("1q2w3e4r", new AuthOptions("testUserId", "1111qqqq"));

        System.out.println(Thread.currentThread().getName() + " : called sessionFuture.get()");
        Session session = sessionFuture.get();

        // then
        assertThat(session).isNotNull();
        assertThat(session.getUrl()).isNotNull().isEqualTo("http://localhost");
        assertThat(session.getToken()).isNotNull();
        assertThat(session.getToken().getAccessToken()).isNotNull().isEqualTo("accessTokenTest1");
        assertThat(session.getToken().getRefreshToken()).isNotNull().isEqualTo("refreshToeknTest2");


    }
}
