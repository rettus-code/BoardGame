public class Day{
   private int dayNum;
   private int numScenes;
   
   private Day(){};
   public Day(int dNum, int nScene){
      this.dayNum = dNum++;
      this.numScenes = nScene;
      placeShotCounters();
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
   public void dealSceneCard(SceneCard deck){
   
   }
   public void placeShotCounters(){
   
   }
   public void endDay(){
      
   }
   
}