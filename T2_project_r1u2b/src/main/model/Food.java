package model;

import org.json.JSONObject;
import persistence.Writable;
import java.util.Objects;

//Represents a food that is a party item, with a specific name, category, whether or not it has been purchased, and
//whether or not the food is needed
public class Food extends PartyItem implements Writable {

    private String foodItemName;
    private String foodItemCategory;
    private Boolean hasBeenPurchased;
    private Boolean isNeeded;

    //EFFECTS: constructs a food with a specific category, name, whether or not it has been purchased, assumes the
    //         food is needed, and logs the item into the event logger
    public Food(String foodItemCategory, String foodItemName, Boolean hasBeenPurchased) {
        this.foodItemCategory = foodItemCategory;
        this.foodItemName = foodItemName;
        this.hasBeenPurchased = hasBeenPurchased;   //true if food has been purchased, false otherwise
        isNeeded = true;
        EventLog.getInstance().logEvent(new Event("New food item was created."));
    }

    //EFFECTS: compares a food item to another
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return Objects.equals(foodItemName, food.foodItemName);
    }

    //EFFECTS: turns a food item name into HashCode
    //MODIFIES: this
    @Override
    public int hashCode() {
        return Objects.hash(foodItemName);
    }

    //EFFECTS: returns the name of the food item
    public String getFoodItemName() {
        return foodItemName;
    }

    //EFFECTS: returns the category of the food item
    public String getFoodItemCategory() {
        return foodItemCategory;
    }

    //EFFECTS: returns true if the food has been purchased already, false otherwise
    public Boolean getHasBeenPurchased() {
        return hasBeenPurchased;
    }

    //EFFECTS: decides whether the food has been purchased based on THIS boolean value
    //MODIFIES: this
    public void setHasBeenPurchasedToTrue() {
        hasBeenPurchased = true;
    }

    //EFFECTS: returns true if the food item is needed, false otherwise
    public Boolean getIsNeeded() {
        return isNeeded;
    }

    //EFFECTS: returns "not yet purchased" if food has not been purchased, return "purchased" otherwise
    //MODIFIES: this
    public String hasFoodBeenPurchased(Boolean hasBeenPurchased) {
        if (hasBeenPurchased == false) {
            return "not yet purchased";
        } else {
            return "purchased";
        }
    }

    //EFFECTS: decides whether the food item is needed based on THIS boolean value
    //MODIFIES: this
    public void setIsNeeded(boolean b) {
        isNeeded = b;
    }

    //overrides toString in PartyItem abstract class
    @Override
    public String toString() {
        String theFood = foodItemCategory + " - " + foodItemName + " --> " + hasFoodBeenPurchased(hasBeenPurchased);
        return theFood;
    }

    //Overrides toJson in Writable interface
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("food item category", foodItemCategory);
        json.put("food item name", foodItemName);
        json.put("purchase status", hasBeenPurchased);
        json.put("food is needed", isNeeded);
        return json;
    }
}


