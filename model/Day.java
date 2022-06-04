package model;

public class Day {
   private int dayNum;
   public static int numScenes;
   private SceneCard[] deck;

   private Day() {
   };

   public Day(int dNum, SceneCard[] d) {
      this.dayNum = ++dNum;
      this.deck = d;
      this.numScenes = 10;
   }

   public int getDay() {
      return this.dayNum;
   }
   public void setDay(int day){
      this.dayNum = day;
   }
   public static int getNumScenes() {
      return numScenes;
   }

}