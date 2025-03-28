package org.example.main;

import org.apache.commons.cli.*;
import org.example.handlers.FileHandler;
import org.example.handlers.OptionsHandler;

public class Main {
    public static void main(String[] args) {
        try {
            Options options = new Options();
            setupOptions(options);

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            OptionsHandler optionsHandler = new OptionsHandler(cmd);
            optionsHandler.processOptions();

            FileHandler fileHandler = new FileHandler(
                    optionsHandler.getOutputPath(),
                    optionsHandler.getPrefix(),
                    optionsHandler.hasAppendMode(),
                    optionsHandler.hasShortStatisticsMode(),
                    optionsHandler.hasFullStatisticsMode(),
                    optionsHandler.getPositionalArgs()
            );
            fileHandler.processFiles();


        } catch (ParseException e) {
            System.err.println("Ошибка парсинга:");
            System.err.println(e.getMessage());
        }
    }

    public static void setupOptions(Options options) {
        options.addOption(new Option("o", true, "Путь для выходных файлов."));
        options.addOption(new Option("p", true, "Префикс для выходных файлов"));
        options.addOption(new Option("a", false, "Добавление в существующий файл"));
        options.addOption(new Option("f", false, "Полная статистика"));
        options.addOption(new Option("s", false, "Короткая статистика"));
    }
}