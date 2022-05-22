package controller;

import model.*;
import view.*;
import javax.swing.*;

public class BoardLayersListener extends JFrame {
   // Eager Initialization
   public static BoardLayersListener instance = new BoardLayersListener();

   private static BoardView board;
   private static DialogView dialog;
   private static Game todaysGame;
   private static int numPlayers;
   private static boolean quit = false;

   // Constructor (singleton)
   private BoardLayersListener() {
      board = BoardView.getInstance();
   }

   public static BoardLayersListener getInstance() {
      return instance;
   }

   public void setNumPlayers(int n) {
      numPlayers = n;
   }

   public void setPlayers(String[] playerNames) {
      for(int i = 0; i<playerNames.length; i++) {
         Player p = new Player(playerNames[i], numPlayers, i, Board.getRoom("trailer"));
         todaysGame.playerSetup(p);
      }
   }

   public void setQuit(String s) {
      quit = (s.equals("No"));
   }

   public static void main(String[] args) {
      board.setVisible(true);

      while (!quit) {
         playGame();
         dialog.displayPlayAgainDialog();
      }

   }

   private static void playGame() {
      // Take input from the user about number of players
      dialog.displayStartDialog();     

      // Instantiate a new game
      todaysGame = new Game(numPlayers);
      DeadWood.readDataFiles(todaysGame);
      todaysGame.dealSceneCards();

       // Get their names
       dialog.displayPlayerNameDialog(todaysGame.getNumPlayers());

       while (todaysGame.getCurrentDay().getDay() <= todaysGame.getLastDay()) {
         while (todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[Game.getActivePlayer()].takeTurn();
            todaysGame.updateActivePlayer();
         }
         todaysGame.newDay();
      }
   }
}
