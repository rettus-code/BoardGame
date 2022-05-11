public class Set extends Room {
   public Role[] parts;  
   private Take[] takes;
   public int takesCompleted;
   private int numOnCardRolesFinished;
   private SceneCard sceneCard;   

   public Set(String name, int x, int y, int h, int w, String[] neighbors, Role[] roles, Take[] takes) {
      this.setName(name);
      this.setLocationX(x);
      this.setLocationY(y);
      this.setHeight(h);
      this.setWidth(w);    
      this.takesCompleted = 0;
      this.numOnCardRolesFinished = 0;      
      this.setNeighbors(neighbors);
      this.parts = roles;
      this.takes = takes;
   }

   public boolean hasSceneCard() {
      return (this.sceneCard != null);
   }

   public SceneCard getSceneCard() {
      return this.sceneCard;
   }

   public boolean isSet() {
      return true;
   }

   public Role[] getRoles() {
      return this.parts;
   }

   public int getNumOnCardRolesFinished() {
      return numOnCardRolesFinished;
   }

   public void setNumOnCardRolesFinished() {
      this.numOnCardRolesFinished++;
   }

   public void addSceneCard(SceneCard newCard) {
      if (this.sceneCard == null) {
         this.sceneCard = newCard;
      } else {
      }
   }

   private void removeSceneCard() {
      this.sceneCard = null;
   }

   private void wrapScene() {
      removeSceneCard();
   }

   public boolean removeShotCounter() {
      this.takes[takesCompleted] = null;
      takesCompleted++;
      if(takesCompleted == takes.length){
         wrapScene();
         return true;
      }
      return false;
   }

   private int getNumCompletedShotCounters(){
      // returns the number of not completed shot counters (Takes)
      int num = 0;
      for(int i = 0; i < takes.length; i++) {
         if(!takes[i].isComplete()) {
            num++;
         }
      }
      return num;
   }

}
