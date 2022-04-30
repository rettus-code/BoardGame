public class Set extends Room{
   public Role[] parts;
   public int takes;
   public int takesCompleted;
   private int numOnCardRolesFinished;
   private SceneCard sceneCard;
   
   public Set(String name, Role[] roles, int numTakes) {
      this.parts = roles;
      this.takes = numTakes;
      this.takesCompleted = 0;
      this.numOnCardRolesFinished = 0;
      this.setName(name);      
   }
   
   public boolean hasSceneCard(){
      return (this.sceneCard != null);
   }
   
   public boolean isSet(){
      return true;
   }
   
   public int getNumOnCardRolesFinished(){
      return numOnCardRolesFinished;
   }
   
   private void addSceneCard(SceneCard newCard){
      if(this.sceneCard == null) {
      this.sceneCard = newCard;
      } else {
      }      
   }
   
   private void removeSceneCard(){
      this.sceneCard = null;
   }
   
   private void wrapScene(){} 

}
