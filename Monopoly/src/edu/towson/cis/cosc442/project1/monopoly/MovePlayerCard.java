package edu.towson.cis.cosc442.project1.monopoly;


public class MovePlayerCard extends Card {
    
    private String destination;
    private int type;

    /**
     * Constructs a MovePlayerCard with a specified destination and card type.
     * @param destination the name of the destination cell to move the player to
     * @param cardType an integer representing the type of this card
     */
    public MovePlayerCard(String destination, int cardType) {
        this.destination = destination;
        this.type = cardType;
    }

    /**
     * Moves the current player to the destination cell specified by this card on the game board.
     */
    public void applyAction() {
        Player currentPlayer = GameMaster.instance().getCurrentPlayer();
        Cell currentPosition = currentPlayer.getPosition();
        int newCell = GameMaster.instance().getGameBoard().queryCellIndex(destination);
        int currentCell = GameMaster.instance().getGameBoard().queryCellIndex(currentPosition.getName());
        int diceValue = 0;
        if(currentCell > newCell) {
            diceValue = (GameMaster.instance().getGameBoard().getCellNumber() + 
                    (newCell - currentCell));
        }
        else if(currentCell <= newCell) {
            diceValue = newCell - currentCell;
        }
        System.out.println(diceValue);
        GameMaster.instance().movePlayer(currentPlayer, diceValue);
    }

    /**
     * Returns the integer type identifier of this card.
     * @return the card type as an integer
     */
    public int getCardType() {
        return type;
    }

    /**
     * Provides a descriptive label indicating the destination of this card.
     * @return a string label in the format 'Go to ' followed by the destination name
     */
    public String getLabel() {
        return "Go to " + destination;
    }

}
