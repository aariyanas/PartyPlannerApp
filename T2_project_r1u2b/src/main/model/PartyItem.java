package model;

//represents a generic party item (i.e. - decoration, food, guest, or party)
public abstract class PartyItem {

    //implemented in the food, guest, decoration and party classes
    //EFFECTS: converts a party item to a string
    //MODIFIES: this
    public abstract String toString();
}