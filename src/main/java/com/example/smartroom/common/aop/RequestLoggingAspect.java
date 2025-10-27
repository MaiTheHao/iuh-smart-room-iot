package com.example.smartroom.common.aop;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Aspect dùng để ghi log các request đến các controller trong ứng dụng.
 * <p>
 * Lớp này sử dụng AOP để tự động ghi lại thông tin về các request như:
 * - Tên lớp và phương thức được gọi
 * - URL và phương thức HTTP của request
 * - Thời gian bắt đầu và thời gian thực thi của request
 * <p>
 * Thông tin log giúp dễ dàng theo dõi, kiểm tra và debug các request đến hệ thống.
 *
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)" + " || within(@org.springframework.stereotype.Controller *)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant startTime = Instant.now();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String formattedTime = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(startTime);

        log.info("===>> Incoming request: {}.{} | URL: {} | HTTP Method: {} | Start Time: {}",
                className,
                methodName,
                request.getRequestURL(),
                request.getMethod(),
                formattedTime);

        Object result = joinPoint.proceed();

        long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();
        log.info("<<=== Completed request: {}.{} | Duration: {} ms", className, methodName, duration);

        return result;
    }
}
