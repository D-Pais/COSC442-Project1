package edu.towson.cis.cosc442.project1.monopoly;

public class TradeDeal {
    private int amount;
    private int playerIndex;
    private String propertyName;

    /**
     * Returns the monetary amount offered in the trade deal.
     * @return The amount of money proposed in the trade.
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Retrieves the index of the player who is the seller in this trade deal.
     * @return The seller player's index.
     */
    public int getPlayerIndex() {
        return playerIndex;
    }
    
    /**
     * Obtains the name of the property involved in the trade.
     * @return The property name being traded.
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * Creates a message string describing the trade deal offer including buyer, seller, property, and amount.
     * @return A string message representing the trade offer to the seller.
     */
    public String makeMessage() {
        String message = GameMaster.instance().getCurrentPlayer() + 
        	" wishes to purchase " +
        	propertyName + " from " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	" for " + amount + ".  " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	", do you wish to trade your property?";
        return message;
    }
    
    /**
     * Sets the monetary amount to be offered in the trade deal.
     * @param amount The amount of money to offer.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Assigns the name of the property to be traded.
     * @param propertyName The name of the property involved in the trade.
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * Defines the seller player's index in the trade deal.
     * @param playerIndex The index identifying the seller player.
     */
    public void setSellerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
