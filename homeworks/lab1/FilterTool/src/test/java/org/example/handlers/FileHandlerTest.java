package org.example.handlers;

import org.example.filter.DataFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private static final String TEST_DIRECTORY = "test_output";
    private static final String FILE_PREFIX = "test_";
    private static final String TEST_FILE_1 = "testfile1.txt";
    private static final String TEST_FILE_2 = "testfile2.txt";
    private static final String OUTPUT_FILE_1 = TEST_DIRECTORY + "/test_integers.txt";
    private static final String OUTPUT_FILE_2 = TEST_DIRECTORY + "/test_strings.txt";

    private FileHandler fileHandler;

    @BeforeEach
    void setUp() throws IOException {
        Path dirPath = Paths.get(TEST_DIRECTORY);
        if (Files.exists(dirPath)) {
            Files.walk(dirPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.createDirectory(dirPath);

        List<String> inputFiles = Arrays.asList(TEST_FILE_1, TEST_FILE_2);
        fileHandler = new FileHandler(TEST_DIRECTORY, FILE_PREFIX, true, true, false, inputFiles);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_1))) {
            writer.write("123");
            writer.newLine();
            writer.write("hello");
            writer.newLine();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_2))) {
            writer.write("456");
            writer.newLine();
            writer.write("world");
            writer.newLine();
        }
    }

    @Test
    void testReplaceTildeWithHome() {
        String path = "~/testdir";
        String expectedPath = System.getProperty("user.home") + "/testdir";

        String expandedPath = fileHandler.replaceTildeWithHome(path);

        assertEquals(expectedPath, expandedPath, "Tilde should be replaced with the home directory");
    }

    @Test
    void testWriteToFile() throws IOException {
        List<String> data = Arrays.asList("Line 1", "Line 2");
        String testFilePath = TEST_DIRECTORY + "/testfile.txt";

        fileHandler.writeToFile(TEST_DIRECTORY, "testfile.txt", data);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "File should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertEquals("Line 1", line1);
            assertEquals("Line 2", line2);
        }

        file.delete();
    }
}
