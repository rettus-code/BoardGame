public class Board{
   public String[] boardSections = new String[]{"topRight", "bottomLeft", "bottomRight", "topLeft"};
   public Room[] rooms = new Room[12];
   public Room currentRoom;
   
   public void Board(){
      arrangeBoard();
      setRooms();
   }
   private void arrangeBoard(){
   
   }
   public void setRooms(){
      /*use this to build all 12 rooms and push int rooms array*/
   }
   public Room getRoom(){
      return currentRoom;
   }
}