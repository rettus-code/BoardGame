package model;
import java.util.*;

import java.util.Scanner;

public class Player {

	public interface observer {
		void stateChanged(Player player);
	 }
	 private ArrayList<observer> observers;
  
	 public void subscribe(observer o) {
		observers.add(o);
	 }
  
	 protected void stateChanged(Player player){
		for(observer o : observers) {
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

	private enum TurnState {
		BEGIN_TURN, MOVED, IN_ROLE, END_TURN
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

	public void setRoom(Room room) {
		this.room = room;
		this.locationX = room.getLocationX();
		this.locationY = room.getLocationY();
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

	public void setCompletedScene(boolean reset) {
		this.completedScene = false;
	}

	public boolean getCompletedScene() {
		return this.completedScene;
	}

	public boolean takeTurn() {
		boolean turnComplete = false;
		System.out.printf("Player %d %s, your turn\n", this.id + 1, this.getName());
		System.out.printf("Your location is: %s\n", this.room.getName());
		this.current_state = TurnState.BEGIN_TURN;

		while (this.current_state != TurnState.END_TURN) {
			switch (this.current_state) {
				case BEGIN_TURN:
					if (this.currentRole != null) {
						this.current_state = TurnState.IN_ROLE;
					} else if (this.room.getName().equals("trailer")) {
						this.promptMove();
					} else if (this.room.getName().equals("office")) {
						this.promptUpgradeMove();
					} else if (!this.room.hasSceneCard() && room.isSet()) {
						promptMove();
					} else {
						promptTakeRoleMove();
					}
					break;
				case MOVED:
					if (this.room.getName().equals("trailer")) {
						this.endTurn();
					} else if (this.room.getName().equals("office")) {
						this.promptUpgrade();
					} else if (!this.room.hasSceneCard() && room.isSet()) {
						this.endTurn();
					} else {
						this.promptTakeRole();
					}
					break;
				case IN_ROLE:
					this.promptInRole();
					break;
				case END_TURN:
					turnComplete = true;
					System.out.printf("Player %d Turn ended\n", this.id + 1);
					break;
			}
		}

		return turnComplete;
	}

	private int promptInRole() {
		// player is in a role
		// choices are act or rehearse
		int i = 0;
		if (rehearseCounter < 6) {
			System.out.println("Make a choice:");
			System.out.println("1. act");
			System.out.println("2. rehearse");
			System.out.println("3. end turn");
			while (i != 1 && i != 2 && i != 3) {
				try {
					i = scanner.nextInt();
				} catch (Exception e) {
					scanner.next();
					System.out.println("Please enter Numbers");
				}
			}

			switch (i) {
				case 1:
					this.act();
					this.endTurn();
					break;
				case 2:
					this.rehearse();
					this.endTurn();
					break;
				case 3:
					this.endTurn();
					break;
				default:
			}
		} else {
			System.out.println("You have 6 rehearsal");
			System.out.println("Make a choice:");
			System.out.println("1. act");
			System.out.println("2. end turn");
			while (i != 1 && i != 2) {
				try {
					i = scanner.nextInt();
				} catch (Exception e) {
					scanner.next();
					System.out.println("Please enter Numbers");
				}
			}
			switch (i) {
				case 1:
					this.act();
					this.endTurn();
					break;
				case 2:
					i++;
					this.endTurn();
					break;
				default:
			}
		}
		return i;
	}

	private void promptUpgradeMove() {
		// choices are upgrade then move or just move
		System.out.println("Make a choice:");
		System.out.println("1. upgrade");
		System.out.println("2. move");
		System.out.println("3. endTurn");
		int i = 0;
		while (i != 1 && i != 2 && i != 3) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}
		switch (i) {
			case 1:
				this.promptUpgradeChoices();
				break;
			case 2:
				this.move();
				break;
			case 3:
				this.endTurn();
				break;
			default:
		}
	}

	private void promptUpgrade() {
		System.out.println("Make a choice:");
		System.out.println("1. upgrade");
		System.out.println("2. endTurn");
		int i = 0;
		while (i != 1 && i != 2) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}
		switch (i) {
			case 1:
				this.promptUpgradeChoices();
				break;
			case 2:
				this.endTurn();
				break;
		}
	}

	private void promptUpgradeChoices() {
		if (this.room.getName().equals("office")) {
			System.out.println("**************Casting Office************");
			System.out.println(this.toString());
			System.out.println("Choose Your Upgrade:");
			// print upgrades out
			Upgrade[] upgrades;
			CastingOffice co = (CastingOffice) this.room;
			upgrades = co.getUpgrades(this.getRank());
			int j = 0;
			for (; j < upgrades.length; j++) {
				System.out.printf("%d. %s\n", j + 1, upgrades[j].toString());
			}
			int end = j + 1;
			System.out.printf("%d. cancel\n", end);
			int i = 0;
			while (i < 1 || i > end) {
				try {
					i = scanner.nextInt();
				} catch (Exception e) {
					scanner.next();
					System.out.println("Please enter Numbers");
				}
			}

			if (i == end) {
				this.endTurn();
			} else {
				this.upgrade(upgrades[i - 1]);
			}
		} else {
			System.out.println("Error: not in Casting Office, cannot upgrade");
			this.endTurn();
		}

	}

	private void promptMove() {
		System.out.println("Make a choice:");
		System.out.println("1. move");
		System.out.println("2. endTurn");
		int i = 0;
		while (i != 1 && i != 2) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}
		switch (i) {
			case 1:
				this.move();
				break;
			case 2:
				this.endTurn();
				break;
			default:
		}
	}

	private void promptTakeRoleMove() {
		// not in a role and not in casting office so choices are move or take a role
		System.out.println("Make a choice:");
		System.out.println("1. move");
		System.out.println("2. take a role");
		System.out.println("3. endTurn");
		int i = 0;
		while (i != 1 && i != 2 && i != 3) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}

		switch (i) {
			case 1:
				this.move();
				break;
			case 2:
				this.takeRole();
				break;
			case 3:
				this.endTurn();
				break;
			default:
		}
	}

	private void promptTakeRole() {
		// not in a role and not in casting office so choices are move or take a role
		System.out.println("Make a choice:");
		System.out.println("1. take a role");
		System.out.println("2. endTurn");
		int i = 0;
		while (i != 1 && i != 2) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}

		switch (i) {
			case 1:
				this.takeRole();
				break;
			case 2:
				this.endTurn();
				break;
		}
	}

	private void endTurn() {
		System.out.println("end turn");
		System.out.println("");
		this.current_state = TurnState.END_TURN;
		stateChanged(this);
	}

	private void move() {
		System.out.println("Which room would you like to move to?");
		String[] neighbors = this.room.getNeighbors();
		for (int i = 0; i < neighbors.length; i++) {
			System.out.printf("%d. %s\n", i + 1, neighbors[i]);
		}
		int i = 0;
		while (i < 1 || i > neighbors.length + 1) {
			try {
				i = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				System.out.println("Please enter Numbers");
			}
		}

		Room moveRoom = Board.getRoom(neighbors[i - 1]);
		this.setRoom(moveRoom);

		System.out.printf("Moved to %s\n", this.room.getName());
		this.current_state = TurnState.MOVED;
		stateChanged(this);
	}

	private void upgrade(Upgrade choice) {
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
			System.out.printf("Upgrading to choice %s\n", choice.toString());
			System.out.println(this.toString());
		} else {
			System.out.printf("Cannot upgrade to choice %s\n", choice.toString());
		}
		stateChanged(this);
	}

	public boolean takeRole() {
		boolean result = false;
		if (this.room.isSet()) {
			Set set = (Set) this.room;
			Role[] onCardRoles = set.getSceneCard().getRoles();
			Role[] extraRoles = set.getRoles();
			Role[] roles = new Role[onCardRoles.length + extraRoles.length];
			int i = 0;
			for (; i < onCardRoles.length; i++) {
				roles[i] = onCardRoles[i];
			}
			for (int j = 0; j < extraRoles.length; j++) {
				roles[i++] = extraRoles[j];
			}

			System.out.println("Choose an available Role:");
			System.out.printf("Your rank is %d Its rank must be lower or equal to yours\n", this.getRank());
			System.out.println("Oncard Roles:");
			i = 0;
			for (; i < onCardRoles.length; i++) {
				System.out.printf("%d. %s\n", i + 1, roles[i].toString());
			}
			System.out.println("Extra Roles:");
			for (; i < roles.length; i++) {
				System.out.printf("%d. %s\n", i + 1, roles[i].toString());
			}
			int end = i + 1;
			System.out.printf("%d. cancel\n", end);
			i = 0;
			while (i < 1 || i > end) {
				try {
					i = scanner.nextInt();
				} catch (Exception e) {
					scanner.next();
					System.out.println("Please enter Numbers");
				}
			}

			if (i == end) {
				this.endTurn();
			} else {
				boolean success = this.takeRole(roles[i - 1]);
				if (!success) {
					this.takeRole();
				}
			}
			result = true;
		}	

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
				stateChanged(this);
			} else {
				System.out.printf("Cannot take role %s because its already taken\n", newRole.getName());
			}
		} else {
			System.out.printf("Cannot take role %s because your rank is not high enough\n", newRole.getName());
		}		
		return result;
	}

	private void act() {
		System.out.println("Roll the dice to act");
		int roll = this.rollDice() + rehearseCounter;
		System.out.printf("You rolled %d\n", roll);
		if (this.room.isSet()) {
			Set set = (Set) this.room;
			int budget = set.getSceneCard().getBudget();
			System.out.printf("The film budget is $%d\n", budget);
			if (roll >= budget) {
				System.out.printf("You succeeded!\n");
				removeShotCounter(set);
			} else {
				System.out.printf("You failed\n");
				if (!currentRole.isOnCard()) {
					this.money++;
				}
				System.out.println("Your dollars = " + this.money + " and credits = " + this.credits);
			}
		}
		stateChanged(this);
	}

	private void rehearse() {
		rehearseCounter++;
		System.out.println("You have " + rehearseCounter + " rehearsal points");
		stateChanged(this);
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
			System.out.println("Insufficient funds");
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
			System.out.println("Insufficient funds");
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
		}
	}

	private int rollDice() {
		return Game.rollDice();
	}

	public String toString() {
		return "Player" + this.getID() + " Rank:" + this.getRank() + " Money:$" + this.getMoney() + " Credits:"
				+ this.getCredits();
	}

}