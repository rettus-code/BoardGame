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

   public Upgrade[] getUpgrades() {
      return this.upgrades;
   }
}
