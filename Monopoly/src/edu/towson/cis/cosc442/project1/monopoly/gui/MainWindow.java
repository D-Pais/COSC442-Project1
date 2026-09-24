package edu.towson.cis.cosc442.project1.monopoly.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import edu.towson.cis.cosc442.project1.monopoly.*;

public class MainWindow extends JFrame implements MonopolyGUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel eastPanel = new JPanel();
	ArrayList<GUICell> guiCells = new ArrayList<GUICell>();

	JPanel northPanel = new JPanel();
	PlayerPanel[] playerPanels;
	JPanel southPanel = new JPanel();
	JPanel westPanel = new JPanel();

	/**
	 * Constructs the main window for the Monopoly game GUI and sets up its panels and window listener.
	 */
	public MainWindow() {
		northPanel.setBorder(new LineBorder(Color.BLACK));
		southPanel.setBorder(new LineBorder(Color.BLACK));
		westPanel.setBorder(new LineBorder(Color.BLACK));
		eastPanel.setBorder(new LineBorder(Color.BLACK));
		
		Container c = getContentPane();

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setSize(d);
		c.add(northPanel, BorderLayout.NORTH);
		c.add(southPanel, BorderLayout.SOUTH);
		c.add(eastPanel, BorderLayout.EAST);
		c.add(westPanel, BorderLayout.WEST);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			/**
			 * Handles the window closing event by exiting the application.
			 */
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * Adds GUI cell components to the specified panel for each cell in the given list.
	 * @param panel The JPanel to which GUI cells are added.
	 * @param cells The list of cells to create GUI components from.
	 */
	private void addCells(JPanel panel, List<?> cells) {
		for(int x=0; x<cells.size(); x++) {
			GUICell cell = new GUICell((Cell)cells.get(x));
			panel.add(cell);
			guiCells.add(cell);
		}
	}
	
	/**
	 * Builds and displays the player panels with information for all players in the game.
	 */
	private void buildPlayerPanels() {
		GameMaster master = GameMaster.instance();
		JPanel infoPanel = new JPanel();
        int players = master.getNumberOfPlayers();
        infoPanel.setLayout(new GridLayout(2, (players+1)/2));
		getContentPane().add(infoPanel, BorderLayout.CENTER);
		playerPanels = new PlayerPanel[master.getNumberOfPlayers()];
		for (int i = 0; i< master.getNumberOfPlayers(); i++){
			playerPanels[i] = new PlayerPanel(master.getPlayer(i));
			infoPanel.add(playerPanels[i]);
			playerPanels[i].displayInfo();
		}
	}

	/**
	 * Enables the 'End Turn' button on the player panel for the specified player index.
	 * @param playerIndex The index of the player whose 'End Turn' button is to be enabled.
	 */
	public void enableEndTurnBtn(int playerIndex) {
		playerPanels[playerIndex].setEndTurnEnabled(true);
	}
	
	/**
	 * Enables the 'Roll Dice' button for the current turn of the specified player index.
	 * @param playerIndex The index of the player whose turn is being enabled.
	 */
	public void enablePlayerTurn(int playerIndex) {
		playerPanels[playerIndex].setRollDiceEnabled(true);
		
	}

	/**
	 * Enables the 'Purchase Property' button for the specified player.
	 * @param playerIndex The index of the player to enable the purchase button for.
	 */
	public void enablePurchaseBtn(int playerIndex) {
		playerPanels[playerIndex].setPurchasePropertyEnabled(true);
	}

	@SuppressWarnings("deprecation")
	/**
	 * Opens a dialog to simulate a dice roll and returns the result as an integer array.
	 * @return An integer array representing the dice roll results.
	 */
	public int[] getDiceRoll() {
		TestDiceRollDialog dialog = new TestDiceRollDialog(this);
		dialog.show();
		return dialog.getDiceRoll();
	}

    /**
     * Checks if the 'Draw Card' button is enabled for the current player.
     * @return True if the 'Draw Card' button is enabled; otherwise false.
     */
    public boolean isDrawCardButtonEnabled() {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        return playerPanels[currentPlayerIndex].isDrawCardButtonEnabled();
    }

    /**
     * Checks if the 'End Turn' button is enabled for the current player.
     * @return True if the 'End Turn' button is enabled; otherwise false.
     */
    public boolean isEndTurnButtonEnabled() {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        return playerPanels[currentPlayerIndex].isEndTurnButtonEnabled();
    }

	/**
	 * Checks if the 'Get Out of Jail' button is enabled for the current player.
	 * @return True if the 'Get Out of Jail' button is enabled; otherwise false.
	 */
	public boolean isGetOutOfJailButtonEnabled() {
		int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
		return playerPanels[currentPlayerIndex].isGetOutOfJailButtonEnabled();
	}

    /**
     * Checks if the 'Trade' button is enabled for the specified player index.
     * @param i The index of the player to check for the trade button status.
     * @return True if the 'Trade' button is enabled for the player; otherwise false.
     */
    public boolean isTradeButtonEnabled(int i) {
        return playerPanels[i].isTradeButtonEnabled();
    }
	
	/**
	 * Moves a player icon from one cell to another on the board for the specified player index.
	 * @param index The player index to move.
	 * @param from The starting cell index.
	 * @param to The destination cell index.
	 */
	public void movePlayer(int index, int from, int to) {
		GUICell fromCell = queryCell(from);
		GUICell toCell = queryCell(to);
		if (fromCell != null) fromCell.removePlayer(index);
		if (toCell != null) toCell.addPlayer(index);
	}

    @SuppressWarnings("deprecation")
	/**
	 * Opens a dialog for a player to respond to a trade deal and returns the dialog instance.
	 * @param deal The TradeDeal instance to respond to.
	 * @return The RespondDialog instance for the trade response.
	 */
	public RespondDialog openRespondDialog(TradeDeal deal) {
        GUIRespondDialog dialog = new GUIRespondDialog();
        dialog.setDeal(deal);
        dialog.show();
        return dialog;
    }

    @SuppressWarnings("deprecation")
	/**
	 * Opens the trade dialog window for the player to initiate a trade and returns the dialog instance.
	 * @return The TradeDialog instance for initiating trades.
	 */
	public TradeDialog openTradeDialog() {
        GUITradeDialog dialog = new GUITradeDialog(this);
        dialog.show();
        return dialog;
    }
	
	/**
	 * Returns the GUI cell component associated with the specified board cell index.
	 * @param index The index of the board cell to query.
	 * @return The GUICell corresponding to the given index or null if not found.
	 */
	private GUICell queryCell(int index) {
		Cell cell = GameMaster.instance().getGameBoard().getCell(index);
		for(int x = 0; x < guiCells.size(); x++) {
			GUICell guiCell = guiCells.get(x);
			if(guiCell.getCell() == cell) return guiCell;
		}
		return null;
	}

    /**
     * Enables or disables the 'Buy House' button for the current player.
     * @param b Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setBuyHouseEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setBuyHouseEnabled(b);
    }

    /**
     * Enables or disables the 'Draw Card' button for the current player.
     * @param b Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setDrawCardEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setDrawCardEnabled(b);
    }

    /**
     * Enables or disables the 'End Turn' button for the current player.
     * @param enabled Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setEndTurnEnabled(boolean enabled) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setEndTurnEnabled(enabled);
    }

    /**
     * Enables or disables the 'Get Out of Jail' button for the current player.
     * @param b Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setGetOutOfJailEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setGetOutOfJailEnabled(b);
    }

    /**
     * Enables or disables the 'Purchase Property' button for the current player.
     * @param enabled Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setPurchasePropertyEnabled(boolean enabled) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setPurchasePropertyEnabled(enabled);
    }

    /**
     * Enables or disables the 'Roll Dice' button for the current player.
     * @param b Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setRollDiceEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setRollDiceEnabled(b);
    }

    /**
     * Enables or disables the 'Trade' button for the specified player index.
     * @param index The player index whose trade button is being enabled or disabled.
     * @param b Flag indicating whether to enable (true) or disable (false) the button.
     */
    public void setTradeEnabled(int index, boolean b) {
        playerPanels[index].setTradeEnabled(b);
    }
	
	/**
	 * Configures the main window panels and cells according to the given game board layout.
	 * @param board The GameBoard instance containing the board layout and cells.
	 */
	public void setupGameBoard(GameBoard board) {
		Dimension dimension = GameBoardUtil.calculateDimension(board.getCellNumber());
		northPanel.setLayout(new GridLayout(1, dimension.width + 2));
		southPanel.setLayout(new GridLayout(1, dimension.width + 2));
		westPanel.setLayout(new GridLayout(dimension.height, 1));
		eastPanel.setLayout(new GridLayout(dimension.height, 1));
		addCells(northPanel, GameBoardUtil.getNorthCells(board));
		addCells(southPanel, GameBoardUtil.getSouthCells(board));
		addCells(eastPanel, GameBoardUtil.getEastCells(board));
		addCells(westPanel, GameBoardUtil.getWestCells(board));
		buildPlayerPanels();
	}

    @SuppressWarnings("deprecation")
	/**
	 * Displays a dialog to allow the specified player to buy houses.
	 * @param currentPlayer The Player instance attempting to buy houses.
	 */
	public void showBuyHouseDialog(Player currentPlayer) {
        BuyHouseDialog dialog = new BuyHouseDialog(currentPlayer);
        dialog.show();
    }

    /**
     * Displays a message dialog with the given text to the user.
     * @param msg The message string to display.
     */
    public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
    }

	/**
	 * Displays a dialog for utility dice roll and returns the result value.
	 * @return The integer result from the utility dice roll.
	 */
	public int showUtilDiceRoll() {
		return UtilDiceRoll.showDialog();
	}

	/**
	 * Initializes the game by placing all players at the starting board position.
	 */
	public void startGame() {
		int numberOfPlayers = GameMaster.instance().getNumberOfPlayers();
		for(int i = 0; i < numberOfPlayers; i++) {
			movePlayer(i, 0, 0);
		}
	}

	/**
	 * Updates the display information for all player panels and all GUI cells on the board.
	 */
	public void update() {
		for(int i = 0; i < playerPanels.length; i++) {
			playerPanels[i].displayInfo();
		}
		for(int j = 0; j < guiCells.size(); j++ ) {
			GUICell cell = guiCells.get(j);
			cell.displayInfo();
		}
	}
}
