package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.List;

//Represents a party with a specific name, type, theme, and guest list, food list and decor list
public class Party extends PartyItem implements Writable {
    String occasionForParty;
    String typeOfParty;
    String theme;
    ArrayList<Guest> guestList;
    ArrayList<Food> foodList;
    ArrayList<Decoration> decorList;

    //EFFECTS: constructs a party with a specific occasion, type and theme (if applicable) and initializes new empty
    //         guest, food and decor lists, and logs the party into the event log
    public Party(String occasionForParty, String typeOfParty, String theme) {
        this.occasionForParty = occasionForParty;
        this.theme = theme;
        this.typeOfParty = typeOfParty;
        guestList = new ArrayList<>();
        foodList = new ArrayList<>();
        decorList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New party " + occasionForParty + " party was created."));
    }

    //EFFECTS: returns the occasion of the party
    public String getOccasionForParty() {
        return occasionForParty;
    }

    //EFFECTS: returns the type of party
    public String getTypeOfParty() {
        return typeOfParty;
    }

    //EFFECTS: returns the theme of the party
    public String getTheme() {
        return theme;
    }

    //EFFECTS: returns the number of guests attending the party
    public int getNumberOfGuestsAttending() {
        return guestList.size();
    }

    //EFFECTS: adds a guest to the guest list and log sit into the event log
    //MODIFIES: this
    //REQUIRES: !guestList.contains(g)
    public Boolean addGuest(Guest g) {
        if (guestList.contains(g)) {
            return false;
        }
        EventLog.getInstance().logEvent(new Event(g.getFirstName() + " " + g.getLastName()
                + " was added to the guest list."));
        return guestList.add(g);
    }

    //EFFECTS: adds a decoration to the decor list and logs it into the event log
    //MODIFIES: this
    //REQUIRES: !decorList.contains(d)
    public Boolean addDecor(Decoration d) {
        if (decorList.contains(d)) {
            return false;
        }
        if (d.getDecorItemName().substring(d.getDecorItemName().length() - 1).equals("s")) {
            EventLog.getInstance().logEvent(new Event(d.getDecorItemName() + " were added to the decor list."));
        } else {
            EventLog.getInstance().logEvent(new Event(d.getDecorItemName() + " was added to the decor list."));
        }
        return decorList.add(d);
    }

    //EFFECTS: adds a food to the food list and logs it into the event log
    //MODIFIES: this
    //REQUIRES: !foodList.contains(f)
    public Boolean addFood(Food f) {
        if (foodList.contains(f)) {
            return false;
        }
        EventLog.getInstance().logEvent(new Event(f.getFoodItemName() + " was added to the food list."));
        return foodList.add(f);
    }

    //EFFECTS: removes a guest from the guest list and logs it into the event log
    //MODIFIES: this
    //REQUIRES: guestList.contains(g)
    public Boolean removeGuest(Guest g) {
        EventLog.getInstance().logEvent(new Event(g.getFirstName() + " " + g.getLastName()
                + " was removed from the guest list."));
        return guestList.remove(g);
    }

    //EFFECTS: removes a decoration from the decor list and logs it into the event log
    //MODIFIES: this
    //REQUIRES: decorList.contains(d)
    public Boolean removeDecor(Decoration d) {
        if (d.getDecorItemName().substring(d.getDecorItemName().length() - 1).equals("s")) {
            EventLog.getInstance().logEvent(new Event(d.getDecorItemName() + " were removed from the decor list."));
        } else {
            EventLog.getInstance().logEvent(new Event(d.getDecorItemName() + " was removed from the decor list."));
        }
        return decorList.remove(d);
    }

    //EFFECTS: removes a food from the food list and logs it into the event log
    //MODIFIES: this
    //REQUIRES: foodList.contains(f)
    public Boolean removeFood(Food f) {
        EventLog.getInstance().logEvent(new Event(f.getFoodItemName() + " was removed from the food list."));
        return foodList.remove(f);
    }

    //EFFECTS: returns the current guest list of the party
    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    //EFFECTS: returns the current food list of the party
    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    //EFFECTS: returns the current decor list of the party
    public ArrayList<Decoration> getDecorList() {
        return decorList;
    }

    //overrides toString in PartyItem abstract class
    @Override
    public String toString() {
        return occasionForParty + " party - " + theme + " themed";
    }

    //Overrides toJson in Writable interface
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("occasion for party", occasionForParty);
        json.put("party theme", theme);
        json.put("type of party", typeOfParty);
        json.put("guest list", guestsToJson());
        json.put("decor list", decorationsToJson());
        json.put("food list", foodsToJson());
        return json;
    }

    //EFFECTS: returns guests for this party as a JSON array
    private JSONArray guestsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Guest g : guestList) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: returns decoration for this party as a JSON array
    private JSONArray decorationsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Decoration d : decorList) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: returns foods for this party as a JSON array
    private JSONArray foodsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Food f : foodList) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: converts the guest list from a list of guests to a list of string
    public List<String> guestsToList() {
        List<String> list = new ArrayList<>();
        for (Guest g : guestList) {
            list.add(g.toString());
        }
        return list;
    }

    //EFFECTS: converts the food list from a list of food to a list of string
    public List<String> foodsToList() {
        List<String> list = new ArrayList<>();
        for (Food f : foodList) {
            list.add(f.toString());
        }
        return list;
    }

    //EFFECTS: converts the decor list from a list of decorations to a list of string
    public List<String> decorToList() {
        List<String> list = new ArrayList<>();
        for (Decoration d : decorList) {
            list.add(d.toString());
        }
        return list;
    }
}