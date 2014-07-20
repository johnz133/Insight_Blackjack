import java.io.*;
import java.util.StringTokenizer;

public class Blackjack {
	private static Player player;
	private static Player house;
	private static BufferedReader br;
	private static Deck deck;
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
		initialize();
		//Game loop
		while(player.getMoney() > 0) {
			startRound();
			playerMove();
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
		//System.out.println("Hello, "+ G)
		deck = new Deck();
	}
	
	private static void startRound() {
		player.clearHand();
		house.clearHand();
		//set the bet
		int betAmount = 0;
		System.out.println("You're current total is $" + player.getMoney() + ", " + player.getName() +".");
		System.out.println("How much will you bet?");
		try {
			betAmount = Integer.parseInt(br.readLine());
		} catch(Exception ioe) {
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
		System.out.println("The dealer was dealth [" + house.readHand() + "], value: [" + house.total() + "].");
		player.addCard(deck.getNextCard());		
		System.out.println("You were dealt [" + player.readHand() + "], total value: [" + player.total() + "].");
		System.out.println();
	}
	
	private static void playerMove() {
		System.out.println("Choose move (enter option number): ");
		System.out.println("1. Stand");
		System.out.println("2. Hit");
		if(player.canSplit()){
			//Not implemented yet
			//System.out.println("3. Split");
		}
		int playerChoice = 0;
		while(!(playerChoice == 1 || playerChoice == 2)){
			try{
				playerChoice = Integer.parseInt(br.readLine());
				switch(playerChoice){
					case(1):
						playerStand();
					break;
					case(2):
						playerHit();
					break;
					default:
						System.out.println("Not an option! Please enter 1 or 2.");
					break;
				}
			} catch(Exception ioe) {
				System.out.println("Error reading choice option!");
				System.exit(1);
			}
		}
		
	}
	
	private static void playerStand() {
		System.out.println("You have chosen to stand.");
		
	}
	
	private static void playerHit() {
		System.out.println("You have chosen to hit.");
		
	}
	
	private static void dealerHit() {
		//dealer must hit until 17+
		//if dealer busts, you win
		//but if dealer busts, and has an Ace, then he continues with ace = 1
		StringTokenizer total = new StringTokenizer(house.total(),"/");
		int value = Integer.parseInt(total.nextToken());
		int aceValue = 0;
		if(total.hasMoreTokens())
			aceValue = Integer.parseInt(total.nextToken());
		if(aceValue != 0){
			while(aceValue > 17)
			{
				System.out.println("The dealer hits...");
				house.addCard(deck.getNextCard());
				System.out.println("The dealer has [" + house.readHand() + "], total value: [" + house.total() + "].");

			}
			if(aceValue > 21){
				
			}
		}
	}
	
}
