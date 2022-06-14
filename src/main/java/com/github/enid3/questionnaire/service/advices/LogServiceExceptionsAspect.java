package com.github.enid3.questionnaire.service.advices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogServiceExceptionsAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void anyService(){}

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @AfterThrowing(value = "anyService() && anyPublicMethod()", throwing = "ex")
    public void logError(Throwable ex) throws Throwable {
        log.error(ex.getMessage());
        throw ex;
    }
}
