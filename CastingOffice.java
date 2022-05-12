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

   public boolean upgrade() {
      return true;
   }

   public Upgrade[] getUpgrades(int rank) {
      int numUpgrades = 0;
      for(int i = 0; i < upgrades.length; i++) {
         if(upgrades[i].getLevel() > rank) {
            numUpgrades++;
         }
      }
      Upgrade[] possibleUpgrades = new Upgrade[numUpgrades];
      int k = 0;
      for(int i = 0; i < upgrades.length; i++) {
         if(upgrades[i].getLevel() > rank) {
            possibleUpgrades[k++] = upgrades[i];
         }
      }
      return possibleUpgrades;
   }

   public boolean hasSceneCard() {
      return false;
   }
}
