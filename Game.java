public class Game{
   public Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   private int numPlayers;
   private int activePlayer;
   private Day currentDay;
   private SceneCard[] deck = new SceneCard[40];
   private int winner;
   
   private Game(){};
   private Game(int numPlayers){
      this.numPlayers = numPlayers;
      this.activePlayer = determineStartingPlayer();
      this.deck = shuffleDeck(deck);
      dayMaker();
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
   private void dayMaker(){
      if(this.currentDay == null){
         this.currentDay = new Day(0, 40);
      } else {
         this.currentDay = new Day(currentDay.getDay(), deck.length);
      }
   }
}