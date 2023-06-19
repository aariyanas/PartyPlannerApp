package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


//unit tests for json reader
public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFileName() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Party party = reader.readParty();
            fail("IOException should have been caught");
        } catch (IOException ioe) {
            //expected behaviour
        }
    }

    @Test
    void testWriterEmptyParty(){
        JsonReader reader = new JsonReader("./data/emptypartyCOMPARETOSAMPLE.json");
        try{
            Party party = reader.readParty();
            assertEquals("Geoffrey Tien's Anniversary", party.getOccasionForParty());
            assertEquals("CPSC121", party.getTheme());
            assertEquals("anniversary", party.getTypeOfParty());

            List emptyArrayList = new ArrayList();
            assertEquals(emptyArrayList, party.getFoodList());
            assertEquals(emptyArrayList, party.getGuestList());
            assertEquals(emptyArrayList, party.getDecorList());

            //expected behaviour
        }catch (IOException ioe){
            fail("IOException was caught - couldn't read from file");
        }
    }

    @Test
    void testReaderParty(){
        JsonReader reader = new JsonReader("./data/partyCOMPARETOSAMPLE.json");
        try{
            Party party = reader.readParty();
            assertEquals("Felix Grund's birthday", party.getOccasionForParty());
            assertEquals("CPSC210", party.getTheme());
            assertEquals("birthday", party.getTypeOfParty());

            //(isAttending == true) is implied for meghanAllen and elisaBaniassad
            Guest meghanAllen = new Guest("Meghan", "Allen", 0);
            checkGuest("Meghan", "Allen", 0, true, meghanAllen);
            Guest elisaBaniassad = new Guest("Elisa", "Baniassad", 45);
            checkGuest("Elisa", "Baniassad", 45, true, elisaBaniassad);

            //(itemIsBeingUsed == true) is implied for streamers
            Decoration streamers = new Decoration("Streamers", "green", 39);
            checkDecoration("Streamers", "green", 39, true,
                    streamers);

            //(isNeeded == true) is implied for pizza and cake
            Food pizza = new Food("Main", "pizza", false);
            checkFood("Main", "pizza", false, true, pizza);
            Food cake = new Food("Dessert", "cake", true);
            checkFood("Dessert", "cake", true, true, cake);

            //expected behaviour

        } catch (IOException ioe){
            fail("IOException was caught - couldn't read from file");
        }
    }
}
