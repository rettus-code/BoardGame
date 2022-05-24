package model;

public class CastingOffice extends Room {
   private Upgrade[] upgrades;

   public CastingOffice(String name, int x, int y, int h, int w, String[] neighbors, Upgrade[] upgrades) {
      this.setName(name);
      this.setLocationX(x);
      this.setLocationY(y);
      this.setWidth(w);
      this.setHeight(h);
      this.setNeighbors(neighbors);
      this.upgrades = upgrades;
   }

   public boolean isSet() {
      return false;
   }

   public String[] getUpgradesGUI() {
      // returns all upgrades
      String[] upgradesArray = new String[this.upgrades.length];
      
      for (int i = 0; i < upgrades.length; i++) {
         upgradesArray[i] = this.upgrades[i].toString();               
      }

      return upgradesArray;
   }

   public Upgrade[] getUpgrades() {
      return this.upgrades;
   }

   public Upgrade[] getUpgrades(int rank) {
      // returns all upgrades > the rank passed in
      int numUpgrades = 0;
      for (int i = 0; i < upgrades.length; i++) {
         if (upgrades[i].getLevel() > rank) {
            numUpgrades++;
         }
      }
      Upgrade[] possibleUpgrades = new Upgrade[numUpgrades];
      int k = 0;
      for (int i = 0; i < upgrades.length; i++) {
         if (upgrades[i].getLevel() > rank) {
            possibleUpgrades[k++] = upgrades[i];
         }
      }
      return possibleUpgrades;
   }

   public boolean hasSceneCard() {
      return false;
   }
}
