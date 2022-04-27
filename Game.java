public class Game{
   public Dice gameDice = new Dice();
   public Board gameBoard = new Board();
   private int numPlayers;
   private int startingPlayer;
   private int activePlayer;
   private Day currentDay = new Day();
   private SceneCard[] deck = new SceneCard[40];
   
   private Game(){};
   private Game(int numPlayers){
      this.numPlayers = numPlayers;
      this.startingPlayer = determineStartingPlayer(numPlayers);
      this.activePlayer = startingplayer;
      this.deck = shuffleDeck(deck);
   };
   private int getActivePlayer(){
      return activePlayer();
   }
   private void setActivePlayer(){
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
}