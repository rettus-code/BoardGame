public class CastingOffice extends Room{
   private Upgrade[] upgrades;
   private Upgrade temp = new Upgrade(1, "temp", 1);

   public CastingOffice(/*, Upgrades[] upgrades*/) {
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
