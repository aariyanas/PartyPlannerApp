package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//unit tests for json writer
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFileName(){
        try{
            Party party = new Party("CP$C210 midturmzz", "misc", "CPSC210");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException should have been caught");
        } catch (IOException ioe){
            //expected behaviour
        }
    }

    @Test
    void testWriterEmptyParty(){
        try{
            Party party = new Party("Geoffrey Tien's Anniversary", "anniversary",
                    "CPSC121");
            JsonWriter writer = new JsonWriter("./data/emptypartyCOMPARETOSAMPLE.json");
            writer.open();
            writer.writeParty(party);
            writer.close();

            JsonReader reader = new JsonReader("./data/emptypartyCOMPARETOSAMPLE.json");
            party = reader.readParty();
            assertEquals("Geoffrey Tien's Anniversary", party.getOccasionForParty());
            assertEquals("CPSC121", party.getTheme());
            assertEquals("anniversary", party.getTypeOfParty());

            List emptyArrayList = new ArrayList();
            assertEquals(emptyArrayList, party.getDecorList());
            assertEquals(emptyArrayList, party.getFoodList());
            assertEquals(emptyArrayList, party.getGuestList());

            //expected behaviour

        }catch (IOException ioe){
            fail("caught IOException when shouldn't have");
        }
    }

    @Test
    void testWriterParty(){
        try{
            Party party = new Party("Felix Grund's birthday", "birthday", "CPSC210");

            //(isAttending == true) is implied for meghanAllen and elisaBaniassad
            //adding guests to guest list
            party.addGuest(new Guest("Meghan", "Allen", 0));
            party.addGuest(new Guest("Elisa", "Baniassad", 45));

            //(itemIsBeingUsed == true) is implied for streamers
            //adding decoration to decor list
            party.addDecor(new Decoration("Streamers", "green", 39));

            //(isNeeded == true) is implied for pizza and cake
            //adding foods to food list
            party.addFood(new Food("Main", "pizza", false));
            party.addFood(new Food("Dessert", "cake", true));

            JsonWriter writer = new JsonWriter("./data/partyCOMPARETOSAMPLE.json");
            writer.open();
            writer.writeParty(party);
            writer.close();

            JsonReader reader = new JsonReader("./data/partyCOMPARETOSAMPLE.json");
            party = reader.readParty();
            assertEquals("Felix Grund's birthday", party.getOccasionForParty());
            assertEquals("CPSC210", party.getTheme());
            assertEquals("birthday", party.getTypeOfParty());

            Guest meghanAllen = new Guest("Meghan", "Allen", 0);
            checkGuest("Meghan", "Allen", 0, true, meghanAllen);
            Guest elisaBaniassad = new Guest("Elisa", "Baniassad", 45);
            checkGuest("Elisa", "Baniassad", 45, true, elisaBaniassad);

            Decoration streamers = new Decoration("Streamers", "green", 39);
            checkDecoration("Streamers", "green", 39, true,
                    streamers);

            Food pizza = (new Food("Main", "pizza", false));
            checkFood("Main", "pizza", false, true, pizza);
            Food cake = (new Food("Dessert", "cake", true));
            checkFood("Dessert", "cake", true, true, cake);

            assertTrue(party.getGuestList().contains(meghanAllen));
            assertTrue(party.getGuestList().contains(elisaBaniassad));
            assertTrue(party.getDecorList().contains(streamers));
            assertTrue(party.getFoodList().contains(pizza));
            assertTrue(party.getFoodList().contains(cake));

            //expected behaviour

        } catch (IOException ioe) {
            fail("caught IOException when shouldn't have");
        }
    }
}