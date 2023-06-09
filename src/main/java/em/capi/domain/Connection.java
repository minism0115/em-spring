package em.capi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Connection {

    private String url;
    private HttpClient httpClient;

    public Connection(String url) {
        this.url = url;
        this.httpClient = HttpClient.newHttpClient();
    }

    public CompletableFuture<String> getVersion() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(this.url + "/version"))
                .build();

        CompletableFuture<HttpResponse<String>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return future.thenApply(HttpResponse::body);
    }

    public CompletableFuture<Module> getModule(long slotNumber) throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(this.url + "/modules/" + slotNumber))
                .build();

        CompletableFuture<HttpResponse<String>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return future.thenApply(response -> {

            ObjectMapper objectMapper = new ObjectMapper();
            ModuleInfo moduleInfo;
            try {
                moduleInfo = objectMapper.readValue(response.body(), ModuleInfo.class);
                return new Module(this.url, moduleInfo.getSlotNumber(), moduleInfo.getSerialNumber());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to map response.body() to ModuleInfo object");
            }
        });
    }

    public CompletableFuture<List<Module>> getModules() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(this.url + "/modules"))
                .build();

        CompletableFuture<HttpResponse<String>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return future.thenApply(response -> {

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> moduleInfos = objectMapper.readValue(response.body(), List.class);
                return moduleInfos.stream().map(moduleInfo -> {
                    return new Module(this.url, ((Integer) moduleInfo.get("slotNumber")).longValue(),
                            moduleInfo.get("serialNumber").toString());
                }).collect(Collectors.toList());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to map response.body() to ModuleInfos object");
            }
        });
    }

    public static class ModuleInfo {

        private long slotNumber;
        private String serialNumber;

        public long getSlotNumber() {
            return slotNumber;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSlotNumber(long slotNumber) {
            this.slotNumber = slotNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
