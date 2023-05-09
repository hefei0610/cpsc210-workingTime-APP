package persistence;

import model.DailyRecord;
import model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WeeklyRecord wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWeeklyRecord() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyWeeklyRecord.json");
        try {
            WeeklyRecord wr = reader.read();
            assertEquals(0, wr.getDailyRecordList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWeeklyRecord() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralWeeklyRecord.json");
        try {
            WeeklyRecord wr = reader.read();
            assertEquals(2, wr.getDailyRecordList().size());
            List<DailyRecord> list = wr.getWeekList();
            checkDailyRecord("Tue", 8, 17, list.get(0));
            checkDailyRecord("Wed", 8, 17, list.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
