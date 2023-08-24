package em;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
//@Order(1)
//@WebFilter(urlPatterns = "/api/*")
public class DecodeEncodeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("---필터1 인스턴스 초기화---");
        Filter.super.init(filterConfig);
    }

    /*
      - 전/후 처리
      - Request, Response가 필터를 거칠 때 수행되는 메소드
      - chain.doFilter() 기점으로 request, response로 나뉜다
   */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();

        try {
            // 커스텀 래퍼적용
            RequestBodyDecryptWrapper requestWrapper = new RequestBodyDecryptWrapper(req);
            ResponseBodyEncryptWrapper responseWrapper = new ResponseBodyEncryptWrapper(res);
            log.info("---Request(" + requestURI + ") 필터1---");

            chain.doFilter(requestWrapper, responseWrapper);

            // Response Body Data 가지고 옴
            String responseMessage = responseWrapper.getDataStreamToString();

            System.out.println("암호화 전> " + responseMessage);
            System.out.println("암호화 후> " + responseBodyEncrypt(responseMessage));

            // Response 처리
            responseMessage = responseBodyEncrypt(responseMessage); // 암호화 메소드 호출(Hex)
            byte[] responseMessageBytes = responseMessage.getBytes("utf-8");
            int contentLength = responseMessageBytes.length;

            response.setContentLength(contentLength);
            response.getOutputStream().write(responseMessageBytes);
            response.flushBuffer(); // marks response as committed

            log.info("---Response(" + requestURI + ") 필터1---");
        } catch (Exception e) {
            // 디코딩 불가 예외 처리
            log.error(e);
            log.error("디코딩 불가능 합니다.");
            String ResponseMessage = "디코드 불가능한 데이터 입니다.";
            byte[] data = ResponseMessage.getBytes("utf-8");
            int count = data.length;
            res.setStatus(400);
            res.setContentLength(count);
            res.getOutputStream().write(data);
            res.flushBuffer();
        }
    }

    @Override
    public void destroy() {
        log.info("---필터1 인스턴스 종료---");
        Filter.super.destroy();
    }

    //==response Body 인코딩==//
    private String responseBodyEncrypt(String responseMessage) {
        return new String(Base64.encodeBase64(responseMessage.getBytes()));
    }
}
