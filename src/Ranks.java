
public enum Ranks {
	Ace(1),
	Two(2),
	Three(3),
	Four(4),
	Five(5),
	Six(6),
	Seven(7),
	Eight(8),
	Nine(9),
	Ten(10),
	Jack(10),
	Queen(10),
	King(10);
	
	private int value;
	
	//constructor
	private Ranks(int value){
		this.value = value;
	}
	
	//return the integer value of the rank
	public int getValue(){
		return value;
	}
}
