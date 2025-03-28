package org.example.filter;

import org.example.statistics.DataStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataFilterTest {

    private static final String TEST_DIRECTORY = "test_output";
    private static final String TEST_FILE_1 = "testfile1.txt";
    private DataFilter dataFilter;

    @BeforeEach
    void setUp() throws IOException {
        File dir = new File(TEST_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_1))) {
            writer.write("123 45.67 hello 89 world 3.14");
        }

        dataFilter = new DataFilter();
    }

    @Test
    void testProcessFile() {
        dataFilter.processFile(TEST_FILE_1);

        List<Integer> integers = dataFilter.getIntegers();
        List<Double> doubles = dataFilter.getDoubles();
        List<String> strings = dataFilter.getStrings();

        assertEquals(2, integers.size(), "There should be 2 integers");
        assertEquals(2, doubles.size(), "There should be 2 doubles");
        assertEquals(2, strings.size(), "There should be 2 strings");

        assertEquals(123, integers.get(0));
        assertEquals(89, integers.get(1));

        assertEquals(45.67, doubles.get(0));
        assertEquals(3.14, doubles.get(1));

        assertEquals("hello", strings.get(0));
        assertEquals("world", strings.get(1));
    }


    @Test
    void testGetIntegers() {
        dataFilter.processFile(TEST_FILE_1);
        List<Integer> integers = dataFilter.getIntegers();

        assertEquals(2, integers.size());
        assertTrue(integers.contains(123));
        assertTrue(integers.contains(89));
    }

    @Test
    void testGetDoubles() {
        dataFilter.processFile(TEST_FILE_1);
        List<Double> doubles = dataFilter.getDoubles();

        assertEquals(2, doubles.size());
        assertTrue(doubles.contains(45.67));
        assertTrue(doubles.contains(3.14));
    }

    @Test
    void testGetStrings() {
        dataFilter.processFile(TEST_FILE_1);
        List<String> strings = dataFilter.getStrings();

        assertEquals(2, strings.size());
        assertTrue(strings.contains("hello"));
        assertTrue(strings.contains("world"));
    }

    @Test
    void testProcessEmptyFile() throws IOException {
        String emptyFile = TEST_DIRECTORY + "/emptyfile.txt";
        new File(emptyFile).createNewFile();

        dataFilter.processFile(emptyFile);

        assertTrue(dataFilter.getIntegers().isEmpty(), "Integers list should be empty");
        assertTrue(dataFilter.getDoubles().isEmpty(), "Doubles list should be empty");
        assertTrue(dataFilter.getStrings().isEmpty(), "Strings list should be empty");
    }
}
