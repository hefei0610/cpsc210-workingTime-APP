package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class WeeklyRecordTest {
    private WeeklyRecord WeeklyRecordTest;

    private DailyRecord DailyRecordTest1;
    private DailyRecord DailyRecordTest2;

    @BeforeEach
    void runBefore() {
        DailyRecordTest1 = new DailyRecord("Fri", 17, 19);
        DailyRecordTest2 = new DailyRecord("Mon", 17, 19);
        WeeklyRecordTest = new WeeklyRecord();
    }

    @Test
    void testConstructor() {
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest1);
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        assertEquals(WeeklyRecordTest.returnDr(0), DailyRecordTest1);
        assertEquals(WeeklyRecordTest.returnDr(1), DailyRecordTest2);
    }

    @Test
    void testAddAmountDuration() {
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest1);
        assertEquals(2, WeeklyRecordTest.getAmountDuration());
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        assertEquals(4, WeeklyRecordTest.getAmountDuration());

    }

    @Test
    void testAdd() {
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest1);
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        ArrayList<String> list1 = new ArrayList<>();
        String s1 = "Mon 17:00-19:00, 2hr";
        String s2 = "Fri 17:00-19:00, 2hr";
        list1.add(s1);
        list1.add(s2);
        assertEquals(list1, WeeklyRecordTest.getDailyRecordList());
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest1);
        assertEquals(list1, WeeklyRecordTest.getDailyRecordList());
    }

    @Test
    void testRemove() {
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest1);
        WeeklyRecordTest.addDailyRecordList(DailyRecordTest2);
        WeeklyRecordTest.removeDailyRecordList(DailyRecordTest2);
        ArrayList<String> list1 = new ArrayList<>();
        String s1 = "Fri 17:00-19:00, 2hr";
        list1.add(s1);
        assertEquals(list1, WeeklyRecordTest.getDailyRecordList());
        assertEquals(2, WeeklyRecordTest.getAmountDuration());
        WeeklyRecordTest.removeDailyRecordList(DailyRecordTest1);
        assertEquals(0, WeeklyRecordTest.getAmountDuration());
    }
}
