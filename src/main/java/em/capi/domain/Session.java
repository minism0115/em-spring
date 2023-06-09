package em.capi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Session {

    @NonNull
    private String url;

    @Nullable
    private Token token;

    @NonNull
    private HttpClient httpClient;

    public Session() {
        this.url = "";
        this.token = new Token();
        this.httpClient = Objects.requireNonNull(HttpClient.newHttpClient(), "httpClient is a required value.");
    }

    public Session(String url) {
        Objects.requireNonNull(url, "The parameter url is a required value.");

        this.url = url;
        this.token = null;
        this.httpClient = Objects.requireNonNull(HttpClient.newHttpClient(), "httpClient is a required value.");
    }

    public Session(String url, Token token) {
        Objects.requireNonNull(url, "The parameter url is a required value.");

        this.url = url;
        this.token = token;
        this.httpClient = Objects.requireNonNull(HttpClient.newHttpClient(), "httpClient is a required value.");
    }

    public static Session deserialize(String serializedData) throws JsonProcessingException {
        String decodedData = new String(Base64.getDecoder().decode(serializedData));

        return new ObjectMapper().readValue(decodedData, Session.class);
    }

    public String serialize() throws JsonProcessingException {

        Map<String, Object> session = new HashMap<>();
        session.put("url", url);
        session.put("token", this.token);

        return Base64.getEncoder().encodeToString(new ObjectMapper().writeValueAsString(session).getBytes());
    }

    public CompletableFuture<String> getKdnId() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                        this.url + "/session/kdnid"))
                .header("Authorization",
                        "Bearer " + Optional.ofNullable(
                                        this.token).orElseGet(() -> {
                                    throw new NullPointerException(
                                            "Token field is null.");
                                })
                                .getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return future.thenApply(HttpResponse::body);
    }

    public CompletableFuture<Module> getModule() throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                        this.url + "module"))
                .header("Authorization",
                        "Bearer " + Optional.ofNullable(
                                        this.token).orElseGet(() -> {
                                    throw new NullPointerException(
                                            "Token field is null.");
                                })
                                .getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        return future.thenApply(response -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Module module = objectMapper.readValue(response.body(), Module.class);
                module.setUrl(this.url);
                return module;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to map response.body() to Module object.");
            }
        });
    }

    public CompletableFuture<byte[]> publicSignEncrypt(List<String> kdnIds, byte[] plainText)
            throws URISyntaxException {

        StringBuilder flatKdnIds = new StringBuilder("");

        Iterator<String> iter = kdnIds.iterator();
        while (iter.hasNext()) {
            flatKdnIds.append(Base64.getEncoder().encodeToString(iter.next().getBytes()));
            if (iter.hasNext()) {
                flatKdnIds.append(",");
            }
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(plainText))
                .uri(new URI(
                        this.url + "/session/public-sign-encrypt"))
                .headers("Authorization",
                        "Bearer " + Optional.ofNullable(
                                        this.token).orElseGet(() -> {
                                    throw new NullPointerException(
                                            "Token field is null.");
                                })
                                .getAccessToken(),
                        "KDNID",
                        flatKdnIds.toString(),
                        "Content-Type",
                        "application/octet-stream")
                .build();

        CompletableFuture<HttpResponse<byte[]>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofByteArray());

        return future.thenApply(HttpResponse::body);
    }

    public CompletableFuture<byte[]> publicDecryptVerify(byte[] cipherText) throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(
                        cipherText))
                .uri(new URI(
                        this.url + "/session/public-decrypt-verify"))
                .headers("Authorization",
                        "Bearer " + Optional.ofNullable(
                                        this.token).orElseGet(() -> {
                                    throw new NullPointerException(
                                            "Token field is null.");
                                })
                                .getAccessToken(),
                        "Content-Type",
                        "application/octet-stream")
                .build();

        CompletableFuture<HttpResponse<byte[]>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofByteArray());

        return future.thenApply(HttpResponse::body);
    }

    public CompletableFuture<byte[]> encrypt(byte[] plainText) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(plainText))
                .uri(new URI(this.url + "/session/symmetric-encrypt"))
                .headers("Authorization",
                        "Bearer " + Optional.ofNullable(this.token)
                                .orElseThrow(() -> new NullPointerException(
                                        "Token field is null."))
                                .getAccessToken(),
                        "Content-Type",
                        "application/octet-stream")
                .build();

        CompletableFuture<HttpResponse<byte[]>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofByteArray());

        return future.thenApply(HttpResponse::body);
    }

    public CompletableFuture<byte[]> decrypt(byte[] cipherText) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(cipherText))
                .uri(new URI(this.url + "/session/symmetric-decrypt"))
                .headers("Authorization",
                        "Bearer " + Optional.ofNullable(this.token)
                                .orElseThrow(() -> new NullPointerException(
                                        "Token field is null."))
                                .getAccessToken(),
                        "Content-Type",
                        "application/octet-stream")
                .build();

        CompletableFuture<HttpResponse<byte[]>> future = this.httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofByteArray());

        return future.thenApply(HttpResponse::body);
    }

    public void close() throws URISyntaxException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(new URI(this.url + "/session"))
                .header("Authorization",
                        "Bearer " + Optional.ofNullable(this.token)
                                .orElseThrow(() -> new NullPointerException(
                                        "Token field is null."))
                                .getAccessToken())
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        Objects.requireNonNull(url, "The parameter url is a required value.");

        this.url = url;
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {

        Objects.requireNonNull(httpClient, "The parameter httpClient is a required value.");

        this.httpClient = httpClient;
    }
}
