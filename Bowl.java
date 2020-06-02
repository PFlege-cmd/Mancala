package nl.sogyo.mancala.domain;

public class Bowl extends BoardObject {
	
	private int position;
	private int Stones = 4;
	private Player Owner;
	private Bowl neighbour;
	private Kalaha Kalaha_neighbour;

	public Bowl() {
		this.position = 1;
		this.Owner = new Player();
		this.Owner.set_myfirstBowl(this);	
		this.neighbour = new Bowl (this.position + 1, this.getOwner());
	}
	
	public Bowl(Player Owner) {
		this.position = 7;
		this.Owner = Owner;
		this.Owner.set_myfirstBowl(this);
		int help_position = this.position + 1;	
		this.neighbour = new Bowl(help_position, this.Owner);
	}
	
	public Bowl(int position, Player Owner) {
		this.position = position;

		this.Owner = Owner;
		position++;
		if (position < 7 || position > 7 && position < 13 ) { // Creates six subsequent Bowls!
			
			this.neighbour = new Bowl (position, this.Owner);
		} else if (position == 7 || position == 13) {
			this.Kalaha_neighbour = new Kalaha(this.Owner);
			this.getOwner().set_Kalaha(this.Kalaha_neighbour);// = this.Kalaha_neighbour;
			
			
		}
		
	}
	
	public int get_Stones() { // So defining it here is necessary if I want to get the updated value of stones...
		return Stones;
	}
	
	public void set_Stones(int new_stones) {
		
		this.Stones = new_stones;
	}
	
	// Below function not essential for game, rather for testing different scenarios, i.e. setting up a specific combo of stones.
	
	public boolean setUpGame(int[] Stones_at_start) {
		
		int how_many_fields_still_to_fill = Stones_at_start.length;
		Bowl Starting_point = this.getOwner().get_myfirstBowl(); // Sets Bowl to first of owner

		if (!(this.getOwner().get_name().contentEquals("1"))) {
			Starting_point = this.getOwner().get_Opponent().get_myfirstBowl(); // Makes sure I start from Bowl no. 1!
		}
		
		while(how_many_fields_still_to_fill != 0) {
			Starting_point.set_Stones(Stones_at_start[12 - how_many_fields_still_to_fill]);
			if (Starting_point.getNeighbour() == null) {
				Kalaha HelpKalaha = Starting_point.getKalahaNeighbour(); // Makes sure I 'jump' over the Kalaha!
				Starting_point = HelpKalaha.getNeighbour();
			} else {
				Starting_point = Starting_point.getNeighbour();
			}
			how_many_fields_still_to_fill--;
		}
		
		return true;
		
		
	}
	
	public int get_Position() {
		
		return position;
		
	}
	
	public String getOwnerName() {
		return this.Owner.get_name(); // Dummy method to check whether initialization worked
	}
	
	
	public Player getOwner() {
			
		return this.Owner;
	}
	
	public void setOwner(Player Owner) {
		this.Owner = Owner; // Useful when setting new owner after Kalaha!
	}
	
	public Bowl getNeighbour() {
		return this.neighbour;
	}
	
	public Kalaha getKalahaNeighbour() {
		return this.Kalaha_neighbour;
	}
	
	public Bowl go_N_steps(int distance) {
		Bowl Current_Bowl = this;
		while (distance != 0) { // Reduces distance till it is zero - then I am at final position!
			distance--;
			Current_Bowl = Current_Bowl.getNeighbour();
		}
		return Current_Bowl;
			
		
	}
	
	public int add_and_pass(int stones_received, Player Current_Player, Bowl Nachbar, Kalaha Kalaha_neighbour) {
		
		int last_bowl = 0; // Position of last bowl, after recursive function!
		
		if (stones_received == 0) { // Base case, If I am the first bowl			
			last_bowl = start_and_and_pass(stones_received, Current_Player, Nachbar, Kalaha_neighbour);			
		} else if (stones_received > 1){		
			last_bowl = amidst_add_and_pass(stones_received, Current_Player, Nachbar, Kalaha_neighbour);
		} else if ( stones_received == 1) {
			last_bowl = end_add_and_pass(stones_received, Current_Player, Nachbar, Kalaha_neighbour);
		}		
		return last_bowl;
	}
	
	private int start_and_and_pass(int stones_received, Player Current_Player, Bowl Nachbar, Kalaha Kalaha_neighbour) {
		int last_bowl;
		stones_received = this.get_Stones();
		this.Stones = 0;
		last_bowl = (Nachbar != null)?Nachbar.add_and_pass(stones_received, Current_Player, Nachbar.getNeighbour(), Nachbar.getKalahaNeighbour()):this.getKalahaNeighbour().add_and_pass(stones_received, Current_Player);
		return last_bowl;
		}
	
	private int amidst_add_and_pass(int stones_received, Player Current_Player, Bowl Nachbar, Kalaha Kalaha_neighbour) {
		int last_bowl;
		stones_received--;
		this.Stones++;
		last_bowl = (Nachbar != null)?(Nachbar.add_and_pass(stones_received--, Current_Player, Nachbar.getNeighbour(), Nachbar.getKalahaNeighbour())):this.getKalahaNeighbour().add_and_pass(stones_received, Current_Player);
		return last_bowl;
	}
	
	private int end_add_and_pass(int stones_received, Player Current_Player, Bowl Nachbar, Kalaha Kalaha_neighbour) {
		int last_bowl;
		this.Stones++;
		last_bowl = this.get_Position();
		return last_bowl;	
	}
	
	
	public Bowl emptyOpposite(int last_bowl, Player Opponent) { // Function which brings me to opposite bowl!		
		
		// Must be used after 'was_my_last_bowl_empty' function of Player!
		
		Bowl Bowl_to_empty = Opponent.get_myfirstBowl();
			
		Bowl_to_empty = Bowl_to_empty.go_N_steps(13 - last_bowl - Bowl_to_empty.get_Position());
				
		return Bowl_to_empty;
			
		
	}

	public boolean am_I_empty() {
		
		boolean empty_bowl = (this.Stones == 0)?true:false;
		return empty_bowl;
	}
	
	// Function to print state of game, not necessary for flow!
	
	public void print_stones_in_bowls() {
		
		Bowl Target_bowl = this;
		int[] Printarray = new int[12];
		Printarray[0] = Target_bowl.get_Stones();
		while (Target_bowl.get_Position() < 12) {
			
			if (Target_bowl.getNeighbour() == null) {
				Kalaha Help_kalaha = Target_bowl.getKalahaNeighbour();
				Target_bowl = Help_kalaha.getNeighbour();
				Printarray[Target_bowl.get_Position() - 1] = Target_bowl.get_Stones();

			} else {
				Target_bowl = Target_bowl.getNeighbour();
				Printarray[Target_bowl.get_Position() - 1] = Target_bowl.get_Stones();
			}
		}
		System.out.print("K1:" +this.getOwner().get_Kalaha().get_Stones() + ":");
		System.out.print("|");
		for (int i = 0; i < Printarray.length; i++) {
			
			System.out.print(Printarray[i] + "|");
			
		}
	Player dummy = this.getOwner().get_Opponent();
	System.out.print("K2:" + dummy.get_Kalaha().get_Stones() + ":");
	System.out.println();

	}
	

}
