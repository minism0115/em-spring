package em.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {
    @Pointcut("@annotation(em.common.AuthorizationHeader)")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void Authorization(JoinPoint joinPoint){
        // header의 id값 체크

    }
}
