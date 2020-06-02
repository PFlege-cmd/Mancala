package nl.sogyo.mancala.domain;
import static org.junit.Assert.assertEquals;

import java.util.Scanner;

public class TestDriveGame {
	
	static int queryBowl(){ // Recursive error-catching-function!
		try {
			Scanner mainScan = new Scanner(System.in);
			int bowl_number = mainScan.nextInt();
			return bowl_number;
		}
		catch (Exception e){
			System.out.println("Give a non-empty bowl of the current player!");
			int bowl_number = queryBowl(); // Function calls itself till proper input!
			return bowl_number;
		}
	}
	
	
	public static void main(String[] args) {
    	Bowl myveryfirstBowl = new Bowl();
    	
    	Player Player_1 = myveryfirstBowl.getOwner();    	
    	Player Player_2 = Player_1.get_Opponent();
      	Player Current_Player = Player_1;
    	
    	boolean game_over = false;
    	
		System.out.println("It is player " + Current_Player.get_name() + " turn.");
		int[] Stones_at_start_2 = {0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1};
    	
    	myveryfirstBowl.setUpGame(Stones_at_start_2);
    		
    	
    	while (game_over == false) {
    		
    		int inputbowl = TestDriveGame.queryBowl();
    		
    		Current_Player = Current_Player.make_turn_and_change_player(inputbowl);
    		
    		Player Check_player = Current_Player;
    		
    		System.out.println("It is player " + Current_Player.get_name() + " turn.");
    		
    		myveryfirstBowl.print_stones_in_bowls();
    		
    		game_over = Check_player.is_the_game_over();
    		
    	}
    	
    	System.out.println("Game over!");
    	Player Winner = Current_Player.Get_the_Winner();
    	if (Winner != null) {
    		System.out.println("And the winner is " + Winner.get_name() + "! Gefeliciteert!!!");
    	} else {
    		System.out.println("It is a tie! How boring :(");
    	}
	}

}
