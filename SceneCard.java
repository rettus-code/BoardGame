public class SceneCard {
   // Functional cohesion
   private int locationX;
   private int locationY;
   private int width;
   private int height;
   private String name;
   private int budget;
   private int number;
   private String setting;
   private Role[] parts;
   private boolean isFlipped;
   private String img;

   public SceneCard(String name, String img, int budget, int number, String setting, Role[] roles) {
      this.name = name;
      this.budget = budget;
      this.number = number;
      this.setting = setting;
      this.parts = roles;
      this.isFlipped = false;
      this.img = img;
   }
   public String getName() {
      return this.name;
   }
   public String getSetting() {
      return this.setting;
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

   public int getBudget() {
      return this.budget;
   }

   public Role[] getRoles() {
      return this.parts;
   }

   public boolean isFlipped() {
      return this.isFlipped;
   }

   public void wrapScene() {

   }
}
