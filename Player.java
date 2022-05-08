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

	public Player(String name, int numPlayers, int newID, Room room) {
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

	public boolean takeTurn() {
		int i = 0;
		System.out.printf("Player %d turn\n", this.id);
		if (this.currentRole != null) {
			// player is in a role
			// choices are act or rehearse
			System.out.println("Make a choice:");
			System.out.println("1. act");
			System.out.println("2. rehearse");
			System.out.println("3. end turn");

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

		} else if (this.room.equals("Casting Office")) {
			// choices are upgrade then move or just move
			System.out.println("Make a choice:");
			System.out.println("1. upgrade");
			System.out.println("2. move");
			System.out.println("3. endTurn");

			while (i != 1 && i != 2 && i != 3) {
				i = scanner.nextInt();
			}

			switch (i) {
				case 1:
					this.upgrade();
					System.out.println("Make a choice:");
					System.out.println("1. move");
					System.out.println("2. endTurn");
					i = 0;
					while (i != 1 || i != 2) {
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
			// not in a role and not in casting office so choices are move or take a role
			System.out.println("Make a choice:");
			System.out.println("1. move");
			System.out.println("2. take a role");
			System.out.println("3. endTurn");
			while (i != 1 && i != 2 && i != 3) {
				i = scanner.nextInt();
			}

			switch (i) {
				case 1:
					this.move();
					System.out.println("Make a choice:");
					System.out.println("1. take a role");
					System.out.println("2. endTurn");
					i = 0;
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
						default:
					}

					break;
				case 2:
					this.takeRole();
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
		System.out.println("Which room would you like to move to?");
		Room[] neighbors = this.room.getNeighbors();
		for (int i = 0; i < neighbors.length; i++) {
			System.out.printf("%d. %s\n", i + 1, neighbors[i].getName());
		}
		int i = 0;
		while (i < 1 || i > neighbors.length) {
			i = scanner.nextInt();
		}

		this.setRoom(neighbors[i - 1]);

		System.out.printf("Moved to %s\n", this.room.getName());
	}

	private void upgrade() {
		System.out.println("Upgradde with two d's");
	}

	public boolean takeRole() {
		//Role newRole = new Role("name", "line", 1, true);
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
