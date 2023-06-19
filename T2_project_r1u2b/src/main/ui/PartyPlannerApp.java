package ui;

import model.Decoration;
import model.Food;
import model.Guest;
import model.Party;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

//CONSOLE-BASED UI
//Represents a party planner app that takes in user input and allows user to create and plan a new party
//public class PartyPlannerApp {
//    private Food food;
//    private Decoration decoration;
//    private Guest guest;
//    private Party party;
//    private Scanner input;
//    private static final String JSON_PARTY_STORE = "./data/party.json";
//    private JsonWriter jsonPartyWriter;
//    private JsonReader jsonPartyReader;
//
//    //EFFECTS: constructs a new instance of the party planner app and runs it
//    public PartyPlannerApp() {
//        runPartyPlanner();
//    }
//
//    //EFFECTS: runs the party planner and processes the user input
//    private void runPartyPlanner() {
//        boolean continueRunning = true;
//        String command = null;
//
//        init();
//
//        while (continueRunning) {
//            displayOptions();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("quit party planner")) {
//                continueRunning = false;
//            } else {
//                processCommandGiven(command);
//            }
//        }
//        System.out.println("\nThank you for using the Party Planner App! Goodbye!");
//    }
//
//    //EFFECTS: processes the command given by the user
//    private void processCommandGiven(String command) {
//        if (command.equals("create new party")) {
//            doCreateNewParty();
//        } else if (command.equals("add guest to guest list")) {
//            doAddGuest();
//        } else if (command.equals("remove guest from guest list")) {
//            doRemoveGuest();
//        } else if (command.equals("add decoration to decor list")) {
//            doAddDecor();
//        } else if (command.equals("remove decoration from decor list")) {
//            doRemoveDecor();
//        } else if (command.equals("add food to food list")) {
//            doAddFood();
//        } else if (command.equals("remove food from food list")) {
//            doRemoveFood();
//        } else {
//            processCommandGiven2(command);
//        }
//    }
//
//    //EFFECTS: processes a subset of commands given by the user
//    public void processCommandGiven2(String command) {
//        if (command.equals("view food list")) {
//            doViewFoodList();
//        } else if (command.equals("view guest list")) {
//            doViewGuestList();
//        } else if (command.equals("view decor list")) {
//            doViewDecorList();
//        } else if (command.equals("save party")) {
//            doSaveParty();
//        } else if (command.equals("load party from file")) {
//            doLoadPartyFromFile();
//        } else {
//            System.out.println("Command invalid. Please select another option");
//        }
//    }
//
//    //EFFECTS: allows the user to initialize a new instance of a party
//    //MODIFIES: this
//    //REQUIRES: this method must only be called once, at the beginning, immediately after initial run of the app
//    private void doCreateNewParty() {
//        System.out.println("Ocassion of Party");
//        String occassionOfParty = input.next();
//        System.out.println("Theme of Party (type none if no theme)");
//        String themeOfParty = input.next();
//        System.out.println("Type of Party (e.g. birthday, anniversary, etc.)");
//        String typeOfParty = input.next();
//        party = new Party(occassionOfParty, typeOfParty, themeOfParty);
//        System.out.println(party.toString());
//    }
//
//    //EFFECTS: allows user to add a guest to the guest list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: !guestList.contains(guest)
//    private void doAddGuest() {
//        System.out.println("\nFirst name of guest");
//        String firstName = input.next();
//        System.out.println("\nLast name of guest");
//        String lastName = input.next();
//        System.out.println("\nAge of guest (type 0 if unknown)");
//        int age = input.nextInt();
//        guest = new Guest(firstName, lastName, age);
//        if (party.getGuestList().contains(guest)) {
//            System.out.println("Guest is already on the guest list");
//        } else {
//            party.getGuestList().add(guest);
//            System.out.println("Guest has been added to the guest list");
//        }
//    }
//
//    //EFFECTS: allows user to remove a guest from the guest list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: (g.getFirstName == firstName) && (guestList.contains(g))
//    private void doRemoveGuest() {
//        System.out.println("\nGuest's first name");
//        String firstName = input.next();
//        Guest dummyGuest = null;
//        for (Guest g : party.getGuestList()) {
//            if (g.getFirstName().equals(firstName)) {
//                dummyGuest = g;
//            }
//        }
//        if (party.removeGuest(dummyGuest)) {
//            System.out.println("Guest has been removed from the guest list");
//        } else {
//            System.out.println("Guest not available");
//        }
//    }
//
//    //EFFECTS: allows user to add a decoration to the decor list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: !decorList.contains(decoration)
//    private void doAddDecor() {
//        System.out.println("\nType of decoration");
//        String itemName = input.next();
//        System.out.println("\nColour of decoration");
//        String itemColour = input.next();
//        System.out.println("\nQuantity of decoration");
//        int itemQuantity = Integer.parseInt(input.next());
//        decoration = new Decoration(itemName, itemColour, itemQuantity);
//        if (party.getDecorList().contains(decoration)) {
//            System.out.println("Decoration is already on the decor list");
//        } else {
//            party.getDecorList().add(decoration);
//            System.out.println("Decoration has been added to the decor list");
//        }
//    }
//
//    //EFFECTS: allows user to remove a decoration from the decor list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: (d.getDecorItemName == decorItemName) && (decorList.contains(d))
//    private void doRemoveDecor() {
//        System.out.println("\nDecoration's name");
//        String decorItemName = input.next();
//        Decoration dummyDecoration = null;
//        for (Decoration d : party.getDecorList()) {
//            if (d.getDecorItemName().equals(decorItemName)) {
//                dummyDecoration = d;
//            }
//        }
//        if (party.removeDecor(dummyDecoration)) {
//            System.out.println("Decoration has been removed from the decor list");
//        } else {
//            System.out.println("Decor not available");
//        }
//    }
//
//    //EFFECTS: allows user to add a food to the food list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: !foodList.contains(food)
//    private void doAddFood() {
//        System.out.println("\nCourse of food");
//        String foodItemCategory = input.next();
//        System.out.println("\nName of food");
//        String foodItemName = input.next();
//        System.out.println("\nHas the food been purchased (type true or false)");
//        Boolean hasBeenPurchased = input.nextBoolean();
//        food = new Food(foodItemCategory, foodItemName, hasBeenPurchased);
//        if (party.getFoodList().contains(food)) {
//            System.out.println("Food is already on the food list");
//        } else {
//            party.getFoodList().add(food);
//            System.out.println("Food has been added to the food list");
//        }
//    }
//
//    //EFFECTS: allows user to remove a food from the food list for their party, based on user input
//    //MODIFIES: this
//    //REQUIRES: (f.getFoodItemName == foodItemName) && (foodList.contains(f))
//    private void doRemoveFood() {
//        System.out.println("\nFood's name");
//        String foodItemName = input.next();
//        Food dummyFood = null;
//        for (Food f : party.getFoodList()) {
//            if (f.getFoodItemName().equals(foodItemName)) {
//                dummyFood = f;
//            }
//        }
//        if (party.removeFood(dummyFood)) {
//            System.out.println("Food has been removed from the food list");
//        } else {
//            System.out.println("Food not available");
//        }
//    }
//
//    //EFFECTS: saves party to file
//    private void doSaveParty() {
//        try {
//            jsonPartyWriter.open();
//            jsonPartyWriter.writeParty(party);
//            jsonPartyWriter.close();
//            System.out.println("Saved " + party.getOccasionForParty() + " party to " + JSON_PARTY_STORE);
//        } catch (FileNotFoundException fnfe) {
//            System.out.println("Couldn't save " + party.getOccasionForParty() + " party to file " + JSON_PARTY_STORE);
//        }
//    }
//
//    //EFFECTS: loads party from file
//    private void doLoadPartyFromFile() {
//        try {
//            party = jsonPartyReader.readParty();
//            System.out.println("Loaded " + party.getOccasionForParty() + " party from " + JSON_PARTY_STORE);
//            System.out.println(JSON_PARTY_STORE);
//        } catch (IOException ioe) {
//            System.out.println("Unable to read from file: " + JSON_PARTY_STORE);
//        }
//    }
//
//    //EFFECTS: allows the user to view the guest list for their party
//    private void doViewGuestList() {
//        for (Guest g : party.getGuestList()) {
//            System.out.println(g.toString());
//        }
//    }
//
//    //EFFECTS: allows the user to view the decor list for their party
//    private void doViewDecorList() {
//        for (Decoration d : party.getDecorList()) {
//            System.out.println(d.toString());
//        }
//    }
//
//    //EFFECTS: allows the user to view the food list for their party
//    private void doViewFoodList() {
//        for (Food f : party.getFoodList()) {
//            System.out.println(f.toString());
//        }
//    }
//
//    //EFFECTS: initializes the scanner
//    private void init() {
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//        jsonPartyWriter = new JsonWriter(JSON_PARTY_STORE);
//        jsonPartyReader = new JsonReader(JSON_PARTY_STORE);
//    }
//
//    //EFFECTS: displays a menu of options that require user input
//    private void displayOptions() {
//        System.out.println("\nSelect from the following options:");
//        System.out.println("\tcreate new party");
//        System.out.println("\tadd guest to guest list");
//        System.out.println("\tadd decoration to decor list");
//        System.out.println("\tadd food to food list");
//        System.out.println("\tremove guest from guest list");
//        System.out.println("\tremove decoration from decor list");
//        System.out.println("\tremove food from food list");
//        System.out.println("\tview guest list");
//        System.out.println("\tview decor list");
//        System.out.println("\tview food list");
//        System.out.println("\tsave party");
//        System.out.println("\tload party from file");
//        System.out.println("\tquit party planner"); //quits the organizer
//    }
//}