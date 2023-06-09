package em.capi.util;

import java.net.http.HttpResponse;

public class HttpStatus {

    public static <T> boolean isOk(HttpResponse<T> httpResponse) {
        return httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300;
    }
}
