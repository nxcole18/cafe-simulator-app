package persistence;

import model.Order;
import model.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class is modeled based on JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads orders from JSON data in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads and returns order from file;
    // throws IOException if an error occurs reading data from file
    public Order read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses order from JSON object and returns it
    private Order parseOrder(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Order o = new Order(name);
        addProducts(o, jsonObject);
        return o;
    }

    // MODIFIES: o
    // EFFECTS: parses products from JSON object and adds them to the order
    private void addProducts(Order o, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("order");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            add(o, nextProduct);
        }
    }

    // MODIFIES:o
    // EFFECTS: parses product from JSON object and adds it to the order
    private void add(Order o, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int prepareTime = jsonObject.getInt("prepare time");
        String description = jsonObject.getString("description");
        Product product = new Product(name, prepareTime, description);
        o.add(product);
    }
}
