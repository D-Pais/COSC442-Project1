package edu.towson.cis.cosc442.project1.monopoly;

public interface MonopolyGUI {
	/**
	 * Enables or activates the 'End Turn' button for the specified player.
	 * @param playerIndex The index identifying the player for whom to enable the button
	 */
	public void enableEndTurnBtn(int playerIndex);
	/**
	 * Sets the user interface to indicate it is the specified player's turn.
	 * @param playerIndex The index of the player whose turn it is
	 */
	public void enablePlayerTurn(int playerIndex);
	/**
	 * Enables the purchase button for a specified player to allow property buying.
	 * @param playerIndex The index of the player for whom the purchase button is enabled
	 */
	public void enablePurchaseBtn(int playerIndex);
	/**
	 * Returns the current dice roll values as an array of integers.
	 * @return An array containing the values of the dice rolled
	 */
	public int[] getDiceRoll();
    /**
     * Checks if the button to draw a card is currently enabled.
     * @return True if the draw card button is enabled, false otherwise
     */
    public boolean isDrawCardButtonEnabled();
    /**
     * Checks if the 'End Turn' button is currently enabled.
     * @return True if the end turn button is enabled, false otherwise
     */
    public boolean isEndTurnButtonEnabled();
	/**
	 * Checks whether the 'Get Out of Jail' button is enabled.
	 * @return True if the get out of jail button is enabled, false otherwise
	 */
	public boolean isGetOutOfJailButtonEnabled();
    /**
     * Determines if the trade button is enabled for a specified player.
     * @param i The index of the player to check trade button status for
     * @return True if the trade button is enabled for the player, false otherwise
     */
    public boolean isTradeButtonEnabled(int i);
	/**
	 * Moves the specified player from one position to another on the game board.
	 * @param index The index of the player to move
	 * @param from The current position of the player on the board
	 * @param to The destination position on the board
	 */
	public void movePlayer(int index, int from, int to);
    /**
     * Opens a dialog allowing the player to respond to a trade deal.
     * @param deal The trade deal to respond to
     * @return A dialog interface for responding to the trade deal
     */
    public RespondDialog openRespondDialog(TradeDeal deal);
    /**
     * Opens the trade dialog to initiate a trade between players.
     * @return A dialog interface for creating and managing trades
     */
    public TradeDialog openTradeDialog();
    /**
     * Enables or disables the ability to buy houses in the UI.
     * @param b True to enable house buying, false to disable
     */
    public void setBuyHouseEnabled(boolean b);
    /**
     * Enables or disables the draw card button in the user interface.
     * @param b True to enable draw card action, false to disable
     */
    public void setDrawCardEnabled(boolean b);
    /**
     * Enables or disables the 'End Turn' button in the UI.
     * @param enabled True to enable the button, false to disable
     */
    public void setEndTurnEnabled(boolean enabled);
    /**
     * Enables or disables the 'Get Out of Jail' button for the player.
     * @param b True to enable the button, false to disable
     */
    public void setGetOutOfJailEnabled(boolean b);
    /**
     * Enables or disables the ability to purchase properties in the UI.
     * @param enabled True to allow property purchases, false to disallow
     */
    public void setPurchasePropertyEnabled(boolean enabled);
    /**
     * Enables or disables the roll dice button in the interface.
     * @param b True to enable rolling dice, false to disable
     */
    public void setRollDiceEnabled(boolean b);
    /**
     * Enables or disables the trade button for a specific player.
     * @param index The index of the player whose trade button status is set
     * @param b True to enable the trade button, false to disable
     */
    public void setTradeEnabled(int index, boolean b);
    /**
     * Displays a dialog allowing the specified player to buy houses.
     * @param currentPlayer The player who may buy houses
     */
    public void showBuyHouseDialog(Player currentPlayer);
    /**
     * Displays a message to the player through the user interface.
     * @param string The message text to display
     */
    public void showMessage(String string);
	/**
	 * Shows the result of a utility dice roll to the user.
	 * @return The total value rolled on the utility dice
	 */
	public int showUtilDiceRoll();
	/**
	 * Initiates the start of the Monopoly game, setting up the UI accordingly.
	 */
	public void startGame();
	/**
	 * Refreshes or updates the game's user interface to reflect the current game state.
	 */
	public void update();
}
