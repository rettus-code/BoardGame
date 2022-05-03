public class Role{
   private String name;
   private String line;
   private int level;
   private boolean onCard;
   private boolean isTaken;
   
   public Role(String name, String line, int level, boolean onCard){
      this.name = name;
      this.line = line;
      this.level = level;
      this.onCard = onCard;
      this.isTaken = false;
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
   
   public boolean onCard(){
      return this.onCard;
   }
   
   public boolean isTaken(){
      return this.isTaken;
   }   
   
   private boolean act(int diceRoll){
      return diceRoll >= this.level;
   }
   
   private boolean rehearse(){
      return true;
   }
   
}
