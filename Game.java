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
      this.deck = shuffleDeck(deck);
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
         roles[0] = new Role("name", "line", 1, true);
         SceneCard newCard = new SceneCard("name", 1, 1, "description", roles);
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

   private SceneCard[] shuffleDeck(SceneCard[] deck) {
      /*
       * deck = need to populate array here then randomize it, probably need to make
       * each card object as we populate it.
       */
      return deck;
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