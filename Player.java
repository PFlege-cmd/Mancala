package nl.sogyo.mancala.domain;

public class Player {
	
	private String name = "";
	private Player myOpponent;
	private Kalaha myKalaha;
	private Bowl myfirstBowl;
	boolean did_I_go_already = false;

	
	public Player() {
		this.name = "1";
		myOpponent = new Player("2");
		myOpponent.myOpponent = this; // Quite circular, sets me as the opponent of my opponent!
	}
	
	public Player(String name) {
		this.name = name;

	}
	
	
	public String get_name() {
		return this.name;
	}
	
	public Bowl get_myfirstBowl() {
		return this.myfirstBowl;
	}
	
	public void set_myfirstBowl(Bowl myfirstBowl) {
		this.myfirstBowl = myfirstBowl;
	}
	
	public Player get_Opponent() {
		return this.myOpponent;
	}
	
	public void set_Opponent(Player Opponent) {
		
		this.myOpponent = Opponent;
	}
	
	public Kalaha get_Kalaha() {
		return this.myKalaha;
	}
	
	public void set_Kalaha(Kalaha myKalaha) {
		this.myKalaha = myKalaha;
	}
	
	public boolean was_my_last_bowl_empty(int last_bowl) {
		
		Bowl Bowl_to_check = myfirstBowl;
		boolean is_last_bowl_from_me = (last_bowl <= (this.myfirstBowl.get_Position() + 5) && last_bowl >= this.myfirstBowl.get_Position());
		
		while (Bowl_to_check.get_Position() != last_bowl) {
			if (Bowl_to_check.getNeighbour() == null) {
				Kalaha HelpKalaha = Bowl_to_check.getKalahaNeighbour(); // Makes sure I 'jump' over the Kalaha!
				Bowl_to_check = HelpKalaha.getNeighbour();
			} else {
				Bowl_to_check = Bowl_to_check.getNeighbour();
			}
		}
		
		boolean is_empty = (Bowl_to_check.get_Stones() == 1)?true:false;
		boolean is_empty_and_from_me = ((is_empty && is_last_bowl_from_me) == true)?true:false;
		return is_empty_and_from_me;
	}
	
	public void add_opposites_to_my_Kalaha(int last_bowl) {
				
		boolean can_I_add_stones = this.was_my_last_bowl_empty(last_bowl);
		Bowl Bowl_to_fill_my_Kalaha_from = null; // Default a null-reference, since it may be that I cannot add from any bowl!
		
		if (can_I_add_stones == true) {
			
			Bowl_to_fill_my_Kalaha_from = myfirstBowl.emptyOpposite(last_bowl, this.myOpponent);
			if (Bowl_to_fill_my_Kalaha_from.get_Stones() != 0) { // If opposite not empty, will not be added!
				int stones_to_add = Bowl_to_fill_my_Kalaha_from.get_Stones() + 1;
				Bowl_to_fill_my_Kalaha_from.set_Stones(0); 
			
				Bowl_to_fill_my_Kalaha_from = (this.go_to_bowl_k(last_bowl)); // Goes back to original bowl and empties it!
				Bowl_to_fill_my_Kalaha_from.set_Stones(0);
			
				myKalaha.add_Stones(stones_to_add);
			}
		}
		
		
	}
	
	public Bowl go_to_bowl_k(int position) { // Function to be used to input starting bowl!
		
		Bowl Target_bowl = this.get_myfirstBowl();
		while (Target_bowl.get_Position() != position) {
			if (Target_bowl.getNeighbour() == null) {
				Kalaha Help_kalaha = Target_bowl.getKalahaNeighbour();
				Target_bowl = Help_kalaha.getNeighbour();
			} else {
				Target_bowl = Target_bowl.getNeighbour();
			}
		}
		
		return Target_bowl;
		
	}
	
	public Player make_turn_and_change_player(int position_of_start_bowl) {
		
		Bowl Start_bowl = this.go_to_bowl_k(position_of_start_bowl);
		
		int last_bowl;
				
		// Checks if stones are empty OR the first bowl from which I start is not one of mine!
		
		if (Start_bowl.get_Stones() != 0 && (Start_bowl.get_Position() <= (this.myfirstBowl.get_Position() + 5) && Start_bowl.get_Position() >= this.myfirstBowl.get_Position())) {
			
			last_bowl = Start_bowl.add_and_pass(0, this, Start_bowl.getNeighbour(), Start_bowl.getKalahaNeighbour());
		} else {
			System.out.println("Choose a proper bowl, one which is not empty and on your side.");
			return this; // If I accidently chose a wrong bowl, makes me go again
		}
		
		Player Next_turn_player = this.change_turn(last_bowl);
		return Next_turn_player;
		
	}
	
	public Player change_turn(int last_bowl) {
		
		if (last_bowl > 0) {
			this.add_opposites_to_my_Kalaha(last_bowl);
			this.did_I_go_already = false;
			return myOpponent;
		}
				
		else if (last_bowl == 0 && this.did_I_go_already == false) {
			
			this.did_I_go_already = true;
			return this;
			
		} else {
			
			this.did_I_go_already = false;
			return this.myOpponent;
			
		}
		
	}
	
	public boolean is_the_game_over() {
		Bowl  Current_bowl = this.myfirstBowl;
		while (Current_bowl != null) {
			if (Current_bowl.am_I_empty() != true) {
				break;
			} else {
				Current_bowl = Current_bowl.getNeighbour();
			}
		}
		boolean is_game_over = (Current_bowl == null)?true:false;
		return is_game_over;
	}	
	
	public int get_my_points() {
		int points = this.myKalaha.get_Stones();
		return points;
	}
	
	public Player Get_the_Winner() {
		
		int points_1 = this.get_my_points(); 
		int points_2 = this.myOpponent.get_my_points();
		
		if (points_1 > points_2) {
			return this;
		} else if (points_2 > points_1) {
			return myOpponent;
		} else {			
			return null;			
		}
				
		
	}
	
	
	
	
	
}
