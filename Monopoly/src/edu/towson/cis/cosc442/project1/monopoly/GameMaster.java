package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Iterator;


public class GameMaster {

	private static GameMaster gameMaster;
	static final public int MAX_PLAYER = 8;	
	private Die[] dice;
	private GameBoard gameBoard;
	private MonopolyGUI gui;
	private int initAmountOfMoney;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int turn = 0;
	private int utilDiceRoll;
	private boolean testMode;

	/**
	 * Returns the singleton instance of GameMaster, creating it if necessary.
	 * @return The singleton GameMaster instance.
	 */
	public static GameMaster instance() {
		if(gameMaster == null) {
			gameMaster = new GameMaster();
		}
		return gameMaster;
	}

	/**
	 * Constructs a GameMaster with default initial money and two dice.
	 */
	public GameMaster() {
		initAmountOfMoney = 1500;
		dice = new Die[]{new Die(), new Die()};
	}

    /**
     * Handles the event when the Buy House button is clicked, showing the buy house dialog for the current player.
     */
    public void btnBuyHouseClicked() {
        gui.showBuyHouseDialog(getCurrentPlayer());
    }

    /**
     * Handles the event when the Draw Card button is clicked, disables the button, draws the appropriate card for the current player's position, applies its action, enables the End Turn button, and returns the card drawn.
     * @return The Card drawn (Chance or Community Chest).
     */
    public Card btnDrawCardClicked() {
        gui.setDrawCardEnabled(false);
        CardCell cell = (CardCell)getCurrentPlayer().getPosition();
        Card card = null;
        if(cell.getType() == Card.TYPE_CC) {
            card = getGameBoard().drawCCCard();
            card.applyAction();
        } else {
            card = getGameBoard().drawChanceCard();
            card.applyAction();
        }
        gui.setEndTurnEnabled(true);
        return card;
    }

    /**
     * Handles the End Turn button click, disables buttons, processes the current player's position action, updates GUI, switches turn if the player is not bankrupt.
     */
    public void btnEndTurnClicked() {
		setAllButtonEnabled(false);
		getCurrentPlayer().getPosition().playAction();
		if(getCurrentPlayer().isBankrupt()) {
			gui.setBuyHouseEnabled(false);
			gui.setDrawCardEnabled(false);
			gui.setEndTurnEnabled(false);
			gui.setGetOutOfJailEnabled(false);
			gui.setPurchasePropertyEnabled(false);
			gui.setRollDiceEnabled(false);
			gui.setTradeEnabled(getCurrentPlayerIndex(),false);
			updateGUI();
		}
		else {
			switchTurn();
			updateGUI();
		}
    }

    /**
     * Handles the Get Out Of Jail button click, attempts to get the current player out of jail and updates button states accordingly.
     */
    public void btnGetOutOfJailClicked() {
		getCurrentPlayer().getOutOfJail();
		if(getCurrentPlayer().isBankrupt()) {
			gui.setBuyHouseEnabled(false);
			gui.setDrawCardEnabled(false);
			gui.setEndTurnEnabled(false);
			gui.setGetOutOfJailEnabled(false);
			gui.setPurchasePropertyEnabled(false);
			gui.setRollDiceEnabled(false);
			gui.setTradeEnabled(getCurrentPlayerIndex(),false);
		}
		else {
			gui.setRollDiceEnabled(true);
			gui.setBuyHouseEnabled(getCurrentPlayer().canBuyHouse());
			gui.setGetOutOfJailEnabled(getCurrentPlayer().isInJail());
		}
    }

    /**
     * Handles the Purchase Property button click by having the current player purchase the property and updating the GUI.
     */
    public void btnPurchasePropertyClicked() {
        Player player = getCurrentPlayer();
		player.purchase();
		gui.setPurchasePropertyEnabled(false);
		updateGUI();
    }
    
    /**
     * Handles the Roll Dice button click by rolling the dice, moving the player accordingly, disabling the roll dice button, and updating the GUI.
     */
    public void btnRollDiceClicked() {
		int[] rolls = rollDice();
		if((rolls[0]+rolls[1]) > 0) {
			Player player = getCurrentPlayer();
			gui.setRollDiceEnabled(false);
			StringBuffer msg = new StringBuffer();
			msg.append(player.getName())
					.append(", you rolled ")
					.append(rolls[0])
					.append(" and ")
					.append(rolls[1]);
			gui.showMessage(msg.toString());
			movePlayer(player, rolls[0] + rolls[1]);
			gui.setBuyHouseEnabled(false);
		}
    }

    /**
     * Handles the Trade button click by opening the trade dialog, processing the trade deal, and updating the GUI if the trade completes.
     */
    public void btnTradeClicked() {
        TradeDialog dialog = gui.openTradeDialog();
        TradeDeal deal = dialog.getTradeDeal();
        if(deal != null) {
            RespondDialog rDialog = gui.openRespondDialog(deal);
            if(rDialog.getResponse()) {
                completeTrade(deal);
                updateGUI();
            }
        }
    }

    /**
     * Completes a trade between the seller and current player by transferring property and money according to the trade deal.
     * @param deal The trade deal containing trade details.
     */
    public void completeTrade(TradeDeal deal) {
        Player seller = getPlayer(deal.getPlayerIndex());
        Cell property = gameBoard.queryCell(deal.getPropertyName());
        seller.sellProperty(property, deal.getAmount());
        getCurrentPlayer().buyProperty(property, deal.getAmount());
    }

    /**
     * Draws a Community Chest card from the game board.
     * @return The Community Chest card drawn.
     */
    public Card drawCCCard() {
        return gameBoard.drawCCCard();
    }

    /**
     * Draws a Chance card from the game board.
     * @return The Chance card drawn.
     */
    public Card drawChanceCard() {
        return gameBoard.drawChanceCard();
    }

	
	/**
	 * Returns the player whose turn it currently is.
	 * @return The current Player object.
	 */
	public Player getCurrentPlayer() {
		return getPlayer(turn);
	}
    
    /**
     * Returns the index of the current player whose turn it is.
     * @return The index of the current player.
     */
    public int getCurrentPlayerIndex() {
        return turn;
    }

	/**
	 * Returns the game board used in the game.
	 * @return The GameBoard object.
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}

    /**
     * Returns the Monopoly GUI interface used by the game.
     * @return The MonopolyGUI instance.
     */
    public MonopolyGUI getGUI() {
        return gui;
    }

	/**
	 * Returns the initial amount of money each player starts with.
	 * @return The initial amount of money.
	 */
	public int getInitAmountOfMoney() {
		return initAmountOfMoney;
	}
	
	/**
	 * Returns the number of players currently in the game.
	 * @return The number of players.
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}

    /**
     * Returns the number of potential sellers in trade (all players except the current one).
     * @return The number of sellers available for trade.
     */
    public int getNumberOfSellers() {
        return players.size() - 1;
    }

	/**
	 * Returns the player at the specified index.
	 * @param index The index of the player to retrieve.
	 * @return The Player at the given index.
	 */
	public Player getPlayer(int index) {
		return (Player)players.get(index);
	}
	
	/**
	 * Returns the index of the specified player in the player list.
	 * @param player The player whose index is requested.
	 * @return The index of the player, or -1 if not found.
	 */
	public int getPlayerIndex(Player player) {
		return players.indexOf(player);
	}

    /**
     * Returns a list of all players except the current player, considered sellers for trade purposes.
     * @return An ArrayList of Player objects excluding the current player.
     */
    public ArrayList<Player> getSellerList() {
        ArrayList<Player> sellers = new ArrayList<Player>();
        for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
            Player player = (Player) iter.next();
            if(player != getCurrentPlayer()) sellers.add(player);
        }
        return sellers;
    }

	/**
	 * Returns the index representing the current turn's player.
	 * @return The current turn index.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Returns the utility dice roll value used for testing or utility purposes.
	 * @return The stored utility dice roll value.
	 */
	public int getUtilDiceRoll() {
		return this.utilDiceRoll;
	}

	/**
	 * Moves the player at the specified index forward by the given dice value and handles passing Go.
	 * @param playerIndex The index of the player to move.
	 * @param diceValue The value of the dice roll to move the player by.
	 */
	public void movePlayer(int playerIndex, int diceValue) {
		Player player = (Player)players.get(playerIndex);
		movePlayer(player, diceValue);
	}
	
	/**
	 * Moves the specified player forward by the given dice value, credits $200 if passing Go, updates GUI, and triggers playerMoved.
	 * @param player The Player to move.
	 * @param diceValue The dice value that dictates movement distance.
	 */
	public void movePlayer(Player player, int diceValue) {
		Cell currentPosition = player.getPosition();
		int positionIndex = gameBoard.queryCellIndex(currentPosition.getName());
		int newIndex = (positionIndex+diceValue)%gameBoard.getCellNumber();
		if(newIndex <= positionIndex || diceValue > gameBoard.getCellNumber()) {
			player.setMoney(player.getMoney() + 200);
		}
		player.setPosition(gameBoard.getCell(newIndex));
		gui.movePlayer(getPlayerIndex(player), positionIndex, newIndex);
		playerMoved(player);
		updateGUI();
	}

	/**
	 * Processes actions and GUI updates after the player has moved to a new cell.
	 * @param player The player who has just moved.
	 */
	public void playerMoved(Player player) {
		Cell cell = player.getPosition();
		int playerIndex = getPlayerIndex(player);
		if(cell instanceof CardCell) {
		    gui.setDrawCardEnabled(true);
		} else{
			if(cell.isAvailable()) {
				int price = cell.getPrice();
				if(price <= player.getMoney() && price > 0) {
					gui.enablePurchaseBtn(playerIndex);
				}
			}	
			gui.enableEndTurnBtn(playerIndex);
		}
        gui.setTradeEnabled(turn, false);
	}

	/**
	 * Resets the game by setting all player positions to start, removing cards from the board, and resetting the turn counter.
	 */
	public void reset() {
		for(int i = 0; i < getNumberOfPlayers(); i++){
			Player player = (Player)players.get(i);
			player.setPosition(gameBoard.getCell(0));
		}
		if(gameBoard != null) gameBoard.removeCards();
		turn = 0;
	}
	
	/**
	 * Rolls the dice, returning GUI provided dice if in test mode, otherwise rolls two random dice.
	 * @return An array of two integers representing dice roll values.
	 */
	public int[] rollDice() {
		if(testMode) {
			return gui.getDiceRoll();
		}
		else {
			return new int[]{
					dice[0].getRoll(),
					dice[1].getRoll()
			};
		}
	}
	
	/**
	 * Sends the specified player to jail, updates their position and GUI accordingly.
	 * @param player The player to send to jail.
	 */
	public void sendToJail(Player player) {
	    int oldPosition = gameBoard.queryCellIndex(getCurrentPlayer().getPosition().getName());
		player.setPosition(gameBoard.queryCell("Jail"));
		player.setInJail(true);
		int jailIndex = gameBoard.queryCellIndex("Jail");
		gui.movePlayer(
		        getPlayerIndex(player),
		        oldPosition,
		        jailIndex);
	}
    
	/**
	 * Enables or disables all relevant GUI buttons for the current player's turn.
	 * @param enabled True to enable buttons, false to disable.
	 */
	private void setAllButtonEnabled(boolean enabled) {
		gui.setRollDiceEnabled(enabled);
		gui.setPurchasePropertyEnabled(enabled);
		gui.setEndTurnEnabled(enabled);
        gui.setTradeEnabled(turn, enabled);
        gui.setBuyHouseEnabled(enabled);
        gui.setDrawCardEnabled(enabled);
        gui.setGetOutOfJailEnabled(enabled);
	}

	/**
	 * Sets the game board used by the game master.
	 * @param board The GameBoard to assign.
	 */
	public void setGameBoard(GameBoard board) {
		this.gameBoard = board;
	}
	
	/**
	 * Sets the GUI interface used by the game.
	 * @param gui The MonopolyGUI instance to assign.
	 */
	public void setGUI(MonopolyGUI gui) {
		this.gui = gui;
	}

	/**
	 * Sets the initial amount of money for players at game start.
	 * @param money The amount of money to initialize players with.
	 */
	public void setInitAmountOfMoney(int money) {
		this.initAmountOfMoney = money;
	}

	/**
	 * Sets the number of players for the game, clearing existing players and creating new ones with initial money.
	 * @param number The number of players to set.
	 */
	public void setNumberOfPlayers(int number) {
		players.clear();
		for(int i =0;i<number;i++) {
			Player player = new Player();
			player.setMoney(initAmountOfMoney);
			players.add(player);
		}
	}

	/**
	 * Sets the utility dice roll value for testing or utility purposes.
	 * @param diceRoll The dice roll value to set.
	 */
	public void setUtilDiceRoll(int diceRoll) {
		this.utilDiceRoll = diceRoll;
	}
	
	/**
	 * Starts the game by initializing the GUI and enabling the first player's turn with trading enabled.
	 */
	public void startGame() {
		gui.startGame();
		gui.enablePlayerTurn(0);
        gui.setTradeEnabled(0, true);
	}

	/**
	 * Advances the turn to the next player, updates GUI button states, and manages jail status actions.
	 */
	public void switchTurn() {
		turn = (turn + 1) % getNumberOfPlayers();
		if(!getCurrentPlayer().isInJail()) {
			gui.enablePlayerTurn(turn);
			gui.setBuyHouseEnabled(getCurrentPlayer().canBuyHouse());
            gui.setTradeEnabled(turn, true);
		}
		else {
			gui.setGetOutOfJailEnabled(true);
		}
	}
	
	/**
	 * Requests the GUI to update its current display state.
	 */
	public void updateGUI() {
		gui.update();
	}

	/**
	 * Displays a utility dice roll via the GUI and stores the result for testing purposes.
	 */
	public void utilRollDice() {
		this.utilDiceRoll = gui.showUtilDiceRoll();
	}

	/**
	 * Enables or disables test mode which affects dice rolling behavior.
	 * @param b True to enable test mode, false to disable.
	 */
	public void setTestMode(boolean b) {
		testMode = b;
	}
}
