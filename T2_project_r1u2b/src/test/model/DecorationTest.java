package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

//unit tests for decoration class
public class DecorationTest {
    Decoration d1;
    Decoration d1Duplicate;
    Decoration d2;
    Decoration d3;

    @BeforeEach
    void runBefore() {
        d1 = new Decoration("banner", "silver", 1);
        d1Duplicate = new Decoration("banner", "silver", 1);
        d2 = new Decoration("streamer rolls", "blue", 2);
        d3 = new Decoration("balloons", "red", 867);
    }

    @Test
    public void testEquals(){
        assertTrue(d1.equals(d1Duplicate));
        assertFalse(d1.equals(d2));
        assertFalse(d1.equals(null));
        assertFalse(d1.equals(new ArrayList<>()));
    }

    @Test
    public void testToString() {
        assertEquals(("1 silver banner"), d1.toString());
        assertEquals(("2 blue streamer rolls"), d2.toString());
        assertEquals(("867 red balloons"), d3.toString());
    }

    @Test
    public void testGetDecorItemName(){
        assertEquals("banner", d1.getDecorItemName());
        assertEquals("streamer rolls", d2.getDecorItemName());
        assertEquals("balloons", d3.getDecorItemName());
    }

    @Test
    public void testGetDecorItemQuantity(){
        assertEquals(1, d1.getDecorItemQuantity());
        assertEquals(2, d2.getDecorItemQuantity());
        assertEquals(867, d3.getDecorItemQuantity());
    }

    @Test
    public void testGetItemIsBeingUsed(){
        d2.setItemIsBeingUsed(false);
        assertFalse(d2.getItemIsBeingUsed());
        assertTrue(d1.getItemIsBeingUsed());
        assertTrue(d3.getItemIsBeingUsed());
    }

    @Test
    public void testSetDecorItemColour(){
        d1.setDecorItemColour("green");
        d2.setDecorItemColour("red");
        d3.setDecorItemColour("yellow");
        assertEquals("green", d1.getDecorItemColour());
        assertEquals("red", d2.getDecorItemColour());
        assertEquals("yellow", d3.getDecorItemColour());
    }

    @Test
    public void testHashCode(){
        assertEquals(Objects.hash(d1.getDecorItemName()), d1.hashCode());
        assertEquals(Objects.hash(d2.getDecorItemName()), d2.hashCode());
        assertEquals(Objects.hash(d3.getDecorItemName()), d3.hashCode());
    }
}
