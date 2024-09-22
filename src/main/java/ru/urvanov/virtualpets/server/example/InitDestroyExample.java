package ru.urvanov.virtualpets.server.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Пример управления жизненным циклом бина.
 */
public class InitDestroyExample
        implements InitializingBean,
                   DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(
            InitDestroyExample.class);
    
    /////////////////////////////////////////////////////////////////////
    // Аннотации из jakarta.annotation
    
    @PostConstruct
    public void jakartaAnnotationPostConstruct() {
        logger.info("(1) @PostConstruct from jakarta.annotation");
    }
    
    @PreDestroy
    public void jakartaAnnotationPreDestroy() {
        logger.info("(4) @PreDestroy from jakarta.annotation");
    }
    
    /////////////////////////////////////////////////////////////////////
    // Методы из InitializingBean и DisposableBean
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.error(
                """
                (2) afterPropertiesSet from \
                InitializingBean interface""");
    }
    
    @Override
    public void destroy() throws Exception {
        logger.info("(5) destroy from DisposableBean interface");
    }


    
    /////////////////////////////////////////////////////////////////////
    // Методы init-method из  destroy-method из XML-конфигурации бина
    
    public void initMethodFromXmlConfiguration() {
        logger.info("(3) init-method from XML-configuration");
    }
    
    public void destroyMethodFromXmlConfiguration() {
        logger.info("(6) destroy-method from XML-configuration");
    }

}
