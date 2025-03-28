package org.example.filter;

import org.example.statistics.DataStatistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class DataFilter {

    public final DataStatistics statistics = new DataStatistics();
    private enum ItemType {
        INTEGER,
        DOUBLE,
        STRING
    }

    private ItemType identifyItemType(Scanner scanner) {
        if (scanner.hasNextInt()) return ItemType.INTEGER;
        else if (scanner.hasNextDouble()) return ItemType.DOUBLE;
        else return ItemType.STRING;
    }

    public void processFile(String filepath) {
        File inputFile = new File(filepath);
        if (!inputFile.exists()){
            System.err.printf("Ошибка: Файл '%s' не существует", inputFile);
            System.exit(-1);
        }

        try (Scanner scanner = new Scanner(inputFile)) {
            scanner.useLocale(Locale.US);
            while (scanner.hasNext()) {
                ItemType item = identifyItemType(scanner);
                switch (item) {
                    case INTEGER -> {
                        int value = scanner.nextInt();
                        statistics.addInteger(value);
                    }
                    case DOUBLE -> {
                        double value = scanner.nextDouble();
                        statistics.addDouble(value);
                    }
                    case STRING -> {
                        String value = scanner.next();
                        statistics.addString(value);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Integer> getIntegers() {
        return statistics.getIntegers();
    }

    public List<Double> getDoubles() {
        return statistics.getDoubles();
    }

    public List<String> getStrings() {
        return statistics.getStrings();
    }
}
