import java.io.*;

public class Blackjack {
	private static Player player;
	private static Player house;
	private static BufferedReader br;
	private static Deck deck;
	private static int betAmount;
	/*
	 * Insight Data Engineering Fellows Program - Coding Challenge
	We'd like you to implement a text-based Blackjack (http://en.wikipedia.org/wiki/Blackjack) 
	program in one of the following programming languages: Java, Clojure, Scala, C, C++, 
	Python or Ruby. There should be one player and one dealer. The dealer should hit 
	until his hand value is 17 or greater. You should implement the basic actions of 
	hitting and standing. Implementing the more advanced actions such as splitting is 
	optional. The player should start with 100 chips and must bet at least 1 chip each 
	hand.
	Any additional game play features are optional, but welcome.
	 */
	//Require:
	//Stand/hit
	//House AI
	//Start with 100
	//At least 1 per hand
	//Optional:
	//Split
	//double down
	
	public static void main(String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		boolean playAgain = true;
		initialize();
		//Game loop
		while(player.getMoney() > 0 && playAgain) {
			startRound();
			playerMove();
			playAgain = askPlayAgain();
		}
		
	}
	
	//Initialize player and house
	private static void initialize() {
		player = new Player("Player", 100);
		house = new Player("House", 100);
		System.out.println("Welcome to Insight Resort and Casino's Blackjack table! ");
		System.out.println("What is your name?");
		try { 
			player.setName(br.readLine());
		} catch(IOException ioe) {
			System.out.println("Error trying to read your name!");
			System.exit(1);
		}
		player.setMoney(100);
		deck = new Deck();
	}
	
	private static void startRound() {
		player.clearHand();
		house.clearHand();
		//set the bet
		betAmount = 0;
		System.out.println("You're current total is $" + player.getMoney() + ", " + player.getName() +".");
		System.out.println("How much will you bet?");
		try {
			betAmount = Integer.parseInt(br.readLine());
		} catch(Exception e) {
			System.out.println("Error trying to read bet amount!");
			System.exit(1);
		}
		player.setMoney(player.getMoney() - betAmount);
		System.out.println("Your total is now $" + player.getMoney() + ".");
		
		//shuffle deck, then deal first cards
		deck.shuffle();
		player.addCard(deck.getNextCard());
		System.out.println("You were dealt [" + player.readHand() + "].");
		house.addCard(deck.getNextCard());
		System.out.println("The house was dealt [" + house.readHand() + "], value: [" + house.total() + "].");
		player.addCard(deck.getNextCard());		
		System.out.println("You were dealt [" + player.readHand() + "], total value: [" + player.total() + "].");
		System.out.println();
	}
	
	private static void playerMove() {
		displayOptions();
		if(player.canSplit()){
			//Not implemented yet
			//System.out.println("3. Split");
		}
		int playerChoice = 0;
		boolean playerAlive = true;
		while(!(playerChoice == 1) && playerAlive){
			try{
				playerChoice = Integer.parseInt(br.readLine());
				switch(playerChoice){
					case(1):
						playerStand();
					break;
					case(2):
						playerAlive = playerHit();
					break;
					default:
						System.out.println("Not an option! Please enter 1 or 2.");
					break;
				}
			} catch(IOException ioe) {
				System.out.println("Error reading choice option!");
				System.exit(1);
			}
		}
		
	}
	
	private static void displayOptions() {
		System.out.println("Choose move (enter option number): ");
		System.out.println("1. Stand");
		System.out.println("2. Hit");
	}
	private static void playerStand() {
		System.out.println("You have chosen to stand at [" + player.value() + "].");
		houseHit();
		if(house.value() <= 21){
			System.out.println("The house stands at [" + house.value() + "].");
			if(player.value() < house.value()) {
				System.out.println("You lose.");
			}
			else if(player.value() > house.value()){
				System.out.println("You win!");
				player.setMoney(player.getMoney() + betAmount*2);
			}
			else {
				System.out.println("Draw!");
				player.setMoney(player.getMoney() + betAmount);
			}
		}
		else {
			System.out.println("The house busts! You win!");
			player.setMoney(player.getMoney() + betAmount*2);
		}
		System.out.println("You now have $" + player.getMoney() + ".");
	}
	
	private static boolean playerHit() {
		System.out.println("You have chosen to hit.");
		player.addCard(deck.getNextCard());
		System.out.println("You now have [" + player.readHand() + "], total value: [" + player.total() + "].");
		if(player.value() > 21){
			System.out.println("Bust! You lose!");
			System.out.println("You now have $" + player.getMoney() + ".");
			return false;
		}
		else{
			displayOptions();
			return true;
		}
	}
	
	private static void houseHit() {
		while(house.value() < 17){
			System.out.println("The house hits...");
			house.addCard(deck.getNextCard());
			System.out.println("The house has [" + house.readHand() + "], total value: [" + house.total() + "].");
		}
	}
	
	private static boolean askPlayAgain() {
		while(true){
			System.out.println("Play again? Y/N");
			String input = "";
			try {
				input = br.readLine();
			}
			catch (IOException ioe) {
				System.out.println("Error reading Y/N input!");
				System.exit(1);
			}
			if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
				return true;
			}
			else if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")){
				return false;
			}
			else {
				System.out.println("Please enter yes or no.");
			}
		}
	}
}
