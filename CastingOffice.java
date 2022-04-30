public class CastingOffice extends Room{
   private Upgrades[] upgrades;

   public CastingOffice(String name, Upgrades[] upgrades) {
        this.upgrades = upgrades;
        this.setName(name);      
   }

   public boolean isSet() {
      return false;
   }
   
   public boolean upgrade() {
      return true;
   }
   
   public Upgrade[] getUpgrades(){
      return this.upgrades;
   }
}
