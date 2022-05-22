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
        int numPlayers = (Integer) JOptionPane.showInputDialog(null, "How many players?",
                "Number of Players", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        BoardLayersListener.getInstance().setNumPlayers(numPlayers);
    }

    public static void displayPlayerNameDialog(int numPlayers) {
        String[] playerNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            String msg = "Enter Player " + i + 1 + " Name";
            String playerName = JOptionPane.showInputDialog(null, msg,
                    "Player Name", JOptionPane.INFORMATION_MESSAGE);
            playerNames[i] = playerName;
        }
        BoardLayersListener.getInstance().setPlayers(playerNames);
    }

    public static void displayPlayAgainDialog() {
        String[] options = { "Yes", "No" };
        String s = (String) JOptionPane.showInputDialog(null, "Play again?",
                "Another round?", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        BoardLayersListener.getInstance().setQuit(s);
    }

}
