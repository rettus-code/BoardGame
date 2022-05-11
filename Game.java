import java.util.*;

public class Game {
   public static Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   public Player[] playerArray = new Player[8];
   private int numPlayers;
   private int activePlayer;
   private Day currentDay;
   private SceneCard[] deck = new SceneCard[40];
   private List<SceneCard> shuffledDeck = new ArrayList<>();
   private int winner;
   private int lastDay;
   private Player buildPlayer;// = new Player("temp", 2, 2);

   private Game() {
   };

   public Game(int numPlayers) {
      this.numPlayers = numPlayers;
      this.activePlayer = determineStartingPlayer();
      this.lastDay = lastDay();
      // playerSetup();
      newDay();
   }

   public int determineStartingPlayer() {
      return (int) (Math.random() * numPlayers);
   }

   public Day getCurrentDay() {
      return this.currentDay;
   }

   public int getLastDay() {
      return this.lastDay;
   }

   public void updateActivePlayer() {
      if (this.activePlayer < this.numPlayers-1) { // players array is zero indexed
         activePlayer++;
      } else {
         activePlayer = 0;
      }
   }

   public static int rollDice() {
      return gameDice.rollDice();
   }

   public void playerSetup(Player playa) {
      /*
       * this will need to create each player, modify attributes based on
       * player numbers and add each player to playerArray
       */
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
      }

      if (this.currentDay.getDay() == this.lastDay) {
         System.out.println("end of game");
         endOfGame();
      }
   }

   public int getActivePlayer() {
      return this.activePlayer;
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
         //System.out.println(card.getName());
         //System.out.println(card.getSetting());
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
      SceneCard[] deck = new SceneCard[10];
      for(int i = 0; i < 10; i++) {
         deck[i] = shuffledDeck.remove(i);
      }
      // pass the 10 cards to the gameboard to add 1 to each Set
      this.gameBoard.dealSceneCards(deck);
   }

   public void makeBoard(String[] rooms) {
      int k = 0;
      int i = 0;
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
         Room room = new Set(roomName, roomX, roomY, roomHeight, roomWidth, neighbors, roles, takes);
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

   private void endOfGame() {

   }
}