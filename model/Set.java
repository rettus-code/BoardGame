package model;
import java.util.Arrays;
import java.util.Collections;

public class Set extends Room {
   public Role[] parts;
   private Take[] takes;
   public int takesCompleted;
   private SceneCard sceneCard;
   private int roomNum;

   public Set(String name, int x, int y, int h, int w, String[] neighbors, Role[] roles, Take[] takes, int roomNum) {
      this.setName(name);
      this.setLocationX(x);
      this.setLocationY(y);
      this.setHeight(h);
      this.setWidth(w);
      this.takesCompleted = 0;
      this.setNeighbors(neighbors);
      this.parts = roles;
      this.takes = takes;
      this.roomNum = roomNum;
      
   }

   public SceneCard getSceneCard() {
      return this.sceneCard;
   }

   public boolean isSet() {
      return true;
   }

   // returns only the extra roles
   public Role[] getRoles() {
      return this.parts;
   }

     // returns on card and extra roles in that order
   public Role[] getAllRoles() { 
      Role[] oncardRoles = this.getSceneCard().getRoles(); // oncard roles        
      Role[] extraRoles = this.getRoles(); // extra roles     
      Role[] allRoles = new Role[oncardRoles.length+extraRoles.length];
      int i = 0;
      for(; i<oncardRoles.length; i++) {
         allRoles[i]=oncardRoles[i];
      }
      for(int j = 0; j<extraRoles.length; j++) {
         allRoles[i++]=extraRoles[j];
      }
      return allRoles;
   }

   public int getRoomNum(){
      return this.roomNum;
   }
   public void addSceneCard(SceneCard newCard) {
      if (this.sceneCard == null) {
         this.sceneCard = newCard;
         this.takesCompleted = 0;
      } else {
      }
   }
   public int[] getCardPosition(){
      int[] point = new int[2];
      String room;
      switch(this.getName()){
         case "Main Street":
               point[0] = 969;
               point[1] = 28;
               break;
         case "Saloon":
               point[0] = 631;
               point[1] = 280;
               break;
         case "Jail":
               point[0] = 281;
               point[1] = 27;
               break;
         case "General Store":
               point[0] = 370;
               point[1] = 282;
               break;
         case "Train Station":
               point[0] = 21;
               point[1] = 69;
               break;
         case "Ranch":
               point[0] = 252;
               point[1] = 478;
               break;
         case "Secret Hideout":
               point[0] = 27;
               point[1] = 732;
               break;
         case "Bank":
               point[0] = 623;
               point[1] = 475;
               break;
         case "Church":
               point[0] = 623;
               point[1] = 734;
               break;
         case "Hotel":
               point[0] = 969;
               point[1] = 740;
               break;
      }
      return point;
   }
   public boolean hasSceneCard() {
      return this.sceneCard != null;
   }

   public void removeSceneCard() {
      controller.BoardLayersListener.getInstance().removeCard(this.sceneCard.getImage(), this.getCardPosition(), this.getRoomNum());
      this.sceneCard = null;
   }

   public void wrapScene() {
      boolean anyOnCard = false;
      int[] onCard = new int[8];
      int[] offCard = new int[8];
      Role[] onCardRoles = this.sceneCard.getRoles();
      Role[] roles = new Role[onCardRoles.length + parts.length];
      int k = 0;
      for (; k < onCardRoles.length; k++) {
         roles[k] = onCardRoles[k];
      }
      for (int j = 0; j < parts.length; j++) {
         roles[k++] = parts[j];
      }
      for (int i = 0; i < roles.length; i++) {
         for (int j = 0; j < Game.playerArray.length; j++) {
            if (Game.playerArray[j] != null) {
               if (Game.playerArray[j].getRoom().getName() == Game.playerArray[Game.getActivePlayer()].getRoom()
                     .getName() && Game.playerArray[j].getCurrentRole() != null) {

                  if (roles[i].getName() == Game.playerArray[j].getCurrentRole().getName()
                        && Game.playerArray[Game.getActivePlayer()].getRoom().getName() == this.getName()) {
                     if (Game.playerArray[j].getCurrentRole().isOnCard()) {
                        anyOnCard = true;
                        onCard[j] = 1;
                     } else {
                        offCard[j] = 1;
                     }
                     //Game.playerArray[j].move(Board.getRoom(this.getName()));
                  }
               }
            }
         }
      }
      if (anyOnCard) {
         for (int i = 0; i < 8; i++) {
            if (offCard[i] == 1) {
               int money = Game.playerArray[i].getCurrentRole().getLevel();
               Game.playerArray[i].addMoney(money);
               Game.playerArray[i].resetRole();
               Game.playerArray[i].rehearseReset();
            }
         }
         Integer[] rewards = new Integer[this.sceneCard.getBudget()];
         for (int i = 0; i < this.sceneCard.getBudget(); i++) {
            rewards[i] = (int) (Math.random() * 6) + 1;
         }
         int j = 0;
         int i = 0;
         Arrays.sort(rewards, Collections.reverseOrder());
         while (j <= Game.playerArray.length) {
            if (onCard[j] == 1) {

               // int money = (int) (Math.random() * 6) + 1;
               Game.playerArray[j].addMoney(rewards[i]);
               Game.playerArray[j].resetRole();
               Game.playerArray[j].rehearseReset();
               i++;
               if (i >= this.sceneCard.getBudget()) {
                  break;
               }
            }
            if (j == Game.playerArray.length - 1) {
               j = 0;
            } else {
               j++;
            }
         }
      } else {
         for (int i = 0; i < 8; i++) {
            if (offCard[i] == 1) {
               Game.playerArray[i].resetRole();
               Game.playerArray[i].rehearseReset();
               // System.out.println(Game.playerArray[i].getName());
            }
         }
      }
      removeSceneCard();      
   }
   public int[][] getTakes(){
      int[][] point = new int[takes.length][2];
      for(int i = 0; i < takes.length; i++){
         point[i][0] = this.takes[i].getLocationX();
         point[i][1] = this.takes[i].getLocationY();
      }
      return point;
   }
   public Take getTake(int i){
      return this.takes[i];
   }
   public boolean removeShotCounter() {
      view.BoardView.getInstance().completedShot(this.takes[takesCompleted].getBoardNum());
      takesCompleted++;
      if (takesCompleted == takes.length) {
         return true;
      }
      return false;
   }
   

}
