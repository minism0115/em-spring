package em.capi.integration;

import em.capi.domain.Connection;
import em.capi.domain.Module;
import em.capi.domain.Session;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    @Test
    @DisplayName("실제 통신을 통한 정상 동작 테스트 (encrypt, decrypt)")
    public void testActualCommunication_whenUseEncryptDecrypt_thenSuccess()
            throws InterruptedException, ExecutionException, URISyntaxException, IOException {

        Connection connection = new Connection("http://localhost:30080");

        for (Module module : connection.getModules().get()) {

            Session session = module.auth("softcamp1!", null).get();

            String kdnid = session.getKdnId().get();

            System.out.println("kdnid : " + kdnid);

            byte[] encrypted = session.encrypt("12345".getBytes()).get();

            byte[] decrypted = session.decrypt(encrypted).get();

            String result = new String(decrypted);

            assertThat(result).isEqualTo("12345");
        }
    }

    @Test
    @DisplayName("실제 통신을 통한 정상 동작 테스트 (publicSignEncrypt, publicDecryptVerify)")
    public void testActualCommunication_whenUsePublicSignEncryptPublicDecryptVerify_thenSuccess()
            throws InterruptedException, ExecutionException, URISyntaxException, IOException {

        Connection connection = new Connection("http://localhost:30080");

        for (Module module : connection.getModules().get()) {

            Session session = module.auth("softcamp1!", null).get();

            String kdnid = session.getKdnId().get();
            List<String> kdnIds = new ArrayList<>();
            kdnIds.add(kdnid);

            System.out.println("kdnid : " + kdnid);

            byte[] encrypted = session.publicSignEncrypt(kdnIds, "12345".getBytes()).get();

            byte[] decrypted = session.publicDecryptVerify(encrypted).get();

            String result = new String(decrypted);

            assertThat(result).isEqualTo("12345");
        }
    }

    @Test
    @DisplayName("HttpUrlConnection 사용한 테스트")
    public void testCommunicationUsingHttpUrlConnection() throws IOException {
        URL url = new URL("http://localhost:30080/modules");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
    @Test
    @DisplayName("OkHttp 사용한 테스트")
    public void testCommunicationUsingOkHttp() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:30080/modules")
                .build();
        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
