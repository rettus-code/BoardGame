abstract class Room{
   public String name;
   public Room[] neighbors;
   
   abstract boolean isSet();
   
   public void setName(String newName){
      this.name = newName;
   }
   public String getName(){
      return this.name;
   }
   
   public void setNeighbors(Room[] newNeighbors){
      this.neighbors = newNeighbors;
   }
   
   public Room[] getNeighbors(){
      return this.neighbors;
   }
   
}
