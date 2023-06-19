package model;

import org.json.JSONObject;
import persistence.Writable;
import java.util.Objects;

//Represents a decoration that is a party item, with a specific name, colour, quantity and if it is being used or not
public class Decoration extends PartyItem implements Writable {
    private String decorItemName;
    private String decorItemColour;
    private int decorItemQuantity;
    private Boolean itemIsBeingUsed;

    //EFFECTS: constructs a decoration with a name, colour, quantity, and assumes it is being used for the party, and
    //         logs the decoration in the event log
    public Decoration(String decorItemName, String decorItemColour, int decorItemQuantity) {
        this.decorItemName = decorItemName;
        this.decorItemColour = decorItemColour;
        this.decorItemQuantity = decorItemQuantity;
        itemIsBeingUsed = true;
        EventLog.getInstance().logEvent(new Event("New decoration was created."));
    }

    //EFFECTS: returns the name of the decoration item
    public String getDecorItemName() {
        return decorItemName;
    }

    //EFFECTS: returns the colour of the decoration item
    public String getDecorItemColour() {
        return decorItemColour;
    }

    //EFFECTS: returns the quantity of the decoration item
    public int getDecorItemQuantity() {
        return decorItemQuantity;
    }

    //EFFECTS: returns true if the decoration item is being used, false otherwise
    public Boolean getItemIsBeingUsed() {
        return itemIsBeingUsed;
    }

    //EFFECTS: sets the decoration item's colour to THIS colour
    //MODIFIES: this
    public void setDecorItemColour(String decorItemColour) {
        this.decorItemColour = decorItemColour;
    }

    //EFFECTS: decides whether the decoration item is being used based on THIS boolean value
    //MODIFIES: this
    public void setItemIsBeingUsed(boolean b) {
        itemIsBeingUsed = b;
    }

    //overrides toString in PartyItem abstract class
    @Override
    public String toString() {
        String theDecoration = String.valueOf(decorItemQuantity) + " " + decorItemColour + " " + decorItemName;
        return theDecoration;
    }

    //EFFECTS: compares two decor items to check if they are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Decoration that = (Decoration) o;
        return Objects.equals(decorItemName, that.decorItemName);
    }

    //EFFECTS: turns a decor item name into HashCode
    //MODIFIES: this
    @Override
    public int hashCode() {
        return Objects.hash(decorItemName);
    }

    //Overrides toJson in Writable interface
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("decor item name", decorItemName);
        json.put("decor item colour", decorItemColour);
        json.put("decor item quantity", decorItemQuantity);
        json.put("item is being used", itemIsBeingUsed);
        return json;
    }
}