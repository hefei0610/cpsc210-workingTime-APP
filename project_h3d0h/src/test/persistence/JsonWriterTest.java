package persistence;

import model.DailyRecord;
import model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            //WeeklyRecord wr = new WeeklyRecord();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WeeklyRecord wr = new WeeklyRecord();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWeeklyRecord.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWeeklyRecord.json");
            wr = reader.read();
            assertEquals(0, wr.getDailyRecordList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WeeklyRecord wr = new WeeklyRecord();
            wr.addDailyRecordList(new DailyRecord("Tue", 8, 17));
            wr.addDailyRecordList(new DailyRecord("Wed", 8, 17));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWeeklyRecord.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWeeklyRecord.json");
            wr = reader.read();
            assertEquals(2, wr.getDailyRecordList().size());
            List<DailyRecord> list = wr.getWeekList();
            checkDailyRecord("Tue", 8, 17, list.get(0));
            checkDailyRecord("Wed", 8, 17, list.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
