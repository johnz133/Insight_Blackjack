import java.io.*;
import java.util.*;

public class Blackjack {
	private static Player player;
	private static BufferedReader br;
	private static Deck deck;
	private static List<Card> dealerHand;
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
	//
	
	public static void main(String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		initialize();
		//Game loop
		while(player.getMoney() > 0) {
			startRound();
			System.out.println("");
		}
		
	}
	
	//Initialize player
	private static void initialize() {
		player = new Player("Player", 100);
		System.out.println("Welcome to Insight Resort and Casino's Blackjack table.");
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
		//set the bet
		int betAmount = 0;
		System.out.println("You're current total is $" + player.getMoney() + ", " + player.getName() +".");
		System.out.println("How much will you bet?");
		try {
			betAmount = Integer.parseInt(br.readLine());
		} catch(IOException ioe) {
			System.out.println("Error trying to read bet amount!");
			System.exit(1);
		}
		player.setMoney(player.getMoney() - betAmount);
		System.out.println("Your total is now $" + player.getMoney() + ".");
		
		//shuffle deck, then deal first cards
		deck.shuffle();
		dealerHand = new ArrayList<Card>();
		dealerHand.add(deck.getNextCard());
		player.addCard(deck.getNextCard());
		player.addCard(deck.getNextCard());
		
		System.out.println("You were dealt " + player.readHand() + ", total value: " + player.total()) ;
	}
	
	
}
