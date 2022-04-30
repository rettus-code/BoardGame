public class SceneCard{
   // Functional cohesion
   private String name;
   private int budget;
   private int number;
   private String description;
   private Role[] parts;
   private boolean isFlipped;
   
   public SceneCard(String name, int budget, int number, String description, Role[] roles) {
      this.name = name;
      this.budget = budget;
      this.number = number;
      this.description = description;
      this.parts = roles;
      this.isFlipped = false;
   }
   
   public Role[] flipCard(){
      if(!this.isFlipped()){
         this.isFlipped = true;
      } else {
         //just being safe
      }
      return this.parts;
   }
   
   public boolean isFlipped() {
      return this.isFlipped;
   }
   
   public void wrapScene(){
      
   }
}
