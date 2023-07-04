package com.onetoone.mapping.aspects;

import com.onetoone.mapping.annotations.MeasureTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
@Aspect
@Slf4j
public class MeasureTimeAspect {

    private static boolean isStringEmptyOrNull(String traceId) {
        return !Objects.isNull(traceId) && !traceId.equals("");
    }

    @Around("execution(* *(..)) && @annotation(measureTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, MeasureTime measureTime) throws Throwable {
        String traceId = null, uri = null, requestMethod = null;
        Pattern p =
                Pattern.compile(
                        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        for (Object attribute : joinPoint.getArgs()) {
            try {
                if (attribute instanceof String && p.matcher((String) attribute).find()) {
                    traceId = (String) attribute;
                }
                if (attribute instanceof HttpServletRequest) {
                    uri = ((HttpServletRequest) attribute).getRequestURI();
                    requestMethod = ((HttpServletRequest) attribute).getMethod();
                }
            } catch (Exception e) {
                log.warn("Exception {}", e.getMessage());
            }
        }
        if (isStringEmptyOrNull(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("traceId: " + traceId + ", className = {}, methodName = {}, uri = {}, requestMethodType = {}, timeTaken in ms = {}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getMethod().getName(), uri, requestMethod, endTime);
        return result;
    }
}
