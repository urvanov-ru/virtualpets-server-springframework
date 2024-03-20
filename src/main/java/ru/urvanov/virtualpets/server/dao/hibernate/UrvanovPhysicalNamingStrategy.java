package ru.urvanov.virtualpets.server.dao.hibernate;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UrvanovPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1510016202928080147L;

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace( logicalName.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                String.join("_", parts),
                logicalName.isQuoted()
        );
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace( logicalName.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                String.join("_", parts),
                logicalName.isQuoted()
        );
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace( logicalName.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                String.join("_", parts),
                logicalName.isQuoted()
        );
    }

    private List<String> splitAndReplace(String name) {
        return Arrays.stream(splitByCharacterTypeCamelCase(name))
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isBlank))
                .map(p -> p.toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
    }

    private String[] splitByCharacterTypeCamelCase(String s) {
        return s.split( "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])" );
    }
}