import java.io.*;
import java.util.Scanner;
 
public class Player{
	private String	name;
	private int	id;
	private int	rank;
	private int	money;
	private int	credits;
	private PlayerDie	playerDie;
	private Role currentRole;
	private String	room;
	private Scanner scanner	= new	Scanner(System.in);
		
	public Player(String	name,	int numPlayers, int newID){
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
      this.playerDie = new PlayerDie(6);	 
		this.playerDie.setRoll(this.rank);
      this.room = "Trailers";
	}
	
   public int getID() {
      return this.id;
   }
  public	int getMoney(){
	return this.money;
  } 
  
  public	int getCredits(){
	return this.credits;
  }
  
	public int getRank()	{
		return this.rank;
	}
	
	public void	takeChips(){}
	
	public void	takeMoney(){}
	
	public void	takeCredits(){}
		
	public int countScore()	{
		return this.money	+ this.rank	+ this.credits;
	}
	
	public String getLocation() {
		return this.room;
	}
	
	public boolean	takeTurn() {
	int i	= 0;	
	System.out.printf("Player %d turn\n", this.id);
		if(this.currentRole != null){
		//	player is in a	role
		//	choices are	act or rehearse
		System.out.println("Make a choice:");
		System.out.println("1. act");	
		System.out.println("2. rehearse");
		System.out.println("3. end turn");		 
	  
		while(i != 1 && i	!=	2 && i != 3) {
			i = scanner.nextInt();
         System.out.printf("you entered %d\n",i);
		}
		
		switch(i){
		case 1:
			this.act();			  
			break;
			case 2:			 
			this.rehearse();
			break;
			case 3:
			this.endTurn();
			break;
			default:			  
		}
		
		}else	if(this.room.equals("Casting Office")){
		//	choices are	upgrade then move	or	just move
		System.out.println("Make a choice:");	 
		System.out.println("1. upgrade"); 
		System.out.println("2. move");	
		System.out.println("3. endTurn");
		
		while(i != 1 && i	!=	2 && i != 3) {
			i = scanner.nextInt();
		}
		
		switch(i){
		case 1:
			this.upgrade(); 
			System.out.println("Make a choice:");	 
			System.out.println("1. move"); 
			System.out.println("2. endTurn");
			while(i != 1 || i	!=	2)	{
				i = scanner.nextInt();
			}
			 switch(i){
				 case	1:
					this.move();
					break;
				case 2: 
					this.endTurn();
					break;
					default:
			 }
			break;
			case 2:			 
			this.move();
			break;
			case 3:
			this.endTurn();
			break;
			default:			  
		}

		
		} else {
		//	not in a	role and	not in casting	office so choices	are move	or	take a role
		System.out.println("Make a choice:");		  
			System.out.println("1. move"); 
			System.out.println("2. take a role");
			System.out.println("3. endTurn");
			while(i != 1 && i	!=	2 && i != 3) {
				i = scanner.nextInt();
            System.out.printf("you entered %d\n",i);
			}
			
			 switch(i){
				 case	1:
					this.move();
					break;
				case 2: 
					Role role =	new Role("name", "line", 1, true);
					this.takeRole(role);
					break;
					case 3:
					this.endTurn();
					default:
			 }
		}
		
		return true;
	}
	
	private void endTurn() {
		System.out.println("end turn");	 
	}

	
	private void move() {
		System.out.println("Move");
	}
	
	private void upgrade() {
		System.out.println("Upgradde with two d's");
	}
	
	public boolean	takeRole(Role newRole){
      System.out.printf("You're taking a role called: %s\n", newRole.getName());
		boolean result	= false;
		if(!newRole.isTaken()){			  
			this.currentRole = newRole; 
			result =	true;			 
		} else {
			
		}		 
		return result;
	}
	
	private void completeRole() {
		this.currentRole = null;
	}
	
	private void act() {
		System.out.println("Act");			
	}
	
	private void rehearse()	{
		System.out.println("Rehearse");
	}
	
	private void payMoney(){}
	private void payCredits(){}
	private void removeShotCounter(){}
	
	private int	rollDice(Dice gameDice)	{
		return gameDice.rollDice();
	}

}
