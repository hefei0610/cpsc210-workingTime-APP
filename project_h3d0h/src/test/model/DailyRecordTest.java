package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordTest {
    private DailyRecord DailyRecordTest;
    private DailyRecord DailyRecordTest2;
    private DailyRecord DailyRecordTest3;
    private DailyRecord DailyRecordTest4;
    private DailyRecord DailyRecordTest5;
    private DailyRecord DailyRecordTest6;
    private DailyRecord DailyRecordTest7;
    private DailyRecord DailyRecordTest8;

    @BeforeEach
    void runBefore() {
        DailyRecordTest = new DailyRecord("Mon", 8, 16);
        DailyRecordTest2 = new DailyRecord("Tue", 8, 16);
        DailyRecordTest3 = new DailyRecord("Wed", 8, 16);
        DailyRecordTest4 = new DailyRecord("Thur", 8, 16);
        DailyRecordTest5 = new DailyRecord("Fri", 8, 16);
        DailyRecordTest6 = new DailyRecord("Sat", 8, 16);
        DailyRecordTest7 = new DailyRecord("Sun", 8, 16);
        DailyRecordTest8 = new DailyRecord("A", 8, 16);
    }

    @Test
    void testConstructor() {
        assertEquals("Mon", DailyRecordTest.getDayOfWeek());
        assertEquals(8, DailyRecordTest.getStart());
        assertEquals(16, DailyRecordTest.getEnd());

    }

    @Test
    void testGetDuration() {
        assertEquals(8, DailyRecordTest.getDuration());
    }

    @Test
    void testGetDateID() {
        assertEquals(1, DailyRecordTest.getDateID());
        assertEquals(2, DailyRecordTest2.getDateID());
        assertEquals(3, DailyRecordTest3.getDateID());
        assertEquals(4, DailyRecordTest4.getDateID());
        assertEquals(5, DailyRecordTest5.getDateID());
        assertEquals(6, DailyRecordTest6.getDateID());
        assertEquals(0, DailyRecordTest7.getDateID());
        assertEquals(-1, DailyRecordTest8.getDateID());
    }


}