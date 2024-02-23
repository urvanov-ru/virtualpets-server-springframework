package ru.urvanov.virtualpets.server.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame;

@Aspect
public class AnnotationBasedAspect {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationBasedAspect.class);
    
    @Before("execution(* ru.urvanov.virtualpets.server.controller.site.*.*(..))")
    public void beforeSitePage(JoinPoint joinPoint) {
        logger.info("Requesting controller {}.{}, arguments count {}.",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs().length);
    }
    
    @AfterReturning(
            value = "execution(* ru.urvanov.virtualpets.server.service.HiddenObjectsServiceImpl.getGameInfo(..))",
            returning = "hiddenObjectsGame")
    public void afterReturningHiddenObjectGameInfo(
            JoinPoint joinPoint,
            HiddenObjectsGame hiddenObjectsGame) {
        logger.info("""
                HiddenObjectsGame finished. \
                Arguments count {}. \
                Result {}. \
                """,
                joinPoint.getArgs().length,
                hiddenObjectsGame);
    }
    
    @AfterThrowing(
            value = "execution(* ru.urvanov.virtualpets.server.service.RoomServiceImpl.openBoxNewbie(..))",
            throwing = "throwableParameter"
            )
    public void openBoxNewbieException(
            JoinPoint joinPoint,
            Throwable throwableParameter) {
        logger.info("""
                
                OpenBoxNewbie thrown an exception. \
                Arguments count {}. \
                Throwing {} \
                """,
            joinPoint.getArgs().length,
            throwableParameter);
    }
    
    @After("execution(* ru.urvanov.virtualpets.server.service.RoomServiceImpl.move*(..))")
    public void afterBuildingMoved(JoinPoint joinPoint) {
        logger.info("""
                After building moved. \
                Arguments count {}. \
                """,
                joinPoint.getArgs().length);
    }
    
    @Around("execution(* ru.urvanov.virtualpets.server.service.RoomServiceImpl.getBuildMenuCosts(..))")
    public void aroundGetBuildMenuCostsAround(
            ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        logger.info("around getBuildMenuCosts advice");
        proceedingJoinPoint.proceed();
    }
    

}

