package ru.urvanov.virtualpets.server.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SchemaBasedAspectTest extends AbstractExampleTest {
    
    @Autowired
    private SchemaBasedAspectTarget schemaBasedAspectTarget;
    
    @Test
    void before() {
        schemaBasedAspectTarget.methodThatNeedsBefore("Василий", 1000);
    }


}
