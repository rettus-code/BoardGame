public class Upgrade {
   // Functional cohesion
   private int locationX;
   private int locationY;
   private int width;
   private int height;
   private int level;
   private String currency;
   private int amount;

   public Upgrade(int level, String currency, int amount, int x, int y, int h, int w) {
      this.level = level;
      this.currency = currency;
      this.amount = amount;
      this.setLocationX(x);
      this.setLocationY(y);
      this.setWidth(w);
      this.setHeight(h);    
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

   public int getLevel() {
      return this.level;
   }

   public String getCurrency() {
      return this.currency;
   }

   public int getAmount() {
      return this.amount;
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

   public String toString() {
      String upgradeStr = "level:" + this.level + ", currency:" + this.currency + ", amount:" + this.amount;
      return upgradeStr;     
   }

}
