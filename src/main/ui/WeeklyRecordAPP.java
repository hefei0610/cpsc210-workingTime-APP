package ui;

import model.DailyRecord;
import model.WeeklyRecord;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class WeeklyRecordAPP {
    private static final String JSON_STORE = "./data/weeklyRecord.json";
    private WeeklyRecord weeklyRecord;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this
    //EFFECTS: run weekly record
    public WeeklyRecordAPP() throws FileNotFoundException {
        weeklyRecord = new WeeklyRecord();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRecord();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRecord() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("g")) {
            doAmount();
        } else if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("s")) {
            saveWeeklyRecord();
        } else if (command.equals("l")) {
            loadWeeklyRecord();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes records
    private void init() {
//        DailyRecord d1 = new DailyRecord("Fri", 17, 19);
//        DailyRecord d2 = new DailyRecord("Mon", 17, 19);
//        weeklyRecord.addDailyRecordList(d1);
//        weeklyRecord.addDailyRecordList(d2);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view");
        System.out.println("\tg -> get amount");
        System.out.println("\ta -> add");
        System.out.println("\tr -> remove");
        System.out.println("\ts -> save this weekly record");
        System.out.println("\tl -> load the weekly record");
        System.out.println("\tq -> quit");
    }


    // EFFECTS: display the whole list of Daily record as list of String
    private void doView() {
        System.out.println(weeklyRecord.getDailyRecordList());
    }


    //EFFECTS: display the total Amount hours work during this week
    private void doAmount() {
        System.out.print(weeklyRecord.getAmountDuration());
    }


    //EFFECTS: do the add, and if there is already a same date in the list, then do not add
    private void doAdd() {
        System.out.print("Enter the date (Mon/Tue/Wed/Thur/Fri/Sat/Sun) of your new record:");
        String date = input.next();
        System.out.print("Enter the start time (0-24) of your work for the new record:");
        int start = input.nextInt();
        System.out.print("Enter the start time (0-24) of your work for the new record:");
        int end = input.nextInt();

        DailyRecord dr = new DailyRecord(date, start, end);
        if (dr.getDateID() == -1) {
            System.out.println("Entered date is not Available, please add again");
            doAdd();
        } else {
            if (!(weeklyRecord.hasDay(dr))) {
                weeklyRecord.addDailyRecordList(dr);
            } else {
                System.out.print("Entered record is already there, please view then remove the old first");
                doView();
            }
        }
    }

    //do remove, and if do not have that same date in the list, then do not remove
    private void doRemove() {
        System.out.print("Enter the date (Mon/Tue/Wed/Thur/Fri/Sat/Sun) of your removed record:");
        String date = input.next();
        int start = 0;
        int end = 1;
        DailyRecord dr = new DailyRecord(date, start, end);
        if (weeklyRecord.hasDay(dr)) {
            weeklyRecord.removeDailyRecordList(dr);
            System.out.println("Removed Success!");
        } else {
            System.out.print("Entered record date is not there, please check the list to confirm");
            doView();
        }
    }

    // EFFECTS: saves the weeklyRecord to file
    private void saveWeeklyRecord() {
        try {
            jsonWriter.open();
            jsonWriter.write(weeklyRecord);
            jsonWriter.close();
            System.out.println("Saved this weekly record to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads weeklyRecord from file
    private void loadWeeklyRecord() {
        try {
            weeklyRecord = jsonReader.read();
            System.out.println("Loaded the record from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}
