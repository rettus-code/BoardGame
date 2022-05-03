import java.io.*;

public class DeadWood{
   public static void main(String[] args){
      Game todaysGame = new Game(6);
      
      // need to write the XML parser to handle all this nonsense
      Room Trailers = new Trailers();
      Room MainStreet = new Set("Main Street");
      Room Saloon = new Set("Saloon");
      Room[] neighs = new Room[2];
      neighs[0]=MainStreet;
      neighs[1]=Saloon;
      Trailers.setNeighbors(neighs);
      
      Player player1 = new Player("Nick",	1, 1, Trailers);
      todaysGame.playerSetup(player1);
      player1.takeTurn();
   }
}