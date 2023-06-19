package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//unit tests for party class
public class PartyTest {
    Party p1;
    Party p2;

    Guest g1;
    Guest g2;

    Decoration d1;
    Decoration d2;

    Food f1;
    Food f2;

    @BeforeEach
    void runBefore() {
        g1 = new Guest("Kyle", "Walker", 12);
        g2 = new Guest("Sarah", "Miles", 27);

        p1 = new Party("Kyle's 15th birthday", "birthday", "Spiderman");
        p2 = new Party("Carl's promotion", "work", "Gucci");

        d1 = new Decoration("banner", "silver", 1);
        d2 = new Decoration("streamer rolls", "blue", 2);

        f1 = new Food("main", "pizza", false);
        f2 = new Food("snack", "pretzels", true);
    }

    @Test
    public void testGetOccasionForParty() {
        assertEquals("Kyle's 15th birthday", p1.getOccasionForParty());
        assertEquals("Carl's promotion", p2.getOccasionForParty());
    }

    @Test
    public void testGetTypeOfParty() {
        assertEquals("birthday", p1.getTypeOfParty());
        assertEquals("work", p2.getTypeOfParty());
    }

    @Test
    public void testGetTheme() {
        assertEquals("Spiderman", p1.getTheme());
        assertEquals("Gucci", p2.getTheme());
    }

    @Test
    public void testGetGuestList() {
        assertEquals(p1.guestList, p1.getGuestList());
        assertEquals(p2.guestList, p2.getGuestList());
    }

    @Test
    public void testGetFoodList() {
        assertEquals(p1.foodList, p1.getFoodList());
        assertEquals(p2.foodList, p2.getFoodList());
    }

    @Test
    public void testGetDecorList() {
        assertEquals(p1.decorList, p1.getDecorList());
        assertEquals(p2.decorList, p2.getDecorList());
    }

    @Test
    public void testAddGuest() {
        assertTrue(p1.addGuest(g1));
        assertTrue(p2.addGuest(g1));
        assertFalse(p1.addGuest(g1));
        assertFalse(p2.addGuest(g1));
        assertTrue(p2.addGuest(g2));
        assertEquals(1, p1.getNumberOfGuestsAttending());
        assertEquals(2, p2.getNumberOfGuestsAttending());
    }

    @Test
    public void testAddDecor() {
        assertTrue(p1.addDecor(d1));
        assertTrue(p2.addDecor(d2));
        assertFalse(p2.addDecor(d2));
    }

    @Test
    public void testAddFood() {
        assertTrue(p1.addFood(f1));
        assertFalse(p1.addFood(f1));
    }

    @Test
    public void testRemoveGuest() {
        assertFalse(p2.removeGuest(g1));
        p1.addGuest(g2);
        assertTrue(p1.removeGuest(g2));
        assertEquals(0, p2.getNumberOfGuestsAttending());
        p1.addGuest(g2);
        assertEquals(1, p1.getNumberOfGuestsAttending());
        p1.removeGuest(g2);
        assertEquals(0, p1.getNumberOfGuestsAttending());
    }

    @Test
    public void testRemoveDecor() {
        assertFalse(p1.removeDecor(d1));
        p2.addDecor(d2);
        assertTrue(p2.removeDecor(d2));
    }

    @Test
    public void testRemoveFood() {
        p1.addFood(f1);
        assertTrue(p1.removeFood(f1));
        assertFalse(p1.removeFood(f2));
    }

    @Test
    public void testToString() {
        assertEquals("Kyle's 15th birthday party - Spiderman themed", p1.toString());
        assertEquals("Carl's promotion party - Gucci themed", p2.toString());
    }

    @Test
    public void testGuestsToList() {
        p1.addGuest(g1);
        List<String> guestToList = new ArrayList<String>(Arrays.asList(g1.toString()));
        assertEquals(guestToList, p1.guestsToList());
    }

    @Test
    public void testFoodsToList() {
        p1.addFood(f1);
        List<String> foodToList = new ArrayList<String>(Arrays.asList(f1.toString()));
        assertEquals(foodToList, p1.foodsToList());
    }

    @Test
    public void testDecorToList() {
        p1.addDecor(d1);
        List<String> decorToList = new ArrayList<String>(Arrays.asList(d1.toString()));
        assertEquals(decorToList, p1.decorToList());
    }

}
