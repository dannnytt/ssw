package org.example.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataStatisticsTest {

    private DataStatistics dataStatistics;

    @BeforeEach
    void setUp() {
        dataStatistics = new DataStatistics();
    }

    @Test
    void testAddInteger() {
        dataStatistics.addInteger(5);
        dataStatistics.addInteger(10);
        dataStatistics.addInteger(20);

        assertEquals(3, dataStatistics.getIntegers().size());
        assertTrue(dataStatistics.getIntegers().contains(5));
        assertTrue(dataStatistics.getIntegers().contains(10));
        assertTrue(dataStatistics.getIntegers().contains(20));
    }

    @Test
    void testAddDouble() {
        dataStatistics.addDouble(5.5);
        dataStatistics.addDouble(10.1);
        dataStatistics.addDouble(20.25);

        assertEquals(3, dataStatistics.getDoubles().size());
        assertTrue(dataStatistics.getDoubles().contains(5.5));
        assertTrue(dataStatistics.getDoubles().contains(10.1));
        assertTrue(dataStatistics.getDoubles().contains(20.25));
    }

    @Test
    void testAddString() {
        dataStatistics.addString("Hello");
        dataStatistics.addString("World");

        assertEquals(2, dataStatistics.getStrings().size());
        assertTrue(dataStatistics.getStrings().contains("Hello"));
        assertTrue(dataStatistics.getStrings().contains("World"));
    }


    @Test
    void testGetIntegers() {
        dataStatistics.addInteger(5);
        dataStatistics.addInteger(10);
        dataStatistics.addInteger(20);

        assertEquals(3, dataStatistics.getIntegers().size());
        assertTrue(dataStatistics.getIntegers().contains(5));
        assertTrue(dataStatistics.getIntegers().contains(10));
        assertTrue(dataStatistics.getIntegers().contains(20));
    }

    @Test
    void testGetDoubles() {
        dataStatistics.addDouble(5.5);
        dataStatistics.addDouble(10.1);
        dataStatistics.addDouble(20.25);

        assertEquals(3, dataStatistics.getDoubles().size());
        assertTrue(dataStatistics.getDoubles().contains(5.5));
        assertTrue(dataStatistics.getDoubles().contains(10.1));
        assertTrue(dataStatistics.getDoubles().contains(20.25));
    }

    @Test
    void testGetStrings() {
        dataStatistics.addString("Hello");
        dataStatistics.addString("World");

        assertEquals(2, dataStatistics.getStrings().size());
        assertTrue(dataStatistics.getStrings().contains("Hello"));
        assertTrue(dataStatistics.getStrings().contains("World"));
    }

}
