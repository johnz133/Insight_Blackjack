
public class Player {
	private String name;
	private int money;
	private Card[] hand;
	
	public Player(String name, int money) {
		this.name = name;
		this.money = money;
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
	public void addCard() {
		 
	}
	
}
