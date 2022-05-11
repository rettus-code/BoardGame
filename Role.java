public class Role {
   private int locationX;
   private int locationY;
   private int width;
   private int height;
   private String name;
   private String line;
   private int level;
   private boolean onCard;
   private boolean isTaken;

   public Role(String name, int level, String line, int w, int h, int x, int y, boolean onCard) {
      this.name = name;
      this.line = line;
      this.level = level;
      this.onCard = onCard;
      this.isTaken = false;
      this.width = w;
      this.height = h;
      this.locationX = x;
      this.locationY = y;
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

   public String getName() {
      return this.name;
   }

   public String getLine() {
      return this.line;
   }

   public int getLevel() {
      return this.level;
   }

   public boolean isOnCard() {
      return this.onCard;
   }

   public boolean isTaken() {
      return this.isTaken;
   }

   private boolean act(int diceRoll) {
      return diceRoll >= this.level;
   }

   private boolean rehearse() {
      return true;
   }

   public String toString() {
      String roleStr = "Part: " + this.name + " Line: " + this.line + " Level: " + this.level;
      return roleStr;
   }

}
