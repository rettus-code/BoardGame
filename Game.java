public class Game{
   public Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   private int numPlayers;
   private int activePlayer;
   private Day currentDay = new Day();
   private SceneCard[] deck = new SceneCard[40];
   private int winner;
   
   private Game(){};
   private Game(int numPlayers){
      this.numPlayers = numPlayers;
      this.activePlayer = determineStartingPlayer();
      this.deck = shuffleDeck(deck);
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
      return (int)(Math.random() * 6) + 1;
   }
}