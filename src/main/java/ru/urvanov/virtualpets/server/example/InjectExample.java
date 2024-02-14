package ru.urvanov.virtualpets.server.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class InjectExample {

    private List<String> names;

    private Map<String, BigDecimal> numberSumMap;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Map<String, BigDecimal> getNumberSumMap() {
        return numberSumMap;
    }

    public void setNumberSumMap(Map<String, BigDecimal> numberSumMap) {
        this.numberSumMap = numberSumMap;
    }

}
