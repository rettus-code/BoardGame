package model;
import view.*;
import java.util.*;

public class Game {
   public static final int NUM_ROOMS = 12;
   public static final int NUM_SCENECARDS = 40;  

   public interface observer {
      void stateChanged(Game game);
   }
   private ArrayList<observer> observers;

   public void subscribe(observer o) {
      observers.add(o);
   }

   protected void stateChanged(Game game){
      for(observer o : observers) {
          o.stateChanged(this);
       }
  }
  
   public static Dice gameDice = new Dice(6);
   public Board gameBoard;
   public static Player[] playerArray = new Player[8];
   private int numPlayers;
   public static int activePlayer;
   private Day currentDay;
   private SceneCard[] deck = new SceneCard[40];
   private List<SceneCard> shuffledDeck = new ArrayList<>();
   private int winner;
   private int lastDay;
   private static BoardView board;
   private Game() {
   };

   public Game(int numPlayers, BoardView board) {
      this.observers = new ArrayList<observer>();
      this.numPlayers = numPlayers;
      this.activePlayer = determineStartingPlayer();
      this.lastDay = lastDay();
      this.board = board;
      this.gameBoard = new Board(board);
      newDay();
   }

   public int determineStartingPlayer() {
      return (int) (Math.random() * numPlayers);
   }

   public int getNumPlayers() {
      return this.numPlayers;
   }

   public Day getCurrentDay() {
      return this.currentDay;
   }

   public int getLastDay() {
      return this.lastDay;
   }

   public void sceneComplete() {
      int scenes = --Day.numScenes;
      if (scenes < 2) {
         endDay();
         if (this.currentDay.getDay() == lastDay()) {
            endOfGame();
         }
      }
      stateChanged(this);
   }

   public void checkScene() {
      for (int i = 0; i < this.numPlayers; i++) {
         if (playerArray[i].getCompletedScene()) {
            sceneComplete();
            playerArray[i].setCompletedScene(false);
         }
      }
   }

   public void updateActivePlayer() {
      checkScene();// check if player completed a scene
      if (this.activePlayer < this.numPlayers - 1) { // players array is zero indexed
         activePlayer++;
      } else {
         activePlayer = 0;
      }
      stateChanged(this);
   }

   public static int rollDice() {
      return gameDice.rollDice();
   }

   public void playerSetup(Player playa) {
      this.playerArray[playa.getID()] = playa;
   }

   public void newDay() {
      if (this.currentDay == null) {
         SceneCard[] deck = new SceneCard[40];
         Role[] roles = new Role[1];
         this.currentDay = new Day(0, deck);
      } else {
         this.currentDay = new Day(currentDay.getDay(), deck);
         dealSceneCards();
         board.resetShots();
      }
      stateChanged(this);
   }
   
   public static int getActivePlayer() {
      return activePlayer;
   }

   public void makeDeck(String[] cards) {
      for (int i = 0; i < cards.length; i++) {
         String[] temp = cards[i].split("@", 0);

         int budget = Integer.parseInt(temp[2]);
         int scene = Integer.parseInt(temp[3]);
         Role[] stars = new Role[Integer.parseInt(temp[5])];
         for (int j = 6; j < temp.length; j += 7) {
            int level = Integer.parseInt(temp[j + 1]);
            int w = Integer.parseInt(temp[j + 3]);
            int h = Integer.parseInt(temp[j + 4]);
            int x = Integer.parseInt(temp[j + 5]);
            int y = Integer.parseInt(temp[j + 6]);
            Role star = new Role(temp[j], level, temp[j + 2], w, h, x, y, true);
            stars[(j - 6) / 7] = star;
         }

         SceneCard card = new SceneCard(temp[0], temp[1], budget, scene, temp[4], stars);

         deck[i] = card;

      }
      shuffleDeck();
   }

   private void shuffleDeck() {
      for (int i = 0; i < deck.length; i++) {
         shuffledDeck.add(deck[i]);
      }
      Collections.shuffle(shuffledDeck);
   }

   public void dealSceneCards() {
      // get top 10 cards from the shuffled deck
      SceneCard[] temp = new SceneCard[10];
      for (int i = 9; i >= 0; i--) {
         temp[i] = shuffledDeck.remove(i);
      }
      // pass the 10 cards to the gameboard to add 1 to each Set
      this.gameBoard.dealSceneCards(temp);
   }

   public void makeBoard(String[] rooms) {
      int k = 0;
      int i = 0;
      int roomNum = 0;
      for (; i < 10; i++) {
         k = 0;
         String[] temp = rooms[i].split("@", 0);
         String roomName = temp[k++];
         int numNeighbors = Integer.parseInt(temp[k++]);
         String[] neighbors = new String[numNeighbors];
         for (int j = 0; j < numNeighbors; j++) {
            String neighbor = temp[k++];
            neighbors[j] = neighbor;
         }

         int roomWidth = Integer.parseInt(temp[k++]);
         int roomHeight = Integer.parseInt(temp[k++]);
         int roomX = Integer.parseInt(temp[k++]);
         int roomY = Integer.parseInt(temp[k++]);

         int numTakes = Integer.parseInt(temp[k++]);
         Take[] takes = new Take[numTakes];
         for (int j = 0; j < numTakes; j++) {
            int takeNumber = Integer.parseInt(temp[k++]);
            int w = Integer.parseInt(temp[k++]);
            int h = Integer.parseInt(temp[k++]);
            int x = Integer.parseInt(temp[k++]);
            int y = Integer.parseInt(temp[k++]);
            takes[j] = new Take(x, y, h, w, takeNumber);
         }

         int numRoles = Integer.parseInt(temp[k++]);
         Role[] roles = new Role[numRoles];
         for (int j = 0; j < numRoles; j++) {
            String partName = temp[k++];
            int level = Integer.parseInt(temp[k++]);
            String line = temp[k++];
            int w = Integer.parseInt(temp[k++]);
            int h = Integer.parseInt(temp[k++]);
            int x = Integer.parseInt(temp[k++]);
            int y = Integer.parseInt(temp[k++]);
            roles[j] = new Role(partName, level, line, w, h, x, y, false);
         }
         Room room = new Set(roomName, roomX, roomY, roomHeight, roomWidth, neighbors, roles, takes, roomNum++);
         this.gameBoard.setRoom(room, i);
      }

      k = 0;
      String[] temp = rooms[i++].split("@", 0);
      String roomName = temp[k++];
      int numNeighbors = Integer.parseInt(temp[k++]);
      String[] neighbors = new String[numNeighbors];
      for (int j = 0; j < numNeighbors; j++) {
         String neighbor = temp[k++];
         neighbors[j] = neighbor;
      }
      int trailerW = Integer.parseInt(temp[k++]);
      int trailerH = Integer.parseInt(temp[k++]);
      int trailerX = Integer.parseInt(temp[k++]);
      int trailerY = Integer.parseInt(temp[k++]);
      Room trailer = new Trailers(roomName, trailerX, trailerY, trailerH, trailerW, neighbors);
      this.gameBoard.setRoom(trailer, 10);

      k = 0;
      temp = rooms[i].split("@", 0);
      roomName = temp[k++];
      numNeighbors = Integer.parseInt(temp[k++]);
      neighbors = new String[numNeighbors];
      for (int j = 0; j < numNeighbors; j++) {
         String neighbor = temp[k++];
         neighbors[j] = neighbor;
      }
      int numUpgrades = Integer.parseInt(temp[k++]);
      Upgrade[] upgrades = new Upgrade[numUpgrades];
      for (int j = 0; j < numUpgrades; j++) {
         int level = Integer.parseInt(temp[k++]);
         int amount = Integer.parseInt(temp[k++]);
         String currency = temp[k++];
         int w = Integer.parseInt(temp[k++]);
         int h = Integer.parseInt(temp[k++]);
         int x = Integer.parseInt(temp[k++]);
         int y = Integer.parseInt(temp[k++]);
         upgrades[j] = new Upgrade(level, currency, amount, x, y, h, w);
      }
      int officeW = Integer.parseInt(temp[k++]);
      int officeH = Integer.parseInt(temp[k++]);
      int officeX = Integer.parseInt(temp[k++]);
      int officeY = Integer.parseInt(temp[k++]);
      Room office = new CastingOffice(roomName, officeX, officeY, officeH, officeW, neighbors, upgrades);
      this.gameBoard.setRoom(office, 11);
   }

   private int lastDay() {
      if (this.numPlayers < 4) {
         return 3;
      } else {
         return 4;
      }
   }

   public void endDay() {
      if(this.currentDay.getDay() < this.lastDay){
      for (int i = 0; i < this.numPlayers; i++) {
         playerArray[i].resetRole();
         playerArray[i].rehearseReset();
         playerArray[i].move(Board.getRoom("trailer"));
      }
      for (int i = 0; i < gameBoard.rooms.length; i++){
         int[] point = {-1,-2};
         if(gameBoard.rooms[i].hasSceneCard()){
            Set set = (Set)gameBoard.rooms[i];
            set.removeSceneCard();
         }
      }
      stateChanged(this);
      } else {
         endOfGame();
      }
   }

   public String endOfGame() {
      int topScore = 0;
      String winner = "";
      for (int i = 0; i < numPlayers; i++) {
         int score = playerArray[i].countScore();
         if (score > topScore) {
            topScore = score;
            winner = playerArray[i].getName();
            this.winner = i;
         }
      }
      return "The winner is " + winner + " with a score of " + topScore;
   }

}
