package edu.towson.cis.cosc442.project1.monopoly;

public class MoneyCard extends Card {
    private int amount;
    private int cardType;
    
    private String label;
    
    /**
     * Constructs a MoneyCard with a label, an amount of money, and a card type identifier.
     * @param label the descriptive label of the card
     * @param amount the amount of money associated with the card
     * @param cardType the type identifier of the card
     */
    public MoneyCard(String label, int amount, int cardType){
        this.label = label;
        this.amount = amount;
        this.cardType = cardType;
    }

    /**
     * Applies the card's monetary effect to the current player's money balance.
     */
    public void applyAction() {
        Player currentPlayer = GameMaster.instance().getCurrentPlayer();
		currentPlayer.setMoney(currentPlayer.getMoney() + amount);
    }

    /**
     * Returns the integer identifier representing the card's type.
     * @return the card type as an integer
     */
    public int getCardType() {
        return cardType;
    }

    /**
     * Returns the label associated with the card.
     * @return the card's label as a string
     */
    public String getLabel() {
        return label;
    }
}
