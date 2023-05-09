package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Comparator;

public class WeeklyRecord implements Writable {
    private final ArrayList<DailyRecord> dailyRecordList;     //list of daily record
    private int totalDuration;                          //total duration of the week
    private ArrayList<String> initialPostList;          //list of string for each day record


    /*
    REQUIRES: totalDuration >= 0
    EFFECTS: construct a list of daily record.
     */
    public WeeklyRecord() {
        this.dailyRecordList = new ArrayList<>();       //list of DailyRecord
        this.totalDuration = 0;//Duration in integer
        this.initialPostList = new ArrayList<>();
    }

    /*
    EFFECTS: boolean to check if the entered date is already include in the list
     */
    public boolean hasDay(DailyRecord dr) {
        boolean ifHas = false;
        for (DailyRecord daily: dailyRecordList) {
            if (dr.getDayOfWeek().equals(daily.getDayOfWeek())) {
                ifHas = true;
                break;
            }
        }
        return ifHas;
    }


    /*
    MODIFIES: this
    EFFECT: add the day to the list of day record
     */
//DailyRecord
    public void addDailyRecordList(DailyRecord dr) {
        if (!hasDay(dr)) {
            dailyRecordList.add(dr);
            EventLog.getInstance().logEvent(new Event(dr.getDayOfWeek() + " "
                    + dr.getStart() + ":00-" + dr.getEnd() + ":00, " + dr.getDuration()
                    + "hr was added to the Weekly record"));
        }
    }

    //EFFECTS: return a daily record at a certain position in the list.
    public DailyRecord returnDr(int i) {
        return dailyRecordList.get(i);
    }


    /*
    MODIFIES: this
    EFFECT: remove the day from the list
     */
    public void removeDailyRecordList(DailyRecord dr) {
        for (int i = 0;i < dailyRecordList.size();i++) {
            if (dr.getDayOfWeek().equals(dailyRecordList.get(i).getDayOfWeek())) {
                dailyRecordList.remove(i);
                EventLog.getInstance().logEvent(new Event(dr.getDayOfWeek() + " "
                        + dr.getStart() + ":00-" + dr.getEnd() + ":00, " + dr.getDuration()
                        + "hr was removed from the Weekly record"));
            }
        }
    }


    /*
    REQUIRES: totalDuration >= 0
    MODIFIES: this
    EFFECT: Add the duration of each day in the list together
     */
    public int getAmountDuration() {
        totalDuration = 0;
        for (DailyRecord daily : dailyRecordList) {
            totalDuration += daily.getDuration();
        }
        EventLog.getInstance().logEvent(new Event("current duration is " + totalDuration
                + " hrs"));
        return totalDuration;
    }



    /*
    EFFECT: change each daily record in the list to a string
            include date, start/end time, duration for each day
     */
    public ArrayList<String> getDailyRecordList() {
        dailyRecordList.sort(Comparator.comparingInt(DailyRecord::getDateID));
        this.initialPostList = new ArrayList<>();
        for (DailyRecord dailyRecord : dailyRecordList) {
            //start time in string for single day
            String startS = String.valueOf(dailyRecord.getStart());
            //end time in string for single day
            String endS = String.valueOf(dailyRecord.getEnd());
            //duration in string for single day
            String durationT = String.valueOf(dailyRecord.getDuration());
            //date in string
            String dateT = dailyRecord.getDayOfWeek();
            //string that include all details for single day
            String postString = dateT + " " + startS + ":00-" + endS + ":00, " + durationT + "hr";
            initialPostList.add(postString);
        }
        return initialPostList;
    }

    public ArrayList<DailyRecord> getWeekList() {
        return dailyRecordList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("weeklyRecord", weeklyRecordToJson());
        return json;
    }

    // EFFECTS: returns things in this weeklyRecord as a JSON array
    private JSONArray weeklyRecordToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DailyRecord d : dailyRecordList) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}


