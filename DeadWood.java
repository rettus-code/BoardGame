import org.w3c.dom.Document;
import java.io.*;

import javax.xml.parsers.ParserConfigurationException;

public class DeadWood{
   public static void main(String[] args){
      Game todaysGame = new Game(6);
      
      // need to write the XML parser to handle all this nonsense
      XML xml = new XML();
      try {
         Document boardDoc = xml.getDocFromFile("board.xml");
         Room[] rooms = xml.readBoardData(boardDoc);
      } catch (ParserConfigurationException e) {         
         e.printStackTrace();
      }

      try {
         Document cardsDoc = xml.getDocFromFile("cards.xml");
         SceneCard[] sceneCards = xml.readSceneData(cardsDoc);
      } catch (ParserConfigurationException e) {         
         e.printStackTrace();
      }

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