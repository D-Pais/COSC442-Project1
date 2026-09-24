package edu.towson.cis.cosc442.project1.monopoly;

public class GoCell extends Cell {
	/**
	 * Constructs a GoCell and initializes its name to "Go" and availability to false.
	 */
	public GoCell() {
		super.setName("Go");
		setAvailable(false);
	}

	/**
	 * Performs the action associated with the GoCell when a player lands on it.
	 */
	public void playAction() {
	}
	
	/**
	 * Overrides the method to set the name of the cell, but does not modify the name for GoCell.
	 * @param name The name to set for the cell
	 */
	void setName(String name) {
	}
}
