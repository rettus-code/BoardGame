public class Game{
   public Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   private int numPlayers;
   private int activePlayer;
   private Day currentDay;
   private SceneCard[] deck = new SceneCard[40];
   private int winner;
   private int lastDay;
   private Player buildPlayer;
   private Player[] playerArray;
   
   private Game(){};
   private Game(int numPlayers){
      this.numPlayers = numPlayers;
      this.activePlayer = determineStartingPlayer();
      this.deck = shuffleDeck(deck);
      this.lastDay = lastDay();
      playerSetup();
      newDay();
   }
   public int determineStartingPlayer(){
      return (int)(Math.random() * numPlayers) + 1;
   }
   private int getActivePlayer(){
      return this.activePlayer;
   }
   private void updateActivePlayer(){
      if(activePlayer < numPlayers){
         activePlayer++;
      } else {
         activePlayer = 1;
      }
   }
   private SceneCard[] shuffleDeck(SceneCard[] deck){
      /*deck = need to populate array here then randomize it, probably need to make
               each card object as we populate it.*/           
      return deck;
   }
   public int rollDice(){
      return gameDice.rollDice();
   }
   private int lastDay(){
      if(this.numPlayers < 4){
         return 3;
      } else {
         return 4;
      }
   }private void playerSetup(){
      /*this will need to create each player, modify attributes based on
      player numbers and add each player to playerArray*/
   }
   private void newDay(){
      if(this.currentDay.getDay() == this.lastDay){
         System.out.println("end of game");
         endOfGame();
      }
      if(this.currentDay == null){
         this.currentDay = new Day(0, deck);
      } else {
         this.currentDay = new Day(currentDay.getDay(), deck);
      }
   }
   private void endOfGame(){
   
   }
}