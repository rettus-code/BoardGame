package controller;

import model.*;
import view.*;
import javax.swing.*;

public class BoardLayersListener extends JFrame
      implements model.Game.observer, model.Player.observer {

   // Eager Initialization
   public static BoardLayersListener instance = new BoardLayersListener();

   private static BoardView board;
   private static DialogView dialog;
   private static Game todaysGame;
   private static int numPlayers = 0;
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
      for (int i = 0; i < playerNames.length; i++) {
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
      while (numPlayers == 0) {
         dialog.displayStartDialog();
      }

      // Instantiate a new game
      todaysGame = new Game(numPlayers, board);
      DeadWood.readDataFiles(todaysGame);
      todaysGame.dealSceneCards();

      // Get their names
      dialog.displayPlayerNameDialog(todaysGame.getNumPlayers());
      board.initPlayerDice(todaysGame.playerArray);

      // register as an observer so it will update when state changes
      subscribe(todaysGame);
      for (Player p : todaysGame.playerArray) {
         if (p != null) {
            subscribe(p);
         }
      }

      while (todaysGame.getCurrentDay().getDay() <= todaysGame.getLastDay()) {
         while (todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[Game.getActivePlayer()].takeTurn();
            todaysGame.updateActivePlayer();
         }
         todaysGame.newDay();
      }
   }

   // subscribe to the game model so it will update when the model state changes
   public static void subscribe(Game game) {
      game.subscribe(getInstance());      
   }

   // subscribe this view to the player model so it will update when the model
   // state changes
   public static void subscribe(Player player) {
      player.subscribe(getInstance());      
   }

   // call all the methods to update the view when the model changes
   public void stateChanged(Game game) {
      Player activePlayer = game.playerArray[game.activePlayer];
      board.updateActivePlayerLabel(activePlayer);
   }

   public void stateChanged(Player player) {
      board.movePlayerDie(player);
   }
   
}
