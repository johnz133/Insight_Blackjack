import java.util.*;


public class Player {
	private String name;
	private int money;
	private List<Card> hand;
	private int numAce;
	private int value;
	private int higherValue;
	
	public Player(String name, int money) {
		this.name = name;
		this.money = money;
		hand = new ArrayList<Card>();
		this.value = 0;
		this.higherValue = 0;
	}
	
	//Set methods	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void addCard(Card card) {
		 hand.add(card);
	}
	
	public void clearHand() {
		hand.clear();
		value = 0;
		higherValue = 0;
	}
	
	//Get methods
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
		
	public String readHand() {
		if(hand.size() == 1) {
			return hand.get(0).readCard();
		} 
		else if(hand.size() == 2){
			return hand.get(0).readCard() + " and " + hand.get(1).readCard();
		} 
		else {
			String str = "";
			for(int i = 0; i < hand.size(); i++) {
				str += hand.get(i).readCard() +  ", "; 
			}
			str += "and " + hand.get(hand.size()).readCard();
			return str;
		}
	}
	
	//return the total value(s) of the hand, depending on ace
	public String total() {
		value = 0;
		higherValue = 0;
		int workingAce = countAce();
		if(workingAce > 0){
			for(Card c : hand){
				if(c.getRank().toString() != "Ace") {
					higherValue += c.getValue();
				}
				else {
					higherValue += 11;
				}
			}
			value = higherValue - numAce*10;
			//player bust, recalculate with ace == 1
			while(higherValue > 21 && numAce > 0){
				higherValue -= 10;
				workingAce--;
			}
		}
		else {
			for(Card c : hand){
				value += c.getValue();
			}
		}
		return (workingAce > 0) ? "" + value + "/" + higherValue : "" + value;
	}
	
	public int value() {
		return (higherValue > 0) ? higherValue : value;
	}
	
	//helper methods
	private int countAce() {
		numAce = 0;
		for(Card c : hand) {
			if(c.getRank().toString() == "Ace")
				numAce++;
		}
		return numAce;
	}
	
	public boolean canSplit() {
		return hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue();
	}
}
