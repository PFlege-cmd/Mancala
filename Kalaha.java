package nl.sogyo.mancala.domain;

public class Kalaha extends BoardObject {
	
	private int position;
	private int Stones;	
	private Player Owner;
	private Bowl neighbour;
	
	// This constructor is called by a 'Bowl' constructor.

	public Kalaha(Player Owner) {
		this.Owner = Owner;
		this.Owner.set_Kalaha(this);
		if ((this.Owner.get_name()).contentEquals("1")) {
			this.neighbour = new Bowl(Owner.get_Opponent());
			this.neighbour.getOwner().set_Opponent(this.Owner); // Sets opponent of player 2 to player 1!
			
		} else if ((this.Owner.get_name()).contentEquals("2")) {
			this.Owner = Owner;
			this.Owner.set_Kalaha(this);
			this.neighbour = this.getOwner().get_Opponent().get_myfirstBowl(); // Closes the circle, now the first bowl is connected 
																			   // to second player Kalaha!			
		}
	}
	
	
	
	public int get_Stones() {
		return this.Stones; 
	}
	
	public void set_Stones(int stones) {
		this.Stones = stones;
	}
	
	public int get_Position() {
		
		return position;
		
	}
	
	public String getOwnerName() {
		
		return this.Owner.get_name();
	}
	
	public Player getOwner() {
		
		return this.Owner;
	}
	
	public Bowl getNeighbour() {
		return this.neighbour;
	}
	
	public int add_and_pass(int stones_received, Player Current_Player) {
		int last_bowl = this.get_Position(); // Will return a zero - unique identifier for Kalaha!
		Bowl Nachbar = this.getNeighbour();
		
		if (stones_received > 1) {
				
			last_bowl = amidst_add_and_pass(stones_received, Current_Player);
			
			} else {
			
			last_bowl = end_add_and_pass(stones_received, Current_Player);
		}
		return last_bowl;
	}
	
	private int amidst_add_and_pass(int stones_received, Player Current_Player) {
		int last_bowl = this.get_Position(); // Will return a zero - unique identifier for Kalaha!
		Bowl Nachbar = this.getNeighbour();
		
		if (Current_Player == this.getOwner()) {
			this.Stones = ++Stones;
			stones_received--;
			last_bowl = Nachbar.add_and_pass(stones_received, Current_Player, Nachbar.getNeighbour(), null);
			
		} else {
			last_bowl = Nachbar.add_and_pass(stones_received, Current_Player, Nachbar.getNeighbour(), null);	
		}
		
		return last_bowl;
	}
	
	private int end_add_and_pass(int stones_received, Player Current_Player) {
		
		int last_bowl = this.get_Position(); // Will return a zero - unique identifier for Kalaha!
		Bowl Nachbar = this.getNeighbour();
		
		if (Current_Player == this.getOwner()) {
			this.Stones = ++Stones;
			stones_received--;
		} else {
			last_bowl = Nachbar.add_and_pass(stones_received, Current_Player, Nachbar.getNeighbour(), null);	
		}
		return last_bowl;
		
	}
	
	public void add_Stones(int stones_to_add) {
		
		this.Stones = Stones + stones_to_add;
		
	}
		

}
