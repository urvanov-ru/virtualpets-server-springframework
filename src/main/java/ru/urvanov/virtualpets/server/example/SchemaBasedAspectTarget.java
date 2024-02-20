package ru.urvanov.virtualpets.server.example;

public interface SchemaBasedAspectTarget {

    void methodThatNeedsBefore(String name, int sum);

    void methodThatNeedsAfter(String name, int sum);

    double methodThatNeedsAfterReturn(String name, int sum);

    void methodThatNeedsThrow(String name, int sum) throws SchemaBasedAspectException;

    double methodThatNeedsAround(String name, int sum);


}
