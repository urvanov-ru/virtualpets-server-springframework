package ru.urvanov.virtualpets.server.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={AbstractExampleConfig.class})
public class AbstractExampleTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractExampleTest.class);
    
    @Test
    public void test() {
        logger.info("test");
    }
}

