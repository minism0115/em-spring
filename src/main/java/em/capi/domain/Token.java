package em.capi.domain;

import lombok.NonNull;

import java.util.Objects;

public class Token {

    @NonNull
    private String accessToken;
    @NonNull
    private String refreshToken;

    public Token() {
        this.accessToken = "";
        this.refreshToken = "";
    }

    public Token(String accessToken, String refreshToken) {

        Objects.requireNonNull(accessToken, "The parameter accessToken is a required value.");
        Objects.requireNonNull(refreshToken, "The parameter refreshToken is a required value.");

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        Objects.requireNonNull(accessToken, "The parameter accessToken is a required value.");

        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        Objects.requireNonNull(refreshToken, "The parameter refreshToken is a required value.");

        this.refreshToken = refreshToken;
    }
}
