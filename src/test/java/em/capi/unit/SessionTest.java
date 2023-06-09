package em.capi.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import em.capi.domain.Module;
import em.capi.domain.Session;
import em.capi.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SessionTest {

    @Test
    @DisplayName("정상적인 Session 객체를 직렬화했을 때 성공합니다")
    public void testSerialize_whenNormalSession_thenSuccess()
            throws JsonProcessingException, MalformedURLException, UnsupportedEncodingException {

        // given
        Session session = new Session("https://softcamp.co.kr",
                new Token("111111abcdqwe", "123213333asdqwqew"));

        // when
        String serialized = session.serialize();
        Session deserialized = Session.deserialize(serialized);

        // then
        assertThat(deserialized.getToken().getAccessToken()).isEqualTo(session.getToken().getAccessToken());
        assertThat(deserialized.getToken().getRefreshToken()).isEqualTo(session.getToken().getRefreshToken());
        assertThat(deserialized.getUrl()).isEqualTo(session.getUrl());
    }

    @Test
    @DisplayName("Session의 생성자에 매개변수를 null 로 초기화 시도 시 실패합니다")
    public void testConstructor_whenParmIsNull_thenFail() throws JsonProcessingException {

        assertThatThrownBy(() -> {
            new Session(null, null);
        }).isInstanceOf(NullPointerException.class);

    }

    @Test
    @DisplayName("Session의 token 필드가 null 일때 정상적으로 직렬화가 성공합니다")
    public void testSerialize_whenTokenIsNull_thenSuccess() throws JsonProcessingException {
        // given
        Session session = new Session("https://softcamp.co.kr",
                null);

        // when
        String serialized = session.serialize();
        Session deserialized = Session.deserialize(serialized);

        // then
        assertThat(deserialized.getToken()).isNull();
        assertThat(deserialized.getUrl()).isEqualTo(session.getUrl());

    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("정상적인 URL 요청으로 KdnId를 얻는데 성공합니다")
    public void testGetKdnId_whenNormalRequest_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<String>> responseFuture = new CompletableFuture<>();

        HttpResponse<String> response = mock(HttpResponse.class);
        given(response.body()).willReturn("tempKdnId");

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when
        CompletableFuture<String> future = session.getKdnId();

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        String kdnid = future.get();

        // then
        assertThat(kdnid).isNotNull().isNotEmpty().isEqualTo("tempKdnId");
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("정상적인 URL 요청으로 Module를 얻는데 성공합니다")
    public void testGetModule_whenNormalRequest_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<String>> responseFuture = new CompletableFuture<>();

        HttpResponse<String> response = mock(HttpResponse.class);
        given(response.body()).willReturn("{\"slotNumber\":1,\"serialNumber\":\"serialNumberTest123\"}");

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when
        CompletableFuture<Module> future = session.getModule();

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        Module module = future.get();

        // then
        assertThat(module).isNotNull();
        assertThat(module.getUrl()).isNotNull().isEqualTo(tempUrl);
        assertThat(module.getSerialNumber()).isNotNull().isEqualTo("serialNumberTest123");
        assertThat(module.getSlotNumber()).isNotNull().isEqualTo(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("공개키 암호화 API 호출 시 정상적으로 암호화된 값을 얻습니다")
    public void testPublicSignEncrypt_whenUseAllNormalParam_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<byte[]>> responseFuture = new CompletableFuture<>();

        HttpResponse<byte[]> response = mock(HttpResponse.class);
        given(response.body()).willReturn("testResponseEncrypted".getBytes());

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        List<String> kdnids = new ArrayList<>();
        kdnids.add("test_kdnid_1_테스트");
        kdnids.add("test_kdnid_2_테스트");
        kdnids.add("test_kdnid_3_테스트");

        // when
        CompletableFuture<byte[]> future = session.publicSignEncrypt(kdnids, "testRequest".getBytes());

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        byte[] encrypted = future.get();

        // then
        assertThat(encrypted).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("공개키 복호화 API 호출 시 정상적으로 복호화된 값을 얻습니다.")
    public void testPublicDecryptVerify_whenUseAllNormalParam_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<byte[]>> responseFuture = new CompletableFuture<>();

        HttpResponse<byte[]> response = mock(HttpResponse.class);
        given(response.body()).willReturn("testResponseDecrypted".getBytes());

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when
        CompletableFuture<byte[]> future = session.publicDecryptVerify("testRequest".getBytes());

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        byte[] decrypted = future.get();

        // then
        assertThat(decrypted).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("암호화 API 호출 시 정상적으로 암호화된 값을 얻습니다.")
    public void testEncrypt_whenUseAllNormalParam_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<byte[]>> responseFuture = new CompletableFuture<>();

        HttpResponse<byte[]> response = mock(HttpResponse.class);
        given(response.body()).willReturn("testResponseEncrypted".getBytes());

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when
        CompletableFuture<byte[]> future = session.encrypt("testRequest".getBytes());

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        byte[] encrypted = future.get();

        // then
        assertThat(encrypted).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("복호화 API 호출 시 정상적으로 복호화된 값을 얻습니다.")
    public void testDecrypt_whenUseAllNormalParam_thenSuccess()
            throws JsonProcessingException, URISyntaxException, InterruptedException, ExecutionException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<byte[]>> responseFuture = new CompletableFuture<>();

        HttpResponse<byte[]> response = mock(HttpResponse.class);
        given(response.body()).willReturn("testResponseDecrypted".getBytes());

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(responseFuture);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when
        CompletableFuture<byte[]> future = session.decrypt("testRequest".getBytes());

        System.out.println(Thread.currentThread().getName() + " : called future.get()");
        byte[] decrypted = future.get();

        // then
        assertThat(decrypted).isNotNull();
    }


    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("세션 종료 API 호출 시 성공적으로 요청이 전송됩니다.")
    public void testSessionClose_whenNormalSession_thenSuccess()
            throws URISyntaxException, InterruptedException, IOException {

        // given
        String tempUrl = "https://softcamp.co.kr";
        Token tempToken = new Token("testAccessToken", "testRefreshToken");
        CompletableFuture<HttpResponse<Void>> responseFuture = new CompletableFuture<>();

        HttpResponse<Void> response = mock(HttpResponse.class);
        given(response.statusCode()).willReturn(200);

        responseFuture.completeAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : virtual work in progress..");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : completed work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        });

        HttpClient mockHttpClient = mock(HttpClient.class);

        given(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .willReturn(response);

        Session session = new Session(tempUrl);
        session.setHttpClient(mockHttpClient);
        session.setToken(tempToken);

        // when&then
        session.close();

    }
}
