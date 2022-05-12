public class Day {
   private int dayNum;
   public static int numScenes;
   private SceneCard[] deck;

   private Day() {
   };

   public Day(int dNum, SceneCard[] d) {
      this.dayNum = dNum++;
      this.deck = d;
      this.numScenes = deck.length;
      placeShotCounters();
      //dealSceneCard(deck);
   }

   public int getDay() {
      return this.dayNum;
   }

   public int getNumScenes() {
      return this.numScenes;
   }

   public static void sceneComplete() {
      int scenes = numScenes--;
      if (scenes < 2) {
         endDay();
      }
   }

   public static void placeShotCounters() {

   }

   public static void endDay() {

   }

}