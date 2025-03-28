package org.example.statistics;

import java.util.ArrayList;
import java.util.List;

public class DataStatistics {

    private final List<Integer> integers = new ArrayList<>();
    private final List<Double> doubles = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    public void addInteger(int value) {
        integers.add(value);
    }

    public void addDouble(double value) {
        doubles.add(value);
    }

    public void addString(String value) {
        strings.add(value);
    }

    public String getShortStats() {
        return String.format(
                """
                -----------------------
                | Type     | Count    |
                -----------------------
                | Integer  | %-4d     |
                | Double   | %-4d     |
                | String   | %-4d     |
                -----------------------
                """,
                integers.size(), doubles.size(), strings.size()
        );
    }

    public String getFullStats() {
        int intCount = integers.size();
        int intMax = integers.stream().max(Integer::compare).orElse(0);
        int intMin = integers.stream().min(Integer::compare).orElse(0);
        int intSum = integers.stream().mapToInt(Integer::intValue).sum();
        double intAverage = integers.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        int doubleCount = doubles.size();
        double doubleMax = doubles.stream().max(Double::compare).orElse(0.0);
        double doubleMin = doubles.stream().min(Double::compare).orElse(0.0);
        double doubleSum = doubles.stream().mapToDouble(Double::doubleValue).sum();
        double doubleAverage = doubles.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        int stringCount = strings.size();
        int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
        int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);

        return String.format(
                """
                Полная статистика:
                ------------------------------------------------------------------------
                | Type     | Count     | Min       | Max       | Sum       | Average   |
                ------------------------------------------------------------------------

                | Integer  | %-9d | %-9d | %-9d | %-9d | %-9.2f |
                | Double   | %-9d | %-9.2f | %-9.2f | %-9.2f | %-9.2f |
                | String   | %-9d | %-9d | %-9d |           |           |
                ------------------------------------------------------------------------

                """,
                intCount, intMin, intMax, intSum, intAverage,
                doubleCount, doubleMin, doubleMax, doubleSum, doubleAverage,
                stringCount, minLength, maxLength
        );
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public List<Double> getDoubles() {
        return doubles;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void printToConsole(String output) {
        System.out.println(output);
    }

}
