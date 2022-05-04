public class SceneCard {
   // Functional cohesion
   private int locationX;
   private int locationY;
   private int width;
   private int height;
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

   public int getLocationX() {
      return this.locationX;
   }

   public int getLocationY() {
      return this.locationY;
   }

   public void setLocationX(int x) {
      if (x >= 0) {
         this.locationX = x;
      }
   }

   public void setLocationY(int y) {
      if (y >= 0) {
         this.locationY = y;
      }
   }

   public int getwidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setWidth(int w) {
      if (w >= 0) {
         this.width = w;
      }
   }

   public void setHeight(int h) {
      if (h >= 0) {
         this.height = h;
      }
   }

   public Role[] flipCard() {
      if (!this.isFlipped()) {
         this.isFlipped = true;
      } else {
         // just being safe
      }
      return this.parts;
   }

   public boolean isFlipped() {
      return this.isFlipped;
   }

   public void wrapScene() {

   }
}
