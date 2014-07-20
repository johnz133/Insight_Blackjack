import java.util.*;


public class Player {
	private String name;
	private int money;
	private List<Card> hand;
	
	public Player(String name, int money) {
		this.name = name;
		this.money = money;
		hand = new ArrayList<Card>();
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
	}
	
	//Get methods
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
		
	public String readHand() {
		String str = "";
		if(hand.size()==2){
			str += hand.get(0).readCard() + " and " + hand.get(1).readCard();
		}
		else {
			for(int i = 0; i < hand.size() - 1; i++) {
				str += hand.get(i).readCard() +  ", "; 
			}
			str += "and " + hand.get(-1).readCard();
		}
		return str;
	}
	
	//return the total value(s) of the hand, depending on ace
	public String total() {
		int tot = 0;
		int totWithAce = 0;
		int numAce = 0;
		if(hasAce()){
			for(Card c : hand){
				if(c.getRank().toString() != "Ace") {
					totWithAce += c.getValue();
				}
				else {
					totWithAce += 11;
					numAce++;
				}
			}
			tot = totWithAce - numAce*10;
			if(totWithAce > 21)
				numAce = 0;
		}
		else {
			for(Card c : hand){
				tot += c.getValue();
			}
		}
		return (numAce > 0) ? "" + tot + "/" +totWithAce : "" + tot;
	}
	
	private boolean hasAce() {
		for(Card c : hand){
			if(c.getRank().toString() == "Ace")
				return true;
		}
		return false;
	}
	
	public boolean canSplit() {
		return hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue();
	}
}
