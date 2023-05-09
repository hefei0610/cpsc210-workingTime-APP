package model;

import org.json.JSONObject;
import persistence.Writable;

public class DailyRecord implements Writable {
    private String dayOfWeek;   //day of the week, Mon/Tue/Wed/Thur/Fri/Sat/Sun
    private int start;          //hour start the whole day work
    private int end;            //hour end the whole day work

    /*
    REQUIRES: dayOfWeek should be equal one of Mon/Tue/Wed/Thur/Fri/Sat/Sun
              start/end time in two digits(0-24), end time should not smaller
              than the start time
    EFFECTS: construct a daily record with Date, work startTime, work endTime
     */
    public DailyRecord(String whichDayOfWeek, int startTime, int endTime) {
        dayOfWeek = whichDayOfWeek;
        start = startTime;
        end = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    //EFFECTS: return the id of the date for the weekly record order.
    public int getDateID() {
        if (dayOfWeek.equals("Mon")) {
            return 1;
        } else if (dayOfWeek.equals("Tue")) {
            return 2;
        } else if (dayOfWeek.equals("Wed")) {
            return 3;
        } else if (dayOfWeek.equals("Thur")) {
            return 4;
        } else if (dayOfWeek.equals("Fri")) {
            return 5;
        } else if (dayOfWeek.equals("Sat")) {
            return 6;
        } else if (dayOfWeek.equals("Sun")) {
            return 0;
        } else {
            return -1;
        }

    }

    /*
    REQUIRES: end >= start
    EFFECTS: end time minus start time get the duration.
     */
    public int getDuration() {
        return end - start;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", dayOfWeek);
        json.put("start", start);
        json.put("end", end);
        return json;
    }



}



