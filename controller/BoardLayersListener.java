package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.*;  

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

      // Get the player names
      dialog.displayPlayerNameDialog(todaysGame.getNumPlayers());
      board.initPlayerDice(todaysGame.playerArray);

      // register as an observer so it will update when state changes
      subscribe(todaysGame);
      board.initGameState();

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

   // call all the methods to update the view when the game model changes
   public void stateChanged() {
      stateChanged(todaysGame);
   }

   public void stateChanged(Game game) {
      Player activePlayer = game.playerArray[Game.getActivePlayer()];
      board.updateActivePlayerLabel(activePlayer);
   }

   // call all the methods to update the view when the player model changes
   public void stateChanged(Player player) {
      board.movePlayerDie(player);
      getPossibleActionsMenu();
   }

   // return the list of neighboring rooms to the view
   public void getPossibleActionsMenu() {
      Player active = todaysGame.playerArray[todaysGame.activePlayer];
      HashMap<String, Boolean> possibleActions = active.getPossibleActions();
      ArrayList<String> actions = new ArrayList<>();
      for(Map.Entry m : possibleActions.entrySet()) {
         if(m.getValue() == Boolean.TRUE) {
            actions.add(m.getKey().toString());
         }
      }
      board.showActionMenu(actions);
   }

   // return the list of neighboring rooms to the view
   public void getMoveMenu() {
      Player active = todaysGame.playerArray[todaysGame.activePlayer];
      String[] neighbors = active.getLocation().getNeighbors();
      board.showMoveMenu(neighbors);
   }

   // the player chose the room to move to so update the data in the model
   public void submitPlayerMove(String roomStr) {
      model.Room room = todaysGame.gameBoard.getRoom(roomStr);
      todaysGame.playerArray[todaysGame.activePlayer].setRoom(room);
   }

   // end turn so next player can go
   public void endTurn() {
      Player active = todaysGame.playerArray[Game.getActivePlayer()];
      active.endTurn();
   }
}
