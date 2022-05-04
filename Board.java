public class Board{
   public String[] boardSections = new String[]{"topRight", "bottomLeft", "bottomRight", "topLeft"};
   public static Room[] rooms = new Room[12];
   public Room currentRoom;
   
   public Board(){
      arrangeBoard();
      setRooms();
   }
   private void arrangeBoard(){
   
   }
   public void setRooms(){
      rooms[0] = new Trailers();
      rooms[1] = new CastingOffice();
      rooms[2] = new Set("temp");
      /*use this to build all 12 rooms and push int rooms array*/
   }
   public static Room getRoom(String roomName){
      boolean found = false;
       int i = 0;
      Room room = rooms[i];     
      
      while(found == false && i < rooms.length) {
         if(roomName.equals(rooms[i].getName())) {
            found = true;
            room = rooms[i];
         } else {
            i++;
         }
      }
      
      return room;
   }
   public Room getRoom(){
      return currentRoom;
   }
}