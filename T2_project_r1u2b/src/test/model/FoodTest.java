package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

//unit tests for food class
public class FoodTest {
    Food f1;
    Food f2;
    Food f1Duplicate;

    @BeforeEach
    void runBefore() {
        f1 = new Food("main", "pizza", false);
        f1Duplicate = new Food("main", "pizza", false);
        f2 = new Food("snack", "pretzels", true);
    }

    @Test
    public void testToString() {
        assertEquals("main - pizza --> not yet purchased", f1.toString());
        assertEquals("snack - pretzels --> purchased", f2.toString());
    }

    @Test
    public void testEquals(){
        assertTrue(f1.equals(f1Duplicate));
        assertFalse(f1.equals(f2));
        assertFalse(f1.equals(null));
        assertFalse(f1.equals(new ArrayList<>()));
    }

    @Test
    public void testGetFoodItemName(){
        assertEquals("pizza", f1.getFoodItemName());
        assertEquals("pretzels", f2.getFoodItemName());
    }

    @Test
    public void testGetFoodItemCategory(){
        assertEquals("main", f1.getFoodItemCategory());
        assertEquals("snack", f2.getFoodItemCategory());
    }

    @Test
    public void testGetHasBeenPurchased(){
        assertFalse(f1.getHasBeenPurchased());
        assertTrue(f2.getHasBeenPurchased());
    }

    @Test
    public void testHashCode(){
        assertEquals(Objects.hash(f1.getFoodItemName()), f1.hashCode());
        assertEquals(Objects.hash(f2.getFoodItemName()), f2.hashCode());
    }

    @Test
    public void testSetHasBeenPurchasedToTrue(){
        f1.setHasBeenPurchasedToTrue();
        assertTrue(f1.getHasBeenPurchased());
    }

    @Test
    public void testSetIsNeeded(){
        f1.setIsNeeded(true);
        f2.setIsNeeded(false);
        assertFalse(f2.getIsNeeded());
        assertTrue(f1.getIsNeeded());
    }
}