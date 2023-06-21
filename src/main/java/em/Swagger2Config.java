package em;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger2Config {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/api/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring API")
                        .description("Spring API 명세서입니다.")
                        .version("v0.0.1")
                );
    }

    // authorization test
//    @Bean
//    public GroupedOpenApi headerApi(){
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/api/**")
//                .group("auth-header")
//                .addOpenApiCustomiser(customHeaderOpenApiCustomiser())
//                .build();
//    }

    // 모든 API에 header 강제
//    @Bean
//    public OpenApiCustomiser customHeaderOpenApiCustomiser() {
//        Parameter userToken = new Parameter()
//                .name("token")
//                .in("header")
//                .required(true)
//                .schema(new StringSchema());
//        return openApi -> openApi.getPaths().values().forEach(
//                operation -> operation
//                        .addParametersItem(userToken)
//        );
//    }
}
