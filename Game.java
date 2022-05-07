public class Game {
   public Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   public Player[] playerArray = new Player[8];
   private int numPlayers;
   private int activePlayer;
   private Day currentDay;
   private SceneCard[] deck = new SceneCard[40];
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
      return (int) (Math.random() * numPlayers) + 1;
   }

   public Day getCurrentDay() {
      return this.currentDay;
   }

   public int getLastDay() {
      return this.lastDay;
   }

   public void updateActivePlayer() {
      if (activePlayer < numPlayers) {
         activePlayer++;
      } else {
         activePlayer = 1;
      }
   }
   
   public int rollDice() {
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
      for(int i = 0; i < cards.length; i++){
         String[] temp = cards[i].split("@", 0);

          int budget = Integer.parseInt(temp[2]);
          int scene = Integer.parseInt(temp[3]);
          Role[] stars = new Role[Integer.parseInt(temp[5])];
          for(int j = 6; j < temp.length; j += 7){
             int level = Integer.parseInt(temp[j+1]);
             int w = Integer.parseInt(temp[j+3]);
             int h = Integer.parseInt(temp[j+4]);
             int x = Integer.parseInt(temp[j+5]);
             int y = Integer.parseInt(temp[j+6]);
             Role star = new Role(temp[j], level, temp[j+2], w, h, x, y, true);
             stars[(j-6)/7] = star;
          }
          
          SceneCard card = new SceneCard(temp[0], temp[1], budget, scene, temp[4], stars);
          System.out.println(card.getName());
          System.out.println(card.getSetting());
          deck[i] = card;
         
      }

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