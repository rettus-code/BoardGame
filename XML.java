public class XML {
   // Procedural Cohesion - open a connection to the XML file, create a DOM, parse the file, build the elements
   public XML(){
      
   }
   
   public Room[] readBoardFile(){
      // I counted 20 rooms in the board.xml file
      Room[] rooms = new Room[30];
      return rooms;
   }
   
   public SceneCard[] readSceneFile(){
      // The game has 40 cards
      SceneCard[] sceneCards = new SceneCard[40];
      return sceneCards;
   }
}
