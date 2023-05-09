package persistence;


import model.DailyRecord;
import model.WeeklyRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads weeklyRecord from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WeeklyRecord read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklyRecord(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WeeklyRecord parseWeeklyRecord(JSONObject jsonObject) {
     //   String name = jsonObject.getString("name");
        WeeklyRecord wr = new WeeklyRecord();
        addWeeklyRecord(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses dailyRecords from JSON object and adds them to weeklyRecord
    private void addWeeklyRecord(WeeklyRecord wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("weeklyRecord");
        for (Object json : jsonArray) {
            JSONObject nextDailyRecord = (JSONObject) json;
            addDailyRecord(wr, nextDailyRecord);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to weeklyRecord
    private void addDailyRecord(WeeklyRecord wr, JSONObject jsonObject) {
        String dayOfWeek = jsonObject.getString("date");
        int start = jsonObject.getInt("start");
        int end = jsonObject.getInt("end");
        DailyRecord daily = new DailyRecord(dayOfWeek, start, end);
        wr.addDailyRecordList(daily);
    }
}
