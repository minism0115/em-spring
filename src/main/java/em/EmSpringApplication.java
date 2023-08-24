package em;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ConfigurationPropertiesScan
@ServletComponentScan // for filter setting, 필터에서 @WebFilter, 서블릿에서 @WebServlet, 리스너에서 @WebListener 등의 어노테이션을 사용할 수 있도록 해준다.
@SpringBootApplication
@EnableCaching
public class EmSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmSpringApplication.class, args);
	}

}
