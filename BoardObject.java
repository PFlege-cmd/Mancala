package nl.sogyo.mancala.domain;

public abstract class BoardObject{

	
	
	private int Stones;
	private Player Owner;
	private int position;
	
	//Player Owner;
	
	//public int get_stones();
	public abstract int get_Stones();
	
		//System.out.println("Stony");
		//return 0;
		
	
	public abstract void set_Stones(int new_stones);
	
	public abstract int get_Position(); 	
	
	public abstract String getOwnerName();
	
	public abstract Player getOwner();
	
	public abstract Bowl getNeighbour();
	
	

}



