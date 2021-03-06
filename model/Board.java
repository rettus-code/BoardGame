package model;
import view.*;

public class Board {
   public String[] boardSections = new String[] { "topRight", "bottomLeft", "bottomRight", "topLeft" };
   public static Room[] rooms = new Room[Game.NUM_ROOMS];
   public Room currentRoom;
   public BoardView board;
   public int shotCount = 0;
   public Board(BoardView board) {
      this.board = board;
   }

   public void dealSceneCards(SceneCard[] deck) {
      int k = 0;
      for (int i = 0; i < rooms.length; i++) {
         if (rooms[i].isSet()) {
            Set set = (Set) rooms[i];
            if (!set.hasSceneCard()) {
               set.addSceneCard(deck[k]);
               board.addCard(deck[k].getImage(), set.getCardPosition(), set.getRoomNum());
               k++;
            }
         }
      }
   }

   public void setRoom(Room room, int index) {
      /* use this to build all 12 rooms and push int rooms array */
      rooms[index] = room;
      placeShots(index);
   }
   public void placeShots(int i){
      if(rooms[i].isSet()){
         Set set = (Set) rooms[i];
         int[][] point = set.getTakes();
         for(int j = 0; j < point.length; j++){
            board.addShot(point[j][0], point[j][1], shotCount);
            set.getTake(j).setBoardNum(shotCount++);
         }
      }
   }
   public static Room getRoom(String roomName) {
      boolean found = false;
      int i = 0;
      Room room = rooms[i];

      while (found == false && i < rooms.length) {
         if (roomName.equals(rooms[i].getName())) {
            found = true;
            room = rooms[i];
         } else {
            i++;
         }
      }

      return room;
   }

   public Room getRoom() {
      return currentRoom;
   }
   public static Room getRoom(int i) {
      return rooms[i];
   }
}