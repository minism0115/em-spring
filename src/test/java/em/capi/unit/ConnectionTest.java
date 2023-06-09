package em.capi.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import em.capi.domain.Connection;
import em.capi.domain.Module;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ConnectionTest {

    private Connection connection = new Connection("https://softcamp.co.kr");

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("버전 정보를 가져오는 API 가 정상적으로 값을 반환했을 때 정상적으로 동작이 완료됩니다.")
    public void testGetVersion_whenNormalRequest_thenSuccess()
            throws IOException, InterruptedException, URISyntaxException, ExecutionException {

        // given
        CompletableFuture<HttpResponse<String>> responseFuture = new CompletableFuture<>();

        HttpResponse<String> response = mock(HttpResponse.class);
        given(response.body()).willReturn("1.0.0");

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

        connection.setHttpClient(mockHttpClient);

        String version = connection.getVersion().get();

        assertThat(version).isEqualTo("1.0.0");
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("인자를 포함하여 GET Modules API 호출 시 정상적으로 Modules를 얻습니다.")
    public void testGetModules_whenUseAllParam_thenSuccess()
            throws IOException, InterruptedException, URISyntaxException, ExecutionException {

        // given
        CompletableFuture<HttpResponse<String>> responseFuture = new CompletableFuture<>();

        HttpResponse<String> response = mock(HttpResponse.class);

        String sampleReponseBody = "{\"slotNumber\":1,\"serialNumber\":\"serialNumberTest1\"}";
        given(response.body()).willReturn(
                sampleReponseBody);

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

        connection.setHttpClient(mockHttpClient);

        // when
        Module module = connection.getModule(1111L).get();

        // then
        assertThat(module).isNotNull();
        assertThat(module.getSerialNumber()).isNotNull().isEqualTo("serialNumberTest1");
        assertThat(module.getSlotNumber()).isNotNull().isEqualTo(1);

    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("인자없이 GET Modules API 호출 시 정상적으로 Modules를 얻습니다.")
    public void testGetModules_whenNonParam_thenSuccess()
            throws IOException, InterruptedException, URISyntaxException, ExecutionException {

        // given
        CompletableFuture<HttpResponse<String>> responseFuture = new CompletableFuture<>();

        HttpResponse<String> response = mock(HttpResponse.class);

        String sampleReponseBody = "[{\"slotNumber\":1,\"serialNumber\":\"serialNumberTest1\"},{\"slotNumber\":2,\"serialNumber\":\"serialNumberTest2\"},{\"slotNumber\":3,\"serialNumber\":\"serialNumberTest3\"}]";
        given(response.body()).willReturn(
                sampleReponseBody);

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

        connection.setHttpClient(mockHttpClient);

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> sampleModuleInfos = objectMapper.readValue(sampleReponseBody, List.class);

        List<Module> sampleModules = sampleModuleInfos.stream().map(moduleInfo -> {
            return new Module("https://softcamp.co.kr", ((Integer) moduleInfo.get("slotNumber")).longValue(),
                    moduleInfo.get("serialNumber").toString());
        }).collect(Collectors.toList());

        // when
        List<Module> modules = connection.getModules().get();

        // then
        int equalCount = 0;
        for (Module module : modules) {
            for (Module sampleModule : sampleModules) {
                if (module.getSerialNumber().equals(sampleModule.getSerialNumber())
                        && module.getSlotNumber() == sampleModule.getSlotNumber() && module.getUrl().equals(
                        sampleModule.getUrl())) {

                    equalCount++;
                }
            }
        }
        assertThat(equalCount).isEqualTo(3);

    }
}
