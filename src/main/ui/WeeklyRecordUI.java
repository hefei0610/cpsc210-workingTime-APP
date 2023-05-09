package ui;

import model.DailyRecord;
import model.Event;
import model.EventLog;
import model.WeeklyRecord;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

public class WeeklyRecordUI extends JPanel implements ListSelectionListener {

    private JList<String> list;
    private DefaultListModel<String> listModel;

    private static final String JSON_STORE = "./data/weeklyRecord.json";

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String amountString = "Amount Duration";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String quitString = "Quit";


    private JButton removeButton;
    private JButton addButton;
    private JButton addAmount;
    private JButton save;
    private JButton load;
    private JButton quit;

    private AddListener addListener;
    private JPanel viewPane;    // add/remove/view panel
    private JPanel otherPane;   // addAmount/save/load panel

    private JTextField date;
    private JTextField start;
    private JTextField end;

    private WeeklyRecord weeklyRecord;



    private final PopUp popUp = new PopUp();

    //EFFECTS: construct a weekly record ui on a borderLayout
    public WeeklyRecordUI() {
        super(new BorderLayout());
        listModel();
        JScrollPane listScrollPane = new JScrollPane(list);
        button();
        button2();
        variables();

        viewPanel();
        otherPanel();
        add(listScrollPane, BorderLayout.CENTER);
        add(viewPane, BorderLayout.PAGE_START);
        add(otherPane, BorderLayout.PAGE_END);
    }

    //EFFECTS: Construct a listModel and a weekly record.
    public void listModel() {
        listModel = new DefaultListModel<>();
        weeklyRecord = new WeeklyRecord();

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
    }

    //MODIFIES: this
    //EFFECTS: Create buttons include add, remove, amount, save and load
    public void button() {
        addButton = new JButton(addString);
        addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        addButton.setToolTipText("Please enter the date: Mon/Tue/Wed/Thur/Fri/Sat/Sun; start/end: 0-24");

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        addAmount = new JButton(amountString);
        addAmount.setToolTipText("Click to get the amount work time during this week");
        addAmount.setActionCommand(amountString);
        addAmount.addActionListener(new AmountListener());

    }

    public void button2() {
        save = new JButton(saveString);
        save.setToolTipText("Click to save the current record");
        save.setActionCommand(saveString);
        save.addActionListener(new SaveListener());

        load = new JButton(loadString);
        load.setToolTipText("Click to load the previous saved record");
        load.setActionCommand(loadString);
        load.addActionListener(new LoadListener());

        quit = new JButton(quitString);
        quit.setToolTipText("Click to quit");
        quit.setActionCommand(quitString);
        quit.addActionListener(new QuitListener());
    }

    //MODIFIES:this
    //EFFECTS: get the value beside the add button blank that can be used to construct a daily record
    public void variables() {
        date = new JTextField(10);
        date.addActionListener(addListener);
        date.getDocument().addDocumentListener(addListener);

        start = new JTextField(10);
        start.addActionListener(addListener);
        start.getDocument().addDocumentListener(addListener);

        end = new JTextField(10);
        end.addActionListener(addListener);
        end.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES:this
    //EFFECT:Create a panel that uses BoxLayout include add and remove button.
    public void viewPanel() {
        viewPane = new JPanel();
        viewPane.setLayout(new BoxLayout(viewPane,
                BoxLayout.LINE_AXIS));

        viewPane.add(removeButton);
        viewPane.add(Box.createHorizontalStrut(5));
        viewPane.add(new JSeparator(SwingConstants.VERTICAL));
        viewPane.add(Box.createHorizontalStrut(5));

        viewPane.add(date);
        viewPane.add(start);
        viewPane.add(end);
        viewPane.add(addButton);
        viewPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }


    //MODIFIES:this
    //EFFECT:Create a panel that uses BoxLayout include amount, save and load button.
    public void otherPanel() {
        otherPane = new JPanel();
        otherPane.setLayout(new BoxLayout(otherPane,
                BoxLayout.LINE_AXIS));

        otherPane.add(addAmount);
        otherPane.add(Box.createHorizontalStrut(5));
        otherPane.add(new JSeparator(SwingConstants.VERTICAL));
        otherPane.add(Box.createHorizontalStrut(5));

        otherPane.add(save);

        otherPane.add(load);
        otherPane.add(Box.createHorizontalStrut(5));
        otherPane.add(new JSeparator(SwingConstants.VERTICAL));
        otherPane.add(Box.createHorizontalStrut(5));

        otherPane.add(quit);

        otherPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    //This listener includes remove button
    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove what ever's selected.
            int index = list.getSelectedIndex();
            weeklyRecord.removeDailyRecordList(weeklyRecord.returnDr(index));
            listModel.remove(index);


            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class AmountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String string = String.valueOf(weeklyRecord.getAmountDuration());
            popUp.gui(string);
        }

    }

    //This listener includes save button
    class SaveListener implements ActionListener {
        public boolean saveSuccess() {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(weeklyRecord);
                jsonWriter.close();
                return true;
            } catch (FileNotFoundException e) {
                return false;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (saveSuccess()) {
                popUp.gui("Saved this weekly record to " + JSON_STORE);
            } else {
                popUp.gui("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //This listener includes load button
    class LoadListener implements ActionListener {
        public boolean loadSuccess() {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            try {
                weeklyRecord = jsonReader.read();
                return true;
            } catch (IOException exc) {
                return false;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index1 = 0;
            if (loadSuccess()) {
                for (String s: weeklyRecord.getDailyRecordList()) {
                    listModel.insertElementAt(s, index1);
                    index1++;
                }
                popUp.gui("Loaded the record from " + JSON_STORE);
            } else {
                popUp.gui("Unable to read from file: " + JSON_STORE);
            }
        }
    }



    static class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            EventLog.getInstance().clear();
            System.exit(0);
        }
    }



    //This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private final JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //EFFECTS: Test if the entered value is fail
        public boolean notFail() {
            String dateText = date.getText();
            DailyRecord dr = new DailyRecord(date.getText(),
                    Integer.parseInt(start.getText()), Integer.parseInt(end.getText()));
            boolean b = false;

            //User enter a date that already in the record
            if (dateText.equals("") || weeklyRecord.hasDay(dr) || dr.getDateID() == -1) {
                Toolkit.getDefaultToolkit().beep();
                date.requestFocusInWindow();
                date.selectAll();
                popUp.gui("entered date not available, ...");
                //return;
            } else if (!(dr.getStart() >= 0 && dr.getStart() <= 24)) {
                Toolkit.getDefaultToolkit().beep();
                start.requestFocusInWindow();
                start.selectAll();
                popUp.gui("entered start not available, ...");
                //return;
            } else if (!(dr.getEnd() >= 0 && dr.getEnd() <= 24)) {
                Toolkit.getDefaultToolkit().beep();
                end.requestFocusInWindow();
                end.selectAll();
                popUp.gui("entered end not available, ...");
                //return;
            } else {
                b = true;
            }
            return b;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            int duration = Integer.parseInt(end.getText()) - Integer.parseInt(start.getText());

            DailyRecord dr = new DailyRecord(date.getText(),
                    Integer.parseInt(start.getText()), Integer.parseInt(end.getText()));
            if (notFail()) {
                listModel.insertElementAt(date.getText() + " " + start.getText() + ":00-"
                        + end.getText() + ":00, " + duration + "hr", index);
                weeklyRecord.addDailyRecordList(dr);
            }


            //Reset the text field.
            date.requestFocusInWindow();
            date.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }



        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            //No selection, disable fire button.
            //Selection, enable the fire button.
            removeButton.setEnabled(list.getSelectedIndex() != -1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws FileNotFoundException {
        JFrame f = new JFrame(); //creates jFrame f
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size
        f.setUndecorated(true); //removes the surrounding border
        ImageIcon image = new ImageIcon("image.jpg"); //imports the image
        JLabel lbl = new JLabel(image); //puts the image into a jLabel
        f.getContentPane().add(lbl); //puts label inside the jFrame
        f.setSize(image.getIconWidth(), image.getIconHeight()); //gets h and w of image and sets jFrame to the size

        int x = (screenSize.width - f.getSize().width) / 2; //These two lines are the dimensions
        int y = (screenSize.height - f.getSize().height) / 2;//of the center of the screen

        f.setLocation(x, y); //sets the location of the jFrame
        f.setVisible(true); //makes the jFrame visible

        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new WeeklyRecordUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
