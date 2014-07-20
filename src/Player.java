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
	
	//Get methods
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
	
	//Game methods
	public void addCard(Card card) {
		 hand.add(card);
	}
	
}
