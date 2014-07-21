
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
	
	//return the integer value of the rank
	public int getValue(){
		return value;
	}
}
