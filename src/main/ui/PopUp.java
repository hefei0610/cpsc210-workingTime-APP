package ui;

import javax.swing.*;

public class PopUp {
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        JOptionPane.showMessageDialog(jframe, "Hello there! How are you today?");
    }

    public void gui(String message) {
        JFrame jframe = new JFrame();
        JOptionPane.showMessageDialog(jframe, message);
    }
}
