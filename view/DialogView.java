package view;

import controller.*;
import javax.swing.*;

public class DialogView {
    // Eager Initialization
    private static DialogView instance = new DialogView();

    // Private Constructor (singleton pattern)
    private DialogView() {
    }

    public static DialogView getInstance() {
        return instance;
    }

    public static void displayStartDialog() {
        // show dialog with dropdown to choose only 2-8
        Integer[] options = { 2, 3, 4, 5, 6, 7, 8 };
        try {
            int numPlayers = (Integer) JOptionPane.showInputDialog(null, "How many players?",
                    "Number of Players", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            BoardLayersListener.getInstance().setNumPlayers(numPlayers);
        } catch (Exception e) {
        }
    }

    public static void displayPlayerNameDialog(int numPlayers) {
        String[] playerNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            String msg = "Enter Player " + (i + 1) + " Name";
            try {
                String playerName = JOptionPane.showInputDialog(null, msg,
                        "Player Name", JOptionPane.INFORMATION_MESSAGE);
                playerNames[i] = playerName;
            } catch (Exception e) {
            }
        }
        BoardLayersListener.getInstance().setPlayers(playerNames);
    }

    public static void displayPlayAgainDialog() {
        String[] options = { "Yes", "No" };
        try {
            String s = (String) JOptionPane.showInputDialog(null, "Play again?",
                    "Another round?", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            BoardLayersListener.getInstance().setQuit(s);
        } catch (Exception e) {
        }
    }

}
