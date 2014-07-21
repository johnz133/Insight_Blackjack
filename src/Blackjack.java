import java.io.*;
import java.util.*;

public class Blackjack {
	private static Player player;
	private static Player house;
	private static BufferedReader br;
	private static Deck deck;
	private static int betAmount;
	private static Set<Player> splitHands;
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
			playerMove(player);
			houseHit();
			if(splitHands.isEmpty()){
				compareHands(player);
			}
			else{
				Iterator<Player> iter = splitHands.iterator();
				while(iter.hasNext()) {
					compareHands(iter.next());
				}
			}
			playAgain = askPlayAgain();
		}
		System.out.println("Thanks for playing, " + player.getName() + "!");
		System.out.println("Come back again!");
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
		splitHands = new HashSet<Player>();
	}
	
	private static void startRound() {
		player.clearHand();
		house.clearHand();
		splitHands.clear();
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
	
	private static Player playerMove(Player hand) {
		displayOptions();
		if(hand.canSplit()){
			//Not implemented yet
			System.out.println("3. Split");
		}
		int playerChoice = 0;
		boolean playerAlive = true;
		while(!(playerChoice == 1) && playerAlive){
			try{
				playerChoice = Integer.parseInt(br.readLine());
				switch(playerChoice){
					case(1):
						playerStand(hand);
						playerAlive = false;
					break;
					case(2):
						playerAlive = playerHit(hand);
					break;
					case(3):
						if(!hand.canSplit())
							System.out.println("Please enter a valid option.");
						else{
							playerSplit(hand);
							playerAlive = false;
						}
					break;
					default:
					break;
				}
			} catch(IOException e) {
				System.out.println("Error reading choice option!");
				System.exit(1);
			}
		}
		return hand;
	}
	
	private static void displayOptions() {
		System.out.println("Choose move (enter option number): ");
		System.out.println("1. Stand");
		System.out.println("2. Hit");
	}
	
	private static void playerStand(Player hand) {
		System.out.println("You have chosen to stand at [" + hand.value() + "].");
		/*if(hand.getName() == player.getName()) {
			System.out.println("Comparing your hand with [" + hand.readHand() + "]...");
			houseHit();
			compareHands(hand);
		}*/
	}
	
	private static void compareHands(Player hand) {
		System.out.println("Comparing [" + hand.readHand() + "], value: [" + hand.value() +"]...");
		if(hand.value() > 21){
			System.out.println("This hand busted already..");
		}
		if(house.value() <= 21 && hand.value() <= 21){
			System.out.println("The house stands at [" + house.value() + "].");
			if(hand.value() < house.value()) {
				System.out.println("You lose.");
			}
			else if(hand.value() > house.value()){
				System.out.println("You win $" + betAmount*2 + "!");
				player.setMoney(player.getMoney() + betAmount*2);
			}
			else {
				System.out.println("Draw! $" + betAmount + " returned.");
				player.setMoney(player.getMoney() + betAmount);
			}
		}
		else if(hand.value() <= 21 ) {
			System.out.println("The house busts! You win $" + betAmount*2 + "!");
			player.setMoney(player.getMoney() + betAmount*2);
		}
		System.out.println("You now have $" + player.getMoney() + ".");
	}
	
	private static boolean playerHit(Player hand) {
		System.out.println("You have chosen to hit.");
		hand.addCard(deck.getNextCard());
		System.out.println("You now have [" + hand.readHand() + "], total value: [" + hand.value() + "].");
		if(hand.value() > 21){
			System.out.println("Bust! You lose!");
			System.out.println("You now have $" + player.getMoney() + ".");
			return false;
		}
		else{
			displayOptions();
			return true;
		}
	}
	
	private static void playerSplit(Player hand) {
		//play first hand
		//play second hand
		System.out.println("You've chosen to split your [" + hand.readHand() + "].");
		Player split = new Player(hand.getCard(0).readCard(), 0);
		split.addCard(hand.removeCard(0));
		split.addCard(deck.getNextCard());
		System.out.println("You're current hand now has [" + split.readHand() + "], value: [" + split.value() + "], with a bet amount of $" + betAmount + ".");
		split = playerMove(split);
		
		hand.addCard(deck.getNextCard());
		System.out.println("You're next hand has [" + hand.readHand() + "], value: [" + hand.value() + "],  with a bet amount of $" + betAmount + "." );
		hand.setName(hand.getCard(0).readCard());
		hand = playerMove(hand);
		
		splitHands.add(split);
		splitHands.add(hand);
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
			if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
				return true;
			}
			else if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
				return false;
			}
			else {
				System.out.println("Please enter yes or no.");
			}
		}
	}
}
