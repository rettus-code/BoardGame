public class Dice {
   private int roll;
   private int numSides;

   public Dice() {
   }

   public Dice(int numsides) {
      this.numSides = numsides;
   }

   public int rollDice() {
      this.roll = (int) (Math.random() * 6) + 1;
      return this.roll;
   }

   public int getRoll() {
      return this.roll;
   }
}