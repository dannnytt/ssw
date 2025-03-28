package org.example.handlers;

import java.util.*;
import org.apache.commons.cli.*;

public class OptionsHandler {

    private final CommandLine cmd;

    public OptionsHandler(CommandLine cmd) {
        this.cmd = cmd;
    }

    public void processOptions() throws ParseException {
        checkDuplicateOptions();
        checkStatsOptions();
        processPositionalArgs();
    }

    private void checkDuplicateOptions() throws ParseException {
        Set<String> options = new HashSet<>();
        for (Option option: cmd.getOptions()) {
            String opt = option.getOpt();

            if (opt != null && !options.add(opt)) {
                throw new  ParseException(String.format("Ошибка: Дублирующаяся опция: -%s", opt));
            }
        }
    }

    private void checkStatsOptions() throws  ParseException {
        Set<String> options = new HashSet<>();
        for (Option option : cmd.getOptions()) {
            String opt = option.getOpt();

            if (opt.equals("f") || opt.equals("s")) {
                options.add(opt);
            }
        }

        if (options.size() > 1) {
            Iterator<String> iterator = options.iterator();
            String first = iterator.next();
            String second = iterator.next();
            throw new ParseException(String.format(
                    "Ошибка: Опции статистики(-%s и -%s) не могут использоваться вместе.", first, second));
        }
    }

    private void processPositionalArgs() throws ParseException {
        if (cmd.getArgs().length == 0) {
            throw new ParseException("Ошибка: Не указаны входные файлы.");
        }

        for (String arg : cmd.getArgs()) {
            if (!isTxtFile(arg)) {
                throw new ParseException(String.format(
                        "Ошибка: Неправильный формат файла: %s. Ожидаемый формат: .txt", arg));
            }
        }
    }

    boolean isTxtFile(String filename) {
        return filename.toLowerCase().endsWith(".txt");
    }

    public String getOutputPath() {
        return cmd.getOptionValue("o");
    }

    public String getPrefix() {
        return cmd.getOptionValue("p");
    }

    public boolean hasAppendMode() {
        return cmd.hasOption("a");
    }

    public boolean hasShortStatisticsMode() {
        return cmd.hasOption("s");
    }

    public boolean hasFullStatisticsMode() {
        return cmd.hasOption("f");
    }

    public List<String> getPositionalArgs() {
        return Arrays.stream(cmd.getArgs()).toList();
    }
}
