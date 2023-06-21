package em;

import em.capi.domain.Connection;
import em.capi.domain.Session;
import em.capi.domain.Module;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

//@Component
//@Order(2)
public class octetStreamRequestBodyDecryptFilter implements Filter {
    /*
       octet-stream 요청에 대해서 filtering하는 클래스.
       content-type이 application/octet-stream 일 경우 이를 application/json 으로 변경,
       request body(이 때의 body는 hexadecimal binary)를 꺼내와 rest-capi를 통해 decrypt 한 뒤 wrapper로 감싸 필터체인에 전달한다.
   */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getMethod().equals("GET")) {
            // GET method면 넘어감
            chain.doFilter(request, response);
        } else if (!httpRequest.getContentType().equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            // octet-stream이 아니면 넘어감
            chain.doFilter(request, response);
        } else if (!httpRequest.getRequestURI().contains("/api")) {
            // /api에 대한 요청이 아니면 넘어감
            chain.doFilter(request, response);
        } else {
            byte[] requestBody = readRequestBody(httpRequest);
            byte[] modifiedRequestBody = null;
            try {
                // rest-capi에게 decrypt 요청
                modifiedRequestBody = decryptRequestBody(requestBody);
            } catch (ExecutionException | InterruptedException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
            HttpServletRequest requestWrapper = new RequestWrapper(httpRequest, modifiedRequestBody);
            chain.doFilter(requestWrapper, response);
        }

    }

    private byte[] readRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        return output.toByteArray();
    }

    private byte[] decryptRequestBody(byte[] encryptedRequestBody) throws ExecutionException, InterruptedException, URISyntaxException, IOException {
        /*
            rest-capi-svc가 실행 중이어야 함.
            ConnectException이 발생할 경우 netstat -ano | findstr ":30080"을 통해 어떤 아이피로 서비스 되고 있는지 확인 후 url 변경
        */

        Connection connection = new Connection("http://[::1]:30080");
//        Connection connection = new Connection("http://127.0.0.1:30080");
//        Connection connection = new Connection("http://localhost:30080");

        for (Module module : connection.getModules().get()) {
            Session session = module.auth("softcamp1!", null).get();
            String kdnid = session.getKdnId().get();
            List<String> kdnIds = new ArrayList<>();
            kdnIds.add(kdnid);

            System.out.println("kdnid : " + kdnid);
            System.out.println(Arrays.toString(encryptedRequestBody));

            byte[] decrypted = session.publicDecryptVerify(encryptedRequestBody).get();
//            System.out.println(new String(encryptedRequestBody));
//            System.out.println(new String(decrypted));
//            String result = new String(decrypted);
//            assert Arrays.equals(encryptedRequestBody, decrypted);
            return decrypted;
        }
        return null;
    }

    private static class RequestWrapper extends HttpServletRequestWrapper {
        private final byte[] modifiedBody;

        public RequestWrapper(HttpServletRequest request, byte[] modifiedBody) {
            super(request);
            this.modifiedBody = modifiedBody;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            // Create a new input stream with the modified body
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(modifiedBody);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public String getHeader(String name) {

            // application/octet-stream으로 온 요청을 application/json으로 변경.
            if (name.equalsIgnoreCase("Content-Type")) {
                return MediaType.APPLICATION_JSON_VALUE;
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {

            // application/octet-stream으로 온 요청을 application/json으로 변경.
            if (name.equalsIgnoreCase("Content-Type")) {
                return Collections.enumeration(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
            }
            return super.getHeaders(name);
        }
    }
}
