import org.w3c.dom.Document;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

public class DeadWood {
   private static final String BOARD_FILE = "board.xml";
   private static final String SCENE_FILE = "cards.xml";
   public static final int NUM_ROOMS = 12;
   public static final int NUM_SCENECARDS = 40;

   private static Scanner scanner = new Scanner(System.in);   
   public static void main(String[] args) {
      System.out.println("Welcome to DeadWood, how many players?:");
      int numPlayers = 0;
      while (numPlayers < 2 || numPlayers > 8) {
         numPlayers = scanner.nextInt();
      }
      System.out.printf("Creating a new game with %d players\n", numPlayers);  

      Game todaysGame = new Game(numPlayers);
      readDataFiles(todaysGame);
      todaysGame.dealSceneCards();

      for(int i = 0; i < numPlayers; i++) {
         System.out.printf("Player %d enter name:\n", i+1);
         String name = scanner.next(); 
         Player player = new Player(name, numPlayers, i, Board.getRoom("trailer"));         
         todaysGame.playerSetup(player);
      }      
      
      while(todaysGame.getCurrentDay().getDay() <=  todaysGame.getLastDay()) {
         while(todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[todaysGame.getActivePlayer()].takeTurn(); 
            todaysGame.updateActivePlayer();
          }
          todaysGame.newDay();
     }
     
   }

   private static void readDataFiles(Game todaysGame){            
      XML xml = new XML();
      try {
          Document boardDoc = xml.getDocFromFile(BOARD_FILE);
          String[] rooms = xml.readBoardData(boardDoc);
          todaysGame.makeBoard(rooms);
       } catch (ParserConfigurationException e) {         
          e.printStackTrace();
       }

       try {
          Document cardsDoc = xml.getDocFromFile(SCENE_FILE);
          String[] sceneCards = xml.readSceneData(cardsDoc);
          todaysGame.makeDeck(sceneCards);
       } catch (ParserConfigurationException e) {         
          e.printStackTrace();
       }

   }
}