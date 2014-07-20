
public class Card {
	private Suits suit;
	private Ranks rank;
	
	//Constructor for a card
	public Card(Suits suit, Ranks rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	//Get Method
	public Ranks getRank() {
		return rank;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	//return english reading of card
	public String readCard() {
		return rank + " of " + suit;
	}
	
	//return integer value of card
	public int getValue() {
		return rank.getValue();
	}
}
