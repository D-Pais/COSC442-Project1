package edu.towson.cis.cosc442.project1.monopoly;

public class RailRoadCell extends Cell {
	static private int baseRent;
	static public String COLOR_GROUP = "RAILROAD";
	static private int price;

	/**
	 * Sets the base rent value for railroad properties.
	 * @param baseRent the base rent amount to set
	 */
	public static void setBaseRent(int baseRent) {
		RailRoadCell.baseRent = baseRent;
	}

	/**
	 * Sets the purchase price for railroad properties.
	 * @param price the price amount to set
	 */
	public static void setPrice(int price) {
		RailRoadCell.price = price;
	}
	
	/**
	 * Returns the purchase price of the railroad property.
	 * @return the current price of the railroad property
	 */
	public int getPrice() {
		return RailRoadCell.price;
	}

	/**
	 * Calculates and returns the rent owed based on the number of railroads owned.
	 * @return the rent amount calculated using the base rent and the owner's number of railroads
	 */
	public int getRent() {
		return RailRoadCell.baseRent * (int)Math.pow(2, theOwner.numberOfRR() - 1);
	}
	
	/**
	 * Executes the action for the current player landing on this railroad, including rent payment if owned by another player.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				currentPlayer.payRentTo(theOwner, getRent());
			}
		}
	}
}
