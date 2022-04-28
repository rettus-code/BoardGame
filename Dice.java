public class Dice{
   private int roll;
   
   public int rollDice(){
      this.roll = (int)(Math.random() * 6) + 1;
      return this.roll;
   }
   public int getRoll(){
      return this.roll;
   }
   private void setRoll(int rank){
      this.roll = rank;
   }
}