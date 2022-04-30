public class Player{
   private String	name;
   private int	id;
   private int	rank;
   private int	money;
   private int	credits;
   private PlayerDie	playerDie;
   private Role currentRole;
   private String room;
      
   public Player(String name, int	numPlayers,	int newID){
   	this.id = newID;	  
   	if(numPlayers == 7 || numPlayers	==	8)	{
   		this.rank =	2;
   	} else { 
   		this.rank =	1;
   	}
   	this.money = 0;
   	if(numPlayers == 5) {
   		this.credits =	2;
   	} else if(numPlayers	==	6){
   		this.credits =	4;
   	}	 
   	this.playerDie.setRoll(this.rank);
   }
	
  public	int getMoney(){
   return this.money;
  } 
  
  public	int getCredits(){
   return this.credits;
  }
  
   public int getRank() {
      return this.rank;
   }
   
   public void takeChips(){}
   
   public void takeMoney(){}
   
   public void takeCredits(){}
   	
   public int countScore()	{
		return this.money	+ this.rank	+ this.credits;
	}
	
	public String getLocation() {
		return this.room;
	}
	
	public boolean	takeTurn() {	
		return true;
	}
	
	private void move() {
	}
	
	private void upgrade() {
	}
	
	public boolean takeRole(Role newRole){
      boolean result = false;
      if(!newRole.isTaken()){         
         this.currentRole = newRole; 
         result = true;        
      } else {
         
      }      
      return result;
   }
	
   private void completeRole() {
      this.currentRole = null;
   }
   
	private void act() {
      	
	}
	
	private void rehearse()	{
	}
	
	private void payMoney(){}
	private void payCredits(){}
	private void removeShotCounter(){}
	
	private int	rollDice(Dice gameDice)	{
		return gameDice.rollDice();
	}

}
