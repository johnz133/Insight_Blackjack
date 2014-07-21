
public enum Suits {
	Clubs(1),
	Diamonds(2),
	Hearts(3),
	Spades(4);
	
	private int value;
	
	//constructor
	private Suits(int value){
		this.value = value;
	}
	
	//return integer value of suit
	//arbitrary in blackjack. Used to calculate hashcode
	public int getValue(){
		return value;
	}
}
