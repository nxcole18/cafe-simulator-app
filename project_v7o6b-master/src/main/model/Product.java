package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a product having a name, prepare time (minutes), and description
public class Product implements Writable {
    private final String productName;
    private final int productPrepareTime;
    private final String productDescription;

    // REQUIRES: non-empty product name and description; prepare time >= 0
    // EFFECTS: constructs a product with a name, time to prepare, and description
    public Product(String productName, int productPrepareTime, String productDescription) {
        this.productName = productName;
        this.productPrepareTime = productPrepareTime;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrepareTime() {
        return productPrepareTime;
    }

    public String getProductDescription() {
        return productDescription;
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns product as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", productName);
        json.put("prepare time", productPrepareTime);
        json.put("description", productDescription);
        return json;
    }
}
