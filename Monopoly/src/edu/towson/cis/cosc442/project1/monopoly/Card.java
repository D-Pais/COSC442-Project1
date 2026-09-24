package edu.towson.cis.cosc442.project1.monopoly;

public abstract class Card {

    public static int TYPE_CHANCE = 1;
    public static int TYPE_CC = 2;

    /**
     * Returns the label text of the card.
     * @return The label text of the card.
     */
    public abstract String getLabel();
    /**
     * Executes the specific action associated with the card.
     */
    public abstract void applyAction();
    /**
     * Retrieves the type identifier of the card (e.g., Chance or Community Chest).
     * @return An integer representing the card type.
     */
    public abstract int getCardType();
}
