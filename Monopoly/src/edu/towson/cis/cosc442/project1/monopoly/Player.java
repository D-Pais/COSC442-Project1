package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Player {
	//the key of colorGroups is the name of the color group.
	private Hashtable<String, Integer> colorGroups = new Hashtable<String, Integer>();
	private boolean inJail;
	private int money;
	private String name;

	private Cell position;
	private ArrayList<PropertyCell> properties = new ArrayList<PropertyCell>();
	private ArrayList<Cell> railroads = new ArrayList<Cell>();
	private ArrayList<Cell> utilities = new ArrayList<Cell>();
	
	/**
	 * Constructs a Player and initializes their position to the 'Go' cell on the game board.
	 */
	public Player() {
		GameBoard gb = GameMaster.instance().getGameBoard();
		inJail = false;
		if(gb != null) {
			position = gb.queryCell("Go");
		}
	}

    /**
     * Assigns ownership of the given property to this player, updates property collections, and deducts the purchase amount from the player's money.
     * @param property The cell representing the property to buy.
     * @param amount The purchase price to deduct from the player.
     */
    public void buyProperty(Cell property, int amount) {
        property.setTheOwner(this);
        if(property instanceof PropertyCell) {
            PropertyCell cell = (PropertyCell)property;
            properties.add(cell);
            colorGroups.put(
                    cell.getColorGroup(), 
                    new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
        }
        if(property instanceof RailRoadCell) {
            railroads.add(property);
            colorGroups.put(
                    RailRoadCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(RailRoadCell.COLOR_GROUP)+1));
        }
        if(property instanceof UtilityCell) {
            utilities.add(property);
            colorGroups.put(
                    UtilityCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(UtilityCell.COLOR_GROUP)+1));
        }
        setMoney(getMoney() - amount);
    }
	
	/**
	 * Determines if the player owns any monopolies, allowing them to buy houses.
	 * @return true if the player can buy a house; false otherwise.
	 */
	public boolean canBuyHouse() {
		return (getMonopolies().length != 0);
	}

	/**
	 * Checks if the player owns the property with the specified name.
	 * @param property The name of the property to check for ownership.
	 * @return true if the player owns the property; false otherwise.
	 */
	public boolean checkProperty(String property) {
		for(int i=0;i<properties.size();i++) {
			Cell cell = (Cell)properties.get(i);
			if(cell.getName().equals(property)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Transfers all properties owned by this player to the specified player, or resets ownership if null is given.
	 * @param player The player to receive the properties or null to reset ownership.
	 */
	public void exchangeProperty(Player player) {
		for(int i = 0; i < getPropertyNumber(); i++ ) {
			PropertyCell cell = getProperty(i);
			cell.setTheOwner(player);
			if(player == null) {
				cell.setAvailable(true);
				cell.setNumHouses(0);
			}
			else {
				player.properties.add(cell);
				colorGroups.put(
						cell.getColorGroup(), 
						new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
			}
		}
		properties.clear();
	}
    
    /**
     * Returns an array of all properties, including regular properties, railroads, and utilities, owned by the player.
     * @return An array containing all cells owned by the player.
     */
    public Cell[] getAllProperties() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (Cell[])list.toArray(new Cell[list.size()]);
    }

	/**
	 * Gets the current amount of money the player has.
	 * @return The player's amount of money.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Retrieves the color groups for which the player owns all properties, constituting monopolies.
	 * @return An array of color group names that the player monopolizes.
	 */
	public String[] getMonopolies() {
		ArrayList<String> monopolies = new ArrayList<String>();
		Enumeration<String> colors = colorGroups.keys();
		while(colors.hasMoreElements()) {
			String color = (String)colors.nextElement();
            if(!(color.equals(RailRoadCell.COLOR_GROUP)) && !(color.equals(UtilityCell.COLOR_GROUP))) {
    			Integer num = (Integer)colorGroups.get(color);
    			GameBoard gameBoard = GameMaster.instance().getGameBoard();
    			if(num.intValue() == gameBoard.getPropertyNumberForColor(color)) {
    				monopolies.add(color);
    			}
            }
		}
		return (String[])monopolies.toArray(new String[monopolies.size()]);
	}

	/**
	 * Returns the player's name.
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pays the jail bail, releases the player from jail, and handles bankruptcy if the player cannot pay.
	 */
	public void getOutOfJail() {
		money -= JailCell.BAIL;
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(null);
		}
		inJail = false;
		GameMaster.instance().updateGUI();
	}

	/**
	 * Returns the cell representing the player's current position on the board.
	 * @return The cell where the player is currently located.
	 */
	public Cell getPosition() {
		return this.position;
	}
	
	/**
	 * Gets the property owned by the player at the specified index.
	 * @param index The index of the property to retrieve.
	 * @return The PropertyCell at the given index.
	 */
	public PropertyCell getProperty(int index) {
		return (PropertyCell)properties.get(index);
	}
	
	/**
	 * Returns the total number of properties owned by the player.
	 * @return The count of properties owned by the player.
	 */
	public int getPropertyNumber() {
		return properties.size();
	}

	/**
	 * Returns the number of properties the player owns in the specified color group.
	 * @param name The color group name to query.
	 * @return The number of properties owned in the color group.
	 */
	private int getPropertyNumberForColor(String name) {
		Integer number = (Integer)colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Checks whether the player is bankrupt, defined as having zero or negative money.
	 * @return true if the player is bankrupt; false otherwise.
	 */
	public boolean isBankrupt() {
		return money <= 0;
	}

	/**
	 * Returns whether the player is currently in jail.
	 * @return true if the player is in jail; false otherwise.
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * Returns the number of railroad properties owned by the player.
	 * @return The count of railroad properties owned.
	 */
	public int numberOfRR() {
		return getPropertyNumberForColor(RailRoadCell.COLOR_GROUP);
	}

	/**
	 * Returns the number of utility properties owned by the player.
	 * @return The count of utility properties owned.
	 */
	public int numberOfUtil() {
		return getPropertyNumberForColor(UtilityCell.COLOR_GROUP);
	}
	
	/**
	 * Pays the specified rent amount to another player, handling partial payments and bankruptcy if necessary.
	 * @param owner The player who will receive the rent.
	 * @param rentValue The amount of rent to be paid.
	 */
	public void payRentTo(Player owner, int rentValue) {
		if(money < rentValue) {
			owner.money += money;
			money -= rentValue;
		}
		else {
			money -= rentValue;
			owner.money +=rentValue;
		}
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(owner);
		}
	}
	
	/**
	 * Attempts to purchase the property on the player's current position if it is available.
	 */
	public void purchase() {
		if(getPosition().isAvailable()) {
			Cell c = getPosition();
			c.setAvailable(false);
			if(c instanceof PropertyCell) {
				PropertyCell cell = (PropertyCell)c;
				purchaseProperty(cell);
			}
			if(c instanceof RailRoadCell) {
				RailRoadCell cell = (RailRoadCell)c;
				purchaseRailRoad(cell);
			}
			if(c instanceof UtilityCell) {
				UtilityCell cell = (UtilityCell)c;
				purchaseUtility(cell);
			}
		}
	}
	
	/**
	 * Purchases the specified number of houses for all properties in the selected monopoly if the player has sufficient funds.
	 * @param selectedMonopoly The name of the monopoly color group.
	 * @param houses The number of houses to purchase per property.
	 */
	public void purchaseHouse(String selectedMonopoly, int houses) {
		GameBoard gb = GameMaster.instance().getGameBoard();
		PropertyCell[] cells = gb.getPropertiesInMonopoly(selectedMonopoly);
		if((money >= (cells.length * (cells[0].getHousePrice() * houses)))) {
			for(int i = 0; i < cells.length; i++) {
				int newNumber = cells[i].getNumHouses() + houses;
				if (newNumber <= 5) {
					cells[i].setNumHouses(newNumber);
					this.setMoney(money - (cells[i].getHousePrice() * houses));
					GameMaster.instance().updateGUI();
				}
			}
		}
	}
	
	/**
	 * Purchases the specified property cell, assigning ownership and deducting cost.
	 * @param cell The property cell to purchase.
	 */
	private void purchaseProperty(PropertyCell cell) {
        buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases the specified railroad property cell, assigning ownership and deducting cost.
	 * @param cell The railroad property cell to purchase.
	 */
	private void purchaseRailRoad(RailRoadCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases the specified utility property cell, assigning ownership and deducting cost.
	 * @param cell The utility property cell to purchase.
	 */
	private void purchaseUtility(UtilityCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

    /**
     * Sells the given property cell, removing ownership and adding the specified amount to the player's money.
     * @param property The property cell to sell.
     * @param amount The amount to credit the player for the sale.
     */
    public void sellProperty(Cell property, int amount) {
        property.setTheOwner(null);
        if(property instanceof PropertyCell) {
            properties.remove(property);
        }
        if(property instanceof RailRoadCell) {
            railroads.remove(property);
        }
        if(property instanceof UtilityCell) {
            utilities.remove(property);
        }
        setMoney(getMoney() + amount);
    }

	/**
	 * Sets the player's jail status to the specified value.
	 * @param inJail true if the player is in jail; false otherwise.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * Sets the player's money to the specified amount.
	 * @param money The new amount of money for the player.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the player's name to the given value.
	 * @param name The new name for the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the player's position to the specified cell on the board.
	 * @param newPosition The cell where the player will be positioned.
	 */
	public void setPosition(Cell newPosition) {
		this.position = newPosition;
	}

    /**
     * Returns a string representation of the player, which is their name.
     * @return The player's name as a string.
     */
    public String toString() {
        return name;
    }
    
    /**
     * Clears all properties, railroads, and utilities owned by the player.
     */
    public void resetProperty() {
    	properties = new ArrayList<PropertyCell>();
    	railroads = new ArrayList<Cell>();
    	utilities = new ArrayList<Cell>();
	}
}
