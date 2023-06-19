package model;

import org.json.JSONObject;
import persistence.Writable;
import java.util.Objects;

//Represents a guest with a specific first name, last name, age and whether or not the guest is attending the party
public class Guest extends PartyItem implements Writable {
    private String firstName;
    private String lastName;
    private int age;
    private Boolean isAttending;

    //EFFECTS: constructs a guest with a specific first name, last name, age, assumes guest is attending the party and
    //         logs the guest into the event log
    public Guest(String firstName, String lastName, int age) {
        this.age = age;
        this.lastName = lastName;
        this.firstName = firstName;
        isAttending = true;
        EventLog.getInstance().logEvent(new Event("New guest with known age was created."));
    }

    //EFFECTS: constructs a guest with a specific first and last name, NO AGE, assumes guest is attending the party, and
    //         logs the guest into the event log
    public Guest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        isAttending = true;
        EventLog.getInstance().logEvent(new Event("New guest with unknown age was created."));
    }

    //EFFECTS: compares a guest to another
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Guest guest = (Guest) o;
        return Objects.equals(firstName, guest.firstName);
    }

    //EFFECTS: turns a guest's first name into HashCode
    //MODIFIES: this
    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }

    //EFFECTS: returns the guest's first name
    public String getFirstName() {
        return firstName;
    }

    //EFFECTS: returns the guest's last name
    public String getLastName() {
        return lastName;
    }

    //EFFECTS: returns the guest's age
    public int getAge() {
        return age;
    }

    //EFFECTS: returns true if the guest is attending the party, false otherwise
    public Boolean getIsAttending() {
        return isAttending;
    }

    //EFFECTS: decides whether the guest is attending the party based on THIS boolean value
    //MODIFIES: this
    public void setIsAttending(Boolean attending) {
        isAttending = attending;
    }

    //EFFECTS: returns "not attending" if guest is not attending, return "attending" otherwise
    //MODIFIES: this
    public String isGuestAttending(Boolean isAttending) {
        if (isAttending == false) {
            return "not attending";
        } else {
            return "attending";
        }
    }

    //overrides toString in PartyItem abstract class
    @Override
    public String toString() {
        String guestName = firstName + " " + lastName;
        String theGuest = guestName + ", " + "age " + String.valueOf(age) + " - " + isGuestAttending(isAttending);
        return theGuest;
    }

    //EFFECTS: converts the guest item from a guest to a string
    //MODIFIES: this
    public String toStringNoAge() {
        String guestName = firstName + " " + lastName;
        String theGuest = guestName + " - " + isGuestAttending(isAttending);
        return theGuest;
    }

    //Overrides toJson in Writable interface
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("first name", firstName);
        json.put("last name", lastName);
        json.put("age", age);
        json.put("attendance status", isAttending);
        return json;
    }
}
