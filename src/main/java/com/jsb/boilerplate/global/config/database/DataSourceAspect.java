package com.jsb.boilerplate.global.config.database;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceAspect {

    @Around("execution(* com.jsb.boilerplate.service..*Service.*(..))")
    public Object setDataSourceType(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        if (isReadOnly(methodName)) {
            DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
            log.debug("DataSource Switch: READ | Method: {}", methodName);
        } else {
            DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
            log.debug("DataSource Switch: WRITE | Method: {}", methodName);
        }

        try {
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }

    private boolean isReadOnly(String methodName) {
        return methodName.startsWith("find") ||
               methodName.startsWith("get") ||
               methodName.startsWith("select") ||
               methodName.startsWith("count") ||
               methodName.startsWith("is") ||
               methodName.startsWith("exists") ||
               methodName.startsWith("load");
    }
}
