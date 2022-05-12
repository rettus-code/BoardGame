import java.util.Arrays;
import java.util.Collections;

public class Set extends Room {
   public Role[] parts;  
   private Take[] takes;
   public int takesCompleted;
   private int numOnCardRolesFinished;
   private SceneCard sceneCard;   

   public Set(String name, int x, int y, int h, int w, String[] neighbors, Role[] roles, Take[] takes) {
      this.setName(name);
      this.setLocationX(x);
      this.setLocationY(y);
      this.setHeight(h);
      this.setWidth(w);    
      this.takesCompleted = 0;
      this.numOnCardRolesFinished = 0;      
      this.setNeighbors(neighbors);
      this.parts = roles;
      this.takes = takes;
   }

   public SceneCard getSceneCard() {
      return this.sceneCard;
   }

   public boolean isSet() {
      return true;
   }

   public Role[] getRoles() {
      return this.parts;
   }

   public int getNumOnCardRolesFinished() {
      return numOnCardRolesFinished;
   }

   public void setNumOnCardRolesFinished() {
      this.numOnCardRolesFinished++;
   }

   public void addSceneCard(SceneCard newCard) {
      if (this.sceneCard == null) {
         this.sceneCard = newCard;
      } else {
      }
   }
   public boolean hasSceneCard() {
      return this.sceneCard != null;
   }
   private void removeSceneCard() {
      this.sceneCard = null;
   }

   public void wrapScene() {
      boolean anyOnCard = false;
      int[] onCard = new int[8];
      int[] offCard = new int[8];
			Role[] onCardRoles = this.sceneCard.getRoles();					
			Role[] roles = new Role[onCardRoles.length + parts.length];
			int k = 0;
			for(; k < onCardRoles.length; k++) {
				roles[k] = onCardRoles[k];
			}
			for(int j = 0; j < parts.length; j++) {
				roles[k++] = parts[j];
			}
      for(int i = 0; i < roles.length; i++){
         for(int j = 0; j < Game.playerArray.length; j++){
            if(Game.playerArray[j] != null){
               if(Game.playerArray[j].getRoom().getName() == Game.playerArray[Game.getActivePlayer()].getRoom().getName()){
               if(roles[i].getName() == Game.playerArray[j].getCurrentRole().getName() && Game.playerArray[Game.getActivePlayer()].getRoom().getName() == this.name){
                  if(Game.playerArray[j].getCurrentRole().isOnCard()){
                     anyOnCard = true;
                     onCard[j] = 1;
                  } else {
                     offCard[j] = 1;
                  }
               Game.playerArray[j].setRoom(Board.getRoom(this.name));
               
               }
               }
            }
         }
      }
      if(anyOnCard){
         for(int i = 0; i < 8; i++){
            if(offCard[i] == 1){
               int money = Game.playerArray[i].getCurrentRole().getLevel();
               Game.playerArray[i].addMoney(money);
               Game.playerArray[i].resetRole();
                Game.playerArray[i].rehearseReset();
               System.out.println("Player " + Game.playerArray[i].getName() + " is rewarded " + money + " dollars");
               System.out.println("Your dollars = " + Game.playerArray[i].getMoney() + " and credits = " + Game.playerArray[i].getCredits());
            }
         }
         Integer[] rewards = new Integer[this.sceneCard.getBudget()];
         for(int i = 0; i < this.sceneCard.getBudget(); i++){
            rewards[i] = (int) (Math.random() * 6) + 1;
         }
         int j = 0;
         int i = 0;
         Arrays.sort(rewards, Collections.reverseOrder());
         while(j <= Game.playerArray.length){
            if(onCard[j] == 1){
               
               //int money = (int) (Math.random() * 6) + 1;
               Game.playerArray[j].addMoney(rewards[i]);
               Game.playerArray[j].resetRole();
               Game.playerArray[j].rehearseReset();
               System.out.println("Player " + Game.playerArray[j].getName() + " is rewarded " + rewards[i] + " dollars");
               System.out.println("Your dollars = " + Game.playerArray[j].getMoney() + " and credits = " + Game.playerArray[j].getCredits());
               i++;
               if(i >= this.sceneCard.getBudget()){
                  break;
               }
            }
            if(j == Game.playerArray.length -1){
               j=0;
            } else {
               j++;
            }
         }
      } else {
         for(int i = 0; i < 8; i++){
            if(offCard[i] == 1){
               Game.playerArray[i].resetRole();
               Game.playerArray[i].rehearseReset();
               System.out.println(Game.playerArray[i].getName());
            }
         }
      }
      removeSceneCard();
      Day.sceneComplete();
   }

   public boolean removeShotCounter() {
      this.takes[takesCompleted] = null;
      takesCompleted++;
      if(takesCompleted == takes.length){
         return true;
      }
      return false;
   }

   private int getNumCompletedShotCounters(){
      // returns the number of not completed shot counters (Takes)
      int num = 0;
      for(int i = 0; i < takes.length; i++) {
         if(!takes[i].isComplete()) {
            num++;
         }
      }
      return num;
   }

}
