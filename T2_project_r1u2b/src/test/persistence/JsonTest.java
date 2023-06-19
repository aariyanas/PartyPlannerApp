package persistence;

import model.*;
import static org.junit.jupiter.api.Assertions.*;

//sub test class for Json reader and writer
public class JsonTest {

    protected void checkGuest(String firstName, String lastName, int age, Boolean isAttending, Guest guest){
        assertEquals(firstName, guest.getFirstName());
        assertEquals(lastName, guest.getLastName());
        assertEquals(age, guest.getAge());
        assertEquals(isAttending, guest.getIsAttending());
    }

    protected void checkDecoration(String decorItemName, String decorItemColour, int decorItemQuantity,
                                   Boolean itemIsBeingUsed, Decoration decoration){
        assertEquals(decorItemName, decoration.getDecorItemName());
        assertEquals(decorItemColour, decoration.getDecorItemColour());
        assertEquals(decorItemQuantity, decoration.getDecorItemQuantity());
        assertEquals(itemIsBeingUsed, decoration.getItemIsBeingUsed());
    }

    protected void checkFood(String foodItemCategory, String foodItemName, Boolean hasBeenPurchased, Boolean isNeeded,
                             Food food){
        assertEquals(foodItemCategory, food.getFoodItemCategory());
        assertEquals(foodItemName, food.getFoodItemName());
        assertEquals(hasBeenPurchased, food.getHasBeenPurchased());
        assertEquals(isNeeded, food.getIsNeeded());
    }
}
