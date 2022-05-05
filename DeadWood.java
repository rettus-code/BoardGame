import org.w3c.dom.Document;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

public class DeadWood {
   private static Scanner scanner = new Scanner(System.in);   
   public static void main(String[] args) {
      System.out.println("Welcome to DeadWood, how many players?:");
      int numPlayers = 0;
      while (numPlayers < 1 || numPlayers > 8) {
         numPlayers = scanner.nextInt();
      }
      System.out.printf("Creating a new game with %d players\n", numPlayers);

      Game todaysGame = new Game(numPlayers);

      readDataFiles();
      
      // temp data until XML is working
      Room Trailers = new Trailers();
      Room MainStreet = new Set("Main Street");
      Room Saloon = new Set("Saloon");
      Room[] neighs = new Room[2];
      neighs[0] = MainStreet;
      neighs[1] = Saloon;
      // Trailers.setNeighbors(neighs);

      Player player1 = new Player("Nick", 1, 1, Trailers);
      todaysGame.playerSetup(player1);
      
      while(todaysGame.getCurrentDay().getDay() <=  todaysGame.getLastDay()) {
         while(todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[todaysGame.getActivePlayer()].takeTurn(); 
            todaysGame.updateActivePlayer();
          }
          todaysGame.newDay();
     }
     
   }

   private static void readDataFiles(){
      // need to write the XML parser to handle all this nonsense
      XML xml = new XML();
      try {
         Document boardDoc = xml.getDocFromFile("board.xml");
         String[] rooms = xml.readBoardData(boardDoc);
      } catch (ParserConfigurationException e) {         
         e.printStackTrace();
      }

       try {
          Document cardsDoc = xml.getDocFromFile("cards.xml");
          String[] sceneCards = xml.readSceneData(cardsDoc);
       } catch (ParserConfigurationException e) {         
          e.printStackTrace();
       }

   }
}