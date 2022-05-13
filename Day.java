public class Day {
   private int dayNum;
   public static int numScenes;
   private SceneCard[] deck;
   private Day() {
   };

   public Day(int dNum, SceneCard[] d) {
      this.dayNum = dNum++;
      this.deck = d;
      this.numScenes = 10;
      System.out.println("it is now day : " + this.dayNum);
   }

   public int getDay() {
      return this.dayNum;
   }

   public static int getNumScenes() {
      return numScenes;
   }




}