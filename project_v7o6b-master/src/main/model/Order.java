package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a user's order consisting of products
public class Order implements Writable {
    private ArrayList<Product> order;
    private String name;

    // EFFECTS: constructs an empty list to store an order
    public Order(String name) {
        this.name = name;
        order = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    // EFFECTS: returns a string array list of product names
    public ArrayList<String> getProductNames() {
        ArrayList<String> result = new ArrayList<>();

        for (Product p : order) {
            result.add(p.getProductName());
        }

        return result;
    }

    // MODIFIES: this
    // EFFECTS: adds a product (p) to the order
    public void add(Product p) {
        this.order.add(p);
        EventLog.getInstance().logEvent(new Event(p.getProductName() + " has been added to your order."));
    }

    // REQUIRES: order must have product (p) in list
    // MODIFIES: this
    // EFFECTS: removes product (p) from the order
    public void remove(Product p) {
        this.order.remove(p);
        EventLog.getInstance().logEvent(new Event(p.getProductName() + " has been removed from your order."));
    }

    // REQUIRES: order must have product in list
    // MODIFIES: this
    // EFFECTS: removes product in index i from the order
    public void remove(int i) {
        Product p = order.get(i);
        this.order.remove(i);
        EventLog.getInstance().logEvent(new Event(p.getProductName() + " has been removed from your order."));
    }

    public int getSize() {
        return this.order.size();
    }

    // EFFECTS: returns the total time order will take to prepare
    public int totalOrderTime() {
        int result = 0;

        for (Product p : order) {
            result += p.getProductPrepareTime();
        }

        EventLog.getInstance().logEvent(new Event(
                "You have checked the preparation time for your order: " + result + " minutes."));
        return result;

    }

    // This method is inspired by a StackOverflow post (response 2)
    // Link: https://stackoverflow.com/questions/1532461/stringbuilder-vs-string-concatenation-in-tostring-in-java
    // EFFECTS: returns order contents
    public String viewOrderContents() {
        StringBuilder contents = new StringBuilder();

        for (Product p : order) {
            contents.append("\n\t- ");
            contents.append(p.getProductName());
        }

        EventLog.getInstance().logEvent(new Event("You have viewed your current order."));
        return contents.toString();
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns order as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("order", orderToJson());
        return json;
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns products in this order as a JSON array
    private JSONArray orderToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : order) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
