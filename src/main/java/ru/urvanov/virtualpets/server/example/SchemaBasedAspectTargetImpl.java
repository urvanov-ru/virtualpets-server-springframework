package ru.urvanov.virtualpets.server.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaBasedAspectTargetImpl implements SchemaBasedAspectTarget {
    
    private static final Logger logger = LoggerFactory.getLogger(SchemaBasedAspectTargetImpl.class);
    
    @Override
    public void methodThatNeedsBefore(String name, int sum) {
        logger.info("methodThatNeedsBefore name = {}, sum = {}", name, sum);
    }
    
    @Override
    public void methodThatNeedsAfter(String name, int sum) {
        logger.info("methodThatNeedsAfter name = {}, sum = {}", name, sum);
    }
    
    @Override
    public double methodThatNeedsAfterReturn(String name, int sum) {
        logger.info("methodThatNeedsAfterReturn name = {}, sum = {}", name, sum);
        return 100.1;
    }
    
    @Override
    public void methodThatNeedsThrow(String name, int sum) throws SchemaBasedAspectException {
        logger.info("methodThatNeedsThrow name = {}, sum = {}", name, sum);
        throw new SchemaBasedAspectException("just example");
        
    }
    
    @Override
    public double methodThatNeedsAround(String name, int sum) {
        logger.info("methodThatNeedsAround name = {}, sum = {}", name, sum);
        return -234.3465;
    }

}
