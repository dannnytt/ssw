package org.example.handlers;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class OptionsHandlerTest {

    OptionsHandler optionsHandler;
    CommandLine cmd;

    @BeforeEach
    void setUp() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-o", "output.txt", "-p", "prefix"});
        optionsHandler = new OptionsHandler(cmd);
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption("o", "output", true, "output file");
        options.addOption("p", "prefix", true, "prefix string");
        options.addOption("a", "append", false, "append mode");
        options.addOption("s", "short-stats", false, "short statistics");
        options.addOption("f", "full-stats", false, "full statistics");
        return options;
    }

    @Test
    void testProcessOptions_NoDuplicateOptions() throws ParseException {
        Option option1 = new Option("o", "output", true, "output file");
        Option option2 = new Option("p", "prefix", true, "prefix string");

        cmd = new DefaultParser().parse(createOptions(), new String[]{"-o", "output.txt", "-p", "prefix123", "somefile.txt"});
        optionsHandler = new OptionsHandler(cmd);

        optionsHandler.processOptions();
    }

    @Test
    void testProcessOptions_DuplicateOptionThrowsException() throws ParseException {
        Option option1 = new Option("o", "output", true, "output file");
        Option option2 = new Option("o", "output", true, "output file");

        cmd = new DefaultParser().parse(createOptions(), new String[]{"-o", "output.txt", "-o", "output2.txt"});
        optionsHandler = new OptionsHandler(cmd);

        ParseException exception = assertThrows(ParseException.class, () -> {
            optionsHandler.processOptions();
        });
        assertTrue(exception.getMessage().contains("Ошибка: Дублирующаяся опция: -o"));
    }

    @Test
    void testProcessOptions_StatsOptionsThrowsException() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-s", "-f"});
        optionsHandler = new OptionsHandler(cmd);

        ParseException exception = assertThrows(ParseException.class, () -> {
            optionsHandler.processOptions();
        });
        assertTrue(exception.getMessage().contains("Ошибка: Опции статистики(-s и -f) не могут использоваться вместе."));
    }

    @Test
    void testProcessPositionalArgs_NoFilesThrowsException() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{});
        optionsHandler = new OptionsHandler(cmd);

        ParseException exception = assertThrows(ParseException.class, () -> {
            optionsHandler.processOptions();
        });
        assertTrue(exception.getMessage().contains("Ошибка: Не указаны входные файлы."));
    }

    @Test
    void testProcessPositionalArgs_InvalidFileFormatThrowsException() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"invalid.file"});
        optionsHandler = new OptionsHandler(cmd);

        ParseException exception = assertThrows(ParseException.class, () -> {
            optionsHandler.processOptions();
        });
        assertTrue(exception.getMessage().contains("Ошибка: Неправильный формат файла: invalid.file. Ожидаемый формат: .txt"));
    }

    @Test
    void testProcessPositionalArgs_ValidFiles() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"file1.txt", "file2.txt"});
        optionsHandler = new OptionsHandler(cmd);

        optionsHandler.processOptions();
    }

    @Test
    void testIsTxtFile_ValidTxtFile() {
        assertTrue(optionsHandler.isTxtFile("file.txt"));
    }

    @Test
    void testIsTxtFile_InvalidFile() {
        assertFalse(optionsHandler.isTxtFile("file.csv"));
    }

    @Test
    void testGetOutputPath() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-o", "/path/to/output"});
        optionsHandler = new OptionsHandler(cmd);

        assertEquals("/path/to/output", optionsHandler.getOutputPath());
    }

    @Test
    void testGetPrefix() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-p", "prefix123"});
        optionsHandler = new OptionsHandler(cmd);

        assertEquals("prefix123", optionsHandler.getPrefix());
    }

    @Test
    void testHasAppendMode() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-a"});
        optionsHandler = new OptionsHandler(cmd);

        assertTrue(optionsHandler.hasAppendMode());
    }

    @Test
    void testHasShortStatisticsMode() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-s"});
        optionsHandler = new OptionsHandler(cmd);

        assertTrue(optionsHandler.hasShortStatisticsMode());
    }

    @Test
    void testHasFullStatisticsMode() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"-f"});
        optionsHandler = new OptionsHandler(cmd);

        assertTrue(optionsHandler.hasFullStatisticsMode());
    }

    @Test
    void testGetPositionalArgs() throws ParseException {
        cmd = new DefaultParser().parse(createOptions(), new String[]{"file1.txt", "file2.txt"});
        optionsHandler = new OptionsHandler(cmd);

        List<String> args = optionsHandler.getPositionalArgs();

        assertEquals(Arrays.asList("file1.txt", "file2.txt"), args);
    }
}
