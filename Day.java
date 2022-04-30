public class Day{
   private int dayNum;
   private int numScenes;
   private SceneCard[] deck;
   
   private Day(){};
   public Day(int dNum, SceneCard[] d){
      this.dayNum = dNum++;
      this.numScenes = deck.length;
      this.deck = d;
      placeShotCounters();
      dealSceneCard(deck);
   }
   public int getDay(){
      return this.dayNum;
   }
   public int getNumScenes(){
      return this.numScenes;
   }
   public void sceneComplete(){
      int scenes = this.numScenes--;
      if(scenes < 2){
         endDay();
      }
   }
   public void dealSceneCard(SceneCard[] deck){
   
   }
   public void placeShotCounters(){
   
   }
   public void endDay(){
      
   }
   
}