package model;
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
      try{
         boolean quit = false;
         int i = 0;
         while (!quit) {
            playGame();
            System.out.print("That was fun! Play again?\n1. Yes\n2. No\n");
            while (i != 1 && i != 2) {
               try {
                  i = scanner.nextInt();
               } catch (Exception e) {
                  scanner.next();
                  System.out.println("Please enter a number");
               }
            }
            if (i == 2) {
               quit = true; // quit
            } else {
               i = 0; // restart the menu
            }
         }
      } catch (Exception e) {
            System.out.println("Something went wrong:");
            e.printStackTrace();
      }
   }

   private static void playGame() {
      System.out.println("Welcome to DeadWood, how many players?:");
      int numPlayers = 0;
      while (numPlayers < 2 || numPlayers > 8) {
         try {
            numPlayers = scanner.nextInt();
            scanner.nextLine();
         } catch (Exception e) {
            scanner.next();
            System.out.println("Please enter Numbers");
         }
      }
      System.out.printf("Creating a new game with %d players\n", numPlayers);

      Game todaysGame = new Game(numPlayers);
      readDataFiles(todaysGame);
      todaysGame.dealSceneCards();

      for (int i = 0; i < numPlayers; i++) {
         System.out.printf("Player %d enter name:\n", i + 1);
         String name = scanner.nextLine();
         Player player = new Player(name, numPlayers, i, Board.getRoom("trailer"));
         todaysGame.playerSetup(player);
      }

      while (todaysGame.getCurrentDay().getDay() <= todaysGame.getLastDay()) {
         while (todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[Game.getActivePlayer()].takeTurn();
            todaysGame.updateActivePlayer();
         }
         todaysGame.newDay();
      }

   }

   public static void readDataFiles(Game todaysGame) {
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