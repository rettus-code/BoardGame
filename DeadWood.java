import org.w3c.dom.Document;

import controller.BoardLayersListener;
import model.Board;
import view.BoardView;
import view.DialogView;

import javax.xml.parsers.ParserConfigurationException;

public class DeadWood {
   private static final String BOARD_FILE = "board.xml";
   private static final String SCENE_FILE = "cards.xml";

   private static view.BoardView board;
   private static view.DialogView dialog;
   private static model.Game todaysGame;
   private static boolean quit = false;
   private static int numPlayers = 0;

   public static void main(String[] args) {
      board = BoardView.getInstance();
      board.setVisible(true);

      dialog = DialogView.getInstance();

      while (!quit) {
         playGame();
         quit = dialog.getPlayAgainFromUser(todaysGame.endOfGame());
      }

   }

   private static void playGame() {
      // Take input from the user about number of players
      while (numPlayers == 0) {
         numPlayers = dialog.getNumPlayersFromUser();
      }

      // Instantiate a new game
      todaysGame = new model.Game(numPlayers, board);
      BoardLayersListener.setGame(todaysGame);
      readDataFiles(todaysGame);
      todaysGame.dealSceneCards();

      // Get the player names
      String[] playerNames = dialog.getPlayerNamesFromUser(todaysGame.getNumPlayers());
      setPlayers(playerNames);
      board.initPlayerDice(todaysGame.playerArray);

      // register as an observer so it will update when state changes
      controller.BoardLayersListener.subscribe(todaysGame);
      board.initGameState();

      for (model.Player p : todaysGame.playerArray) {
         if (p != null) {
            controller.BoardLayersListener.subscribe(p);
         }
      }

      while (todaysGame.getCurrentDay().getDay() <= todaysGame.getLastDay()) {
         while (todaysGame.getCurrentDay().getNumScenes() > 1) {
            todaysGame.playerArray[model.Game.getActivePlayer()].takeTurn();
            todaysGame.updateActivePlayer();         
         }
         if(todaysGame.getCurrentDay().getDay() < todaysGame.getLastDay()){
            todaysGame.newDay();
         } else {
            todaysGame.getCurrentDay().setDay(todaysGame.getLastDay() + 1);
         }
      }
   }

   public static void setPlayers(String[] playerNames) {
      for (int i = 0; i < playerNames.length; i++) {
         model.Player p = new model.Player(playerNames[i], numPlayers, i, Board.getRoom("trailer"));
         todaysGame.playerSetup(p);
      }
   }

   public static void readDataFiles(model.Game todaysGame) {
      model.XML xml = new model.XML();
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