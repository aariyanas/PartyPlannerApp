package persistence;

import org.json.JSONObject;

//Represents objects that are writable to Json
public interface Writable {

    // implemented in the individual party item classes
    // EFFECTS: returns this as JSON object
    // REQUIRES: object is originally not a JSON object
    // MODIFIES: this
    JSONObject toJson();
}