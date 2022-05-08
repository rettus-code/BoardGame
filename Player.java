import java.util.Scanner;

public class Player {
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
	private Scanner scanner = new Scanner(System.in);

	private enum TurnState {
		BEGIN_TURN, MOVED, IN_ROLE, END_TURN
	}

	private TurnState current_state;

	public Player(String name, int numPlayers, int newID, Room room) {
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
	}

	public int getID() {
		return this.id;
	}

	public int getMoney() {
		return this.money;
	}

	public int getCredits() {
		return this.credits;
	}

	public int getRank() {
		return this.rank;
	}

	public void takeChips() {
	}

	public void takeMoney() {
	}

	public void takeCredits() {
	}

	public int countScore() {
		return this.money + this.rank + this.credits;
	}

	public Room getLocation() {
		return this.room;
	}

	public String getName() {
		return this.name;
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
					} else if (this.room.name.equals("trailer")) {
						this.promptMove();
					} else if (this.room.name.equals("office")) {
						this.promptUpgradeMove();
					} else {
						promptTakeRoleMove();
					}
					break;
				case MOVED:
					if (this.room.name.equals("trailer")) {
						this.endTurn();
					} else if (this.room.name.equals("office")) {
						this.promptUpgrade();
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
		System.out.println("Make a choice:");
		System.out.println("1. act");
		System.out.println("2. rehearse");
		System.out.println("3. end turn");
		int i = 0;
		while (i != 1 && i != 2 && i != 3) {
			i = scanner.nextInt();
		}

		switch (i) {
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
			i = scanner.nextInt();
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
		// choices are upgrade then move or just move
		System.out.println("Make a choice:");
		System.out.println("1. upgrade");
		System.out.println("2. endTurn");
		int i = 0;
		while (i != 1 && i != 2) {
			i = scanner.nextInt();
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
			System.out.println("Choose Your Upgrade:");
			// print upgrades out
			Upgrade[] upgrades;
			CastingOffice co = (CastingOffice) this.room;
			upgrades = co.getUpgrades();
			int j = 0;
			for (; j < upgrades.length; j++) {
				System.out.printf("%d. %s\n", j + 1, upgrades[j].toString());
			}
			int end = j + 1;
			System.out.printf("%d. cancel\n", end);
			int i = 0;
			while (i < 1 || i > end) {
				i = scanner.nextInt();
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
			i = scanner.nextInt();
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
			i = scanner.nextInt();
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
			i = scanner.nextInt();
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
	}

	private void move() {
		System.out.println("Which room would you like to move to?");
		String[] neighbors = this.room.getNeighbors();
		for (int i = 0; i < neighbors.length; i++) {
			System.out.printf("%d. %s\n", i + 1, neighbors[i]);
		}
		int i = 0;
		while (i < 1 || i > neighbors.length + 1) {
			i = scanner.nextInt();
		}

		Room moveRoom = Board.getRoom(neighbors[i - 1]);
		this.setRoom(moveRoom);

		System.out.printf("Moved to %s\n", this.room.getName());
		this.current_state = TurnState.MOVED;
	}

	private void upgrade(Upgrade choice) {
		// perform the chosen upgrade?
		System.out.printf("Upgrading to choice %s\n", choice.toString());
	}

	public boolean takeRole() {
		// Role newRole = new Role("name", "line", 1, true);
		System.out.printf("You're taking a role called: \n");
		boolean result = false;

		return result;
	}

	public boolean takeRole(Role newRole) {
		System.out.printf("You're taking a role called: %s\n", newRole.getName());
		boolean result = false;
		if (!newRole.isTaken()) {
			this.currentRole = newRole;
			result = true;
			this.current_state = TurnState.IN_ROLE;
		} else {
			System.out.printf("Cannot take role %s because its already taken", newRole.getName());
		}

		return result;
	}

	private void completeRole() {
		this.currentRole = null;
	}

	private void act() {
		System.out.println("Act");
	}

	private void rehearse() {
		System.out.println("Rehearse");
	}

	private void payMoney() {
	}

	private void payCredits() {
	}

	private void removeShotCounter() {
	}

	private int rollDice(Dice gameDice) {
		return gameDice.rollDice();
	}

}
