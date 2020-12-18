package com.test.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class KthLogAspect {

    //作用于加了注解的方法
    @Pointcut("@annotation(com.test.demo.annotation.KthLog)")
    private void pointcut() {}

    //作用于指定路径所有方法
    @Pointcut("execution(public * com.test.demo.controller..*.*(..))")
    public void controllerMethod() {
    }

//    @Around("pointcut()")
//    public void advice(JoinPoint joinPoint) {
//        System.out.println("注解作用的方法名: " + joinPoint.getSignature().getName());
//        System.out.println("所在类的简单类名: " + joinPoint.getSignature().getDeclaringType().getSimpleName());
//        System.out.println("所在类的完整类名: " + joinPoint.getSignature().getDeclaringType());
//        System.out.println("目标方法的声明类型: " + Modifier.toString(joinPoint.getSignature().getModifiers()));
//    }


//    @Around("controllerMethod()")
    @Around("pointcut()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args=joinPoint.getArgs();
        for(Object object:args){
            //输入参数
            logInfo(object,0,joinPoint);
        }
        Object object;
        try{
            object=joinPoint.proceed();
        }catch (Exception e){
            log.warn("Errors\t===>>\t"+e.getClass().getName()+":"+e.getMessage());
            throw e;
        }
        //输出参数
        logInfo(object,1,joinPoint);
    }

    public void logInfo(Object object,Integer i,ProceedingJoinPoint joinPoint){
        //获取执行方法的包名+类名
        String className=joinPoint.getTarget().getClass().getName();
        //获取实例和方法
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        Method method=signature.getMethod();
        String logType=i==0?"输入参数：":"输出参数：";
        if(null==object){
            log.info(className+"."+method.getName()+"----"+logType+"为null");
        }else{
            log.info(className+"."+method.getName()+"----"+logType+object.toString());
        }
    }

}
