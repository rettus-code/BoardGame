public class PlayerDie {
   private int roll;
   private int numSides;
   
   public PlayerDie(int numsides){
      this.numSides = numsides;
   }
   
   public int getRoll(){
      return this.roll;
   }
   public void setRoll(int rank){
      this.roll = rank;
   }
}