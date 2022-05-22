package model;

public class PlayerDie extends Dice {
   private int locationX;
   private int locationY;
   private int width;
   private int height;
   private int roll;

   public PlayerDie(int numsides) {
      this.numSides = numsides;
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

   public int getRoll() {
      return this.roll;
   }

   public void setRoll(int rank) {
      this.roll = rank;
   }
}