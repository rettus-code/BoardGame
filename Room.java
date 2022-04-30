abstract class Room{
   public String name;
   public String[] neighbors;
   
   abstract boolean isSet();
   
   public void setName(String newName){
      this.name = newName;
   }
   public String getName(){
      return this.name;
   }
   
   public void setNeighbors(String[] newNeighbors){
      this.neighbors = newNeighbors;
   }
   
   public String[] getNeighbors(){
      return this.neighbors;
   }
}
