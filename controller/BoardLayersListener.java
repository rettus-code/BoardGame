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
      Player activeP = game.playerArray[Game.getActivePlayer()];
      board.updateActivePlayerLabel(activeP);
   }

   // call all the methods to update the view when the player model changes
   public void stateChanged(Player player) {
      board.movePlayerDie(player);
      getPossibleActionsMenu();
   }

   // return the list of neighboring rooms to the view
   public void getPossibleActionsMenu() {
      Player activeP = todaysGame.playerArray[todaysGame.activePlayer];
      HashMap<String, Boolean> possibleActions = activeP.getPossibleActions();
      ArrayList<String> actions = new ArrayList<>();
      for (Map.Entry m : possibleActions.entrySet()) {
         if (m.getValue() == Boolean.TRUE) {
            actions.add(m.getKey().toString());
         }
      }
      board.showActionMenu(actions);
   }

   // return the list of roles to the view
   public void getPossibleRolesMenu() {      
      model.Player activeP = todaysGame.playerArray[todaysGame.activePlayer];
      model.Room room = activeP.getLocation();
      // get roles
      if (room.isSet()) {
         model.Set set = (model.Set) room;
         model.Role[] roles = set.getAllRoles(); // oncard and extra roles
         String[] rolesArray = new String[roles.length];
         for(int i = 0; i<roles.length; i++) {
            rolesArray[i] = roles[i].toString();
         }
         board.showTakeRoleMenu(rolesArray);       
      } else {
         // do nothing
      }      
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
      todaysGame.playerArray[todaysGame.activePlayer].move(room);
   }

   // validate, take role if valid, show message
   public String submitPlayerTakeRole(int n) {
      String resultMsg = "Success!";
      Player activeP = todaysGame.playerArray[todaysGame.activePlayer];
      // get the role from the role String
      model.Room room = activeP.getLocation();
      // get roles
      if (room.isSet()) {
         model.Set set = (model.Set) room;
         model.Role[] roles = set.getAllRoles(); // oncard and extra roles        
         model.Role chosenRole = roles[n];      
         boolean success = activeP.takeRole(chosenRole);
         if(success) {
            board.movePlayerDie(activeP);
         } else {
            if(chosenRole.isTaken()) {
               resultMsg = "Role already taken";
            } else if (chosenRole.getLevel() > activeP.getRank()) {
               resultMsg = "Level too high";
            }
         }
      }
      return resultMsg;
   }

   // end turn so next player can go
   public void endTurn() {
      Player activeP = todaysGame.playerArray[Game.getActivePlayer()];
      activeP.endTurn();
   }

   public void flipCard() {
      Player activeP = todaysGame.playerArray[Game.getActivePlayer()];
      model.Room room = activeP.getLocation();
      // get roles
      if (room.isSet()) {
         model.Set set = (model.Set) room;
         board.flipCard(set.getSceneCard().getImage(), set.getCardPosition(), set.getRoomNum());
      }
   }

   public void removeCard(String image, int[] point, int roomNum) {      
      board.removeCard(image, point, roomNum);      
   }

   public String act(){
      Player activeP = todaysGame.playerArray[Game.getActivePlayer()];
      return activeP.actGUI();         
   }

   public String rehearse(){
      Player activeP = todaysGame.playerArray[Game.getActivePlayer()];
      return activeP.rehearseGUI();         
   }

    // return the list of upgrades to the view
   public void getUpgradesMenu() {      
      model.Player activeP = todaysGame.playerArray[todaysGame.activePlayer];
      model.Room room = activeP.getLocation();
      // get roles
      if (room.getName().equals("office")) {
         model.CastingOffice office = (model.CastingOffice) room;         
         String[] upgradesArray = office.getUpgradesGUI();         
         board.showUpgradeMenu(upgradesArray);       
      } else {
         // do nothing
      }      
   }

   public String submitUpgrade(int n){  
      model.Player activeP = todaysGame.playerArray[todaysGame.activePlayer];    
      String result = activeP.upgradeGUI(n);
      return result;
   }
}
