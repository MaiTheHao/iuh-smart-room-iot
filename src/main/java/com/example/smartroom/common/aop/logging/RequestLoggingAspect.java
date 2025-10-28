package com.example.smartroom.common.aop.logging;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import com.example.smartroom.common.enumeration.LogType;

/**
 * Aspect để ghi log tất cả các request đến Controller và RestController.
 * Tự động ghi lại thông tin request, response, status code, và thời gian thực thi.
 */
@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yy - HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restfulControllerMethods() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *) && !within(@org.springframework.web.bind.annotation.RestController *)")
    public void viewControllerMethods() {
    }

    @Around("restfulControllerMethods()")
    public Object logAroundRest(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRequestAndProceed(joinPoint, LogType.REST);
    }

    @Around("viewControllerMethods()")
    public Object logAroundView(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRequestAndProceed(joinPoint, LogType.VIEW);
    }

    /**
     * Phương thức lõi để xử lý log.
     * Được gọi bởi cả logAroundRest và logAroundView.
     *
     * @param joinPoint Pointcut
     * @param logType   Kiểu log (LogType.REST hoặc LogType.VIEW) để thêm vào tiền tố log.
     * @return Kết quả của phương thức controller.
     * @throws Throwable
     */
    private Object logRequestAndProceed(ProceedingJoinPoint joinPoint, LogType logType) throws Throwable {
        Instant startTime = Instant.now();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String formattedTime = TIME_FORMATTER.format(startTime);
        String prefix = "[" + logType.name() + "]";

        log.info("\n========================= Start of {} Request ========================", logType.name());

        log.info(
                "\n{} REQUEST\n" +
                "* Class.Method : {}.{}\n" +
                "* URL          : {}\n" +
                "* HTTP Method  : {}\n" +
                "* Start Time   : {}",
                prefix,
                className,
                methodName,
                request.getRequestURL(),
                request.getMethod(),
                formattedTime
        );

        if (log.isDebugEnabled()) {
            log.debug(
                    "\n{} Request Details:\n" +
                    "* Headers : {}\n" +
                    "* Params  : {}",
                    prefix,
                    getHeadersInfo(request),
                    request.getParameterMap()
            );
        }

        Object result = joinPoint.proceed();

        long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();
        Integer status = getResponseStatus();

        log.info(
                "\n{} RESPONSE\n" +
                "* Class.Method : {}.{}\n" +
                "* Status       : {}\n" +
                "* Duration     : {} ms",
                prefix,
                className,
                methodName,
                status != null ? status : "unknown",
                duration
        );

        if (log.isDebugEnabled()) {
            if (logType == LogType.REST) {
                log.debug("\n{} Response Body: {}", prefix, result);
            } else {
                log.debug("\n{} Response (View Name / Model): {}", prefix, result);
            }
        }

        log.info("\n========================== End of {} Request =========================\n", logType.name());

        return result;
    }

    private Integer getResponseStatus() {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            return (response != null) ? response.getStatus() : null;
        } catch (Exception e) {
            log.warn("Không thể lấy HTTP response status. {}", e.getMessage());
            return null;
        }
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        if (request == null) {
            return Collections.emptyMap();
        }
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        request::getHeader
                ));
    }
}
