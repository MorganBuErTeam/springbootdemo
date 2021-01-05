package com.test.demo.aspect;

import com.test.demo.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Api耗时切面计时器
 *
 * @author NotoChen
 */
@Slf4j
@Aspect
@Component
public class ApiTimerAspect {

    /**
     * 打印格式
     */
    private final static String LOG_TEMPLATE = "{}.{} execute in : {} ms [{}]";

    public static void print(ProceedingJoinPoint joinPoint, long in) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        BaseEnum.getListByEnum(ApiElapsedStatus.class)
                .stream()
                .filter(status -> status.key.test(in))
                .findAny()
                .orElse(ApiElapsedStatus.unknown)
                .print(status ->
                        status.value.accept(LOG_TEMPLATE, new Object[]{className, methodName, in, status.msg}));
    }

    /**
     * trace ：< 100ms                  流畅
     * debug  ：100ms ~ 200ms           可用
     * info  ：500ms ~ 1000ms          卡顿
     * warn ：2000ms ~ 5000ms         阻塞
     * error ：> 5000ms               [超时或不可用]
     *
     * @param joinPoint 切入节点
     * @return 响应数据
     */
    @SneakyThrows
    @Around("execution(* com.test.demo.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        StopWatch stopWatch = new StopWatch(this.getClass().getSimpleName());
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        print(joinPoint, stopWatch.getTotalTimeMillis());
        return proceed;
    }


    @Getter
    @AllArgsConstructor
    enum ApiElapsedStatus implements BaseEnum<Predicate<Long>, BiConsumer<String, Object[]>> {
        /**
         *
         */
        unknown((in -> false), log::trace, "未知"),

        affluent((in -> in < 100), log::debug, "极速"),

        available((in -> 100 < in && in < 200), log::debug, "流畅"),

        lag((in -> 200 < in && in < 1000), log::info, "迟延"),

        block((in -> 1000 < in && in < 5000), log::warn, "阻塞"),

        timeOut((in -> 5000 < in), log::error, "超时");

        /**
         * 耗时区间过滤
         */
        private final Predicate<Long> key;
        /**
         * 日志打印级别
         */
        private final BiConsumer<String, Object[]> value;
        /**
         * API耗时测评
         */
        private final String msg;

        public void print(Consumer<ApiElapsedStatus> consumer) {
            consumer.accept(this);
        }

    }
}
