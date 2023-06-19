package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

//unit tests for guest class
public class GuestTest {
    Guest g1;
    Guest g1Duplicate;
    Guest g2;
    Guest g3;

    @BeforeEach
    void runBefore(){
        g1 = new Guest("Kyle", "Walker", 12);
        g1Duplicate = new Guest("Kyle", "Walker", 12);
        g2 = new Guest("Sarah", "Miles", 27);
        g3 = new Guest("Meghan", "Allen");
    }

    @Test
    public void testEquals(){
        assertFalse(g2.equals(g1));
        assertTrue(g1.equals(g1));
        assertFalse(g1.equals(null));
        assertFalse(g1.equals(new ArrayList<>()));
    }

    @Test
    public void testToString(){
        assertEquals("Kyle Walker, age 12 - attending", g1.toString());
        assertEquals("Sarah Miles, age 27 - attending", g2.toString());
    }

    @Test
    public void testToStringNoAge(){
        assertEquals("Meghan Allen - attending", g3.toStringNoAge());
    }

    @Test
    public void testGetFirstName(){
        assertEquals("Kyle", g1.getFirstName());
        assertEquals("Sarah", g2.getFirstName());
    }

    @Test
    public void testGetLastName(){
        assertEquals("Walker", g1.getLastName());
        assertEquals("Miles", g2.getLastName());
    }

    @Test
    public void testGetAge(){
        assertEquals(12, g1.getAge());
        assertEquals(27, g2.getAge());
    }

    @Test
    public void testSetIsAttending(){
        g2.setIsAttending(false);
        assertFalse(g2.getIsAttending());
        g1.setIsAttending(true);
        assertTrue(g1.getIsAttending());
    }

    @Test
    public void testIsGuestAttending(){
        assertEquals("attending", g1.isGuestAttending(g1.getIsAttending()));
        g2.setIsAttending(false);
        assertEquals("not attending", g2.isGuestAttending(g2.getIsAttending()));
    }

    @Test
    public void testHashCode(){
        assertEquals(Objects.hash(g2.getFirstName()), g2.hashCode());
        assertEquals(Objects.hash(g1.getFirstName()), g1.hashCode());

    }
}
