package org.example.handlers;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.example.filter.DataFilter;

public class FileHandler {

    private final DataFilter filter = new DataFilter();
    String statisticsOutput;

    private final String outputPath;
    private final String filePrefix;
    private final boolean appendMode;
    private final boolean shortStatsMode;
    private final boolean fullStatsMode;
    private final List<String> inputFiles;

    public FileHandler(String outputPath, String filePrefix,
                       boolean appendMode, boolean shortStatsMode,
                       boolean fullStatsMode,List<String> inputFiles)
    {
        this.outputPath = (outputPath != null) ? outputPath : ".";
        this.filePrefix = (filePrefix != null) ? filePrefix : "";
        this.appendMode = appendMode;
        this.shortStatsMode = shortStatsMode;
        this.fullStatsMode = fullStatsMode;
        this.inputFiles = inputFiles;
    }

    public void processFiles() {
        for (String filename : inputFiles) {
            filter.processFile(expandPath(filename));
        }

        writeIfNotEmpty(filter.getIntegers(), "integers.txt");
        writeIfNotEmpty(filter.getDoubles(), "doubles.txt");
        writeIfNotEmpty(filter.getStrings(), "strings.txt");

        identifyStatsMode(shortStatsMode, fullStatsMode);
        filter.statistics.printToConsole(statisticsOutput);
   }

    String expandPath(String path) {
        return Paths.get(replaceTildeWithHome(path))
                .toAbsolutePath()
                .normalize()
                .toString();
    }

    void writeIfNotEmpty(List<?> list, String filename) {
        if (!list.isEmpty()){
            writeToFile(expandPath(outputPath), filePrefix + filename, list);
        }
    }

   private void identifyStatsMode(boolean shortStatsMode, boolean fullStatsMode) {
       if (shortStatsMode)
           statisticsOutput = filter.statistics.getShortStats();
       if (fullStatsMode)
           statisticsOutput = filter.statistics.getFullStats();
   }

    String replaceTildeWithHome(String path) {
        return path.startsWith("~") ? System.getProperty("user.home") + path.substring(1) : path;
    }

    <T> void writeToFile(String filepath, String filename, List<T> data) {
        File outputFile = new File(filepath, filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, appendMode))) {
            Class<?> type = data.getFirst().getClass();
            switch (type.getSimpleName()) {
                case "Integer", "Double", "String" -> {
                    for (T item : data) {
                        writer.write(item.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
