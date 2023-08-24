package em;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;

@Log4j2
public class RequestBodyDecryptWrapper extends HttpServletRequestWrapper {
    // 가로챈 데이터를 가공하여 담을 final 변수
    private final String requestDecryptBody;

    public RequestBodyDecryptWrapper(HttpServletRequest request) throws IOException, DecoderException {
        super(request);

        String requestHashData = requestDataByte(request); // Request Data 가로채기
        String decodeTemp = requestBodyDecode(requestHashData); // Request Data Hex 디코드

        log.info("인코딩 데이터: " + requestHashData);
        log.info("디코딩 데이터: " + decodeTemp);

        requestDecryptBody = decodeTemp;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestDecryptBody.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    //==request Body 가로채기==//
    private String requestDataByte(HttpServletRequest request) throws IOException {
        byte[] rawData = new byte[128];
        InputStream inputStream = request.getInputStream();
        rawData = IOUtils.toByteArray(inputStream);
        return new String(rawData);
    }

    //==request Body Hex 디코딩==//
    private String requestBodyDecode(String requestHashData) throws DecoderException {
        return HexDecodeToString(requestHashData);
    }

    //==Request Data Decode (Hex Decode)==//
    public static String HexDecodeToString(String encodeText) throws DecoderException {
        return new String(Hex.decodeHex(encodeText.toCharArray()));
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
