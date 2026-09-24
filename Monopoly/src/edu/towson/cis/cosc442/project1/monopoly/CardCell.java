package edu.towson.cis.cosc442.project1.monopoly;

public class CardCell extends Cell {
    private int type;
    
    /**
     * Constructs a CardCell with the specified type and name.
     * @param type the integer type representing the card category
     * @param name the name of the card cell
     */
    public CardCell(int type, String name) {
        setName(name);
        this.type = type;
    }
    
    /**
     * Performs the action associated with this CardCell when played.
     */
    public void playAction() {
    }
    
    /**
     * Returns the type of this CardCell.
     * @return the integer type of the card cell
     */
    public int getType() {
        return type;
    }
}
