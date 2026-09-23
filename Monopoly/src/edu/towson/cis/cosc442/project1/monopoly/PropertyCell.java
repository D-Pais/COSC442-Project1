package edu.towson.cis.cosc442.project1.monopoly;

public class PropertyCell extends Cell {
	private String colorGroup;
	private int housePrice;
	private int numHouses;
	private int rent;
	private int sellPrice;

	/**
	 * Returns the color group to which this property belongs.
	 * @return the color group of the property
	 */
	public String getColorGroup() {
		return colorGroup;
	}

	/**
	 * Returns the price to build a house on this property.
	 * @return the cost of building a house
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Returns the number of houses currently built on this property.
	 * @return the number of houses on the property
	 */
	public int getNumHouses() {
		return numHouses;
	}
    
    /**
     * Returns the selling price of this property.
     * @return the sell price of the property
     */
    public int getPrice() {
		return sellPrice;
	}

	/**
	 * Calculates and returns the rent amount owed when landing on this property.
	 * @return the rent to be charged, considering monopolies and houses
	 */
	public int getRent() {
		int rentToCharge = rent;
		String [] monopolies = theOwner.getMonopolies();
		rentToCharge = calculateMonopoliesRent(rentToCharge, monopolies);
		if(numHouses > 0) {
			rentToCharge = rent * (numHouses + 1);
		}
		return rentToCharge;
	}

	/**
	 * Calculates the rent considering if the owner has a monopoly in the given color groups.
	 * @param rentToCharge the initial rent amount to be adjusted
	 * @param monopolies an array of color groups the owner has monopolies on
	 * @return the adjusted rent amount factoring in monopolies
	 */
	private int calculateMonopoliesRent(int rentToCharge, String[] monopolies) {
		for(int i = 0; i < monopolies.length; i++) {
			if(monopolies[i].equals(colorGroup)) {
				rentToCharge = rent * 2;
			}
		}
		return rentToCharge;
	}

	/**
	 * Executes the action when a player lands on this property, including rent payment if owned.
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

	/**
	 * Sets the color group of this property.
	 * @param colorGroup the color group to assign to this property
	 */
	public void setColorGroup(String colorGroup) {
		this.colorGroup = colorGroup;
	}

	/**
	 * Sets the cost to build a house on this property.
	 * @param housePrice the house building cost to set
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Sets the number of houses on this property.
	 * @param numHouses the number of houses to assign to this property
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * Sets the selling price of this property.
	 * @param sellPrice the sell price to assign to this property
	 */
	public void setPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * Sets the base rent amount of this property.
	 * @param rent the rent value to set
	 */
	public void setRent(int rent) {
		this.rent = rent;
	}
}
