package edu.towson.cis.cosc442.project1.monopoly;


public class JailCard extends Card {
    int type;
    
    /**
     * Constructs a JailCard with a specified card type identifier.
     * @param cardType the integer identifier representing the type of this JailCard
     */
    public JailCard(int cardType) {
        type = cardType;
    }

    /**
     * Executes the action of sending the current player directly to jail without collecting $200.
     */
    public void applyAction() {
        Player currentPlayer = GameMaster.instance().getCurrentPlayer();
		GameMaster.instance().getGameBoard().queryCell("Jail");
		GameMaster.instance().sendToJail(currentPlayer);
    }

    /**
     * Returns the integer identifier representing this card's type.
     * @return the card type integer
     */
    public int getCardType() {
        return type;
    }

    /**
     * Provides a descriptive label for the JailCard's effect.
     * @return a string describing the card's action regarding immediate jail placement
     */
    public String getLabel() {
        return "Go to Jail immediately without collecting" +
        		" $200 when passing the GO cell";
    }
}
