package controller;

import model.*;
import view.*;
import view.BoardView;

import javax.swing.*;
import java.util.HashMap;
import java.util.*;

public class BoardLayersListener extends JFrame
      implements model.Game.observer, model.Player.observer {

         private static BoardView board = BoardView.getInstance();         
         private static model.Game todaysGame;
         private static int numPlayers = 0;         

   // Eager Initialization
   public static BoardLayersListener instance = new BoardLayersListener();

   // Constructor (singleton)
   private BoardLayersListener() {
   }

   public static BoardLayersListener getInstance() {
      return instance;
   }

   public int getNumPlayers(int n) {
      return numPlayers;
   }

   public void setNumPlayers(int n) {
      numPlayers = n;
   }

   public static void setGame(Game game) {
      todaysGame = game;
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
      board.updatePlayerDice(player.getRank(), player.getID());
      board.updateActivePlayerDice(player);
      board.updateActivePlayerRehearsalChips(player);
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
         for (int i = 0; i < roles.length; i++) {
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
         if (success) {
            board.movePlayerDie(activeP);
         } else {
            if (chosenRole.isTaken()) {
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

   public String act() {
      Player activeP = todaysGame.playerArray[Game.getActivePlayer()];
      return activeP.actGUI();
   }

   public String rehearse() {
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

   public String submitUpgrade(int n) {
      model.Player activeP = todaysGame.playerArray[todaysGame.activePlayer];
      String result = activeP.upgradeGUI(n);
      return result;
   }
}
