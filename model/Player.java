package model;

import java.util.*;
import java.util.Scanner;
import java.util.HashMap;

public class Player {

	public interface observer {
		void stateChanged(Player player);
	}

	private ArrayList<observer> observers;

	public void subscribe(observer o) {
		observers.add(o);
	}

	protected void stateChanged(Player player) {
		for (observer o : observers) {
			o.stateChanged(this);
		}
	}

	private int locationX;
	private int locationY;
	private String name;
	private int id;
	private int rank;
	private int money;
	private int credits;
	private PlayerDie playerDie;
	private Role currentRole;
	private Room room;
	private int rehearseCounter;
	private Scanner scanner = new Scanner(System.in);
	private boolean completedScene;

	private HashMap<String, Boolean> possibleActions = new HashMap<>();  

	private enum TurnState {
		BEGIN_TURN, MOVED, IN_ROLE, ACTED, END_TURN
	}

	private TurnState current_state;

	public Player(String name, int numPlayers, int newID, Room room) {
		this.observers = new ArrayList<observer>();
		this.name = name;
		this.id = newID;
		if (numPlayers == 7 || numPlayers == 8) {
			this.rank = 2;
		} else {
			this.rank = 1;
		}
		this.money = 0;
		if (numPlayers == 5) {
			this.credits = 2;
		} else if (numPlayers == 6) {
			this.credits = 4;
		}
		this.playerDie = new PlayerDie(6);
		this.playerDie.setRoll(this.rank);
		this.setRoom(room);
		rehearseCounter = 0;
		this.completedScene = false;
		initPossibleActions();
	}

	// set all possible actions to false
	public void initPossibleActions() {
		possibleActions.clear();
		possibleActions.put("act", false);
		possibleActions.put("rehearse", false);
		possibleActions.put("move", false);
		possibleActions.put("upgrade", false);
		possibleActions.put("takerole", false);
	}

	// update the model and alert observers
	private void setPossibleAction(String action, boolean bool) {
		if (possibleActions.get(action) != bool) {
			possibleActions.put(action, bool);
			stateChanged(this);
		}      
	}

	public int getLocationX() {
		return this.locationX;
	}

	public int getLocationY() {
		return this.locationY;
	}

	public void setLocationX(int x) {
		if (x >= 0) {
			this.locationX = x;
		}
	}

	public void setLocationY(int y) {
		if (y >= 0) {
			this.locationY = y;
		}
	}

	private void setRoom(Room room) {
		this.room = room;
		this.locationX = room.getLocationX();
		this.locationY = room.getLocationY();
		this.playerDie.setLocationX(this.locationX);
		this.playerDie.setLocationY(this.locationY);
		this.current_state = TurnState.MOVED;
		stateChanged(this);
	}

	public Room getRoom() {
		return this.room;
	}

	public int getID() {
		return this.id;
	}

	public int getMoney() {
		return this.money;
	}

	public void addMoney(int m) {
		this.money += m;
		stateChanged(this);
	}

	public int getCredits() {
		return this.credits;
	}

	public void addCredits(int c) {
		this.credits += c;
		stateChanged(this);
	}

	public int getRank() {
		return this.rank;
	}

	public int countScore() {
		return this.money + (5 * this.rank) + this.credits;
	}

	public Room getLocation() {
		return this.room;
	}

	public String getName() {
		return this.name;
	}

	public Role getCurrentRole() {
		return this.currentRole;
	}

	public void resetRole() {
		if (this.currentRole != null) {
			this.currentRole.completeRole();
			this.currentRole = null;
			stateChanged(this);
		} else {
			// do nothing
		}
	}

	public int getRehearseCounter() {
		return rehearseCounter;
	}

	public HashMap<String, Boolean> getPossibleActions() {
		return this.possibleActions;
	}

	public void setCompletedScene(boolean reset) {
		this.completedScene = false;
	}

	public boolean getCompletedScene() {
		return this.completedScene;
	}

	public boolean takeTurn() {
		boolean turnComplete = false;
		
		this.current_state = TurnState.BEGIN_TURN;
		initPossibleActions();
		possibleActions.put("move", true);
		possibleActions.put("takerole", false);
		stateChanged(this);
		while (this.current_state != TurnState.END_TURN) {
			switch (this.current_state) {
				case BEGIN_TURN:
					if (this.currentRole != null) {
						this.current_state = TurnState.IN_ROLE;					
					} else if (this.room.getName().equals("office")) {						
						setPossibleAction("upgrade", true);									
					} else if (this.room.hasSceneCard()){											
						setPossibleAction("takerole", true);						
					}
               stateChanged(this);               
					break;
				case MOVED:
					setPossibleAction("move", false);
					setPossibleAction("act", false);
					setPossibleAction("rehearse", false);
					if (this.room.getName().equals("trailer")) {
						this.endTurn();
					} else if (this.room.getName().equals("office")) {						
						setPossibleAction("upgrade", true);
						setPossibleAction("takerole", false);
					} else if (!this.room.hasSceneCard() && room.isSet()) {
						this.endTurn();
					} else if (this.room.hasSceneCard()){												
						setPossibleAction("upgrade", false);
						setPossibleAction("takerole", true);      
					}

					break;
				case IN_ROLE:
					setPossibleAction("move", false);
					setPossibleAction("upgrade", false);
					setPossibleAction("act", true);
					setPossibleAction("takerole", false);
					setPossibleAction("rehearse", true);						
					break;
				case ACTED: // added for the GUI
					setPossibleAction("move", false);
					setPossibleAction("upgrade", false);
					setPossibleAction("act", false);
					setPossibleAction("takerole", false);
					setPossibleAction("rehearse", false);	
					break;
				case END_TURN:
					turnComplete = true;					
					break;
			}

		}

		return turnComplete;
	}	
	
	public void endTurn() {		
		current_state = TurnState.END_TURN;
		stateChanged(this);
	}

	public void move(Room room) {
		// update the player's location if not in role
		if(this.currentRole == null){
			this.setRoom(room);
			this.current_state = TurnState.MOVED;
			// flip the scene card
			if(room.isSet()) {
				Set set = (Set)room;
				if(set.hasSceneCard()) {
					set.getSceneCard().flipCard();				
				}
			}	
		}	
	}
	
	public String upgradeGUI(int n) {
		String result="<html>";
		if(this.room.getName().equals("office")) {
			CastingOffice office = (CastingOffice)this.room;
			Upgrade[] choices = office.getUpgrades();
			Upgrade choice = choices[n];
			// perform the chosen upgrade?
			boolean success = false;
			int amount = choice.getAmount();
			String currency = choice.getCurrency();
			if (currency.equals("dollar")) {
				if (payMoney(amount)) {
					success = true;
				} else {
					// do nothing
				}
			} else if (currency.equals("credit")) {
				if (payCredits(amount)) {
					success = true;
				} else {
					// do nothing
				}
			}

			if (success) {
				this.rank = choice.getLevel();
				result += "Upgraded to choice:<br>" + choice.toString() + "<br>";			
				stateChanged(this);
			} else {
				result += "Cannot upgrade <br> Insufficient Funds<br>";
			}
		}		
		result += "</html>";		
		return result;
	}	

	public boolean takeRole(Role newRole) {
		System.out.printf("You're taking a role called: %s\n", newRole.getName());
		boolean result = false;
		if (this.getRank() >= newRole.getLevel()) {
			if (newRole.takeRole()) {
				this.currentRole = newRole;
				result = true;
				this.current_state = TurnState.IN_ROLE;
				// on card role locations are relative to the card position
				int x = 0;
				int y = 0;
				if(newRole.isOnCard()){					
					if(this.getLocation().isSet()) {
						Set set = (Set)this.getLocation();
						int[] point = set.getCardPosition();
						x = point[0] + this.currentRole.getLocationX();
						y = point[1] + this.currentRole.getLocationY();
					}
				} else {
					// extra role locations are absolute
					x = this.currentRole.getLocationX();
					y = this.currentRole.getLocationY();
				}
				this.setLocationX(x);
				this.setLocationY(y);
				stateChanged(this);
			} else {
				System.out.printf("Cannot take role %s because its already taken\n", newRole.getName());
			}
		} else {
			System.out.printf("Cannot take role %s because your rank is not high enough\n", newRole.getName());
		}
		return result;
	}

	public String actGUI() {		
		String result="";
		int roll = this.rollDice() + rehearseCounter;	
		result+="<html>You rolled " + roll + "<br>";	
		if (this.room.isSet()) {
			Set set = (Set) this.room;
			int budget = set.getSceneCard().getBudget();
			result+= "The film budget is $" + budget + "<br>";
			if (roll >= budget) {
				result+=("You succeeded!<br>");
				removeShotCounter(set);
			} else {
				result+="You failed<br>";
				if (!currentRole.isOnCard()) {
					this.money++;
				}
				result+="You have $" + this.money + " and " + this.credits + " credit" + "<br></html>";
			}
		}
		this.current_state = TurnState.ACTED;
		stateChanged(this);
		return result;
	}

	private void rehearse() {
		rehearseCounter++;		
		stateChanged(this);
	}

	public String rehearseGUI() {
		String result="";
		// first check if allowed to rehearse
		if(this.getLocation().isSet()){
			Set set = (Set)this.getLocation();
			int budget = set.getSceneCard().getBudget();			
		
			if((rehearseCounter + 1) < budget){		
				rehearseCounter++;			
				stateChanged(this);
				this.current_state = TurnState.ACTED;
				result = "<html>You now have:<br>" + rehearseCounter + " rehearsal points<br></html>";
			} else {
				result = "<html>You cannot rehearse<br>You have:<br>" + rehearseCounter + " rehearsal points<br>You must act<br></html>";		
			}
		}
		return result;	
	}

	public void rehearseReset() {
		rehearseCounter = 0;
		stateChanged(this);
	}

	private boolean payMoney(int amount) {
		boolean result = false;
		int balance = this.getMoney();
		if (balance >= amount) {
			result = true;
			this.money -= amount;
		} else {			
		}
		return result;
	}

	private boolean payCredits(int amount) {
		boolean result = false;
		int balance = this.getCredits();
		if (balance >= amount) {
			result = true;
			this.credits -= amount;
		} else {			
		}
		return result;
	}

	private void removeShotCounter(Set set) {
		boolean wrapped = set.removeShotCounter();
		if (currentRole.isOnCard()) {
			this.credits++;
			this.money++;
			System.out.println("Your dollars = " + this.money + " and credits = " + this.credits);
		} else {
			this.money += 2;
			System.out.println("Your dollars = " + this.money + " and credits = " + this.credits);
		}
		if (wrapped) {			
			System.out.println("The scene is completed");
			set.wrapScene();
			this.completedScene = true;
			this.rehearseReset();
   	}
	}
	private int rollDice() {
		return Game.rollDice();
	}

	public String toString() {
		return "<html>" + "Player" + this.getID() + "<br>" + "Rank:" + this.getRank() + "<br>" 
		+ "Money:$" + this.getMoney() + "<br>" + "Credits:" + this.getCredits() + "<br></html>";
	}

}
