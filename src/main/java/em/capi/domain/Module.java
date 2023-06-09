package em.capi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Module {

    @NonNull
    private String url;

    private long slotNumber;
    private String serialNumber;

    @NonNull
    private HttpClient httpClient;

    public Module() {
        this.url = "";
        this.httpClient = Objects.requireNonNull(HttpClient.newHttpClient(), "httpClient is a required value.");
    }


    public Module(String url, long slotNumber, String serialNumber) {

        Objects.requireNonNull(url, "The parameter url is a required value.");

        this.url = url;
        this.slotNumber = slotNumber;
        this.serialNumber = serialNumber;
        this.httpClient = Objects.requireNonNull(HttpClient.newHttpClient(), "httpClient is a required value.");
    }

    public CompletableFuture<TokenInfo> getTokenInfo() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                        this.url + "/modules/" + this.slotNumber + "/token-info"))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = this.httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return responseFuture.thenApply(response -> {
            Objects.requireNonNull(response.body(), "response body is null ");

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return objectMapper.readValue(response.body(), TokenInfo.class);
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<List<String>> getUserIds() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                        this.url + "/modules/" + this.slotNumber + "/users"))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = this.httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return responseFuture.thenApply(response -> {
            ObjectMapper objectMapper = new ObjectMapper();

            List<String> userIds = new ArrayList<>();

            try {
                userIds = objectMapper.readValue(response.body(), List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return userIds;
        });

    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<List<String>> getVids() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                        this.url + "/modules/" + this.slotNumber + "/vids"))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = this.httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return responseFuture.thenApply(response -> {
            ObjectMapper objectMapper = new ObjectMapper();

            List<String> vids = new ArrayList<>();

            try {
                vids = objectMapper.readValue(response.body(), List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return vids;
        });
    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<Session> auth(String pin, AuthOptions options)
            throws URISyntaxException, IOException {

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("pin", pin);
        requestBodyMap.put("options", options);

        ObjectMapper objectMapper = new ObjectMapper();

        String requestBodyJson = objectMapper.writeValueAsString(requestBodyMap);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .uri(new URI(
                        this.url + "/modules/" + this.slotNumber + "/auth"))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return responseFuture.thenApply(response -> {

            Map<String, Object> responseBodyMap = new HashMap<>();
            try {
                responseBodyMap = objectMapper.readValue(response.body(), Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return new Session(this.url, new Token(Optional.ofNullable(responseBodyMap.get(
                    "accessToken")).orElseGet(() -> {
                throw new IllegalArgumentException("accessToken is null");
            }).toString(),
                    Optional.ofNullable(responseBodyMap.get("refreshToken")).orElseGet(() -> {
                        throw new IllegalArgumentException("refreshToken is null");
                    }).toString()));
        });

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        Objects.requireNonNull(url, "The parameter url is a required value.");

        this.url = url;
    }

    public Long getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Long slotNumber) {
        this.slotNumber = slotNumber;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {

        Objects.requireNonNull(httpClient, "The parameter httpClient is a required value.");

        this.httpClient = httpClient;
    }

    public void setSlotNumber(long slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
