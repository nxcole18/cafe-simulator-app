package persistence;

import org.json.JSONObject;

// Represents an interface for writable objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
