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
}
