import java.util.*;

public class Deck {
	private List<Card> cards;
	private Iterator<Card> iter;
	
	//Initialize the deck of 52 cards, not shuffled
	public Deck() {
		cards = new ArrayList<Card>();
		for(Suits s : Suits.values()) {
			for(Ranks r : Ranks.values()) {
				cards.add(new Card(s,r));
			}
		}
	}
	
	//return the next card in the iterator
	//if no more cards, reshuffle deck
	public Card getNextCard() {
		if(!iter.hasNext()){
			Collections.shuffle(cards);
			iter = cards.iterator();
		}
		return iter.next();
	}
	
	//shuffle deck
	public void shuffle() {
		Collections.shuffle(cards);
		iter = cards.iterator();
	}
}
