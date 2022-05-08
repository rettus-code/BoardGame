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

   public boolean isSet() {
      return true;
   }

   public int getNumOnCardRolesFinished() {
      return numOnCardRolesFinished;
   }

   private void addSceneCard(SceneCard newCard) {
      if (this.sceneCard == null) {
         this.sceneCard = newCard;
      } else {
      }
   }

   private void removeSceneCard() {
      this.sceneCard = null;
   }

   private void wrapScene() {
   }

}
