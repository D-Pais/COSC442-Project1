package edu.towson.cis.cosc442.project1.monopoly;

public abstract class Cell {
	private boolean available = true;
	private String name;
	protected Player theOwner;

	/**
	 * Returns the name of the cell.
	 * @return The name of the cell as a String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the player who owns the cell.
	 * @return The Player object that owns the cell, or null if unowned.
	 */
	public Player getTheOwner() {
		return theOwner;
	}
	
	/**
	 * Returns the price of the cell, which is zero by default.
	 * @return The price of the cell as an integer.
	 */
	public int getPrice() {
		return 0;
	}

	/**
	 * Checks whether the cell is currently available.
	 * @return True if the cell is available; false otherwise.
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * Performs the action associated with landing on this cell.
	 */
	public abstract void playAction();

	/**
	 * Sets the availability status of the cell.
	 * @param available The availability status to set for the cell.
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	/**
	 * Sets the name of the cell.
	 * @param name The new name to assign to the cell.
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Assigns ownership of the cell to a player.
	 * @param owner The Player who will own this cell.
	 */
	public void setTheOwner(Player owner) {
		this.theOwner = owner;
	}
    
    /**
     * Returns a string representation of the cell.
     * @return The name of the cell as its string representation.
     */
    public String toString() {
        return name;
    }
}
