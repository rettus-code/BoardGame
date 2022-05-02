public class Board{
   public String[] boardSections = new String[]{"topRight", "bottomLeft", "bottomRight", "topLeft"};
   public Room[] rooms = new Room[12];
   public Room currentRoom;
   
   public Board(){
      arrangeBoard();
      setRooms();
   }
   private void arrangeBoard(){
   
   }
   public void setRooms(){
      rooms[0] = new Trailers("temp");
      rooms[1] = new CastingOffice("temp");
      rooms[2] = new Set("temp");
      /*use this to build all 12 rooms and push int rooms array*/
   }
   public Room getRoom(){
      return currentRoom;
   }
}