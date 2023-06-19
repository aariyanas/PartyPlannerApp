package persistence;

import model.*;
import org.json.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Represents a reader that reads data saved by the user to Json and reloads it into the application at its current state
public class JsonReader {
    private String source;

    //EFFECTS: constructs a reader to read data from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads party from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Party readParty() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseParty(jsonObject);
    }

    // EFFECTS: parses workroom from JSON object and returns it
    public Party parseParty(JSONObject jsonObject) {
        String occasionForParty = jsonObject.getString("occasion for party");
        String theme = jsonObject.getString("party theme");
        String typeOfParty = jsonObject.getString("type of party");
        JSONArray guestList = jsonObject.getJSONArray("guest list");
        JSONArray decorList = jsonObject.getJSONArray("decor list");
        JSONArray foodList = jsonObject.getJSONArray("food list");
        Party party = new Party(occasionForParty, typeOfParty, theme);
        addPartyGuests(party, jsonObject);
        addPartyDecor(party, jsonObject);
        addPartyFoods(party, jsonObject);
        return party;
    }

    // MODIFIES: party
    // EFFECTS: parses a guest from JSON object and adds them to party
    private void addGuestToGuestList(Party party, JSONObject jsonThing) {
        String firstName = jsonThing.getString("first name");
        String lastName = jsonThing.getString("last name");
        int age = jsonThing.getInt("age");
        Guest guest = new Guest(firstName, lastName, age);
        party.addGuest(guest);
    }

    // MODIFIES: party
    // EFFECTS: parses guests from JSON object and adds them to party
    private void addPartyGuests(Party party, JSONObject objectOfJson) {
        JSONArray jsonArray = objectOfJson.getJSONArray("guest list");
        for (Object jsonObject : jsonArray) {
            JSONObject nextGuest = (JSONObject) jsonObject;
            addGuestToGuestList(party, nextGuest);
        }
    }

    // MODIFIES: party
    // EFFECTS: parses a decoration from JSON object and adds it to party
    private void addDecorationToDecorList(Party party, JSONObject jsonThing) {
        String decorItemName = jsonThing.getString("decor item name");
        String decorItemColour = jsonThing.getString("decor item colour");
        int decorItemQuantity = jsonThing.getInt("decor item quantity");
        Decoration decoration = new Decoration(decorItemName, decorItemColour, decorItemQuantity);
        party.addDecor(decoration);
    }

    // MODIFIES: party
    // EFFECTS: parses a food item from JSON object and adds it to party
    private void addFoodToFoodList(Party party, JSONObject jsonThing) {
        String foodItemCategory = jsonThing.getString("food item category");
        String foodItemName = jsonThing.getString("food item name");
        Boolean hasBeenPurchased = jsonThing.getBoolean("purchase status");
        Boolean isNeeded = jsonThing.getBoolean("food is needed");
        Food food = new Food(foodItemCategory, foodItemName, hasBeenPurchased);
        party.addFood(food);
    }

    // MODIFIES: party
    // EFFECTS: parses decorations from JSON object and adds them to party
    private void addPartyDecor(Party party, JSONObject objectOfJson) {
        JSONArray jsonArray = objectOfJson.getJSONArray("decor list");
        for (Object jsonObject : jsonArray) {
            JSONObject nextDecoration = (JSONObject) jsonObject;
            addDecorationToDecorList(party, nextDecoration);
        }
    }

    // MODIFIES: party
    // EFFECTS: parses foods from JSON object and adds them to party
    private void addPartyFoods(Party party, JSONObject objectOfJson) {
        JSONArray jsonArray = objectOfJson.getJSONArray("food list");
        for (Object jsonObject : jsonArray) {
            JSONObject nextFood = (JSONObject) jsonObject;
            addFoodToFoodList(party, nextFood);
        }
    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
}