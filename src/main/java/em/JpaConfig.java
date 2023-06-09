package em;

import em.common.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableJpaAuditing
@Configuration
public class JpaConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**"); // for everywhere!
    }

    //    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(BoardPrincipal.class::cast)
//                .map(BoardPrincipal::getUsername);
//    }

}
