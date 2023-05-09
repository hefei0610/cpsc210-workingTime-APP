package persistence;

import model.DailyRecord;
import model.WeeklyRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDailyRecord(String date, int start, int end, DailyRecord dr) {
        assertEquals(date, dr.getDayOfWeek());
        assertEquals(start, dr.getStart());
        assertEquals(end, dr.getEnd());
    }
}
