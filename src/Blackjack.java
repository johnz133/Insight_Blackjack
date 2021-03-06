import java.io.*;
import java.util.*;

public class Blackjack {
	private static Player player;
	private static Player house;
	private static BufferedReader br;
	private static Deck deck;
	private static int betAmount;
	private static Set<Player> splitHands;
	
	//Main game program
	public static void main(String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		boolean playAgain = true;
		initialize();
		//Game loop
		while(player.getMoney() > 0 && playAgain) {
			startRound();
			playerMove(player);
			houseHit();
			//If no split, compare current hand
			if(splitHands.isEmpty()){
				compareHands(player);
			}
			//If split exists, then we compare each hand
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
	
	//Initialize variables, display welcome message
	private static void initialize() {
		player = new Player("Player", 100);
		house = new Player("House", 100);
		System.out.println("Welcome to Insight Resort and Casino's Blackjack table!");
		System.out.println("My name is John, and I'll be your dealer for tonight.");
		System.out.println("What's your name?");
		try { 
			player.setName(br.readLine());
		} catch(IOException ioe) {
			System.out.println("Error trying to read your name!");
			System.exit(1);
		}
		System.out.println("Nice to meet you, " + player.getName() + "!");
		System.out.println("As a gift, enjoy $100 on the house tonight!");
		player.setMoney(100);
		deck = new Deck();
		splitHands = new HashSet<Player>();
	}
	
	//Begin new round. All data from last round is cleared. 
	//Ask for wager, reshuffle the deck, then deal cards.
	private static void startRound() {
		player.clearHand();
		house.clearHand();
		splitHands.clear();
		betAmount = 0;
		System.out.println("You're current total is $" + player.getMoney() + ", " + player.getName() +".");
		System.out.println("Let's start a new round. How much will you bet?");
		try {
			betAmount = Integer.parseInt(br.readLine());
			while(betAmount < 1){
				System.out.println("You must bet at least $1.");
				betAmount = Integer.parseInt(br.readLine());
			}
		} catch(Exception e) {
			System.out.println("Error trying to read bet amount!");
			System.exit(1);
		}
		player.setMoney(player.getMoney() - betAmount);
		System.out.println("Ok. Your total is now $" + player.getMoney() + ".");
		
		//shuffle deck, then deal first cards
		deck.shuffle();
		player.addCard(deck.getNextCard());
		System.out.println("You were dealt [" + player.readHand() + "]...");
		pause(1.5);
		house.addCard(deck.getNextCard());
		System.out.println("The house was dealt [" + house.readHand() + "], value: [" + house.total() + "]...");
		pause(1.5);
		player.addCard(deck.getNextCard());		
		System.out.println("You were dealt [" + player.readHand() + "], total value: [" + player.total() + "]...");
	}
	
	//Player choice loop.
	//1. Stops when player stands
	//2. Deals player a new card, check if busted
	//3. Split equal value cards.
	//4. Double Down
	
	//Note: playerAlive checks to see if player is alive, or able, to continue 
	//choosing the next move.
	private static Player playerMove(Player hand) {
		displayOptions(hand);
		if(hand.canSplit())
			System.out.println("3. Split");
		int playerChoice = -1;
		boolean playerAlive = true;
		while(!(playerChoice == 1 || playerChoice == 0) && playerAlive){
			try{
				playerChoice = Integer.parseInt(br.readLine());
				switch(playerChoice){
					case(0):
						playerDoubleDown(hand);
						playerAlive = false;
					break;
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
			} catch(IOException ioe) {
				System.out.println("Error reading choice option!");
				System.exit(1);
			}
		}
		return hand;
	}
	
	//Display the standard options.
	private static void displayOptions(Player hand) {
		System.out.println("Choose move (enter option number): ");
		if(hand.canDoubleDown())
			System.out.println("0. Double Down");
		System.out.println("1. Stand");
		System.out.println("2. Hit");
	}
	
	private static void playerDoubleDown(Player hand){
		System.out.println("You have chosen to double down at [" + hand.value() + "].");
		player.setMoney(player.getMoney() - betAmount);
		System.out.println("An additional $" + betAmount +" was placed. Your current money: $" + player.getMoney());
		betAmount *= 2;
		hand.addCard(deck.getNextCard());
		pause(1.5);
		System.out.println("You were dealt [" + hand.getLastCard().readCard() + "].");
		System.out.println("Your hand now has [" + hand.readHand() + "], total value: [" + hand.value() + "].");
	}
	
	//Prints out player's final value for this hand
	private static void playerStand(Player hand) {
		System.out.println("You have chosen to stand at [" + hand.value() + "].");
	}
	
	//Compare hand to dealer's card values and update money
	private static void compareHands(Player hand) {
		System.out.println("Comparing [" + hand.readHand() + "], value: [" + hand.value() +"]...");
		if(hand.value() > 21){
			System.out.println("This hand busted..");
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
	
	//Deals player a new card
	private static boolean playerHit(Player hand) {
		System.out.println("You have chosen to hit...");
		pause(1.5);
		hand.addCard(deck.getNextCard());
		System.out.println("You were dealt [" + hand.getLastCard().readCard() + "].");
		if(hand.value() > 21){
			System.out.println("Bust! No more moves available for hand.");
			return false;
		}
		else{
			System.out.println("You now have [" + hand.readHand() + "], total value: [" + hand.value() + "].");
			displayOptions(hand);
			return true;
		}
	}
	
	//Recursive split method. Split the two cards into two hands.
	//After playing each hand, we add it to the set of hands to be compared later.
	private static void playerSplit(Player hand) {
		System.out.println("You've chosen to split your [" + hand.readHand() + "]...");
		//play first hand
		Player split = new Player(hand.getCard(0).readCard(), 0);
		split.addCard(hand.removeCard(0));
		split.addCard(deck.getNextCard());
		pause(1.5);
		System.out.println("You were dealt [" + split.getLastCard().readCard() + "].");
		System.out.println("You're current hand now has [" + split.readHand() + "], value: [" + split.value() + "], with a bet amount of $" + betAmount + ".");
		split = playerMove(split);
		//play other hand
		hand.addCard(deck.getNextCard());
		pause(1.5);
		System.out.println("You were dealt [" + hand.getLastCard().readCard() + "].");
		System.out.println("You're next hand has [" + hand.readHand() + "], value: [" + hand.value() + "],  with a bet amount of $" + betAmount + "." );
		hand.setName(hand.getCard(0).readCard());
		hand = playerMove(hand);
		//add finished hands to be compared later. 
		splitHands.add(split);
		splitHands.add(hand);
	}
	
	//AI for the house hit. Hit until hand value is greater than 17.
	private static void houseHit() {
		while(house.value() < 17){
			System.out.println("The house hits...");
			pause(1.5);
			house.addCard(deck.getNextCard());
			System.out.println("The house drew [" + house.getLastCard().readCard() + "].");
			System.out.println("The house now has [" + house.readHand() + "], total value: [" + house.total() + "].");
		}
	}
	
	//Ask the player to play again. 
	private static boolean askPlayAgain() {
		while(true){
			System.out.println("Would you like to play again, " + player.getName() + "? Y/N");
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
	
	//pause the thread to add a little suspense. 
	private static void pause(double seconds){
		try{
			Thread.sleep((int)(1000*seconds));
		} catch(InterruptedException ie){
			System.out.println("Thread sleep interrupted!");
		}
	}
}
